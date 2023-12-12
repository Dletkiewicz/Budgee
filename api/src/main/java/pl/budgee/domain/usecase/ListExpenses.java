package pl.budgee.domain.usecase;

import lombok.RequiredArgsConstructor;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.BudgetNotFoundException;
import pl.budgee.domain.model.Expense;
import pl.budgee.domain.port.BudgetRepository;
import pl.budgee.domain.port.ExpenseRepository;

import java.util.List;

@RequiredArgsConstructor
public class ListExpenses {

  public record ListExpensesRequest(BudgetId budgetId) {
  }

  private final BudgetRepository budgets;
  private final ExpenseRepository expenses;

  public List<Expense> list(ListExpensesRequest request) {
    var budget = budgets.findOneById(request.budgetId()).orElseThrow(() -> new BudgetNotFoundException(request.budgetId()));

    return expenses.findAll(budget.id());
  }
}
