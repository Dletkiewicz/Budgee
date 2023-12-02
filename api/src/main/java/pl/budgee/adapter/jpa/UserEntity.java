package pl.budgee.adapter.jpa;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID databaseId;

  @NaturalId
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "business_id")
  private UUID businessId;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;
}
