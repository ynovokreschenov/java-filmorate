package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class InMemoryUserStorage implements UserStorage {
    long generator = 0;
    private final HashMap<Long, User> userMap = new HashMap<>();
    private final HashMap<Long, Set<Long>> userFriendIds = new HashMap<>();
    //private static final Logger log = LoggerFactory.getLogger(InMemoryUserStorage.class);

    @Override
    public List<User> getAll() {
        return (List<User>) userMap.values();
    }

    @Override
    public User create(User user) {
        user.setId(++generator);
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public void update(User user) {
        userMap.put(user.getId(), user);
    }

    @Override
    public Optional<User> get(Long userId) {
        return Optional.of(userMap.get(userId));
    }

    @Override
    public User save(User user) {
        Long id = ++generator;
        userMap.put(id, user);
        user.setId(id);
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }

    @Override
    public void addFriend(User user, User friend) {
        Set<Long> uFriendIds = userFriendIds.computeIfAbsent(user.getId(), id -> new HashSet<>());
        uFriendIds.add(friend.getId());
        // дружба автоматом принимается второй стороной
        Set<Long> fFriendIds = userFriendIds.computeIfAbsent(friend.getId(), id -> new HashSet<>());
        fFriendIds.add(user.getId());
    }

    @Override
    public void deleteFriend(User user, User friend) {
        Set<Long> uFriendIds = userFriendIds.computeIfAbsent(user.getId(), id -> new HashSet<>());
        uFriendIds.remove(friend.getId());

        Set<Long> fFriendIds = userFriendIds.computeIfAbsent(friend.getId(), id -> new HashSet<>());
        fFriendIds.remove(user.getId());
    }

    @Override
    public List<User> getUserFriends(Long userId) {
        List<User> userFriends = new ArrayList<>();
        for (Long friendId : userFriendIds.get(userId)) {
            userFriends.add(userMap.get(friendId));
        }
        return userFriends;
    }

    @Override
    public List<User> getUsersCommonFriends(Long userId, Long otherId) {
        Set<Long> uFriendIds = userFriendIds.get(userId);
        Set<Long> oFriendIds = userFriendIds.get(otherId);
        Set<Long> commonFriendIds = uFriendIds.stream()
                .filter(oFriendIds::contains)
                .collect(Collectors.toSet());
        List<User> commonFriendList = new ArrayList<>();
        for (Long friendId : commonFriendIds) {
            commonFriendList.add(userMap.get(friendId));
        }
        return commonFriendList;
    }
}
