package pl.budgee.adapter.jpa.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import pl.budgee.domain.model.Audit;
import pl.budgee.domain.model.User;

import java.time.Instant;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AuditMixin {

  @CreatedBy
  @Column(name = "created_by")
  @Type(JsonBinaryType.class)
  private User createdBy;

  @CreatedDate
  @Column(name = "created_at")
  private Instant createdAt;

  @LastModifiedBy
  @Column(name = "last_modified_by")
  @Type(JsonBinaryType.class)
  private User lastModifiedBy;

  @LastModifiedDate
  @Column(name = "last_modified_at")
  private Instant lastModifiedAt;

  public Audit toModel() {
    return new Audit(createdBy, createdAt, lastModifiedBy, lastModifiedAt);
  }
}