package uz.lb.updaterserver.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.payload.VersionPayload;
import uz.lb.updaterserver.repository.UserRepository;
import uz.lb.updaterserver.repository.VersionRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class VersionService {

    private final VersionRepository versionRepository;


    public ResponseEntity<ResultDTO> saveVersion(VersionPayload versionPayload) {
        return null;
    }

    public ResponseEntity<ResultDTO> getAllVersion() {

        return null;
    }

    public ResponseEntity<ResultDTO> getVersionById(Long id) {

        return null;
    }

    public ResponseEntity<ResultDTO> updateVersionById(Long id, VersionPayload versionPayload) {

        return null;
    }

    public ResponseEntity<ResultDTO> deleteVersionById(Long id, VersionPayload versionPayload) {

        return null;
    }
}