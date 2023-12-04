package pl.budgee.domain.model;

import pl.budgee.domain.model.User.UserId;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(UserId id) {
    super("User [" + id.value() + "] not found");
  }
}
