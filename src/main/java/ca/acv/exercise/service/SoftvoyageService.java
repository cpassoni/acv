package ca.acv.exercise.service;

import ca.acv.exercise.consumer.Softvoyage;
import ca.acv.exercise.exception.NotFoundException;
import ca.acv.exercise.model.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SoftvoyageService {
	private final Softvoyage softvoyage;
	@Getter
	private Map<Integer, DestinationResource> destinationResourceMap;

	public void heatCache() {
		destinationResourceMap = process(softvoyage.getXml());
	}

	public DestinationResource updateDestinationName(Integer destinationId, String newDestinationName) {
		if (destinationResourceMap.containsKey(destinationId)) {
			final var destinationResource = destinationResourceMap.get(destinationId);
			destinationResource.setDestinationName(newDestinationName);
			return destinationResource;
		}
		throw new NotFoundException("Destination not found for Id");
	}

	public void deleteDestination(Integer destinationId) {
		if (!destinationResourceMap.containsKey(destinationId)) {
			throw new NotFoundException("Destination not found for Id");
		}
		destinationResourceMap.remove(destinationId);
	}

	private Map<Integer, DestinationResource> process(Xml xml) {
		if (xml == null) {
			throw new RuntimeException();
		}
		return xml.getGateways().getGateways().stream().filter(Objects::nonNull)
				.map(Gateway::getDestinationGroups).filter(Objects::nonNull)
				.flatMap(destinationGroups -> destinationGroups.getGroups().stream()).filter(Objects::nonNull)
				.map(Group::getDestinations).filter(Objects::nonNull)
				.flatMap(destinations -> destinations.getDestinations().stream()).filter(Objects::nonNull)
				.map(this::createDestinationResourceFrom).filter(
						destinationResource -> destinationResource.getDestinationCode() != null)
				.collect(Collectors.toConcurrentMap(DestinationResource::getDestinationCode,
						Function.identity(),
						mergeDestinations()));
	}

	private BinaryOperator<DestinationResource> mergeDestinations() {
		return (existing, replacement) -> {
			existing.getHotelCodes().addAll(replacement.getHotelCodes());
			return existing;
		};
	}

	private DestinationResource createDestinationResourceFrom(Destination destination) {
		if (Objects.isNull(destination)) {
			return DestinationResource.builder().build();
		}
		return DestinationResource.builder()
				.destinationCode(destination.getDestinationCode())
				.destinationName(destination.getDestinationName())
				.hotelCodes(getHotelCodes(destination))
				.build();
	}

	private Set<Integer> getHotelCodes(Destination destination) {
		if (Objects.isNull(destination)) {
			new HashSet<>();
		}
		return destination.getHotels() == null
				? new HashSet<>()
				: Arrays.stream(destination.getHotels().split(","))
				.filter(s -> !s.isBlank())
				.map(Integer::parseInt)
				.collect(Collectors.toSet());
	}
}
