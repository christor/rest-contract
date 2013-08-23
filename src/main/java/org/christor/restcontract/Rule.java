package org.christor.restcontract;

import lombok.Data;
import org.christor.restcontract.Request.RequestBuilder;
import org.christor.restcontract.Response.ResponseBuilder;

@Data
public class Rule {

    Contract contract;
    Request request;
    Response response;
    Rule next;

    public Rule() {
    }

    public Rule(Request q, Response s) {
        this.request = q;
        this.response = s;
    }

    private Rule(RequestBuilder q, ResponseBuilder s) {
        this(q.build(), s.build());
    }

    public Rule then(Request q, Response s) {
        final Rule rule = new Rule(q, s);
        setNext(rule);
        return rule;
    }
    public Rule then(RequestBuilder q, ResponseBuilder s) {
        final Rule rule = new Rule(q.build(), s.build());
        setNext(rule);
        return rule;
    }

    public boolean hasNext() {
        return next != null;
    }
}
