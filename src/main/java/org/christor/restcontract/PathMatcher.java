package org.christor.restcontract;

public interface PathMatcher {

    boolean matches(String path);

    String getTextValue();
}
