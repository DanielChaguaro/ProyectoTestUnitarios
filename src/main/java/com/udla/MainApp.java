package com.udla;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        StudentService service = new StudentService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Sistema de Registro de Estudiantes ===");

        boolean continuar = true;
        while (continuar) {
            try {
                System.out.print("Ingrese el nombre del estudiante: ");
                String nombre = scanner.nextLine();

                System.out.print("Ingrese la calificación del estudiante (0 - 100): ");
                double calificacion = Double.parseDouble(scanner.nextLine());

                service.addStudent(nombre, calificacion);

                System.out.print("¿Desea agregar otro estudiante? (s/n): ");
                String respuesta = scanner.nextLine();
                if (!respuesta.equalsIgnoreCase("s")) {
                    continuar = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Error: La calificación debe ser un número válido.");
            } catch (IllegalArgumentException e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        }

        System.out.println("\n=== Lista de estudiantes registrados ===");
        service.listStudents();

        scanner.close();
    }
}
