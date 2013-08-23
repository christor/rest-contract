package org.crued.restcontract.server;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.spi.container.ContainerRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import org.crued.restcontract.Contract;
import org.crued.restcontract.HeaderMatcher;
import org.crued.restcontract.Rule;
import org.glassfish.grizzly.http.server.HttpServer;

@Path("/")
public class ContractServer {

    HttpServer server = null;
    private static final HashMap<String, Contract> contractMap = new HashMap<>();

    public void start(String baseUri, Contract contract) throws IOException {
        contractMap.put(baseUri, contract);
        server = GrizzlyServerFactory.createHttpServer(baseUri);
    }

    public void stop() {
        if (server != null) {
            server.stop();
        }
    }
    @Context
    Request request;

    @GET
    @Path("{subResources: .*}")
    public Response getMethod(@PathParam("subResources") String path, @Context Request request, @Context HttpHeaders headers) {
        String baseUri = ((ContainerRequest) request).getBaseUri().toASCIIString();
        String method = "GET";
        return handleRequest(baseUri, path, method, headers);
    }

    @POST
    @Path("{subResources: .*}")
    public Response postMethod(@PathParam("subResources") String path, @Context Request request, @Context HttpHeaders headers, String body) {
        String baseUri = ((ContainerRequest) request).getBaseUri().toASCIIString();
        String method = "POST";
        return handleRequest(baseUri, path, method, headers, body);
    }

    private Response handleRequest(String baseUri, String path, String method, HttpHeaders headers) {
        return handleRequest(baseUri, path, method, headers, null);
    }

    private Response handleRequest(String baseUri, String path, String method, HttpHeaders headers, String body) {
        Contract contract = contractMap.get(baseUri);
        if (contract != null) {
            return handleRequest(contract, path, method, headers, body);
        } else {
            System.err.println("Can't find contract for baseUri");
            return Response.serverError().build();
        }
    }

    private Response handleRequest(Contract contract, String path, String method, HttpHeaders headers, String body) {
        for (Rule rule : contract.getRules()) {
            final org.crued.restcontract.Request targetRequest = rule.getRequest();
            if (targetRequest.getMethod().matches(method)) {
                if (targetRequest.getPath().matches(path)) {
                    if (targetRequest.getHeaders().matches(headers.getRequestHeaders())) {
                        if (targetRequest.getBody().matches(body)) {
                            org.crued.restcontract.Response response = rule.getResponse();
                            Response.ResponseBuilder builder = Response.status(response.getStatus().getValue());
                            for (HeaderMatcher m : response.getHeaders()) {
                                builder.header(m.getKey(), m.getValue());
                            }
                            builder.entity(response.getBody().getOutputText());
                            return builder.build();
                        }
                    }
                }
            }
        }
        System.err.println("No rule matching request: " + method + " " + path);
        return Response.serverError().entity("No rule matching request").build();
    }
}
