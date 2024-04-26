package com.user.repositories;

import com.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    @Query(value = "select * from users where email =:email",nativeQuery = true)
    Optional<User> findUserByEmail(@Param("email") String email);

    @Query(value = "select * from users where name like %:key% and email like %:key%" ,nativeQuery = true)
    List<User> searchUser(String key);
}
