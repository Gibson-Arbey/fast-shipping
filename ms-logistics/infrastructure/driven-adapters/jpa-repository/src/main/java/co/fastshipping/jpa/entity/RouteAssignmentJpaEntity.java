package co.fastshipping.jpa.entity;

import co.fastshipping.model.routeassignment.RouteAssignmentStatus;
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
@Table(name = "route_assignments")
public class RouteAssignmentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roas_id")
    private Long id;

    @Column(name = "rout_id", nullable = false)
    private Long routeId;

    @Column(name = "driv_id", nullable = false)
    private Long driverId;

    @Column(name = "vehi_id", nullable = false)
    private Long vehicleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "roas_status", nullable = false, length = 20)
    private RouteAssignmentStatus status;

    @Column(name = "roas_startedat")
    private LocalDateTime startedAt;

    @Column(name = "roas_completedat")
    private LocalDateTime completedAt;
}
