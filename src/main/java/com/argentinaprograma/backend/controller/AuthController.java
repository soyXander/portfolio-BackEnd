package com.argentinaprograma.backend.controller;

import com.argentinaprograma.backend.model.ERole;
import com.argentinaprograma.backend.model.Role;
import com.argentinaprograma.backend.model.User;
import com.argentinaprograma.backend.payload.request.LoginRequest;
import com.argentinaprograma.backend.payload.request.SignupRequest;
import com.argentinaprograma.backend.payload.response.JwtResponse;
import com.argentinaprograma.backend.repository.RoleRepository;
import com.argentinaprograma.backend.repository.UserRepository;
import com.argentinaprograma.backend.security.jwt.JwtUtils;
import com.argentinaprograma.backend.service.UserDetailsImpl;
import com.argentinaprograma.backend.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Xander.-
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return new ResponseEntity(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles), HttpStatus.OK);
	}
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity(new Message("Nombre de usuario ya registrado!"), HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity(new Message("Existe una cuenta registrada con este email!"), HttpStatus.BAD_REQUEST);
		}

		User user = new User(
				signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.USER)
					.orElseThrow(() -> new RuntimeException("Error: No se encuentra el rol."));
			roles.add(userRole);
		}
		else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: No se encuentra el rol."));
						roles.add(adminRole);
						break;
					default:
						Role userRole = roleRepository.findByName(ERole.USER)
								.orElseThrow(() -> new RuntimeException("Error: No se encuentra el rol."));
						roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return new ResponseEntity(new Message("¡Usuario registrado exitosamente!"), HttpStatus.OK);
	}
}