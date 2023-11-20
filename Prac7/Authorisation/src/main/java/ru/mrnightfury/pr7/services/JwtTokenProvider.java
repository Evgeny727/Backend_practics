package ru.mrnightfury.pr7.services;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.mrnightfury.pr7.db.model.User;

import java.util.Date;

@Component
public class JwtTokenProvider {
	@Value("${app.jwtSecret}")
	private String secret;

	@Value("${app.expirationTimeInMs}")
	private int jwtExpirationTime;

	@SuppressWarnings("deprecation")
	public String generateToken(User user) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + jwtExpirationTime);

		return Jwts.builder()
				.subject(Long.toString(user.getId()))
				.issuedAt(now)
				.expiration(exp)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	@SuppressWarnings("deprecation")
	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(secret)
				.build()
				.parseClaimsJws(token)
				.getBody();
		return Long.parseLong(claims.getSubject());
	}

	@SuppressWarnings("deprecation")
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(secret).build().parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			System.out.println("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			System.out.println("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			System.out.println("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			System.out.println("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			System.out.println("JWT claims string is empty.");
		}
		return false;
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}

		return null;
	}
}
