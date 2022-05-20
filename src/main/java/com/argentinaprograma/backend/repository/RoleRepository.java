package com.argentinaprograma.backend.repository;

import com.argentinaprograma.backend.model.ERole;
import com.argentinaprograma.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Xander.-
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
