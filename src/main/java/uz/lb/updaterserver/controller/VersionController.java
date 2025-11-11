package uz.lb.updaterserver.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.lb.updaterserver.config.CustomUserDetails;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.payload.VersionPayload;
import uz.lb.updaterserver.service.VersionService;

import java.io.DataInput;
import java.io.IOException;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/version")
@RequiredArgsConstructor
public class VersionController {

    private final VersionService versionService;

    @PostMapping( "/save")
    public ResponseEntity<ResultDTO> saveVersion(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                 @RequestBody VersionPayload versionPayload) {
        return versionService.saveVersion(currentUser,versionPayload);
    }

    @GetMapping("/versions-by-application-id/{applicationId}")
    public ResponseEntity<ResultDTO> getAllVersionsByApplicationId(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                                   @PathVariable("applicationId") String applicationId) {
        return versionService.getAllVersionsByApplicationId(currentUser, applicationId);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO> getVersionById(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                    @PathVariable(value = "id") String id) {
        return versionService.getVersionById(currentUser, id);
    }

    @GetMapping("/version")
    public ResponseEntity<ResultDTO> getVersionByVersion(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                         @RequestParam(value = "version") String version) {
        return versionService.getVersionsByVersion(currentUser, version);
    }

    @GetMapping("/versions-by-version")
    public ResponseEntity<ResultDTO> getVersionsByApplicationName(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                                  @RequestParam(value = "version") String version) {
        return versionService.getVersionsByApplicationName(currentUser, version);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO> updateVersionById(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                       @PathVariable(value = "id") String id,
                                                       @RequestBody VersionPayload versionPayload,
                                                       @RequestParam(value = "file") MultipartFile multipartFile) {
        return versionService.updateVersionById(currentUser, id, versionPayload, multipartFile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO> deleteVersionById(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                       @PathVariable(value = "id") String id) {
        return versionService.deleteVersionById(currentUser, id);
    }

}
