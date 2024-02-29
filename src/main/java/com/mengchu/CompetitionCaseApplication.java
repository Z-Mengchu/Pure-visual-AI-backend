package com.mengchu;

import com.mengchu.pojo.Modeling;
import com.mengchu.pojo.Rems;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.client.RestTemplate;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
/*继承SpringBootServletInitializer,并重写configure方法*/
public class CompetitionCaseApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CompetitionCaseApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CompetitionCaseApplication.class);
	}
}
