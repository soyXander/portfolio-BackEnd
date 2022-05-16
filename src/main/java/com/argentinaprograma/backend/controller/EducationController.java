package com.argentinaprograma.backend.controller;

import com.argentinaprograma.backend.dto.EducationDTO;
import com.argentinaprograma.backend.model.Education;
import com.argentinaprograma.backend.service.IEducationService;
import com.argentinaprograma.backend.utils.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Xander.-
 */
@RestController
@RequestMapping("/educacion")
@CrossOrigin(origins = "http://localhost:4200/")
public class EducationController {
	@Autowired
	IEducationService eduService;

	@RequestMapping("/lista")
	public ResponseEntity<Object> list() {
		return new ResponseEntity(eduService.list(), HttpStatus.OK);
	}

	@PostMapping("/agregar")
	public ResponseEntity<?> save(@RequestBody EducationDTO eduDTO) {
		if (StringUtils.isBlank(eduDTO.getInstitute()))
			return new ResponseEntity(new Message("El nombre del instituto es obligatorio"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(eduDTO.getCertification()))
			return new ResponseEntity(new Message("El certificado es obligatorio"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(eduDTO.getDescription()))
			return new ResponseEntity(new Message("La descripción es obligatoria"), HttpStatus.BAD_REQUEST);

		Education edu = new Education(
				eduDTO.getInstitute(),
				eduDTO.getCertification(),
				eduDTO.getDescription());
		eduService.save(edu);
		return new ResponseEntity(new Message("Educación agregada"), HttpStatus.CREATED);
	}

	@GetMapping("/detalle/{id}")
	public ResponseEntity<Education> getById(@PathVariable("id") Long id){
		if(!eduService.existsById(id))
			return new ResponseEntity(new Message("No existe"), HttpStatus.NOT_FOUND);
		Education education = eduService.getOne(id).get();
		return new ResponseEntity(education, HttpStatus.OK);
	}

	@PutMapping("/editar/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EducationDTO eduDTO) {
		if (!eduService.existsById(id))
			return new ResponseEntity(new Message("No existe"), HttpStatus.NOT_FOUND);

		if (StringUtils.isBlank(eduDTO.getInstitute()))
			return new ResponseEntity(new Message("El nombre del instituto es obligatorio"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(eduDTO.getCertification()))
			return new ResponseEntity(new Message("El certificado es obligatorio"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(eduDTO.getDescription()))
			return new ResponseEntity(new Message("La descripción es obligatoria"), HttpStatus.BAD_REQUEST);

		Education edu = eduService.getOne(id).get();
		edu.setInstitute(eduDTO.getInstitute());
		edu.setCertification(eduDTO.getCertification());
		edu.setDescription(eduDTO.getDescription());
		eduService.update(edu);

		return new ResponseEntity(new Message("Educación actualizada"), HttpStatus.OK);
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if (!eduService.existsById(id))
			return new ResponseEntity(new Message("No existe"), HttpStatus.NOT_FOUND);

		eduService.delete(id);
		return new ResponseEntity(new Message("Educación eliminada"), HttpStatus.OK);
	}
}
