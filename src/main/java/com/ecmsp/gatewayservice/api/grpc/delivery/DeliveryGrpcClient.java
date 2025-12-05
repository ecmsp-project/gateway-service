package com.ecmsp.gatewayservice.api.grpc.delivery;
import com.ecmsp.gatewayservice.api.grpc.UserContextGrpcWrapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.product.v1.delivery.v1.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class DeliveryGrpcClient {

    @GrpcClient("product-service")
    private DeliveryServiceGrpc.DeliveryServiceBlockingStub deliveryServiceStub;

    public RecordDeliveryResponse recordDelivery(RecordDeliveryRequest request, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        DeliveryServiceGrpc.DeliveryServiceBlockingStub stubWithMetadata =
                deliveryServiceStub.withCallCredentials(credentials);

        return stubWithMetadata.recordDelivery(request);
    }

    public ListDeliveriesResponse listDeliveries(ListDeliveriesRequest request, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        DeliveryServiceGrpc.DeliveryServiceBlockingStub stubWithMetadata =
                deliveryServiceStub.withCallCredentials(credentials);

        return stubWithMetadata.listDeliveries(request);
    }
}
