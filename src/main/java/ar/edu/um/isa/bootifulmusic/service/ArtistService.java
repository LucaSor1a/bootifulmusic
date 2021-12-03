package ar.edu.um.isa.bootifulmusic.service;

import ar.edu.um.isa.bootifulmusic.domain.Artist;
import ar.edu.um.isa.bootifulmusic.repository.ArtistRepository;
import ar.edu.um.isa.bootifulmusic.service.dto.ArtistDTO;
import ar.edu.um.isa.bootifulmusic.service.mapper.ArtistMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Artist}.
 */
@Service
@Transactional
public class ArtistService {

    private final Logger log = LoggerFactory.getLogger(ArtistService.class);

    private final ArtistRepository artistRepository;

    private final ArtistMapper artistMapper;

    public ArtistService(ArtistRepository artistRepository, ArtistMapper artistMapper) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
    }

    /**
     * Save a artist.
     *
     * @param artistDTO the entity to save.
     * @return the persisted entity.
     */
    public ArtistDTO save(ArtistDTO artistDTO) {
        log.debug("Request to save Artist : {}", artistDTO);
        Artist artist = artistMapper.toEntity(artistDTO);
        artist = artistRepository.save(artist);
        return artistMapper.toDto(artist);
    }

    /**
     * Partially update a artist.
     *
     * @param artistDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ArtistDTO> partialUpdate(ArtistDTO artistDTO) {
        log.debug("Request to partially update Artist : {}", artistDTO);

        return artistRepository
            .findById(artistDTO.getId())
            .map(existingArtist -> {
                artistMapper.partialUpdate(existingArtist, artistDTO);

                return existingArtist;
            })
            .map(artistRepository::save)
            .map(artistMapper::toDto);
    }

    /**
     * Get all the artists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArtistDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Artists");
        return artistRepository.findAll(pageable).map(artistMapper::toDto);
    }

    /**
     * Get one artist by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArtistDTO> findOne(Long id) {
        log.debug("Request to get Artist : {}", id);
        return artistRepository.findById(id).map(artistMapper::toDto);
    }

    /**
     * Delete the artist by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Artist : {}", id);
        artistRepository.deleteById(id);
    }
}
