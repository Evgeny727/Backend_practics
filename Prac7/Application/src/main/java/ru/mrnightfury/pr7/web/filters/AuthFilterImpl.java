package ru.mrnightfury.pr7.web.filters;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import ru.mrnightfury.pr7.db.model.UserRole;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Deprecated
@Order(10)
@Component
public class AuthFilterImpl implements Filter {
	private UserRole role;
	private Long id;



	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		HttpServletRequest req = (HttpServletRequest) request;
//		AuthFilter filter = getAnnotation(req);
//		if (filter == null) {
//			chain.doFilter(req, response);
//			System.out.println("Filter is not called");
//			return;
//		}
//		System.out.println("Filter is called");
		chain.doFilter(request, response);
	}

//	private AuthFilter getAnnotation(HttpServletRequest request) {
//		System.out.println("asdasd");
//		String methodName = "";
////		String methodName = request.getAttribute("org.springframework.web.reactive.HandlerMapping.bestMatchingHandler").toString();
//		try {
//			Class<?> declaringClass = Class.forName(methodName.split(" ")[0]);
//			if (declaringClass.isAnnotationPresent(AuthFilter.class)) {
//				return declaringClass.getAnnotation(AuthFilter.class);
//			}
//			String methodNameOnly = methodName.split(" ")[1];
//			Method method = Arrays.stream(declaringClass.getMethods())
//					.filter(m -> m.getName().equals(methodNameOnly))
//					.findFirst()
//					.orElse(null);
//
//			if (method != null && method.isAnnotationPresent(AuthFilter.class)) {
//				return method.getAnnotation(AuthFilter.class);
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}
