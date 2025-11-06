package uz.lb.updaterserver.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.lb.updaterserver.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    long countByCreatedByUserId(Long createdByUserId);

    @Modifying
    @Query(value = "SELECT * FROM  account  WHERE LOWER(login) LIKE LOWER(CONCAT('%', :login, '%'))", nativeQuery = true)
    List<User> findUsersByLogin(@Param(value = "login") String login);

    User findUserByLogin(String login);
//    Optional<User> findByUsernameAndVisibleTrue(String username);
    @Modifying
    @Query(value = "UPDATE account SET visible = false WHERE account.id= :id", nativeQuery = true)
    User deleteUserById(@Param("id") Long id);

    @Query(value = """
       WITH RECURSIVE user_hierarchy AS (
         SELECT id
         FROM account
         WHERE id = :userId
       
         UNION ALL
       
         SELECT child.id
         FROM account child
         INNER JOIN user_hierarchy uh ON child.created_by_user_id = uh.id
       )
       SELECT a.*
       FROM account a
       WHERE a.id IN (SELECT id FROM user_hierarchy);
            """, nativeQuery = true)
    List<User> getAllDescendants(@Param("userId") Long userId);

//    @Query(value = """
//    WITH RECURSIVE user_hierarchy AS (
//        SELECT id, created_by_user_id
//        FROM account
//        WHERE id = :userId
//
//        UNION ALL
//
//        SELECT parent.id, parent.created_by_user_id
//        FROM account parent
//        INNER JOIN user_hierarchy child ON parent.id = child.created_by_user_id
//    )
//    SELECT a.*
//    FROM account a
//    WHERE a.id IN (SELECT id FROM user_hierarchy)
//    """, nativeQuery = true)
//    List<User> getAllAncestors(@Param("userId") Long userId);


}