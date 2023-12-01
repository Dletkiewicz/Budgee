package pl.budgee.domain.model;

import java.time.Instant;
import java.util.UUID;

public record User(UserId id, String username, String password, Audit audit) {

  public record UserId(UUID value) {

    public static UserId create() {
      return new UserId(UUID.randomUUID());
    }
  }
}
