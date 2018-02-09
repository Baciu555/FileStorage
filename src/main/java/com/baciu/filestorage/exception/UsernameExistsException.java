package com.baciu.filestorage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "nazwa zajęta")
public class UsernameExistsException extends Exception {
}
