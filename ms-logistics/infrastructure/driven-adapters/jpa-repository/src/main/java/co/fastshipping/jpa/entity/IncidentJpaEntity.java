package co.fastshipping.jpa.entity;

import co.fastshipping.model.incident.IncidentStatus;
import co.fastshipping.model.incident.IncidentType;
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

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "incidents")
public class IncidentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inci_id")
    private Long id;

    @Column(name = "rout_assignmentid", nullable = false)
    private Long routeAssignmentId;

    @Column(name = "deli_id")
    private Long deliveryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "inci_type", nullable = false, length = 40)
    private IncidentType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "inci_status", nullable = false, length = 20)
    private IncidentStatus status;

    @Column(name = "inci_description", nullable = false, length = 500)
    private String description;

    @Column(name = "inci_createdat", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "inci_resolvedat")
    private LocalDateTime resolvedAt;

    @Column(name = "user_id", nullable = false)
    private Long reportedBy;
}
