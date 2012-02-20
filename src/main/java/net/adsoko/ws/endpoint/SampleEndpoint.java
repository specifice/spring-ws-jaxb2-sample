package net.adsoko.ws.endpoint;

import net.adsoko.ws.type.SampleRequest;
import net.adsoko.ws.type.SampleResponse;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SampleEndpoint {

	@PayloadRoot(namespace = "http://adsoko.net/ws/2012/Core", localPart = "sampleRequest")
	@ResponsePayload
	public SampleResponse processSampleRequest(@RequestPayload SampleRequest request) {
		SampleResponse result = new SampleResponse();
		result.setResponse("Request was: " + request.getRequest());
		return result;
	}
}