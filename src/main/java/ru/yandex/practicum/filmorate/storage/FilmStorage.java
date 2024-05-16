package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Optional<Film> get(Long filmId);

    Film save(Film film);

    Film update(Film film);

    void addLike(Film film, User user);

    void deleteLike(Film film, User user);

    List<Film> listTop10Films(Integer count);

    List<Film> getAll();
}
