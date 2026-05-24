/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

    Connection conectar = null;

    public Connection conectarBD() { //metodo que nos permite realizar la conexión con la BD

        try {
            
            String url = "jdbc:mariadb://localhost:3306/zoologico";
            String usuario = "root";
            String contraseña = "ani1234";

            conectar = DriverManager.getConnection(url, usuario, contraseña);

            System.out.println("");

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
        return conectar;
    }
}