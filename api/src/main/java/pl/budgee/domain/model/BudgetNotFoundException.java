package pl.budgee.domain.model;

import pl.budgee.domain.model.Budget.BudgetId;

public class BudgetNotFoundException extends RuntimeException{

  public BudgetNotFoundException(BudgetId id) {
    super("Budget of ID [" + id.value() + "] not found");
  }
}
