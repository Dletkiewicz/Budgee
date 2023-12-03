package pl.budgee.adapter.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import pl.budgee.domain.model.Audit;
import pl.budgee.domain.model.User.UserId;

import java.time.Instant;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AuditMixin {

  @CreatedDate
  @Column(name = "created_at")
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "last_modified_at")
  private Instant lastModifiedAt;

  public Audit toModel() {
    return new Audit(createdAt, lastModifiedAt);
  }
}