package com.argentinaprograma.backend.controller;

import com.argentinaprograma.backend.dto.ExperienceDTO;
import com.argentinaprograma.backend.model.Experience;
import com.argentinaprograma.backend.model.Image;
import com.argentinaprograma.backend.service.IExperienceService;
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
	@Autowired
	IImageService imageService;
	String timeStamp = String.valueOf(System.currentTimeMillis());

	@GetMapping("/lista")
	public ResponseEntity<Experience> list() {
		List<Experience> list = expService.list();
		return new ResponseEntity(expService.list(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/agregar")
	public ResponseEntity<?> save(@RequestParam("experience") String exp, @RequestParam(value = "image", required = false) MultipartFile file) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ExperienceDTO expDTO = mapper.readValue(exp, ExperienceDTO.class);

		if (StringUtils.isBlank(expDTO.getCompany()))
			return new ResponseEntity(new Message("El nombre de la empresa es obligatorio"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(expDTO.getPosition()))
			return new ResponseEntity(new Message("El puesto es obligatorio"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(expDTO.getDescription()))
			return new ResponseEntity(new Message("La descripción es obligatoria"), HttpStatus.BAD_REQUEST);

		if (file != null) {
			try {
				Image img = new Image(
                        timeStamp + "-" + file.getOriginalFilename() ,
						file.getContentType(),
						ImageUtil.compressImage(file.getBytes()));
				imageService.saveImage(img);

				Experience experience = new Experience(
						expDTO.getCompany(),
						expDTO.getPosition(),
						expDTO.getDescription(),
						img);
				expService.save(experience);

				return new ResponseEntity(new Message("Experiencia guardada con éxito"), HttpStatus.CREATED);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		else {
			Experience experience = new Experience(
					expDTO.getCompany(),
					expDTO.getPosition(),
					expDTO.getDescription(),
					null);
			expService.save(experience);

			return new ResponseEntity(new Message("Experiencia guardada con éxito"), HttpStatus.CREATED);
		}
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
	public ResponseEntity<?> update(@PathVariable Long id, @RequestParam("experience") String exp, @RequestParam(value = "image", required = false) MultipartFile file) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ExperienceDTO expDTO = mapper.readValue(exp, ExperienceDTO.class);

		if (!expService.existsById(id))
			return new ResponseEntity<>(new Message("Experiencia no encontrada"), HttpStatus.NOT_FOUND);
		if (StringUtils.isBlank(expDTO.getCompany()))
			return new ResponseEntity<>(new Message("El nombre de la empresa es obligatorio"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(expDTO.getPosition()))
			return new ResponseEntity<>(new Message("El puesto es obligatorio"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(expDTO.getDescription()))
			return new ResponseEntity<>(new Message("La descripción es obligatoria"), HttpStatus.BAD_REQUEST);

		Experience expById = expService.findById(id);
		expById.setCompany(expDTO.getCompany());
		expById.setPosition(expDTO.getPosition());
		expById.setDescription(expDTO.getDescription());
		if (file != null) {
			try {
				if (expById.getImage() != null)
					imageService.deleteImage(expById.getImage().getId());
				Image img = new Image(
						timeStamp + "-" + file.getOriginalFilename(),
						file.getContentType(),
						ImageUtil.compressImage(file.getBytes()));
				imageService.saveImage(img);
				expById.setImage(img);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		expService.update(expById);
		return new ResponseEntity<>(new Message("Experiencia actualizada con éxito"), HttpStatus.OK);
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
