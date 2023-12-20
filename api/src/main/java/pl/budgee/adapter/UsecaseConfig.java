package pl.budgee.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.budgee.domain.port.BudgetRepository;
import pl.budgee.domain.port.ExpenseRepository;
import pl.budgee.domain.port.IncomeRepository;
import pl.budgee.domain.port.UserRepository;
import pl.budgee.domain.usecase.*;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class UsecaseConfig {

  private final UserRepository users;
  private final BudgetRepository budgets;
  private final IncomeRepository incomes;
  private final ExpenseRepository expenses;

  @Bean
  GetIncome getIncome() {
    return new GetIncome(incomes, budgets);
  }

  @Bean
  GetIncomeSum getIncomeSum() {
    return new GetIncomeSum(budgets, incomes);
  }

  @Bean
  ListExpenses listExpenses() {
    return new ListExpenses(budgets, expenses);
  }

  @Bean
  ListIncomes listIncomes() {
    return new ListIncomes(budgets, incomes);
  }

  @Bean
  GetExpense getExpense() {
    return new GetExpense(expenses, budgets);
  }

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

  @Bean
  CreateIncome createIncome() {
    return new CreateIncome(incomes, budgets);
  }

  @Bean
  DeleteIncome deleteIncome() {
    return new DeleteIncome(incomes, budgets);
  }

  @Bean
  CreateExpense createExpense() {
    return new CreateExpense(expenses, budgets);
  }

  @Bean
  DeleteExpense deleteExpense() {
    return new DeleteExpense(expenses, budgets);
  }
}
