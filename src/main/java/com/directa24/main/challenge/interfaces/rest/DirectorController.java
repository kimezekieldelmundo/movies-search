package com.directa24.main.challenge.interfaces.rest;

import com.directa24.main.challenge.domain.models.Director;
import com.directa24.main.challenge.domain.services.DirectorService;
import com.directa24.main.challenge.interfaces.rest.dto.DirectorsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DirectorController {
  private final DirectorService directorService;

  @GetMapping("/api/directors")
  public ResponseEntity<DirectorsDto> getDirectors(@RequestParam Integer threshold) {
    return ResponseEntity.ok(
        DirectorsDto.builder()
            .directors(
                directorService.getDirectorsByMovieCount(threshold).stream()
                    .map(Director::getName)
                    .toList())
            .build());
  }
}
