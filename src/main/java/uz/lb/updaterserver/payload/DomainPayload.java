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
import uz.lb.updaterserver.enums.RoleEnum;

@Data
@ProjectedPayload
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DomainPayload {

    @NotBlank(message = "domain must not be empty.")
    String domain;

    GeneralStatus status;

    Boolean visible;

}
