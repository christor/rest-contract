package org.crued.restcontract;

import lombok.ToString;

@ToString
public class ExactBodyMatcher implements BodyMatcher {
    
    String pattern;

    public ExactBodyMatcher(String body) {
        this.pattern = body;
    }

    @Override
    public boolean matches(String bodyText) {
        return this.pattern.equals(bodyText);
    }

    @Override
    public String getOutputText() {
        return pattern;
    }
    
}
