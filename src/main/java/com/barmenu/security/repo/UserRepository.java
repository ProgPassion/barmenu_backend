package com.barmenu.security.repo;

import com.barmenu.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUrl(String url);
    @Query("SELECT u.url FROM User u WHERE u.id = :userId")
    String findUrlByUserId(
            @Param("userId") Integer userId
    );

    @Query("SELECT u.business_name FROM User u WHERE u.id = :userId")
    String findBusinessNameByUserId(
            @Param("userId") Integer userId
    );

    @Query("SELECT u.business_name FROM User u WHERE u.url = :userUrl")
    String findBusinessNameByUrl(
            @Param("userUrl") String userUrl
    );

    @Query("SELECT COUNT(u) FROM User u WHERE u.id <> :userId " +
            "AND u.url = :userUrl")
    Integer existsUserUrlWithDifferentId(
            @Param("userId") Integer userId,
            @Param("userUrl") String userUrl
    );
}
