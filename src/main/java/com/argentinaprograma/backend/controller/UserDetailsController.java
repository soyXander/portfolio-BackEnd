package com.argentinaprograma.backend.controller;

import com.argentinaprograma.backend.model.Image;
import com.argentinaprograma.backend.model.UserDetails;
import com.argentinaprograma.backend.service.IImageService;
import com.argentinaprograma.backend.service.IUserDetailsService;
import com.argentinaprograma.backend.service.UserDetailsService;
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
@RequestMapping("/detalles-usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class UserDetailsController {

    @Autowired
    IUserDetailsService userDetService;
    @Autowired
    IImageService imageService;
    String timeStamp = String.valueOf(System.currentTimeMillis());

    /*@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/lista")
    public ResponseEntity<UserDetails> list() {
        List<UserDetails> list = userDetService.list();
        return new ResponseEntity(userDetService.list(), HttpStatus.OK);
    }*/

    @GetMapping("/ver/{id}")
    public ResponseEntity<UserDetails> get(@PathVariable("id") Long id) {
        UserDetails userDet = userDetService.findById(id);
        if(userDet == null)
            return new ResponseEntity(new Message("No se encontró el usuario"), HttpStatus.NOT_FOUND);
        return new ResponseEntity(userDet, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/guardar")
    public ResponseEntity<?> save(@RequestParam("userDetails") String userDet, @RequestParam(value = "image", required = false) MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserDetails userDetDTO = mapper.readValue(userDet, UserDetails.class);

        if (StringUtils.isBlank(userDetDTO.getFirstName()))
            return new ResponseEntity(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(userDetDTO.getLastName()))
            return new ResponseEntity(new Message("El apellido es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(userDetDTO.getTitle()))
            return new ResponseEntity(new Message("El título es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(userDetDTO.getDescription()))
            return new ResponseEntity(new Message("La descripción es obligatoria"), HttpStatus.BAD_REQUEST);
        if (file != null && !ImageUtil.imgExtValidator(file.getContentType()))
            return new ResponseEntity(new Message("El formato de la imagen no es válido"), HttpStatus.BAD_REQUEST);

        if (file != null) {
            try {
                Image img = new Image(
                        "user-" + timeStamp + file.getOriginalFilename(),
                        file.getContentType(),
                        ImageUtil.compressImage(file.getBytes()));
                imageService.saveImage(img);

                UserDetails userDetails = new UserDetails(
                        userDetDTO.getFirstName(),
                        userDetDTO.getLastName(),
                        userDetDTO.getLocation(),
                        userDetDTO.getTitle(),
                        userDetDTO.getDescription(),
                        img,
                        userDetDTO.getFacebookId(),
                        userDetDTO.getInstagramId(),
                        userDetDTO.getGithubId(),
                        userDetDTO.getLinkedinId(),
                        userDetDTO.getTwitterId());
                userDetService.save(userDetails);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            UserDetails userDetails = new UserDetails(
                    userDetDTO.getFirstName(),
                    userDetDTO.getLastName(),
                    userDetDTO.getLocation(),
                    userDetDTO.getTitle(),
                    userDetDTO.getDescription(),
                    null,
                    userDetDTO.getFacebookId(),
                    userDetDTO.getInstagramId(),
                    userDetDTO.getGithubId(),
                    userDetDTO.getLinkedinId(),
                    userDetDTO.getTwitterId());
            userDetService.save(userDetails);
        }
        return new ResponseEntity(new Message("Detalles del usuario guardado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestParam("userDetails") String userDetails, @RequestParam(value = "image", required = false) MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserDetails userDetDTO = mapper.readValue(userDetails, UserDetails.class);

        if (StringUtils.isBlank(userDetDTO.getFirstName()))
            return new ResponseEntity(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(userDetDTO.getLastName()))
            return new ResponseEntity(new Message("El apellido es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(userDetDTO.getTitle()))
            return new ResponseEntity(new Message("El título es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(userDetDTO.getDescription()))
            return new ResponseEntity(new Message("La descripción es obligatoria"), HttpStatus.BAD_REQUEST);
        if (file != null && !ImageUtil.imgExtValidator(file.getContentType()))
            return new ResponseEntity(new Message("El formato de la imagen no es válido"), HttpStatus.BAD_REQUEST);

        UserDetails userDetailsById = userDetService.findById(id);
        userDetailsById.setFirstName(userDetDTO.getFirstName());
        userDetailsById.setLastName(userDetDTO.getLastName());
        userDetailsById.setLocation(userDetDTO.getLocation());
        userDetailsById.setTitle(userDetDTO.getTitle());
        userDetailsById.setDescription(userDetDTO.getDescription());
        if (file != null) {
            try {
                Image img = new Image(
                        "user-" + timeStamp + file.getOriginalFilename(),
                        file.getContentType(),
                        ImageUtil.compressImage(file.getBytes()));
                imageService.saveImage(img);
                userDetailsById.setImage(img);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        userDetailsById.setFacebookId(userDetDTO.getFacebookId());
        userDetailsById.setInstagramId(userDetDTO.getInstagramId());
        userDetailsById.setGithubId(userDetDTO.getGithubId());
        userDetailsById.setLinkedinId(userDetDTO.getLinkedinId());
        userDetailsById.setTwitterId(userDetDTO.getTwitterId());
        userDetService.save(userDetailsById);
        return new ResponseEntity(new Message("Detalles del usuario actualizado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        userDetService.delete(id);
        return new ResponseEntity(new Message("Detalles del usuario eliminado"), HttpStatus.OK);
    }
}
