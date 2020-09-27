package org.example.dummy_server;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RequestHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(Hello.builder()
                .id(request.queryParam("id").map(Long::parseLong).orElseThrow())
                .name(request.queryParam("name").orElseThrow())
                .build()), Hello.class);

    }

    public Mono<ServerResponse> bye(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(Bye.builder().name(getName(request)).build()), Bye.class);
    }

    private String getName(ServerRequest request) {
        return "bye dev id=" + request.queryParam("id") + ", name=" + request
            .queryParam("name");
    }
}
