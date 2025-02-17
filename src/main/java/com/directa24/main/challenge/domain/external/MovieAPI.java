package com.directa24.main.challenge.domain.external;

import com.directa24.main.challenge.domain.external.dto.GetMoviesDto;
import reactor.core.publisher.Mono;

public interface MovieAPI {
  Mono<GetMoviesDto> getMovies(Integer page);
}
