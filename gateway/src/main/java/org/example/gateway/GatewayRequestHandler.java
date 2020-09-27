package org.example.gateway;

import java.util.function.Function;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component

public class GatewayRequestHandler {

    private final  WebClient webClient = WebClient.builder().baseUrl("http://localhost:18888").build();


    public Mono<ServerResponse> greet(ServerRequest request) {

        final String id = request.pathVariable("id");
        final String name = request.pathVariable("name");

        final Mono<ClientResponse> helloExchange = webClient.get()
            .uri("/hello?id={id}&name={name}", id, name)
            .exchange();

        final Mono<Hello> helloMono = helloExchange.flatMap(rsp -> rsp.bodyToMono(Hello.class));

        final Mono<Bye> byeMono = helloMono.flatMap(hello ->
            webClient.get()
                .uri("/bye?id={id}&name={name}", hello.getId()+1000, hello.getName()+"-proxied")
                .exchange()
        ).flatMap(rsp -> rsp.bodyToMono(Bye.class));


        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(byeMono, Bye.class);

    }
}
