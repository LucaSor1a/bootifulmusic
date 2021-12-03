package ar.edu.um.isa.bootifulmusic.repository;

import ar.edu.um.isa.bootifulmusic.domain.Artist;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Artist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {}
