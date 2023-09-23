package com.example.discordBackend.service.impl;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.dtos.auth.*;
import com.example.discordBackend.exception.DiscordException;
import com.example.discordBackend.models.User;
import com.example.discordBackend.repos.UserRepo;
import com.example.discordBackend.security.JwtTokenProvider;
import com.example.discordBackend.service.AuthService;
import com.example.discordBackend.utils.Role;
import com.example.discordBackend.utils.RoleValue;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    public static Integer RESET_PASSWORD_TOKEN_EXPIRY = 30;
    private ModelMapper mapper;
    private AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    private MongoTemplate mongoTemplate;

    public AuthServiceImpl(ModelMapper mapper, AuthenticationManager authenticationManager, UserRepo userRepo, JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, MongoTemplate mongoTemplate) {
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public ApiResponse login(LoginReqDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepo.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new DiscordException(HttpStatus.NOT_FOUND, "email not found"));

        String token = jwtTokenProvider.generateToken(authentication);
        return new ApiResponse(true, new LoginResDto(token, user.getEmail(), user.getUsername(), user.getId()), null);
    }

    @Override
    public ApiResponse register(RegisterReqDto registerDto) {
        if(registerDto.getPassword().length() < 6){
            throw new DiscordException(HttpStatus.BAD_REQUEST, "invalid password");
        }

        if(userRepo.existsByEmail(registerDto.getEmail())){
            throw new DiscordException(HttpStatus.BAD_REQUEST, "email already exists");
        }

        Role role = RoleValue.getRole(registerDto.getUserType());
        if(role == null || role == Role.ROLE_ADMIN) {
            throw new DiscordException(HttpStatus.BAD_REQUEST, "invalid userType");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(List.of(role));

        user = userRepo.save(user);
        String token = sendToken(user);

        return new ApiResponse(true, new RegisterResDto(token, user.getEmail(), user.getUsername(), user.getId()), null);
    }

    @Override
    public ApiResponse getUser(Authentication authentication) {
        String email = authentication.getName();
        var user = userRepo.findByEmail(email)
                .orElseThrow(() -> new DiscordException(HttpStatus.NOT_FOUND, String.format("user with email %s not found", email)));
        return new ApiResponse(true, mapper.map(user, UserResDto.class), null);
    }

    private String sendToken(User user){
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        return jwtTokenProvider.generateToken(authenticationToken);
    }
}
