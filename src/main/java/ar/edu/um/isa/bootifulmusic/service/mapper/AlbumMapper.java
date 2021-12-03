package ar.edu.um.isa.bootifulmusic.service.mapper;

import ar.edu.um.isa.bootifulmusic.domain.Album;
import ar.edu.um.isa.bootifulmusic.service.dto.AlbumDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Album} and its DTO {@link AlbumDTO}.
 */
@Mapper(componentModel = "spring", uses = { ArtistMapper.class, GenreMapper.class })
public interface AlbumMapper extends EntityMapper<AlbumDTO, Album> {
    @Mapping(target = "artist", source = "artist", qualifiedByName = "nick")
    @Mapping(target = "genre", source = "genre", qualifiedByName = "name")
    AlbumDTO toDto(Album s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    AlbumDTO toDtoName(Album album);
}
