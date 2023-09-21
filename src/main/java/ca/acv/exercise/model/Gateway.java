package ca.acv.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Gateway {
	@JsonProperty("DESTINATIONS_GROUPS")
	private DestinationGroups destinationGroups;

}
