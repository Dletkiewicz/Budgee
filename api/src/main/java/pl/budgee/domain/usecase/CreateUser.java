package pl.budgee.domain.usecase;

import lombok.RequiredArgsConstructor;
import pl.budgee.domain.model.User;
import pl.budgee.domain.model.UsernameAlreadyExists;
import pl.budgee.domain.port.UserRepository;

@RequiredArgsConstructor
public class CreateUser {

  public record CreateUserRequest(String firstName, String lastName, String username, String password){
  }

  private final UserRepository users;

  public User createUser(CreateUserRequest request) {


    return null;
  }
}
