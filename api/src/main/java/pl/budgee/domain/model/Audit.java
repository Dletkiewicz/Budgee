package pl.budgee.domain.model;

import java.time.Instant;

public record Audit(Instant createdAt, Instant lastModifiedAt) {
}
