package com.example;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplierBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class LoadBalancerConfiguration {

	@Bean
    public ServiceInstanceListSupplier serviceInstanceListSupplier(ServiceInstanceListSupplierBuilder builder) {
        // This gets the default delegate
        ServiceInstanceListSupplier delegate = builder.build(null);
        return new ClientTypeBasedServiceInstanceListSupplier(delegate);
    }

    static class ClientTypeBasedServiceInstanceListSupplier implements ServiceInstanceListSupplier {

        private final ServiceInstanceListSupplier delegate;

        public ClientTypeBasedServiceInstanceListSupplier(ServiceInstanceListSupplier delegate) {
            this.delegate = delegate;
        }

        @Override
        public String getServiceId() {
            return delegate.getServiceId();
        }

        @Override
        public Flux<List<ServiceInstance>> get() {
            return delegate.get().map(this::filterByClientType);
        }
        private List<ServiceInstance> filterByClientType(List<ServiceInstance> instances) {
            String clientType = getClientTypeFromContext();
            return instances.stream()
                    .filter(instance -> clientType.equals(instance.getMetadata().get("clientType")))
                    .collect(Collectors.toList());
        }

        private String getClientTypeFromContext() {
            // Logic to determine client type (e.g., based on headers)
            return "web"; // Default example
        }
    }
}
