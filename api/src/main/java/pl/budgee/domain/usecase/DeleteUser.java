package pl.budgee.domain.usecase;

import lombok.RequiredArgsConstructor;
import pl.budgee.domain.model.User.UserId;
import pl.budgee.domain.model.UserNotFoundException;
import pl.budgee.domain.port.UserRepository;

@RequiredArgsConstructor
public class DeleteUser {

  public record DeleteUserRequest(UserId id) {
  }

  private final UserRepository users;

  public void delete(DeleteUserRequest request) {
    var user = users.findOneById(request.id()).orElseThrow(() -> new UserNotFoundException(request.id()));
    users.delete(user.id());
  }
}
