package fr.killiangodet.cantineconnect.schoollife.domain.model;

import fr.killiangodet.cantineconnect.schoollife.domain.exception.InvalidStudentDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StudentTest {

    @Test
    @DisplayName("Should create a student with no special educational needs and no Individual Education Plan (IEP) by default")
    void shouldCreateValidStudent() {
        StudentId studentId = StudentId.generate();

        Student student = Student.register(studentId, "Robert", "Kiyosaki");

        assertThat(student).isNotNull();
        assertThat(student.getId()).isEqualTo(studentId);
        assertThat(student.getFirstName()).isEqualTo("Robert");
        assertThat(student.getLastName()).isEqualTo("Kiyosaki");
        assertThat(student.isHasPai()).isFalse();
    }

    @Test
    @DisplayName("Should throw an exception if the first name is empty")
    void shouldThrowExceptionWhenFirstNameIsEmpty() {
        StudentId studentId = StudentId.generate();

        assertThatThrownBy(() -> Student.register(studentId, "   ", "Kiyosaki"))
            .isInstanceOf(InvalidStudentDataException.class)
            .hasMessage("student.firstname.blank");
    }

    @Test
    @DisplayName("Should throw an exception if the last name is empty")
    void shouldThrowExceptionWhenLastNameIsEmpty() {
        StudentId studentId = StudentId.generate();

        assertThatThrownBy(() -> Student.register(studentId, "Robert", ""))
            .isInstanceOf(InvalidStudentDataException.class)
            .hasMessage("student.lastname.blank");
    }

    @Test
    @DisplayName("Should declare a PAI with at least one allergy")
    void shouldDeclarePaiWithAllergies() {
        // Given
        Student student = Student.register(StudentId.generate(), "Robert", "Kiyosaki");
        Set<Allergy> allergies = Set.of(Allergy.PEANUT, Allergy.MILK);

        // When
        student.declarePai(allergies);

        // Then
        assertThat(student.isHasPai()).isTrue();
        assertThat(student.getAllergies()).containsExactlyInAnyOrder(Allergy.PEANUT, Allergy.MILK);
    }

    @Test
    @DisplayName("Should throw an exception when declaring a PAI without any allergies")
    void shouldThrowExceptionWhenDeclaringPaiWithEmptyAllergies() {
        Student student = Student.register(StudentId.generate(), "Robert", "Kiyosaki");

        assertThatThrownBy(() -> student.declarePai(Set.of()))
            .isInstanceOf(InvalidStudentDataException.class)
            .hasMessage("student.pai.allergies.empty");
    }

    @Test
    @DisplayName("Should throw an exception when declaring a PAI with null allergies")
    void shouldThrowExceptionWhenDeclaringPaiWithNullAllergies() {
        Student student = Student.register(StudentId.generate(), "Robert", "Kiyosaki");

        assertThatThrownBy(() -> student.declarePai((Set<Allergy>) null))
            .isInstanceOf(InvalidStudentDataException.class)
            .hasMessage("student.pai.allergies.null");
    }

    @Test
    @DisplayName("Should remove a PAI and reset the allergies")
    void shouldRemovePai() {
        Student student = Student.register(StudentId.generate(), "Robert", "Kiyosaki");
        student.declarePai(Set.of(Allergy.PEANUT));

        student.removePai();

        assertThat(student.isHasPai()).isFalse();
        assertThat(student.getAllergies()).isEmpty();
    }
}
