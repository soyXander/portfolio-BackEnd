package com.argentinaprograma.backend.controller;

import com.argentinaprograma.backend.dto.SkillDTO;
import com.argentinaprograma.backend.model.Experience;
import com.argentinaprograma.backend.model.Skill;
import com.argentinaprograma.backend.service.ISkillService;
import com.argentinaprograma.backend.utils.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Xander.-
 */
@RestController
@RequestMapping("/habilidad")
@CrossOrigin(origins = "http://localhost:4200/")
public class SkillController {

	@Autowired
	ISkillService skillService;

	@GetMapping("/lista")
	public ResponseEntity<Experience> list() {
		List<Skill> list = skillService.list();
		return new ResponseEntity(skillService.list(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/guardar")
	public ResponseEntity<?> save(@RequestBody SkillDTO skill) {
		if (StringUtils.isBlank(skill.getSkill()))
			return new ResponseEntity(new Message("La habilidad es obligatoria"), HttpStatus.BAD_REQUEST);
		if (skill.getPercentage() < 0 || skill.getPercentage() > 100)
			return new ResponseEntity(new Message("El porcentaje debe ser entre 0 y 100"), HttpStatus.BAD_REQUEST);

		Skill sk = new Skill(
				skill.getSkill(),
				skill.getPercentage());
		skillService.save(sk);

		return new ResponseEntity(new Message("Habilidad agregada"), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/detalle/{id}")
	public ResponseEntity<Skill> getById(@PathVariable("id") Long id){
		if(!skillService.existsById(id))
			return new ResponseEntity(new Message("Habilidad no encontrada"), HttpStatus.NOT_FOUND);
		Skill skill = skillService.getOne(id).get();
		return new ResponseEntity(skill, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody SkillDTO skillDTO) {
		if (StringUtils.isBlank(skillDTO.getSkill()))
			return new ResponseEntity(new Message("La habilidad es obligatoria"), HttpStatus.BAD_REQUEST);
		if (skillDTO.getPercentage() <= 0 || skillDTO.getPercentage() >= 100)
			return new ResponseEntity(new Message("El porcentaje debe ser entre 0 y 100"), HttpStatus.BAD_REQUEST);

		Skill sk = skillService.findById(id);
		sk.setSkill(skillDTO.getSkill());
		sk.setPercentage(skillDTO.getPercentage());
		skillService.update(sk);

		return new ResponseEntity(new Message("Habilidad actualizada"), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		if(!skillService.existsById(id))
			return new ResponseEntity(new Message("Habilidad no encontrada"), HttpStatus.NOT_FOUND);

		skillService.delete(id);
		return new ResponseEntity(new Message("Habilidad eliminada"), HttpStatus.OK);
	}
}
