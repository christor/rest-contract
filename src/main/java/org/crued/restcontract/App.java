package org.crued.restcontract;

import java.io.IOException;
import org.crued.restcontract.client.ContractClient;
import org.crued.restcontract.server.ContractServer;

public class App {

    public static void main(String[] args) throws RestContractViolationException, IOException, InterruptedException {
        Contract contract = new Contract("https://thoughtstreams.io");
        contract.rule(Request.get("api/v1/"), Response.code(200).body("ThoughtStreams API").header("Content-Type", "text/plain"));
        contract.rule(Request.get("foo"), Response.code(200).body("bar").header("Content-Type", "text/plain"));
        contract.rule(Request.get("fooh"), Response.code(200).body("bar").header("Content-Type", "text/html"));

        new ContractServer().start("http://localhost:5555/", contract);
        
        ContractClient client = new ContractClient(contract);
        client.run("http://localhost:5555/");
        Thread.sleep(1000000);
    }
}
