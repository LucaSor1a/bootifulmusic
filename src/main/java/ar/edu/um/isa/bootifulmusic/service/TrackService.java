package ar.edu.um.isa.bootifulmusic.service;

import ar.edu.um.isa.bootifulmusic.domain.Album;
import ar.edu.um.isa.bootifulmusic.domain.Track;
import ar.edu.um.isa.bootifulmusic.repository.AlbumRepository;
import ar.edu.um.isa.bootifulmusic.repository.TrackRepository;
import ar.edu.um.isa.bootifulmusic.service.dto.TrackDTO;
import ar.edu.um.isa.bootifulmusic.service.mapper.TrackMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Track}.
 */
@Service
@Transactional
public class TrackService {

    private final Logger log = LoggerFactory.getLogger(TrackService.class);

    private final TrackRepository trackRepository;

    private final TrackMapper trackMapper;

    @Autowired
    AlbumRepository albumRepository;

    public TrackService(TrackRepository trackRepository, TrackMapper trackMapper) {
        this.trackRepository = trackRepository;
        this.trackMapper = trackMapper;
    }

    /**
     * Save a track.
     *
     * @param trackDTO the entity to save.
     * @return the persisted entity.
     */
    public TrackDTO save(TrackDTO trackDTO) {
        log.debug("Request to save Track : {}", trackDTO);
        Track track = trackMapper.toEntity(trackDTO);
        track = trackRepository.save(track);
        return trackMapper.toDto(track);
    }

    /**
     * Partially update a track.
     *
     * @param trackDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TrackDTO> partialUpdate(TrackDTO trackDTO) {
        log.debug("Request to partially update Track : {}", trackDTO);

        return trackRepository
            .findById(trackDTO.getId())
            .map(existingTrack -> {
                trackMapper.partialUpdate(existingTrack, trackDTO);

                return existingTrack;
            })
            .map(trackRepository::save)
            .map(trackMapper::toDto);
    }

    /**
     * Get all the tracks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TrackDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tracks");
        return trackRepository.findAll(pageable).map(trackMapper::toDto);
    }

    /**
     * Get one track by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TrackDTO> findOne(Long id) {
        log.debug("Request to get Track : {}", id);
        return trackRepository.findById(id).map(trackMapper::toDto);
    }

    /**
     * Delete the track by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Track : {}", id);
        trackRepository.deleteById(id);
    }

    /**
     * Get all tracks by album.
     *
     * @param pageable the pagination information.
     * @param id of the album.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TrackDTO> findByAlbum(Pageable pageable, Long id) {
        log.debug("Request to get all Tracks of Album : {}", id);
        Album album = albumRepository.getById(id);
        return trackRepository.findByAlbum(pageable, album).map(trackMapper::toDto);
    }
}
