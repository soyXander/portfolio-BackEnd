package com.argentinaprograma.backend.repository;

import com.argentinaprograma.backend.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Xander.-
 */
@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
}
