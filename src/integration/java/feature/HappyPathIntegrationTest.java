package feature;


import com.songify.SongifyApplication;
import com.songify.infrastructure.security.jwt.JwtAuthConverter;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SongifyApplication.class)
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("integration")
@Log4j2
class HappyPathIntegrationTest implements MockSongsRequest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14-alpine");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtAuthConverter jwtAuthConverter;


    @DynamicPropertySource
    public static void setContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        postgreSQLContainer.getJdbcUrl();
    }



    @Test
    void happy_path() throws Exception {


//        1. when I go to /songs without jwt then I can see no songs

        ResultActions perform = mockMvc.perform(get("/songs")
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", empty()));


        // SECURITY STEP when i go to /songs with jwt then I can see no songs

        ResultActions perform2 = mockMvc.perform(get("/songs")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON));

        perform2.andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", empty()));


        // SECURITY STEP  when I post to /songs without jwt with Song "Song Title" then 401 is returned

        ResultActions resultActions2 = mockMvc.perform(post("/songs"));



        resultActions2
                .andExpect(status().isUnauthorized());


        // SECURITY STEP  when I post to /songs with User role with Song "Song Title" then 403 is returned


        ResultActions resultActions3 = mockMvc.perform(post("/songs")
                .with(authentication(createJwtWithUserRole())));

        resultActions3.andExpect(status().isForbidden());


//        2. when I post to /songs with Song "Song Title" then Song "Song Title" is returned with id 1


        ResultActions resultActions = mockMvc.perform(post("/songs")
                .with(authentication(createJwtWithAdminRole()))
                .content(
                        retrieveSongWithDurationZero()
                ).contentType(MediaType.APPLICATION_JSON));


        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        log.info("contentAsString: {}", contentAsString);


        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.id", is(1)))
                .andExpect(jsonPath("$.song.name", is("Song Title")))
                .andExpect(jsonPath("$.song.genre.id", is(1)))
                .andExpect(jsonPath("$.song.genre.name", is("default")));


//        3. when I post to /songs with Song "Lose Yourself" then Song "Lose Yourself" is returned with id 2

        ResultActions result = mockMvc.perform(post("/songs")
                .with(authentication(createJwtWithAdminRole()))
                .content(
                        retrieveSongLoseYourself()
                ).contentType(MediaType.APPLICATION_JSON));

        String contentAsString2 = result.andReturn().getResponse().getContentAsString();
        log.info("contentAsString song Lose YourSelf: {}", contentAsString2);

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.song.id", is(2)));


//        4. when I go to /genres then I can see default genre with Id 1


        mockMvc.perform(get("/genres").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genres[0].id", is(1)))
                .andExpect(jsonPath("$.genres[0].name", is("default")));


        //  5. when I post to /genres with Genre "Rap" then Genre "Rap" is returned with id 2

        ResultActions genre = mockMvc.perform(post("/genres")
                .with(authentication(createJwtWithAdminRole()))
                .content(
                        retrieveGenreRap()
                ).contentType(MediaType.APPLICATION_JSON));


        String genreString = genre.andReturn().getResponse().getContentAsString();
        log.info("genreString: {}", genreString);

        genre.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Rap")));


        //  6. when I go to /songs/1 then I can see default genre with id 1 and name default

        ResultActions songsOne = mockMvc.perform(get("/songs/1")
                .contentType(MediaType.APPLICATION_JSON));

        songsOne.andExpect(status().isOk())
                .andExpect(jsonPath("$.song.id", is(1)))
                .andExpect(jsonPath("$.song.genre.id", is(1)))
                .andExpect(jsonPath("$.song.genre.name", is("default")));


        //  7. when I put to /songs/1/genres/2 then Genre with id 2 ("Rap") is added to Song with id 1 ("Til i collapse")


        ResultActions putSongWithGenre = mockMvc.perform(put("/songs/1/genres/2")
                        .with(authentication(createJwtWithAdminRole()))
                .contentType(MediaType.APPLICATION_JSON));


        putSongWithGenre.andExpect(status().isOk())
                .andExpect(jsonPath("$", is("updated")));


        //  8. when I go to /songs/1 then I can see "Rap" genre
        ResultActions getSongWithGenre = mockMvc.perform(get("/songs/1")
                .contentType(MediaType.APPLICATION_JSON));

        getSongWithGenre.andExpect(status().isOk())
                .andExpect(jsonPath("$.song.id", is(1)))
                .andExpect(jsonPath("$.song.genre.id", is(2)))
                .andExpect(jsonPath("$.song.genre.name", is("Rap")));


        //  9. when I go to /albums then I can see no albums


        ResultActions getAlbums = mockMvc.perform(get("/albums")
                .contentType(MediaType.APPLICATION_JSON));

        getAlbums.andExpect(status().isOk())
                .andExpect(jsonPath("$.albums", empty()));


//        10. when I post to /albums with Album "EminemAlbum1" and Song with id 1 then Album "EminemAlbum1" is returned with id 1


        ResultActions postAlbums = mockMvc.perform(post("/albums")
                .with(authentication(createJwtWithAdminRole()))
                .content(retrieveEminemAlbum())
                .contentType(MediaType.APPLICATION_JSON));

        postAlbums.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("EminemAlbum1")))
                .andExpect(jsonPath("$.songIds", containsInAnyOrder(1)));


        //  11. when I go to /albums/1 then I can not see any albums because there is no artist in system

        ResultActions getAlbumWithNoArtist = mockMvc.perform(get("/albums/1")
                .contentType(MediaType.APPLICATION_JSON));

        getAlbumWithNoArtist.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Album with id 1 not found")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")));


        //  12. when I post to /artists with Artist "Eminem" then Artist "Eminem" is returned with id 1

        ResultActions postArtist = mockMvc.perform(post("/artists")
                .with(authentication(createJwtWithAdminRole()))
                .content(retrieveEminemArtist())
                .contentType(MediaType.APPLICATION_JSON));

        postArtist.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Eminem")));


        //  13. when I put to /artists/1/albums/1 then Artist with id 1 ("Eminem") is added to Album with id 1 ("EminemAlbum1")

        ResultActions putArtistToAlbum = mockMvc.perform(put("/artists/1/1")
                .with(authentication(createJwtWithAdminRole()))
                .contentType(MediaType.APPLICATION_JSON));

        putArtistToAlbum.andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Album with id " + 1 + " has been added to Artist with id " + 1 + ".")));


        //  14. when I go to /albums/1 then I can see album with single song with id 1 and single artist with id 1

        ResultActions getAlbumWithSongAndArtist = mockMvc.perform(get("/albums/1")
                .contentType(MediaType.APPLICATION_JSON));

        getAlbumWithSongAndArtist.andExpect(status().isOk())
                .andExpect(jsonPath("$.artists[*].id", containsInAnyOrder(1)))
                .andExpect(jsonPath("$.songs[*].id", containsInAnyOrder(1)));


        //  15. when I put to /albums/1/songs/2 then Song with id 2 ("Lose Yourself") is added to Album with id 1 ("EminemAlbum1")
        ResultActions putSongToAlbum = mockMvc.perform(put("/albums/1/songs/2")
                .with(authentication(createJwtWithAdminRole()))
                .contentType(MediaType.APPLICATION_JSON));

        putSongToAlbum.andExpect(status().isOk())
                .andExpect(jsonPath("$.albumId", is(1)))
                .andExpect(jsonPath("$.name", is("EminemAlbum1")))
                .andExpect(jsonPath("$.songsIds[*]", containsInAnyOrder(1, 2)));


        //  16. when I go to /albums/1 then I can see album with 2 songs (id1 and id2)


        ResultActions getAlbumWithSongs = mockMvc.perform(get("/albums/1")
                .contentType(MediaType.APPLICATION_JSON));

        getAlbumWithSongs.andExpect(status().isOk())
                .andExpect(jsonPath("$.album.id", is(1)))
                .andExpect(jsonPath("$.songs[*].id", containsInAnyOrder(1, 2)));


    }


    private JwtAuthenticationToken createJwtWithAdminRole() {

        Jwt jwt = Jwt.withTokenValue("123")
                .claim("email", "anastazjaglowska12345@gmail.com")
                .header("alg", "none")
                .build();
        return jwtAuthConverter.convert(jwt);
    }


    private JwtAuthenticationToken createJwtWithUserRole() {

        Jwt jwt = Jwt.withTokenValue("123")
                .claim("email", "bartek@gmail.com")
                .header("alg", "none")
                .build();
        return jwtAuthConverter.convert(jwt);


    }
}
