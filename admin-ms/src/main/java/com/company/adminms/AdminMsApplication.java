package com.company.adminms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AdminMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminMsApplication.class, args);
	}

}
