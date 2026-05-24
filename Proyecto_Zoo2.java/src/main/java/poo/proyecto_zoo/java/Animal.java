/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.proyecto_zoo.java;

/**
 *
 * @author tonoc
 */
public class Animal {
    
    String nombre;
    long idAnimal;
    int edad;
    double consumoDiario;

    public Animal(long idAnimal, String nombre, int edad, double consumoDiario) { //constructor
        this.idAnimal = idAnimal;
        this.nombre = nombre;
        this.edad = edad;
        this.consumoDiario = consumoDiario;
    }

    public void mostrarInfo() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Edad: " + edad + " anios");
        System.out.println("Consumo diario: " + consumoDiario + "lbs");
        System.out.println();
    }

    public double calcularConsumo(int dias) {
        if (dias == 1) {
            return consumoDiario;
        } else {
            return consumoDiario + calcularConsumo(dias - 1);
        }
    }
     public void alimentar() {
        System.out.println(nombre + " esta comiendo.");
    }
}
