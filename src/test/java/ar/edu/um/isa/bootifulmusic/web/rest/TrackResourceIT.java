package ar.edu.um.isa.bootifulmusic.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.um.isa.bootifulmusic.IntegrationTest;
import ar.edu.um.isa.bootifulmusic.domain.Track;
import ar.edu.um.isa.bootifulmusic.repository.TrackRepository;
import ar.edu.um.isa.bootifulmusic.service.dto.TrackDTO;
import ar.edu.um.isa.bootifulmusic.service.mapper.TrackMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TrackResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrackResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RELEASED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RELEASED = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_LENGTH = 1;
    private static final Integer UPDATED_LENGTH = 2;

    private static final String ENTITY_API_URL = "/api/tracks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private TrackMapper trackMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrackMockMvc;

    private Track track;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Track createEntity(EntityManager em) {
        Track track = new Track().name(DEFAULT_NAME).released(DEFAULT_RELEASED).length(DEFAULT_LENGTH);
        return track;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Track createUpdatedEntity(EntityManager em) {
        Track track = new Track().name(UPDATED_NAME).released(UPDATED_RELEASED).length(UPDATED_LENGTH);
        return track;
    }

    @BeforeEach
    public void initTest() {
        track = createEntity(em);
    }

    @Test
    @Transactional
    void createTrack() throws Exception {
        int databaseSizeBeforeCreate = trackRepository.findAll().size();
        // Create the Track
        TrackDTO trackDTO = trackMapper.toDto(track);
        restTrackMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trackDTO)))
            .andExpect(status().isCreated());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeCreate + 1);
        Track testTrack = trackList.get(trackList.size() - 1);
        assertThat(testTrack.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrack.getReleased()).isEqualTo(DEFAULT_RELEASED);
        assertThat(testTrack.getLength()).isEqualTo(DEFAULT_LENGTH);
    }

    @Test
    @Transactional
    void createTrackWithExistingId() throws Exception {
        // Create the Track with an existing ID
        track.setId(1L);
        TrackDTO trackDTO = trackMapper.toDto(track);

        int databaseSizeBeforeCreate = trackRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrackMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = trackRepository.findAll().size();
        // set the field null
        track.setName(null);

        // Create the Track, which fails.
        TrackDTO trackDTO = trackMapper.toDto(track);

        restTrackMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trackDTO)))
            .andExpect(status().isBadRequest());

        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTracks() throws Exception {
        // Initialize the database
        trackRepository.saveAndFlush(track);

        // Get all the trackList
        restTrackMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(track.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].released").value(hasItem(DEFAULT_RELEASED.toString())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)));
    }

    @Test
    @Transactional
    void getTrack() throws Exception {
        // Initialize the database
        trackRepository.saveAndFlush(track);

        // Get the track
        restTrackMockMvc
            .perform(get(ENTITY_API_URL_ID, track.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(track.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.released").value(DEFAULT_RELEASED.toString()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH));
    }

    @Test
    @Transactional
    void getNonExistingTrack() throws Exception {
        // Get the track
        restTrackMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTrack() throws Exception {
        // Initialize the database
        trackRepository.saveAndFlush(track);

        int databaseSizeBeforeUpdate = trackRepository.findAll().size();

        // Update the track
        Track updatedTrack = trackRepository.findById(track.getId()).get();
        // Disconnect from session so that the updates on updatedTrack are not directly saved in db
        em.detach(updatedTrack);
        updatedTrack.name(UPDATED_NAME).released(UPDATED_RELEASED).length(UPDATED_LENGTH);
        TrackDTO trackDTO = trackMapper.toDto(updatedTrack);

        restTrackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trackDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trackDTO))
            )
            .andExpect(status().isOk());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeUpdate);
        Track testTrack = trackList.get(trackList.size() - 1);
        assertThat(testTrack.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrack.getReleased()).isEqualTo(UPDATED_RELEASED);
        assertThat(testTrack.getLength()).isEqualTo(UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void putNonExistingTrack() throws Exception {
        int databaseSizeBeforeUpdate = trackRepository.findAll().size();
        track.setId(count.incrementAndGet());

        // Create the Track
        TrackDTO trackDTO = trackMapper.toDto(track);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trackDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trackDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrack() throws Exception {
        int databaseSizeBeforeUpdate = trackRepository.findAll().size();
        track.setId(count.incrementAndGet());

        // Create the Track
        TrackDTO trackDTO = trackMapper.toDto(track);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trackDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrack() throws Exception {
        int databaseSizeBeforeUpdate = trackRepository.findAll().size();
        track.setId(count.incrementAndGet());

        // Create the Track
        TrackDTO trackDTO = trackMapper.toDto(track);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrackMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trackDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrackWithPatch() throws Exception {
        // Initialize the database
        trackRepository.saveAndFlush(track);

        int databaseSizeBeforeUpdate = trackRepository.findAll().size();

        // Update the track using partial update
        Track partialUpdatedTrack = new Track();
        partialUpdatedTrack.setId(track.getId());

        partialUpdatedTrack.name(UPDATED_NAME).length(UPDATED_LENGTH);

        restTrackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrack.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrack))
            )
            .andExpect(status().isOk());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeUpdate);
        Track testTrack = trackList.get(trackList.size() - 1);
        assertThat(testTrack.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrack.getReleased()).isEqualTo(DEFAULT_RELEASED);
        assertThat(testTrack.getLength()).isEqualTo(UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void fullUpdateTrackWithPatch() throws Exception {
        // Initialize the database
        trackRepository.saveAndFlush(track);

        int databaseSizeBeforeUpdate = trackRepository.findAll().size();

        // Update the track using partial update
        Track partialUpdatedTrack = new Track();
        partialUpdatedTrack.setId(track.getId());

        partialUpdatedTrack.name(UPDATED_NAME).released(UPDATED_RELEASED).length(UPDATED_LENGTH);

        restTrackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrack.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrack))
            )
            .andExpect(status().isOk());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeUpdate);
        Track testTrack = trackList.get(trackList.size() - 1);
        assertThat(testTrack.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrack.getReleased()).isEqualTo(UPDATED_RELEASED);
        assertThat(testTrack.getLength()).isEqualTo(UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void patchNonExistingTrack() throws Exception {
        int databaseSizeBeforeUpdate = trackRepository.findAll().size();
        track.setId(count.incrementAndGet());

        // Create the Track
        TrackDTO trackDTO = trackMapper.toDto(track);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trackDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trackDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrack() throws Exception {
        int databaseSizeBeforeUpdate = trackRepository.findAll().size();
        track.setId(count.incrementAndGet());

        // Create the Track
        TrackDTO trackDTO = trackMapper.toDto(track);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trackDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrack() throws Exception {
        int databaseSizeBeforeUpdate = trackRepository.findAll().size();
        track.setId(count.incrementAndGet());

        // Create the Track
        TrackDTO trackDTO = trackMapper.toDto(track);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrackMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(trackDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrack() throws Exception {
        // Initialize the database
        trackRepository.saveAndFlush(track);

        int databaseSizeBeforeDelete = trackRepository.findAll().size();

        // Delete the track
        restTrackMockMvc
            .perform(delete(ENTITY_API_URL_ID, track.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
