package ar.edu.um.isa.bootifulmusic.repository;

import ar.edu.um.isa.bootifulmusic.domain.Album;
import ar.edu.um.isa.bootifulmusic.domain.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Track entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    @Query("select track from Track track where track.album = :album")
    Page<Track> findByAlbum(Pageable pageable, @Param("album") Album album);
}
