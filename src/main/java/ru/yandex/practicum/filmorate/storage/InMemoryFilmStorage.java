package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.Map;


@Component // @Repository
public class InMemoryFilmStorage implements FilmStorage {
    long generator = 0;
    private final Map<Long, Film> filmMap = new HashMap<>();
    private final HashMap<Long, Set<Long>> filmLikeIds = new HashMap<>();
    //private static final Logger log = LoggerFactory.getLogger(InMemoryFilmStorage.class);

    @Override
    public List<Film> getAll() {
        return (List<Film>) filmMap.values();
    }

    @Override
    public Optional<Film> get(Long filmId) {
        return Optional.of(filmMap.get(filmId));
    }

    @Override
    public Film save(Film film) {
        film.setId(++generator);
        filmMap.put(film.getId(), film);
        return film;
    }

    @Override
    public void addLike(Film film, User user) {
        Set<Long> fLikeIds = filmLikeIds.computeIfAbsent(user.getId(), id -> new HashSet<>());
        fLikeIds.add(user.getId());
    }

    @Override
    public void deleteLike(Film film, User user) {
        Set<Long> fLikeIds = filmLikeIds.computeIfAbsent(user.getId(), id -> new HashSet<>());
        fLikeIds.remove(user.getId());
    }

    public List<Film> listTop10Films(Integer count){
        HashMap<Film, Integer> filmLikesMap = new HashMap<>();
        for(Map.Entry<Long, Set<Long>> entry : filmLikeIds.entrySet()) {
            Long filmId = entry.getKey();
            Set<Long> value = entry.getValue();
            Film film = filmMap.get(filmId);
            Integer likes = value.size();
            filmLikesMap.put(film, likes);
        }

        filmLikesMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue());

        List<Film> filmsSorted = (List<Film>) filmLikesMap.keySet();
        if (filmsSorted.size() > count){
            return filmsSorted.subList(0, count-1);
        } else {
            return filmsSorted;
        }
    }


//    private boolean validate(Film film) {
//        //название не может быть пустым;
//        if (film.getName() == null || film.getName().isBlank()) {
//            log.error("Название не может быть пустым");
//            return false;
//        }
//        //максимальная длина описания — 200 символов;
//        if (film.getDescription().length() > 200) {
//            log.error("Максимальная длина описания — 200 символов: {}", film.getDescription().length());
//            return false;
//        }
//        //дата релиза — не раньше 28 декабря 1895 года;
//        LocalDate dateMin = LocalDate.of(1895, 12, 28);
//        if (film.getReleaseDate().isBefore(dateMin)) {
//            log.error("дата релиза {} — должна быть не раньше {}", film.getReleaseDate(), dateMin);
//            return false;
//        }
//        //продолжительность фильма должна быть положительным числом.
//        if (film.getDuration() < 0) {
//            log.error("продолжительность фильма должна быть положительным числом: {}", film.getDuration());
//            return false;
//        }
//        return true;
//    }
}
