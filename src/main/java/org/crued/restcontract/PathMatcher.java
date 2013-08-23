package org.crued.restcontract;

public interface PathMatcher {

    boolean matches(String path);

    String getTextValue();
}
