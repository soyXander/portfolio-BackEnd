package com.argentinaprograma.backend.service;

import com.argentinaprograma.backend.model.Skill;

import java.util.List;
import java.util.Optional;

/**
 * @author Xander.-
 */
public interface ISkillService {

	List<Skill> list();

	void save(Skill skill);
	void update(Skill skill);
	void delete(Long id);
	Skill findById(Long id);
	boolean existsById(Long id);
	Optional<Skill> getOne(Long id);
}
