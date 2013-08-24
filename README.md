rest-contract
=============

An API for describing a REST contract via examples. This provides a client and a server that will run according to the rules in this contract. This was designed with testing RESTful clients and RESTful services independently.


**Create a Contract**

        Contract contract = new Contract();
        contract.rule(Request.get("api/v1/"), 
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




**Create a Server that will follow the rules**

        new ContractServer().start("http://localhost:5555/", contract);

_you can now test your RESTful client against this_
        



**Create a Client and run the rules**

        ContractClient client = new ContractClient(contract);
        client.run("http://localhost:5555/");
        Thread.sleep(1000000);
        
_you can now test your RESTful service using this_
