package ar.edu.um.isa.bootifulmusic.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.isa.bootifulmusic.domain.Artist;
import ar.edu.um.isa.bootifulmusic.service.dto.ArtistDTO;
import ar.edu.um.isa.bootifulmusic.web.rest.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArtistMapperTest {

    private ArtistMapper artistMapper;

    @BeforeEach
    public void setUp() {
        artistMapper = new ArtistMapperImpl();
    }

    @Test
    public void testArtistMapperCanConvertBackAndForth() throws Exception {
        TestUtil.equalsVerifier(Artist.class);
        Artist artist = new Artist().firstName("Lucas").lastName("Soria").nick("Firebolt");
        ArtistDTO artistDTO = artistMapper.toDto(artist);
        assertThat(artistMapper.toEntity(artistDTO)).usingRecursiveComparison().isEqualTo(artist);
    }

    @Test
    public void testArtistMapperNotEqual() throws Exception {
        TestUtil.equalsVerifier(Artist.class);
        Artist artist1 = new Artist().id(1L).firstName("Lucas").lastName("Soria").nick("Firebolt");
        Artist artist2 = new Artist().id(2L).firstName("Lucas").lastName("Soria").nick("Firebolt");
        ArtistDTO artistDTO1 = artistMapper.toDto(artist1);
        assertThat(artistMapper.toEntity(artistDTO1)).usingRecursiveComparison().isNotEqualTo(artist2);
    }
}
