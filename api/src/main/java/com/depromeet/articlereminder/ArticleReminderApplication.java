package com.depromeet.articlereminder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@EnableJpaAuditing
@EnableAspectJAutoProxy
@SpringBootApplication
@Slf4j
public class ArticleReminderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticleReminderApplication.class, args);

		log.error("error");
		log.warn("warn");
		log.info("info");
		log.trace("trace");
		log.debug("debug");
	}

}
