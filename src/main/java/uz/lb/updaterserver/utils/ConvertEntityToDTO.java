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
        userDTO.setRole(user.getRole().toString());

        if (user.getApplications() != null && !user.getApplications().isEmpty()) {
            ListDataDTO listData = ApplicationListToListDTO(user.getApplications());
            userDTO.setApplications((List<ApplicationDTO>) listData.getData());
        }

        if (user.getCreatedByUserId() != null)
            userDTO.setCreatedUserId(user.getCreatedByUserId());

        if (user.getUpdatedByUserId() != null)
            userDTO.setUpdatedUserId(user.getUpdatedByUserId());

        return userDTO;
    }

    public static ListDataDTO UserListToListDTO(List<User> users) {
        List<UserDTO> resultList = new ArrayList<>();
        for (User user : users) {
            resultList.add(UserToUserDTO(user));
        }
        return new ListDataDTO(resultList ,resultList.size());
    }

    public static ApplicationDTO ApplicationToApplicationDTO(Application application) {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setId(application.getId());
        applicationDTO.setName(application.getName());
        applicationDTO.setDescriptions(application.getDescriptions());
        if (application.getVersions() != null && !application.getVersions().isEmpty()) {
            ListDataDTO listData = VersionListToListDTO(application.getVersions());
            applicationDTO.setVersions((List<VersionDTO>) listData.getData());
        }
        if (application.getCreatedByUserId() != null)
            applicationDTO.setCreatedUserId(application.getCreatedByUserId());
        if (application.getUpdatedByUserId() != null)
            applicationDTO.setUpdatedUserId(application.getUpdatedByUserId());
        return applicationDTO;
    }


    public static ApplicationDTO ApplicationToApplicationWithUserDTO(Application application) {
        ApplicationDTO applicationDTO = ApplicationToApplicationDTO(application);
        applicationDTO.setUserDTO(UserToUserDTO(application.getUser()));
        return applicationDTO;
    }

    public static ListDataDTO ApplicationListToListDTO(List<Application> applications) {
        List<ApplicationDTO> resultList = new ArrayList<>();
        for (Application application : applications) {
            resultList.add(ApplicationToApplicationDTO(application));
        }
        return new ListDataDTO(resultList ,resultList.size());
    }

    public static ListDataDTO ApplicationWithUserListToListDTO(List<Application> applications) {
        List<ApplicationDTO> resultList = new ArrayList<>();
        for (Application application : applications) {
            resultList.add(ApplicationToApplicationWithUserDTO(application));
        }
        return new ListDataDTO(resultList ,resultList.size());
    }


    public static VersionDTO VersionToVersionDTO(Version version) {
        VersionDTO versionDTO = new VersionDTO();
        versionDTO.setId(version.getId());
        versionDTO.setVersion(version.getVersion());
        versionDTO.setUrl(version.getUrl());
        versionDTO.setHash(version.getHash());
        versionDTO.setReleaseNotes(version.getReleaseNotes());
        versionDTO.setApplicationId(version.getApplication().getId());

        if (version.getAttachment() != null)
            versionDTO.setAttachmentDTO(AttachmentToAttachmentDTO(version.getAttachment()));

        if (version.getCreatedByUserId() != null)
            versionDTO.setCreatedUserId(version.getCreatedByUserId());

        if (version.getCreatedByUserId() != null)
            versionDTO.setUpdatedUserId(version.getUpdatedByUserId());

        return versionDTO;
    }

    public static VersionDTO VersionWithUserToVersionDTO(Version version) {
        VersionDTO versionDTO = VersionToVersionDTO(version);
        versionDTO.setUserDTO(UserToUserDTO(version.getUser()));
        return versionDTO;
    }

    public static ListDataDTO VersionListWithUserToListDTO(List<Version> versions) {
        List<VersionDTO> resultList = new ArrayList<>();
        for (Version version : versions) {
            resultList.add(VersionWithUserToVersionDTO(version));
        }
        return new ListDataDTO(resultList ,resultList.size());
    }

    public static ListDataDTO VersionListToListDTO(List<Version> versions) {
        List<VersionDTO> resultList = new ArrayList<>();
        for (Version version : versions) {
            resultList.add(VersionToVersionDTO(version));
        }
        return new ListDataDTO(resultList ,resultList.size());
    }

    public static AttachmentDTO AttachmentToAttachmentDTO(Attachment attachment) {

        AttachmentDTO attachmentDTO = new AttachmentDTO();

        attachmentDTO.setId(attachment.getId());
        attachmentDTO.setFileName(attachment.getFileName());

        attachmentDTO.setHashId(attachment.getHashId());
        attachmentDTO.setContentType(attachment.getContentType());
        attachmentDTO.setExtension(attachment.getExtension());
//        attachmentDTO.setLink(attachment.getLink());
        attachmentDTO.setFileSize(attachment.getFileSize());

        if (attachment.getUser() != null)
            attachmentDTO.setUserId(attachment.getUser().getId());

        if (attachment.getApplication() != null)
            attachmentDTO.setApplicationId(attachment.getApplication().getId());

        if (attachment.getVersion() != null)
            attachmentDTO.setVersionId(attachment.getVersion().getId());

        if (attachment.getCreatedByUserId() != null)
            attachmentDTO.setCreatedUserId(attachment.getCreatedByUserId());

        if (attachment.getUpdatedByUserId() != null)
            attachmentDTO.setUpdatedUserId(attachment.getUpdatedByUserId());

        return attachmentDTO;
    }

    public static ListDataDTO AttachmentListToAttachmentDTOList(List<Attachment> attachments) {
        List<AttachmentDTO> resultList = new ArrayList<>();
        for (Attachment attachment : attachments) {
            resultList.add(AttachmentToAttachmentDTO(attachment));
        }
        return new ListDataDTO(resultList ,resultList.size());
    }
}
