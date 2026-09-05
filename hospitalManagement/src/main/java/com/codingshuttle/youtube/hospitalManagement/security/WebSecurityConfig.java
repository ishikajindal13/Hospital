package com.codingshuttle.youtube.hospitalManagement.security;

import com.codingshuttle.youtube.hospitalManagement.entity.type.RoleType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;


import static com.codingshuttle.youtube.hospitalManagement.entity.type.PermissionType.*;
import static com.codingshuttle.youtube.hospitalManagement.entity.type.RoleType.*;

import java.io.IOException;

import static com.codingshuttle.youtube.hospitalManagement.entity.type.RoleType.ADMIN;
import static com.codingshuttle.youtube.hospitalManagement.entity.type.RoleType.*;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity
public class WebSecurityConfig {



//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
////        httpSecurity.formLogin(formConfig->formConfig.authenticationDetailsSource())
//        httpSecurity
//                .authorizeHttpRequests(auth->auth
//                        .requestMatchers("/public/**","/auth/**").permitAll()
////                        .requestMatchers("/admin/**").authenticated()
//                                .requestMatchers("/admin/**").hasRole("ADMIN")
//                                .requestMatchers("/doctors/**").hasAnyRole("DOCTORS","ADMIN")
//                )
//
//                .formLogin(Customizer.withDefaults()); // login by sprong framework
//
//        return httpSecurity.build();
//    }

private final JwtAuthFilter jwtAuthFilter;
private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final HandlerExceptionResolver handlerExceptionResolver;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(csrfConig-> csrfConig.disable())
                .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
                                .requestMatchers("/public/**","/auth/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/admin/**")
                        .hasAnyAuthority(APPOINTMENT_DELETE.name(),
                                USER_MANAGE.name())
                                .requestMatchers("/admin/**").hasRole(ADMIN.name())
                                .requestMatchers("/doctors/**").hasAnyRole(DOCTOR.name(),ADMIN.name())
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oAuth2->oAuth2
                        .failureHandler(
                                (request, response, exception) ->{
                                        log.error("OAuth2 error :{}",exception.getMessage());
                                    handlerExceptionResolver.resolveException(request,response,null,exception);

                                })
                        .successHandler(oAuth2SuccessHandler)
                )
//                        .successHandler( (request, response, authentication) -> {
//
//                        })

                .exceptionHandling(exceptionConfig->
                        exceptionConfig.accessDeniedHandler((AccessDeniedHandler) (request, response, accessDeniedException) -> {
                            handlerExceptionResolver.resolveException(request,response,null,accessDeniedException);
                        }));
        return httpSecurity.build();
    }

}


// remove .formLogin
// now we habve tyo handl;e it by apis
// we also dont need session security , csrf security is also nopt needed
// earkier they we storing session with user id  in its memeory by its own - and when we rel;oad it ch3cks whether that session is their or not , noyt in any db
// now we want to go ststeless ->disaable csrf and sessionsecurity
// we want jwt to handle authenticatuion
// no need to store anything on server side
// just store user ki info , authentication will bne saved by jwt via token

