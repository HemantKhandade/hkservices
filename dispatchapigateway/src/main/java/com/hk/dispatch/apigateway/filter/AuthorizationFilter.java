package com.hk.dispatch.apigateway.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import com.hk.dispatch.apigateway.config.RedisHashBean;
import com.hk.dispatch.apigateway.dto.ApiKey;
import com.hk.dispatch.apigateway.util.MapperUtils;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
/**
 * 
 * @author DELL
 *
 */
@Component
@Slf4j
public class AuthorizationFilter implements GlobalFilter, Ordered {
	
	@Autowired
	private Environment env;
	
	@Autowired
	 private RedisHashBean redisHashBean;
	
	@Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) 
	{
		log.info("In AuthorizationFilter::Filter method ");
        List<String> apiKeyHeader=exchange.getRequest().getHeaders().get("gatewaykey");
        log.info("api key {} ",apiKeyHeader);
        Route route=exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        String routeId=route!=null? route.getId() : null;

        if(routeId ==null || CollectionUtils.isEmpty(apiKeyHeader) || !isAuthorize(routeId, apiKeyHeader.get(0))){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"you can't consume this service , Please validate your apikeys");
        }return chain.filter(exchange);

    }
	
	
	@Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
	
	private boolean isAuthorize(String routeId,String apiKey){
        Object apiKeyObject=redisHashBean.hGet(env.getProperty("record.key"), apiKey);
        if(apiKeyObject!=null){
            ApiKey key= MapperUtils.objectMapper(apiKeyObject, ApiKey.class);
            return key.getServices().contains(routeId);
        }else{
            return false;
        }
    }
}
