package com.example.memderadmin.config;

import com.example.memderadmin.app.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AuthenticationWebConfig implements WebMvcConfigurer {

    private final AuthService authService;

    public AuthenticationWebConfig(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void addArgumentResolvers(List argumentResolvers) {
        argumentResolvers.add(createMemberTokenArgumentResolver());
    }

    @Bean
    public MemberTokenArgumentResolver createMemberTokenArgumentResolver() {
        return new MemberTokenArgumentResolver(authService);
    }
}
