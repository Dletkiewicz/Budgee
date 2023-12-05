package pl.budgee.adapter.web;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.budgee.adapter.web.WebModels.CreateUserDto;
import pl.budgee.adapter.web.WebModels.UpdateUserDto;
import pl.budgee.adapter.web.WebModels.UserDto;
import pl.budgee.domain.model.UserNotFoundException;
import pl.budgee.domain.model.UsernameTakenException;
import pl.budgee.domain.usecase.CreateUser;
import pl.budgee.domain.usecase.DeleteUser;
import pl.budgee.domain.usecase.DeleteUser.DeleteUserRequest;
import pl.budgee.domain.usecase.UpdateUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

  private final CreateUser createUser;
  private final DeleteUser deleteUser;
  private final UpdateUser updateUser;

  @PostMapping("/users")
  @Operation(summary = "Create new user")
  ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto payload) {
    try {
      var request = payload.toRequest();
      var user = createUser.create(request);
      return ResponseEntity.created(
          ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{userId}").build(user.id().value())).body(UserDto.of(user));
    } catch (UsernameTakenException e) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage(), e);
    }
  }

  @PutMapping("/users/{username}")
  @Operation(summary = "Update user data")
  ResponseEntity<UserDto> updateUser(@Valid @RequestBody UpdateUserDto payload) {
    try {
      var request = payload.toRequest();
      var user = updateUser.update(request);
      return ResponseEntity.ok(UserDto.of(user));
    } catch (UsernameTakenException e) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage(), e);
    }
  }

  @DeleteMapping("/users/{username}")
  @Operation(summary = "Delete user by ID")
  ResponseEntity<Void> deleteUser(@PathVariable String username) {
    try {
      var request = new DeleteUserRequest(username);
      deleteUser.delete(request);
      return ResponseEntity.noContent().build();
    } catch (UserNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
    }
  }

}
