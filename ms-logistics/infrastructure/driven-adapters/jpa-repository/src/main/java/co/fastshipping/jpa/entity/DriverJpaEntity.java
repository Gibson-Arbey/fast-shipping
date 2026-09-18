package co.fastshipping.jpa.entity;

import co.fastshipping.model.driver.DriverStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drivers")
public class DriverJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driv_id")
    private Long id;

    @Column(name = "driv_user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "driv_license_number", nullable = false, unique = true, length = 30)
    private String licenseNumber;

    @Column(name = "driv_status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    @Builder.Default
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<DriverLicenseCategoryJpaEntity> licenseCategories = new HashSet<>();
}
