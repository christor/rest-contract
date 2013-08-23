package org.crued.restcontract;

public class RuleBuilder {
    private Rule rule = new Rule();
    private Contract contract;
    
    public RuleBuilder(Contract contract) {
        rule.setContract(contract);
    }
    
    public RuleBuilder expect(Response resonse) {
        rule.setResponse(resonse);
        return this;
    }
    
    public RuleBuilder request(Request request) {
        rule.setRequest(request);
        return this;
    }
    
    public void add() {
        contract.addRule(rule);
    }
    
}
