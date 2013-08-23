package org.christor.restcontract;

public interface MethodMatcher {
    public boolean matches(String method);
    public String getTextValue();
}
