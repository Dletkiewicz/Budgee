package pl.budgee.domain.usecase;

import lombok.RequiredArgsConstructor;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.BudgetNotFoundException;
import pl.budgee.domain.model.Expense;
import pl.budgee.domain.model.Expense.ExpenseId;
import pl.budgee.domain.model.ExpenseNotFoundException;
import pl.budgee.domain.port.BudgetRepository;
import pl.budgee.domain.port.ExpenseRepository;

@RequiredArgsConstructor
public class GetExpense {

  public record GetExpenseRequest(BudgetId budgetId, ExpenseId id) {
  }

  private final ExpenseRepository expenses;
  private final BudgetRepository budgets;

  public Expense get(GetExpenseRequest request) {
    var budget = budgets.findOneById(request.budgetId()).orElseThrow(() -> new BudgetNotFoundException(request.budgetId()));

    return expenses.findOneById(budget.id(), request.id).orElseThrow(() -> new ExpenseNotFoundException(request.id));
  }
}
