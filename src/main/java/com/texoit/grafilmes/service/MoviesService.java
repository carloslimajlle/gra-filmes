package com.texoit.grafilmes.service;

import com.texoit.grafilmes.dtos.MoviesDTO;
import com.texoit.grafilmes.dtos.ProducersWinnerDTO;
import com.texoit.grafilmes.entity.Movies;
import com.texoit.grafilmes.enums.EnIndicated;
import com.texoit.grafilmes.error.BadRequestException;
import com.texoit.grafilmes.repository.MoviesRepository;
import com.texoit.grafilmes.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Carlos Lima on 20/10/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MoviesService {

    private final MoviesRepository repository;

    public void loadCsvMovies(final String path) {

        final List<MoviesDTO> moviesList;
        try {
            moviesList = Util.readData(path, MoviesDTO.class, ';', false);
        } catch (final Exception e) {
            throw new BadRequestException("Não foi possivel carregar a lista de filmes!");
        }

        moviesList.forEach(v -> {
            create(Movies.builder()
                .year(v.getYear())
                .producers(v.getProducers())
                .title(v.getTitle())
                .winner(v.getWinner())
                .studios(v.getStudios())
                .build());
        });
    }

    public Movies create(final Movies movies) {

        if (movies.getYear() > LocalDate.now(Util.ZONA_ID)
            .getYear()) {
            throw new BadRequestException("Ano da produção do filme não pode ser maior que o ano atual!");
        }

        return repository.save(movies);
    }

    public Map<String,List<ProducersWinnerDTO>> findAllProducersWinnerInterval() {
        final List<Movies> moviesList = repository.findAll();

        final List<String> producerList = moviesList
            .stream()
            .distinct()
            .map(Movies::getProducers)
            .collect(Collectors.toList());

        final List<ProducersWinnerDTO> producersWinnerDTOList = new ArrayList<>();

        producerList.forEach(v -> {
            final List<ProducersWinnerDTO> producersSequencialList = findWinnerSequencial(v);

            producersSequencialList.forEach(p -> {
                if (p.getInterval() > 0 && !existsProducersListWinner(producersWinnerDTOList,
                    p)) {
                    producersWinnerDTOList.add(p);
                }
            });
        });

        return getIntervalAwards(producersWinnerDTOList);
    }

    private Map<Integer,List<Integer>> findIntervalWinProducer(final String producer) {
        final List<Movies> moviesProducerList = repository.findAllByProducersContainsIgnoreCaseAndWinnerOrderByYear(producer,
            EnIndicated.YES.getIndicated());

        final List<Integer> yearsMovies = moviesProducerList
            .stream()
            .map(Movies::getYear)
            .sorted()
            .collect(Collectors.toList());

        final Map<Integer,List<Integer>> producerInterval = new HashMap<>();

        yearsMovies.stream().reduce((firstYear,secondYear) ->{

            final List<Integer> yearsWinner = new ArrayList<>();
            yearsWinner.add(firstYear);
            yearsWinner.add(secondYear);

            final LocalDate dateFirstYearWinner = LocalDate.ofYearDay(firstYear,1);
            final LocalDate dateSecondYearWinner = LocalDate.ofYearDay(secondYear,1);
            final Period periodInterval = Period.between(dateFirstYearWinner, dateSecondYearWinner);

            try {
                final int totalYearsInterval = Math.abs(periodInterval.getYears());
                producerInterval.put(totalYearsInterval, yearsWinner);
            } catch (final Exception e) {
                log.info("{} Erro ao obter o total de intervalo de anos! Error: {}", Util.LOG_PREFIX, e.getMessage());
            }

            return secondYear;
        });

        return producerInterval;
    }

    private List<ProducersWinnerDTO> findWinnerSequencial(final String producer) {
        final Map<Integer, List<Integer>> intervalWinProducer = findIntervalWinProducer(producer);

        final List<ProducersWinnerDTO> producersWinnerDTOList = new ArrayList<>();

        intervalWinProducer.forEach((key, value) -> {
            final ProducersWinnerDTO producersWinnerDTO = ProducersWinnerDTO.builder()
                .producer(producer)
                .build();
            producersWinnerDTO.setInterval(key);
            value.stream()
                .reduce((firstYear, secondYear) -> {
                    producersWinnerDTO.setPreviousWin(firstYear);
                    producersWinnerDTO.setFollowingWin(secondYear);
                    return secondYear;
                });
            producersWinnerDTOList.add(producersWinnerDTO);
        });

        return producersWinnerDTOList;
    }

    private boolean existsProducersListWinner(
        final List<ProducersWinnerDTO> producersWinnerDTOList, final ProducersWinnerDTO producersWinnerDTO) {
        return producersWinnerDTOList.stream().anyMatch(v -> v.getProducer().equalsIgnoreCase(
            producersWinnerDTO.getProducer()) && v.getPreviousWin()== producersWinnerDTO.getPreviousWin() && v.getFollowingWin()== producersWinnerDTO.getFollowingWin());
    }

    private Map<String,List<ProducersWinnerDTO>> getIntervalAwards(final List<ProducersWinnerDTO> producersWinnerDTOList) {
        final int min = producersWinnerDTOList.stream()
            .mapToInt(ProducersWinnerDTO::getInterval)
            .min()
            .orElse(0);

        final int max = producersWinnerDTOList.stream()
            .mapToInt(ProducersWinnerDTO::getInterval)
            .max()
            .orElse(0);

        final List<ProducersWinnerDTO> minWinner = producersWinnerDTOList.stream()
            .filter(v -> v.getInterval() == min)
            .collect(
                Collectors.toList());

        final List<ProducersWinnerDTO> maxWinner = producersWinnerDTOList.stream()
            .filter(v -> v.getInterval() == max)
            .collect(
                Collectors.toList());

        final Map<String, List<ProducersWinnerDTO>> intervalAward = new HashMap<>();
        intervalAward.put("min", minWinner);
        intervalAward.put("max", maxWinner);

        return intervalAward;
    }
}
