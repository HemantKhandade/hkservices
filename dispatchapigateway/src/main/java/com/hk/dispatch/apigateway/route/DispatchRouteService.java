package com.hk.dispatch.apigateway.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DispatchRouteService {
	
	
	@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		System.out.println("Builing the routes");
        return builder.routes()
        		.route("APPOINTMENT_SERVICE_KEY",
        				r -> r.path("/appointment/**")
        					  .uri("http://localhost:8081/"))
        		.route("ASSIGNTECH_SERVICE_KEY",
        				r -> r.path("/assigntech/**")
        				.uri("http://localhost:8082/"))
        		.build();
    }
}
