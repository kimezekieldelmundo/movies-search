package com.directa24.main.challenge.domain.services;

import com.directa24.main.challenge.domain.external.MovieAPI;
import com.directa24.main.challenge.domain.external.dto.GetMoviesDto;
import com.directa24.main.challenge.domain.external.dto.Movie;
import com.directa24.main.challenge.domain.models.Director;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DirectorServiceImplTest {
  @InjectMocks DirectorServiceImpl underTest;
  @Mock MovieAPI movieAPI;

  @Test
  void givenMovies_whenGetDirectorsByMovieCount_thenReturnDirectors() {
    final String directorA = "Director A";
    final String directorB = "Director B";
    final int totalPages = 3;

    when(movieAPI.getMovies(1))
        .thenReturn(
            Mono.fromSupplier(
                () ->
                    GetMoviesDto.builder()
                        .page(1)
                        .totalPages(totalPages)
                        .data(
                            List.of(
                                Movie.builder().director(directorA).title("Movie 1").build(),
                                Movie.builder().director(directorB).title("Movie 2").build()))
                        .build()));
    when(movieAPI.getMovies(2))
        .thenReturn(
            Mono.fromSupplier(
                () ->
                    GetMoviesDto.builder()
                        .page(2)
                        .totalPages(totalPages)
                        .data(
                            List.of(
                                Movie.builder().director(directorA).title("Movie 3").build(),
                                Movie.builder().director(directorB).title("Movie 4").build()))
                        .build()));
    when(movieAPI.getMovies(3))
        .thenReturn(
            Mono.fromSupplier(
                () ->
                    GetMoviesDto.builder()
                        .page(2)
                        .totalPages(totalPages)
                        .data(
                            List.of(
                                Movie.builder().director(directorA).title("Movie 5").build(),
                                Movie.builder().director(directorA).title("Movie 6").build()))
                        .build()));
    assertEquals(List.of(new Director(directorA)), underTest.getDirectorsByMovieCount(4));
  }

  @Test
  void givenOneMoviePage_whenGetDirectorsByMovieCount_thenReturnDirectors() {
    final String directorA = "Director A";
    final String directorB = "Director B";
    final int totalPages = 1;

    when(movieAPI.getMovies(1))
        .thenReturn(
            Mono.fromSupplier(
                () ->
                    GetMoviesDto.builder()
                        .page(1)
                        .totalPages(totalPages)
                        .data(
                            List.of(
                                Movie.builder().director(directorA).title("Movie 1").build(),
                                Movie.builder().director(directorB).title("Movie 2").build()))
                        .build()));
    assertIterableEquals(
        List.of(new Director(directorA), new Director(directorB)),
        underTest.getDirectorsByMovieCount(1));
  }

  @Test
  void givenNoDirectorsMeetMinimum_whenGetDirectorsByMovieCount_thenReturnEmptyList() {
    final String directorA = "Director A";
    final String directorB = "Director B";
    final int totalPages = 1;

    when(movieAPI.getMovies(1))
        .thenReturn(
            Mono.fromSupplier(
                () ->
                    GetMoviesDto.builder()
                        .page(1)
                        .totalPages(totalPages)
                        .data(
                            List.of(
                                Movie.builder().director(directorA).title("Movie 1").build(),
                                Movie.builder().director(directorB).title("Movie 2").build()))
                        .build()));
    assertIterableEquals(Collections.emptyList(), underTest.getDirectorsByMovieCount(2));
  }
}
