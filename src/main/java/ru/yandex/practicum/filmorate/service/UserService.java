package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;


@Service
@AllArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User get(Long userId) {
        final User user = userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException("User not found with" + userId));
        return user;
    }

    public User save(User user) {
        return userStorage.save(user);
    }

    public void update(User user) {
        Long userId = user.getId();
        final User saved = userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException("User not found with" + userId));
        userStorage.update(user);
    }

    public void addFriend(Long userId, Long friendId) {
        final User user = userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException("User not found with" + userId));
        final User friend = userStorage.get(friendId)
                .orElseThrow(() -> new NotFoundException("User not found with" + friendId));
        // TODO check userId friendID
        userStorage.addFriend(user, friend);
    }

    public void deleteFriend(Long userId, Long friendId) {
        final User user = userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException("User not found with" + userId));
        final User friend = userStorage.get(friendId)
                .orElseThrow(() -> new NotFoundException("User not found with" + friendId));
        // TODO check userId friendID
        userStorage.deleteFriend(user, friend);
    }

    public List<User> getUserFriends(Long userId) {
        final User user = userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException("User not found with" + userId));
        return userStorage.getUserFriends(userId);
    }

    public List<User> getUsersCommonFriends(Long userId, Long otherId) {
        final User user = userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException("User not found with" + userId));
        final User other = userStorage.get(otherId)
                .orElseThrow(() -> new NotFoundException("User not found with" + otherId));
        return userStorage.getUsersCommonFriends(userId, otherId);
    }
}
