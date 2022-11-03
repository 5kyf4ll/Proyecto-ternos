package Clases2;
import java.sql.*;import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**** @author Miguel Silva ****/
public class Controlador {
 //public static Conexion Base=new Conexion();
 public static Conexion2 Base = Conexion2.getInstance();  
 public int Veces=0;public String[] Data=new String[20];

 public void MarcarTexto(JTextField tx){
  tx.setSelectionStart(0);tx.setSelectionEnd(tx.getText().length());
  tx.grabFocus();
 } 
         
 public ResultSet RegistroSet(String consulta){
     ResultSet r=null;
     try {
      Base.st=Base.conec.createStatement();r=Base.st.executeQuery(consulta);
     } catch (Exception e) {}
     return r;
 }
 public void ActualizarRegistro(String consulta){
     try {
      Base.st=Base.conec.createStatement();Base.st.executeUpdate(consulta);
     } catch (Exception e) {e.printStackTrace();}   
 }
 public void LlenarJCombo(JComboBox cb,String consulta,int posi){
  cb.removeAllItems();
     try {
      Base.rt=RegistroSet(consulta);
         while (Base.rt.next()) {             
          cb.addItem(Base.rt.getString(posi));
         }
         cb.setSelectedIndex(-1);
     } catch (Exception e) {}
 }
 public void LlenarJtable(DefaultTableModel md,String cd,int ctclm){
  try{String[] dt= new String[20];Base.rt=RegistroSet(cd);
   while(md.getRowCount()>0) md.removeRow(0);
    while (Base.rt.next()) {
     for(int i=1;i<=ctclm;i++)
      dt[i-1]=Base.rt.getString(i);           
     md.addRow(dt);
    }
  }catch(Exception e){}
 }
 public boolean VerificaConsulta(String Cst){
  boolean b=false;
  try{
   Base.rt=RegistroSet(Cst);
   b=Base.rt.next();
  }   
  catch(Exception e){}
  return b;
 }
 public String DevolverRegistroDato(String cnst,int coldev){
  String dtres="";
  try{
   Base.rt=RegistroSet(cnst);
   if(Base.rt.next())dtres=Base.rt.getString(coldev);
  }
  catch(Exception e){}
  return dtres;
 }
 public String[] DevolverUnRegistroDatos(String cnst,int cantcol){
  String[] dtres=new String[15];
  try{
   Base.rt=RegistroSet(cnst);int ct=0;
   if(Base.rt.next()){
    for(int id=1;id<=cantcol;id++)
     dtres[id-1]=Base.rt.getString(id);   
   }
  }
  catch(Exception e){}
  return dtres;
 }
 
 public void imprimirParametro(String reporte, String para, String cb) {

        try {

            Map parame = new HashMap();
            String rta = System.getProperties().getProperty("user.dir")
                    + "/src/Reportes/" + reporte + ".jasper";
            parame.put(para, cb);
            JasperPrint prt = JasperFillManager.fillReport(rta, parame, Base.conec);
            int n = prt.getPages().size();
            if (n > 0) {
                if (JOptionPane.showConfirmDialog(null, "Deseas Previsualizar primero", "Confirmar", 0) == 0) {
                    JasperViewer JsperView = new JasperViewer(prt, false);
                    JsperView.setTitle("Reporte");
                    JsperView.setExtendedState(6);
                    JsperView.show();
                } else {
                    net.sf.jasperreports.engine.JasperPrintManager.printReport(prt, false);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No hay datos para mostrar", "Report",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (JRException E) {
            JOptionPane.showMessageDialog(null, E.getMessage());
            E.printStackTrace();
        }

    }
 
}
