package pl.budgee.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.budgee.domain.port.BudgetRepository;
import pl.budgee.domain.port.UserRepository;
import pl.budgee.domain.usecase.CreateUser;
import pl.budgee.domain.usecase.DeleteUser;
import pl.budgee.domain.usecase.UpdateUser;

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

  @Bean
  UpdateUser updateUser() {
    return new UpdateUser(users);
  }
}
