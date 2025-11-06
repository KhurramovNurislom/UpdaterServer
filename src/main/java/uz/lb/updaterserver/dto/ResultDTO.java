package uz.lb.updaterserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
public class ResultDTO {

    String message;
    boolean status;
    Object data;
    String e;

    public ResultDTO(String message) {
        this.message = message;
    }

    public ResultDTO(String message, boolean status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public ResultDTO(String message, boolean status, String e) {
        this.message = message;
        this.status = status;
        this.e = e;
    }

    public ResultDTO(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public ResultDTO message(String message) {
        return new ResultDTO(message);
    }

    public static ResultDTO success(Object data) {
        return new ResultDTO("success", true, data);
    }

    public ResultDTO success() {
        return new ResultDTO("success", true);
    }

    public static ResultDTO error(String message) {
        return new ResultDTO(message, false);
    }

    public ResultDTO error(Exception e) {
        return new ResultDTO("error", false, e.getMessage());
    }
}
