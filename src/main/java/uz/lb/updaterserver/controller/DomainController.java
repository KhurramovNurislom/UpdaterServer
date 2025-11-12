package uz.lb.updaterserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.lb.updaterserver.config.CustomUserDetails;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.payload.DomainPayload;
import uz.lb.updaterserver.service.DomainService;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/domain")
@RequiredArgsConstructor
public class DomainController {

    private final DomainService domainService;

    @GetMapping("/all")
    public ResponseEntity<ResultDTO> getAllDomain() {
        return domainService.getAllDomain();
    }

    @PostMapping("/save")
    public ResponseEntity<ResultDTO> saveDomain(@AuthenticationPrincipal CustomUserDetails currentUser, @RequestBody DomainPayload domainPayload) {
        return domainService.saveDomain(currentUser, domainPayload);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO> getDomainById(@PathVariable(value = "id") String id) {
        return domainService.getDomainById(id);
    }

    @GetMapping("/domain")
    public ResponseEntity<ResultDTO> getDomainByDomain(@RequestBody DomainPayload domainPayload) {
        return domainService.getDomainByDomain(domainPayload);
    }

    @GetMapping("/for-application")
    public ResponseEntity<ResultDTO> getDomainForApplication() {
        return domainService.getDomainForApplications();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO> updateDomainById(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable(value = "id") String id, @RequestBody DomainPayload domainPayload) {
        return domainService.updateDomainById(currentUser, id, domainPayload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO> deleteDomainById(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable(value = "id") String id) {
        return domainService.deleteDomainById(currentUser, id);
    }
}
