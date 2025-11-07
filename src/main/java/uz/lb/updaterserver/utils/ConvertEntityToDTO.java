package uz.lb.updaterserver.utils;

import uz.lb.updaterserver.dto.*;
import uz.lb.updaterserver.entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConvertEntityToDTO {

    public static UserDTO UserToUserDTO(User user) {

        UserDTO userDTO = new UserDTO();

        System.out.println("user.role -> " + user.getRole());

        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setRole(user.getRole().toString());

        if (user.getApplications() != null) {
            userDTO.setApplications((List<ApplicationDTO>) ApplicationListToListDTO(user.getApplications()));
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

        return new ListDataDTO(resultList, resultList.size());
    }

    public static ApplicationDTO ApplicationToApplicationDTO(Application application) {

        ApplicationDTO applicationDTO = new ApplicationDTO();

        applicationDTO.setId(application.getId());
        applicationDTO.setName(application.getName());
        applicationDTO.setDescriptions(application.getDescriptions());

        if (application.getVersions() != null)
            applicationDTO.setVersions((List<VersionDTO>) VersionListToListDTO(application.getVersions()));

        if (application.getCreatedByUserId() != null)
            applicationDTO.setCreatedUserId(application.getCreatedByUserId());

        if (application.getUpdatedByUserId() != null)
            applicationDTO.setUpdatedUserId(application.getUpdatedByUserId());

        return applicationDTO;
    }


    public static ListDataDTO ApplicationListToListDTO(List<Application> applications) {
        List<ApplicationDTO> resultList = new ArrayList<>();
        for (Application application : applications) {
            resultList.add(ApplicationToApplicationDTO(application));
        }

        ListDataDTO listDataDTO = new ListDataDTO();
        listDataDTO.setData(resultList);
        listDataDTO.setSize(resultList.size());
        return listDataDTO;
    }


    public static VersionDTO VersionToVersionDTO(Version version) {

        VersionDTO versionDTO = new VersionDTO();

        versionDTO.setVersion(version.getVersion());
        versionDTO.setUrl(versionDTO.getUrl());
        versionDTO.setHash(versionDTO.getHash());
        versionDTO.setReleaseNotes(version.getReleaseNotes());

        versionDTO.setUserId(version.getUser().getId());
        versionDTO.setApplicationId(version.getApplication().getId());
        versionDTO.setAttachmentHashID(version.getAttachment().getHashId());

        if (version.getCreatedByUserId() != null)
            versionDTO.setCreatedUserId(version.getCreatedByUserId());

        if (version.getCreatedByUserId() != null)
            versionDTO.setUpdatedUserId(version.getUpdatedByUserId());

        return versionDTO;
    }


    public static ListDataDTO VersionListToListDTO(List<Version> versions) {
        List<VersionDTO> resultList = new ArrayList<>();
        for (Version version : versions) {
            resultList.add(VersionToVersionDTO(version));
        }
        ListDataDTO listDataDTO = new ListDataDTO();
        listDataDTO.setData(resultList);
        listDataDTO.setSize(resultList.size());
        return listDataDTO;
    }

    public static AttachmentDTO AttachmentToAttachmentDTO(Attachment attachment) {

        AttachmentDTO attachmentDTO = new AttachmentDTO();

        attachmentDTO.setId(attachment.getId());
        attachmentDTO.setFileName(attachment.getFileName());

        attachmentDTO.setHashId(attachment.getHashId());
        attachmentDTO.setContentType(attachment.getContentType());
        attachmentDTO.setExtension(attachment.getExtension());
        attachmentDTO.setLink(attachment.getLink());
        attachmentDTO.setFileSize(attachment.getFileSize());


        if (attachment.getVersion() != null)
            attachmentDTO.setVersionId(attachment.getVersion().getId());

        if (attachment.getApplication() != null)
            attachmentDTO.setApplicationId(attachment.getApplication().getId());

        if (attachment.getCreatedByUserId() != null)
            attachmentDTO.setCreatedUserId(attachment.getCreatedByUserId());

        if (attachment.getUpdatedByUserId() != null)
            attachmentDTO.setUpdatedUserId(attachment.getUpdatedByUserId());

        return attachmentDTO;
    }


}
