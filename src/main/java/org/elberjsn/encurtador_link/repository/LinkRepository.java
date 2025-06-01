package org.elberjsn.encurtador_link.repository;

import org.elberjsn.encurtador_link.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findByUserLinkId(Long user);

}
