package br.com.trip.handler;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.trip.dao.TripRepository;
import br.com.trip.model.HandlerRequest;
import br.com.trip.model.HandlerResponse;
import br.com.trip.model.Trip;

public class GetTripRecordsByCountry implements RequestHandler<HandlerRequest, HandlerResponse> {

	private final TripRepository repository = new TripRepository();

	public HandlerResponse handleRequest(HandlerRequest request, Context context) {

		final String country = request.getPathParameters().get("country");

		context.getLogger()
				.log("Procurando por Trips no país " + country);
		
		final List<Trip> trips = this.repository.findByCountry(country);

		if (trips == null || trips.isEmpty()) {
			return HandlerResponse.builder().setStatusCode(404).build();
		}

		return HandlerResponse.builder().setStatusCode(200).setObjectBody(trips).build();
	}
}