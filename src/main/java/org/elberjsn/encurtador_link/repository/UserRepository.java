package org.elberjsn.encurtador_link.repository;

import org.elberjsn.encurtador_link.model.UserLink;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserLink, Long> {
    Optional<UserLink> findByEmailAndPassword(String email, String password);
}
