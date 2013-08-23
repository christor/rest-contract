package org.crued.restcontract;

import lombok.ToString;

@ToString
public class AnyMethodMatcher implements MethodMatcher {

    public AnyMethodMatcher() {
    }

    @Override
    public boolean matches(String method) {
        return true;
    }

    @Override
    public String getTextValue() {
        return "GET";
    }
    
}
