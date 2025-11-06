package uz.lb.updaterserver.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.lb.updaterserver.entity.Application;

@Repository
@Transactional
public interface VersionRepository extends JpaRepository<Application, Long> {


}