package org.crued.restcontract.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.filter.ClientFilter;
import java.net.MalformedURLException;
import javax.ws.rs.core.MultivaluedMap;
import org.crued.restcontract.BodyMatcher;
import org.crued.restcontract.Contract;
import org.crued.restcontract.HeaderMatcher;
import org.crued.restcontract.Request;
import org.crued.restcontract.Response;
import org.crued.restcontract.RestContractViolationException;
import org.crued.restcontract.Rule;

public class ContractClient {

    Contract contract;

    public ContractClient(Contract contract) {
        this.contract = contract;
    }

    public void run(String baseUrl) throws RestContractViolationException {
        for (Rule rule : contract.getRules()) {
            try {
                runRule(rule, baseUrl);
            } catch (MalformedURLException ex) {
                throw new RestContractViolationException("Error executing rule: " + rule, ex);
            }
        }
    }

    public void runRule(Rule rule, String baseUrl) throws MalformedURLException, RestContractViolationException {
        final Client jerseyClient = Client.create();
        final ResponseHeaderCollectorClientFilter clientFilter = new ResponseHeaderCollectorClientFilter();
        final Request request = rule.getRequest();
        final String urlString = baseUrl + request.getPath().getTextValue();
        final WebResource resource = jerseyClient.resource(urlString);
        jerseyClient.addFilter(clientFilter);
        Builder builder = resource.getRequestBuilder();
        for (HeaderMatcher matcher : request.getHeaders()) {
            builder = resource.header(matcher.getKey(), matcher.getValue());
        }
        if (request.getBody() != null && request.getBody().getOutputText() != null) {
            builder.entity(request.getBody().getOutputText());
        }
        try {
            builder.method(request.getMethod().getTextValue());
        } catch (UniformInterfaceException ex) {
            // non-2xx response codes will cause us to land here
        }
        final String responseText = clientFilter.getBody();
        final Response response = rule.getResponse();
        if (!response.getStatus().matches(clientFilter.getStatus())) {
            throw new RestContractViolationException("Error in rule: " + rule + " Unexpected HTTP Status Code. Expected: " + response.getStatus().getValue() + " but got " + clientFilter.getStatus());
        }
        BodyMatcher body = response.getBody();
        if (!body.matches(responseText)) {
            throw new RestContractViolationException("Unexpected response body. Expected: " + body + " But got: " + responseText);
        }
        if (!response.getHeaders().matches(clientFilter.getResponseHeaders())) {
            throw new RestContractViolationException("Not all required headers found");
        }
        if (rule.hasNext()) {
            runRule(rule.getNext(), baseUrl);
        }
    }

    private static class ResponseHeaderCollectorClientFilter extends ClientFilter {

        private MultivaluedMap<String, String> responseHeaders;
        private int status;
        private String body;

        public ResponseHeaderCollectorClientFilter() {
        }

        @Override
        public ClientResponse handle(ClientRequest request) throws ClientHandlerException {
            ClientResponse response = getNext().handle(request);
            this.responseHeaders = response.getHeaders();
            this.status = response.getStatus();
            response.bufferEntity();
            this.body = response.getEntity(String.class);
            return response;
        }

        public MultivaluedMap<String, String> getResponseHeaders() {
            return responseHeaders;
        }

        public int getStatus() {
            return status;
        }

        public String getBody() {
            return body;
        }
    }
}
