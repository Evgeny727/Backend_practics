package ru.mrnightfury.pr7.web.filters;

import org.springframework.context.annotation.Import;
import ru.mrnightfury.pr7.db.model.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthFilter {
	UserRole userRole() default UserRole.CLIENT;
	long id() default -1L;
}