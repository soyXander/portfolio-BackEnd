package com.argentinaprograma.backend.controller;

import com.argentinaprograma.backend.model.Banner;
import com.argentinaprograma.backend.model.Image;
import com.argentinaprograma.backend.service.IBannerService;
import com.argentinaprograma.backend.service.IImageService;
import com.argentinaprograma.backend.utils.ImageUtil;
import com.argentinaprograma.backend.utils.Message;
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
@RequestMapping("/api/banner")
@CrossOrigin(origins = "*")
public class BannerController {

    @Autowired
    IBannerService bannerService;
    @Autowired
    IImageService imageService;
    String timeStamp = String.valueOf(System.currentTimeMillis());

    @GetMapping("/lista")
    public ResponseEntity<Banner> list() {
        List<Banner> list = bannerService.list();
        return new ResponseEntity(bannerService.list(), HttpStatus.OK);
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<Banner> get(@PathVariable("id") Long id) {
        Banner banner = bannerService.findById(id);
        if(banner == null)
            return new ResponseEntity(new Message("No se encontró el banner"), HttpStatus.NOT_FOUND);
        return new ResponseEntity(banner, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/guardar")
    public ResponseEntity<?> save(@RequestParam(value = "image", required = false) MultipartFile file) throws IOException {
        if (file != null) {
            try {
                Image img = new Image(
                        "banner-" + timeStamp + file.getOriginalFilename(),
                        file.getContentType(),
                        ImageUtil.compressImage(file.getBytes()));
                imageService.saveImage(img);

                Banner banner = new Banner(img);
                bannerService.save(banner);
            } catch (Exception e) {
                return new ResponseEntity(new Message("Error al subir la imagen"), HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity(new Message("La imagen es obligatoria"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new Message("El banner se guardó correctamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestParam(value = "image") MultipartFile file) throws IOException {
        if (file != null) {
            Banner bannerById = bannerService.findById(id);
            try {
                if (bannerById != null)
                    imageService.deleteImage(bannerById.getImage().getId());
                Image img = new Image(
                        "banner-" + timeStamp + file.getOriginalFilename(),
                        file.getContentType(),
                        ImageUtil.compressImage(file.getBytes()));
                imageService.saveImage(img);

                bannerById.setImage(img);
                bannerService.update(bannerById);

                return new ResponseEntity(new Message("El banner se actualizo correctamente"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity(new Message("Error al actualizar el banner"), HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity(new Message("La imagen es obligatoria"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/existe/{id}")
    public ResponseEntity<?> existsById(@PathVariable Long id) {
        if (bannerService.existsById(id))
            return new ResponseEntity(new Message("El banner existe"), HttpStatus.OK);
        else
            return new ResponseEntity(new Message("El banner no existe"), HttpStatus.NOT_FOUND);
    }
}
