package com.mengchu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//继承SpringBootServletInitializer,重写configure方法
public class CompetitionCaseApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CompetitionCaseApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CompetitionCaseApplication.class);
	}
}
