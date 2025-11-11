package uz.lb.updaterserver.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.lb.updaterserver.config.CustomUserDetails;
import uz.lb.updaterserver.dto.ListDataDTO;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.entity.Application;
import uz.lb.updaterserver.entity.Attachment;
import uz.lb.updaterserver.entity.User;
import uz.lb.updaterserver.entity.Version;
import uz.lb.updaterserver.exception.AppBadRequestException;
import uz.lb.updaterserver.exception.AppForbiddenException;
import uz.lb.updaterserver.exception.AppItemNotFoundException;
import uz.lb.updaterserver.payload.VersionPayload;
import uz.lb.updaterserver.repository.ApplicationRepository;
import uz.lb.updaterserver.repository.AttachmentRepository;
import uz.lb.updaterserver.repository.UserRepository;
import uz.lb.updaterserver.repository.VersionRepository;
import uz.lb.updaterserver.utils.ConvertEntityToDTO;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VersionService {

    @Value("${domain.name}")
    private String domainName;

    private final VersionRepository versionRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final AttachmentRepository attachmentRepository;

    @Transactional
    public ResponseEntity<ResultDTO> saveVersion(CustomUserDetails currentUser, VersionPayload versionPayload) {

        User user = findUserById(currentUser.getId());

        Application application = applicationRepository.findById(versionPayload.getApplicationId()).orElseThrow(() -> {
            throw new AppItemNotFoundException("Application not found with this id = " + versionPayload.getApplicationId());
        });

        if (!application.getUser().getId().equals(user.getId())) {
            throw new AppForbiddenException("You do not have permission to modify this application. applicationId = " + versionPayload.getApplicationId());
        }
        List<Version> versions = versionRepository.findAllByVersion(versionPayload.getVersion());

        if (versions != null && !versions.isEmpty()) {
            for (Version v : versions) {
                if (v.getVersion().equalsIgnoreCase(versionPayload.getVersion()) &&
                        v.getApplication().getId().equals(application.getId())) {
                    throw new AppBadRequestException("This version of the application exists." +
                            "appplication = " + application.getName() + ". version = " + v.getVersion());
                }
            }
        }

        Attachment attachment = attachmentRepository.findAttachmentByHashId(versionPayload.getAttachmentHashId());

        if (attachment == null) {
            throw new AppItemNotFoundException("attachment not found with this hashId = " + versionPayload.getAttachmentHashId());
        }

        if (!attachment.getUser().getId().equals(user.getId())) {
            throw new AppForbiddenException("You do not have permission to modify this attachment. attachmentHashId = " + versionPayload.getAttachmentHashId());
        }

        Version version = versionRepository.save(Version.builder()
                .version(versionPayload.getVersion())
                .url(domainName + "/file/download/" + attachment.getHashId())
                .hash(generateHash(versionPayload.getVersion(), attachment.getHashId()))
                .releaseNotes(versionPayload.getReleaseNotes())
                .user(user)
                .application(application)
                .attachment(attachment)
                .createdByUserId(user.getId())
                .build());

        attachment.setVersion(version);
        attachment.setApplication(application);

        attachmentRepository.save(attachment);

        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.VersionToVersionDTO(version)));
    }


    @Transactional
    public ResponseEntity<ResultDTO> getAllVersionsByApplicationId(CustomUserDetails currentUser, Long applicationId) {
        List<Version> versions = versionRepository.getVersionByApplicationId(applicationId);
        if (versions == null) {
            throw new AppItemNotFoundException("Versions not found this applicationId = " + applicationId);
        }
        return ResponseEntity.ok(ResultDTO.success(ConvertEntityToDTO.VersionListWithUserToListDTO(versions)));
    }

    public ResponseEntity<ResultDTO> getVersionById(CustomUserDetails currentUser, Long id) {
        Version version = versionRepository.findVersionById(id);
        if (version == null) {
            throw new AppItemNotFoundException("Version not found this id = " + id);
        }
        return ResponseEntity.ok(ResultDTO.success(ConvertEntityToDTO.VersionToVersionDTO(version)));
    }

    public ResponseEntity<ResultDTO> getVersionsByVersion(CustomUserDetails currentUser, String version) {
        List<Version> versions = versionRepository.findAllByVersion(version);
        if (versions == null) {
            throw new AppItemNotFoundException("Versions not found this version = " + version);
        }
        return ResponseEntity.ok(ResultDTO.success(ConvertEntityToDTO.VersionListWithUserToListDTO(versions)));
    }

    public ResponseEntity<ResultDTO> getVersionsByApplicationName(CustomUserDetails currentUser, String name) {
        return null;
    }


    @Transactional
    public ResponseEntity<ResultDTO> updateVersionById(CustomUserDetails currentUser, Long id, VersionPayload versionPayload, MultipartFile multipartFile) {

        return null;
    }

    @Transactional
    public ResponseEntity<ResultDTO> deleteVersionById(CustomUserDetails id, Long versionPayload) {

        return null;
    }

    /*********************************************************************************************************************/
    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new AppItemNotFoundException("user not found with this id = " + userId);
        });
    }

    private String generateHash(String version, String attachmentHash) {
        return DigestUtils.sha256Hex(version + ":" + attachmentHash);
    }

}