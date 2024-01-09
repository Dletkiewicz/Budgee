package pl.budgee.domain.model;

import pl.budgee.domain.model.Expense.ExpenseId;

public class ExpenseNotFoundException extends RuntimeException{

  public ExpenseNotFoundException(ExpenseId id) {
    super("Expense of ID [" + id.value() + "] not found");
  }
}
