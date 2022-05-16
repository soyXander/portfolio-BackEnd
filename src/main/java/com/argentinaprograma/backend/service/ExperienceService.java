package com.argentinaprograma.backend.service;

import com.argentinaprograma.backend.model.Experience;
import com.argentinaprograma.backend.repository.ExperienceRepository;
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
public class ExperienceService implements IExperienceService{

	@Autowired
	ExperienceRepository experienceRepository;

	public List<Experience> list(){
		return experienceRepository.findAll();
	}

	public void save(Experience experience){
		experienceRepository.save(experience);
	}

	public void delete(Long id){
		experienceRepository.deleteById(id);
	}

	public void update(Experience experience){
		experienceRepository.save(experience);
	}

	public Experience findById(Long id){
		return experienceRepository.findById(id).orElse(null);
	}

	public boolean existsById(Long id) {
		return experienceRepository.existsById(id);
	}

	public Optional<Experience> getOne(Long id){
		return experienceRepository.findById(id);
	}
}
