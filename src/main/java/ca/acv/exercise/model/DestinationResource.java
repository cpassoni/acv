package ca.acv.exercise.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class DestinationResource {
	Integer destinationCode;
	String destinationName;
	Set<Integer> hotelCodes;

}
