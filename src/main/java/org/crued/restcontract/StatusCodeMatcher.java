package org.crued.restcontract;

public interface StatusCodeMatcher {
   boolean matches (int status);
   int getValue();
}
