package br.com.trip.handler;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.trip.dao.TripRepository;
import br.com.trip.model.HandlerRequest;
import br.com.trip.model.HandlerResponse;
import br.com.trip.model.Trip;

public class GetTripRecordsByPeriod implements RequestHandler<HandlerRequest, HandlerResponse> {

	private final TripRepository repository = new TripRepository();
	
	public HandlerResponse handleRequest(HandlerRequest request, Context context) {

		final String start = request.getQueryStringParameters().get("start");
		final String end = request.getQueryStringParameters().get("end");

		context.getLogger().log("Procurando por Trips entre  " + start + " and " + end);

		final List<Trip> trips = this.repository.findByPeriod(start, end);
		
		return HandlerResponse.builder().setStatusCode(200).setObjectBody(trips).build();
	}
}