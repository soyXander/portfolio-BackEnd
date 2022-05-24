package com.argentinaprograma.backend.controller;

import com.argentinaprograma.backend.dto.ExperienceDTO;
import com.argentinaprograma.backend.model.Experience;
import com.argentinaprograma.backend.service.IExperienceService;
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
@RequestMapping("/experiencia")
@CrossOrigin(origins = "http://localhost:4200/")
public class ExperienceController {
	@Autowired
	IExperienceService expService;

	@GetMapping("/lista")
	public ResponseEntity<Experience> list() {
		List<Experience> list = expService.list();
		return new ResponseEntity(expService.list(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/agregar")
	public ResponseEntity<?> save(@RequestBody ExperienceDTO expDTO) {
		if (StringUtils.isBlank(expDTO.getCompany()))
			return new ResponseEntity(new Message("El nombre de la empresa es obligatorio"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(expDTO.getPosition()))
			return new ResponseEntity(new Message("El puesto es obligatorio"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(expDTO.getDescription()))
			return new ResponseEntity(new Message("La descripción es obligatoria"), HttpStatus.BAD_REQUEST);

		Experience exp = new Experience(
				expDTO.getCompany(),
				expDTO.getPosition(),
				expDTO.getDescription());
		expService.save(exp);

		return new ResponseEntity(new Message("Experiencia agregada"), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/detalle/{id}")
	public ResponseEntity<Experience> getById(@PathVariable("id") Long id){
		if(!expService.existsById(id))
			return new ResponseEntity(new Message("No existe"), HttpStatus.NOT_FOUND);
		Experience experience = expService.getOne(id).get();
		return new ResponseEntity(experience, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ExperienceDTO expDTO) {
		if (!expService.existsById(id))
			return new ResponseEntity<>(new Message("Experiencia no encontrada"), HttpStatus.NOT_FOUND);

		if (StringUtils.isBlank(expDTO.getCompany()))
			return new ResponseEntity<>(new Message("El nombre de la empresa es obligatorio"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(expDTO.getPosition()))
			return new ResponseEntity<>(new Message("El puesto es obligatorio"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(expDTO.getDescription()))
			return new ResponseEntity<>(new Message("La descripción es obligatoria"), HttpStatus.BAD_REQUEST);

		Experience exp = expService.findById(id);
		exp.setCompany(expDTO.getCompany());
		exp.setPosition(expDTO.getPosition());
		exp.setDescription(expDTO.getDescription());
		expService.update(exp);

		return new ResponseEntity<>(new Message("Experiencia actualizada"), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if (!expService.existsById(id))
			return new ResponseEntity<>(new Message("Experiencia no encontrada"), HttpStatus.NOT_FOUND);

		expService.delete(id);
		return new ResponseEntity<>(new Message("Experiencia eliminada"), HttpStatus.OK);
	}
}
