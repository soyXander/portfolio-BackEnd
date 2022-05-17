package com.argentinaprograma.backend.service;

import com.argentinaprograma.backend.model.Project;

import java.util.List;
import java.util.Optional;

/**
 * @author Xander.-
 */
public interface IProjectService {

	List<Project> list();

	void save(Project project);
	void update(Project project);
	void delete(Long id);
	Project findById(Long id);
	boolean existsById(Long id);
	Optional<Project> getOne(Long id);
}
