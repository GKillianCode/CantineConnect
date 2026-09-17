package fr.killiangodet.cantineconnect.schoollife.domain.model;

import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
public final class Student {

    private final StudentId id;
    private final String firstName;
    private final String lastName;
    private boolean hasPai;
    private Set<Allergy> allergies;

    private Student(StudentId id, String firstName, String lastName) {
        validateFirstName(firstName);
        validateLastName(lastName);

        this.id = Objects.requireNonNull(id, "Student ID is mandatory.");
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
            throw new IllegalArgumentException("The list of allergies cannot be null.");
        }
        if (allergies.isEmpty()) {
            throw new IllegalArgumentException("An Individualised Action Plan (PAI) must include at least one allergy.");
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
            throw new IllegalArgumentException("The pupil’s first name cannot be left blank.");
        }
    }

    private void validateLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("The pupil’s last name cannot be left blank.");
        }
    }

    public Set<Allergy> getAllergies() {
        return Collections.unmodifiableSet(allergies);
    }
}
