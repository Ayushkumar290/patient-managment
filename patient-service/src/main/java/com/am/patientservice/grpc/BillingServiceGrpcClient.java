package com.am.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGrpcClient {

    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub ;

    // localhost:9004/BillingService/createBillingAccount\
    // aws.grpc:123/BillingService/createBillingAccount
    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort
    ) {

        log.info("Connecting  to Billing Service GRPC Service at {}:{}",serverAddress,serverPort );

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,serverPort).usePlaintext().build();
        this.blockingStub = BillingServiceGrpc.newBlockingStub(channel);



    }
    public BillingResponse  createBillingAccount(String patientId,String name, String email) {
        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId).setName(name).setEmail(email).build();

        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Received Response from billing Service via Grpc: {}",response);
        return response;

    }

}
