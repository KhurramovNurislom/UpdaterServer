package uz.lb.updaterserver.service;


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
import uz.lb.updaterserver.dto.VersionDTO;
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


    @Value("${upload.folder}")
    private String uploadFolder;


    private final VersionRepository versionRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final AttachmentRepository attachmentRepository;


    public ResponseEntity<ResultDTO> saveVersion(CustomUserDetails currentUser, VersionPayload versionPayload) {

        Version version = versionRepository.findVersionByVersion(versionPayload.getVersion());

        if (version != null) {
            throw new AppBadRequestException("Version with this version = " + versionPayload.getVersion() + " exists");
        }

        User user = findUserById(currentUser.getId());

        Application application = applicationRepository.findById(versionPayload.getApplicationId()).orElseThrow(() -> {
            throw new AppItemNotFoundException("Application not found with this id = " + versionPayload.getApplicationId());
        });

        if (!application.getUser().getId().equals(user.getId())) {
            throw new AppForbiddenException("You do not have permission to modify this application. applicationId = " + versionPayload.getApplicationId());
        }

        Attachment attachment = attachmentRepository.findByHashId(versionPayload.getAttachmentHashId());

        if (attachment != null) {
            throw new AppItemNotFoundException("attachment not found with this hashId = " + versionPayload.getAttachmentHashId());
        }

        if (!attachment.getUser().getId().equals(user.getId())) {
            throw new AppForbiddenException("You do not have permission to modify this attachment. attachmentHashId = " + versionPayload.getAttachmentHashId());
        }

        version = versionRepository.save(Version.builder()
                .version(versionPayload.getVersion())
                .url(attachment.getLink())
                .hash(generateHash(versionPayload.getVersion(), attachment.getLink()))
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

    public ResponseEntity<ResultDTO> getAllVersionsByApplicationId(CustomUserDetails currentUser, Long applicationId) {
        List<Version> versions = versionRepository.getVersionByApplicationId(applicationId);
        if (versions == null) {
            throw new AppItemNotFoundException("Versions not found this applicationId = " + applicationId);
        }
        ListDataDTO listDTO = ConvertEntityToDTO.VersionListToListDTO(versions);

        return ResponseEntity.ok(ResultDTO.success(listDTO));
    }

    public ResponseEntity<ResultDTO> getVersionById(CustomUserDetails currentUser, Long id) {

        return null;
    }

    public ResponseEntity<ResultDTO> getVersionByVersion(CustomUserDetails currentUser, String version) {
        return null;
    }

    public ResponseEntity<ResultDTO> getVersionsByApplicationName(CustomUserDetails currentUser, String name) {
        return null;
    }

    public ResponseEntity<ResultDTO> updateVersionById(CustomUserDetails currentUser, Long id, VersionPayload versionPayload, MultipartFile multipartFile) {

        return null;
    }

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