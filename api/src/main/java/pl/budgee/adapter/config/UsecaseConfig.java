package pl.budgee.adapter.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import pl.budgee.domain.port.BudgetRepository;
import pl.budgee.domain.port.UserRepository;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class UsecaseConfig {

  private final UserRepository users;
  private final BudgetRepository budgets;
}
