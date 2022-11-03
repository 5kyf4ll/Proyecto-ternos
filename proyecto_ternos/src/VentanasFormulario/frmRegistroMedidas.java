/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VentanasFormulario;

import Clases.Conexion;
import Clases.limpiar;
import Clases2.Controlador;
import Formularios.frmPersona;
import Formularios.frmPrueba;
import Formularios.frmVenta_Confec;
import static VentanasFormulario.frmRegistroCliente.jFormattedDni;
import static VentanasFormulario.frmRegistroCliente.jFormattedTelefono;
import static VentanasFormulario.frmRegistroCliente.jFormattedTextFieldRuc;
import static VentanasFormulario.frmRegistroCliente.jTextFieldDirec;
import static VentanasFormulario.frmRegistroCliente.jTextFieldSocial;
import static VentanasFormulario.frmRegistroCliente.jTextField_apellido_m;
import static VentanasFormulario.frmRegistroCliente.jTextField_apellido_p;
import static VentanasFormulario.frmRegistroCliente.jTextField_direccion;
import static VentanasFormulario.frmRegistroCliente.jTextField_email;
import static VentanasFormulario.frmRegistroCliente.jTextField_nombre;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PERSONAL
 */
public class frmRegistroMedidas extends javax.swing.JFrame {

    /**
     * Creates new form frmRegistroMedidas
     */
    DefaultTableModel tabla = new DefaultTableModel();
    DefaultTableModel tabla2 = new DefaultTableModel();
    Conexion cn = new Conexion();
    limpiar lim = new limpiar();
    Controlador control=new Controlador();
    Controlador ctl=new Controlador();
    public frmRegistroMedidas() {
        initComponents();
        
        //jFormattedDni.grabFocus();
        jFormattedDni.setEnabled(false);
        jTextField_nombre.setEnabled(false);
        jTextField_apellido_m.setEnabled(false);
        jTextField_apellido_p.setEnabled(false);
        jTextField_direccion.setEnabled(false);
        jTextField_email.setEnabled(false);
        jFormattedTelefono.setEnabled(false);
        jFormattedTextFieldRu.setEnabled(false);
        jTextFieldDirec.setEnabled(false);
        jTextFieldSocial.setEnabled(false);
    }
    public void nombresTitulos() {
        String[] tit = {"ID Cliente Medida","CLIENTE" , "FK ID Medida saco", "FK ID Medida pantalon"};
        tabla.setColumnIdentifiers(tit);
        jTable1.setModel(tabla);
        
    }
     public void nombresTitulosRuc() {
        String[] tit = {"ID Cliente Medida","RAZON SOCIAL","FK ID Medida saco", "FK ID Medida pantalon"};
        tabla2.setColumnIdentifiers(tit);
        jTable1.setModel(tabla2);
        
    }
    
