package co.fastshipping.usecase.parcel;

import co.fastshipping.model.exception.InvalidFieldException;
import co.fastshipping.model.parcel.Parcel;
import co.fastshipping.model.parcel.ParcelStatus;
import co.fastshipping.model.parcel.exception.ParcelNotFoundException;
import co.fastshipping.model.parcel.gateways.ParcelLifecycleRepository;
import co.fastshipping.model.parcel.gateways.ParcelRepository;
import co.fastshipping.model.shipment.Shipment;
import co.fastshipping.model.shipment.ShipmentStatus;
import co.fastshipping.model.shipment.ShipmentStatusResolver;
import co.fastshipping.model.shipment.exception.ShipmentNotFoundException;
import co.fastshipping.model.shipment.gateways.ShipmentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateParcelStatusUseCase {
    private final ParcelRepository parcelRepository;
    private final ParcelLifecycleRepository parcelLifecycleRepository;
    private final ShipmentRepository shipmentRepository;
    private final ShipmentStatusResolver statusResolver;

    public Parcel execute(Long parcelId, ParcelStatus newStatus, Long userId, String location, String observation) {
        Parcel parcel = parcelRepository.findById(parcelId);
        if (parcel == null) {
            throw new ParcelNotFoundException("Parcel not found: " + parcelId);
        }
        if (newStatus == null) {
            throw new InvalidFieldException("status cannot be null");
        }
        if (parcel.getStatus() == newStatus) {
            return parcel;
        }

        Parcel changedParcel = transition(parcel, newStatus);
        Shipment shipmentToUpdate = recalculateShipmentStatus(changedParcel);
        return parcelLifecycleRepository.saveWithHistory(changedParcel, userId, location, observation, shipmentToUpdate);
    }

    private Parcel transition(Parcel parcel, ParcelStatus newStatus) {
        return switch (newStatus) {
            case ASSIGNED -> parcel.assignToDelivery();
            case IN_TRANSIT -> parcel.markInTransit();
            case DELIVERED -> parcel.markDelivered();
            case DELIVERY_FAILED -> parcel.markDeliveryFailed();
            case CANCELLED -> parcel.markCancelled();
            default -> throw new InvalidFieldException("Parcel status cannot be changed to " + newStatus);
        };
    }

    private Shipment recalculateShipmentStatus(Parcel parcel) {
        if (parcel.getShipmentId() == null) {
            return null;
        }
        Shipment shipment = shipmentRepository.findById(parcel.getShipmentId());
        if (shipment == null) {
            throw new ShipmentNotFoundException("Shipment not found: " + parcel.getShipmentId());
        }
        var parcels = parcelRepository.findAllByShipmentId(parcel.getShipmentId()).stream()
                .map(current -> current.getId().equals(parcel.getId()) ? parcel : current)
                .toList();
        if (parcels.stream().noneMatch(current -> current.getId().equals(parcel.getId()))) {
            var withChangedParcel = new java.util.ArrayList<>(parcels);
            withChangedParcel.add(parcel);
            parcels = withChangedParcel;
        }
        ShipmentStatus resolvedStatus = statusResolver.resolve(shipment, parcels);
        return shipment.getStatus() == resolvedStatus ? null : shipment.withStatus(resolvedStatus);
    }
}
