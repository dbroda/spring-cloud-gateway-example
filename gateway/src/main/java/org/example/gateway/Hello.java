package org.example.gateway;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Hello {

    private final String name;
    private final Long id;
}
