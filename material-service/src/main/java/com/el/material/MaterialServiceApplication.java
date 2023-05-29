package com.el.material;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MaterialServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MaterialServiceApplication.class, args);
    }
}