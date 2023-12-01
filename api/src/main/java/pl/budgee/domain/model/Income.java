package pl.budgee.domain.model;

import pl.budgee.domain.model.Budget.BudgetId;

import java.math.BigDecimal;
import java.util.UUID;

public record Income(IncomeId id, BudgetId budgetId, BigDecimal amount, String category, String description, Audit audit) {

  public record IncomeId(UUID value) {
    public static IncomeId create() {
      return new IncomeId(UUID.randomUUID());
    }
  }
}
