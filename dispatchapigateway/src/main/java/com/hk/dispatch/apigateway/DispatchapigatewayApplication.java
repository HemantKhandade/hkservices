package com.hk.dispatch.apigateway;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.hk.dispatch.apigateway.config.RedisHashBean;
import com.hk.dispatch.apigateway.dto.ApiKey;
import com.hk.dispatch.apigateway.route.DispatchRouteService;

@SpringBootApplication
public class DispatchapigatewayApplication {
	
	@Autowired
	private Environment env;
	
	 @Autowired
	 private RedisHashBean redisHashBean;
	 
	public static void main(String[] args) {
		SpringApplication.run(DispatchapigatewayApplication.class, args);
	}
	
	 @PostConstruct
    public void initKeysToRedis() {
	 
        List<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(new ApiKey(env.getProperty("all.api.access.key"), Stream.of(env.getProperty("appt.service.key"),
        		env.getProperty("assigntech.service.key")).collect(Collectors.toList())));
        apiKeys.add(new ApiKey(env.getProperty("appointment.api.access.key"), Stream.of(env.getProperty("appt.service.key"))
                .collect(Collectors.toList())));
       
        List<Object> lists = redisHashBean.hValues(env.getProperty("record.key"));
        if (lists.isEmpty()) {
            apiKeys.forEach(k -> redisHashBean.setKeyValues(env.getProperty("record.key"), k.getKey(), k));
        }
        
    }
	
	
	

}
