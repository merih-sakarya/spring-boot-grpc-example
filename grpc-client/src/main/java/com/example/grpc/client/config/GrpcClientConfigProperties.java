package com.example.grpc.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "grpc.client")
public class GrpcClientConfigProperties {

    private String host;
    private int port;
}

