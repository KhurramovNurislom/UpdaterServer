package uz.lb.updaterserver.payload;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.web.ProjectedPayload;

@Data
@ProjectedPayload
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VersionPayload {

    @NotBlank(message = "applicationId must not be empty.")
    Long applicationId;

    @NotBlank(message = "Version must not be empty.")
    String version;

}
