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

    @NotBlank(message = "version must not be empty.")
    String version;

    @NotBlank(message = "applicationId must not be empty.")
    String applicationId;

    @NotBlank(message = "releaseNotes must not be empty.")
    String releaseNotes;

    @NotBlank(message = "attachmentHashId must not be empty.")
    String attachmentHashId;

}
