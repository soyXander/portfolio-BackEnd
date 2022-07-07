package com.argentinaprograma.backend.security.jwt;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.argentinaprograma.backend.dto.JwtDTO;
import com.argentinaprograma.backend.service.UserImpl;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

/**
 * @author Xander.-
 */
@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	@Value("${argprog.backend.jwtSecret}")
	private String jwtSecret;
	@Value("${argprog.backend.jwtExpirationMs}")
	private int jwtExpirationMs;
	public String generateJwtToken(Authentication auth) {
		UserImpl userPrincipal = (UserImpl) auth.getPrincipal();
		List<String> roles = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		return Jwts.builder()
				.setSubject(userPrincipal.getUsername())
				.claim("roles", roles)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

	public String refreshToken(JwtDTO jwtDTO) throws ParseException {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtDTO.getToken());
		} catch (ExpiredJwtException e) {
			JWT jwt = JWTParser.parse(jwtDTO.getToken());
			JWTClaimsSet claims = jwt.getJWTClaimsSet();
			String userName = claims.getSubject();
			List<String> roles = (List<String>)claims.getClaim("roles");

			String refreshedToken = Jwts.builder()
					.setSubject(userName)
					.claim("roles", roles)
					.setIssuedAt(new Date())
					.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
					.signWith(SignatureAlgorithm.HS512, jwtSecret)
					.compact();
			return refreshedToken;
		}
		return null;
	}
}
