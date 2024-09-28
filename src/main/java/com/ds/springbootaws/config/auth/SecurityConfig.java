package com.ds.springbootaws.config.auth;

import com.ds.springbootaws.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomOAuth2UserService customOAuth2UserService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth ->
            {
              auth.requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**","/post/**").permitAll();
              auth.requestMatchers("/api/v1/**").hasRole(Role.USER.name());
            }
        )
        .logout(logout -> logout.logoutSuccessUrl("/"))
        .oauth2Login(oauth -> oauth
            .userInfoEndpoint(userinfo ->
                userinfo.userService(customOAuth2UserService))
        );

    return http.build();
  }
}
