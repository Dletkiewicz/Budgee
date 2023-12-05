package pl.budgee.adapter.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import pl.budgee.domain.model.Audit;

import java.time.Instant;

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