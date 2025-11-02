package com.songify.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SongInsertGenerator {
    public static void main(String[] args){

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("song_inserts.sql"));


            Random random = new Random();
            Date date = simpleDateFormat.parse("2024-01-01 00:00:00");
            for(int i = 0; i < 10000;i++){
                String releaseDate = simpleDateFormat.format(new Date(date.getTime() + i * 86400000L));
                String songName = "Song " + (random.nextInt(10000) + 1);
                int duration = 150 + random.nextInt(151);
                int albumId = random.nextInt(10) + 1;
                int genreId = random.nextInt(20) + 1;

                String sql =  String.format("INSERT INTO song (name, duration, release_date, song_language, album_id, genre_id) " +
                                "VALUES ('%s', %d, '%s', 'ENGLISH', %d, %d);%n",
                        songName, duration, releaseDate, albumId, genreId);

                bufferedWriter.write(sql);
            }

            bufferedWriter.close();
            System.out.println("SQL statements generated successfully in insert_songs.sql");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


    }
}
