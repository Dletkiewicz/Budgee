package pl.budgee.domain.usecase;

import lombok.RequiredArgsConstructor;
import pl.budgee.domain.model.UserNotFoundException;
import pl.budgee.domain.port.UserRepository;

@RequiredArgsConstructor
public class DeleteUser {

  public record DeleteUserRequest(String username) {
  }

  private final UserRepository users;

  public void delete(DeleteUserRequest request) {
    var user = users.findOneByUsername(request.username()).orElseThrow(() -> new UserNotFoundException(request.username()));
    users.delete(user.id());
  }
}
