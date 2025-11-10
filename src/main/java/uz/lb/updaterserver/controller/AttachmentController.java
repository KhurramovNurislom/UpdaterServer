package uz.lb.updaterserver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.lb.updaterserver.config.CustomUserDetails;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.service.AttachmentService;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @GetMapping("/all")
    public ResponseEntity<ResultDTO> getAllAttachment(@AuthenticationPrincipal CustomUserDetails currentUser) {
        return attachmentService.getAllAttachment(currentUser);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<ResultDTO> saveAttachment(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                    @RequestParam("file") MultipartFile multipartFile) {
        return attachmentService.saveAttachment(currentUser, multipartFile);
    }

    @GetMapping("/preview/{hashId}")
    public ResponseEntity<FileUrlResource> preview(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable String hashId) {
        return attachmentService.preview(currentUser, hashId);
    }

    @GetMapping("/download/{hashId}")
    public ResponseEntity<FileUrlResource> download(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable String hashId) {
        return attachmentService.download(currentUser, hashId);
    }

    @DeleteMapping("/delete/{hashId}")
    public ResponseEntity<ResultDTO> delete(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable String hashId) {
        return attachmentService.removeAttachmentByHashId(currentUser, hashId);
    }
}
