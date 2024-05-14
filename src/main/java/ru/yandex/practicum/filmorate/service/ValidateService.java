package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
@Service
public class ValidateService {
    public void validateUser(User user) {
        // электронная почта не может быть пустой и должна содержать символ @;
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("Email не может быть пустым и должен содержать @: {}", user.getEmail());
            throw new ValidationException("Email не может быть пустым и должен содержать @");
        }
        // логин не может быть пустым и содержать пробелы;
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.error("Логин не может быть пустым и не должен содержать пробелов: {}", user.getLogin());
            throw new ValidationException("Логин не может быть пустым и не должен содержать пробелов");
        }
        // дата рождения не может быть в будущем.
        if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) {
            log.error("День рождения не может быть пустым и не может быть в будущем: {}", user.getBirthday());
            throw new ValidationException("День рождения не может быть пустым и не может быть в будущем");
        }
        // имя для отображения может быть пустым — в таком случае будет использован логин;
        if (user.getName() == null || user.getName().isBlank()) {
            log.warn("Имя пользователя не указано, использован логин: {}", user.getName());
            //throw new ValidationException("Ошибка валидации");
            user.setName(user.getLogin());
        }
    }

    public void validateFilm(Film film) {
        //название не может быть пустым;
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("Название не может быть пустым");
            throw new ValidationException("Название не может быть пустым");
        }
        //максимальная длина описания — 200 символов;
        if (film.getDescription().length() > 200) {
            log.error("Максимальная длина описания — 200 символов: {}", film.getDescription().length());
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        //дата релиза — не раньше 28 декабря 1895 года;
        LocalDate dateMin = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(dateMin)) {
            log.error("дата релиза {} — должна быть не раньше {}", film.getReleaseDate(), dateMin);
            throw new ValidationException("дата релиза — должна быть не раньше 1985.12.28");
        }
        //продолжительность фильма должна быть положительным числом.
        if (film.getDuration() < 0) {
            log.error("продолжительность фильма должна быть положительным числом: {}", film.getDuration());
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
    }
}
