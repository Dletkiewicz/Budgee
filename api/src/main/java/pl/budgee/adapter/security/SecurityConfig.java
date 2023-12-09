package pl.budgee.adapter.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
      http.authorizeHttpRequests(requests -> {
        requests
            .requestMatchers(
                mvc.pattern("/swagger-ui.html"),
                mvc.pattern("/swagger-ui/**"),
                mvc.pattern("/v3/api-docs"),
                mvc.pattern("/v3/api-docs/**"),
                mvc.pattern("/api/v3/api-docs/**")
            )
            .permitAll() // Permit all to OpenAPI
            .anyRequest()
            .authenticated();
      });

      return http.build();
    }

    @Scope("prototype")
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
      return new MvcRequestMatcher.Builder(introspector);
    }

}
