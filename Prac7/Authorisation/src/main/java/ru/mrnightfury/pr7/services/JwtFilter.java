package ru.mrnightfury.pr7.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.mrnightfury.pr7.services.JwtTokenProvider;

import java.io.IOException;

@Deprecated
@Component
public class JwtFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public JwtFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = jwtTokenProvider.resolveToken(request);

			if (token != null && jwtTokenProvider.validateToken(token)) {
				// Если токен валиден, устанавливаем аутентификацию в SecurityContext
//				SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
			}
		} catch (Exception ex) {
			// В случае ошибки, не обрабатываем и продолжаем цепочку фильтров
			logger.error("Could not set user authentication in security context", ex);
		}

		// Пропускаем запрос к следующему фильтру в цепочке
		filterChain.doFilter(request, response);
	}
}