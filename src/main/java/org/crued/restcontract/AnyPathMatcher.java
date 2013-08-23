package org.crued.restcontract;

import lombok.ToString;

@ToString
public class AnyPathMatcher implements PathMatcher {

    public AnyPathMatcher() {
    }

    @Override
    public boolean matches(String method) {
        return true;
    }

    @Override
    public String getTextValue() {
        return "/default";
    }
    
}
