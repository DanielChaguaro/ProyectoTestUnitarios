package com.udla;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Debería lanzar excepción con nombres nulos o vacíos")
    void testCreateStudentInvalidNames(String invalidName) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Student(invalidName, 8.0);
        });
        assertEquals("El nombre no puede estar vacío.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -0.1, 10.1, 11.0, 100.0})
    @DisplayName("Debería lanzar excepción con calificaciones inválidas")
    void testCreateStudentInvalidGrades(double invalidGrade) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Student("Ana", invalidGrade);
        });
        assertEquals("La calificación debe estar entre 0 y 10.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 5.0, 10.0, 8.5, 9.75})
    @DisplayName("Debería crear estudiante correctamente con calificaciones válidas")
    void testCreateStudentValidGrades(double validGrade) {
        Student student = new Student("Juan", validGrade);
        assertEquals("Juan", student.getName());
        assertEquals(validGrade, student.getGrade());
    }

    @Test
    @DisplayName("Debería crear estudiante correctamente con datos válidos")
    void testCreateStudentCorrectly() {
        Student student = new Student("María", 8.5);
        assertEquals("María", student.getName());
        assertEquals(8.5, student.getGrade());
    }
}