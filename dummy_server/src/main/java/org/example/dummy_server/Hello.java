package org.example.dummy_server;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Hello {

    private final String name;
    private final Long id;
}
