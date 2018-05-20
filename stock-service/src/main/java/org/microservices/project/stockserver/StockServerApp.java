package org.microservices.project.stockserver;

import org.microservices.project.stockserver.config.ServerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

/**
 * Stock Server microservice.
 * @author mcaleerj
 */
@SpringBootApplication
@EnableEurekaClient
@Import(ServerConfiguration.class)
public class StockServerApp {

	public static void main(String[] args) {
		SpringApplication.run(StockServerApp.class, args);
	}
}
