package ru.medvedev.userjob.resource.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseDto {

    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String description;
}
