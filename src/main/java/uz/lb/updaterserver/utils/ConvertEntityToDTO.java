package uz.lb.updaterserver.utils;

import uz.lb.updaterserver.dto.*;
import uz.lb.updaterserver.entity.*;

import java.util.ArrayList;
import java.util.List;

public class ConvertEntityToDTO {



    public static AttachmentDTO AttachmentToAttachmentDTO(Attachment attachment) {

        AttachmentDTO attachmentDTO = new AttachmentDTO();

        attachmentDTO.setId(attachment.getId());
        attachmentDTO.setHashId(attachment.getHashId());
        attachmentDTO.setContentType(attachment.getContentType());

        attachmentDTO.setFileName(attachment.getFileName());
        attachmentDTO.setExtension(attachment.getExtension());
        attachmentDTO.setLink(attachment.getLink());
        attachmentDTO.setFileSize(attachment.getFileSize());

        return attachmentDTO;
    }


}
