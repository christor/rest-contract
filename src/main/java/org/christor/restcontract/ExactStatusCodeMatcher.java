package org.christor.restcontract;

import lombok.ToString;

@ToString
public class ExactStatusCodeMatcher implements StatusCodeMatcher {

    private final int status;

    public ExactStatusCodeMatcher(int status) {
        this.status = status;
    }

    @Override
    public boolean matches(int status) {
        return this.status == status;
    }

    @Override
    public int getValue() {
        return status;
    }
}
