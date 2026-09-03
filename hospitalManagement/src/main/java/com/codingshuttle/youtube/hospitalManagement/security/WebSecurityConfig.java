package com.codingshuttle.youtube.hospitalManagement.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
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
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(csrfConig-> csrfConig.disable())
                .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
                                .requestMatchers("/public/**","/auth/**").permitAll()
//                                .requestMatchers("/admin/**").hasRole("ADMIN")
//                                .requestMatchers("/doctors/**").hasAnyRole("DOCTORS","ADMIN")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

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

