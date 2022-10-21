package com.texoit.grafilmes.repository;

import com.texoit.grafilmes.entity.Movies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author Carlos Lima on 20/10/2022
 */
public interface MoviesRepository extends JpaRepository<Movies, UUID> {

    List<Movies> findAllByProducersContainsIgnoreCaseAndWinnerOrderByYear(String producer, String indicated);
}
