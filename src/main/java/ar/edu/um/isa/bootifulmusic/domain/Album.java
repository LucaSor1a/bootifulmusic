package ar.edu.um.isa.bootifulmusic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Album.
 */
@Entity
@Table(name = "album")
public class Album implements Serializable {

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

    @OneToMany(mappedBy = "album")
    @JsonIgnoreProperties(value = { "album" }, allowSetters = true)
    private Set<Track> tracks = new HashSet<>();

    @ManyToOne
    private Artist artist;

    @ManyToOne
    private Genre genre;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Album id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Album name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleased() {
        return this.released;
    }

    public Album released(LocalDate released) {
        this.setReleased(released);
        return this;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    public Set<Track> getTracks() {
        return this.tracks;
    }

    public void setTracks(Set<Track> tracks) {
        if (this.tracks != null) {
            this.tracks.forEach(i -> i.setAlbum(null));
        }
        if (tracks != null) {
            tracks.forEach(i -> i.setAlbum(this));
        }
        this.tracks = tracks;
    }

    public Album tracks(Set<Track> tracks) {
        this.setTracks(tracks);
        return this;
    }

    public Album addTrack(Track track) {
        this.tracks.add(track);
        track.setAlbum(this);
        return this;
    }

    public Album removeTrack(Track track) {
        this.tracks.remove(track);
        track.setAlbum(null);
        return this;
    }

    public Artist getArtist() {
        return this.artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album artist(Artist artist) {
        this.setArtist(artist);
        return this;
    }

    public Genre getGenre() {
        return this.genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Album genre(Genre genre) {
        this.setGenre(genre);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Album)) {
            return false;
        }
        return id != null && id.equals(((Album) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Album{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", released='" + getReleased() + "'" +
            "}";
    }
}
