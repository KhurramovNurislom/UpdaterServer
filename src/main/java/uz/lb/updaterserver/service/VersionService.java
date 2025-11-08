package uz.lb.updaterserver.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.lb.updaterserver.config.CustomUserDetails;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.entity.Application;
import uz.lb.updaterserver.entity.User;
import uz.lb.updaterserver.exception.AppBadRequestException;
import uz.lb.updaterserver.payload.VersionPayload;
import uz.lb.updaterserver.repository.ApplicationRepository;
import uz.lb.updaterserver.repository.AttachmentRepository;
import uz.lb.updaterserver.repository.UserRepository;
import uz.lb.updaterserver.repository.VersionRepository;
import uz.lb.updaterserver.utils.ConvertEntityToDTO;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VersionService {

    private final VersionRepository versionRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final AttachmentRepository attachmentRepository;


    public ResponseEntity<ResultDTO> saveVersion(VersionPayload versionPayload) {

//
//        User user = findUserById(currentUser.getId());
//
//        Application application = applicationRepository.findApplicationByName(applicationPayload.getName());
//
//        if (application != null) {
//            throw new AppBadRequestException("Application with this name = " + applicationPayload.getName() + " exists");
//        }
//
//        application = applicationRepository.save(Application.builder()
//                .name(applicationPayload.getName())
//                .descriptions(applicationPayload.getDescriptions())
//                .createdByUserId(currentUser.getId())
//                .user(user)
//                .build());
//
//        List<Application> applicationList = new ArrayList<>();
//        applicationList.addAll(user.getApplications());
//        applicationList.add(application);
//
//        user.setApplications(applicationList);
//        userRepository.save(user);
//
//        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.ApplicationToApplicationWithUserDTO(application)));
//

        return null;
    }

    public ResponseEntity<ResultDTO> getAllVersion() {

        return null;
    }

    public ResponseEntity<ResultDTO> getVersionById(Long id) {

        return null;
    }

    public ResponseEntity<ResultDTO> getVersionByVersion(CustomUserDetails currentUser, String version) {
        return null;
    }

    public ResponseEntity<ResultDTO> getVersionsByVersion(CustomUserDetails currentUser, String version) {
        return null;
    }

    public ResponseEntity<ResultDTO> updateVersionById(Long id, VersionPayload versionPayload) {

        return null;
    }

    public ResponseEntity<ResultDTO> deleteVersionById(Long id, VersionPayload versionPayload) {

        return null;
    }

}