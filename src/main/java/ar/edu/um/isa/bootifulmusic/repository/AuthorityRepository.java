package ar.edu.um.isa.bootifulmusic.repository;

import ar.edu.um.isa.bootifulmusic.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
