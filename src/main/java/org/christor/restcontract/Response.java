package org.christor.restcontract;

import lombok.Data;

@Data
public class Response {

    StatusCodeMatcher status = new ExactStatusCodeMatcher(200);
    HeaderMatcherList headers = new HeaderMatcherArrayList();
    BodyMatcher body = new TrueBodyMatcher();

    public static ResponseBuilder builder() {
        ResponseBuilder builder = new ResponseBuilder();
        return builder;
    }

    public static ResponseBuilder body(String body) {
        return builder().body(body);
    }

    public static ResponseBuilder code(int code) {
        return builder().status(code);
    }

    public static ResponseBuilder header(String k, String v) {
        return builder().header(k, v);
    }

    public static final class ResponseBuilder {

        Response response = new Response();

        private ResponseBuilder() {
        }

        public ResponseBuilder status(int statusCode) {
            response.setStatus(new ExactStatusCodeMatcher(statusCode));
            return this;
        }

        public ResponseBuilder header(final String key, final String value) {
            response.getHeaders().add(new ExactHeaderMatcher(key, value));
            return this;
        }

        public ResponseBuilder body(final String body) {
            response.setBody(new ExactBodyMatcher(body));
            return this;
        }

        public Response build() {
            return response;
        }
    }
}
