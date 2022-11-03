/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases2;


/************** @author Ing. Miguel Angel Silva Zapata ******************/
import Clases2.Conexion2;
import java.io.*;import java.sql.*;import java.util.*;
import java.util.logging.*;import javax.swing.*;
/**
 *
 * @author PERSONAL
 */
public class Conexion2 {
     public PreparedStatement prest = null;public CallableStatement cllst = null;
    public static Connection conec = null;public Statement st = null;
    public ResultSet rt = null;public static String user, password, host, db, usu;
    //***************Atributos*********************
    
    //*****************Métodos*********************
    private static Conexion2 instance = null;
     static {
      String properties = "src/Clases2/Configuracion.properties";
      PropertyResourceBundle file;
      try {
        file = new PropertyResourceBundle(new FileInputStream(properties));
        user = file.getString("user");password = file.getString("password");
        host = file.getString("host");db = file.getString("bd");
       }
      catch (Exception e) { }
    }    
    
    public static Conexion2 getInstance() {
     if (instance == null) {instance = new Conexion2();Conectar();}
      try {if (conec.isClosed()) Conectar();}
     catch (SQLException ex) {
      Logger.getLogger(Conexion2.class.getName()).log(Level.SEVERE, null, ex);
     }
     return instance;
    }
    
    public static Connection Conectar() {
      try {
       String url = "jdbc:mysql://" + host + "/" + db;
       //Class.forName("com.mysql.jdbc.Driver").newInstance();
       Class.forName("com.mysql.cj.jdbc.Driver"); 
       conec = DriverManager.getConnection(url, user, password);
      }
      catch (Exception e) {
       e.printStackTrace();
       JOptionPane.showMessageDialog(null, 
       "Ha ocurrido un error al realizar la conexión al servidor de base de datos.\n"
       + "Verifique los parámetros de conexión.\n\nDetalle de error:\n\n" + e +
       "", "Error de conexión", JOptionPane.ERROR_MESSAGE); System.exit(0);
      }
      return conec;
    }
}
