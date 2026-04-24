package com.biomath3d.data;

import com.biomath3d.util.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gestiona la conexion de base de datos
 */
public class DatabaseHelper {

    public static String databaseURL = Constants.DATABASE_DRIVER;

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(databaseURL);
    }

    public static void initDatabase(){
        String sql = "CREATE TABLE IF NOT EXISTS Funciones_Base (" +
                "id_funcion INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre_funcion TEXT NOT NULL, " +
                "expresion_matematica TEXT NOT NULL, " +
                "fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");";
        try(Connection conn = getConnection();
            Statement stmt = conn.createStatement()){
                stmt.execute(sql);
                System.out.println("SISTEMA_DATA > Base de datos inicializada correctamente.");
        }catch(SQLException sqlException){
            System.err.println("ERROR_DATA > " + sqlException.getMessage());
        }
    }
}
