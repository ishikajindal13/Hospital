package com.codingshuttle.youtube.hospitalManagement.security;


import com.codingshuttle.youtube.hospitalManagement.dto.LoginRequestDto;
import com.codingshuttle.youtube.hospitalManagement.dto.LoginResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.SignupResponseDto;
import com.codingshuttle.youtube.hospitalManagement.entity.type.AuthProviderType;
import com.codingshuttle.youtube.hospitalManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.codingshuttle.youtube.hospitalManagement.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RequiredArgsConstructor


public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;   // this is one way hash , u can never go back

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
     Authentication authentication=authenticationManager.authenticate(
             new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword())
     );
     User user=(User)authentication.getPrincipal(); // here i have stored user
        // to make token , i will make a new service AuthUtil

        String token=authUtil.generateAccessToken(user);

         return new LoginResponseDto(token, user.getId());
    }

    public User signUpInternal(LoginRequestDto signupRequestDto,AuthProviderType authProviderType, String providerId){
        User user=userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);
        if(user!=null) throw new IllegalArgumentException("user alredy exists");

       user= User.builder()
                .username(signupRequestDto.getUsername())
               .providerId(providerId)
               .providerType(authProviderType)
                .build();
       if(authProviderType==AuthProviderType.EMAIL){
           user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
       }
       return userRepository.save(user);
    }


    // login conmtroller (by email id)
    public SignupResponseDto signup(LoginRequestDto signupRequestDto) {
       User user=signUpInternal(signupRequestDto,AuthProviderType.EMAIL,null);
        return new SignupResponseDto(user.getId(), user.getUsername());

    }

    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
        AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

        User user = userRepository.findByProviderIdAndProviderType(providerId, providerType).orElse(null);

        String email = oAuth2User.getAttribute("email");

        User emailUser = userRepository.findByUsername(email).orElse(null);

        if (user == null && emailUser == null) {
            //signup flow
            // usrr doesnt exist
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
            user = signUpInternal(new LoginRequestDto(username, null),providerType,providerId);

        } else if (user != null) {
            // agar user chnage hua h toh voh bho save kr liya
            if (email != null && !email.isBlank() && !email.equals(user.getUsername())) {
                user.setUsername(email);
                userRepository.save(user);
            }
        } else {
            // if by em,ial id , user exists and it now trryoing login by oauth
            throw new BadCredentialsException("This email is already registred with provider" + emailUser.getProviderType());
        }

        LoginResponseDto loginResponseDto=new LoginResponseDto(authUtil.generateAccessToken(user),user.getId());
        return ResponseEntity.ok(loginResponseDto);

        // ftech provideerType an providerId
        // save the provodertype and providerid info with user
        // if the user has an account : direcl;y login
        // otherwise -> signupn -> login
        //
    }

}
