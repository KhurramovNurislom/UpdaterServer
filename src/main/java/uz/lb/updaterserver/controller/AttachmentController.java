package uz.lb.updaterserver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.ResponseEntity;
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
        public ResponseEntity<ResultDTO> getAllAttachment() {
        return attachmentService.getAllAttachment();
    }

    @PostMapping("/save")
        public ResponseEntity<ResultDTO> saveAttachment(@Valid @RequestParam(value = "file") MultipartFile multipartFile) {
        return attachmentService.saveAttachment(multipartFile);
    }

    @GetMapping("/preview/{hashId}")
        public ResponseEntity<FileUrlResource> preview(@PathVariable String hashId) {
        return attachmentService.preview(hashId);
    }

    @GetMapping("/download/{hashId}")
        public ResponseEntity<FileUrlResource> download(@PathVariable String hashId) {
        return attachmentService.download(hashId);
    }

    @DeleteMapping("/delete/{hashId}")
        public ResponseEntity<ResultDTO> delete(@PathVariable String hashId) {
        return attachmentService.removeAttachmentByHashId(hashId);
    }
}
