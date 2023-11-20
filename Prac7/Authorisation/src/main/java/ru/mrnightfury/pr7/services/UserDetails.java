package ru.mrnightfury.pr7.services;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.mrnightfury.pr7.db.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Deprecated
@Getter
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
	private Long id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public UserDetails(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserDetails build(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

		return new UserDetails(
				user.getId(),
				user.getLogin(),
				user.getPassword(),
				authorities
		);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
