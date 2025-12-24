package com.trendkeyword.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@Slf4j
public class RestClientConfig {

    @Bean
    public RestClient restClient(){
        return RestClient.builder()
                .requestInterceptor((request, body, execution) -> {
                   log.info("[API REQUEST] {} {}]",request.getMethod(),request.getURI());
                   return execution.execute(request,body);
                })
                .build();
    }
}
