package com.example.grpc.server.service;

import com.example.grpc.ServerStreamingRequest;
import com.example.grpc.ServerStreamingResponse;
import com.example.grpc.ServerStreamingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerStreamingService extends ServerStreamingServiceGrpc.ServerStreamingServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(ServerStreamingService.class);

    @Override
    public void serverStreamingCall(ServerStreamingRequest request, StreamObserver<ServerStreamingResponse> responseObserver) {
        logger.info("Received server streaming call request with message: {}", request.getMessage());

        for (int i = 1; i <= 5; i++) {
            ServerStreamingResponse response = ServerStreamingResponse.newBuilder().setMessage("Server streaming response " + i + ": " + request.getMessage()).build();
            responseObserver.onNext(response);
            logger.info("Sent server streaming response: {}", response.getMessage());
        }

        responseObserver.onCompleted();
        logger.info("Server streaming completed.");
    }
}
