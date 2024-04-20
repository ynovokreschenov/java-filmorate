package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = { "id" })
public class User {
    private Long id;
    @Email
    private String email;
    private String login;
    @NotBlank
    private String name;
    private LocalDate birthday;
}
