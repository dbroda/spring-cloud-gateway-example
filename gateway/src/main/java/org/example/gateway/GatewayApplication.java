package org.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("path_route", r -> r.path("/hi**")
                .filters(f -> f.rewritePath("/hi(?<RID>.*)", "/hello${RID}"))
                .uri("http://localhost:18888"))
            .build();
    }

    @Bean
    public RouterFunction<ServerResponse> handleConfig(GatewayRequestHandler gatewayRequestHandler) {

        return RouterFunctions
            .route(RequestPredicates.GET("/yo/{id}/{name}"), gatewayRequestHandler::greet);
    }
}
