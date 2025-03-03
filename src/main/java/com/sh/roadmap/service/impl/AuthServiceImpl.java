package com.sh.roadmap.service.impl;

import com.sh.roadmap.builder.ResponseBuilder;
import com.sh.roadmap.entity.UserEntity;
import com.sh.roadmap.exception.LeaderboardException;
import com.sh.roadmap.model.Response;
import com.sh.roadmap.payload.RegisterUserRequest;
import com.sh.roadmap.payload.request.AuthenticateRequest;
import com.sh.roadmap.payload.response.AuthenticationResponse;
import com.sh.roadmap.payload.response.UserResponse;
import com.sh.roadmap.repository.UserRepository;
import com.sh.roadmap.service.AuthService;
import com.sh.roadmap.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Response createUser(RegisterUserRequest request) throws LeaderboardException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new LeaderboardException("USR000");
        }
        UserEntity user = UserEntity
                .builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        UserResponse userResponse = new ModelMapper().map(userRepository.save(user), UserResponse.class);
        return ResponseBuilder.buildSuccessResponse(userResponse);
    }

    @Override
    public Response authenticate(AuthenticateRequest request) throws LeaderboardException {
        UserEntity user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new LeaderboardException("USR001"));
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new LeaderboardException("USR002");
        }
        String jwtToken = jwtService.generateToken(user);

        AuthenticationResponse authenticationResponse = AuthenticationResponse
                .builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpiration())
                .build();

        return ResponseBuilder.buildSuccessResponse(authenticationResponse);
    }
}
