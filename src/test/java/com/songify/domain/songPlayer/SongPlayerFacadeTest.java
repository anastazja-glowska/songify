package com.songify.domain.songPlayer;

import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.SongDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SongPlayerFacadeTest {

        SongifyCrudFasade songifyCrudFasade = mock(SongifyCrudFasade.class);
        YouTubeHttpClient youTubeHttpClient = mock(YouTubeHttpClient.class);

        SongPlayerFacade songPlayerFacade = new SongPlayerFacade(
                songifyCrudFasade,
                youTubeHttpClient);




    @Test
    @DisplayName("should return success when played song with id")
        void should_return_success_when_played_song_with_id() {

            // given
        Long songId = 1L;
        SongDto mockSong = SongDto.builder()
                .id(songId)
                .name("Test Song")
                .build();

        when(songifyCrudFasade.findSongDtoById(songId)).thenReturn(mockSong);
        when(youTubeHttpClient.playSongByName(any())).thenReturn("success");
            // when
        String result = songPlayerFacade.playSongWithId(1L);
        // then
        assertThat(result).isEqualTo("success");


        }

    @Test
    void should_return_exception_when_something_was_wrong() {

        // given
        Long songId = 1L;
        SongDto mockSong = SongDto.builder()
                .id(songId)
                .name("Test Song")
                .build();

        when(songifyCrudFasade.findSongDtoById(songId)).thenReturn(mockSong);
        when(youTubeHttpClient.playSongByName(any())).thenReturn("error");
        // when
        Throwable throwable = catchThrowable(() -> songPlayerFacade.playSongWithId(1L));
        // then
        assertThat(throwable).isInstanceOf(RuntimeException.class);


    }

}