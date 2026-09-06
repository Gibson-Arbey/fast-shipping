package co.fastshipping.usecase.parcel;

import co.fastshipping.model.parcel.ClasificationTamanho;
import co.fastshipping.model.parcel.Parcel;
import co.fastshipping.model.parcel.ParcelType;
import co.fastshipping.model.parcel.gateways.ParcelRepository;
import co.fastshipping.usecase.parcel.command.CreateParcelCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateParcelUseCase {

    private final ParcelRepository parcelRepository;

    public Parcel execute(CreateParcelCommand command) {
        ClasificationTamanho clasificationTamanho = ClasificationTamanho.fromDimensions(command.height(), command.width(), command.length());

        Parcel parcel = Parcel.create(command.weight(), command.height(), command.width(), command.length(), clasificationTamanho, ParcelType.valueOf(command.type()), command.description());
        return parcelRepository.save(parcel);

    }
}
