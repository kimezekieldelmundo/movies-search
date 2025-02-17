package com.directa24.main.challenge.domain.services;

import com.directa24.main.challenge.domain.external.MovieAPI;
import com.directa24.main.challenge.domain.external.dto.GetMoviesDto;
import com.directa24.main.challenge.domain.external.dto.Movie;
import com.directa24.main.challenge.domain.models.Director;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Slf4j
public class DirectorServiceImpl implements DirectorService {
  private final MovieAPI movieAPI;

  @Override
  public List<Director> getDirectorsByMovieCount(Integer minimum) {
    Optional<GetMoviesDto> firstPage = movieAPI.getMovies(1).blockOptional();

    if (firstPage.isEmpty()) {
      return Collections.emptyList();
    }
    Map<String, Integer> moviesDirected =
        firstPage.map(GetMoviesDto::getData).map(this::moviesDirected).orElse(new HashMap<>());

    if (firstPage.get().getTotalPages() > 1) {
      getMoviePages(firstPage.get().getTotalPages())
          .map(GetMoviesDto::getData)
          .map(this::moviesDirected)
          .reduce(new HashMap<>(), this::merge)
          .blockOptional()
          .map(a -> this.merge(moviesDirected, a));
    }
    return moviesDirected.entrySet().stream()
        .filter(stringIntegerEntry -> stringIntegerEntry.getValue() >= minimum)
        .map(movie -> new Director(movie.getKey()))
        .toList();
  }

  private Map<String, Integer> moviesDirected(List<Movie> movies) {
    return movies.stream().collect(Collectors.toMap(Movie::getDirector, movie -> 1, Integer::sum));
  }

  private Map<String, Integer> merge(Map<String, Integer> acc, Map<String, Integer> newData) {
    newData.forEach((director, directed) -> acc.merge(director, directed, Integer::sum));
    return acc;
  }

  private Flux<GetMoviesDto> getMoviePages(int end) {
    return Flux.merge(IntStream.rangeClosed(2, end).boxed().map(movieAPI::getMovies).toList());
  }
}
