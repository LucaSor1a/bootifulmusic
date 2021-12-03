package ar.edu.um.isa.bootifulmusic.service.mapper;

import ar.edu.um.isa.bootifulmusic.domain.Track;
import ar.edu.um.isa.bootifulmusic.service.dto.TrackDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Track} and its DTO {@link TrackDTO}.
 */
@Mapper(componentModel = "spring", uses = { AlbumMapper.class })
public interface TrackMapper extends EntityMapper<TrackDTO, Track> {
    @Mapping(target = "album", source = "album", qualifiedByName = "name")
    TrackDTO toDto(Track s);
}
