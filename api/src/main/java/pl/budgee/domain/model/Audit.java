package pl.budgee.domain.model;

import java.time.Instant;

public record Audit(User createdBy, Instant createdAt, User lastModifiedBy, Instant lastModifiedAt) {
}
