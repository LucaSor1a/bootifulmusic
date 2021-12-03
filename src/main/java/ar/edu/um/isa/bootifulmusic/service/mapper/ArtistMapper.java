package ar.edu.um.isa.bootifulmusic.service.mapper;

import ar.edu.um.isa.bootifulmusic.domain.Artist;
import ar.edu.um.isa.bootifulmusic.service.dto.ArtistDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Artist} and its DTO {@link ArtistDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ArtistMapper extends EntityMapper<ArtistDTO, Artist> {
    @Named("nick")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nick", source = "nick")
    ArtistDTO toDtoNick(Artist artist);
}
