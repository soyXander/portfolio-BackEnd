package com.argentinaprograma.backend.controller;

import com.argentinaprograma.backend.dto.ProjectDTO;
import com.argentinaprograma.backend.model.Image;
import com.argentinaprograma.backend.model.Project;
import com.argentinaprograma.backend.service.IImageService;
import com.argentinaprograma.backend.service.IProjectService;
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
@RequestMapping("/proyecto")
@CrossOrigin(origins = "http://localhost:4200/")
public class ProjectController {

	@Autowired
	IProjectService projectService;
	@Autowired
	IImageService imageService;
	String timeStamp = String.valueOf(System.currentTimeMillis());

	@GetMapping("/lista")
	public ResponseEntity<Project> list() {
		List<Project> list = projectService.list();
		return new ResponseEntity(projectService.list(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/guardar")
	public ResponseEntity<?> save(@RequestParam("project") String proj, @RequestParam(value = "image", required = false) MultipartFile file) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ProjectDTO projDTO = mapper.readValue(proj, ProjectDTO.class);

		if (StringUtils.isBlank(projDTO.getProject()))
			return new ResponseEntity(new Message("El nombre del proyecto es obligatorio"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(projDTO.getTechnology()))
			return new ResponseEntity(new Message("La tecnología es obligatoria"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(projDTO.getDescription()))
			return new ResponseEntity(new Message("La descripción es obligatoria"), HttpStatus.BAD_REQUEST);
		if (file != null && !ImageUtil.imgExtValidator(file.getContentType()))
			return new ResponseEntity(new Message("La extension de la imagen debe ser: jpg, png o gif"), HttpStatus.BAD_REQUEST);

		if (file != null) {
			try {
				Image img = new Image(
						"proj-" + timeStamp + "-" + file.getOriginalFilename(),
						file.getContentType(),
						ImageUtil.compressImage(file.getBytes()));
				imageService.saveImage(img);

				Project project = new Project(
						projDTO.getProject(),
						projDTO.getTechnology(),
						projDTO.getDescription(),
						img);
				projectService.save(project);

				return new ResponseEntity(new Message("Proyecto guardado con éxito"), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity(new Message("Error al guardar el proyecto"), HttpStatus.BAD_REQUEST);
			}
		}
		else {
			Project project = new Project(
					projDTO.getProject(),
					projDTO.getTechnology(),
					projDTO.getDescription(),
					null);
			projectService.save(project);

			return new ResponseEntity(new Message("Proyecto guardado con éxito"), HttpStatus.OK);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/detalle/{id}")
	public ResponseEntity<Project> getById(@PathVariable("id") Long id){
		if(!projectService.existsById(id))
			return new ResponseEntity(new Message("No existe"), HttpStatus.NOT_FOUND);
		Project project = projectService.getOne(id).get();
		return new ResponseEntity(project, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestParam("project") String proj, @RequestParam(value = "image", required = false) MultipartFile file) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ProjectDTO projDTO = mapper.readValue(proj, ProjectDTO.class);

		if(!projectService.existsById(id))
			return new ResponseEntity(new Message("Proyecto no encontrado"), HttpStatus.NOT_FOUND);
		if (StringUtils.isBlank(projDTO.getProject()))
			return new ResponseEntity(new Message("El nombre del proyecto es obligatorio"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(projDTO.getTechnology()))
			return new ResponseEntity(new Message("La tecnología es obligatoria"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(projDTO.getDescription()))
			return new ResponseEntity(new Message("La descripción es obligatoria"), HttpStatus.BAD_REQUEST);
		if (file != null && !ImageUtil.imgExtValidator(file.getContentType()))
			return new ResponseEntity(new Message("La extension de la imagen debe ser: jpg, png o gif"), HttpStatus.BAD_REQUEST);

		Project projById = projectService.findById(id);
		projById.setProject(projDTO.getProject());
		projById.setTechnology(projDTO.getTechnology());
		projById.setDescription(projDTO.getDescription());
		if (file != null) {
			try {
				if (projById.getImage() != null)
					imageService.deleteImage(projById.getImage().getId());
				Image img = new Image(
						"proj-" + timeStamp + "-" + file.getOriginalFilename(),
						file.getContentType(),
						ImageUtil.compressImage(file.getBytes()));
				imageService.saveImage(img);
				projById.setImage(img);
			} catch (IOException e) {
				throw new RuntimeException("Error al guardar la imagen");
			}
		}
		projectService.update(projById);
		return new ResponseEntity(new Message("Proyecto actualizado con exito"), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		if(!projectService.existsById(id))
			return new ResponseEntity(new Message("No existe"), HttpStatus.NOT_FOUND);

		projectService.delete(id);
		return new ResponseEntity(new Message("Proyecto eliminado"), HttpStatus.OK);
	}
}
