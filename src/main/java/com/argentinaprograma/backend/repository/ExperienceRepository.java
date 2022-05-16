package com.argentinaprograma.backend.repository;

import com.argentinaprograma.backend.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Xander.-
 */
@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
