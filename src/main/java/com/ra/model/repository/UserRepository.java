package com.ra.model.repository;

import com.ra.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "select u.* from user u join user_role ur on u.id=ur.user_id " +
            "where ur.role_id = 2 and u.status like :status",nativeQuery = true)
    List<User> findAllByStatus(String status);
    @Query(value = "select u.* from user u join user_role ur on u.id=ur.user_id " +
            "where ur.role_id = 2",nativeQuery = true)
    List<User> getAll();
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String userName);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

}
