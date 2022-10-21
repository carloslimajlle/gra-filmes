package com.texoit.grafilmes.endpoint.controller;

import com.texoit.grafilmes.dtos.ProducersWinnerDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Carlos Lima on 20/10/2022
 */
@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*")
public interface IMoviesController {

    @GetMapping(value = "producers/interval-winner", produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<Map<String, List<ProducersWinnerDTO>>> getProducersWinner();
}
