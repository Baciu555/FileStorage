package com.baciu.filestorage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "grupa nie istnieje")
public class GroupNotExistsException extends Exception {
}
