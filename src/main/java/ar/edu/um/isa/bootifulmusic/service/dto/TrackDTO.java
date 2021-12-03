package ar.edu.um.isa.bootifulmusic.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ar.edu.um.isa.bootifulmusic.domain.Track} entity.
 */
public class TrackDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private LocalDate released;

    private Integer length;

    private AlbumDTO album;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public AlbumDTO getAlbum() {
        return album;
    }

    public void setAlbum(AlbumDTO album) {
        this.album = album;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrackDTO)) {
            return false;
        }

        TrackDTO trackDTO = (TrackDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, trackDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrackDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", released='" + getReleased() + "'" +
            ", length=" + getLength() +
            ", album=" + getAlbum() +
            "}";
    }
}
