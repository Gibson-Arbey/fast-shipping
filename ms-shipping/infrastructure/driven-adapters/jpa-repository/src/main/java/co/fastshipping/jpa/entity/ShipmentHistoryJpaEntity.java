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
@Table(name = "shipmenthistories")
public class ShipmentHistoryJpaEntity {

    @Id
    @Column(name = "shhi_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ship_id", referencedColumnName = "ship_id", nullable = false)
    private ShipmentJpaEntity shipment;

    @Column(name = "shhi_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    @Column(name = "shhi_createdat", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "shhi_location")
    private String location;

    @Column(name = "shhi_observation")
    private String observation;
}
