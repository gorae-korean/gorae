package gorae.backend.constant.endpoint;

public interface Endpoint {
    String getPath();

    static String createUrl(String baseUrl, Endpoint endpoint) {
        return baseUrl + endpoint.getPath();
    }

    static String createUrl(String baseUrl, Endpoint endpoint, String... params) {
        return baseUrl + endpoint.getPath() + String.format("/%s", (Object) params);
    }
}
