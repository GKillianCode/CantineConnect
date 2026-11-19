package fr.killiangodet.cantineconnectv2.schoollife.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class StudentLegalGuardianId implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "legal_guardian_id")
    private Long legalGuardianId;

    public StudentLegalGuardianId() {}

    public StudentLegalGuardianId(Long studentId, Long legalGuardianId) {
        this.studentId = studentId;
        this.legalGuardianId = legalGuardianId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentLegalGuardianId that = (StudentLegalGuardianId) o;
        return Objects.equals(studentId, that.studentId) &&
                Objects.equals(legalGuardianId, that.legalGuardianId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, legalGuardianId);
    }
}
