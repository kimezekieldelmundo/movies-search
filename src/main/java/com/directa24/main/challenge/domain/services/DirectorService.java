package com.directa24.main.challenge.domain.services;

import com.directa24.main.challenge.domain.models.Director;

import java.util.List;

public interface DirectorService {
    List<Director> getDirectorsByMovieCount(Integer minimum);
}
