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
import pl.budgee.adapter.web.WebModels.UserDto;
import pl.budgee.domain.model.User.UserId;
import pl.budgee.domain.model.UserNotFoundException;
import pl.budgee.domain.model.UsernameTakenException;
import pl.budgee.domain.usecase.CreateUser;
import pl.budgee.domain.usecase.DeleteUser;
import pl.budgee.domain.usecase.DeleteUser.DeleteUserRequest;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

  private final CreateUser createUser;
  private final DeleteUser deleteUser;

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

  @DeleteMapping("/users/{userId}")
  @Operation(summary = "Delete user by ID")
  ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
    try {
      var request = new DeleteUserRequest(new UserId(userId));
      deleteUser.delete(request);
      return ResponseEntity.noContent().build();
    } catch (UserNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
    }
  }

}