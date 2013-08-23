package org.christor.restcontract;

public interface StatusCodeMatcher {
   boolean matches (int status);
   int getValue();
}
