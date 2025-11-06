package uz.lb.updaterserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AppForbiddenException extends RuntimeException {

    public AppForbiddenException(String message) {
        super(message);
    }
}

