package co.fastshipping.jpa.entity;

import co.fastshipping.model.user.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserJpaEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "user_lastname")
    private String lastName;

    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleJpaEntity role;

    @Column(name = "user_status")
    private UserStatus status;

    @Column(name = "user_createdat")
    private LocalDateTime createdAt;
}
