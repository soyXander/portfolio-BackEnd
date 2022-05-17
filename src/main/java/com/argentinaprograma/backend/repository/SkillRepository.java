package com.argentinaprograma.backend.repository;

import com.argentinaprograma.backend.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Xander.-
 */
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
}
