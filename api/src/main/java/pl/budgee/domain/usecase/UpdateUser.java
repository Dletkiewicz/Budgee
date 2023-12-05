package pl.budgee.domain.usecase;

import lombok.RequiredArgsConstructor;
import pl.budgee.domain.model.User;
import pl.budgee.domain.model.UserNotFoundException;
import pl.budgee.domain.port.UserRepository;

@RequiredArgsConstructor
public class UpdateUser {

  public record UpdateUserRequest(String firstName, String lastName, String username, String password) {
  }

  private final UserRepository users;

  public User update(UpdateUserRequest request) {
    var user = users.findOneByUsername(request.username()).orElseThrow(() -> new UserNotFoundException(request.username()));

    return users.save(user.updateData(request));
  }
}
