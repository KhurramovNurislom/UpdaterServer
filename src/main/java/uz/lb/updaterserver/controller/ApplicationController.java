package uz.lb.updaterserver.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.lb.updaterserver.config.CustomUserDetails;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.payload.ApplicationPayload;
import uz.lb.updaterserver.service.ApplicationService;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/save")
    public ResponseEntity<ResultDTO> saveApplication(@AuthenticationPrincipal CustomUserDetails currentUser, @RequestBody ApplicationPayload applicationPayload) {
        return applicationService.saveApplication(currentUser, applicationPayload);
    }

    @GetMapping("/all")
    public ResponseEntity<ResultDTO> getAllApplication(@AuthenticationPrincipal CustomUserDetails currentUser) {
        return applicationService.getAllApplication(currentUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO> getApplicationById(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable(value = "id") Long id) {
        return applicationService.getApplicationById(currentUser, id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO> updateApplicationById(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable(value = "id") Long id, @RequestBody ApplicationPayload applicationPayload) {
        return applicationService.updateApplicationById(currentUser, id, applicationPayload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO> deleteApplicationById(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable(value = "id") Long id) {
        return applicationService.deleteApplicationById(currentUser, id);
    }
}
