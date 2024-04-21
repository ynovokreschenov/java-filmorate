package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    private static User user;

    @BeforeEach
    public void beforeEach() {
        user = new User();
        user.setName("Vasya");
        user.setId(Long.valueOf(1));
        user.setEmail("user@mail.ru");
        user.setLogin("user");
        user.setName("Vasya");
    }

    @Test
    public void setAndGetSubtaskTest() {
        assertEquals(user.getId(), Long.valueOf(1), "Не совпадает id");
        assertEquals(user.getName(), "Vasya", "Не совпадает name");
        assertEquals(user.getEmail(), "user@mail.ru", "Не совпадает email");
        assertEquals(user.getLogin(), "user", "Не совпадает login");
    }
}
