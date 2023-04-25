package com.mystays.authorizationserver.constants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class HostUi {
    @Value("${host.ui}")
    public String hostUri;

    public String uriPath(String path) {
        return hostUri + path;
    }
}
