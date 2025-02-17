package com.directa24.main.challenge.infrastructure.web;

import com.directa24.main.challenge.domain.external.MovieAPI;
import com.directa24.main.challenge.domain.external.dto.GetMoviesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MovieAPIImpl implements MovieAPI {
  private final String MOVIES_SEARCH_PATH = "/api/movies/search";
  private final String PAGE = "page";
  private final WebClient eronInternationalWebClient;

  @Override
  public Mono<GetMoviesDto> getMovies(Integer page) {
    return eronInternationalWebClient
        .get()
        .uri(uriBuilder -> uriBuilder.path(MOVIES_SEARCH_PATH).queryParam(PAGE, page).build())
        .retrieve()
        .bodyToMono(GetMoviesDto.class);
  }
}
