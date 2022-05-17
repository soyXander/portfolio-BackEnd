package com.argentinaprograma.backend.service;

import com.argentinaprograma.backend.model.Project;
import com.argentinaprograma.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Xander.-
 */
@Service
@Transactional
public class ProjectService implements IProjectService {

	@Autowired
	ProjectRepository projectRepository;

	public List<Project> list() {
		return projectRepository.findAll();
	}

	public void save(Project project) {
		projectRepository.save(project);
	}

	public void delete(Long id) {
		projectRepository.deleteById(id);
	}

	public void update(Project project) {
		projectRepository.save(project);
	}

	public Project findById(Long id) {
		return projectRepository.findById(id).orElse(null);
	}

	public boolean existsById(Long id) {
		return projectRepository.existsById(id);
	}

	public Optional<Project> getOne(Long id) {
		return projectRepository.findById(id);
	}
}
