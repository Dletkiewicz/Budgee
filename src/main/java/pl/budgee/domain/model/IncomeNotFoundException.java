package pl.budgee.domain.model;

import pl.budgee.domain.model.Income.IncomeId;

public class IncomeNotFoundException extends RuntimeException{

  public IncomeNotFoundException(IncomeId id) {
    super("Income of ID [" + id.value() + "] not found");
  }
}
