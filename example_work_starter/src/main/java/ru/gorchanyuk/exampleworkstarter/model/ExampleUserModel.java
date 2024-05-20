package ru.gorchanyuk.exampleworkstarter.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Пример модели пользователя")
public class ExampleUserModel {

    @Schema(description = "Имя", example = "Иван")
    private String firstName;

    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;

    @Schema(description = "Ник пользователя", example = "ivan_12")
    private String username;

    @Schema(description = "Адрес электронной почты", example = "ivan_12@mail.com")
    private String email;
}
