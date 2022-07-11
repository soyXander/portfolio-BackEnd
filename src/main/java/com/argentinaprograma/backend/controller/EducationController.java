package com.argentinaprograma.backend.controller;

import com.argentinaprograma.backend.dto.EducationDTO;
import com.argentinaprograma.backend.model.Education;
import com.argentinaprograma.backend.model.Image;
import com.argentinaprograma.backend.service.IEducationService;
import com.argentinaprograma.backend.service.IImageService;
import com.argentinaprograma.backend.utils.ImageUtil;
import com.argentinaprograma.backend.utils.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Xander.-
 */
@RestController
@RequestMapping("/educacion")
@CrossOrigin(origins = "http://localhost:4200/")
public class EducationController {
	@Autowired
	IEducationService eduService;
	@Autowired
	IImageService imageService;
	String timeStamp = String.valueOf(System.currentTimeMillis());

	@RequestMapping("/lista")
	public ResponseEntity<EducationDTO> list() {
		return new ResponseEntity(eduService.list(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/guardar")
	public ResponseEntity<?> save(@RequestParam("education") String edu, @RequestParam(value = "image", required = false) MultipartFile file) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		EducationDTO eduDTO = mapper.readValue(edu, EducationDTO.class);

		if (StringUtils.isBlank(eduDTO.getInstitute()))
			return new ResponseEntity(new Message("El nombre del instituto es obligatorio"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(eduDTO.getCertification()))
			return new ResponseEntity(new Message("El certificado es obligatorio"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(eduDTO.getDescription()))
			return new ResponseEntity(new Message("La descripción es obligatoria"), HttpStatus.BAD_REQUEST);
		if (file != null && !ImageUtil.imgExtValidator(file.getContentType()))
			return new ResponseEntity(new Message("La extensión de la imagen debe ser: jpg, jpeg, png o gif"), HttpStatus.BAD_REQUEST);

		if (file != null) {
			try {
				Image img = new Image(
						"edu" + timeStamp + "-" + file.getOriginalFilename(),
						file.getContentType(),
						ImageUtil.compressImage(file.getBytes()));
				imageService.saveImage(img);

				Education education = new Education(
						eduDTO.getInstitute(),
						eduDTO.getCertification(),
						eduDTO.getDescription(),
						img);
				eduService.save(education);

				return new ResponseEntity(new Message("Educación guardada con éxito"), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity(new Message("Error al guardar la educación"), HttpStatus.BAD_REQUEST);
			}
		}
		else {
			Education education = new Education(
					eduDTO.getInstitute(),
					eduDTO.getCertification(),
					eduDTO.getDescription(),
					null);
			eduService.save(education);

			return new ResponseEntity(new Message("Educación agregada"), HttpStatus.CREATED);
		}
	}

	@GetMapping("/detalle/{id}")
	public ResponseEntity<Education> getById(@PathVariable("id") Long id){
		if(!eduService.existsById(id))
			return new ResponseEntity(new Message("No existe"), HttpStatus.NOT_FOUND);
		Education education = eduService.getOne(id).get();
		return new ResponseEntity(education, HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
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
