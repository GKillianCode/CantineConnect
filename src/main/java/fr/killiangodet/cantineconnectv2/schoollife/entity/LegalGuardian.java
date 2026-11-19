package fr.killiangodet.cantineconnectv2.schoollife.entity;

import fr.killiangodet.cantineconnectv2.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "legal_guardian")
@Getter
@Setter
public class LegalGuardian extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true)
    private String publicId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phone_number;

    @OneToMany(mappedBy = "legalGuardian", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentLegalGuardian> studentLinks = new HashSet<>();

    public LegalGuardian() {}

    public LegalGuardian(String publicId, String firstName, String lastName, String email, String password, String phone_number) {
        this.publicId = publicId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
    }
}
