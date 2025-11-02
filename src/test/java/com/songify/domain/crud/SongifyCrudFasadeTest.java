package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.AlbumWithArtistsAndSongsDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

class SongifyCrudFasadeTest {


    SongifyCrudFasade songifyCrudFasade = SongifyCrudFacadeConfiguration.createSongifyCrud(
            new InMemorySongRepository(),
            new InMemoryGenreRepository(),
            new InMemoryArtistRepository(),
            new InMemoryAlbumRepository()
    );


    @Test
    @DisplayName("should add Artist when artist is sent")
    void should_add_Artist_when_artist_is_sent() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder().name("shawn mendes").build();
        Set<ArtistDto> allArtists = songifyCrudFasade.findAllArtists(Pageable.unpaged());
        assertTrue(allArtists.isEmpty());


        // when
        ArtistDto response = songifyCrudFasade.addArtist(shawnMendes);

        int size = songifyCrudFasade.findAllArtists(Pageable.unpaged()).size();

        // then
        assertThat(response.id()).isEqualTo(0L);
        assertThat(response.name()).isEqualTo("shawn mendes");
        assertThat(size).isEqualTo(1);


    }

    @Test
    @DisplayName("should throw exception Artist not found when artist does not exist")
    void should_throw_exception_artist_not_found_when_artist_does_not_exist() {

        // given
        assertThat(songifyCrudFasade.findAllArtists(Pageable.unpaged())).isEmpty();

        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFasade.deleteArtistByIdWithAlbumsAndSongs(1L));

        // then

        assertThat(throwable).isInstanceOf(ArtistNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Artist with id " + 1L + " not found");
    }


    @Test
    @DisplayName("should not throw exception Artist not found when artist exist")
    void should_not_throw_exception_artist_not_found_when_artist_exist() {

        // given

        ArtistRequestDto shawnMendes = ArtistRequestDto.builder().name("shawn mendes").build();
        ArtistDto response = songifyCrudFasade.addArtist(shawnMendes);

        Set<ArtistDto> allArtists = songifyCrudFasade.findAllArtists(Pageable.unpaged());
        assertThat(allArtists).isNotEmpty();

        //when

        Throwable throwable = catchThrowable(() -> songifyCrudFasade.deleteArtistByIdWithAlbumsAndSongs(response.id()));


        // then
        assertThat(throwable).isNull();


    }

    @Test
    @DisplayName("should delete artist by id When he have no albums")
    void should_delete_artist_by_id_When_he_have_no_albums() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder().name("shawn mendes").build();
        ArtistDto response = songifyCrudFasade.addArtist(shawnMendes);


        // when

        Set<AlbumDto> albumsDtoByArtistId = songifyCrudFasade.findAlbumsDtoByArtistId(response.id());
        assertThat(albumsDtoByArtistId).isEmpty();
        songifyCrudFasade.deleteArtistByIdWithAlbumsAndSongs(response.id());

        //then

        assertThat(songifyCrudFasade.findAllArtists(Pageable.unpaged())).isEmpty();
    }

    @Test
    @DisplayName("should delete artist with album and songs by id When he have one album and he was only one artist")
    void should_delete_artist_with_album_and_songs_by_id_When_he_have_one_album_and_he_was_only_one_artist() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder().name("shawn mendes").build();
        Long artistId = songifyCrudFasade.addArtist(shawnMendes).id();

        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH).build();

        SongDto songDto = songifyCrudFasade.addSong(songRequestDto);

        Long songId = songDto.id();


        AlbumRequestDto albumRequestDto = AlbumRequestDto.builder().songId(songId).title("album1").build();


        AlbumDto albumDto = songifyCrudFasade.addAlbumWithSong(albumRequestDto);
        Long albumId = albumDto.id();
        songifyCrudFasade.addArtistToAlbum(artistId, albumId);

        assertThat(songifyCrudFasade.findAlbumsDtoByArtistId(artistId)).size().isEqualTo(1);
        AlbumWithArtistsAndSongsDto albumByIdWithArtistsAndSongs = songifyCrudFasade.findAlbumByIdWithArtistsAndSongs(albumId);
        assertThat(albumByIdWithArtistsAndSongs.artists()).hasSize(1);

        Long artistNumberInAlbum = songifyCrudFasade.countArtistsByAlbumId(albumId);
        assertThat(artistNumberInAlbum).isEqualTo(1);

        // when
        songifyCrudFasade.deleteArtistByIdWithAlbumsAndSongs(artistId);

        //then
        songifyCrudFasade.findSongDtoById(songId);
        songifyCrudFasade.findAlbumById(albumId);
        assertThat(songifyCrudFasade.findAllArtists(Pageable.unpaged())).isEmpty();
    }


    @Test
    @DisplayName("should delete artist from album and songs by id When there was more than one artist in album")
    void should_delete_artist_from_album_and_songs_by_id_When_there_was_more_than_one_artist_in_album() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder().name("shawn mendes").build();
        Long id = songifyCrudFasade.addArtist(shawnMendes).id();

        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH).build();

        SongDto songDto = songifyCrudFasade.addSong(songRequestDto);

        Long songId = songDto.id();


        AlbumRequestDto albumRequestDto = AlbumRequestDto.builder().songId(songId).title("album1").build();


        AlbumDto albumDto = songifyCrudFasade.addAlbumWithSong(albumRequestDto);
        songifyCrudFasade.addArtistToAlbum(id, albumDto.id());
        assertThat(songifyCrudFasade.findAlbumsDtoByArtistId(id)).size().isEqualTo(1);

        // when
        songifyCrudFasade.deleteArtistByIdWithAlbumsAndSongs(id);

        //then

        assertThat(songifyCrudFasade.findAllArtists(Pageable.unpaged())).isEmpty();
    }

    @Test
    void should_add_album_with_song() {

    }

    @Test
    void should_add_song() {


        // given
        SongRequestDto song1 = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH).build();

        //when


        SongDto songDto = songifyCrudFasade.addSong(song1);

        //then

    }

    @Test
    void should_add_artist_to_album() {

    }


}