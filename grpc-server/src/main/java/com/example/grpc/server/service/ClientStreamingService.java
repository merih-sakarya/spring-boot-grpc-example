package com.example.grpc.server.service;

import com.example.grpc.ClientStreamingRequest;
import com.example.grpc.ClientStreamingResponse;
import com.example.grpc.ClientStreamingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientStreamingService extends ClientStreamingServiceGrpc.ClientStreamingServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(ClientStreamingService.class);

    @Override
    public StreamObserver<ClientStreamingRequest> clientStreamingCall(StreamObserver<ClientStreamingResponse> responseObserver) {
        return new StreamObserver<ClientStreamingRequest>() {
            StringBuilder messages = new StringBuilder();

            @Override
            public void onNext(ClientStreamingRequest value) {
                logger.info("Received client streaming request with message: {}", value.getMessage());
                messages.append(value.getMessage()).append(" ");
            }

            @Override
            public void onError(Throwable t) {
                logger.error("Error in client streaming call", t);
            }

            @Override
            public void onCompleted() {
                String responseMessage = "Client streaming response: " + messages.toString();
                ClientStreamingResponse response = ClientStreamingResponse.newBuilder().setMessage(responseMessage).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                logger.info("Client streaming completed with response: {}", response.getMessage());
            }
        };
    }
}
