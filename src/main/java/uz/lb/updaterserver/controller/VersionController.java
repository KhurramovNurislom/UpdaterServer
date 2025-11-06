package uz.lb.updaterserver.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.payload.VersionPayload;
import uz.lb.updaterserver.service.VersionService;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/version")
@RequiredArgsConstructor
public class VersionController {

    private final VersionService versionService;

    @PostMapping("/save")
    public ResponseEntity<ResultDTO> saveVersion(@RequestBody VersionPayload versionPayload) {
        return versionService.saveVersion(versionPayload);
    }

    @GetMapping("/all")
    public ResponseEntity<ResultDTO> getAllVersion() {
        return versionService.getAllVersion();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO> getVersionById(@PathVariable(value = "id") Long id) {
        return versionService.getVersionById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO> updateVersionById(@PathVariable(value = "id") Long id, @RequestBody VersionPayload versionPayload) {
        return versionService.updateVersionById(id, versionPayload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO> deleteVersionById(@PathVariable(value = "id") Long id, @RequestBody VersionPayload versionPayload) {
        return versionService.deleteVersionById(id, versionPayload);
    }

}
