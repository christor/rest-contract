/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.christor.restcontract.client;

import lombok.Cleanup;
import org.christor.restcontract.Contract;
import org.christor.restcontract.Request;
import org.christor.restcontract.Response;
import org.christor.restcontract.RestContractViolationException;
import org.christor.restcontract.server.ContractServer;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alex Beggs
 */
public class ContractClientTest {

    @Test
    public void testNullContract() throws Exception {
        Contract contract = null;
        try {
            ContractClient client = new ContractClient(contract);
            client.run("http://localhost:5555");
            fail("Expected a NullPointerException");
        } catch (NullPointerException e) {
        }
    }

    @Test
    public void testMatchingContractWithServerAndClient() throws Exception {
        Contract contract = new Contract();
        final String baseUrl = "http://localhost:5555/";
        contract.rule(Request.get(baseUrl),
                Response.code(200).body("ThoughtStreams API")
                .header("Content-Type", "text/plain"));
        contract.rule(Request.get("foo"),
                Response.code(200)
                .body("bar")
                .header("Content-Type", "text/plain"));
        contract.rule(Request.get("fooh"),
                Response.code(200)
                .body("bar")
                .header("Content-Type", "text/html"));
        contract.rule(Request.get("asdfasdfa"),
                Response.code(404).body("Go away")
                .header("Content-Type", "text/html"))
                .then(Request.get("asdfasdfa"),
                Response.code(404)
                .body("Go away or I will taunt you a second time")
                .header("Content-Type", "text/html"));

        @Cleanup("stop")
        ContractServer server = new ContractServer();
        server.start(baseUrl, contract);
        ContractClient client = new ContractClient(contract);
        client.run(baseUrl);
    }

    @Test
    public void testNonMatchingContractWithServerAndClient() throws Exception {
        final String baseUrl = "http://localhost:5555/";
        Contract serverContract = new Contract();
        serverContract.rule(Request.get(baseUrl),
                Response.code(200).body("ThoughtStreams API")
                .header("Content-Type", "text/plain"));

        Contract clientContract = new Contract();
        clientContract.rule(Request.get(baseUrl),
                Response.code(200).body("Client expects a different body... Kate Upton's maybe?")
                .header("Content-Type", "text/plain"));

        @Cleanup("stop")
        ContractServer server = new ContractServer();
        server.start(baseUrl, serverContract);

        ContractClient client = new ContractClient(clientContract);
        try {
            client.run(baseUrl);
            fail("Should have thrown a RestContractViolationException because the body didn't match");
        } catch (RestContractViolationException e) {
            // expected
        }
    }
}