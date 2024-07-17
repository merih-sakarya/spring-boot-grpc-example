package com.example.grpc.server;

import com.example.grpc.server.config.GrpcServerConfigProperties;
import com.example.grpc.server.service.BidirectionalStreamingService;
import com.example.grpc.server.service.ClientStreamingService;
import com.example.grpc.server.service.ServerStreamingService;
import com.example.grpc.server.service.UnaryService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class GrpcServerApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(GrpcServerApplication.class);

    private final GrpcServerConfigProperties grpcServerConfigProperties;

    public static void main(String[] args) {
        SpringApplication.run(GrpcServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Configure the gRPC server to listen on the specified port and add services.
        Server server = ServerBuilder.forPort(grpcServerConfigProperties.getPort())
                .addService(new UnaryService())
                .addService(new ServerStreamingService())
                .addService(new ClientStreamingService())
                .addService(new BidirectionalStreamingService())
                .build();

        server.start(); // Start the gRPC server
        logger.info("🚀 gRPC Server has successfully started on port 9090.");

        // Add a shutdown hook to gracefully stop the gRPC server when the JVM is shutting down
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("⚠️ Initiating shutdown of gRPC server...");
            server.shutdown();
            logger.info("✅ gRPC Server has been shut down gracefully.");
        }));

        // Keep the server running
        logger.info("💡 gRPC Server is running and awaiting termination...");
        server.awaitTermination();
    }
}
