package com.directa24.main.challenge.interfaces.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DirectorsDto {
    private List<String> directors;
}
