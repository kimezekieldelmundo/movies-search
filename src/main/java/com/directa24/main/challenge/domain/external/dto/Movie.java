package com.directa24.main.challenge.domain.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Movie {
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year") private String year;
    @JsonProperty("Rated") private String rated;
    @JsonProperty("Released") private String released;
    @JsonProperty("Runtime") private String runtime;
    @JsonProperty("Genre") private String genre;
    @JsonProperty("Director") private String director;
    @JsonProperty("Writer") private String writer;
    @JsonProperty("Actors") private String actors;
    //    Title: title of the movie
//    Year: year the movie was released
//    Rated: movie rating
//    Released: movie release date
//    Runtime: movie duration time in minutes
//    Genre: move genre
//    Director: movie director
//    Writer: movie writers
//    Actors: movie actors
}
