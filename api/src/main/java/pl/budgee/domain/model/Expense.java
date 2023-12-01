package pl.budgee.domain.model;

import pl.budgee.domain.model.Budget.BudgetId;

import java.math.BigDecimal;
import java.util.UUID;

public record Expense(ExpenseId id, BudgetId budgetId, BigDecimal amount, String category, String description, Audit audit) {

  public record ExpenseId(UUID value) {
    public static ExpenseId create() {
      return new ExpenseId(UUID.randomUUID());
    }
  }
}
