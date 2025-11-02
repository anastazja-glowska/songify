package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.AlbumWithArtistsAndSongsDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

@Log4j2
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


        AlbumRequestDto albumRequestDto = AlbumRequestDto.builder().songIds(Set.of(songId)).title("album1").build();


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
        Throwable throwable = catchThrowable(() -> songifyCrudFasade.findSongDtoById(songId));
        assertThat(throwable).isInstanceOf(SongNotFoundException.class);


        Throwable throwable1 = catchThrowable(() -> songifyCrudFasade.findAlbumById(albumId));
        assertThat(throwable1).isInstanceOf(AlbumNotFoundEcxeption.class);

        assertThat(songifyCrudFasade.findAllArtists(Pageable.unpaged())).isEmpty();
    }


    @Test
    @DisplayName("should delete only artist from album and songs by id When there was more than one artist in album")
    void should_delete_only_artist_from_album_and_songs_by_id_When_there_was_more_than_one_artist_in_album() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder().name("shawn mendes").build();
        Long artistId = songifyCrudFasade.addArtist(shawnMendes).id();

        ArtistRequestDto ladygaga = ArtistRequestDto.builder().name("lady gaga").build();
        Long artistId2 = songifyCrudFasade.addArtist(ladygaga).id();

        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH).build();

        SongDto songDto = songifyCrudFasade.addSong(songRequestDto);

        Long songId = songDto.id();



        AlbumRequestDto albumRequestDto = AlbumRequestDto.builder().songIds(Set.of(songId)).title("album1").build();



        AlbumDto albumDto = songifyCrudFasade.addAlbumWithSong(albumRequestDto);
        Long albumId = albumDto.id();
        songifyCrudFasade.addArtistToAlbum(artistId, albumId);
        songifyCrudFasade.addArtistToAlbum(artistId2, albumId);


        Long artistNumberInAlbum = songifyCrudFasade.countArtistsByAlbumId(albumId);
        log.info("Number of artists in album before deleting artist: {} ", artistNumberInAlbum);
        assertThat(artistNumberInAlbum).isEqualTo(2);
        
        // when
        songifyCrudFasade.deleteArtistByIdWithAlbumsAndSongs(artistId);


        //then

        AlbumWithArtistsAndSongsDto album = songifyCrudFasade.findAlbumByIdWithArtistsAndSongs(albumId);
        Set<ArtistDto> artists = album.artists();
        assertThat(artists).extracting("id").containsOnly(artistId2);

    }

    @Test
    @DisplayName("should delete artist with all albums and songs by artist id When there was only one artist in album")
    void should_delete_artist_with_all_albums_and_songs_by_artist_id_When_there_was_only_one_artist_in_album(){
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder().name("shawn mendes").build();
        Long artistId = songifyCrudFasade.addArtist(shawnMendes).id();

        SongRequestDto songRequestDto1 = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH).build();

        SongRequestDto songRequestDto2 = SongRequestDto.builder()
                .name("song2")
                .language(SongLanguageDto.ENGLISH).build();

        SongDto songDto1 = songifyCrudFasade.addSong(songRequestDto1);
        SongDto songDto2 = songifyCrudFasade.addSong(songRequestDto2);

        Long songId = songDto1.id();
        Long songId2 = songDto2.id();


        AlbumRequestDto albumRequestDto1 = AlbumRequestDto.builder().songIds(Set.of(songId)).title("album1").build();
        AlbumRequestDto albumRequestDto2 = AlbumRequestDto.builder().songIds(Set.of(songId2)).title("album2").build();



        AlbumDto albumDto = songifyCrudFasade.addAlbumWithSong(albumRequestDto1);
        AlbumDto albumDto2 = songifyCrudFasade.addAlbumWithSong(albumRequestDto2);
        Long albumId = albumDto.id();
        Long albumId2 = albumDto2.id();

        songifyCrudFasade.addSongToAlbum(songDto2, albumId);

        songifyCrudFasade.addArtistToAlbum(artistId, albumId);
        songifyCrudFasade.addArtistToAlbum(artistId, albumId2);

        assertThat(songifyCrudFasade.findAlbumsDtoByArtistId(artistId)).size().isEqualTo(2);


        Long artistNumberInAlbum = songifyCrudFasade.countArtistsByAlbumId(albumId);
        assertThat(artistNumberInAlbum).isEqualTo(1);
        assertThat(songifyCrudFasade.findAllSongs(Pageable.unpaged()).size()).isEqualTo(2);

        // when
        songifyCrudFasade.deleteArtistByIdWithAlbumsAndSongs(artistId);

        //then
        Throwable throwable = catchThrowable(() -> songifyCrudFasade.findSongDtoById(songId));
        assertThat(throwable).isInstanceOf(SongNotFoundException.class);


        Throwable throwable1 = catchThrowable(() -> songifyCrudFasade.findAlbumById(albumId));
        assertThat(throwable1).isInstanceOf(AlbumNotFoundEcxeption.class);

        assertThat(songifyCrudFasade.findAllArtists(Pageable.unpaged())).isEmpty();
    }



    @Test
    @DisplayName("should add album with song")
    void should_add_album_with_song() {

        // given
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH).build();

        SongDto songDto = songifyCrudFasade.addSong(songRequestDto);

        Long songId = songDto.id();


        AlbumRequestDto albumRequestDto = AlbumRequestDto.builder().songIds(Set.of(songId)).title("album1").build();
        assertThat(songifyCrudFasade.findAllAlbums()).isEmpty();

        //when
        AlbumDto albumDto = songifyCrudFasade.addAlbumWithSong(albumRequestDto);

        //then
        assertThat(songifyCrudFasade.findAllAlbums()).hasSize(1);
        assertThat(albumDto.id()).isEqualTo(0L);
        assertThat(albumDto.name()).isEqualTo("album1");
    }

    @Test
    @DisplayName("should add song")
    void should_add_song() {


        // given
        SongRequestDto song1 = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH).build();

        assertThat(songifyCrudFasade.findAllSongs(Pageable.unpaged()).isEmpty());

        //when


        SongDto songDto = songifyCrudFasade.addSong(song1);

        //then
        List<SongDto> allSongs = songifyCrudFasade.findAllSongs(Pageable.unpaged());

        assertThat(allSongs).extracting(songDto1 -> songDto1.id()).containsExactly(0L);
        assertThat(songDto.id()).isEqualTo(0L);
        assertThat(songDto.name()).isEqualTo("song1");

    }

    @Test
    @DisplayName("should add artist to album")
    void should_add_artist_to_album() {

        //given

        ArtistRequestDto shawnMendes = ArtistRequestDto.builder().name("shawn mendes").build();
        Long artistId = songifyCrudFasade.addArtist(shawnMendes).id();


        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH).build();

        SongDto songDto = songifyCrudFasade.addSong(songRequestDto);
        Long songId = songDto.id();

        AlbumRequestDto albumRequestDto = AlbumRequestDto.builder().songIds(Set.of(songId)).title("album1").build();


        AlbumDto albumDto = songifyCrudFasade.addAlbumWithSong(albumRequestDto);
        Long albumId = albumDto.id();

        //when
        songifyCrudFasade.addArtistToAlbum(artistId, albumId);


        //then

        AlbumWithArtistsAndSongsDto albumByIdWithArtistsAndSongs = songifyCrudFasade.findAlbumByIdWithArtistsAndSongs(albumId);
        Set<AlbumDto> albumsDtoByArtistId = songifyCrudFasade.findAlbumsDtoByArtistId(artistId);
        assertThat(albumsDtoByArtistId).hasSize(1);
        assertThat(albumsDtoByArtistId).extracting(a -> a.id()).containsExactly(albumId);
        assertThat(albumByIdWithArtistsAndSongs.artists()).hasSize(1);

    }

    @Test
    @DisplayName("should find album by id")
    void should_find_album_by_id(){

        // given

        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH).build();

        SongDto songDto = songifyCrudFasade.addSong(songRequestDto);
        Long songId = songDto.id();

        AlbumRequestDto albumRequestDto = AlbumRequestDto.builder().songIds(Set.of(songId)).title("album1").build();

        AlbumDto albumDto = songifyCrudFasade.addAlbumWithSong(albumRequestDto);
        Long albumId = albumDto.id();
        // when
        AlbumDto foundAlbum = songifyCrudFasade.findAlbumById(albumId);
        // then
        assertThat(foundAlbum).isNotNull();
        assertThat(foundAlbum.id()).isEqualTo(albumId);
        assertThat(foundAlbum.name()).isEqualTo("album1");


    }

    @Test
    @DisplayName("should throw exception when album not found")
    void should_throw_exception_when_album_not_found(){

        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFasade.findAlbumById(1L));
        // then
        assertThat(throwable).isInstanceOf(AlbumNotFoundEcxeption.class);
        assertThat(throwable.getMessage()).isEqualTo("Album with id " + 1L + " not found");

    }


    @Test
    @DisplayName("should throw exception when song not found")
    void should_throw_exception_when_song_not_found(){
        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFasade.findSongDtoById(1L));
        // then
        assertThat(throwable).isInstanceOf(SongNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Song not found");
    }




}