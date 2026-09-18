package co.fastshipping.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(DriverLicenseCategoryJpaEntity.DriverLicenseCategoryId.class)
@Table(name = "driver_license_categories")
public class DriverLicenseCategoryJpaEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "drli_driver_id", nullable = false)
    private DriverJpaEntity driver;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "drli_license_category_id", nullable = false)
    private LicenseCategoryJpaEntity licenseCategory;

    @Getter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DriverLicenseCategoryId implements Serializable {
        private Long driver;
        private Long licenseCategory;
    }
}
