package org.crued.restcontract;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.crued.restcontract.Request.RequestBuilder;
import org.crued.restcontract.Response.ResponseBuilder;

@Data
public class Contract {

    List<Rule> rules = new ArrayList<>();

    public Contract() {
    }

    public void addRule(final Rule rule) {
        rules.add(rule);
    }

    public Rule rule(final Request q, final Response s) {
        final Rule rule = new Rule(q, s);
        rules.add(rule);
        return rule;
    }

    public Rule rule(final RequestBuilder q, final ResponseBuilder s) {
        return rule(q.build(), s.build());
    }
}
