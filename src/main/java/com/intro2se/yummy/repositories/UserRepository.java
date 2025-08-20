package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.User;
import com.intro2se.yummy.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUserType(UserType userType);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByPhoneNumber(String phone);

    @Query("select case when count(u) > 0 then true else false end from User u where u.username = ?1")
    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String email);

    boolean existsByEmail(String email);

}
