package br.com.trip.handler;

import java.io.IOException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.trip.dao.TripRepository;
import br.com.trip.model.HandlerRequest;
import br.com.trip.model.HandlerResponse;
import br.com.trip.model.Trip;

public class CreateTripRecord implements RequestHandler<HandlerRequest, HandlerResponse> {
	
	private final TripRepository repository = new TripRepository();

	public HandlerResponse handleRequest(HandlerRequest request, Context context) {
		Trip trip = null;
		try {
			trip = new ObjectMapper().readValue(request.getBody(), Trip.class);
		} catch (IOException e) {
			return HandlerResponse.builder().setStatusCode(400).setRawBody("Ocorreu um erro ao salvar a Trip!").build();
		}
		context.getLogger().log("Criando uma nova Trip para o país " + trip.getCountry());
		final Trip tripRecorded = repository.save(trip);
		return HandlerResponse.builder().setStatusCode(201).setObjectBody(tripRecorded).build();
	}
	
}
