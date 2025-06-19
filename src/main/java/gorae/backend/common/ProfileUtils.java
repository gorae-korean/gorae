package gorae.backend.common;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ProfileUtils {
    private final Environment environment;

    public boolean isProdMode() {
        return Arrays.asList(environment.getActiveProfiles())
                .contains("prod");
    }
}
