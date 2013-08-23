package org.crued.restcontract;

import java.util.ArrayList;
import javax.ws.rs.core.MultivaluedMap;

public class HeaderMatcherArrayList extends ArrayList<HeaderMatcher> implements HeaderMatcherList {

    @Override
    public boolean matches(MultivaluedMap<String, String> responseHeaders) {
        boolean retVal = true;
        for (HeaderMatcher matcher : this) {
            if (!matcher.matches(responseHeaders)) {
                retVal = false;
                break;
            }
        }
        return retVal;
    }

}
