package gorae.backend.constant.endpoint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GoogleEndpoint implements Endpoint {
    CREATE_SPACE("/v2/spaces");

    private final String path;
}
