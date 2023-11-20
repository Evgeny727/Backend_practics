package ru.mrnightfury.pr7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@ComponentScan({"ru.mrnightfury.pr7.*", "ru.mrnightfury.pr7.db.redis"})
public class Pr7Application {
	public static void main(String[] args) {
		SpringApplication.run(Pr7Application.class, args);
	}
}
