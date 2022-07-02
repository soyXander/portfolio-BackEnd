package com.argentinaprograma.backend.repository;

import com.argentinaprograma.backend.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Xander.-
 */
@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
}
