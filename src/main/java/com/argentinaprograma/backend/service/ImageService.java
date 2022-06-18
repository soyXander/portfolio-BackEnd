package com.argentinaprograma.backend.service;

import com.argentinaprograma.backend.model.Image;
import com.argentinaprograma.backend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Xander.-
 */
@Service
public class ImageService implements IImageService {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public byte[] getImage(Long id) {
        return imageRepository.findById(id).get().getImage();
    }
    public void saveImage(Image image) {
        imageRepository.save(image);
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    public Image findById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    public boolean existsById(Long id) {
        return imageRepository.existsById(id);
    }

    public Optional<Image> getOne(Long id) {
        return imageRepository.findById(id);
    }

    public Optional<Image> findByName(String name) {
        return imageRepository.findByName(name);
    }

   public void deleteByName(String name) {
        imageRepository.deleteByName(name);
    }
}
