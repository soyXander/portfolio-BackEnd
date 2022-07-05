package com.argentinaprograma.backend.service;

import com.argentinaprograma.backend.model.Banner;
import com.argentinaprograma.backend.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Xander.-
 */
@Service
@Transactional
public class BannerService implements IBannerService {

    @Autowired
    BannerRepository bannerRepository;

    @Override
    public List<Banner> list() {
        return bannerRepository.findAll();
    }

    @Override
    public void save(Banner banner) {
        bannerRepository.save(banner);
    }

    @Override
    public void update(Banner banner) {
        bannerRepository.save(banner);
    }

    @Override
    public void delete(Long id) {
        bannerRepository.deleteById(id);
    }

    @Override
    public Banner findById(Long id) {
        return bannerRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return bannerRepository.existsById(id);
    }

    @Override
    public Optional<Banner> getOne(Long id) {
        return bannerRepository.findById(id);
    }
}