     public void cargarDatos() {
        try {
            lim.limpiarTabla(jTable1, tabla);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = cn.conectar();
            String sql = "select * from v_MedDni ";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {200, 200, 200, 200,200};
            for (int i = 0; i < jTable1.getColumnCount(); i++) {
                jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchosEs[i]);

            }
            while (rs.next()) {
                Object[] filas = new Object[canatidadColumnasEs];
                for (int i = 0; i < canatidadColumnasEs; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                tabla.addRow(filas);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
      public void cargarDatosRuc() {
        try {
            lim.limpiarTabla(jTable1, tabla2);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = cn.conectar();
            String sql = "select * from v_MedRuc ";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {200, 200, 200, 200,200};
            for (int i = 0; i < jTable1.getColumnCount(); i++) {
                jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchosEs[i]);

            }
            while (rs.next()) {
                Object[] filas = new Object[canatidadColumnasEs];
                for (int i = 0; i < canatidadColumnasEs; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                tabla2.addRow(filas);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    public void mostrardato(){
        String cant=control.DevolverRegistroDato(" select CHARACTER_LENGTH('"+jFormattedDni.getText()+"');", 1);
        if(control.VerificaConsulta("SELECT * from v_dni where DNI='"+jFormattedDni.getText()+"'") ){
            jTextField_nombre.setText(control.DevolverRegistroDato("SELECT p.nombre from persona p where p.dni='"+jFormattedDni.getText()+"';", 1));
            jTextField_apellido_p.setText(control.DevolverRegistroDato("SELECT p.apellido_p from persona p where p.dni='"+jFormattedDni.getText()+"';", 1));
            jTextField_apellido_m.setText(control.DevolverRegistroDato("SELECT p.apellido_m from persona p where p.dni='"+jFormattedDni.getText()+"';", 1));
            jTextField_direccion.setText(control.DevolverRegistroDato("SELECT p.direccion from persona p where p.dni='"+jFormattedDni.getText()+"';", 1));
            jFormattedTelefono.setText(control.DevolverRegistroDato("SELECT p.telefono from persona p where p.dni='"+jFormattedDni.getText()+"';", 1));
            jTextField_email.setText(control.DevolverRegistroDato("SELECT p.email from persona p where p.dni='"+jFormattedDni.getText()+"';", 1));
        
        }
        
//        else if("8".equals(cant)){
//            frmMedidasDni frm=new frmMedidasDni();
//            frm.setVisible(true);
//            frmMedidasDni.jFormattedTextDni.setText(""+jFormattedDni.getText());
//            
//        }
//        else{
//            jLabel13.setVisible(true);
//        }
        System.out.println(""+jFormattedDni.getText());
        System.out.println(cant);
    
    }
    public void mostrardatoRuc(){
        String cant=control.DevolverRegistroDato(" select CHARACTER_LENGTH('"+jFormattedTextFieldRu.getText()+"');", 1);
        if(control.VerificaConsulta("SELECT * from v_ruc where ruc='"+jFormattedTextFieldRu.getText()+"'") ){
            jTextFieldSocial.setText(control.DevolverRegistroDato("SELECT p.razon from persona p where p.ruc='"+jFormattedTextFieldRu.getText()+"';", 1));
            jTextFieldDirec.setText(control.DevolverRegistroDato("SELECT p.direccion from persona p where p.ruc='"+jFormattedTextFieldRu.getText()+"';", 1));
        
        }
        
        else if("11".equals(cant)){
            frmMedidadRuc frm=new frmMedidadRuc();
            frm.setVisible(true);
            frmMedidadRuc.jFormattedTextFieldRuc.setText(jFormattedTextFieldRu.getText());
            
        }
        else{
            jLabel13.setVisible(true);
        }
        System.out.println(""+jFormattedDni.getText());
        System.out.println(cant);
    
    }
    public void limpiar(){
         jTextFieldCintuta2.setText("");
         jTextFieldCasdera2.setText("");
         jTextFieldmuslo.setText("");
         jTextFieldRodilla.setText("");
         jTextFieldBoca.setText("");
         jTextFieldBoca.setText("");
         jTextFieldJareta.setText("");
         jTextFieldLsrgo2.setText("");
         jTextFieldCintura.setText("");
         jTextFieldCadera.setText("");
         jTextFieldEspalda.setText("");
         jTextFieldTalle.setText("");
         jTextFieldHombro.setText("");
         jTextFieldManga.setText("");
         jTextFieldLargo.setText("");
         jTextFieldPecho1.setText("");
         jTextFieldBusto.setText("");
         jTextFieldSeparacion.setText("");
         jFormattedDni.setText("");
        jTextField_nombre.setText("");
        jTextField_apellido_p.setText("");
        jTextField_direccion.setText("");
        jFormattedTelefono.setText("");
        jTextField_email.setText("");
        jTextField_apellido_m.setText("");
        jFormattedTextFieldRu.setText("");
        jTextFieldSocial.setText("");
        jTextFieldDirec.setText("");
    }
    public void insertPantalon(){
       ctl.ActualizarRegistro("call p_insertMp('"+jFormattedDni.getText()+"','"+jTextFieldCintuta2.getText()+"',"
               + "'"+jTextFieldCasdera2.getText()+"','"+jTextFieldmuslo.getText()+"',"
                       + "'"+jTextFieldRodilla.getText()+"','"+jTextFieldBoca.getText()+"',"
                               + "'"+jTextFieldJareta.getText()+"','"+jTextFieldLsrgo2.getText()+"')");
    
    }
    public void insertSaco(){
       ctl.ActualizarRegistro("call p_insertMs('"+jFormattedDni.getText()+"','"+jTextFieldCintura.getText()+"',"
               + "'"+jTextFieldCadera.getText()+"','"+jTextFieldEspalda.getText()+"','"+jTextFieldTalle.getText()+"',"
                       + "'"+jTextFieldHombro.getText()+"','"+jTextFieldManga.getText()+"','"+jTextFieldLargo.getText()+"',"
                               + "'"+jTextFieldPecho1.getText()+"','"+jTextFieldBusto.getText()+"','"+jTextFieldSeparacion.getText()+"')");
    
    }
    public void insertPantalonRuc(){
        String msg=control.DevolverRegistroDato("SELECT p.id_persona from persona p where p.ruc='"+jFormattedTextFieldRu.getText()+"';", 1);
        ctl.ActualizarRegistro("call p_insertMp('"+msg+"','"+jTextFieldCintuta2.getText()+"',"
               + "'"+jTextFieldCasdera2.getText()+"','"+jTextFieldmuslo.getText()+"',"
                       + "'"+jTextFieldRodilla.getText()+"','"+jTextFieldBoca.getText()+"',"
                               + "'"+jTextFieldJareta.getText()+"','"+jTextFieldLsrgo2.getText()+"')");
    
    }
    public void insertSacoRuc(){
        String msg=control.DevolverRegistroDato("SELECT p.id_persona from persona p where p.ruc='"+jFormattedTextFieldRu.getText()+"';", 1);
        ctl.ActualizarRegistro("call p_insertMs('"+msg+"','"+jTextFieldCintura.getText()+"',"
               + "'"+jTextFieldCadera.getText()+"','"+jTextFieldEspalda.getText()+"','"+jTextFieldTalle.getText()+"',"
                       + "'"+jTextFieldHombro.getText()+"','"+jTextFieldManga.getText()+"','"+jTextFieldLargo.getText()+"',"
                               + "'"+jTextFieldPecho1.getText()+"','"+jTextFieldBusto.getText()+"','"+jTextFieldSeparacion.getText()+"')");
    
    }
     public void guardarDatosPersonaDNI() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into persona(id_persona,DNI,nombre,apellido_p,apellido_m,direccion,telefono,email,fk_idtipo_persona)values (?,?,?,?,?,?,?,?,?);");
            ps.setString(1, jFormattedDni.getText());
             ps.setString(2, jFormattedDni.getText());
            ps.setString(3, jTextField_nombre.getText());
            ps.setString(4, jTextField_apellido_p.getText());
            ps.setString(5, jTextField_apellido_m.getText());
            ps.setString(6, jTextField_direccion.getText());
            ps.setString(7, jFormattedTelefono.getText());
            ps.setString(8, jTextField_email.getText());
            ps.setString(9, "1");

            ps.execute();
            
            //cargarDatos();
            

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
     public void guardarDatosCliente() {
        guardarDatosPersonaDNI();
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into cliente(id_cliente,fk_id_persona)values (?,?);");
            
            String msg=control.DevolverRegistroDato("SELECT p.id_persona from persona p where p.dni='"+jFormattedDni.getText()+"';", 1);
            ps.setString(1, null);
            ps.setString(2, msg);
            ps.execute();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    
     public void guardarDatos() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into cliente_medidas (idCleinteM,fk_id_cliente,fk_idMedidasaco,fk_idmedidapantalon) values (?,?,?,?);");
            String msg=control.DevolverRegistroDato("SELECT p.id_persona from persona p where p.dni='"+jFormattedDni.getText()+"';", 1);
            String cli=control.DevolverRegistroDato("SELECT c.id_cliente from cliente c WHERE c.fk_id_persona='"+msg+"';", 1);
            ps.setString(1, jFormattedDni.getText());
            ps.setString(2, cli);
            ps.setString(3, jFormattedDni.getText());
            ps.setString(4, jFormattedDni.getText());
            ps.execute();
            //JOptionPane.showMessageDialog(null, "Se guardo con éxito");
            
            String aux2=control.DevolverRegistroDato("Select id_persona from persona where DNI='"+jFormattedDni.getText()+"'", 1);
            String aux=control.DevolverRegistroDato("Select id_Cliente from cliente where fk_id_Persona='"+aux2+"';", 1);
            frmVenta_Confec.text_cod.setText(""+aux);
            frmVenta_Confec.text_Medidas.setText(jFormattedDni.getText());
            frmVenta_Confec.text_dni.setText(""+jFormattedDni.getText());
            frmVenta_Confec.text_cliente.setText(jTextField_nombre.getText()+" "+jTextField_apellido_p.getText()+" "+jTextField_apellido_m.getText());
            frmVenta_Confec.text_telf.setText(jFormattedTelefono.getText());
            frmVenta_Confec.text_Correo.setText(jTextField_email.getText());
            frmVenta_Confec.text_Direccion.setText(jTextField_direccion.getText());
            //limpiar();

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    public void guardarDatosRuc() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into cliente_medidas (idCleinteM,fk_id_cliente,fk_idMedidasaco,fk_idmedidapantalon) values (?,?,?,?);");
            String msg=control.DevolverRegistroDato("SELECT id_persona from persona  where ruc='"+jFormattedTextFieldRu.getText()+"';", 1);
            String cli=control.DevolverRegistroDato("SELECT id_cliente from cliente  where fk_id_persona='"+msg+"';", 1);
            System.out.println(""+msg);
            System.out.println(""+cli);
            ps.setString(1, msg);
            ps.setString(2, cli);
            ps.setString(3, msg);
            ps.setString(4, msg);
            ps.execute();
            //JOptionPane.showMessageDialog(null, "Se guardo con éxito");
            String aux2=control.DevolverRegistroDato("Select id_persona from persona where ruc='"+jFormattedTextFieldRuc.getText()+"'", 1);
            String aux=control.DevolverRegistroDato("Select id_Cliente from cliente where fk_id_Persona='"+aux2+"';", 1);
            frmVenta_Confec.text_cod.setText(""+aux);
            frmVenta_Confec.text_Medidas.setText(msg);
            frmVenta_Confec.text_dni.setText(""+jFormattedTextFieldRuc.getText());
            frmVenta_Confec.text_cliente.setText(jTextFieldSocial.getText());
            frmVenta_Confec.text_Direccion.setText(jTextFieldDirec.getText());
            cargarDatosRuc();

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    
     public void llenarDatos() {
        int filas;
        int n = jTable1.getSelectedRow();
        String idMedidas=(jTable1.getValueAt(n, 0).toString());
        String cli=control.DevolverRegistroDato("SELECT fk_id_cliente from cliente_medidas WHERE idCleinteM='"+idMedidas+"';", 1);
        String saco=(jTable1.getValueAt(n, 2).toString());
        String pantalon=(jTable1.getValueAt(n, 3).toString());
        filas = n;
        jFormattedDni.setEnabled(false);
        String cliente=control.DevolverRegistroDato("SELECT fk_id_persona from cliente WHERE id_Cliente='"+cli+"';", 1);
        jFormattedDni.setText(control.DevolverRegistroDato("SELECT p.DNI from persona p where p.id_persona='"+cliente+"';", 1));
            jTextField_nombre.setText(control.DevolverRegistroDato("SELECT p.nombre from persona p where p.id_persona='"+cliente+"';", 1));
            jTextField_apellido_p.setText(control.DevolverRegistroDato("SELECT p.apellido_p from persona p where p.id_persona='"+cliente+"';", 1));
            jTextField_apellido_m.setText(control.DevolverRegistroDato("SELECT p.apellido_m from persona p where p.id_persona='"+cliente+"';", 1));
            jTextField_direccion.setText(control.DevolverRegistroDato("SELECT p.direccion from persona p where p.id_persona='"+cliente+"';", 1));
            jFormattedTelefono.setText(control.DevolverRegistroDato("SELECT p.telefono from persona p where p.id_persona='"+cliente+"';", 1));
            jTextField_email.setText(control.DevolverRegistroDato("SELECT p.email from persona p where p.id_persona='"+cliente+"';", 1));
            
            jFormattedTextFieldRu.setText(control.DevolverRegistroDato("SELECT p.ruc from persona p where p.id_persona='"+cliente+"';", 1));
            jTextFieldSocial.setText(control.DevolverRegistroDato("SELECT p.razon from persona p where p.id_persona='"+cliente+"';", 1));
            jTextFieldDirec.setText(control.DevolverRegistroDato("SELECT p.direccion from persona p where p.id_persona='"+cliente+"';", 1));
        
        jTextFieldCintuta2.setText(control.DevolverRegistroDato("select cintura from medidapantalon WHERE idmedidapantalon='"+pantalon+"'", 1));
        jTextFieldCasdera2.setText(control.DevolverRegistroDato("select cadera from medidapantalon WHERE idmedidapantalon='"+pantalon+"'", 1));
        jTextFieldmuslo.setText(control.DevolverRegistroDato("select muslo from medidapantalon WHERE idmedidapantalon='"+pantalon+"'", 1));
        jTextFieldRodilla.setText(control.DevolverRegistroDato("select rodilla from medidapantalon WHERE idmedidapantalon='"+pantalon+"'", 1));
        jTextFieldBoca.setText(control.DevolverRegistroDato("select boca from medidapantalon WHERE idmedidapantalon='"+pantalon+"'", 1));
        jTextFieldJareta.setText(control.DevolverRegistroDato("select jareta from medidapantalon WHERE idmedidapantalon='"+pantalon+"'", 1));
        jTextFieldLsrgo2.setText(control.DevolverRegistroDato("select largo from medidapantalon WHERE idmedidapantalon='"+pantalon+"'", 1));
        
        jTextFieldCintura.setText(control.DevolverRegistroDato("select cintura from medidasaco WHERE idmedidasaco='"+saco+"';", 1));
        jTextFieldCadera.setText(control.DevolverRegistroDato("select cadera from medidasaco WHERE idmedidasaco='"+saco+"';", 1));
        jTextFieldEspalda.setText(control.DevolverRegistroDato("select espalda from medidasaco WHERE idmedidasaco='"+saco+"';", 1));
        jTextFieldTalle.setText(control.DevolverRegistroDato("select talle from medidasaco WHERE idmedidasaco='"+saco+"';", 1));
        jTextFieldHombro.setText(control.DevolverRegistroDato("select hombro from medidasaco WHERE idmedidasaco='"+saco+"';", 1));
        jTextFieldManga.setText(control.DevolverRegistroDato("select manga from medidasaco WHERE idmedidasaco='"+saco+"';", 1));
        jTextFieldLargo.setText(control.DevolverRegistroDato("select largo from medidasaco WHERE idmedidasaco='"+saco+"';", 1));
        jTextFieldPecho1.setText(control.DevolverRegistroDato("select pecho from medidasaco WHERE idmedidasaco='"+saco+"';", 1));
        jTextFieldBusto.setText(control.DevolverRegistroDato("select busto from medidasaco WHERE idmedidasaco='"+saco+"';", 1));
        jTextFieldSeparacion.setText(control.DevolverRegistroDato("select separacion from medidasaco WHERE idmedidasaco='"+saco+"';", 1));
        
    }
    public void actualizarDatos() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("update persona set DNI=?,nombre=?,apellido_p=?,apellido_m=?,direccion=?,telefono=?,email=?,fk_idtipo_persona=?  where id_persona=?;");
            String ca=control.DevolverRegistroDato("select fk_idtipo_persona from persona WHERE DNI='"+jFormattedDni.getText()+"';", 1);
            String id=control.DevolverRegistroDato("select id_persona from persona WHERE DNI='"+jFormattedDni.getText()+"';", 1);
            ps.setString(1, jFormattedDni.getText());
            ps.setString(2, jTextField_nombre.getText());
            ps.setString(3, jTextField_apellido_p.getText());
            ps.setString(4, jTextField_apellido_m.getText());
            ps.setString(5, jTextField_direccion.getText());
            ps.setString(6, jFormattedTelefono.getText());
            ps.setString(7, jTextField_email.getText());
            ps.setString(8, ca);
            ps.setString(9, id);
            ps.execute();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar");
            System.out.println(e.toString());
        }
    }
    public void actualizarDatosRuc() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("update persona set ruc=?, razon=?, direccion=?,fk_idtipo_persona=?  where id_persona=?;");
            String ca=control.DevolverRegistroDato("select fk_idtipo_persona from persona WHERE ruc='"+jFormattedTextFieldRu.getText()+"';", 1);
            String id=control.DevolverRegistroDato("select id_persona from persona WHERE ruc='"+jFormattedTextFieldRu.getText()+"';", 1);
            ps.setString(1, jFormattedTextFieldRu.getText());
            ps.setString(2, jTextFieldSocial.getText());
            ps.setString(3, jTextFieldDirec.getText());
            ps.setString(4, ca);
            ps.setString(5, id);
            ps.execute();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar");
            System.out.println(e.toString());
        }
    }
    public void modificarSaco(){
        PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
            ps=con.prepareStatement("call p_updateMs(?,?,?,?,?,?,?,?,?,?,?);");
           ps.setString(1,jFormattedDni.getText());
          ps.setString(2,jTextFieldCintura.getText());
           ps.setString(3,jTextFieldCadera.getText());
            ps.setString(4,jTextFieldEspalda.getText());
            ps.setString(5,jTextFieldTalle.getText());
            ps.setString(6,jTextFieldHombro.getText());
            ps.setString(7,jTextFieldManga.getText());
            ps.setString(8,jTextFieldLargo.getText());
             ps.setString(9,jTextFieldPecho1.getText());     
              ps.setString(10,jTextFieldBusto.getText());
                ps.setString(11,jTextFieldSeparacion.getText());
            ps.execute();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en actualizacion!");
            System.out.println(e.toString());
        }
    }
    public void modificarPantalon(){
        PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
            ps=con.prepareStatement("call p_updateMp(?,?,?,?,?,?,?,?);;");
           ps.setString(1,jFormattedDni.getText());
           ps.setString(2,jTextFieldCintuta2.getText());
           ps.setString(3,jTextFieldCasdera2.getText());
            ps.setString(4,jTextFieldmuslo.getText());
            ps.setString(5,jTextFieldRodilla.getText());
            ps.setString(6,jTextFieldBoca.getText());
            ps.setString(7,jTextFieldJareta.getText());
            ps.setString(8,jTextFieldLsrgo2.getText());
            ps.execute();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en actualizacion!");
            System.out.println(e.toString());
        }
    }
    
    
    public void modificarSacoRuc(){
        PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
            ps=con.prepareStatement("call p_updateMs(?,?,?,?,?,?,?,?,?,?,?);");
            String msg=control.DevolverRegistroDato("SELECT p.id_persona from persona p where p.ruc='"+jFormattedTextFieldRu.getText()+"';", 1);
           ps.setString(1,msg);
          ps.setString(2,jTextFieldCintura.getText());
           ps.setString(3,jTextFieldCadera.getText());
            ps.setString(4,jTextFieldEspalda.getText());
            ps.setString(5,jTextFieldTalle.getText());
            ps.setString(6,jTextFieldHombro.getText());
            ps.setString(7,jTextFieldManga.getText());
            ps.setString(8,jTextFieldLargo.getText());
             ps.setString(9,jTextFieldPecho1.getText());     
              ps.setString(10,jTextFieldBusto.getText());
                ps.setString(11,jTextFieldSeparacion.getText());
            ps.execute();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en actualizacion!");
            System.out.println(e.toString());
        }
    }
    public void modificarPantalonRuc(){
        PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
             ps=con.prepareStatement("call p_updateMp(?,?,?,?,?,?,?,?);;");
            String msg=control.DevolverRegistroDato("SELECT p.id_persona from persona p where p.ruc='"+jFormattedTextFieldRu.getText()+"';", 1);
           ps.setString(1,msg);
           ps.setString(2,jTextFieldCintuta2.getText());
           ps.setString(3,jTextFieldCasdera2.getText());
            ps.setString(4,jTextFieldmuslo.getText());
            ps.setString(5,jTextFieldRodilla.getText());
            ps.setString(6,jTextFieldBoca.getText());
            ps.setString(7,jTextFieldJareta.getText());
            ps.setString(8,jTextFieldLsrgo2.getText());
            ps.execute();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en actualizacion!");
            System.out.println(e.toString());
        }
    }
    public void MostrarFacultades(){
     ctl.LlenarJtable(tabla,"select * from v_meddni where idCleinteM like'%"
     +jTextField_buscar.getText()+"%'",4);    
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lblcab = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextFieldCintura = new javax.swing.JTextField();
        jTextFieldCadera = new javax.swing.JTextField();
        jTextFieldEspalda = new javax.swing.JTextField();
        jTextFieldTalle = new javax.swing.JTextField();
        jTextFieldHombro = new javax.swing.JTextField();
        jTextFieldPecho1 = new javax.swing.JTextField();
        jTextFieldBusto = new javax.swing.JTextField();
        jTextFieldSeparacion = new javax.swing.JTextField();
        jTextFieldLargo = new javax.swing.JTextField();
        jTextFieldManga = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldCintuta2 = new javax.swing.JTextField();
        jTextFieldCasdera2 = new javax.swing.JTextField();
        jTextFieldmuslo = new javax.swing.JTextField();
        jTextFieldRodilla = new javax.swing.JTextField();
        jTextFieldBoca = new javax.swing.JTextField();
        jTextFieldJareta = new javax.swing.JTextField();
        jTextFieldLsrgo2 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jFormattedDni = new javax.swing.JFormattedTextField();
        jLabel28 = new javax.swing.JLabel();
        jTextField_nombre = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jTextField_apellido_p = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jTextField_apellido_m = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jTextField_direccion = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jFormattedTelefono = new javax.swing.JFormattedTextField();
        jLabel33 = new javax.swing.JLabel();
        jTextField_email = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jFormattedTextFieldRu = new javax.swing.JFormattedTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextFieldSocial = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jTextFieldDirec = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        pnel_inf1 = new javax.swing.JPanel();
        jTextField_buscar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblcab.setBackground(new java.awt.Color(204, 204, 204));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("CLIENTE MEDIDAS");

        javax.swing.GroupLayout lblcabLayout = new javax.swing.GroupLayout(lblcab);
        lblcab.setLayout(lblcabLayout);
        lblcabLayout.setHorizontalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblcabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addContainerGap(1003, Short.MAX_VALUE))
        );
        lblcabLayout.setVerticalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(lblcab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 30));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setText("Cintura:");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 60, 40));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setText("Cadera:");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 49, 20));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setText("Espalda:");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 49, -1));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setText("Alto de busto:");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, 80, 20));

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setText("Pecho:");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, -1, 30));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Talle:");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 37, 20));

        jLabel19.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel19.setText("Separación:");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 180, 84, 30));

        jLabel20.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel20.setText("Hombro:");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, 20));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos del proyecto final/sacos-gamarra-02-Mckeover (1).png"))); // NOI18N
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 40, 140, -1));

        jTextFieldCintura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCinturaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCinturaKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldCintura, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 120, -1));

        jTextFieldCadera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCaderaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCaderaKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldCadera, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 118, -1));

        jTextFieldEspalda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldEspaldaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldEspaldaKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldEspalda, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 118, -1));

        jTextFieldTalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldTalleKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldTalleKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldTalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 120, -1));

        jTextFieldHombro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldHombroKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldHombroKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldHombro, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 120, -1));

        jTextFieldPecho1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldPecho1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPecho1KeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldPecho1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, 118, -1));

        jTextFieldBusto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBustoActionPerformed(evt);
            }
        });
        jTextFieldBusto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldBustoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldBustoKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldBusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 118, -1));

        jTextFieldSeparacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldSeparacionKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldSeparacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, 118, -1));

        jTextFieldLargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLargoActionPerformed(evt);
            }
        });
        jTextFieldLargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldLargoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldLargoKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldLargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, 118, -1));

        jTextFieldManga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldMangaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldMangaKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldManga, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 118, -1));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel15.setText("Largo:");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, -1, -1));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setText("Manga: ");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 60, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, 670, 230));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Cintura:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 59, 30));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Cadera:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 69, 30));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Muslo:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 37, 30));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Largo:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, 37, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Boca:");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 37, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Rodilla:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, 65, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("Jareta:");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, -1, -1));

        jTextFieldCintuta2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCintuta2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCintuta2KeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldCintuta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 118, -1));

        jTextFieldCasdera2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCasdera2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCasdera2KeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldCasdera2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 118, -1));

        jTextFieldmuslo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldmusloKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldmusloKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldmuslo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 118, -1));

        jTextFieldRodilla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldRodillaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldRodillaKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldRodilla, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 105, -1));

        jTextFieldBoca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBocaActionPerformed(evt);
            }
        });
        jTextFieldBoca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldBocaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldBocaKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldBoca, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 105, -1));

        jTextFieldJareta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldJaretaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldJaretaKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldJareta, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 105, -1));

        jTextFieldLsrgo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldLsrgo2KeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldLsrgo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 105, -1));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos del proyecto final/pantalon-satinado-microfibra-para-hombre (1).png"))); // NOI18N
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 160, 170));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 330, 670, -1));

        jToolBar2.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar2.setRollover(true);

        jButton1.setBackground(new java.awt.Color(102, 204, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/eliminar.png"))); // NOI18N
        jButton1.setText("Eliminar");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton1);

        jButton4.setBackground(new java.awt.Color(102, 204, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/Reniciar.png"))); // NOI18N
        jButton4.setText("Reiniciar");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton4);

        jButton2.setBackground(new java.awt.Color(102, 204, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/puerta-de-salida.png"))); // NOI18N
        jButton2.setText("Salir");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton2);

        jPanel1.add(jToolBar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 400, -1));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("DNI :");

        try {
            jFormattedDni.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedDniKeyPressed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel28.setText("Nombre :");

        jTextField_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_nombreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_nombreKeyTyped(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel29.setText("Apellido Paterno :");

        jTextField_apellido_p.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_apellido_pKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_apellido_pKeyTyped(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel30.setText("Apellido Materno:");

        jTextField_apellido_m.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_apellido_mKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_apellido_mKeyTyped(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel31.setText("Direccion :");

        jTextField_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_direccionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_direccionKeyTyped(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel32.setText("Telefono :");

        try {
            jFormattedTelefono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTelefonoKeyPressed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel33.setText("Correo :");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(105, 105, 105)
                        .addComponent(jFormattedDni, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(79, 79, 79)
                        .addComponent(jTextField_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(31, 31, 31)
                        .addComponent(jTextField_apellido_p, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addGap(32, 32, 32)
                        .addComponent(jTextField_apellido_m, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addGap(75, 75, 75)
                        .addComponent(jFormattedTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel33)
                            .addGap(85, 85, 85)
                            .addComponent(jTextField_email))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel31)
                            .addGap(70, 70, 70)
                            .addComponent(jTextField_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(140, 140, 140))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2))
                    .addComponent(jFormattedDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel28))
                    .addComponent(jTextField_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel29))
                    .addComponent(jTextField_apellido_p, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel30))
                    .addComponent(jTextField_apellido_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel31))
                    .addComponent(jTextField_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel32))
                    .addComponent(jFormattedTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel33))
                    .addComponent(jTextField_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("DATOS DNI", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        try {
            jFormattedTextFieldRu.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldRu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextFieldRuKeyPressed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel25.setText("RUC");

        jLabel26.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel26.setText("Razon Social");

        jTextFieldSocial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSocialKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldSocialKeyTyped(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel27.setText("Direccion");

        jTextFieldDirec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldDirecKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel25)
                    .addComponent(jLabel27))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextFieldSocial)
                        .addComponent(jTextFieldDirec, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jFormattedTextFieldRu, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jFormattedTextFieldRu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jTextFieldSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDirec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addContainerGap(198, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("DATOS RUC", jPanel6);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 390, 410));

        jButton6.setBackground(new java.awt.Color(255, 102, 0));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/agregar-archivo (4).png"))); // NOI18N
        jButton6.setText("Guardar");
        jButton6.setMaximumSize(new java.awt.Dimension(110, 30));
        jButton6.setMinimumSize(new java.awt.Dimension(110, 30));
        jButton6.setPreferredSize(new java.awt.Dimension(110, 30));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 540, -1, -1));

        jButton7.setBackground(new java.awt.Color(51, 153, 255));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/actualizar (2).png"))); // NOI18N
        jButton7.setText("Actualizar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 540, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 650, 870, 220));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("DNI");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("RUC");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 150, 30));

        pnel_inf1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204), 2));
        pnel_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_buscarKeyTyped(evt);
            }
        });
        pnel_inf1.add(jTextField_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 220, -1));

        jLabel10.setText("DNI");
        pnel_inf1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 30));

        jPanel1.add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 580, 370, 50));

        jButton5.setBackground(new java.awt.Color(51, 51, 255));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/buscar (1).png"))); // NOI18N
        jButton5.setText("Buscar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 580, -1, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 890));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //eliminarDatos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        limpiar();
        
        jTabbedPane1.setSelectedIndex(1);
        
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jFormattedTextFieldRuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextFieldRuKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            mostrardatoRuc();
            //radiobutton();
        }
    }//GEN-LAST:event_jFormattedTextFieldRuKeyPressed

    private void jTextFieldSocialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSocialKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldDirec.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldSocialKeyPressed

    private void jTextFieldSocialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSocialKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);

        }
    }//GEN-LAST:event_jTextFieldSocialKeyTyped

    private void jTextFieldDirecKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDirecKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);

        }
    }//GEN-LAST:event_jTextFieldDirecKeyTyped

    private void jFormattedDniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedDniKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            mostrardato();
            //radiobutton();
        }
    }//GEN-LAST:event_jFormattedDniKeyPressed

    private void jTextField_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_nombreKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_apellido_p.requestFocus();
        }
    }//GEN-LAST:event_jTextField_nombreKeyPressed

    private void jTextField_nombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_nombreKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);

        }
    }//GEN-LAST:event_jTextField_nombreKeyTyped

    private void jTextField_apellido_pKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_apellido_pKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_apellido_m.requestFocus();
        }
    }//GEN-LAST:event_jTextField_apellido_pKeyPressed

    private void jTextField_apellido_pKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_apellido_pKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);

        }
    }//GEN-LAST:event_jTextField_apellido_pKeyTyped

    private void jTextField_direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_direccionKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jFormattedTelefono.requestFocus();
        }
    }//GEN-LAST:event_jTextField_direccionKeyPressed

    private void jTextField_direccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_direccionKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);

        }
    }//GEN-LAST:event_jTextField_direccionKeyTyped

    private void jFormattedTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTelefonoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_email.requestFocus();
        }
    }//GEN-LAST:event_jFormattedTelefonoKeyPressed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(jRadioButton1.isSelected()){
            guardarDatosCliente();
            insertPantalon();
            insertSaco();
            guardarDatos();
            cargarDatos();
           
            //dispose();
        }
        else if(jRadioButton2.isSelected()){
            insertPantalonRuc();
            insertSacoRuc();
            guardarDatosRuc();
            cargarDatosRuc();
            //dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Elija la opccion DNI O RUC", "CUIDADO", JOptionPane.WARNING_MESSAGE, null);
        }
        limpiar();
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        
        
        if(jRadioButton1.isSelected()){
            actualizarDatos();
            modificarPantalon();
            modificarSaco();
            limpiar();
        }
        else if(jRadioButton2.isSelected()){
            actualizarDatosRuc();
            modificarPantalonRuc();
            modificarSacoRuc();
            limpiar();
        }
        else{
            JOptionPane.showMessageDialog(null, "Elija la opccion DNI O RUC", "CUIDADO", JOptionPane.WARNING_MESSAGE, null);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
     
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
        jFormattedTextFieldRu.setText("");
        jTextFieldSocial.setText("");
        jTextFieldDirec.setText("");
        
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jTextFieldLsrgo2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldLsrgo2KeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldLsrgo2KeyTyped

    private void jTextFieldJaretaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldJaretaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldJaretaKeyTyped

    private void jTextFieldJaretaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldJaretaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldLsrgo2.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldJaretaKeyPressed

    private void jTextFieldBocaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBocaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldBocaKeyTyped

    private void jTextFieldBocaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBocaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldJareta.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldBocaKeyPressed

    private void jTextFieldBocaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBocaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBocaActionPerformed

    private void jTextFieldRodillaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldRodillaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldRodillaKeyTyped

    private void jTextFieldRodillaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldRodillaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldBoca.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldRodillaKeyPressed

    private void jTextFieldmusloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldmusloKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldmusloKeyTyped

    private void jTextFieldmusloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldmusloKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldRodilla.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldmusloKeyPressed

    private void jTextFieldCasdera2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCasdera2KeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldCasdera2KeyTyped

    private void jTextFieldCasdera2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCasdera2KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldmuslo.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldCasdera2KeyPressed

    private void jTextFieldCintuta2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCintuta2KeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldCintuta2KeyTyped

    private void jTextFieldCintuta2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCintuta2KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldCasdera2.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldCintuta2KeyPressed

    private void jTextFieldMangaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMangaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldMangaKeyTyped

    private void jTextFieldMangaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMangaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldLargo.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldMangaKeyPressed

    private void jTextFieldLargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldLargoKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldLargoKeyTyped

    private void jTextFieldLargoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldLargoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldPecho1.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldLargoKeyPressed

    private void jTextFieldLargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLargoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLargoActionPerformed

    private void jTextFieldSeparacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSeparacionKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldSeparacionKeyTyped

    private void jTextFieldBustoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBustoKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldBustoKeyTyped

    private void jTextFieldBustoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBustoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldSeparacion.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldBustoKeyPressed

    private void jTextFieldBustoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBustoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBustoActionPerformed

    private void jTextFieldPecho1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPecho1KeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldPecho1KeyTyped

    private void jTextFieldPecho1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPecho1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldBusto.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldPecho1KeyPressed

    private void jTextFieldHombroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldHombroKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldHombroKeyTyped

    private void jTextFieldHombroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldHombroKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldManga.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldHombroKeyPressed

    private void jTextFieldTalleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTalleKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldTalleKeyTyped

    private void jTextFieldTalleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTalleKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldHombro.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldTalleKeyPressed

    private void jTextFieldEspaldaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEspaldaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldEspaldaKeyTyped

    private void jTextFieldEspaldaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEspaldaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldTalle.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldEspaldaKeyPressed

    private void jTextFieldCaderaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCaderaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldCaderaKeyTyped

    private void jTextFieldCaderaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCaderaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldEspalda.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldCaderaKeyPressed

    private void jTextFieldCinturaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCinturaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldCinturaKeyTyped

    private void jTextFieldCinturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCinturaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldCadera.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldCinturaKeyPressed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        llenarDatos();
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        llenarDatos();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
        jFormattedDni.setEnabled(true);
    }//GEN-LAST:event_jPanel1MouseClicked

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
        jFormattedTextFieldRu.setEnabled(false);
        jTextFieldDirec.setEnabled(false);
        jTextFieldSocial.setEnabled(false);
        jFormattedDni.setEnabled(true);
        jTextField_nombre.setEnabled(true);
        jTextField_apellido_m.setEnabled(true);
        jTextField_apellido_p.setEnabled(true);
        jTextField_direccion.setEnabled(true);
        jTextField_email.setEnabled(true);
        jFormattedTelefono.setEnabled(true);
        jFormattedDni.grabFocus();
        nombresTitulos();
            cargarDatos();
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);
        jFormattedDni.setEnabled(false);
        jTextField_nombre.setEnabled(false);
        jTextField_apellido_m.setEnabled(false);
        jTextField_apellido_p.setEnabled(false);
        jTextField_direccion.setEnabled(false);
        jTextField_email.setEnabled(false);
        jFormattedTelefono.setEnabled(false);
        jFormattedTextFieldRu.setEnabled(true);
        jTextFieldDirec.setEnabled(true);
        jTextFieldSocial.setEnabled(true);
        jFormattedTextFieldRu.grabFocus();
        nombresTitulosRuc();
        cargarDatosRuc();
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        // buscarDatos();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField_buscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_buscarKeyTyped
        // TODO add your handling code here:
        MostrarFacultades();
    }//GEN-LAST:event_jTextField_buscarKeyTyped

    private void jTextField_apellido_mKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_apellido_mKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);

        }
    }//GEN-LAST:event_jTextField_apellido_mKeyTyped

    private void jTextField_apellido_mKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_apellido_mKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_direccion.requestFocus();
        }
    }//GEN-LAST:event_jTextField_apellido_mKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmRegistroMedidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmRegistroMedidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmRegistroMedidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmRegistroMedidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmRegistroMedidas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    public static javax.swing.JFormattedTextField jFormattedDni;
    public static javax.swing.JFormattedTextField jFormattedTelefono;
    public static javax.swing.JFormattedTextField jFormattedTextFieldRu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    public static javax.swing.JRadioButton jRadioButton1;
    public static javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldBoca;
    private javax.swing.JTextField jTextFieldBusto;
    private javax.swing.JTextField jTextFieldCadera;
    private javax.swing.JTextField jTextFieldCasdera2;
    private javax.swing.JTextField jTextFieldCintura;
    private javax.swing.JTextField jTextFieldCintuta2;
    public static javax.swing.JTextField jTextFieldDirec;
    private javax.swing.JTextField jTextFieldEspalda;
    private javax.swing.JTextField jTextFieldHombro;
    private javax.swing.JTextField jTextFieldJareta;
    private javax.swing.JTextField jTextFieldLargo;
    private javax.swing.JTextField jTextFieldLsrgo2;
    private javax.swing.JTextField jTextFieldManga;
    private javax.swing.JTextField jTextFieldPecho1;
    private javax.swing.JTextField jTextFieldRodilla;
    private javax.swing.JTextField jTextFieldSeparacion;
    public static javax.swing.JTextField jTextFieldSocial;
    private javax.swing.JTextField jTextFieldTalle;
    public static javax.swing.JTextField jTextField_apellido_m;
    public static javax.swing.JTextField jTextField_apellido_p;
    private javax.swing.JTextField jTextField_buscar;
    public static javax.swing.JTextField jTextField_direccion;
    public static javax.swing.JTextField jTextField_email;
    public static javax.swing.JTextField jTextField_nombre;
    private javax.swing.JTextField jTextFieldmuslo;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPanel lblcab;
    private javax.swing.JPanel pnel_inf1;
    // End of variables declaration//GEN-END:variables
}
