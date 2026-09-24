package co.fastshipping.usecase.parcel;

import co.fastshipping.model.parcel.Parcel;
import co.fastshipping.model.parcel.ParcelStatus;
import co.fastshipping.model.parcel.gateways.ParcelLifecycleRepository;
import co.fastshipping.usecase.parcel.command.CreateParcelCommand;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateParcelUseCaseTest {

    @Test
    void createsParcelAndRequestsInitialHistoryInTheSameLifecycleWrite() {
        ParcelLifecycleRepository lifecycleRepository = mock(ParcelLifecycleRepository.class);
        when(lifecycleRepository.saveWithHistory(any(), eq(7L), eq(""), eq("Parcel registered"), isNull()))
                .thenAnswer(invocation -> {
                    Parcel parcel = invocation.getArgument(0);
                    return Parcel.restore(9L, parcel.getTrackingNumber(), parcel.getDestinationAddressId(),
                            parcel.getWeight(), parcel.getHeight(), parcel.getWidth(), parcel.getLength(),
                            parcel.getClasificationTamanho(), parcel.getType(), parcel.getStatus(),
                            parcel.getDescription(), null);
                });
        CreateParcelUseCase useCase = new CreateParcelUseCase(lifecycleRepository);

        Parcel created = useCase.execute(7L, new CreateParcelCommand(
                15L, new BigDecimal("2"), new BigDecimal("10"), new BigDecimal("10"),
                new BigDecimal("10"), "standard", "fragile parcel"));

        assertEquals(9L, created.getId());
        assertEquals(ParcelStatus.CREATED, created.getStatus());
        verify(lifecycleRepository).saveWithHistory(any(), eq(7L), eq(""), eq("Parcel registered"), isNull());
    }
}
