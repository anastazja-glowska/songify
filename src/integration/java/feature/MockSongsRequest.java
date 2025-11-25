package feature;

public interface MockSongsRequest {

    default String retrieveSongWithDurationZero(){
        return """
                                {
                                  "name": "Song Title",
                                  "releaseDate": "2025-03-25T13:55:20.850Z",
                                  "duration": 0,
                                  "language": "ENGLISH" 
                                }
                                """.trim();
    }

    default String retrieveSongLoseYourself(){
        return """
                                {
                                  "name": "Lose YourSelf",
                                  "releaseDate": "2025-01-25T13:55:20.850Z",
                                  "duration": 5,
                                  "language": "ENGLISH" 
                                }
                                """.trim();
    }
    default String retrieveGenreRap(){
        return """
                                {
                                  "name": "Rap"
                                }
                                """.trim();
    }

    default String retrieveEminemAlbum(){
        return """
                        {
                          "songIds": [
                            1
                          ],
                          "title": "EminemAlbum1",
                          "releaseDate": "2025-11-03T14:28:07.043Z"
                        }
                        """.trim();
    }

    default String retrieveEminemArtist(){
        return """
                        {
                          "name": "Eminem"
                        }
                        """.trim();
    }
}
