package org.christor.restcontract;

import java.util.List;
import java.util.Map.Entry;
import javax.ws.rs.core.MultivaluedMap;
import lombok.ToString;

@ToString
public class ExactHeaderMatcher implements HeaderMatcher {

    String key;
    String value;

    public ExactHeaderMatcher(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean matches(String key, String value) {
        return this.key.equals(key) && this.value.equals(value);
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean matches(MultivaluedMap<String, String> responseHeaders) {
        boolean retVal = false;
        for (Entry<String, List<String>> entry : responseHeaders.entrySet()) {
            for (String entryValue : entry.getValue()) {
                retVal = matches(entry.getKey(), entryValue);
                if (retVal) {
                    break;
                }
            }
        }
        return retVal;
    }
}
