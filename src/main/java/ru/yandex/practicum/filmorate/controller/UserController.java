package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.service.ValidateService;

import java.util.Collection;
import java.util.List;


@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ValidateService validateService;

//    public UserController(UserService userService, ValidateService validateService) {
//        this.userService = userService;
//        this.validateService = validateService;
//    }
    //private final UserStorage userStorage;
    //private final UserController userController;

    //public UserController(UserStorage userStorage, UserController userController){
    //    this.userStorage = userStorage;
    //    this.userController = userController;
    //}

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    public User get(@PathVariable Long userId) {
        log.info("GET /users/{} ==>", userId);
        User user = userService.get(userId);
        log.info("GET /users/{} <== {}", userId, user);
        return user;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@Valid @RequestBody User user) {
        log.info("Create user: {} - started", user);
        validateService.validateUser(user);
        User saved = userService.save(user);
        log.info("Create user: {} - finished", user);
        return saved;
    }

    @PutMapping()
    public void update(@Valid @RequestBody User user) {
        validateService.validateUser(user);
        userService.update(user);
    }

    @PutMapping("/users/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/users/{userId}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/users/{userId}/friends")
    public List<User> getUserFriends(@PathVariable Long userId) {
        return userService.getUserFriends(userId);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getUsersCommonFriends(@PathVariable Long userId, @PathVariable Long otherId) {
        return userService.getUsersCommonFriends(userId, otherId);
    }
}
