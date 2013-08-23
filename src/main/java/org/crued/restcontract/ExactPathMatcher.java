package org.crued.restcontract;

import lombok.ToString;

@ToString
public class ExactPathMatcher implements PathMatcher {

    String path;
    
    public ExactPathMatcher(String path) {
        this.path = path;
    }

    @Override
    public boolean matches(String path) {
        return this.path.equals(path);
    }

    @Override
    public String getTextValue() {
        return this.path;
    }
    
}
