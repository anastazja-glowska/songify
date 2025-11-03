package com.songify.domain.crud;

import com.songify.util.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
class Album extends BaseEntity {

    @Id
    @GeneratedValue(generator = "album_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "album_id_seq",
            sequenceName = "album_id_seq",
            allocationSize = 1)
    private Long id;


    private String title;

    private Instant releaseDate;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "album_id")
    private Set<Song> songs = new HashSet<>();

    @ManyToMany(mappedBy = "albums")
    private Set<Artist> artists = new HashSet<>();

    void addSongToAlbum(Song song) {
        songs.add(song);
    }

    void removeArtist(Artist artist) {
        artists.remove(artist);
        artist.removeAlbum(this);
    }

    Artist findArtistById(Long artistId) {
        return artists.stream()
                .filter(artist -> artist.getId().equals(artistId))
                .findFirst()
                .orElseThrow(() -> new ArtistNotFoundException("Artist with id " + artistId + " not found in album with id " + this.id));
    }


    void addArtist(Artist artist) {
        artists.add(artist);

    }

    void addSongsToAlbum(Set<Song> songs1) {
        songs.addAll(songs1);

    }

    public Set<Long> getSongIds() {
        return songs.stream().map(song -> song.getId()).collect(Collectors.toSet());
    }
}
