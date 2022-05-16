package com.argentinaprograma.backend.service;

import com.argentinaprograma.backend.model.Experience;

import java.util.List;
import java.util.Optional;

/**
 * @author Xander.-
 */
public interface IExperienceService {

	List<Experience> list();

	void save(Experience experience);
	void update(Experience experience);
	void delete(Long id);
	Experience findById(Long id);
	boolean existsById(Long id);
	Optional<Experience> getOne(Long id);
}
