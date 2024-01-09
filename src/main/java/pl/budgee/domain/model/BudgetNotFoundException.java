package pl.budgee.domain.model;

import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.User.UserId;

public class BudgetNotFoundException extends RuntimeException{

  public BudgetNotFoundException(UserId id) {
    super("Budget of user [" + id.value() + "] not found");
  }

  public BudgetNotFoundException(BudgetId id) {
    super("Budget of ID [" + id.value() + "] not found");
  }
}
