package uz.lb.updaterserver.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AppItemNotFoundException extends RuntimeException {
    public AppItemNotFoundException(String message) {
        super(message);
    }
}
