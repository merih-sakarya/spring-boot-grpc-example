# Spring gRPC Example

This repository contains two separate Spring Boot projects demonstrating a gRPC server and client. It showcases the four main types of gRPC communication patterns: Unary RPC, Server Streaming RPC, Client Streaming RPC, and Bidirectional Streaming RPC.

## Projects

### grpc-server
This project contains the gRPC server implementation. It includes services such as `UnaryService`, `ServerStreamingService`, `ClientStreamingService`, and `BidirectionalStreamingService`.

### grpc-client
This project contains the gRPC client implementation which interacts with the gRPC server using the mentioned services.

## Prerequisites
- Java 17
- Maven

## Running the Projects

### Running the Server
1. Navigate to the `grpc-server` directory:
    ```sh
    cd spring-grpc-example/grpc-server
    ```
2. Run the server application:
    ```sh
    ./mvnw spring-boot:run
    ```
3. You should see logs indicating the server has started on the specified port:
    ```
    🚀 gRPC Server has successfully started on port 9090.
    ```

### Running the Client
1. Open a new terminal and navigate to the `grpc-client` directory:
    ```sh
    cd spring-grpc-example/grpc-client
    ```
2. Run the client application:
    ```sh
    ./mvnw spring-boot:run
    ```
3. You should see logs indicating the client has initialized the system and started the different gRPC interactions:
    ```
    Unary response: Unary response: Performing unary call...
    Sent client message: Client message 1
    Received from server streaming: Server streaming response 1: Performing server streaming call...
    Received from bidirectional streaming: Response: Ping
    ```

## Project Structure
- `grpc-server`: Contains the gRPC server code.
- `grpc-client`: Contains the gRPC client code.
- `proto/unary.proto`: Protocol Buffers definition file used by both server and client for the unary service.
- `proto/server_streaming.proto`: Protocol Buffers definition file used by both server and client for the server streaming service.
- `proto/client_streaming.proto`: Protocol Buffers definition file used by both server and client for the client streaming service.
- `proto/bidirectional_streaming.proto`: Protocol Buffers definition file used by both server and client for the bidirectional streaming service.

## Example Protobuf Definitions

### Unary Service (unary.proto)
```proto
syntax = "proto3";

option java_multiple_files = true;
option java_package = "com.example.grpc";
option java_outer_classname = "UnaryProto";

package unary;

// The Unary service definition.
service UnaryService {
  rpc UnaryCall (UnaryRequest) returns (UnaryResponse);
}

// The request message containing the unary call message.
message UnaryRequest {
  string message = 1;
}

// The response message containing the unary call response.
message UnaryResponse {
  string message = 1;
}
```

### Server Streaming Service (server_streaming.proto)
```proto
syntax = "proto3";

option java_multiple_files = true;
option java_package = "com.example.grpc";
option java_outer_classname = "ServerStreamingProto";

package serverstreaming;

// The Server Streaming service definition.
service ServerStreamingService {
  rpc ServerStreamingCall (ServerStreamingRequest) returns (stream ServerStreamingResponse);
}

// The request message containing the server streaming call message.
message ServerStreamingRequest {
  string message = 1;
}

// The response message containing the server streaming response.
message ServerStreamingResponse {
  string message = 1;
}
```

## Dependencies
- Spring Boot 3
- gRPC
- Protocol Buffers

## Contributing
Feel free to fork this repository and contribute by submitting a pull request.

## License
This project is licensed under the MIT License.
