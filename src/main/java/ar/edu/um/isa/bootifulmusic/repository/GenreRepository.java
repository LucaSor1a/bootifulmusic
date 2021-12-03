package ar.edu.um.isa.bootifulmusic.repository;

import ar.edu.um.isa.bootifulmusic.domain.Genre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Genre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {}
