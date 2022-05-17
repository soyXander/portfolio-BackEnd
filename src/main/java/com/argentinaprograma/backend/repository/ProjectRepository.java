package com.argentinaprograma.backend.repository;

import com.argentinaprograma.backend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Xander.-
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
