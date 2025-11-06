package uz.lb.updaterserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.exception.AppBadRequestException;
import uz.lb.updaterserver.exception.AppForbiddenException;
import uz.lb.updaterserver.exception.AppItemNotFoundException;
import uz.lb.updaterserver.exception.AppPermissionDeniedException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@ControllerAdvice
public class ExceptionHandlerController {
    private final ResultDTO result = new ResultDTO();

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ResultDTO> handler(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(result.error(e));
    }

    @ExceptionHandler({AppBadRequestException.class})
    public ResponseEntity<ResultDTO> handlerBadRequest(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(result.error(e));
    }

    @ExceptionHandler({AppItemNotFoundException.class})
    public ResponseEntity<ResultDTO> handlerNotFound(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(result.error(e));
    }

    @ExceptionHandler({AppForbiddenException.class})
    public ResponseEntity<ResultDTO> handleForbidden(AppForbiddenException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ResultDTO().error(e.getMessage()));
    }

    @ExceptionHandler(AppPermissionDeniedException.class)
    public ResponseEntity<ResultDTO> handlePermissionDenied(AppPermissionDeniedException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ResultDTO().error(e.getMessage()));
    }

    @ExceptionHandler({AccessDeniedException.class, AuthorizationDeniedException.class})
    public ResponseEntity<ResultDTO> handleAccessDenied(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ResultDTO("You do not have permission.",
                false,
                ex.getMessage()));
    }

    // JSON xato bo'lsa (masalan, } yoki vergul noto‘g‘ri)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonParseError(HttpMessageNotReadableException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", 400);
        body.put("error", "Bad Request");
        body.put("message", "JSON parse error: " + ex.getMostSpecificCause().getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    // @Valid validatsiya xatolari
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", 400);
        body.put("error", "Validation Error");

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        body.put("messages", errors);
        return ResponseEntity.badRequest().body(body);
    }

    // Boshqa xatolar (umumiy)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", 500);
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        return ResponseEntity.internalServerError().body(body);
    }

}
