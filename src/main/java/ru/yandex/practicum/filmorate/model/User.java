package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(of = { "id" })
public class User {
    private Long id;
    private String email;
    private String login;
    private String name;
    private Date birthday;
}
