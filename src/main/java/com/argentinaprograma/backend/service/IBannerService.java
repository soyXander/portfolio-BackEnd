package com.argentinaprograma.backend.service;

import com.argentinaprograma.backend.model.Banner;

import java.util.List;
import java.util.Optional;

/**
 * @author Xander.-
 */
public interface IBannerService {
    List<Banner> list();
    void save(Banner banner);
    void update(Banner banner);
    void delete(Long id);
    Banner findById(Long id);
    boolean existsById(Long id);
    Optional<Banner> getOne(Long id);
}
