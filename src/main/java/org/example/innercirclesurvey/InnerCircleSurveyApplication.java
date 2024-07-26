package org.example.innercirclesurvey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class InnerCircleSurveyApplication {

	public static void main(String[] args) {
		SpringApplication.run(InnerCircleSurveyApplication.class, args);
	}

}
