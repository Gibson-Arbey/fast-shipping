package co.fastshipping.usecase.parcel;

import co.fastshipping.model.parcel.ClasificationTamanho;
import co.fastshipping.model.parcel.Parcel;
import co.fastshipping.model.parcel.ParcelType;
import co.fastshipping.model.exception.InvalidFieldException;
import co.fastshipping.model.parcel.gateways.ParcelLifecycleRepository;
import co.fastshipping.usecase.parcel.command.CreateParcelCommand;
import lombok.RequiredArgsConstructor;

import java.util.Locale;


@RequiredArgsConstructor
public class CreateParcelUseCase {

    private final ParcelLifecycleRepository parcelLifecycleRepository;

    public Parcel execute(Long userId, CreateParcelCommand command) {
        if (command == null) {
            throw new InvalidFieldException("command cannot be null");
        }
        ClasificationTamanho clasificationTamanho = ClasificationTamanho.fromDimensions(command.height(), command.width(), command.length());

        Parcel parcel = Parcel.create(
                command.destinationAddressId(),
                command.weight(),
                command.height(),
                command.width(),
                command.length(),
                clasificationTamanho,
                parseType(command.type()),
                command.description()
        );
        return parcelLifecycleRepository.saveWithHistory(parcel, userId, "", "Parcel registered", null);
    }

    private ParcelType parseType(String type) {
        if (type == null || type.isBlank()) {
            throw new InvalidFieldException("type is required");
        }
        try {
            return ParcelType.valueOf(type.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new InvalidFieldException("Invalid parcel type: " + type);
        }
    }
}
