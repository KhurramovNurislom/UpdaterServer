package uz.lb.updaterserver.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.lb.updaterserver.config.CustomUserDetails;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.entity.Attachment;
import uz.lb.updaterserver.entity.User;
import uz.lb.updaterserver.exception.AppForbiddenException;
import uz.lb.updaterserver.exception.AppItemNotFoundException;
import uz.lb.updaterserver.repository.AttachmentRepository;
import uz.lb.updaterserver.repository.UserRepository;
import uz.lb.updaterserver.utils.ConvertEntityToDTO;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final UserRepository userRepository;

    @Value("${upload.folder}")
    private String uploadFolder;

    public ResponseEntity<ResultDTO> getAllAttachment(CustomUserDetails currentUser) {
        List<Attachment> attachments = attachmentRepository.findAll(Sort.by("createdAt"));
        if (attachments.isEmpty()) {
            log.error("AttachmentService.getAllAttachment => {}", "attachments does not exist");
            throw new AppItemNotFoundException("attachment does not exist");
        }
        ResultDTO resultDTO = new ResultDTO();
        return ResponseEntity.ok(resultDTO.success(ConvertEntityToDTO.AttachmentListToAttachmentDTOList(attachments)));
    }


    @Transactional
    public ResponseEntity<ResultDTO> saveAttachment(CustomUserDetails currentUser, MultipartFile multipartFile) {

        User user = findUserById(currentUser.getId());

        Attachment attachment = new Attachment();

        attachment.setContentType(multipartFile.getContentType());
        attachment.setFileName(multipartFile.getOriginalFilename().toLowerCase());
        attachment.setFileSize(multipartFile.getSize() / (1024f));
        attachment.setHashId(UUID.randomUUID().toString());
        attachment.setExtension(getExtension(multipartFile.getOriginalFilename().toLowerCase()));
        LocalDate date = LocalDate.now();
        String uploadPath = String.format("%s/%d/%d/%d/%s", uploadFolder, date.getYear(), date.getMonthValue(), date.getDayOfMonth(), attachment.getExtension());

        File file = new File(uploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        attachment.setUploadPath(uploadPath);
        attachment.setLink(String.format("%s/%s.%s", file.getAbsolutePath(), attachment.getHashId(), attachment.getExtension()));
        attachment.setUser(user);
        attachment.setCreatedByUserId(currentUser.getId());
        try {
            multipartFile.transferTo(new File(attachment.getLink()));
        } catch (IOException e) {
            log.error("AttachmentService.saveAttachment => {}", e.getMessage());
            throw new RuntimeException(e);
        }
        attachmentRepository.save(attachment);

        ResultDTO resultDTO = new ResultDTO();

        return ResponseEntity.ok(resultDTO.success(ConvertEntityToDTO.AttachmentToAttachmentDTO(attachment)));
    }


    public ResponseEntity<FileUrlResource> preview(CustomUserDetails currentUser, String hashId) {
        ResultDTO result = findAttachmentByHashId(hashId);
        if (result.isStatus()) {
            Attachment attachment = (Attachment) result.getData();
            try {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName="
                                + URLEncoder.encode(attachment.getFileName()))
                        .contentType(
                                MediaType.parseMediaType(attachment.getContentType()))
                        .body(new FileUrlResource(attachment.getLink()));

            } catch (MalformedURLException e) {
                log.error("AttachmentService.preview.MalformedURLException => {}", e.getMessage());
//                throw new RuntimeException(e.getMessage());
            }
        } else {
            log.error("AttachmentService.preview => {}", result.getMessage());
//            throw new RuntimeException(result.getMessage());
        }
        return null;
    }

    public ResponseEntity<FileUrlResource> download(CustomUserDetails currentUser, String hashId) {
        ResultDTO result = findAttachmentByHashId(hashId);
        if (result.isStatus()) {
            Attachment attachment = (Attachment) result.getData();
            try {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName="
                        + URLEncoder.encode(attachment.getFileName())).contentType(MediaType.parseMediaType(attachment.getContentType())).body(new FileUrlResource(attachment.getLink()));

            } catch (MalformedURLException e) {
                log.error("AttachmentController.preview.MalformedURLException => {}", e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        } else {
            log.error("AttachmentService.download => {}", result.getMessage());
            throw new RuntimeException(result.getMessage());
        }
    }


    public ResponseEntity<ResultDTO> removeAttachmentByHashId(CustomUserDetails currentUser, String hashId) {

        /** bu yerda role admin bo'lmasa delete emas visible false qilish zarur */

        try {
            Attachment attachment = attachmentRepository.findAttachmentByHashId(hashId);

            new File(attachment.getLink()).delete();
            ResultDTO resultDTO = new ResultDTO();
            return ResponseEntity.ok(new ResultDTO().success(resultDTO.success(attachment)));
        } catch (Exception e) {
            log.error("AttachmentService.removeAttachmentByHashId => {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO().error(e));
        }
    }

    /***************************************************************************************************************/
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public ResultDTO findAttachmentByHashId(String hashId) {
        Attachment attachment = attachmentRepository.findAttachmentByHashId(hashId);

        ResultDTO resultDTO = new ResultDTO();
        if (attachment == null) return resultDTO.error("hashId not found");
        else return resultDTO.success(attachment);
    }

    private User findUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new AppItemNotFoundException("user not found with this id = " + userId);
        });
    }
}
