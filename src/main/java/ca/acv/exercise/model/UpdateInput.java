package ca.acv.exercise.model;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateInput {
	@Pattern(regexp = "[a-zA-Z]+$")
	private String newName;
}
