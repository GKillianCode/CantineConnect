package fr.killiangodet.cantineconnectv2.schoollife.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true)
    private String publicId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "liaison_code", nullable = false, unique = true)
    private String liaisonCode;

    protected Student() {}

    public Student(String publicId, String firstName, String lastName, LocalDate birthDate, String liaisonCode) {
        this.publicId = publicId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.liaisonCode = liaisonCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(publicId, student.publicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, publicId);
    }
}
