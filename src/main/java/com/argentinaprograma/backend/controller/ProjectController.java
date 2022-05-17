package com.argentinaprograma.backend.controller;

import com.argentinaprograma.backend.dto.ProjectDTO;
import com.argentinaprograma.backend.model.Project;
import com.argentinaprograma.backend.service.IProjectService;
import com.argentinaprograma.backend.utils.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/lista")
	public ResponseEntity<Project> list() {
		List<Project> list = projectService.list();
		return new ResponseEntity(projectService.list(), HttpStatus.OK);
	}

	@PostMapping("/agregar")
	public ResponseEntity<?> save(@RequestBody ProjectDTO projectDTO) {
		if (StringUtils.isBlank(projectDTO.getProject()))
			return new ResponseEntity(new Message("El nombre del proyecto es obligatorio"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(projectDTO.getTechnology()))
			return new ResponseEntity(new Message("La tecnología es obligatoria"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(projectDTO.getDescription()))
			return new ResponseEntity(new Message("La descripción es obligatoria"), HttpStatus.BAD_REQUEST);

		Project project = new Project(
				projectDTO.getProject(),
				projectDTO.getTechnology(),
				projectDTO.getDescription());
		projectService.save(project);

		return new ResponseEntity(new Message("Proyecto agregado"), HttpStatus.CREATED);
	}

	@GetMapping("/detalle/{id}")
	public ResponseEntity<Project> getById(@PathVariable("id") Long id){
		if(!projectService.existsById(id))
			return new ResponseEntity(new Message("No existe"), HttpStatus.NOT_FOUND);
		Project project = projectService.getOne(id).get();
		return new ResponseEntity(project, HttpStatus.OK);
	}

	@PutMapping("/editar/{id}")
	public ResponseEntity<?> update(@RequestBody ProjectDTO projectDTO, @PathVariable("id") Long id){
		if(!projectService.existsById(id))
			return new ResponseEntity(new Message("No existe"), HttpStatus.NOT_FOUND);

		if (StringUtils.isBlank(projectDTO.getProject()))
			return new ResponseEntity(new Message("El nombre del proyecto es obligatorio"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(projectDTO.getTechnology()))
			return new ResponseEntity(new Message("La tecnología es obligatoria"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(projectDTO.getDescription()))
			return new ResponseEntity(new Message("La descripción es obligatoria"), HttpStatus.BAD_REQUEST);

		Project project = projectService.findById(id);
		project.setProject(projectDTO.getProject());
		project.setTechnology(projectDTO.getTechnology());
		project.setDescription(projectDTO.getDescription());
		projectService.update(project);

		return new ResponseEntity(new Message("Proyecto actualizado"), HttpStatus.OK);
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		if(!projectService.existsById(id))
			return new ResponseEntity(new Message("No existe"), HttpStatus.NOT_FOUND);

		projectService.delete(id);
		return new ResponseEntity(new Message("Proyecto eliminado"), HttpStatus.OK);
	}
}
