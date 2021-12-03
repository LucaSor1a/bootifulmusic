package ar.edu.um.isa.bootifulmusic.repository;

import ar.edu.um.isa.bootifulmusic.domain.Album;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Album entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {}
