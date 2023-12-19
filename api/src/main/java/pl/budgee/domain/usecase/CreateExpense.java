package pl.budgee.domain.usecase;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.BudgetNotFoundException;
import pl.budgee.domain.model.Expense;
import pl.budgee.domain.model.Expense.ExpenseId;
import pl.budgee.domain.model.ExpenseType;
import pl.budgee.domain.port.BudgetRepository;
import pl.budgee.domain.port.ExpenseRepository;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CreateExpense {

  public record CreateExpenseRequest(BudgetId budgetId, BigDecimal amount, ExpenseType type, @Nullable String description) {
  }

  private final ExpenseRepository expenses;
  private final BudgetRepository budgets;

  public Expense create(CreateExpenseRequest request) {
    var budget = budgets.findOneById(request.budgetId()).orElseThrow(() -> new BudgetNotFoundException(request.budgetId()));

    budgets.subtractBalance(budget, request.amount());

    return expenses.save(new Expense(ExpenseId.create(), budget.id(), request.amount(), request.type(), request.description(), null));
  }
}
