package org.christor.restcontract;

import lombok.ToString;

@ToString
public class ExactMethodMatcher implements MethodMatcher {

    String method;
    
    public ExactMethodMatcher(String method) {
        this.method = method;
    }

    @Override
    public boolean matches(String method) {
        return this.method.equals(method);
    }

    @Override
    public String getTextValue() {
        return method;
    }
    
}
