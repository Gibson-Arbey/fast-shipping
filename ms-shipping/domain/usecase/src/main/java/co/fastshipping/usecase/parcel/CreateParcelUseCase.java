package co.fastshipping.usecase.parcel;

import co.fastshipping.model.parcel.ClasificationTamanho;
import co.fastshipping.model.parcel.Parcel;
import co.fastshipping.model.parcel.ParcelType;
import co.fastshipping.model.parcel.gateways.ParcelRepository;
import co.fastshipping.model.parcelhistory.ParcelHistory;
import co.fastshipping.model.parcelhistory.gateways.ParcelHistoryRepository;
import co.fastshipping.usecase.parcel.command.CreateParcelCommand;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CreateParcelUseCase {

    private final ParcelRepository parcelRepository;
    private final ParcelHistoryRepository parcelHistoryRepository;

    public Parcel execute(Long userId, CreateParcelCommand command) {
        ClasificationTamanho clasificationTamanho = ClasificationTamanho.fromDimensions(command.height(), command.width(), command.length());

        Parcel parcel = Parcel.create(
                command.destinationAddressId(),
                command.weight(),
                command.height(),
                command.width(),
                command.length(),
                clasificationTamanho,
                ParcelType.valueOf(command.type()),
                command.description()
        );
        Parcel parcelSaved = parcelRepository.save(parcel);
        registerHistory(userId, parcelSaved);
        return parcelSaved;
    }

    private void registerHistory(Long userId, Parcel parcel) {
        parcelHistoryRepository.save(ParcelHistory.create(
                parcel.getId(),
                parcel.getStatus(),
                userId,
                "",
                "Parcel registered"
        ));
    }
}
