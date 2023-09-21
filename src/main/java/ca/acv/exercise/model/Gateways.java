package ca.acv.exercise.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

import java.util.List;

@Data
public class Gateways {
	@JsonProperty("GATEWAY")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Gateway> gateways;

}
