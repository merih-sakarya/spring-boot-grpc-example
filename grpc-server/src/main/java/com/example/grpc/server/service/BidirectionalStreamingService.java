package com.example.grpc.server.service;

import com.example.grpc.BidirectionalStreamingRequest;
import com.example.grpc.BidirectionalStreamingResponse;
import com.example.grpc.BidirectionalStreamingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BidirectionalStreamingService extends BidirectionalStreamingServiceGrpc.BidirectionalStreamingServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(BidirectionalStreamingService.class);

    @Override
    public StreamObserver<BidirectionalStreamingRequest> bidirectionalStreamingCall(StreamObserver<BidirectionalStreamingResponse> responseObserver) {
        return new StreamObserver<BidirectionalStreamingRequest>() {
            @Override
            public void onNext(BidirectionalStreamingRequest value) {
                logger.info("Received bidirectional streaming request with message: {}", value.getMessage());
                BidirectionalStreamingResponse response = BidirectionalStreamingResponse.newBuilder().setMessage("Response: " + value.getMessage()).build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                logger.error("Error in bidirectional streaming call", t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
                logger.info("Bidirectional streaming completed.");
            }
        };
    }
}
