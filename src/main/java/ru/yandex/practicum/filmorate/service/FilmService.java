package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Service
@AllArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film get(Long filmId) {
        final Film film = filmStorage.get(filmId)
                .orElseThrow(() -> new NotFoundException("Film not found with " + filmId));
        return film;
    }

    public Film save(Film film) {
        return filmStorage.save(film);
    }

    public Film update(Film film) {
        Long filmId = film.getId();
        final Film saved = filmStorage.get(filmId)
                .orElseThrow(() -> new NotFoundException("Film not found with " + filmId));
        return filmStorage.update(film);
    }

    public void addLike(Long filmId, Long userId) {
        final Film film = filmStorage.get(filmId)
                .orElseThrow(() -> new NotFoundException("Film not found with " + filmId));
        final User user = userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException("User not found with " + userId));
        filmStorage.addLike(film, user);
    }

    public void deleteLike(Long filmId, Long userId) {
        final Film film = filmStorage.get(filmId)
                .orElseThrow(() -> new NotFoundException("Film not found with " + filmId));
        final User user = userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException("User not found with " + userId));
        filmStorage.deleteLike(film, user);
    }

    public List<Film> listTop10Films(Integer count) {
        if (count == null || count < 0) count = 10;
        return filmStorage.listTop10Films(count);
    }

}
