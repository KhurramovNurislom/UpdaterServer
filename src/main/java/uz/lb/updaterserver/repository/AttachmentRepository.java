package uz.lb.updaterserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.lb.updaterserver.entity.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, String> {
    Attachment findAttachmentByHashId(String hashId);
}
