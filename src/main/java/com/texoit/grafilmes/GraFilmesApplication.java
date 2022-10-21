package com.texoit.grafilmes;

import com.texoit.grafilmes.service.MoviesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class GraFilmesApplication {

    @Value("${config.message}")
    private String message;

    @Autowired
    private MoviesService moviesService;

    public static void main(final String[] args) {
        SpringApplication.run(GraFilmesApplication.class, args);
    }

    @Bean
    CommandLineRunner init() {
        moviesService.loadCsvMovies();
        return args -> log.info(message);
    }
}
