package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;


@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping
    public Collection<Film> getAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film){
        if (validate(film)) {
            film.setId(getNextId());
            films.put(film.getId(), film);
            return film;
        } else {
            throw new ValidationException("Ошибка валидации");
        }
    }

    //@PutMapping("/{id}")
    //public Film update(@RequestBody Film film, @PathVariable Long id){
    @PutMapping
    public Film update(@Valid @RequestBody Film film){
        Long id = film.getId();
        if (!films.containsKey(id)) {
            throw new ConditionsNotMetException("Указан некорректный идентификатор");
        }
        if (validate(film)) {
            film.setId(id);
            films.replace(id, film);
            return film;
        } else {
            throw new ValidationException("Ошибка валидации");
        }
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    private boolean validate(Film film) {
        //название не может быть пустым;
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("Название не может быть пустым");
            return false;
        }
        //максимальная длина описания — 200 символов;
        if (film.getDescription().length() > 200) {
            log.error("Максимальная длина описания — 200 символов: {}", film.getDescription().length());
            return false;
        }
        //дата релиза — не раньше 28 декабря 1895 года;
        LocalDate dateMin = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(dateMin)){
            log.error("дата релиза {} — должна быть не раньше {}", film.getReleaseDate(), dateMin);
            return false;
        }
        //продолжительность фильма должна быть положительным числом.
        if (film.getDuration() < 0){
            log.error("продолжительность фильма должна быть положительным числом: {}", film.getDuration());
            return false;
        }
        return true;
    }
}
