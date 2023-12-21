package pl.budgee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.budgee.domain.model.User;
import pl.budgee.domain.model.User.UserId;
import pl.budgee.domain.model.UserNotFoundException;
import pl.budgee.domain.port.UserRepository;
import pl.budgee.domain.usecase.CreateUser;
import pl.budgee.domain.usecase.CreateUser.CreateUserRequest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateUserTests {

  public final CreateUser createUser = mock(CreateUser.class);
  private final UserRepository users = mock(UserRepository.class);

  @Test
  void should_create_user_when_username_free() {
    String firstName = "Dariusz";
    String lastName = "Letkiewicz";
    String username = "Dletkiewicz";
    String password = "root";

    CreateUserRequest request = new CreateUserRequest(firstName, lastName, username, password);
    when(users.findOneByUsername(username)).thenThrow(new UserNotFoundException(username));
    when(createUser.create(any(CreateUserRequest.class))).thenReturn(new User(new UserId(UUID.randomUUID()), firstName, lastName, username, password, null));

    var user = createUser.create(request);

    assertEquals(firstName, user.firstName());
    assertEquals(lastName, user.lastName());
    assertEquals(username, user.username());
    assertEquals(password, user.password());
  }

}
