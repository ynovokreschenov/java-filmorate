package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.util.Date;

/**
 * Film.
 */
@Data
@EqualsAndHashCode(of = { "id" })
public class Film {
    private Long id;
    private String name;
    private String description;
    private Date releaseDate;
    private Integer duration;
}
