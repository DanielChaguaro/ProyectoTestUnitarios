package com.udla;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StudentServiceTest {

    private StudentService service;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        service = new StudentService();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @DisplayName("Debería agregar estudiantes y obtener todos correctamente")
    void testAddStudentAndGetAll() {
        service.addStudent("Juan", 7.5);
        service.addStudent("Ana", 9.0);

        List<Student> students = service.getAllStudents();
        assertEquals(2, students.size());
        assertEquals("Juan", students.get(0).getName());
        assertEquals(7.5, students.get(0).getGrade());
        assertEquals("Ana", students.get(1).getName());
        assertEquals(9.0, students.get(1).getGrade());
    }

    @Test
    @DisplayName("Debería retornar una copia de la lista (inmutabilidad)")
    void testGetAllReturnsCopy() {
        service.addStudent("Carlos", 8.0);

        List<Student> students = service.getAllStudents();
        students.clear(); // Modifico la lista que me dieron

        // La lista original dentro de StudentService no debe verse afectada
        List<Student> students2 = service.getAllStudents();
        assertEquals(1, students2.size());
    }

    @Test
    @DisplayName("Debería imprimir correctamente la lista de estudiantes")
    void testListStudentsOutput() {
        service.addStudent("Luis", 9.0);
        service.addStudent("María", 8.5);

        service.listStudents();

        String output = outputStream.toString();
        assertTrue(output.contains("Estudiante: Luis, Calificación: 9.0"));
        assertTrue(output.contains("Estudiante: María, Calificación: 8.5"));
        assertTrue(output.contains("Estudiante agregado con exito:"));

        // Restaurar System.out
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Debería manejar lista vacía correctamente")
    void testListStudentsEmpty() {
        service.listStudents(); // No debería lanzar excepción

        List<Student> students = service.getAllStudents();
        assertTrue(students.isEmpty());

        // Restaurar System.out
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Debería propagar excepciones del constructor Student")
    void testAddStudentInvalidData() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.addStudent("", 8.0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            service.addStudent("Juan", -1.0);
        });

        // Restaurar System.out
        System.setOut(originalOut);
    }
}