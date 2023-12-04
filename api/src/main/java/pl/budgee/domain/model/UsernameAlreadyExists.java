package pl.budgee.domain.model;

public class UsernameAlreadyExists extends RuntimeException {

  public UsernameAlreadyExists(String username) {
    super("Username [" + username + "] already exists!");
  }
}
