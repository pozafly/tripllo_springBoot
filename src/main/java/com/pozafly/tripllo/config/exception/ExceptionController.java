package com.pozafly.tripllo.config.exception;

import com.pozafly.tripllo.model.network.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    Message message = new Message();

    // 400
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> BadRequestException(final RuntimeException ex) {
        log.warn("error", ex);
        message.setMessage(ex.getMessage());
        return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
    }

    // 401
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity handleAccessDeniedException(final AccessDeniedException ex) {
        log.warn("error", ex);
        message.setMessage(ex.getMessage());
        return new ResponseEntity<>(message, null, HttpStatus.UNAUTHORIZED);
    }


    // 500
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);

        message.setMessage(ex.getMessage());
        return new ResponseEntity<>(message, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
