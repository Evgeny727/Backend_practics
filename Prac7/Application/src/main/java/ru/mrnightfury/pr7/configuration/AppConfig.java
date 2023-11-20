package ru.mrnightfury.pr7.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.mrnightfury.pr7.db.redis.RedisLoader;
import ru.mrnightfury.pr7.web.filters.AuthFilter;
import ru.mrnightfury.pr7.web.filters.AuthFilterImpl;
import ru.mrnightfury.pr7.web.filters.AuthInterceptor;

import javax.sql.DataSource;

@Configuration
@EnableRedisRepositories("ru.mrnightfury.pr7.db.redis")
@EnableJpaRepositories("ru.mrnightfury.pr7.db.repository")
public class AppConfig implements WebMvcConfigurer {
	private RedisLoader redisLoader;

	@Value("${spring.datasource.url}")
	private String jdbcUrl;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Autowired
	public AppConfig(RedisLoader redisLoader) {
		this.redisLoader = redisLoader;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean
	public FilterRegistrationBean<AuthFilterImpl> customFilter() {
		FilterRegistrationBean<AuthFilterImpl> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new AuthFilterImpl());
		registrationBean.addUrlPatterns("/**");
		registrationBean.setOrder(10);

		return registrationBean;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthInterceptor(redisLoader)).addPathPatterns("/**");
	}
}
