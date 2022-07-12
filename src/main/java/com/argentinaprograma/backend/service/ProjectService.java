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

	@Override
	public List<Project> list() {
		return projectRepository.findAll();
	}

	@Override
	public void save(Project project) {
		projectRepository.save(project);
	}

	@Override
	public void delete(Long id) {
		projectRepository.deleteById(id);
	}

	@Override
	public void update(Project project) {
		projectRepository.save(project);
	}

	@Override
	public Project findById(Long id) {
		return projectRepository.findById(id).orElse(null);
	}

	@Override
	public boolean existsById(Long id) {
		return projectRepository.existsById(id);
	}

	@Override
	public Optional<Project> getOne(Long id) {
		return projectRepository.findById(id);
	}
}
