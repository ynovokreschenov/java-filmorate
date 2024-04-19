package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.Instant;
import java.util.*;


@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> getAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film){
        if (validate(film)) {
            film.setId(getNextId());
            film.setReleaseDate(Date.from(Instant.now()));
            films.put(film.getId(), film);
            return film;
        } else {
            throw new ValidationException("Ошибка валидации");
        }
    }

    @PutMapping("/{id}")
    public Film update(@RequestBody Film film, @PathVariable Long id){
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
            //throw new ConditionsNotMetException("Название не может быть пустым");
            // TODO логирование
            return false;
        }
        //максимальная длина описания — 200 символов;
        if (film.getDescription().length() > 200) {
            //throw new ConditionsNotMetException("Название не может быть пустым");
            // TODO логирование
            return false;
        }
        //дата релиза — не раньше 28 декабря 1895 года;
        Date dateMin = new Date(1895, 12, 28);
        if (film.getReleaseDate().toInstant().isBefore(dateMin.toInstant())){
            // TODO логирование
            return false;
        }
        //продолжительность фильма должна быть положительным числом.
        if (film.getDuration() < 0){
            // TODO логирование
            return false;
        }
        return true;
    }
}
