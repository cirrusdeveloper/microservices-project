package org.microservices.project.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Configuration server - centralised microservice configuration.
 * Configurations maintained as local property files.
 * @author mcaleerj
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApp {
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApp.class, args);
	}
}
