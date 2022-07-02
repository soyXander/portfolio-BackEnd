package com.argentinaprograma.backend.service;

import com.argentinaprograma.backend.model.UserDetails;
import com.argentinaprograma.backend.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Xander.-
 */
@Service
@Transactional
public class UserDetailsService implements IUserDetailsService {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Override
    public List<UserDetails> list() {
        return userDetailsRepository.findAll();
    }

    @Override
    public void save(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
    }

    @Override
    public void update(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
    }

    @Override
    public void delete(Long id) {
        userDetailsRepository.deleteById(id);
    }

    @Override
    public UserDetails findById(Long id) {
        return userDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return userDetailsRepository.existsById(id);
    }

    @Override
    public Optional<UserDetails> getOne(Long id) {
        return userDetailsRepository.findById(id);
    }
}
