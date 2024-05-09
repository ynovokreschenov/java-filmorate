package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = { "id" })
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {
    Long id;
    @NotBlank
    String name;
    String description;
    @Past
    LocalDate releaseDate;
    Integer duration;
    //@JsonIgnore
    //Set<Long> likes = new HashSet<>();
}
