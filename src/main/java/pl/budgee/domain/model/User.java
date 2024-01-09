package pl.budgee.domain.model;

import pl.budgee.domain.usecase.UpdateUser.UpdateUserRequest;

import java.util.UUID;

public record User(UserId id, String firstName, String lastName, String username, String password, Audit audit) {

  public record UserId(UUID value) {

    public static UserId create() {
      return new UserId(UUID.randomUUID());
    }
  }

  public User updateData(UpdateUserRequest request) {
    return new User(id, request.firstName(), request.lastName(), request.username(), request.password(), audit);
  }
}
