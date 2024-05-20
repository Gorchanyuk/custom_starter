package ru.gorchanyuk.exampleworkstarter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gorchanyuk.exampleworkstarter.exception.ExampleException;
import ru.gorchanyuk.exampleworkstarter.model.ExampleUserModel;

@RestController
@RequestMapping("/test")
@Tag(name = "Пример контроллера", description = "Содержит методы различного типа с разными кодами ответа")
public class ExampleController {

    @GetMapping("/users/{id}")
    @Operation(summary = "Получение пользователя",
            description = "Пример получение модели пользователя")
    public ResponseEntity<ExampleUserModel> getUser(@Parameter(description = "id пользователя (любое число)")
                                                    @PathVariable("id") long id) {
        HttpHeaders headers = createHeaders();
        ExampleUserModel model = ExampleUserModel.builder()
                .firstName("Семен")
                .lastName("Семенов")
                .username("semen")
                .email("semen12@mail.com")
                .build();
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(model);
    }

    @PostMapping("/user")
    @Operation(summary = "Создание пользователя",
            description = "Пример успешного создания нового пользователя")
    public ResponseEntity<?> createUser(@RequestBody ExampleUserModel user) {
        HttpHeaders headers = createHeaders();

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();
    }

    @PutMapping("/forbidden")
    @Operation(summary = "Неаторизованный пользователь",
            description = "Пример отправки статуса что пользователь не авторизован")
    public ResponseEntity<?> forbidden() {
        HttpHeaders headers = createHeaders();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).headers(headers).build();
    }

    @DeleteMapping("/exception")
    @Operation(summary = "Выброс пользовательского исключения",
            description = "Пример выброса пользовательского исключения")
    public ResponseEntity<?> throwException() {

        throw new ExampleException("Выброс пользовательского исключения");
    }

    private HttpHeaders createHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("Bearer token123");
        headers.setExpires(0);
        headers.setConnection("keep-alive");
        headers.add("Custom-Header", "customHeaderValue");

        return headers;
    }

}
