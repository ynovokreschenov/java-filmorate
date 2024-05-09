package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    String message;
    String stacktrace;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
