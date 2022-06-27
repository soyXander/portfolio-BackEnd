package com.argentinaprograma.backend.controller;

import com.argentinaprograma.backend.dto.ImageDTO;
import com.argentinaprograma.backend.model.Image;
import com.argentinaprograma.backend.service.IImageService;
import com.argentinaprograma.backend.utils.ImageUtil;
import com.argentinaprograma.backend.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Xander.-
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    IImageService imageService;

    @GetMapping("/ver/{name}")
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) {

        Optional<Image> img = imageService.findByName(name);
        if (!img.isPresent()) {
            return new ResponseEntity(new Message("No existe"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(img.get().getType()))
                .body(ImageUtil.decompressImage(img.get().getImage()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = {"/info/{name}"})
    public Image getImageDetails(@PathVariable("name") String name) throws IOException {
        Optional<Image> img = imageService.findByName(name);
        if (!img.isPresent()) {
            return null;
        }
        return Image.builder()
                .id(img.get().getId())
                .name(img.get().getName())
                .type(img.get().getType())
                .image(ImageUtil.decompressImage(img.get().getImage())).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/subir")
    public ResponseEntity<?> save(@RequestBody ImageDTO imgDTO, @RequestParam("image") MultipartFile file) {
        try {
            Image img = new Image(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    ImageUtil.compressImage(file.getBytes()));
            imageService.saveImage(img);
            return new ResponseEntity(new Message("Imagen subida: "+ file.getOriginalFilename()), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(new Message("Error al subir la imagen"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{name}")
    public ResponseEntity<Message> deleteImage(@PathVariable("name") String name) {
        imageService.deleteByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Message("Imagen eliminada exitosamente: " + name));
    }
}
