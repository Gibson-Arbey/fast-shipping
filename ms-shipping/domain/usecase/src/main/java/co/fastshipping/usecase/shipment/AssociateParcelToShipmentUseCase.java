package co.fastshipping.usecase.shipment;

import co.fastshipping.model.parcel.Parcel;
import co.fastshipping.model.parcel.exception.ParcelNotFoundException;
import co.fastshipping.model.parcel.gateways.ParcelRepository;
import co.fastshipping.model.shipment.Shipment;
import co.fastshipping.model.shipment.ShipmentStatusResolver;
import co.fastshipping.model.shipment.exception.ShipmentNotFoundException;
import co.fastshipping.model.shipment.gateways.ShipmentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AssociateParcelToShipmentUseCase {
    private final ParcelRepository parcelRepository;
    private final ShipmentRepository shipmentRepository;
    private final ShipmentStatusResolver statusResolver;

    public Parcel execute(Long parcelId, Long shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId);
        if (shipment == null) {
            throw new ShipmentNotFoundException("Shipment not found: " + shipmentId);
        }

        Parcel parcel = parcelRepository.findById(parcelId);
        if (parcel == null) {
            throw new ParcelNotFoundException("Parcel not found: " + parcelId);
        }

        Parcel associatedParcel = parcel.associateToShipment(shipmentId);
        if (associatedParcel == parcel) {
            return parcel;
        }

        Parcel saved = parcelRepository.save(associatedParcel);
        recalculateShipmentStatus(shipment);
        return saved;
    }

    private void recalculateShipmentStatus(Shipment shipment) {
        var parcels = parcelRepository.findAllByShipmentId(shipment.getId());
        var status = statusResolver.resolve(shipment, parcels);
        if (shipment.getStatus() != status) {
            shipmentRepository.save(shipment.withStatus(status));
        }
    }
}
