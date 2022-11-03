/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author USUARIO
 */
public class Conexion {
    
    String bd="dbtiendaropa";
    String url= "jdbc:mysql://localhost:3306/";
    String user="root";
    String password="";
    String driver="com.mysql.cj.jdbc.Driver";
    Connection cnx;
    public Conexion(){
        
    }
    public Connection conectar(){
        
        try {
            Class.forName(driver);
            cnx=DriverManager.getConnection(url+bd,user,password);
            System.out.println("Conexion realizada con exito a "+bd);
           
            
            
        } catch (ClassNotFoundException | SQLException ex ) {
            System.out.println("Fallo la conexion a "+bd);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE,null,ex);
        }
        return cnx;
    } 
    
}

