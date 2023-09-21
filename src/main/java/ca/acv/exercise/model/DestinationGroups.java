package ca.acv.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DestinationGroups {
	@JsonProperty("GROUP")
	@JacksonXmlElementWrapper(useWrapping = false)
	List<Group> groups;

}
