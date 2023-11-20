package ru.mrnightfury.pr7.web;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mrnightfury.pr7.db.redis.RedisSaver;
import ru.mrnightfury.pr7.db.redis.TokenInformation;
import ru.mrnightfury.pr7.db.repository.UserRepository;
import ru.mrnightfury.pr7.db.model.User;
import ru.mrnightfury.pr7.services.JwtTokenProvider;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final RedisSaver redisSaver;

	@Autowired
	public AuthController(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, RedisSaver redisSaver) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.userRepository = userRepository;
		this.redisSaver = redisSaver;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		User user = userRepository.findByLogin(loginRequest.getUsername()).orElse(null);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		if (Objects.equals(user.getPassword(), loginRequest.getPassword())) {
			String token = jwtTokenProvider.generateToken(user);
			redisSaver.saveTokenInformation(token, new TokenInformation(user.getId(), user.getRole().toString()));
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
	}

	@GetMapping("/validate")
	public ResponseEntity<?> validate(@RequestHeader("Authorization") String authorizationHeader) {
		if (jwtTokenProvider.validateToken(extractToken(authorizationHeader))) {
			Long id = jwtTokenProvider.getUserIdFromToken(extractToken(authorizationHeader));
			User user = userRepository.findById(id).orElse(null);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}
			return ResponseEntity.ok(user.getRole().toString());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("There are no token");
	}

	@GetMapping("/check")
	public ResponseEntity<?> check(@RequestHeader("Authorization") String authorizationHeader) {
		if (jwtTokenProvider.validateToken(extractToken(authorizationHeader))) {
			TokenInformation token = redisSaver.getTokenInformation(extractToken(authorizationHeader));
			try {
				return ResponseEntity.ok(new Gson().toJson(token));
			} catch (NullPointerException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized");
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("There are no token");
	}

	private String extractToken(String authorizationHeader) {
		return authorizationHeader.replace("Bearer ", "");
	}
}
