package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.ValidateService;

import java.util.*;


@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    private final ValidateService validateService;

    @GetMapping
    public List<Film> getAll() {
        return filmService.getAll();
    }

    @GetMapping("/{filmId}")
    public Film get(@PathVariable Long filmId) {
        log.info("GET /films/{} ==>", filmId);
        Film film = filmService.get(filmId);
        log.info("GET /films/{} <==", filmId);
        return film;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        validateService.validateFilm(film);
        return filmService.save(film);
    }

    @PutMapping
    public Film save(@Valid @RequestBody Film film) {
        validateService.validateFilm(film);
        return filmService.save(film);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteLike(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular?count={count}")
    public void listTop10Films(@PathVariable Integer count) {
        filmService.listTop10Films(count);
    }
}
