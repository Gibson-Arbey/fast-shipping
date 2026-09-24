package co.fastshipping.jpa.entity;

import co.fastshipping.model.parcel.ClasificationTamanho;
import co.fastshipping.model.parcel.ParcelStatus;
import co.fastshipping.model.parcel.ParcelType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parcels")
public class ParcelJpaEntity {

    @Id
    @Column(name = "parc_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parc_trackingnumber", nullable = false, unique = true)
    private UUID trackingNumber;

    @Column(name = "addr_id", nullable = false)
    private Long destinationAddressId;

    @Column(name = "parc_weight", nullable = false)
    private BigDecimal weight;

    @Column(name = "parc_height", nullable = false)
    private BigDecimal height;

    @Column(name = "parc_width", nullable = false)
    private BigDecimal width;

    @Column(name = "parc_length", nullable = false)
    private BigDecimal length;

    @Column(name = "parc_clasificationtamanho", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClasificationTamanho clasificationTamanho;

    @Column(name = "parc_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParcelType type;

    @Column(name = "parc_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParcelStatus status;

    @Column(name = "parc_description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ship_id", referencedColumnName = "ship_id")
    private ShipmentJpaEntity shipment;
}
