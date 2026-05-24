/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.proyecto_zoo.java;

/**
 *
 * @author tonoc
 */
public class Mamifero extends Animal {
    
    
     public Mamifero(long idAnimal, String nombre, int edad, double consumoDiario) {
        super(idAnimal, nombre, edad, consumoDiario);
    }

    public void comer() {
        System.out.println(nombre + " come carne o plantas.");
    }
}
