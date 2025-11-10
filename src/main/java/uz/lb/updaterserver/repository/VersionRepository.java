package uz.lb.updaterserver.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.lb.updaterserver.entity.Application;
import uz.lb.updaterserver.entity.Version;

import java.util.List;

@Repository
@Transactional
public interface VersionRepository extends JpaRepository<Version, Long> {

    Version findVersionByVersion(String version);
    Version findVersionById(Long id);

    @Query("""
                SELECT v
                FROM Version v
                WHERE v.application.id = :applicationId
            """)
    List<Version> getVersionByApplicationId(@Param("applicationId") Long applicationId);
}