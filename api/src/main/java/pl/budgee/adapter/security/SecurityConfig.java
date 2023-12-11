package pl.budgee.adapter.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(auth -> {
      auth.requestMatchers(
          "/swagger-ui/**",
          "/swagger-ui.html",
          "/v3/api-docs",
          "/v3/api-docs/**",
          "/api/v3/api-docs/**"
          ).permitAll();
      auth.anyRequest().authenticated();
    })
        .oauth2Login(Customizer.withDefaults())
        .formLogin(Customizer.withDefaults())
        .build();

  }
}
