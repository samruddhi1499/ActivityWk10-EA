package com.example;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoadBalancedService {

    private final RestTemplate restTemplate;

    public LoadBalancedService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getRemoteData() {
        return restTemplate.getForObject("http://target-service/endpoint", String.class);
    }
}
