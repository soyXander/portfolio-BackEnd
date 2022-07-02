package com.argentinaprograma.backend.service;

import com.argentinaprograma.backend.model.UserDetails;

import java.util.List;
import java.util.Optional;

/**
 * @author Xander.-
 */
public interface IUserDetailsService {
    List<UserDetails> list();
    void save(UserDetails userDetails);
    void update(UserDetails userDetails);
    void delete(Long id);
    UserDetails findById(Long id);
    boolean existsById(Long id);
    Optional<UserDetails> getOne(Long id);
}
