package com.example.grpc.client.service;

import com.example.grpc.*;
import com.example.grpc.client.config.GrpcClientConfigProperties;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class GrpcClientService {

    private static final Logger logger = LoggerFactory.getLogger(GrpcClientService.class);

    private final UnaryServiceGrpc.UnaryServiceBlockingStub unaryBlockingStub;
    private final ServerStreamingServiceGrpc.ServerStreamingServiceStub serverStreamingStub;
    private final ClientStreamingServiceGrpc.ClientStreamingServiceStub clientStreamingStub;
    private final BidirectionalStreamingServiceGrpc.BidirectionalStreamingServiceStub bidirectionalStreamingStub;


    public GrpcClientService(GrpcClientConfigProperties grpcClientConfigProperties) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcClientConfigProperties.getHost(), grpcClientConfigProperties.getPort())
                .usePlaintext()
                .build();

        unaryBlockingStub = UnaryServiceGrpc.newBlockingStub(channel);
        serverStreamingStub = ServerStreamingServiceGrpc.newStub(channel);
        clientStreamingStub = ClientStreamingServiceGrpc.newStub(channel);
        bidirectionalStreamingStub = BidirectionalStreamingServiceGrpc.newStub(channel);
    }

    // Unary Service
    public String unaryCall(String message) {
        UnaryRequest request = UnaryRequest.newBuilder().setMessage(message).build();
        UnaryResponse response;
        try {
            response = unaryBlockingStub.unaryCall(request);
            logger.info("Unary response: {}", response.getMessage());
        } catch (Exception e) {
            logger.error("Failed to call unary service: {}", e.getMessage());
            return "Unary call failed";
        }
        return response.getMessage();
    }

    // Server Streaming Service
    public void serverStreamingCall(String message) {
        ServerStreamingRequest request = ServerStreamingRequest.newBuilder().setMessage(message).build();
        serverStreamingStub.serverStreamingCall(request, new StreamObserver<ServerStreamingResponse>() {
            @Override
            public void onNext(ServerStreamingResponse value) {
                logger.info("Received from server streaming: {}", value.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                logger.error("Error in server streaming call", t);
            }

            @Override
            public void onCompleted() {
                logger.info("Server streaming completed");
            }
        });
    }

    // Client Streaming Service
    public void clientStreamingCall() {
        StreamObserver<ClientStreamingRequest> requestObserver = clientStreamingStub.clientStreamingCall(new StreamObserver<ClientStreamingResponse>() {
            @Override
            public void onNext(ClientStreamingResponse value) {
                logger.info("Received from client streaming: {}", value.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                logger.error("Error in client streaming call", t);
            }

            @Override
            public void onCompleted() {
                logger.info("Client streaming completed");
            }
        });

        // Send multiple messages to the server
        for (int i = 1; i <= 5; i++) {
            ClientStreamingRequest request = ClientStreamingRequest.newBuilder().setMessage("Client message " + i).build();
            requestObserver.onNext(request);
            logger.info("Sent client message: {}", request.getMessage());
        }
        requestObserver.onCompleted();
    }

    // Bidirectional Streaming Service
    public void bidirectionalStreamingCall() {
        StreamObserver<BidirectionalStreamingRequest> requestObserver = bidirectionalStreamingStub.bidirectionalStreamingCall(new StreamObserver<BidirectionalStreamingResponse>() {
            @Override
            public void onNext(BidirectionalStreamingResponse value) {
                logger.info("Received from bidirectional streaming: {}", value.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                logger.error("Error in bidirectional streaming call", t);
            }

            @Override
            public void onCompleted() {
                logger.info("Bidirectional streaming completed");
            }
        });

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            BidirectionalStreamingRequest request = BidirectionalStreamingRequest.newBuilder().setMessage("Bidirectional message").build();
            requestObserver.onNext(request);
            logger.info("Sent bidirectional message: {}", request.getMessage());
        }, 0, 2, TimeUnit.SECONDS);
    }
}
