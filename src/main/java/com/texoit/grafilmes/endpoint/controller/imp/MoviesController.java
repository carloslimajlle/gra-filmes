package com.texoit.grafilmes.endpoint.controller.imp;

import com.texoit.grafilmes.dtos.ProducersWinnerDTO;
import com.texoit.grafilmes.endpoint.controller.IMoviesController;
import com.texoit.grafilmes.service.MoviesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Carlos Lima on 20/10/2022
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MoviesController implements IMoviesController {

    private final MoviesService service;

    @Override
    public ResponseEntity<Map<String, List<ProducersWinnerDTO>>> getProducersWinner() {
        return ResponseEntity.ok(service.findAllProducersWinnerInterval());
    }
}
