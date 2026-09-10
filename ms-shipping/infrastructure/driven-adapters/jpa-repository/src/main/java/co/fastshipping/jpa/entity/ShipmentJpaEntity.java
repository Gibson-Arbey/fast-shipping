package co.fastshipping.jpa.entity;

import co.fastshipping.model.shipment.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shipments")
public class ShipmentJpaEntity {

    @Id
    @Column(name = "ship_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ship_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    @Column(name = "ship_originaddress", nullable = false)
    private String originAddress;

    @Column(name = "ship_destinationaddress", nullable = false)
    private String destinationAddress;

    @Column(name = "ship_createdat", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "ship_deliveredat")
    private LocalDateTime deliveredAt;
}
