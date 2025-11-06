package com.songify;

import com.songify.infrastructure.security.jwt.JwtConfiguationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = JwtConfiguationProperties.class)
public class SongifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SongifyApplication.class, args);
    }

}
