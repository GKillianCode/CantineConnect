package fr.killiangodet.cantineconnectv2.schoollife.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "student_legal_guardian")
@Getter
@Setter
public class StudentLegalGuardian {

    @EmbeddedId
    private StudentLegalGuardianId id = new StudentLegalGuardianId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("legalGuardianId")
    @JoinColumn(name = "legal_guardian_id")
    private LegalGuardian legalGuardian;

    public StudentLegalGuardian() {}

    public StudentLegalGuardian(Student student, LegalGuardian legalGuardian) {
        this.student = student;
        this.legalGuardian = legalGuardian;
        this.id = new StudentLegalGuardianId(student.getId(), legalGuardian.getId());
    }
}
