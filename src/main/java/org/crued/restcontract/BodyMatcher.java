package org.crued.restcontract;

public interface BodyMatcher {
    boolean matches(String bodyText);
    String getOutputText();
}
