package com.odin.repository;

import com.odin.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Group entities.
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}