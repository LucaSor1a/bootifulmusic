package ar.edu.um.isa.bootifulmusic.service.mapper;

import ar.edu.um.isa.bootifulmusic.domain.Genre;
import ar.edu.um.isa.bootifulmusic.service.dto.GenreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Genre} and its DTO {@link GenreDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GenreMapper extends EntityMapper<GenreDTO, Genre> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    GenreDTO toDtoName(Genre genre);
}
