package ca.acv.exercise.task;

import ca.acv.exercise.service.SoftvoyageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TaskManager {
	private final SoftvoyageService softvoyageService;

	@Bean
	public void loadSoftvoyage() {
		softvoyageService.heatCache();
	}

}
