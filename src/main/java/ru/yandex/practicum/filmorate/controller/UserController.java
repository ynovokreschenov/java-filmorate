package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getAll() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user){
        if (validate(user)) {
            user.setId(getNextId());
            users.put(user.getId(), user);
            return user;
        } else {
            throw new ValidationException("Ошибка валидации");
        }
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User user, @PathVariable Long id){
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

    private boolean validate(User user){
        // электронная почта не может быть пустой и должна содержать символ @;
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getLogin().contains("@")) {
            //throw new ValidationException("Email не может быть пустым и должен содержать @");
            // TODO логирование
            return false;
        }
        // логин не может быть пустым и содержать пробелы;
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            //throw new ValidationException("Логин не может быть пустым и не должен содержать пробелов");
            // TODO логирование
            return false;
        }
        // дата рождения не может быть в будущем.
        if (user.getBirthday() == null || user.getBirthday().toInstant().isAfter(Instant.now())) {
            //throw new ValidationException("День рождения не может быть пустым и не может быть в будущем");
            // TODO логирование
            return false;
        }
        // имя для отображения может быть пустым — в таком случае будет использован логин;
        if (user.getName() == null || user.getName().isBlank()) {
            // TODO логирование
            user.setName(user.getLogin());
        }
        return true;
    }
}
