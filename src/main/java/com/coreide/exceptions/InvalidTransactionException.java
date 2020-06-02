package com.coreide.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@ResponseBody
public class InvalidTransactionException extends RuntimeException {

    public InvalidTransactionException(String message){
        super(message);
    }
}
