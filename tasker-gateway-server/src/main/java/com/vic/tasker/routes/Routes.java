//package com.vic.tasker.routes;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
//import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
//import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.servlet.function.RequestPredicates;
//import org.springframework.web.servlet.function.RouterFunction;
//import org.springframework.web.servlet.function.ServerResponse;
//
//import java.net.URI;
//
//import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
//import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
//
//@Configuration
//public class Routes {
//
//    @Value("${user.service.url}")
//    private String userServiceUrl;
//    @Value("${task.service.url}")
//    private String taskServiceUrl;
//    @Value("${submission.service.url}")
//    private String submissionServiceUrl;
//
//
//
//    @Bean
//    public RouterFunction<ServerResponse> userServiceRoute() {
//        return GatewayRouterFunctions.route("TASKER-USER-SERVICE")
//                .route(RequestPredicates.path("/auth/**"), HandlerFunctions.http(userServiceUrl))
//                .route(RequestPredicates.path("/users/**"), HandlerFunctions.http(userServiceUrl))
//                .route(RequestPredicates.path("/api/users/**"), HandlerFunctions.http(userServiceUrl))
//                .route(RequestPredicates.path("/"), HandlerFunctions.http(userServiceUrl))
//                .filter(CircuitBreakerFilterFunctions.circuitBreaker("userServiceCircuitBreaker",
//                        URI.create("forward:/fallbackRoute")))
//                .build();
//    }
//
//
//    @Bean
//    public RouterFunction<ServerResponse> userServiceSwaggerRoute() {
//        return GatewayRouterFunctions.route("TASKER-USER-SERVICE-SWAGGER")
//                .route(RequestPredicates.path("/aggregate/TASKER-USER-SERVICE/v3/api-docs"), HandlerFunctions.http(userServiceUrl))
//                .filter(CircuitBreakerFilterFunctions.circuitBreaker("userServiceSwaggerCircuitBreaker",
//                        URI.create("forward:/fallbackRoute")))
//                .filter(setPath("/api-docs"))
//                .build();
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> taskServiceRoute() {
//        return GatewayRouterFunctions.route("TASKER-TASK-SERVICE")
//                .route(RequestPredicates.path("/api/tasks/**"), HandlerFunctions.http(taskServiceUrl))
//                .route(RequestPredicates.path("/tasks/**"), HandlerFunctions.http(taskServiceUrl))
//                .filter(CircuitBreakerFilterFunctions.circuitBreaker("taskServiceCircuitBreaker",
//                        URI.create("forward:/fallbackRoute")))
//                .build();
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> taskServiceSwaggerRoute() {
//        return GatewayRouterFunctions.route("TASKER-TASK-SERVICE-SWAGGER")
//                .route(RequestPredicates.path("/aggregate/TASKER-TASK-SERVICE/v3/api-docs"), HandlerFunctions.http(taskServiceUrl))
//                .filter(CircuitBreakerFilterFunctions.circuitBreaker("taskServiceSwaggerCircuitBreaker",
//                        URI.create("forward:/fallbackRoute")))
//                .filter(setPath("/api-docs"))
//                .build();
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> submissionServiceRoute() {
//        return GatewayRouterFunctions.route("TASKER-SUBMISSION-SERVICE")
//                .route(RequestPredicates.path("/api/submissions/**"), HandlerFunctions.http(submissionServiceUrl))
//                .route(RequestPredicates.path("/submissions/**"), HandlerFunctions.http(submissionServiceUrl))
//
//                .filter(CircuitBreakerFilterFunctions.circuitBreaker("submissionServiceCircuitBreaker",
//                        URI.create("forward:/fallbackRoute")))
//                .build();
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> submissionServiceSwaggerRoute() {
//        return GatewayRouterFunctions.route("TASKER-SUBMISSION-SERVICE-SWAGGER")
//                .route(RequestPredicates.path("/aggregate/TASKER-SUBMISSION-SERVICE/v3/api-docs"), HandlerFunctions.http(submissionServiceUrl))
//                .filter(CircuitBreakerFilterFunctions.circuitBreaker("submissionServiceSwaggerCircuitBreaker",
//                        URI.create("forward:/fallbackRoute")))
//                .filter(setPath("/api-docs"))
//                .build();
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> fallbackRoute() {
//        return route("fallbackRoute")
//                .GET("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
//                        .body("Service Unavailable, please try again later"))
//                .build();
//    }
//}
