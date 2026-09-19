package fr.killiangodet.cantineconnect.schoollife.domain.model;

import fr.killiangodet.cantineconnect.schoollife.domain.exception.InvalidStudentDataException;
import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
public final class Student {

    private final StudentId id;
    private final String firstName;
    private final String lastName;
    private boolean hasPai;
    private Set<Allergy> allergies;

    private Student(StudentId id, String firstName, String lastName) {
        if (id == null) {
            throw new InvalidStudentDataException("student.id.mandatory");
        }
        validateFirstName(firstName);
        validateLastName(lastName);

        this.id = id;
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.hasPai = false;
        this.allergies = new HashSet<>();
    }

    public static Student register(StudentId id, String firstName, String lastName) {
        return new Student(id, firstName, lastName);
    }

    public void declarePai(Set<Allergy> allergies) {
        if (allergies == null) {
            throw new InvalidStudentDataException("student.pai.allergies.null");
        }
        if (allergies.isEmpty()) {
            throw new InvalidStudentDataException("student.pai.allergies.empty");
        }

        this.hasPai = true;
        this.allergies = new HashSet<>(allergies);
    }

    public void removePai() {
        this.hasPai = false;
        this.allergies.clear();
    }

    private void validateFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new InvalidStudentDataException("student.firstname.blank");
        }
    }

    private void validateLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new InvalidStudentDataException("student.lastname.blank");
        }
    }

    public Set<Allergy> getAllergies() {
        return Collections.unmodifiableSet(allergies);
    }
}
