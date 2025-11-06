package uz.lb.updaterserver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.service.AttachmentService;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @GetMapping("/get-all")
    @PreAuthorize("hasAuthority('GET_ALL_FILES')")
    public ResponseEntity<ResultDTO> getAllAttachment() {
        return attachmentService.getAllAttachment();
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('SAVE_FILE')")
    public ResponseEntity<ResultDTO> saveAttachment(@Valid @RequestParam(value = "file") MultipartFile multipartFile) {
        return attachmentService.saveAttachment(multipartFile);
    }

    @GetMapping("/preview/{hashId}")
    @PreAuthorize("hasAuthority('PREVIEW_FILE')")
    public ResponseEntity<FileUrlResource> preview(@PathVariable String hashId) {
        return attachmentService.preview(hashId);
    }

    @GetMapping("/download/{hashId}")
    @PreAuthorize("hasAuthority('DOWNLOAD_FILE')")
    public ResponseEntity<FileUrlResource> download(@PathVariable String hashId) {
        return attachmentService.download(hashId);
    }

    @DeleteMapping("/delete/{hashId}")
    @PreAuthorize("hasAuthority('DELETE_FILE')")
    public ResponseEntity<ResultDTO> delete(@PathVariable String hashId) {
        return attachmentService.removeAttachmentByHashId(hashId);
    }
}
