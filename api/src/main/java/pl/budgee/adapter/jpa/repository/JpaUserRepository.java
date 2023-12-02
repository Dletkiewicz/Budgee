package pl.budgee.adapter.jpa.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.budgee.adapter.jpa.entity.UserEntity;
import pl.budgee.domain.port.UserRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class JpaUserRepository implements UserRepository {

  interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {

  }


}
