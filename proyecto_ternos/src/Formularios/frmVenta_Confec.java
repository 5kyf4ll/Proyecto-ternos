/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import Clases.Conexion;
import Clases2.Controlador;
import static Formularios.frmPrueba.table_factu;
import static Formularios.frmPrueba.text_cod;
import static Formularios.frmPrueba.text_cod_pro;
import static Formularios.frmPrueba.text_precio;
import static Formularios.frmPrueba.text_producto;
import VentanasFormulario.frmRegistroMedidas;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author USUARIO
 */
public class frmVenta_Confec extends javax.swing.JFrame {
    DefaultTableModel model=new DefaultTableModel();
    DefaultTableModel model2=new DefaultTableModel();
    DefaultTableModel model3=new DefaultTableModel();
     Conexion cn=new Conexion();
     String cpro;
     String cventa;
     Controlador control=new Controlador();
    Controlador ctl=new Controlador();
     String fecha2;
    String fecha3;
    /**
     * Creates new form frmVenta_Confec
     */
    public frmVenta_Confec() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/venta4.png")).getImage());
        titulos();
        cargarDetalle();
        titulos2();
        titulos3();
        cargarDetalle2();
        pueb();
        pueb2();
        insertComprobante();
    }
    public void insertComprobante(){
        PreparedStatement ps=null;
        try {
          Connection con=cn.conectar();
          ps=con.prepareStatement("call p_comprobante()");
          ps.execute();
          cargarDetalle();
          //JOptionPane.showMessageDialog(null, "Se creo el comrobante");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar registro!!");
            System.out.println(e);
        }
    }
    
    
    public void titulos(){
        String[] tit={"N° COMPROBANTE"};
        model.setColumnIdentifiers(tit);
        table_factu.setModel(model);
    }
    public void  limpiarTabla(JTable tb, DefaultTableModel md){
        while(tb.getRowCount()>0){
            md.removeRow(0);
        }
    }
    public void cargarDetalle(){
        try {
            limpiarTabla(table_factu, model);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="SELECT MAX(idcomprobante) AS id FROM comprobante;";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadcolumnas=rsMd.getColumnCount();
            int [] anchos={130};
            for(int i=0;i<table_factu.getColumnCount();i++){
                table_factu.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            while(rs.next()){
                Object[] filas=new  Object[cantidadcolumnas];
                for(int i=0;i<cantidadcolumnas;i++){
                    filas[i]=rs.getObject(i+1);
                }
                model.addRow(filas);
               
            }
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
    }
    
    public void titulos3(){
        String[] tit={"N° VENTA CONFECCION"};
        model3.setColumnIdentifiers(tit);
        table_Nventa.setModel(model3);
    }
    public void cargarDetalle2(){
        try {
            limpiarTabla(table_Nventa, model3);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="SELECT MAX(idventa_confec+1) AS id FROM venta_confec;";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadcolumnas=rsMd.getColumnCount();
            int [] anchos={130};
            for(int i=0;i<table_Nventa.getColumnCount();i++){
                table_Nventa.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            while(rs.next()){
                Object[] filas=new  Object[cantidadcolumnas];
                for(int i=0;i<cantidadcolumnas;i++){
                    filas[i]=rs.getObject(i+1);
                }
                model3.addRow(filas);
               
            }
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
    }
    
    public void titulos2(){
        String[] tit={"CODIGO VENTA","MODELO TELA","COD COMPROBANTE","TELA GASTADA","CLIENTE MEDIDAS","CANTIDAD","COSTO UNITARIO","COSTO TOTAL"};
        model2.setColumnIdentifiers(tit);
        table_fac.setModel(model2);
    }
    public void cargarVenta(){
        try {
            limpiarTabla(table_fac, model2);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="select * from venta_confec where fk_idComprobante=?";
            ps=con.prepareStatement(sql);
            pueb();
            ps.setString(1, cpro);
//            ps.setString(1, jTextField2.getText());
            rs=ps.executeQuery();
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadcolumnas=rsMd.getColumnCount();
            int [] anchos={100,100,150,150,100,150,150,150};
            for(int i=0;i<table_fac.getColumnCount();i++){
                table_fac.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            while(rs.next()){
                Object[] filas=new  Object[cantidadcolumnas];
                for(int i=0;i<cantidadcolumnas;i++){
                    filas[i]=rs.getObject(i+1);
                }
                model2.addRow(filas);
            }
            
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
    }
    
    public void insertVenta(){
        int filas;
        int n = table_fac.getSelectedRow();
        int cant=0;
        int precio=0;
        double tot=0;
        PreparedStatement ps=null;
        try {
          Connection con=cn.conectar();
          pueb();
          pueb2();
          ps=con.prepareStatement("insert into  venta_confec(idventa_confec,fk_idmodelo_tela,fk_idComprobante,tela_gastada,fk_idCleinteM,"
                  + "cantidad,costo,canxcos) values (?,?,?,?,?,?,?,?)");
//          ps.setString(1, jTextField1.getText());
//          ps.setString(2,jTextField2.getText());
          cant=Integer.parseInt(text_cant1.getText());
          precio=Integer.parseInt(text_precio.getText());
          tot=cant*precio;
          System.out.println(tot);
          
          ps.setString(1, cventa);
          ps.setString(2, text_cod_mod.getText());
          ps.setString(3,cpro);
          ps.setString(4, text_telagastada.getText());
          ps.setString(5,text_Medidas.getText());
          ps.setString(6,text_cant1.getText());
          ps.setString(7,text_precio.getText());
          ps.setString(8,""+tot);
          ps.execute();
          //JOptionPane.showMessageDialog(null, "Venta guaradada!!");
           limpiar();
          cargarVenta();
         
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar registro!!");
            System.out.println(e);
        }
        filas = n;
    }
     public void limpiar(){
        text_cant1.setText("");
        text_precio.setText("");
        text_cod_mod.setText("");
        text_Modelo.setText("");
        text_Marca.setText("");
        text_stok.setText("");
        text_telagastada.getText();
    }
    
    public void eliminarventa(){
        PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
            int fila=table_fac.getSelectedRow();
            String codigo=table_fac.getValueAt(fila, 0).toString();
            
            ps=con.prepareStatement("delete from venta_confec where idventa_confec=?;");
            ps.setString(1, codigo);
            ps.execute();
            model2.removeRow(fila);
            //JOptionPane.showMessageDialog(null, "Venta elimanada con exito");
            limpiar();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en eliminacion!");
            System.out.println(e.toString());
        }
        cargarVenta();
    }
    
     public void reiniciar(){
        limpiarTabla(table_fac, model2);
        cargarVenta();
        cargarDetalle2();
        limpiar();
        text_cod.setText("");
        text_Medidas.setText("");
        text_dni.setText("");
        text_cliente.setText("");
        text_Correo.setText("");
        text_telf.setText("");
        text_Direccion.setText("");
        text_subtotal.setText("");
        text_iva.setText("");
        text_total.setText("");
        buttonGroup1.clearSelection();
        
       
    }  
    public void pueb(){
        for (int i = 0; i < table_factu.getRowCount(); i++) {
            cpro=""+table_factu.getValueAt(i, 0);
            System.out.println(cpro);
        }
        System.out.println(""+text_cod.getText());
    }
    public void pueb2(){  
        for (int i = 0; i < table_Nventa.getRowCount(); i++) {
            cventa=""+table_Nventa.getValueAt(i, 0);
            System.out.println(cventa);
        }

    }
    
    public void suma(){
        double suma = 0;
        double igv=0;
        double total=0;
        int tamFila = table_fac.getRowCount();
        for (int i = 0; i < tamFila; i++) {
            suma += Double.parseDouble(table_fac.getValueAt(i, 7).toString()); 
        }
        igv=suma*0.18;
        total=suma+igv;
        String cadena = Double.toString(suma);
        text_subtotal.setText(cadena);      
        text_iva.setText(""+igv);
        text_total.setText(""+total);
    }
    public void actualizarDatos() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            pueb();
            ps = con.prepareStatement("update Comprobante set fk_id_cliente=?,fk_Num_boleta=?,fk_Num_factura=?,adelanto=? where idComprobante=?;");

            ps.setString(1, text_cod.getText());
            ps.setString(2, "B000"+cpro);
            ps.setString(3, "F");
            ps.setString(4, text_adelanto.getText());
            ps.setString(5, cpro);
            ps.execute();
            
            control.VerificaConsulta("call p_adelanto('"+cpro+"','"+text_adelanto.getText()+"');");
            
            JOptionPane.showMessageDialog(null, "Se actualizo con éxito ");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar");
            System.out.println(e.toString());
        }
    }
     public void reportedBoletaConfec(){
        try {
            Conexion conn=new Conexion();
            Connection con=conn.conectar();
            JasperReport reporte=null;
            String path="src\\Reportes\\rRecibo.jasper";
            Map parametro=new HashMap();
            pueb();
            int bol=Integer.parseInt(cpro);
            parametro.put("rbol", bol);
            reporte=(JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint=JasperFillManager.fillReport(reporte,parametro,con);
            JasperViewer view=new JasperViewer(jprint,false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            
        } catch (Exception e) {
            Logger.getLogger(JDialogVer_VentaConfec.class.getName()).log(Level.SEVERE, null, e);
        }
    }
     public void actualizarDatos2() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            pueb();
            ps = con.prepareStatement("update Comprobante set fk_id_cliente=?,fk_Num_boleta=?,fk_Num_factura=?,adelanto=? where idComprobante=?;");
            ps.setString(1, text_cod.getText());
            ps.setString(2, "B");
            ps.setString(3, "F000"+cpro);
            ps.setString(4, text_adelanto.getText());
            ps.setString(5, cpro);
            ps.execute();
            control.VerificaConsulta("call p_adelanto('"+cpro+"','"+text_adelanto.getText()+"');");
            JOptionPane.showMessageDialog(null, "Se actualizo con éxito ");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar");
            System.out.println(e.toString());
        }
    }
    
    public void limpiarTabla(){
        int fila=table_fac.getRowCount();
        for(int i=fila-1;i>=0;i--){
            model2.removeRow(i);
        }
    }
    public void insertVerVenta(){
        PreparedStatement ps=null;
        try {
          Connection con=cn.conectar();
          pueb();
          ps=con.prepareStatement("call p_verconfec(?);");
          ps.setString(1, cpro);
          ps.execute();
//          JOptionPane.showMessageDialog(null, "Modelo guaradada!!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar registro!!");
            System.out.println(e);
        }
    }
    
    public void cliente(){
        String cant=control.DevolverRegistroDato(" select CHARACTER_LENGTH('"+text_dni.getText()+"');", 1);
        
        if("8".equals(cant)){
        if(control.VerificaConsulta("SELECT * from v_vCliConfec where DNI='"+text_dni.getText()+"'") ){
            text_cod.setText(control.DevolverRegistroDato("SELECT id_cliente from v_vCliConfec where DNI='"+text_dni.getText()+"';", 1));
            text_Medidas.setText(control.DevolverRegistroDato("SELECT idCleinteM from v_vCliConfec where DNI='"+text_dni.getText()+"';", 1));
            text_cliente.setText(control.DevolverRegistroDato("SELECT dato from v_vCliConfec where DNI='"+text_dni.getText()+"';", 1));
           text_Correo.setText(control.DevolverRegistroDato("SELECT email from v_vCliConfec where DNI='"+text_dni.getText()+"';", 1));
           text_telf.setText(control.DevolverRegistroDato("SELECT telefono from v_vCliConfec where DNI='"+text_dni.getText()+"';", 1));
           text_Direccion.setText(control.DevolverRegistroDato("SELECT direccion from v_vCliConfec where DNI='"+text_dni.getText()+"';", 1));
           jRadioButtonBoleta.setSelected(true);
           jRadioButtonFactura.setSelected(false);
        }
        
        else if("8".equals(cant)){
            frmRegistroMedidas frm=new frmRegistroMedidas();
            frm.setVisible(true);
            frmRegistroMedidas.jRadioButton1.setSelected(true);
            frmRegistroMedidas.jFormattedDni.setText(text_dni.getText());
            frmRegistroMedidas.jTabbedPane1.setSelectedIndex(0);
            jRadioButtonBoleta.setSelected(true);
            jRadioButtonFactura.setSelected(false);
        }
        
        }
        else if("11".equals(cant)){
        if(control.VerificaConsulta("SELECT * from v_vCliConfec where ruc='"+text_dni.getText()+"'") ){
            text_cod.setText(control.DevolverRegistroDato("SELECT id_cliente from v_vCliConfec where ruc='"+text_dni.getText()+"';", 1));
            text_Medidas.setText(control.DevolverRegistroDato("SELECT idCleinteM from v_vCliConfec where ruc='"+text_dni.getText()+"';", 1));
            text_cliente.setText(control.DevolverRegistroDato("SELECT razon from v_vCliConfec where ruc='"+text_dni.getText()+"';", 1));
           text_Direccion.setText(control.DevolverRegistroDato("SELECT direccion from v_vCliConfec where ruc='"+text_dni.getText()+"';", 1));
           jRadioButtonFactura.setSelected(true);
           jRadioButtonBoleta.setSelected(false);
        }
        
        else if("11".equals(cant)){
            frmRegistroMedidas frm=new frmRegistroMedidas();
            frm.setVisible(true);
            frmRegistroMedidas.jRadioButton2.setSelected(true);
            frmRegistroMedidas.jFormattedTextFieldRu.setText(text_dni.getText());
             frmRegistroMedidas.jTabbedPane1.setSelectedIndex(0);
            jRadioButtonFactura.setSelected(true);
            jRadioButtonBoleta.setSelected(false);
        }
        
        
        }
        else{
            JOptionPane.showMessageDialog(null,"Ingresar DNI O RUC", "Aviso", JOptionPane.WARNING_MESSAGE, null);
            jRadioButtonFactura.setSelected(false);
            jRadioButtonBoleta.setSelected(false);
        }
        
    }
    public void Clientes2(){
        String cant=control.DevolverRegistroDato(" select CHARACTER_LENGTH('"+text_dni.getText()+"');", 1);
        
        if("8".equals(cant)){
        
        }
        else if("11".equals(cant)){
            
        }
        else{
            buttonGroup1.clearSelection();
            text_cod.setText("");
            text_cliente.setText("");
            text_Correo.setText("");
            text_telf.setText("");
            text_Direccion.setText("");
            text_Medidas.setText("");
            
        }
    }
    public void bol(){
        if(jRadioButtonBoleta.isSelected()){
            double suma = 0;
        double igv=0;
        double total=0;
        int tamFila = table_fac.getRowCount();
        for (int i = 0; i < tamFila; i++) {
            suma += Double.parseDouble(table_fac.getValueAt(i, 7).toString());
        }
        igv=suma*0;
        total=suma+igv;
        String cadena = Double.toString(suma);
        text_subtotal.setText(cadena);
        text_iva.setText(""+igv);
        text_total.setText(""+total);
        }
        
    }
    public void fac(){
        if(jRadioButtonFactura.isSelected()){
        double suma = 0;
        double igv=0;
        double total=0;
        int tamFila = table_fac.getRowCount();
        for (int i = 0; i < tamFila; i++) {
            suma += Double.parseDouble(table_fac.getValueAt(i, 7).toString());
        }
        igv=suma*0.18;
        total=suma+igv;
        String cadena = Double.toString(suma);
        text_subtotal.setText(cadena);
        text_iva.setText(""+igv);
        text_total.setText(""+total);
    }
    }
     public void ver(){
        for (int i = 0; i < table_fac.getRowCount(); i++) {
            
                    System.out.print(table_fac.getValueAt(i, 0));
            
            System.out.println();
            PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
            
            String codigo=table_fac.getValueAt(i, 0).toString();
            
            ps=con.prepareStatement("delete from venta_confec where idventa_confec=?;");
            ps.setString(1, codigo);
            ps.execute();
            //model2.removeRow(i);
            //JOptionPane.showMessageDialog(null, "Registro elimanado con exito");
            limpiar();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en eliminacion!");
            System.out.println(e.toString());
        }

    }
               cargarVenta();
        cargarDetalle2(); 
        
        
    }
    
    public void reportedFichaBol(){
        try {
            Conexion conn=new Conexion();
            Connection con=conn.conectar();
            JasperReport reporte=null;
            String path="src\\Reportes\\rRecibo.jasper";
            Map parametro=new HashMap();
            pueb();   
            int bol=Integer.parseInt(cpro);
            parametro.put("rbol", bol);
            reporte=(JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint=JasperFillManager.fillReport(reporte,parametro,con);
            JasperViewer view=new JasperViewer(jprint,false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            
        } catch (Exception e) {
            Logger.getLogger(JDialogVer_VentaConfec.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public void reportedFichaFact(){
        try {
            Conexion conn=new Conexion();
            Connection con=conn.conectar();
            JasperReport reporte=null;
            String path="src\\Reportes\\rReciboF.jasper";
            Map parametro=new HashMap();
            pueb();    
            int bol=Integer.parseInt(cpro);
            parametro.put("rRuc", bol);
            reporte=(JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint=JasperFillManager.fillReport(reporte,parametro,con);
            JasperViewer view=new JasperViewer(jprint,false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            
        } catch (Exception e) {
            Logger.getLogger(JDialogVer_VentaConfec.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public void fechaNac(){
        SimpleDateFormat fff=new SimpleDateFormat("yyyy-MM-dd");
        Date fecha=jDateChooserFecha.getDate();
        fecha2=fff.format(fecha);

    }
     public void AsignarFecha(){
         fechaNac();
        for (int i = 0; i < table_fac.getRowCount(); i++) {
            
                    System.out.print(table_fac.getValueAt(i, 0));
            
            System.out.println();
            PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
            
            String codigo=table_fac.getValueAt(i, 0).toString();
            
            //ps=con.prepareStatement("delete from venta_confec where idventa_confec=?;");
            ps=con.prepareStatement("insert into esconfec(fechestimada,fk_idventa_confec) values(?,?)");
            ps.setString(1, fecha2);
            ps.setString(2, codigo);
            ps.execute();
            //model2.removeRow(i);
            //JOptionPane.showMessageDialog(null, "Registro elimanado con exito");
            limpiar();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en eliminacion!");
            System.out.println(e.toString());
        }

    }
               cargarVenta();
        cargarDetalle2(); 
        
        
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
        panel_all = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        pnel_inf = new javax.swing.JPanel();
        text_dni = new javax.swing.JTextField();
        lblProveedor = new javax.swing.JLabel();
        lblced = new javax.swing.JLabel();
        text_Correo = new javax.swing.JTextField();
        lbldir = new javax.swing.JLabel();
        text_cliente = new javax.swing.JTextField();
        text_Direccion = new javax.swing.JTextField();
        lblus = new javax.swing.JLabel();
        text_telf = new javax.swing.JTextField();
        btn_bus_cli = new javax.swing.JButton();
        lbltelf = new javax.swing.JLabel();
        lblProveedor1 = new javax.swing.JLabel();
        text_cod = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        text_Medidas = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        btn_clean = new javax.swing.JButton();
        pnl_inf1 = new javax.swing.JPanel();
        lblprod = new javax.swing.JLabel();
        text_Marca = new javax.swing.JTextField();
        lblcodprod = new javax.swing.JLabel();
        text_cod_mod = new javax.swing.JTextField();
        lblprecio1 = new javax.swing.JLabel();
        text_precio = new javax.swing.JTextField();
        lblstok1 = new javax.swing.JLabel();
        text_stok = new javax.swing.JTextField();
        lblcantidad1 = new javax.swing.JLabel();
        text_cant1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        text_Modelo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        text_telagastada = new javax.swing.JTextField();
        btn_bus_prod = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_fac = new javax.swing.JTable();
        pnel_inf1 = new javax.swing.JPanel();
        text_subtotal = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        text_iva = new javax.swing.JTextField();
        text_total = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        text_adelanto = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        btn_agregar = new javax.swing.JButton();
        pnl_cab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_factu = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_Nventa = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jRadioButtonBoleta = new javax.swing.JRadioButton();
        jRadioButtonFactura = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jDateChooserFecha = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("REGISTRO DE VENTA");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_all.setBackground(new java.awt.Color(255, 255, 255));
        panel_all.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panel_all.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 140, 120, -1));

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/quitar.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        panel_all.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 90, 60));

        pnel_inf.setBackground(new java.awt.Color(255, 255, 255));
        pnel_inf.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnel_inf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        text_dni.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_dni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_dniActionPerformed(evt);
            }
        });
        text_dni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_dniKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                text_dniKeyTyped(evt);
            }
        });
        pnel_inf.add(text_dni, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 140, -1));

        lblProveedor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblProveedor.setText("Código:");
        pnel_inf.add(lblProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        lblced.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblced.setText("Correo:");
        pnel_inf.add(lblced, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        text_Correo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_Correo.setEnabled(false);
        pnel_inf.add(text_Correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 160, -1));

        lbldir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbldir.setText("Cliente:");
        pnel_inf.add(lbldir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 20));

        text_cliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cliente.setEnabled(false);
        text_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_clienteActionPerformed(evt);
            }
        });
        pnel_inf.add(text_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 440, -1));

        text_Direccion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_Direccion.setEnabled(false);
        pnel_inf.add(text_Direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 520, -1));

        lblus.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblus.setText("Dirección:");
        pnel_inf.add(lblus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 20));

        text_telf.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_telf.setEnabled(false);
        pnel_inf.add(text_telf, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, 190, -1));

        btn_bus_cli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Search.png"))); // NOI18N
        btn_bus_cli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bus_cliActionPerformed(evt);
            }
        });
        pnel_inf.add(btn_bus_cli, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 90, 80));

        lbltelf.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbltelf.setText("Teléfono:");
        pnel_inf.add(lbltelf, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, -1, 20));

        lblProveedor1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblProveedor1.setText("DNI:");
        pnel_inf.add(lblProveedor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, 20));

        text_cod.setEnabled(false);
        text_cod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_codActionPerformed(evt);
            }
        });
        pnel_inf.add(text_cod, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 60, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Cod. Medidas:");
        pnel_inf.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, 20));

        text_Medidas.setEnabled(false);
        pnel_inf.add(text_Medidas, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 90, -1));

        panel_all.add(pnel_inf, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 630, 130));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadir.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        panel_all.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 90, 70));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setRollover(true);
        jToolBar1.setEnabled(false);

        btn_clean.setBackground(new java.awt.Color(51, 153, 255));
        btn_clean.setForeground(new java.awt.Color(255, 255, 255));
        btn_clean.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reiniciar3.png"))); // NOI18N
        btn_clean.setText("Reiniciar");
        btn_clean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cleanActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_clean);

        panel_all.add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 30, 630, 40));

        pnl_inf1.setBackground(new java.awt.Color(255, 255, 255));
        pnl_inf1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 102), 2));
        pnl_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblprod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblprod.setText("Marca:");
        pnl_inf1.add(lblprod, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, -1, 20));

        text_Marca.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_Marca.setEnabled(false);
        pnl_inf1.add(text_Marca, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 290, -1));

        lblcodprod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcodprod.setText("Código:");
        pnl_inf1.add(lblcodprod, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 20));

        text_cod_mod.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cod_mod.setEnabled(false);
        pnl_inf1.add(text_cod_mod, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 10, 90, -1));

        lblprecio1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblprecio1.setText("Precio:");
        pnl_inf1.add(lblprecio1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, -1, 20));

        text_precio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_precio.setEnabled(false);
        pnl_inf1.add(text_precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 60, -1));

        lblstok1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblstok1.setText("Stok:");
        pnl_inf1.add(lblstok1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 30, 20));

        text_stok.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_stok.setEnabled(false);
        pnl_inf1.add(text_stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 50, -1));

        lblcantidad1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcantidad1.setText("Cantidad:");
        pnl_inf1.add(lblcantidad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 60, 20));

        text_cant1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_cant1ActionPerformed(evt);
            }
        });
        text_cant1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_cant1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_cant1KeyReleased(evt);
            }
        });
        pnl_inf1.add(text_cant1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, 50, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Modelo:");
        pnl_inf1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 20));

        text_Modelo.setEnabled(false);
        pnl_inf1.add(text_Modelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 330, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Tela Gastada:");
        pnl_inf1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, -1, 20));

        text_telagastada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_telagastadaKeyPressed(evt);
            }
        });
        pnl_inf1.add(text_telagastada, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, 60, -1));

        panel_all.add(pnl_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 550, 100));

        btn_bus_prod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar_prod.png"))); // NOI18N
        btn_bus_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bus_prodActionPerformed(evt);
            }
        });
        panel_all.add(btn_bus_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 200, 80, 100));

        table_fac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(table_fac);

        panel_all.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 300, 540, 130));

        pnel_inf1.setBackground(new java.awt.Color(204, 204, 204));

        text_subtotal.setText("0.0");
        text_subtotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_subtotal.setEnabled(false);

        jLabel28.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Subtotal:");

        jLabel29.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("IGV:");

        text_iva.setText("0.0");
        text_iva.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_iva.setEnabled(false);

        text_total.setBackground(new java.awt.Color(0, 102, 102));
        text_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        text_total.setForeground(new java.awt.Color(255, 51, 51));
        text_total.setText("0.0");
        text_total.setDisabledTextColor(new java.awt.Color(0, 51, 153));
        text_total.setEnabled(false);

        jLabel31.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Total:");

        text_adelanto.setForeground(new java.awt.Color(255, 0, 0));

        jLabel32.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Adelanto");

        javax.swing.GroupLayout pnel_inf1Layout = new javax.swing.GroupLayout(pnel_inf1);
        pnel_inf1.setLayout(pnel_inf1Layout);
        pnel_inf1Layout.setHorizontalGroup(
            pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnel_inf1Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnel_inf1Layout.createSequentialGroup()
                        .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(text_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(text_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnel_inf1Layout.createSequentialGroup()
                        .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(text_adelanto)
                            .addComponent(text_total, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnel_inf1Layout.setVerticalGroup(
            pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnel_inf1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(text_subtotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(text_iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_adelanto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_all.add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 430, 190, 160));

        btn_agregar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar01.png"))); // NOI18N
        btn_agregar.setText(" Guardar Ficha");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        panel_all.add(btn_agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 500, 160, 30));

        pnl_cab.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Ventas de confección de ternos");

        javax.swing.GroupLayout pnl_cabLayout = new javax.swing.GroupLayout(pnl_cab);
        pnl_cab.setLayout(pnl_cabLayout);
        pnl_cabLayout.setHorizontalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(732, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        panel_all.add(pnl_cab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        table_factu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(table_factu);

        panel_all.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 80, 110, 50));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Codigo Venta Conf:");
        panel_all.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 140, -1, 20));

        table_Nventa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(table_Nventa);

        panel_all.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 170, 110, 50));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Tipo de comprobante:");
        panel_all.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 270, -1, -1));

        buttonGroup1.add(jRadioButtonBoleta);
        jRadioButtonBoleta.setText("Boleta");
        jRadioButtonBoleta.setEnabled(false);
        jRadioButtonBoleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonBoletaActionPerformed(evt);
            }
        });
        panel_all.add(jRadioButtonBoleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 300, -1, -1));

        buttonGroup1.add(jRadioButtonFactura);
        jRadioButtonFactura.setText("Factura");
        jRadioButtonFactura.setEnabled(false);
        jRadioButtonFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonFacturaActionPerformed(evt);
            }
        });
        panel_all.add(jRadioButtonFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 330, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icon_factura.png"))); // NOI18N
        panel_all.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 360, 120, 130));

        jDateChooserFecha.setDateFormatString("MM/dd/yyyy");
        panel_all.add(jDateChooserFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 600, 190, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("Fecha entrega:");
        panel_all.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 600, -1, 30));

        getContentPane().add(panel_all, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 660));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        eliminarventa();
        cargarVenta();
        cargarDetalle2();
        bol();
        fac();
        //suma();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void text_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_clienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_clienteActionPerformed

    private void btn_bus_cliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bus_cliActionPerformed
        // TODO add your handling code here:
        new JDialogVer_cliMedidas(this, false).setVisible(true);
    }//GEN-LAST:event_btn_bus_cliActionPerformed

    private void text_codActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_codActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_codActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(text_cant1.getText().equals("") || text_telagastada.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Complete el cuadro de Cantidad o Tela Gastada", "Antención", JOptionPane.WARNING_MESSAGE, null);
        }
        else{
            insertVenta();
            text_telagastada.setText("");
            text_stok.setText("");
            //cantporPre();
            cargarVenta();
            cargarDetalle2();
            bol();
            fac();
        }
        
        //suma();

    }//GEN-LAST:event_jButton5ActionPerformed

    private void btn_cleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cleanActionPerformed
        // TODO add your handling code here:
        ver();
        reiniciar();
    }//GEN-LAST:event_btn_cleanActionPerformed

    private void text_cant1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_cant1KeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_text_cant1KeyReleased

    private void btn_bus_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bus_prodActionPerformed
        // TODO add your handling code here:
        new JDialogVer_Tela(this, false).setVisible(true);
    }//GEN-LAST:event_btn_bus_prodActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        // TODO add your handling code here:
        double total=0;
        double ade=0;
        double por=0;
        total=Double.parseDouble(text_total.getText());
        ade=Double.parseDouble(text_adelanto.getText());
        por=total*0.5;
        
        if(ade>total){
            JOptionPane.showMessageDialog(null, "El adelanto debe ser menor al total", "Cuidado", JOptionPane.WARNING_MESSAGE, null);
        }
        else if(ade<por){
            JOptionPane.showMessageDialog(null, "El adelando debe ser mayor a: "+por, "Atención", JOptionPane.WARNING_MESSAGE, null);
        }
        else if(ade>por){
            limpiar();

            text_dni.setText("");
            text_cliente.setText("");
            text_Correo.setText("");
            text_telf.setText("");
            text_Direccion.setText("");
            text_subtotal.setText("");
            text_iva.setText("");
            text_total.setText("");
            text_stok.setText("");
            //text_adelanto.setText("");
            //text_Medidas.setText("");
            //jDateChooserFecha.setDate(null);
            if(jRadioButtonBoleta.isSelected()){
                actualizarDatos();
                AsignarFecha();

            }else if(jRadioButtonFactura.isSelected()){
                actualizarDatos2();
                AsignarFecha();

            }
            limpiarTabla();
            text_cod.setText("");
            insertVerVenta();
            if(jRadioButtonBoleta.isSelected()){

                reportedFichaBol();
            }else if(jRadioButtonFactura.isSelected()){

                reportedFichaFact();
            }
        }
        else if(ade<0){
            JOptionPane.showMessageDialog(null, "Adelanto invalido", "CUIDADO", JOptionPane.WARNING_MESSAGE, null);
        }
        
