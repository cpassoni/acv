package ca.acv.exercise.service;

import ca.acv.exercise.consumer.Softvoyage;
import ca.acv.exercise.exception.NotFoundException;
import ca.acv.exercise.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SoftvoyageServiceTest {

	@Mock
	private Softvoyage softvoyage;

	@InjectMocks
	private SoftvoyageService softvoyageService;

	@Test
	public void shouldRenameDestinationResource() {
		final var sample = getSample();

		when(softvoyage.getXml()).thenReturn(sample);
		softvoyageService.heatCache();
		for (int i = 0; i < 100; i++) {
			int finalI = i;
			Thread newThread = new Thread(() ->
					assertEquals("newName" + finalI,
							softvoyageService.updateDestinationName(1, "newName" + finalI).getDestinationName()));
			newThread.start();
		}
	}

	@Test
	public void shouldRemoveDestinationResource() {
		final var sample = getSample();

		when(softvoyage.getXml()).thenReturn(sample);
		softvoyageService.heatCache();
		softvoyageService.deleteDestination(1);

		assertFalse(softvoyageService.getDestinationResourceMap().containsKey(1));
	}

	@Test
	public void process_shouldReturnCorrectDestinationResource() {
		final var sample = getSample();

		when(softvoyage.getXml()).thenReturn(sample);
		softvoyageService.heatCache();
		var result = softvoyageService.getDestinationResourceMap();
		assertEquals("name", result.get(1).getDestinationName());
		assertTrue(result.get(1).getHotelCodes().containsAll(Arrays.asList(1, 2, 3, 4, 5, 6)));
		assertFalse(result.containsKey(2));
		assertTrue(result.get(3).getHotelCodes().isEmpty());
		assertTrue(result.get(4).getHotelCodes().containsAll(Arrays.asList(5, 6, 3, 4)));
	}

	@Test
	public void process_shouldThrowExceptionWhenNoDestinationResourceFoundWhenUpdating() {
		final var sample = getSample();

		when(softvoyage.getXml()).thenReturn(sample);
		softvoyageService.heatCache();
		Throwable exception = assertThrows(NotFoundException.class,
				() -> softvoyageService.updateDestinationName(6, "asdf"));
		assertEquals("Destination not found for Id", exception.getMessage());
	}

	@Test
	public void process_shouldThrowExceptionWhenNoDestinationResourceFoundWhenDeleting() {
		final var sample = getSample();

		when(softvoyage.getXml()).thenReturn(sample);
		softvoyageService.heatCache();
		Throwable exception = assertThrows(NotFoundException.class, () -> softvoyageService.deleteDestination(6));
		assertEquals("Destination not found for Id", exception.getMessage());
	}

	private Xml getSample() {
		Xml sample = new Xml();
		final var gateways = new Gateways();
		final var gateway1 = new Gateway();
		final var gateway2 = new Gateway();
		gateways.setGateways(List.of(gateway1, gateway2));

		final var destinationGroups = new DestinationGroups();
		final var group1 = new Group();
		final var group2 = new Group();
		final var group3 = new Group();
		final var group4 = new Group();
		destinationGroups.setGroups(List.of(group1, group2, group3, group4));

		final var destinations = new Destinations();
		final var destination1 = Destination.builder().destinationCode(1).destinationName("name").hotels(
				"1,2,3,4").build();
		final var destination2 = Destination.builder().destinationCode(1).destinationName("name").hotels(
				"5,6,3,4").build();
		final var destination3 = Destination.builder().destinationCode(3).destinationName("name3").hotels("").build();
		final var destination4 = Destination.builder().destinationCode(4).destinationName("name4").hotels(
				"5,6,3,4").build();
		destinations.setDestinations(List.of(destination1, destination2, destination3, destination4));

		group1.setDestinations(destinations);
		group2.setDestinations(null);
		gateway1.setDestinationGroups(destinationGroups);
		sample.setGateways(gateways);
		return sample;
	}

}
