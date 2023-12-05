package pl.budgee.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.budgee.domain.port.BudgetRepository;
import pl.budgee.domain.port.UserRepository;
import pl.budgee.domain.usecase.CreateUser;
import pl.budgee.domain.usecase.DeleteUser;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class UsecaseConfig {

  private final UserRepository users;
  private final BudgetRepository budgets;

  @Bean
  CreateUser createUser() {
    return new CreateUser(users, budgets);
  }

  @Bean
  DeleteUser deleteUser() {
    return new DeleteUser(users);
  }
}
