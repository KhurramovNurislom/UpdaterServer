package uz.lb.updaterserver.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.payload.ApplicationPayload;
import uz.lb.updaterserver.repository.UserRepository;
import uz.lb.updaterserver.repository.ApplicationRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;


    public ResponseEntity<ResultDTO> saveApplication(ApplicationPayload applicationPayload) {
        return null;
    }

    public ResponseEntity<ResultDTO> getAllApplication() {

        return null;
    }

    public ResponseEntity<ResultDTO> getApplicationById(Long id) {

        return null;
    }

    public ResponseEntity<ResultDTO> updateApplicationById(Long id, ApplicationPayload applicationPayload) {

        return null;
    }

    public ResponseEntity<ResultDTO> deleteApplicationById(Long id, ApplicationPayload applicationPayload) {

        return null;
    }

}