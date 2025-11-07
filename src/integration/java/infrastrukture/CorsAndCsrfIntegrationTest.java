package infrastrukture;


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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.options;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = SongifyApplication.class)
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@Testcontainers
class CorsAndCsrfIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14-alpine");

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    }

//    @Test
//    @WithMockUser
//    void testCsrf() throws Exception {
//        ResultActions perform = mockMvc.perform(post("/genres")
////                        .with(csrf())
//                .content("""
//                        {
//                            "name" : "Rap"
//                        }
//                        """.trim())
//                .contentType(MediaType.APPLICATION_JSON));
//
//        perform.andExpect(status().isForbidden());
//
//    }
//
//    @Test
//    public void testCors() throws Exception {
//        mockMvc.perform(options("/songs")
//                        .header("Access-Control-Request-Method", List.of("GET", "POST", "PUT", "DELETE", "PATCH"))
//                        .header("Origin", "https://localhost:3000")
//                )
//                .andExpect(header().exists("Access-Control-Allow-Origin"))
//                .andExpect(header().string("Access-Control-Allow-Origin", "https://localhost:3000"))
//                .andExpect(header().exists("Access-Control-Allow-Methods"))
//                .andExpect(header().string("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH"))
//                .andExpect(status().isOk());
//    }


    //


}
