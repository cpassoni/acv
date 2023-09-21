package ca.acv.exercise.controller;

import ca.acv.exercise.exception.InputException;
import ca.acv.exercise.exception.NotFoundException;
import ca.acv.exercise.model.DestinationResource;
import ca.acv.exercise.model.UpdateInput;
import ca.acv.exercise.service.SoftvoyageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/destination/hotels")
@RequiredArgsConstructor
public class HotelDestinationController {

	private final SoftvoyageService softvoyageService;

	@Operation(summary = "Retrieve hotels for all Destinations")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "All hotels from all destination")
	})
	@GetMapping(value = "", produces = "application/json")
	public Collection<DestinationResource> getAll() {
		return softvoyageService.getDestinationResourceMap().values();
	}

	@Operation(summary = "Retrieve hotels for a destination")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "All hotels from the destination"),
			@ApiResponse(responseCode = "404", description = "Destination not found", content = @Content)
	})
	@GetMapping(value = "/{destinationId}", produces = "application/json")
	public DestinationResource getHotelsByDestination(@PathVariable("destinationId") Integer destId) {
		if (softvoyageService.getDestinationResourceMap().containsKey(destId)) {
			return softvoyageService.getDestinationResourceMap().get(destId);
		}
		throw new NotFoundException("DestinationId not found");
	}

	@Operation(summary = "Change destination name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Changed destination name"),
			@ApiResponse(responseCode = "400", description = "New name contains invalid character",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Destination not found", content = @Content)
	})
	@PostMapping(value = "/{destinationId}", produces = "application/json", consumes = "application/json")
	public DestinationResource updateHotelName(@PathVariable("destinationId") Integer destId,
	                                           @RequestBody UpdateInput input) {
		validateInput(input);
		return softvoyageService.updateDestinationName(destId, input.getNewName());
	}

	@Operation(summary = "Remove destination")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Destination removed"),
			@ApiResponse(responseCode = "404", description = "Destination not found", content = @Content)
	})
	@DeleteMapping("/{destinationId}")
	public void deleteById(@PathVariable("destinationId") Integer destId) {
		softvoyageService.deleteDestination(destId);
	}

	private void validateInput(UpdateInput input) {
		if (Objects.isNull(input) || Objects.isNull(input.getNewName()) || !input.getNewName().matches("[a-zA-Z]+$")) {
			throw new InputException("New name contains invalid character");
		}
	}
}
