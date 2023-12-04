package pl.budgee.domain.usecase;

import lombok.RequiredArgsConstructor;
import pl.budgee.domain.model.User;
import pl.budgee.domain.model.User.UserId;
import pl.budgee.domain.model.UsernameTakenException;
import pl.budgee.domain.port.UserRepository;

@RequiredArgsConstructor
public class CreateUser {

  public record CreateUserRequest(String firstName, String lastName, String username, String password){
  }

  private final UserRepository users;

  public User create(CreateUserRequest request) {
    if (users.existsByUsername(request.username())) {
      throw new UsernameTakenException(request.username());
    }

    return users.save(new User(
        UserId.create(),
        request.firstName(),
        request.lastName(),
        request.username(),
        request.password(),
        null));
  }

  //todo create budget
}
