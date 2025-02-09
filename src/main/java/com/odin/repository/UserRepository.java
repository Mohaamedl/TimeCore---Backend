package com.odin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.odin.model.User;

/**
 * Repository interface for User entity operations.
 * Provides methods for database access and user queries.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Finds a user by their email address, case-insensitive
     * @param email Email address to search for
     * @return User if found, null otherwise
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    User findByEmail(@Param("email") String email);
}
