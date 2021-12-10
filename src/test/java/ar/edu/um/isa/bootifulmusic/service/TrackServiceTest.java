package ar.edu.um.isa.bootifulmusic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import ar.edu.um.isa.bootifulmusic.domain.Album;
import ar.edu.um.isa.bootifulmusic.domain.Track;
import ar.edu.um.isa.bootifulmusic.repository.TrackRepository;
import ar.edu.um.isa.bootifulmusic.repository.UserRepository;
import ar.edu.um.isa.bootifulmusic.service.dto.TrackDTO;
import ar.edu.um.isa.bootifulmusic.service.mapper.TrackMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@ExtendWith(MockitoExtension.class)
public class TrackServiceTest {

    @Mock
    TrackRepository trackRepository;

    @Mock
    UserRepository userRepository;

    @Autowired
    TrackService trackService;

    @Autowired
    TrackMapper trackMapper;

    @Test
    public void testTracksByAlbum() {
        List<Track> tracks = new ArrayList<>();
        List<TrackDTO> tracksDTO = new ArrayList<>();
        //Page<Track> pagedResponse = new PageImpl(tracks);
        //Page<TrackDTO> pagedResponseDTO = new PageImpl(tracksDTO);
        Track track = new Track();
        tracks.add(track);
        //when(userRepository.count()).thenReturn(2L);
        Assertions.assertNotEquals(tracksDTO, tracks.stream().map(trackMapper::toDto).collect(Collectors.toList()));
    }
}
