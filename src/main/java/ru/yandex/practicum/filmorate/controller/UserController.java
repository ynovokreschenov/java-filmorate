package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/users")
//@Slf4j
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public Collection<User> getAll() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (validate(user)) {
            user.setId(getNextId());
            users.put(user.getId(), user);
            return user;
        } else {
            throw new ValidationException("Ошибка валидации");
        }
    }

    //@PutMapping("/{id}")
    //public User update(@RequestBody User user, @PathVariable Long id) {
    @PutMapping
    public User update(@Valid @RequestBody User user) {
        Long id = user.getId();
        if (!users.containsKey(id)) {
            throw new ConditionsNotMetException("Указан некорректный идентификатор");
        }
        if (validate(user)){
            user.setId(id);
            users.replace(id, user);
            return user;
        } else {
            throw new ValidationException("Ошибка валидации");
        }
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    private boolean validate(User user) {
        // электронная почта не может быть пустой и должна содержать символ @;
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("Email не может быть пустым и должен содержать @: {}", user.getEmail());
            return false;
        }
        // логин не может быть пустым и содержать пробелы;
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.error("Логин не может быть пустым и не должен содержать пробелов: {}", user.getLogin());
            return false;
        }
        // дата рождения не может быть в будущем.
        if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) {
            log.error("День рождения не может быть пустым и не может быть в будущем: {}", user.getBirthday());
            return false;
        }
        // имя для отображения может быть пустым — в таком случае будет использован логин;
        if (user.getName() == null || user.getName().isBlank()) {
            log.warn("Имя пользователя не указано, использован логин: {}", user.getName());
            user.setName(user.getLogin());
        }
        return true;
    }
}
