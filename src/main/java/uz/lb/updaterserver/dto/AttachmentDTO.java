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
public class AttachmentDTO {
    String id;
    String fileName;
    String hashId;
    String contentType;
    String extension;
//    String link;
    Float fileSize;

    String userId;
    String applicationId;
    String versionId;

    String createdUserId;
    String updatedUserId;

}
