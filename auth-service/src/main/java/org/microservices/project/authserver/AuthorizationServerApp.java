package org.microservices.project.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Authorization server. Generates OAuth2 JWT tokens for authenticated clients.*
 * @author mcaleerj
 */
@SpringBootApplication
@EnableEurekaClient
public class AuthorizationServerApp {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApp.class, args);
	}

}
