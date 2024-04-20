package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

/**
 * Film.
 */
@Data
@EqualsAndHashCode(of = { "id" })
public class Film {
    private Long id;
    @NotBlank
    private String name;
    @Max(200)
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
}
