package com.sltc.dait.library.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Book")
@NoArgsConstructor
public class NoSuchBookExistsException extends RuntimeException {
    private String message;

    public NoSuchBookExistsException(String msg) {
        super(msg);
        this.message = msg;
    }
}
