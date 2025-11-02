package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;


import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.AlbumWithArtistsAndSongsDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.SongRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.songify.domain.crud.dto.SongDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class SongifyCrudFasade {

    private final SongAdder songAdder;
    private final SongRetrierver songRetrierver;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;
    private final ArtistAdder artistAdder;
    private final GenreAdder genreAdder;
    private final AlbumAdder albumAdder;
    private final ArtistRetriever artistRetrierver;
    private final AlbumRetriever albumRetriever;
    private final ArtistDeleter artistDeleter;
    private final ArtistAssigner artistAssigner;
    private final ArtistUpdater artistUpdater;


    public ArtistDto addArtist(ArtistRequestDto dto){
        return artistAdder.addArtist(dto.name());
    }

    public GenreDto addGenre(GenreRequestDto dto){
        return genreAdder.addGenre(dto.name());
    }

    public AlbumDto addAlbumWithSong(AlbumRequestDto dto){
        return albumAdder.addAlbum(dto.songId(), dto.title(), dto.releaseDate());
    }

    public SongDto addSong(SongRequestDto songDto) {
         return songAdder.addSong(songDto);

    }

    public void addArtistToAlbum(Long artistId, Long albumId){
        artistAssigner.addArtistToAlbum(artistId, albumId);
    }

    public ArtistDto addArtistWithDefaultAlbumAndSong(ArtistRequestDto dto){
        return artistAdder.addArtistWithDefaultAlbumAndSong(dto);
    }

    public Set<ArtistDto> findAllArtists(Pageable pageable){
        return artistRetrierver.findAllArtists(pageable);
    }

    public List<SongDto> findAllSongs(Pageable pageable) {
        return songRetrierver.findAll(pageable);
    }

    public AlbumWithArtistsAndSongsDto findAlbumByIdWithArtistsAndSongs(Long id){
        return albumRetriever.findAlbumByIdWithArtistsAndSongs(id);
    }
    public void updateSongById(Long id, SongDto songDto) {
        songRetrierver.findSongById(id);
        Song song = new Song(songDto.name());

        songUpdater.updateById(id, song);
    }

    public ArtistDto updateArtistNameById(Long id, String name) {
        return artistUpdater.updateArtistNameById(id, name);
    }

    public SongDto updateSongPartiallyById(Long id, SongDto songFromRequest) {
        songRetrierver.findSongById(id);

        Song songFromDataBase = songRetrierver.findSongById(id);
        Song toSave = new Song();

        if(songFromRequest.name() != null) {
            toSave.setName(songFromRequest.name());
        } else {
            toSave.setName(songFromDataBase.getName());
        }

        songUpdater.updateById(id, toSave);

        return SongDto.builder()
                .id(toSave.getId())
                .name(toSave.getName())
                .build();
    }



    public void deleteSongById(Long id) {
        songRetrierver.findSongById(id);
        songDeleter.deleteSongById(id);
    }


    public void deleteSongAndGenreById(Long id){
        songDeleter.deleteSongAndGenreById(id);
    }

    public void deleteArtistByIdWithAlbumsAndSongs(Long artistId){
        artistDeleter.deleteArtistByIdWithAlbumsAndSongs(artistId);
    }

    public SongDto findSongDtoById(Long id) {
        return songRetrierver.findSongDtoById(id);
    }

    public Set<AlbumDto> findAlbumsDtoByArtistId(Long id) {

        return albumRetriever.findAlbumsDtoByArtistId(id);
    }

    Long countArtistsByAlbumId(Long albumId) {
        return albumRetriever.countArtistsByAlbumId(albumId);
    }

    AlbumDto findAlbumById(Long albumId) {
        Album album = albumRetriever.findById(albumId);
        return new AlbumDto(album.getId(), album.getTitle());
    }
}
