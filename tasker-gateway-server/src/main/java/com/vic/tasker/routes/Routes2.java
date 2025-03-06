//package com.vic.tasker.routes;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.function.RequestPredicates;
//
//@Configuration
//public class Routes2 {
//
//    @Value("${user.service.url}")
//    private String userServiceUrl;
//
//    @Value("${task.service.url}")
//    private String taskServiceUrl;
//
//    @Value("${submission.service.url}")
//    private String submissionServiceUrl;
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("TASKER-USER-SERVICE", r -> r.path("/auth/**", "/users/**", "/api/users/**", "/")
//                     .uri(userServiceUrl))
//                .route("TASKER-TASK-SERVICE", r -> r.path("/api/tasks/**", "/tasks/**")
//                        .uri(taskServiceUrl))
//                .route("TASKER-SUBMISSION-SERVICE", r -> r.path("/api/submissions/**", "/submissions/**")
//                        .uri(submissionServiceUrl))
//                .route("swagger_route", r -> r.path("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/api-docs/**", "/aggregate/**"
//         )
//                        .uri("http://localhost:5000"))
//                .build();
//    }
//
//    @Bean
//    public RouteLocator SwaggerRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("TASKER-USER-SERVICE-SWAGGER", r -> r.path("/aggregate/TASKER-USER-SERVICE/v3/api-docs")
//                        .uri(userServiceUrl))
//                .route("TASKER-TASK-SERVICE-SWAGGER", r -> r.path("/aggregate/TASKER-TASK-SERVICE/v3/api-docs")
//                        .uri(taskServiceUrl))
//                .route("TASKER-SUBMISSION-SERVICE-SWAGGER", r -> r.path("/aggregate/TASKER-SUBMISSION-SERVICE/v3/api-docs")
//                        .uri(submissionServiceUrl))
//                .build();
//    }
//}
//
