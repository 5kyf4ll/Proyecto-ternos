/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import com.toedter.calendar.JDateChooser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author USUARIO
 */
public class Funciones {
    SimpleDateFormat Formato = new SimpleDateFormat("dd-MM-yyyy");
 public String getFecha(JDateChooser jd){
 if (jd.getDate()!=null){
 return Formato.format(jd.getDate());
 }else{
 return null;
 }
 }

public java.util.Date StringADate(String fecha){
 SimpleDateFormat formato_del_Texto = new SimpleDateFormat("yyyy-MM-dd");
 Date fechaE=null;
 try {
 fechaE = formato_del_Texto.parse(fecha);
 return fechaE;
 } catch (ParseException ex) {
 return null;
 }
    
}
}
