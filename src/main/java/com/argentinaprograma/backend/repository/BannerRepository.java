package com.argentinaprograma.backend.repository;

import com.argentinaprograma.backend.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Xander.-
 */
@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
}
