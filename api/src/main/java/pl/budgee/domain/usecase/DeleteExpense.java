package pl.budgee.domain.usecase;

import lombok.RequiredArgsConstructor;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.BudgetNotFoundException;
import pl.budgee.domain.model.Expense.ExpenseId;
import pl.budgee.domain.model.ExpenseNotFoundException;
import pl.budgee.domain.port.BudgetRepository;
import pl.budgee.domain.port.ExpenseRepository;

@RequiredArgsConstructor
public class DeleteExpense {

  public record DeleteExpenseRequest(BudgetId budgetId, ExpenseId id) {
  }

  private final ExpenseRepository expenses;
  private final BudgetRepository budgets;

  public void delete(DeleteExpenseRequest request) {
    var budget = budgets.findOneById(request.budgetId()).orElseThrow(() -> new BudgetNotFoundException(request.budgetId()));
    var expense = expenses.findOneById(request.budgetId(), request.id()).orElseThrow(() -> new ExpenseNotFoundException(request.id()));

    expenses.delete(budget.id(), expense.id());
  }
}
