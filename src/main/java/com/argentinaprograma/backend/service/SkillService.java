package com.argentinaprograma.backend.service;

import com.argentinaprograma.backend.model.Skill;
import com.argentinaprograma.backend.repository.SkillRepository;
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
public class SkillService implements ISkillService{

	@Autowired
	SkillRepository skillRepository;

	public List<Skill> list(){
		return skillRepository.findAll();
	}

	public void save(Skill skill){
		skillRepository.save(skill);
	}

	public void delete(Long id){
		skillRepository.deleteById(id);
	}

	public void update(Skill skill){
		skillRepository.save(skill);
	}

	public Skill findById(Long id){
		return skillRepository.findById(id).orElse(null);
	}

	public boolean existsById(Long id) {
		return skillRepository.existsById(id);
	}

	public Optional<Skill> getOne(Long id){
		return skillRepository.findById(id);
	}
}
