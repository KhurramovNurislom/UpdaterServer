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
public class VersionDTO {

    Long id;

    String version;
    String url;
    String hash;
    String releaseNotes;

    UserDTO userDTO;
    Long applicationId;
    AttachmentDTO attachmentDTO;

    Long createdUserId;
    Long updatedUserId;
}
