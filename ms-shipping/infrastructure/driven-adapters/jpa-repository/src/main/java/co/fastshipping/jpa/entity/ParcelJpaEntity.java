package co.fastshipping.jpa.entity;

import co.fastshipping.model.parcel.ClasificationTamanho;
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

    @Column(name = "parc_trackingnumber")
    private UUID trackingNumber;

    @Column(name = "parc_weight")
    private BigDecimal weight;

    @Column(name = "parc_height")
    private BigDecimal height;

    @Column(name = "parc_width")
    private BigDecimal width;

    @Column(name = "parc_length")
    private BigDecimal length;

    @Column(name = "parc_clasificationtamanho")
    @Enumerated(EnumType.STRING)
    private ClasificationTamanho clasificationTamanho;

    @Column(name = "parc_type")
    @Enumerated(EnumType.STRING)
    private ParcelType type;

    @Column(name = "parc_description")
    private String description;
}
