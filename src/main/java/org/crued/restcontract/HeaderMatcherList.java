package org.crued.restcontract;

import java.util.List;
import javax.ws.rs.core.MultivaluedMap;

public interface HeaderMatcherList extends List<HeaderMatcher> {
    boolean matches(MultivaluedMap<String, String> responseHeaders);
}
