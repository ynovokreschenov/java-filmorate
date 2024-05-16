package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

public interface UserStorage {
    List<User> getAll();

    User create(User user);

    void update(User user);

    Optional<User> get(Long userId);

    User save(User user);

    void addFriend(User user, User friend);

    void deleteFriend(User user, User friend);

    List<User> getUserFriends(User user);

    List<User> getUsersCommonFriends(User user, User other);
}
