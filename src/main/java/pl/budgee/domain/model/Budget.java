package pl.budgee.domain.model;

import pl.budgee.domain.model.User.UserId;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record Budget(BudgetId id, UserId userId, BigDecimal balance, Currency currency) {

  public record BudgetId(UUID value) {

    public static BudgetId create() {
      return new BudgetId(UUID.randomUUID());
    }
  }
}
