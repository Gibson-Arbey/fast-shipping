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

    @Column(name = "addr_id", nullable = false)
    private Long senderAddressId;

    @Column(name = "ship_createdat", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "ship_status", nullable = false, length = 20)
    private ShipmentStatus status;
}
