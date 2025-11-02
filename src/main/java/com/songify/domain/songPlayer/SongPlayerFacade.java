package com.songify.domain.songPlayer;

import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.SongDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SongPlayerFacade {

    private final SongifyCrudFasade songifyCrudFasade;
    private final YouTubeHttpClient youTubeHttpClient;



    public String playSongWithId(Long songId) {
        SongDto song = songifyCrudFasade.findSongDtoById(songId);
        String result = youTubeHttpClient.playSongByName(song.name());
        if(result.equals("success")){
            return result;
        }else {
            throw new RuntimeException("some error - result failed");
        }


    }
}
