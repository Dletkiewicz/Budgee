package pl.budgee.adapter.jpa.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import pl.budgee.domain.model.Audit;
import pl.budgee.domain.model.User;
import pl.budgee.domain.model.User.UserId;

import java.time.Instant;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AuditMixin {

  @CreatedBy
  @Column(name = "created_by")
  private UUID createdBy;

  @CreatedDate
  @Column(name = "created_at")
  private Instant createdAt;

  @LastModifiedBy
  @Column(name = "last_modified_by")
  private UUID lastModifiedBy;

  @LastModifiedDate
  @Column(name = "last_modified_at")
  private Instant lastModifiedAt;

  public Audit toModel() {
    return new Audit(new UserId(createdBy), createdAt, new UserId(lastModifiedBy), lastModifiedAt);
  }
}