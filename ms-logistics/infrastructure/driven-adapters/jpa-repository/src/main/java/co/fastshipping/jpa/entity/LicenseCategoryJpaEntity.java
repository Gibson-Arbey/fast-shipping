package co.fastshipping.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "license_categories")
public class LicenseCategoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lica_id")
    private Long id;

    @Column(name = "lica_code", nullable = false, unique = true, length = 20)
    private String code;

    @Column(name = "lica_name", nullable = false, length = 100)
    private String name;

    @Column(name = "lica_description", length = 255)
    private String description;
}
