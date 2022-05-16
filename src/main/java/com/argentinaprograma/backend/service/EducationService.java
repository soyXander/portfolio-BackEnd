package com.argentinaprograma.backend.service;

import com.argentinaprograma.backend.model.Education;
import com.argentinaprograma.backend.repository.EducationRepository;
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
public class EducationService implements IEducationService {

	@Autowired
	EducationRepository educationRepository;

	public List<Education> list(){
		return educationRepository.findAll();
	}

	public void save(Education education){
		educationRepository.save(education);
	}

	public void delete(Long id){
		educationRepository.deleteById(id);
	}

	public void update(Education education){
		educationRepository.save(education);
	}

	public Education findById(Long id){
		return educationRepository.findById(id).orElse(null);
	}

	public boolean existsById(Long id) {
		return educationRepository.existsById(id);
	}

	public Optional<Education> getOne(Long id){
		return educationRepository.findById(id);
	}
}
