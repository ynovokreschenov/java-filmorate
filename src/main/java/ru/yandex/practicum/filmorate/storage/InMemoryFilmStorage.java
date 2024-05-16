package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.Map;


@Component
public class InMemoryFilmStorage implements FilmStorage {
    long generator = 0;
    private final Map<Long, Film> filmMap = new HashMap<>();
    private final HashMap<Long, Set<Long>> filmLikeIds = new HashMap<>();

    @Override
    public List<Film> getAll() {
        return filmMap.values().stream().toList();
    }

    @Override
    public Optional<Film> get(Long filmId) {
        //return Optional.of(filmMap.get(filmId));
        if (filmMap.containsKey(filmId)) {
            return Optional.of(filmMap.get(filmId));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Film save(Film film) {
        film.setId(++generator);
        filmMap.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        filmMap.put(film.getId(), film);
        return film;
    }

    @Override
    public void addLike(Film film, User user) {
        Set<Long> fLikeIds = filmLikeIds.computeIfAbsent(film.getId(), id -> new HashSet<>());
        fLikeIds.add(user.getId());
    }

    @Override
    public void deleteLike(Film film, User user) {
        Set<Long> fLikeIds = filmLikeIds.computeIfAbsent(film.getId(), id -> new HashSet<>());
        fLikeIds.remove(user.getId());
    }

    public List<Film> listTop10Films(Integer count) {
        HashMap<Film, Integer> filmLikesMap = new HashMap<>();
        for (Map.Entry<Long, Set<Long>> entry : filmLikeIds.entrySet()) {
            Long filmId = entry.getKey();
            Set<Long> value = entry.getValue();
            Film film = filmMap.get(filmId);
            Integer likes = value.size();
            filmLikesMap.put(film, likes);
        }

        filmLikesMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue());

        Set<Film> filmsSorted = filmLikesMap.keySet();
        if (filmsSorted.size() > count) {
            return filmsSorted.stream().toList().subList(0, count - 1);
        } else {
            return filmsSorted.stream().toList();
        }
    }
}
