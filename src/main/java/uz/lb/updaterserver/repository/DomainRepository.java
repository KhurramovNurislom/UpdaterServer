package uz.lb.updaterserver.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.lb.updaterserver.entity.Domain;
import uz.lb.updaterserver.entity.Version;
import uz.lb.updaterserver.enums.GeneralStatus;

@Repository
@Transactional
public interface DomainRepository extends JpaRepository<Domain, String> {
    Domain findDomainByDomain(String domain);
    Domain findTopByStatusAndVisibleTrueOrderByCreatedAtDesc(GeneralStatus status);

}