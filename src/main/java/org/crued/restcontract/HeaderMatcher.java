package org.crued.restcontract;

import javax.ws.rs.core.MultivaluedMap;

public interface HeaderMatcher {

    String getKey();

    String getValue();

    boolean matches(String key, String value);

    boolean matches(MultivaluedMap<String, String> responseHeaders);
}
