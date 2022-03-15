package com.example.demo;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.example.demo.resource.UserResource;

@Component
@ApplicationPath("/api")
public class ApiConfig extends ResourceConfig {
 public ApiConfig() {
	 register(UserResource.class);
 }
}
