package org.christor.restcontract;

public interface BodyMatcher {
    boolean matches(String bodyText);
    String getOutputText();
}
