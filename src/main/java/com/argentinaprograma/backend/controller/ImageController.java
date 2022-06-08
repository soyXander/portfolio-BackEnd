package com.argentinaprograma.backend.controller;

import com.argentinaprograma.backend.model.Image;
import com.argentinaprograma.backend.repository.ImageRepository;
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
    ImageRepository imgRepository;

    @GetMapping("/ver/{name}")
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) {
        final Optional<Image> dbImg = imgRepository.findByName(name);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(dbImg.get().getType()))
                .body(ImageUtil.decompressImage(dbImg.get().getImage()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = {"/info/{name}"})
    public Image getImageDetails(@PathVariable("name") String name) throws IOException {

        final Optional<Image> dbImage = imgRepository.findByName(name);

        return Image.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .image(ImageUtil.decompressImage(dbImage.get().getImage())).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/subir")
    public ResponseEntity<Message> uplaodImage(@RequestParam("image") MultipartFile file)
            throws IOException {

        imgRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtil.compressImage(file.getBytes())).build());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Message("Imagen subida exitosamente: " +
                        file.getOriginalFilename()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{name}")
    public ResponseEntity<Message> deleteImage(@PathVariable("name") String name) {
        imgRepository.deleteByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Message("Imagen eliminada exitosamente: " + name));
    }
}
