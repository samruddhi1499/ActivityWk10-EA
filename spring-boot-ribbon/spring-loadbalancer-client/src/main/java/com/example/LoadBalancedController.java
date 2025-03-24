package com.example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoadBalancedController {

    private final LoadBalancedService loadBalancedService;

    public LoadBalancedController(LoadBalancedService loadBalancedService) {
        this.loadBalancedService = loadBalancedService;
    }

    @GetMapping("/loadbalanced")
    public String loadBalancedRequest(@RequestHeader(value = "Client-Type", defaultValue = "web") String clientType) {
        return "Response from service: " + loadBalancedService.getRemoteData();
    }
}
