package uz.lb.updaterserver.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AppPermissionDeniedException extends RuntimeException {
    public AppPermissionDeniedException(String message) {
        super(message);
    }
}
