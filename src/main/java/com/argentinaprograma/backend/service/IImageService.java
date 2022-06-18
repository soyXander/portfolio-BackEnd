package com.argentinaprograma.backend.service;

import com.argentinaprograma.backend.model.Image;

import java.util.Optional;

/**
 * @author Xander.-
 */
public interface IImageService {

    byte[] getImage(Long id);

    void saveImage(Image image);

    void deleteImage(Long id);

    Image findById(Long id);

    boolean existsById(Long id);

    Optional<Image> getOne(Long id);

    Optional<Image> findByName(String name);

    void deleteByName(String name);
}
