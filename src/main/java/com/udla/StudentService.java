package com.udla;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final List<Student> students = new ArrayList<>();

    public void addStudent(String name, double grade) {
        Student student = new Student(name, grade);
        students.add(student);
        System.out.println("Estudiante agregado con exito: ");
    }

    public void listStudents() {
        students.forEach(s -> System.out.println("Estudiante: " + s.getName() + ", Calificación: " + s.getGrade()));
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
}
