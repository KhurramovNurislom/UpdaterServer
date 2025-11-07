package uz.lb.updaterserver.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.web.ProjectedPayload;
import uz.lb.updaterserver.annotation.StrongPassword;
import uz.lb.updaterserver.enums.GeneralStatus;

@Data
@ProjectedPayload
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPayload {

    @NotBlank(message = "Login must not be empty.")
    String login;

    @NotBlank(message = "Password must not be empty.")
    @StrongPassword(message = "Password must not contain spaces.")
    String password;

    GeneralStatus status;

    Boolean visible;


}
