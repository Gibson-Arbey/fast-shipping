package co.fastshipping.jpa.entity;

import co.fastshipping.model.delivery.DeliveryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "deliveries")
public class DeliveryJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deli_id")
    private Long id;

    @Column(name = "parc_id", nullable = false)
    private Long parcelId;

    @Column(name = "rout_assignmentid", nullable = false)
    private Long routeAssignmentId;

    @Column(name = "rout_stopid", nullable = false)
    private Long routeStopId;

    @Enumerated(EnumType.STRING)
    @Column(name = "deli_status", nullable = false, length = 20)
    private DeliveryStatus status;
}
