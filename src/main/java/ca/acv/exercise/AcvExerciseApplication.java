package ca.acv.exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "ca.acv.exercise.consumer")
public class AcvExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcvExerciseApplication.class, args);
	}

}
