package org.crued.restcontract;

import lombok.Data;

@Data
public class Request {

    MethodMatcher method = new AnyMethodMatcher();
    PathMatcher path = new AnyPathMatcher();
    HeaderMatcherList headers = new HeaderMatcherArrayList();
    BodyMatcher body = new TrueBodyMatcher();

    public static RequestBuilder builder() {
        RequestBuilder builder = new RequestBuilder();
        return builder;
    }

    public static RequestBuilder put(String path) {
        return builder().put(path);
    }

    public static RequestBuilder get(String path) {
        return builder().get(path);
    }

    public static RequestBuilder post(String path) {
        return builder().post(path);
    }

    public static RequestBuilder header(final String key, final String value) {
        return builder().header(key, value);
    }

    public static RequestBuilder body(final String body) {
        return builder().body(body);
    }

    public static final class RequestBuilder {

        Request request = new Request();

        private RequestBuilder() {
        }

        public RequestBuilder put(String path) {
            request.setMethod(new ExactMethodMatcher("PUT"));
            request.setPath(new ExactPathMatcher(path));
            return this;
        }

        public RequestBuilder get(String path) {
            request.setMethod(new ExactMethodMatcher("GET"));
            request.setPath(new ExactPathMatcher(path));
            return this;
        }

        public RequestBuilder post(String path) {
            request.setMethod(new ExactMethodMatcher("POST"));
            request.setPath(new ExactPathMatcher(path));
            return this;
        }

        public RequestBuilder header(final String key, final String value) {
            request.getHeaders().add(new ExactHeaderMatcher(key, value));
            return this;
        }

        public RequestBuilder body(final String body) {
            request.setBody(new ExactBodyMatcher(body));
            return this;
        }

        public Request build() {
            return request;
        }
    }
}
