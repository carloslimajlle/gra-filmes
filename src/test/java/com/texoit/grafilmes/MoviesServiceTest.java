package com.texoit.grafilmes;

import com.texoit.grafilmes.service.MoviesService;
import com.texoit.grafilmes.utils.CleanTables;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Carlos Lima on 23/10/2022
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@WebAppConfiguration
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class MoviesServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    MoviesService moviesService;

    @Autowired
    CleanTables cleanTables;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void winnerTest() throws Exception {
        cleanTables.clean("movies");
        moviesService.loadCsvMovies("src/test/resources/movielist.csv");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/producers/interval-winner")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.min", notNullValue()))
            .andExpect(jsonPath("$.max", notNullValue()));

    }

    @Test
    public void moreSameWinnerTest() throws Exception {
        cleanTables.clean("movies");
        moviesService.loadCsvMovies("src/test/resources/movielist-same-winner.csv");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/producers/interval-winner")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.min.[0].producer", is("Bo Derek")))
            .andExpect(jsonPath("$.min.[1].producer", is("Bo Derek")));

    }

    @Test
    public void noWinnerTest() throws Exception {
        cleanTables.clean("movies");
        moviesService.loadCsvMovies("src/test/resources/movielist-no-winner.csv");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/producers/interval-winner")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.min", anyOf(nullValue(), empty())))
            .andExpect(jsonPath("$.max", anyOf(nullValue(), empty())));

    }

    @Test
    public void undefinedWinnerTest() throws Exception {
        cleanTables.clean("movies");
        moviesService.loadCsvMovies("src/test/resources/movielist-no-winner.csv");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/producers/interval-winner")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.min", notNullValue()));

    }

}
