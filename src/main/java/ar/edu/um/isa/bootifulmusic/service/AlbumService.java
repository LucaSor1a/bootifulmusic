package ar.edu.um.isa.bootifulmusic.service;

import ar.edu.um.isa.bootifulmusic.domain.Album;
import ar.edu.um.isa.bootifulmusic.domain.Genre;
import ar.edu.um.isa.bootifulmusic.repository.AlbumRepository;
import ar.edu.um.isa.bootifulmusic.repository.GenreRepository;
import ar.edu.um.isa.bootifulmusic.service.dto.AlbumDTO;
import ar.edu.um.isa.bootifulmusic.service.dto.ArtistDTO;
import ar.edu.um.isa.bootifulmusic.service.mapper.AlbumMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Album}.
 */
@Service
@Transactional
public class AlbumService {

    private final Logger log = LoggerFactory.getLogger(AlbumService.class);

    private final AlbumRepository albumRepository;

    private final AlbumMapper albumMapper;

    @Autowired
    GenreRepository genreRepository;

    public AlbumService(AlbumRepository albumRepository, AlbumMapper albumMapper) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
    }

    /**
     * Save a album.
     *
     * @param albumDTO the entity to save.
     * @return the persisted entity.
     */
    public AlbumDTO save(AlbumDTO albumDTO) {
        log.debug("Request to save Album : {}", albumDTO);
        Album album = albumMapper.toEntity(albumDTO);
        album = albumRepository.save(album);
        return albumMapper.toDto(album);
    }

    /**
     * Partially update a album.
     *
     * @param albumDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AlbumDTO> partialUpdate(AlbumDTO albumDTO) {
        log.debug("Request to partially update Album : {}", albumDTO);

        return albumRepository
            .findById(albumDTO.getId())
            .map(existingAlbum -> {
                albumMapper.partialUpdate(existingAlbum, albumDTO);

                return existingAlbum;
            })
            .map(albumRepository::save)
            .map(albumMapper::toDto);
    }

    /**
     * Get all the albums.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AlbumDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Albums");
        return albumRepository.findAll(pageable).map(albumMapper::toDto);
    }

    /**
     * Get one album by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AlbumDTO> findOne(Long id) {
        log.debug("Request to get Album : {}", id);
        return albumRepository.findById(id).map(albumMapper::toDto);
    }

    /**
     * Delete the album by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Album : {}", id);
        albumRepository.deleteById(id);
    }

    /**
     * Get all albums by genre.
     *
     * @param pageable the pagination information.
     * @param id of the genre.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AlbumDTO> findByGenre(Pageable pageable, Long id) {
        log.debug("Request to get all Artists of Genre : {}", id);
        Genre genre = genreRepository.getById(id);
        return albumRepository.findByGenre(pageable, genre).map(albumMapper::toDto);
    }
}
