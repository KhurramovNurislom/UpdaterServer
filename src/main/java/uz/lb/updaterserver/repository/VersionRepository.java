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
import java.util.Optional;

@Repository
@Transactional
public interface VersionRepository extends JpaRepository<Version, String> {

    List<Version> findAllByVersion(String version);
    Version findVersionById(String id);

    @Query("""
                SELECT v
                FROM Version v
                WHERE v.application.id = :applicationId
            """)
    List<Version> getVersionByApplicationId(@Param("applicationId") String applicationId);

    @Query("""
        SELECT v FROM Version v
        WHERE v.application.id = :appId
        ORDER BY v.createdAt DESC
        LIMIT 1
        """)
    Version findLatestVersionByApplicationId(@Param("appId") String appId);


}