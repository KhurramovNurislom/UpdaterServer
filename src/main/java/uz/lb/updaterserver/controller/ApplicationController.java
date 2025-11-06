package uz.lb.updaterserver.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<ResultDTO> saveApplication(@RequestBody ApplicationPayload applicationPayload) {
        return applicationService.saveApplication(applicationPayload);
    }

    @GetMapping("/all")
    public ResponseEntity<ResultDTO> getAllApplication() {
        return applicationService.getAllApplication();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO> getApplicationById(@PathVariable(value = "id") Long id) {
        return applicationService.getApplicationById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO> updateApplicationById(@PathVariable(value = "id") Long id, @RequestBody ApplicationPayload applicationPayload) {
        return applicationService.updateApplicationById(id, applicationPayload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO> deleteApplicationById(@PathVariable(value = "id") Long id, @RequestBody ApplicationPayload applicationPayload) {
        return applicationService.deleteApplicationById(id, applicationPayload);
    }
}
