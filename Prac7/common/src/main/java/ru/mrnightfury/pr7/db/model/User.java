package ru.mrnightfury.pr7.db.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "clients")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;
	private String login;
	private String password;

	@Enumerated(EnumType.STRING)
	private UserRole role = UserRole.CLIENT;
}
