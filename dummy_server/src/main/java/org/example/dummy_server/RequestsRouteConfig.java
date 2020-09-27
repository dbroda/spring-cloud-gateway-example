package org.example.dummy_server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RequestsRouteConfig {


    @Bean
    public RouterFunction<ServerResponse> route(RequestHandler requestHandler) {

        return RouterFunctions
            .route(RequestPredicates.GET("/hello"), requestHandler::hello)
            .andRoute(RequestPredicates.GET("/bye"), requestHandler::bye);
    }
}
