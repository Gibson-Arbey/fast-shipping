package co.fastshipping.config;

import co.fastshipping.model.shipment.ShipmentStatusResolver;
import co.fastshipping.model.parcel.gateways.ParcelLifecycleRepository;
import co.fastshipping.model.parcel.gateways.ParcelRepository;
import co.fastshipping.model.parcel.gateways.ParcelStatusNotificationGateway;
import co.fastshipping.model.shipment.gateways.ShipmentRepository;
import co.fastshipping.usecase.parcel.UpdateParcelStatusUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.fastshipping.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = UpdateParcelStatusUseCase.class)
        },
        useDefaultFilters = false)
public class UseCasesConfig {

    @Bean
    public ShipmentStatusResolver shipmentStatusResolver() {
        return new ShipmentStatusResolver();
    }

    @Bean
    public UpdateParcelStatusUseCase updateParcelStatusUseCase(
            ParcelRepository parcelRepository,
            ParcelLifecycleRepository parcelLifecycleRepository,
            ShipmentRepository shipmentRepository,
            ShipmentStatusResolver statusResolver,
            ParcelStatusNotificationGateway notificationGateway) {
        return new UpdateParcelStatusUseCase(
                parcelRepository,
                parcelLifecycleRepository,
                shipmentRepository,
                statusResolver,
                notificationGateway);
    }
}
