package ru.mrnightfury.pr7.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mrnightfury.pr7.db.model.UserRole;
import ru.mrnightfury.pr7.db.redis.RedisLoader;
import ru.mrnightfury.pr7.db.repository.UserRepository;
import ru.mrnightfury.pr7.db.model.User;
import ru.mrnightfury.pr7.web.filters.AuthFilter;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	private final UserRepository userRepository;
	private final RedisLoader redisLoader;

	@Autowired
	public ClientController(UserRepository userRepository, RedisLoader redisLoader) {
		this.userRepository = userRepository;
		this.redisLoader = redisLoader;
	}

	@GetMapping("")
	public List<User> getAllClients() {
		return userRepository.findAll();
	}

	@GetMapping("/{id}")
	public User getClientById(@PathVariable Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@PostMapping("")
	public User createClient(@RequestBody User user) {
		if (userRepository.findByLogin(user.getLogin()).isEmpty()) {
			return userRepository.save(user);
		}
		return null;
	}

	@PutMapping("/{id}")
	public User updateClient(@PathVariable Long id, @RequestBody User user, @RequestAttribute("user") Long userId) {
		if (!Objects.equals(userId, id)) {
			return null;
		}
		if (userRepository.existsById(id)) {
			user.setId(id);
			return userRepository.save(user);
		}
		return null;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable Long id, @RequestAttribute("user") Long userId) {
		if (Objects.equals(userId, id)) {
			userRepository.deleteById(id);
			return ResponseEntity.ok("");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
	}

	@AuthFilter(userRole = UserRole.CLIENT)
	@PutMapping("/{id}/becomeSeller")
	public ResponseEntity<?> becomeSeller(@PathVariable Long id, @RequestAttribute("user") Long userId,
										  @RequestHeader String Authorization) {
		if (!Objects.equals(userId, id)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong user");
		}
		User user = userRepository.getReferenceById(id);
		user.setRole(UserRole.SELLER);
		userRepository.save(user);
		redisLoader.deleteTokenInformation(Authorization.replace("Bearer ", ""));
		return ResponseEntity.ok("");
	}

	@AuthFilter(userRole = UserRole.SELLER)
	@PutMapping("/{id}/becomeClient")
	public ResponseEntity<?> becomeClient(@PathVariable Long id, @RequestAttribute("user") Long userId,
										  @RequestHeader String Authorization) {
		if (!Objects.equals(userId, id)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong user");
		}
		User user = userRepository.getReferenceById(id);
		user.setRole(UserRole.CLIENT);
		userRepository.save(user);
		redisLoader.deleteTokenInformation(Authorization.replace("Bearer ", ""));
		return ResponseEntity.ok("");
	}

	@AuthFilter
	@PutMapping("/{id}/becomeAdmin")
	public ResponseEntity<?> becomeAdmin(@PathVariable Long id, @RequestAttribute("user") Long userId,
										 @RequestHeader String Authorization) {
		if (!Objects.equals(userId, id)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong user");
		}
		User user = userRepository.getReferenceById(id);
		user.setRole(UserRole.ADMIN);
		userRepository.save(user);
		redisLoader.deleteTokenInformation(Authorization.replace("Bearer ", ""));
		return ResponseEntity.ok("");
	}
}
