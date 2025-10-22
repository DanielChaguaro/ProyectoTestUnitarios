package com.udla;

public class Student {
    private String name;
    private double grade;

    public Student(String name, double grade) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("El nombre no puede estar vacío.");
        if (grade < 0 || grade > 10) throw new IllegalArgumentException("La calificación debe estar entre 0 y 10.");
        this.name = name;
        this.grade = grade;
    }

    public String getName() { return name; }
    public double getGrade() { return grade; }
}
