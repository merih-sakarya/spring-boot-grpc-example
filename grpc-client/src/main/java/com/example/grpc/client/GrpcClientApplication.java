package com.example.grpc.client;

import com.example.grpc.client.service.GrpcClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GrpcClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(GrpcClientService grpcClientService) {
        return args -> {
            // Unary RPC call
            grpcClientService.unaryCall("Performing unary call...");

            // Server Streaming RPC call
            grpcClientService.serverStreamingCall("Performing server streaming call...");

            // Client Streaming RPC call
            grpcClientService.clientStreamingCall();

            // Bidirectional Streaming RPC call
            grpcClientService.bidirectionalStreamingCall();
        };
    }
}
