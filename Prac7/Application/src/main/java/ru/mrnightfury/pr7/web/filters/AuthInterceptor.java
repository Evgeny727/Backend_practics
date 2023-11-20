package ru.mrnightfury.pr7.web.filters;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ru.mrnightfury.pr7.db.model.UserRole;
import ru.mrnightfury.pr7.db.redis.RedisLoader;
import ru.mrnightfury.pr7.db.redis.TokenInformation;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class AuthInterceptor implements HandlerInterceptor {

	private final RedisLoader redisLoader;

	public AuthInterceptor(RedisLoader redisLoader) {
		this.redisLoader = redisLoader;
	}

	@Override
	public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
							 @NonNull Object handler) throws Exception {
		AuthFilter auth;
		try {
			auth = getAnnotation((HandlerMethod)handler);
		} catch (ClassCastException e) {
			return true;
		}

		if (auth == null) {
			return true;
		}
		String header = request.getHeader("Authorization");
		if (header == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		TokenInformation token = redisLoader.getTokenInformation(header.replace("Bearer ", ""));
		if (token == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		if (!canDo(token, auth)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
		request.setAttribute("user", token.getId());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
		// Логика, выполняемая после обработки запроса контроллером, но до возвращения ответа клиенту
		System.out.println("Interceptor postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		System.out.println("Interceptor afterCompletion");
	}

	private AuthFilter getAnnotation(HandlerMethod handler) {
		Method method = handler.getMethod();
		AuthFilter annotation = method.getAnnotation(AuthFilter.class);
		if (annotation != null) {
			return annotation;
		}
		return method.getDeclaringClass().getAnnotation(AuthFilter.class);
	}

	private boolean canDo(TokenInformation token, AuthFilter auth) {
		System.out.println(token.getRole() + "-" + auth.userRole());
		UserRole role = UserRole.valueOf(token.getRole());
		if (role == UserRole.ADMIN) {
			return true;
		}
		if (auth.id() != -1) {
			return auth.id() == token.getId();
		}
		if (auth.userRole() == UserRole.SELLER) {
			return role == UserRole.SELLER;
		}
		return true;
	}
}
