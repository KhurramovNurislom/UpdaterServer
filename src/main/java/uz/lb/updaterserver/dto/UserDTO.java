package uz.lb.updaterserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.lb.updaterserver.entity.Application;
import uz.lb.updaterserver.enums.GeneralStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
public class UserDTO {
    String id;
    String login;
    String role;
    List<ApplicationDTO> applications;
    String createdUserId;
    String updatedUserId;
}
