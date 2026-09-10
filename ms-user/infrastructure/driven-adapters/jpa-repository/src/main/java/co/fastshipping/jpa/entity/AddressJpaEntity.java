package co.fastshipping.jpa.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class AddressJpaEntity {

    @Id
    @Column(name = "addr_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserJpaEntity customer;

    @Column(name = "addr_street")
    private String street;

    @Column(name = "addr_number")
    private String number;

    @Column(name = "addr_neighborhood")
    private String neighborhood;

    @Column(name = "addr_city")
    private String city;

    @Column(name = "addr_state")
    private String state;

    @Column(name = "addr_country")
    private String country;

    @Column(name = "addr_postalcode")
    private String postalCode;

    @Column(name = "addr_deleted")
    private Boolean deleted;
}
