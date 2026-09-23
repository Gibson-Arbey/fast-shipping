package co.fastshipping.jpa.entity;

import co.fastshipping.model.vehicle.VehicleStatus;
import co.fastshipping.model.vehicle.VehicleType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicles")
public class VehicleJpaEntity {

    @Id
    @Column(name = "vehi_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehi_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    @Column(name = "vehi_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @Column(name = "vehi_maxweight", nullable = false)
    private BigDecimal maxWeight;

    @Column(name = "vehi_maxvolume", nullable = false)
    private BigDecimal maxVolume;

    @Column(name = "vehi_plate", nullable = false)
    private String plate;
}