//        new JDialogVer_VentaConfec(this, false).setVisible(true);
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void jRadioButtonBoletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonBoletaActionPerformed
        // TODO add your handling code here:
        double suma = 0;
        double igv=0;
        double total=0;
        int tamFila = table_fac.getRowCount();
        for (int i = 0; i < tamFila; i++) {
            suma += Double.parseDouble(table_fac.getValueAt(i, 7).toString());
        }
        igv=suma*0;
        total=suma+igv;
        String cadena = Double.toString(suma);
        text_subtotal.setText(cadena);
        text_iva.setText(""+igv);
        text_total.setText(""+total);
    }//GEN-LAST:event_jRadioButtonBoletaActionPerformed

    private void jRadioButtonFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonFacturaActionPerformed
        // TODO add your handling code here:
        double suma = 0;
        double igv=0;
        double total=0;
        int tamFila = table_fac.getRowCount();
        for (int i = 0; i < tamFila; i++) {
            suma += Double.parseDouble(table_fac.getValueAt(i, 7).toString());
        }
        igv=suma*0.18;
        total=suma+igv;
        String cadena = Double.toString(suma);
        text_subtotal.setText(cadena);
        text_iva.setText(""+igv);
        text_total.setText(""+total);
    }//GEN-LAST:event_jRadioButtonFacturaActionPerformed

    private void text_cant1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_cant1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_cant1ActionPerformed

    private void text_dniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_dniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_dniActionPerformed

    private void text_dniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_dniKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            cliente();
        }
    }//GEN-LAST:event_text_dniKeyPressed

    private void text_dniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_dniKeyTyped
        // TODO add your handling code here:
        Clientes2();
    }//GEN-LAST:event_text_dniKeyTyped

    private void text_cant1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_cant1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            if(text_telagastada.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Complete el cuadro de Tela Gastada", "Antención", JOptionPane.WARNING_MESSAGE, null);
        }
        else{
            insertVenta();
            text_telagastada.setText("");
            text_stok.setText("");
            //cantporPre();
            cargarVenta();
            cargarDetalle2();
            bol();
            fac();
        }
        }
    }//GEN-LAST:event_text_cant1KeyPressed

    private void text_telagastadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_telagastadaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            text_cant1.requestFocus();
        }
    }//GEN-LAST:event_text_telagastadaKeyPressed

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
            java.util.logging.Logger.getLogger(frmVenta_Confec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmVenta_Confec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmVenta_Confec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmVenta_Confec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmVenta_Confec().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_bus_cli;
    private javax.swing.JButton btn_bus_prod;
    private javax.swing.JButton btn_clean;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton9;
    private com.toedter.calendar.JDateChooser jDateChooserFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public static javax.swing.JRadioButton jRadioButtonBoleta;
    public static javax.swing.JRadioButton jRadioButtonFactura;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblProveedor;
    private javax.swing.JLabel lblProveedor1;
    private javax.swing.JLabel lblcantidad1;
    private javax.swing.JLabel lblced;
    private javax.swing.JLabel lblcodprod;
    private javax.swing.JLabel lbldir;
    private javax.swing.JLabel lblprecio1;
    private javax.swing.JLabel lblprod;
    private javax.swing.JLabel lblstok1;
    private javax.swing.JLabel lbltelf;
    private javax.swing.JLabel lblus;
    private javax.swing.JPanel panel_all;
    private javax.swing.JPanel pnel_inf;
    private javax.swing.JPanel pnel_inf1;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JPanel pnl_inf1;
    private javax.swing.JTable table_Nventa;
    private javax.swing.JTable table_fac;
    public static javax.swing.JTable table_factu;
    public static javax.swing.JTextField text_Correo;
    public static javax.swing.JTextField text_Direccion;
    public static javax.swing.JTextField text_Marca;
    public static javax.swing.JTextField text_Medidas;
    public static javax.swing.JTextField text_Modelo;
    private javax.swing.JTextField text_adelanto;
    private javax.swing.JTextField text_cant1;
    public static javax.swing.JTextField text_cliente;
    public static javax.swing.JTextField text_cod;
    public static javax.swing.JTextField text_cod_mod;
    public static javax.swing.JTextField text_dni;
    private javax.swing.JTextField text_iva;
    public static javax.swing.JTextField text_precio;
    public static javax.swing.JTextField text_stok;
    private javax.swing.JTextField text_subtotal;
    private javax.swing.JTextField text_telagastada;
    public static javax.swing.JTextField text_telf;
    private javax.swing.JTextField text_total;
    // End of variables declaration//GEN-END:variables
}
