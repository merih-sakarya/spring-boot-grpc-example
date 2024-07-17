package com.example.grpc.server.service;

import com.example.grpc.UnaryRequest;
import com.example.grpc.UnaryResponse;
import com.example.grpc.UnaryServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnaryService extends UnaryServiceGrpc.UnaryServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(UnaryService.class);

    @Override
    public void unaryCall(UnaryRequest request, StreamObserver<UnaryResponse> responseObserver) {
        logger.info("Received unary call request with message: {}", request.getMessage());
        UnaryResponse response = UnaryResponse.newBuilder().setMessage("Unary response: " + request.getMessage()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        logger.info("Unary response sent: {}", response.getMessage());
    }
}