package co.fastshipping.jpa.entity;

import co.fastshipping.model.parcel.ParcelStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parcelhistories")
public class ParcelHistoryJpaEntity {

    @Id
    @Column(name = "parh_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parc_id", referencedColumnName = "parc_id", nullable = false)
    private ParcelJpaEntity parcel;

    @Column(name = "parh_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParcelStatus status;

    @Column(name = "parh_createdat", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "parh_location")
    private String location;

    @Column(name = "parh_observation")
    private String observation;
}
