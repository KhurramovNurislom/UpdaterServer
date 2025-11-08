package uz.lb.updaterserver.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.lb.updaterserver.config.CustomUserDetails;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.entity.Application;
import uz.lb.updaterserver.entity.User;
import uz.lb.updaterserver.entity.Version;
import uz.lb.updaterserver.enums.RoleEnum;
import uz.lb.updaterserver.exception.AppBadRequestException;
import uz.lb.updaterserver.exception.AppForbiddenException;
import uz.lb.updaterserver.exception.AppItemNotFoundException;
import uz.lb.updaterserver.payload.ApplicationPayload;
import uz.lb.updaterserver.repository.ApplicationRepository;
import uz.lb.updaterserver.repository.UserRepository;
import uz.lb.updaterserver.repository.VersionRepository;
import uz.lb.updaterserver.utils.ConvertEntityToDTO;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final VersionRepository versionRepository;

    @Transactional
    public ResponseEntity<ResultDTO> saveApplication(CustomUserDetails currentUser, ApplicationPayload applicationPayload) {

        User user = findUserById(currentUser.getId());

        Application application = applicationRepository.findApplicationByName(applicationPayload.getName());

        if (application != null) {
            throw new AppBadRequestException("Application with this name = " + applicationPayload.getName() + " exists");
        }

        application = applicationRepository.save(Application.builder()
                .name(applicationPayload.getName())
                .descriptions(applicationPayload.getDescriptions())
                .createdByUserId(currentUser.getId())
                .user(user)
                .build());

        List<Application> applicationList = new ArrayList<>();
        applicationList.addAll(user.getApplications());
        applicationList.add(application);

        user.setApplications(applicationList);
        userRepository.save(user);

        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.ApplicationToApplicationWithUserDTO(application)));
    }

    public ResponseEntity<ResultDTO> getAllApplication(CustomUserDetails currentUser) {
        List<Application> applications = applicationRepository.findAll();
        if (applications.isEmpty()) {
            throw new AppItemNotFoundException("applications does not exists");
        }
        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.ApplicationWithUserListToListDTO(applications)));
    }

    public ResponseEntity<ResultDTO> getApplicationById(CustomUserDetails currentUser, Long id) {
        return ResponseEntity.ok(new ResultDTO().success(
                ConvertEntityToDTO.ApplicationToApplicationWithUserDTO(findApplicationById(id))));
    }

    public ResponseEntity<ResultDTO> getApplicationByName(CustomUserDetails currentUser, String name) {

        Application application = applicationRepository.findApplicationByName(name);
        if (application == null) {
            throw new AppItemNotFoundException("application with this name = " + name + " was not found");
        }
        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.ApplicationToApplicationWithUserDTO(application)));
    }

    public ResponseEntity<ResultDTO> getApplicationsByName(CustomUserDetails currentUser, String name) {
        List<Application> applications = applicationRepository.findApplicationsByName(name);
        if (applications.isEmpty()) {
            throw new AppItemNotFoundException("applications does not exists");
        }
        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.ApplicationWithUserListToListDTO(applications)));
    }

    @Transactional
    public ResponseEntity<ResultDTO> updateApplicationById(CustomUserDetails currentUser, Long id, ApplicationPayload applicationPayload) {
        User updaterUser = findUserById(currentUser.getId());

        if (updaterUser.getRole().equals(RoleEnum.ROLE_ADMIN) || currentUser.getId() == id) {
            Application application = findApplicationById(id);
            Application applicationTemp = applicationRepository.findApplicationByName(applicationPayload.getName());

            if (applicationTemp != null && applicationTemp.getId() != id) {
                throw new AppBadRequestException("Application with this name = " + applicationTemp.getName() + " exists");
            }

            application.setName(applicationPayload.getName());
            application.setDescriptions(applicationPayload.getDescriptions());

            if (applicationPayload.getVersionIds() != null && !applicationPayload.getVersionIds().isEmpty())
                application.setVersions(findVersions(applicationPayload.getVersionIds()));

            application.setUpdatedByUserId(currentUser.getId());
            application = applicationRepository.save(application);

            return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.ApplicationToApplicationDTO(application)));
        }

        throw new AppForbiddenException("Forbidden...");
    }

    @Transactional
    public ResponseEntity<ResultDTO> deleteApplicationById(CustomUserDetails currentUser, Long id) {
        User deleter = findUserById(currentUser.getId());
        if (deleter.getRole().equals(RoleEnum.ROLE_ADMIN) || currentUser.getId() == id) {
            Application application = findApplicationById(id);
            applicationRepository.delete(application);
            return ResponseEntity.ok(new ResultDTO().success("application deleted, id = " + id));
        }
        throw new AppForbiddenException("Forbidden...");
    }

    /*********************************************************************************************************************/
    private List<Version> findVersions(List<Long> ids) {
        List<Version> versions = new ArrayList<>();
        for (Long i : ids) {
            versions.add(versionRepository.findById(i).orElseThrow(() -> {
                throw new AppItemNotFoundException("version not found with this id = " + i);
            }));
        }
        return versions;
    }

    private Application findApplicationById(Long id) {
        return applicationRepository.findById(id).orElseThrow(() -> {
            throw new AppItemNotFoundException("application not found with this id = " + id);
        });
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new AppItemNotFoundException("user not found with this id = " + userId);
        });
    }

}