package uz.lb.updaterserver.utils;

import uz.lb.updaterserver.dto.*;
import uz.lb.updaterserver.entity.*;

import java.util.ArrayList;
import java.util.List;

public class ConvertEntityToDTO {

    public static UserDTO UserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        return userDTO;
    }


    public static ApplicationDTO ApplicationToApplicationDTO(Application application) {
        ApplicationDTO applicationDTO = new ApplicationDTO();

        return applicationDTO;
    }

    public static VersionDTO VersionToVersionDTO(Version version) {
        VersionDTO versionDTO = new VersionDTO();

        return versionDTO;
    }


    public static AttachmentDTO AttachmentToAttachmentDTO(Attachment attachment) {

        AttachmentDTO attachmentDTO = new AttachmentDTO();

        attachmentDTO.setId(attachment.getId());
        attachmentDTO.setHashId(attachment.getHashId());
        attachmentDTO.setContentType(attachment.getContentType());

        attachmentDTO.setFileName(attachment.getFileName());
        attachmentDTO.setExtension(attachment.getExtension());
        attachmentDTO.setLink(attachment.getLink());
        attachmentDTO.setFileSize(attachment.getFileSize());

        return attachmentDTO;
    }


    public static ListDataDTO ListToListDTO(List<Object> objects) {

        List<Object> objectDTOs = new ArrayList<>();

        if (objects.get(0) instanceof User) {
            for (Object object : objects) {
                User user = (User) object;
                objectDTOs.add(UserToUserDTO(user));
            }
        } else if (objects.get(0) instanceof Application) {
            for (Object object : objects) {
                Application application = (Application) object;
                objectDTOs.add(ApplicationToApplicationDTO(application));
            }
        } else if (objects.get(0) instanceof Version) {
            for (Object object : objects) {
                Version version = (Version) object;
                objectDTOs.add(VersionToVersionDTO(version));
            }
        } else if (objects.get(0) instanceof Attachment) {
            for (Object object : objects) {
                Attachment attachment = (Attachment) object;
                objectDTOs.add(AttachmentToAttachmentDTO(attachment));
            }
        }

        return new ListDataDTO(objects, objects.size());
    }


}
