package pl.budgee.domain.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.BudgetNotFoundException;
import pl.budgee.domain.model.Expense;
import pl.budgee.domain.port.BudgetRepository;
import pl.budgee.domain.port.ExpenseRepository;

@RequiredArgsConstructor
public class ListExpenses {

  public record ListExpensesRequest(BudgetId budgetId, Pageable pageable) {
  }

  private final BudgetRepository budgets;
  private final ExpenseRepository expenses;

  public Slice<Expense> list(ListExpensesRequest request) {
    var budget = budgets.findOneById(request.budgetId()).orElseThrow(() -> new BudgetNotFoundException(request.budgetId()));

    return expenses.findAll(budget.id(), request.pageable());
  }
}
