package com.argentinaprograma.backend.service;

import com.argentinaprograma.backend.model.Education;

import java.util.List;
import java.util.Optional;

/**
 * @author Xander.-
 */
public interface IEducationService {

	List<Education> list();

	void save(Education education);
	void update(Education education);
	void delete(Long id);
	Education findById(Long id);
	boolean existsById(Long id);
	Optional<Education> getOne(Long id);
}
