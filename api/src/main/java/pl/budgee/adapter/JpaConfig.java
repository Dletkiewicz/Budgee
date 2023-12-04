package pl.budgee.adapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(considerNestedRepositories = true)
@EnableJpaAuditing
public class JpaConfig {
}
