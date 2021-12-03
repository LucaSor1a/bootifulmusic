package ar.edu.um.isa.bootifulmusic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Track.
 */
@Entity
@Table(name = "track")
public class Track implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "released")
    private LocalDate released;

    @Column(name = "length")
    private Integer length;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tracks", "artist", "genre" }, allowSetters = true)
    private Album album;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Track id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Track name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleased() {
        return this.released;
    }

    public Track released(LocalDate released) {
        this.setReleased(released);
        return this;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    public Integer getLength() {
        return this.length;
    }

    public Track length(Integer length) {
        this.setLength(length);
        return this;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Album getAlbum() {
        return this.album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Track album(Album album) {
        this.setAlbum(album);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Track)) {
            return false;
        }
        return id != null && id.equals(((Track) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Track{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", released='" + getReleased() + "'" +
            ", length=" + getLength() +
            "}";
    }
}
