package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmTest {
    private static Film film;

    @BeforeEach
    public void beforeEach() {
        film = new Film();
        film.setName("FilmName");
        film.setId(Long.valueOf(1));
        film.setDescription("Film Description");
    }

    @Test
    public void setAndGetSubtaskTest() {
        assertEquals(film.getId(), Long.valueOf(1), "Не совпадает id");
        assertEquals(film.getName(), "FilmName", "Не совпадает name");
        assertEquals(film.getDescription(), "Film Description", "Не совпадает description");
    }
}
