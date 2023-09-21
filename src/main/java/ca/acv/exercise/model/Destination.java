package ca.acv.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Destination {
	@JsonProperty("DEST_CODE")
	Integer destinationCode;
	@JsonProperty("DEST_NAME")
	String destinationName;

	@JsonProperty("HOTELS")
	String hotels;

}
