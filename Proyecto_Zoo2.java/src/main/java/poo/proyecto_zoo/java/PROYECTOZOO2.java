/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package poo.proyecto_zoo.java;

import java.util.Scanner; //permite leer los datos que el usuario ingresa en consola 
import Conexion.ConexionBD; //Trae la clase ConexionBD del paquete Conexion
import java.sql.Connection;  //permite conectarnos a la BD por medio del puente "conexion.conexionBD"
import java.sql.PreparedStatement; //se usa para ejecutar instrucciones SQL, como usar CRUD
import java.sql.ResultSet; //se usa para guardar resultados de una consulta SQL de manera temporal
    
/**
 *
 * @author tonoc
 */
public class PROYECTOZOO2 {

    //static int contador = 0; //Variable utilizada antes para leer nuestro array. queda obsoleto porque ya usamos una BD.

    public static void main(String[] args) {  
        
        ConexionBD c = new ConexionBD(); //crea un objeto de la de la clase ConexionBD, que es para conectarse con la BD
        c.conectarBD(); //llama al metodo "ConexionBD" para establecer conexion con MariaDB

        Scanner sc = new Scanner(System.in); // sirve para leer los datos que 

        int opcion; //variable que almacena el dato de la opcion que el usuario seleccione
        do {
            System.out.println("------------ MENU ZOO ------------");
            System.out.println("Comencemos...");
            System.out.println("1. Zoo");
            System.out.println("2. Calcular comida");
            System.out.println("3. CSV");
            System.out.println("4. Salir");
            opcion = sc.nextInt(); // almacenamos el dato ingresado en la variable opcion
            
            switch (opcion) {
                case 1:
                    menuZoo(sc);
                    break;
                case 2:
                   calcularConsumo(sc);
                   pausar(sc);
                   break;
                case 3:
                   exportarCSV();
                   pausar(sc);
                   break;
                case 4:
                    System.out.println("Adios...\n");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        } while (opcion != 4);
    }
    

    public static void menuZoo(Scanner sc) {
        char opcion;//variable que almacena el dato de la opcion que el usuario seleccione

        do {
            System.out.println("========================================");
            System.out.println("ZOOLOGICO LA AURORA");
            System.out.println("========================================");
            System.out.println("a. Trabajar con Mamifero");
            System.out.println("b. Trabajar con Ave");
            System.out.println("c. Trabajar con Reptil");
            System.out.println();
            System.out.println("d. Buscar animal por id");
            System.out.println("e. Buscar animal por nombre");
            System.out.println("f. Ordenar arreglo por identificador");
            System.out.println("g. Mostrar todos los animales");
            System.out.println("h. Mostrar estadisticas");
            System.out.println("i. Regresar");
            System.out.println();
            
            System.out.print("Opcion: ");
            opcion = sc.next().charAt(0);//sirve para tomar el texto ingresado y devolver una posición

            switch (opcion) {
                case 'a':
                    menuCRUD(sc, "Mamifero");
                    pausar(sc);
                    break;
                    
                case 'b':
                    menuCRUD(sc, "Ave");
                    pausar(sc);
                    break;
                    
                case 'c':
                    menuCRUD(sc, "Reptil");
                    pausar(sc);
                    break;
                    
                case 'd':
                    System.out.println("Ingrese ID: ");
                    int id = sc.nextInt();
                    
                    buscarporid(id);
                    pausar(sc);
                    break;
                    
                case 'e':
                    sc.nextLine();
                    System.out.print("Ingrese nombre: ");
                    String nombre = sc.nextLine();
                    
                    buscarpornombre(nombre);
                    pausar(sc);
                    break;
                    
                case 'f':
                    System.out.println("1. Ascendente");
                    System.out.println("2. Descendente");

                    int orden = sc.nextInt();

                    if (orden == 1) {
                        ordenar(true);
                    }

                    else if (orden == 2) {
                        ordenar(false);
                    }

                    else {
                        System.out.println("Opcion invalida");
                    }
                    pausar(sc);
                    break;

                case 'g':
                    mostrarAnimales();
                    pausar(sc);
                    break;
                    
                case 'h':
                    estadisticas();
                    pausar(sc);
                    break;
                    
                case 'i':
                    System.out.println("Regresando...\n");
                    break;
                    
                default:
                    System.out.println("Opcion invalida");
            }

        } while (opcion != 'i');
    }

    public static void menuCRUD(Scanner sc, String tipoAnimal) {

        char opcion;

        do {
            System.out.println("\n=== CRUD " + tipoAnimal + " ===");

            System.out.println("C. Insertar");
            System.out.println("R. Consultar");
            System.out.println("U. Actualizar");
            System.out.println("D. Eliminar");
            System.out.println("S. Regresar");

            opcion = Character.toUpperCase(sc.next().charAt(0)); //lee el texto que el usuario ingresa, solo tomando la primera letra,
            //lo convierte a mayuscula y lo almacena en la variable "opcion"

            switch(opcion) {
                case 'C':
                    agregarAnimal(sc, tipoAnimal);
                    pausar(sc);
                    break;

                case 'R':
                    consultar(tipoAnimal);
                    pausar(sc);
                    break;
                    
                case 'U':
                    actualizarAnimal(sc, tipoAnimal);
                    pausar(sc);
                    break;

                case 'D':
                    eliminarAnimal(sc, tipoAnimal);
                    pausar(sc);
                    break;

                case 'S':
                    System.out.println("Regresando...");
                    break;
                    
                default:
                    System.out.println("\n\n Opcion invalida!\n");
            }
        } while(opcion != 'S');
    }
    
    public static void agregarAnimal(Scanner sc,String tipoAnimal) {

        sc.nextLine();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine(); //leemos el valor ingresado por el usuario y lo almacenamos en la variable declarada

        System.out.print("Edad: ");
        int edad = sc.nextInt();

        System.out.print("Comida: "); 
        double comida = sc.nextDouble();

        try {
            ConexionBD conexion = new ConexionBD(); //creamos un objeto llamado conexion en la clase ConexionBD
            //accedemos a la clase "ConexionBD"
            Connection con = conexion.conectarBD(); //llama al metodo "conectarBD"
            //Ejecutamos la instruccion para conectarnos a la BD
                 //ambas lineas establecen la conexión con la BD a traves del metodo antes mencionado

            String sql = "INSERT INTO animales(nombre, edad, consumo, tipo) VALUES (?, ?, ?, ?)"; 
            //almacenamos en una variable el texto para a instrucción que enviaremos a la BD

            PreparedStatement ps = con.prepareStatement(sql); //prepara la instruccion SQL para ejecutarla

            ps.setString(1, nombre); //asigna el valor al primer parametro de la consulta (el orden se basa en el numero)
            ps.setInt(2, edad);
            ps.setDouble(3, comida);
            ps.setString(4, tipoAnimal);

            ps.executeUpdate(); //Ejecuta la instrucción en SQL y actualiza los datos en la BD
            //executeQuery - lee
            //executeUpdate - modifica

            System.out.println("\n" + nombre + " fue agregado correctamente");
        }catch (Exception e) {
            System.out.println("Error SQL: " + e.toString());
        }
    }
    //parte 2 del proyecto l11 a 119
    /*public static boolean idExiste(Animal[] animales, long id) {
        for (int i = 0; i < contador; i++) {
            if (animales[i].idAnimal == id) {
                return true;
            }
        }
        return false;
    }*/
    
    //parte 2 del proyecto l 136 a 146
    
    public static void buscarporid(long id) {

    try {
        ConexionBD conexion = new ConexionBD(); //Para acceder a la clase "ConexionBD"
        Connection con = conexion.conectarBD(); //para acceder al metodo "conectarBD" en la clase anterior

        String sql = "SELECT * FROM animales WHERE id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id); //asigna el valor al parametro ID
        ResultSet rs = ps.executeQuery(); //envia la instruccion y trae los datos de la base de datos

        if (rs.next()) { //lee todos los registros de la BD
            System.out.println("\n--- Animal encontrado ---");
            System.out.println("ID: " + rs.getLong("id"));
            System.out.println("Nombre: " + rs.getString("nombre"));
            System.out.println("Edad: " + rs.getInt("edad"));
            System.out.println("Consumo: " + rs.getDouble("consumo"));
            System.out.println("Tipo: " + rs.getString("tipo"));
        } 
        else {
            System.out.println("Animal no encontrado");
        }
    } catch (Exception e) {
        System.out.println("Error SQL: " + e.toString());
    }
}
    
    public static void buscarpornombre(String nombre) {

    try {
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.conectarBD();

        String sql = "SELECT * FROM animales WHERE nombre = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombre);
        ResultSet rs = ps.executeQuery();

        boolean encontrado = false;

        while (rs.next()) {  //recorre todos los registros
            encontrado = true; //si el valor de "encontrado" es verdadero, se ejecuta la instrucción 

            System.out.println("\n--- Animal encontrado ---");
            System.out.println("ID: " + rs.getLong("id"));
            System.out.println("Nombre: " + rs.getString("nombre"));
            System.out.println("Edad: " + rs.getInt("edad"));
            System.out.println("Consumo: " + rs.getDouble("consumo"));
            System.out.println("Tipo: " + rs.getString("tipo"));
        }
            if (!encontrado) { //si el valor es falso se ejecuta la instruccion de if (imprimir texto)
                System.out.println("No se encontraron coincidencias");
            }
        } catch (Exception e) {
            System.out.println("Error SQL: " + e.toString());
        }
    }
    
    public static void ordenar(boolean ascendente) {

        try {
            ConexionBD conexion = new ConexionBD();
            Connection con = conexion.conectarBD();

            String sql;

            if (ascendente) {
                sql = "SELECT * FROM animales ORDER BY id ASC"; //instruccion para leer los datos de forma ascendente
            } else {
                sql = "SELECT * FROM animales ORDER BY id DESC";//instruccion para leer los datos de forma descendente
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- ANIMALES ORDENADOS ---");

            while (rs.next()) {

                System.out.println("----------------------");
                System.out.println("ID: " + rs.getLong("id"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Edad: " + rs.getInt("edad"));
                System.out.println("Consumo: " + rs.getDouble("consumo"));
                System.out.println("Tipo: " + rs.getString("tipo"));
                System.out.println("----------------------");
            }
        } catch (Exception e) {
            System.out.println("Error SQL: " + e.toString());
        }
    }
    
    public static void estadisticas() {

        try {

            ConexionBD conexion = new ConexionBD();
            Connection con = conexion.conectarBD();

            String sqlTotal =
                "SELECT COUNT(*) AS total FROM animales"; //instrucción para contar todos los registros 

            String sqlMamiferos =
                "SELECT COUNT(*) AS mamiferos FROM animales WHERE tipo = 'Mamifero'"; //instrucción para contar los registros de animales
            //tipo "mamifero"

            String sqlAves =
                "SELECT COUNT(*) AS aves FROM animales WHERE tipo = 'Ave'"; //instrucción para contar los registros de animales tipo "ave"

            String sqlReptiles =
                "SELECT COUNT(*) AS reptiles FROM animales WHERE tipo = 'Reptil'";//instrucción para contar los registros de animales
            //tipo "reptil"

            String sqlMayor =
                "SELECT nombre FROM animales ORDER BY consumo DESC LIMIT 1"; //instrucción para ordenar de manera descendente todos
            //los registros y leer el nombre del primero

            String sqlMenor =
                "SELECT nombre FROM animales ORDER BY consumo ASC LIMIT 1"; //instrucción para ordenar de manera ascendente todos
            //los registros y leer el nombre del primero

            String sqlPromedioEdad =
                "SELECT AVG(edad) AS promedioEdad FROM animales"; //instrucción para calcular el promedio del campo "edad" de todos los
            //registros

            PreparedStatement psTotal = con.prepareStatement(sqlTotal); //almacenamos el dato obtenido en una variable
            ResultSet rsTotal = psTotal.executeQuery(); //enviamos y ejecutamos la instruccion e  la BD

            PreparedStatement psMamiferos = con.prepareStatement(sqlMamiferos);
            ResultSet rsMamiferos = psMamiferos.executeQuery();

            PreparedStatement psAves = con.prepareStatement(sqlAves);
            ResultSet rsAves = psAves.executeQuery();

            PreparedStatement psReptiles = con.prepareStatement(sqlReptiles);
            ResultSet rsReptiles = psReptiles.executeQuery();

            PreparedStatement psMayor = con.prepareStatement(sqlMayor);
            ResultSet rsMayor = psMayor.executeQuery();

            PreparedStatement psMenor = con.prepareStatement(sqlMenor);
            ResultSet rsMenor = psMenor.executeQuery();

            PreparedStatement psPromedioEdad =
                con.prepareStatement(sqlPromedioEdad);

            ResultSet rsPromedioEdad =
                psPromedioEdad.executeQuery();
            
            //inicializamos todas las variables con valor 0 o vacias.
            int total = 0;
            int mamiferos = 0;
            int aves = 0;
            int reptiles = 0;

            String animalMayor = "";
            String animalMenor = "";

            double promedioEdad = 0;

            //almacenamos los datos en las variables finales
            if (rsTotal.next()) {
                total = rsTotal.getInt("total"); //si se encuentra un dato en el campo "total", lo almacena en la variable definida
            }

            if (rsMamiferos.next()) {
                mamiferos = rsMamiferos.getInt("mamiferos");
            }

            if (rsAves.next()) {
                aves = rsAves.getInt("aves");
            }

            if (rsReptiles.next()) {
                reptiles = rsReptiles.getInt("reptiles");
            }

            if (rsMayor.next()) {
                animalMayor = rsMayor.getString("nombre");
            }

            if (rsMenor.next()) {
                animalMenor = rsMenor.getString("nombre");
            }

            if (rsPromedioEdad.next()) {
                promedioEdad =
                    rsPromedioEdad.getDouble("promedioEdad");
            }

            System.out.println("\n--- Estadisticas ---");
            System.out.println("Total animales: " + total);
            System.out.println("Mamiferos: " + mamiferos);
            System.out.println("Aves: " + aves);
            System.out.println("Reptiles: " + reptiles);
            System.out.println("Animal con mayor consumo: " + animalMayor);
            System.out.println("Animal con menor consumo: " + animalMenor);
            System.out.println("Promedio de edades: " + promedioEdad);

        } catch (Exception e) {
            System.out.println("Error SQL: " + e.toString());
        }
    }

    public static void mostrarAnimales() {

    try {
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.conectarBD();

        String sql = "SELECT * FROM animales";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("\n====== LISTA DE ANIMALES ======");

        while (rs.next()) { //recorremos fila por fila los registros encontrados
            System.out.println("\n------------------------");
            System.out.println("ID: " + rs.getLong("id"));
            System.out.println("Nombre: " + rs.getString("nombre"));
            System.out.println("Edad: " + rs.getInt("edad"));
            System.out.println("Consumo: " + rs.getDouble("consumo"));
            System.out.println("Tipo: " + rs.getString("tipo"));
            System.out.println("------------------------");
        }
    } catch (Exception e) {
        System.out.println("Error SQL: " + e.toString());
    }
}
    
    public static void calcularConsumo(Scanner sc) {

    try {
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.conectarBD();

        System.out.print("Ingrese ID del animal: ");
        long id = sc.nextLong();

        System.out.print("Ingrese cantidad de dias: ");
        int dias = sc.nextInt();

        String sql = "SELECT nombre, consumo FROM animales WHERE id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String nombre = rs.getString("nombre");
            double consumo = rs.getDouble("consumo");

            double total = consumo * dias;

            System.out.println("\nAnimal: " + nombre);
            System.out.println("Consumo total para " + dias + " dias: " + total + " lbs");
        } else {
            System.out.println("Animal no encontrado");
        }
    } catch (Exception e) {
        System.out.println("Error SQL: " + e.toString());
    }
}
    
    public static void exportarCSV() {

    try {
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.conectarBD();

        String sql = "SELECT * FROM animales";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        java.io.FileWriter writer = new java.io.FileWriter("animales.csv");
        //creamos un objeto FileWriter del paquete java.io que sirve para escribir archivos. Crea el archivo CSV
        writer.write("ID,Nombre,Edad,Consumo,Tipo\n"); //escribe el texto dentro del archivo CSV, y asigna los encabezados

        while (rs.next()) { //mientras se encuentren registros, se ejecutará la instrucción
            writer.write( //escribe la información obtenida en el archivo CSV
                rs.getLong("id") + "," + //lee los registros en orden para escribirlos en el CSV
                rs.getString("nombre") + "," +
                rs.getInt("edad") + "," +
                rs.getDouble("consumo") + "," +
                rs.getString("tipo") + "\n"
            );
        }
        writer.close(); //termina de escribir en el archivo
        System.out.println("Datos exportados correctamente a animales.csv");
    } catch (Exception e) {
        System.out.println("Error al exportar CSV: " + e.toString());
    }
}
    public static void consultar(String tipoAnimal) {

        try {
            ConexionBD conexion = new ConexionBD();
            Connection con = conexion.conectarBD();

            String sql = "SELECT * FROM animales WHERE tipo = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tipoAnimal);
            ResultSet rs = ps.executeQuery();

            boolean encontrado = false;

            System.out.println("\n=== " + tipoAnimal.toUpperCase() + " ===");

            while (rs.next()) {
                encontrado = true;

                System.out.println("----------------------");
                System.out.println("ID: " + rs.getLong("id"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Edad: " + rs.getInt("edad"));
                System.out.println("Consumo: " + rs.getDouble("consumo"));
                System.out.println("----------------------");
            }
            if (!encontrado) {
                System.out.println("No existen registros");
            }
        }catch (Exception e) {
            System.out.println("Error SQL: " + e.toString());
        }
    }
    
    public static void actualizarAnimal(Scanner sc, String tipoAnimal) {

        try {
            ConexionBD conexion = new ConexionBD();
            Connection con = conexion.conectarBD();

            System.out.print("Ingrese ID del animal: ");

            long id = sc.nextLong();

            String verificar =
            "SELECT * FROM animales WHERE id = ? AND tipo = ?";

            PreparedStatement psVerificar =
            con.prepareStatement(verificar);

            psVerificar.setLong(1, id);
            psVerificar.setString(2, tipoAnimal);

            ResultSet rs = psVerificar.executeQuery();

            if (rs.next()) {

                System.out.println("\n--- Datos actuales ---");

                System.out.println("Nombre: " +
                rs.getString("nombre"));

                System.out.println("Edad: " +
                rs.getInt("edad"));

                System.out.println("Consumo: " +
                rs.getDouble("consumo"));

                sc.nextLine();

                System.out.print("\nNuevo nombre: ");
                String nombre = sc.nextLine();

                System.out.print("Nueva edad: ");
                int edad = sc.nextInt();

                System.out.print("Nuevo consumo: ");
                double consumo = sc.nextDouble();

                String actualizar =
                "UPDATE animales SET nombre=?, edad=?, consumo=? WHERE id=?";

                PreparedStatement psActualizar =
                con.prepareStatement(actualizar);

                psActualizar.setString(1, nombre);
                psActualizar.setInt(2, edad);
                psActualizar.setDouble(3, consumo);
                psActualizar.setLong(4, id);

                int filas = psActualizar.executeUpdate();

                if (filas > 0) {
                    System.out.println("Registro actualizado correctamente");
                }
                else {
                    System.out.println("No se pudo actualizar");
                }
            }
            else {
                System.out.println("El registro no existe");
            }
        }catch (Exception e) {
            System.out.println("Error SQL: " + e.toString());
        }
    }
    
    public static void eliminarAnimal(Scanner sc, String tipoAnimal) {

        try {
            ConexionBD conexion = new ConexionBD();
            Connection con = conexion.conectarBD();

            System.out.print("Ingrese ID del animal: ");

            long id = sc.nextLong();

            String verificar =
            "SELECT * FROM animales WHERE id = ? AND tipo = ?";

            PreparedStatement psVerificar =
            con.prepareStatement(verificar);

            psVerificar.setLong(1, id);
            psVerificar.setString(2, tipoAnimal);

            ResultSet rs = psVerificar.executeQuery();

            if (rs.next()) {

                System.out.println("\n--- Registro encontrado ---");

                System.out.println("Nombre: " +
                rs.getString("nombre"));

                System.out.println("Edad: " +
                rs.getInt("edad"));

                System.out.println("Consumo: " +
                rs.getDouble("consumo"));

                sc.nextLine();

                System.out.print(
                "\n¿Seguro que desea eliminar? (s/n): ");

                String confirmar = sc.nextLine();

                if (confirmar.equalsIgnoreCase("s")) {

                    String eliminar =
                    "DELETE FROM animales WHERE id = ?";

                    PreparedStatement psEliminar =
                    con.prepareStatement(eliminar);

                    psEliminar.setLong(1, id);

                    int filas = psEliminar.executeUpdate();

                    if (filas > 0) {
                        System.out.println(
                        "Registro eliminado correctamente");
                    }
                    else {
                        System.out.println(
                        "No se pudo eliminar");
                    }
                }
                else {
                    System.out.println("Operacion cancelada");
                }
            }
            else {
                System.out.println("El registro no existe");
            }
        }catch (Exception e) {
            System.out.println("Error SQL: " + e.toString());
        }
    }
    
    public static void pausar(Scanner sc) { //metodo para que no aparezca el menú inmediatamente si no despues de presionar "enter".

        System.out.println("\nPresione ENTER para continuar...");
        sc.nextLine();
        sc.nextLine();
    }
}
