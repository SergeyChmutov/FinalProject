package ru.skypro.homework.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.skypro.homework.exception.AdsAvatarNotFoundException;
import ru.skypro.homework.exception.AdsImageFileNotFoundException;

@RestControllerAdvice
public class AdsExceptionHandler extends RuntimeException {

    @ExceptionHandler(AdsAvatarNotFoundException.class)
    public ResponseEntity<?> avatarNotFoundException(AdsAvatarNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(AdsImageFileNotFoundException.class)
    public ResponseEntity<?> imageNotFoundException(AdsImageFileNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

}
