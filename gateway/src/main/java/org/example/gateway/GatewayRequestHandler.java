package org.example.gateway;

import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component

public class GatewayRequestHandler {

    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:18888")
        .build();


    private final WebClient ldClient = WebClient.builder().baseUrl("http://livedata-core.gazeta.pl")
        .build();


    public Mono<ServerResponse> greet(ServerRequest request) {

        final String id = request.pathVariable("id");
        final String name = request.pathVariable("name");

        final Mono<ClientResponse> helloExchange = webClient.get()
            .uri("/hello?id={id}&name={name}", id, name)
            .exchange();

        final Mono<Hello> helloMono = helloExchange.flatMap(rsp -> rsp.bodyToMono(Hello.class));

        final var livedataMono = helloMono.flatMap(hello ->
            ldClient.get()
                .uri("/livedata/football/eventhead/v1?languageId=23&ids={id}", hello.getId())
                .exchange()

        ).flatMap(rsp -> rsp.bodyToMono(List.class));



        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(livedataMono, List.class);

    }
}
