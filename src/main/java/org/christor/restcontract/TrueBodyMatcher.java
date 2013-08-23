package org.christor.restcontract;

import lombok.ToString;

@ToString
public class TrueBodyMatcher implements BodyMatcher {

    public TrueBodyMatcher() {
    }

    public boolean matches(String bodyText) {
        return true;
    }

    @Override
    public String getOutputText() {
        return null;
    }
    
}
