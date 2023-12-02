package pl.budgee.domain.model;

import pl.budgee.domain.model.User.UserId;

import java.time.Instant;

public record Audit(UserId createdBy, Instant createdAt, UserId lastModifiedBy, Instant lastModifiedAt) {
}
