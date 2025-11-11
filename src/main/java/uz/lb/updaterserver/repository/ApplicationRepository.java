package uz.lb.updaterserver.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.lb.updaterserver.entity.Application;
import uz.lb.updaterserver.entity.User;

import java.util.List;

@Repository
@Transactional
public interface ApplicationRepository extends JpaRepository<Application, String> {

    Application findApplicationByName(String name);

    @Modifying
    @Query(value = "SELECT * FROM  application  WHERE LOWER(name) LIKE LOWER(CONCAT('%', :name, '%'))", nativeQuery = true)
    List<Application> findApplicationsByName(@Param(value = "name") String name);
}