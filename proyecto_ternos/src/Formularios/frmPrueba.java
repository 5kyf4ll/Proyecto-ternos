/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import Clases.Conexion;
import Clases2.Controlador;
import VentanasFormulario.frmClienteReg;
import VentanasFormulario.frmClienteRegRuc;
import VentanasFormulario.frmRegistroCliente;
import static VentanasFormulario.frmRegistroCliente.jFormattedDni;
import static VentanasFormulario.frmRegistroCliente.jFormattedTelefono;
import static VentanasFormulario.frmRegistroCliente.jTextField_apellido_m;
import static VentanasFormulario.frmRegistroCliente.jTextField_apellido_p;
import static VentanasFormulario.frmRegistroCliente.jTextField_direccion;
import static VentanasFormulario.frmRegistroCliente.jTextField_email;
import static VentanasFormulario.frmRegistroCliente.jTextField_nombre;
import formularios.frmComprobante;
import static formularios.frmComprobante.jTextField_fk_id_cliente;
import static formularios.frmComprobante.jTextField_id_comprobante;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.RadioButton;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
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
public class frmPrueba extends javax.swing.JFrame {
    DefaultTableModel model=new DefaultTableModel();
    DefaultTableModel model2=new DefaultTableModel();
    DefaultTableModel model3=new DefaultTableModel();
     Conexion cn=new Conexion();
     String cpro;
     String cventa;
     Controlador control=new Controlador();
    Controlador ctl=new Controlador();
    /**
     * Creates new form frmPrueba
     */
    public frmPrueba() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/venta3.png")).getImage());
        titulos();
        cargarDetalle();
        titulos2();
        titulos3();
        cargarDetalle2();
        pueb();
        pueb2();
        insertComprobante();
        limpiar();
        text_cod.setText("");
        text_dni.setText("");
        text_cliente.setText("");
        text_Correo.setText("");
        text_telf.setText("");
        text_Direccion.setText("");
        text_subtotal.setText("");
        text_iva.setText("");
        text_total.setText("");
        buttonGroup1.clearSelection();
        limpiarTabla();
        prueb();
        // cargarVenta();
        
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
        String[] tit={"N° VENTA"};
        model3.setColumnIdentifiers(tit);
        table_Nventa.setModel(model3);
    }
    public void cargarDetalle2(){
        try {
            limpiarTabla(table_Nventa, model3);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="SELECT MAX(id_venta+1) AS id FROM venta_prenda;";
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
        String[] tit={"CODIGO VENTA","COD COMPROBANTE","CODIGO ARTICULO","CANTIDAD","COSTO UNITARIO","COSTO TOTAL"};
        model2.setColumnIdentifiers(tit);
        table_fac.setModel(model2);
    }
    public void cargarVenta(){
        try {
            limpiarTabla(table_fac, model2);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="select * from venta_prenda where fk_idComprobante=?";
            //String sql="SELECT v.id_venta,v.fk_idComprobante,v.fk_idarticulo,t.tipo_prenda,v.Cantidad,v.costo,v.caxcos FROM venta_prenda v INNER JOIN articulo a on v.fk_idarticulo=a.idarticulo INNER JOIN "
                   // + "tipo_prenda t on a.fk_idtipo_prenda=t.idtipo_prenda WHERE fk_idComprobante=?;";
            ps=con.prepareStatement(sql);
            pueb();
            ps.setString(1, cpro);
//            ps.setString(1, jTextField2.getText());
            rs=ps.executeQuery();
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadcolumnas=rsMd.getColumnCount();
            int [] anchos={100,100,150,150,100,150};
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
          ps=con.prepareStatement("insert into venta_prenda(id_venta,fk_idComprobante,fk_idarticulo,Cantidad,costo,caxcos) values (?,?,?,?,?,?)");
//          ps.setString(1, jTextField1.getText());
//          ps.setString(2,jTextField2.getText());
          cant=Integer.parseInt(text_cant1.getText());
          precio=Integer.parseInt(text_precio.getText());
          tot=cant*precio;
          System.out.println(tot);
          
          ps.setString(1, cventa);
          ps.setString(2,cpro);
          ps.setString(3,text_cod_pro.getText());
          ps.setString(4,text_cant1.getText());
          ps.setString(5,text_precio.getText());
          ps.setString(6,""+tot);
          ps.execute();
          //JOptionPane.showMessageDialog(null, "Venta prenda guaradada!!");
           limpiar();
          cargarVenta();
         
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar registro!!");
            System.out.println(e);
        }
        filas = n;
    }
    
    public void insertventa2(){
        int filas;
        int n = table_fac.getSelectedRow();
        pueb();
        pueb2();
        int cant2=0;
        int cant=0;
        double precio=0;
        double tot=0;
        int suma=0;

        PreparedStatement ps=null;
        if(control.VerificaConsulta("SELECT id_venta FROM venta_prenda   WHERE  fk_idarticulo="+text_cod_pro.getText()+" and fk_idComprobante="+cpro+" ;")){
            String id=control.DevolverRegistroDato("SELECT id_venta FROM venta_prenda   WHERE  fk_idarticulo="+text_cod_pro.getText()+" and fk_idComprobante="+cpro+" ;", 1);
            cant2=Integer.parseInt(control.DevolverRegistroDato("SELECT cantidad FROM venta_prenda   WHERE  fk_idarticulo="+text_cod_pro.getText()+" and fk_idComprobante="+cpro+" ;", 1));
            precio=Double.parseDouble(control.DevolverRegistroDato("SELECT costo FROM venta_prenda   WHERE  fk_idarticulo="+text_cod_pro.getText()+" and fk_idComprobante="+cpro+" ;", 1));
            cant=Integer.parseInt(text_cant1.getText());
            suma=cant+cant2;
            tot=suma*precio;
            control.ActualizarRegistro("update venta_prenda set Cantidad='"+suma+"', caxcos='"+tot+"' where id_venta='"+id+"' and fk_idComprobante='"+cpro+"'");
            limpiar();
            cargarVenta();
        }
        else{
                try {
              Connection con=cn.conectar();
              pueb();
              pueb2();
              ps=con.prepareStatement("insert into venta_prenda(id_venta,fk_idComprobante,fk_idarticulo,Cantidad,costo,caxcos) values (?,?,?,?,?,?)");
    //          ps.setString(1, jTextField1.getText());
    //          ps.setString(2,jTextField2.getText());
              cant=Integer.parseInt(text_cant1.getText());
              precio=Integer.parseInt(text_precio.getText());
              tot=cant*precio;
              System.out.println(tot);

              ps.setString(1, cventa);
              ps.setString(2,cpro);
              ps.setString(3,text_cod_pro.getText());
              ps.setString(4,text_cant1.getText());
              ps.setString(5,text_precio.getText());
              ps.setString(6,""+tot);
              ps.execute();
              //JOptionPane.showMessageDialog(null, "Venta prenda guaradada!!");
               limpiar();
              cargarVenta();

            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar registro!!");
                System.out.println(e);
            }
        }
        filas = n;
}
    
     public void limpiar(){
        text_cant1.setText("");
        text_precio.setText("");
        text_cod_pro.setText("");
        text_producto.setText("");
        text_precio.setText("");
      
        
    }
    
    public void eliminarventa(){
        PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
            int fila=table_fac.getSelectedRow();
            String codigo=table_fac.getValueAt(fila, 0).toString();
            
            ps=con.prepareStatement("delete from venta_prenda where id_venta=?;");
            ps.setString(1, codigo);
            ps.execute();
            model2.removeRow(fila);
            //JOptionPane.showMessageDialog(null, "Registro elimanado con exito");
            limpiar();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en eliminacion!");
            System.out.println(e.toString());
        }
        cargarVenta();
    }
    public void ver(){
        for (int i = 0; i < table_fac.getRowCount(); i++) {
            
                    System.out.print(table_fac.getValueAt(i, 0));
            
            System.out.println();
            PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
            
            String codigo=table_fac.getValueAt(i, 0).toString();
            
            ps=con.prepareStatement("delete from venta_prenda where id_venta=?;");
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
    
     public void reiniciar(){
        limpiarTabla(table_fac, model2);
        //cargarVenta();
        cargarDetalle2();
        limpiar();
        text_cod.setText("");
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
            suma += Double.parseDouble(table_fac.getValueAt(i, 5).toString()); 
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
            ps = con.prepareStatement("update Comprobante set fk_id_cliente=?,fk_Num_boleta=?,fk_Num_factura=? where idComprobante=?;");

            ps.setString(1, text_cod.getText());
            ps.setString(2, "B000"+cpro);
            ps.setString(3, "F");
            ps.setString(4, cpro);
            ps.execute();
            //JOptionPane.showMessageDialog(null, "Se actualizo con éxito ");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar");
            System.out.println(e.toString());
        }
    }
     public void actualizarDatos2() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            pueb();
            ps = con.prepareStatement("update Comprobante set fk_id_cliente=?,fk_Num_boleta=?,fk_Num_factura=? where idComprobante=?;");
            ps.setString(1, text_cod.getText());
            ps.setString(2, "B");
            ps.setString(3, "F000"+cpro);
            ps.setString(4, cpro);
            ps.execute();
            //JOptionPane.showMessageDialog(null, "Se actualizo con éxito ");
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
          ps=con.prepareStatement("call p_verventa(?);");
          ps.setString(1, cpro);
          ps.execute();
//          JOptionPane.showMessageDialog(null, "Modelo guaradada!!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar registro!!");
            System.out.println(e);
        }
    }
    public void prueb(){
        String cant=control.DevolverRegistroDato(" select CHARACTER_LENGTH('"+text_dni.getText()+"');", 1);
        
        if("11".equals(cant)){
            System.out.println(cant);
        }
        
    }
    public void Clientes(){
        String cant=control.DevolverRegistroDato(" select CHARACTER_LENGTH('"+text_dni.getText()+"');", 1);
        
        if("8".equals(cant)){
        if(control.VerificaConsulta("SELECT * from v_Cli where DNI='"+text_dni.getText()+"'") ){
            text_cod.setText(control.DevolverRegistroDato("SELECT id_cliente from v_cli where DNI='"+text_dni.getText()+"';", 1));
            text_cliente.setText(control.DevolverRegistroDato("SELECT dato from v_cli where DNI='"+text_dni.getText()+"';", 1));
           text_Correo.setText(control.DevolverRegistroDato("SELECT email from v_cli where DNI='"+text_dni.getText()+"';", 1));
           text_telf.setText(control.DevolverRegistroDato("SELECT telefono from v_cli where DNI='"+text_dni.getText()+"';", 1));
           text_Direccion.setText(control.DevolverRegistroDato("SELECT direccion from v_cli where DNI='"+text_dni.getText()+"';", 1));
           jRadioButtonBoleta.setSelected(true);
           jRadioButtonFactura.setSelected(false);
           bol();
        }
        
        else if("8".equals(cant)){
//            frmRegistroCliente frm=new frmRegistroCliente();
//            frm.setVisible(true);
//            frmRegistroCliente.jRadioButton1.setSelected(true);
//            frmRegistroCliente.jFormattedDni.setText(text_dni.getText());
//            frmRegistroCliente.jPanel2.setVisible(true);
            frmClienteReg frm=new frmClienteReg();
            frm.setVisible(true);
            frmClienteReg.jFormattedDNI.setText(text_dni.getText());
            jRadioButtonBoleta.setSelected(true);
            jRadioButtonFactura.setSelected(false);
            bol();
        }
        
        }
        else if("11".equals(cant)){
        if(control.VerificaConsulta("SELECT * from v_Cli where ruc='"+text_dni.getText()+"'") ){
            text_cod.setText(control.DevolverRegistroDato("SELECT id_cliente from v_cli where ruc='"+text_dni.getText()+"';", 1));
            text_cliente.setText(control.DevolverRegistroDato("SELECT ruc from v_cli where ruc='"+text_dni.getText()+"';", 1));
           text_Direccion.setText(control.DevolverRegistroDato("SELECT direccion from v_cli where ruc='"+text_dni.getText()+"';", 1));
           jRadioButtonFactura.setSelected(true);
           jRadioButtonBoleta.setSelected(false);
            fac();
        }
        
        else if("11".equals(cant)){
//            frmRegistroCliente frm=new frmRegistroCliente();
//            frm.setVisible(true);
//            frmRegistroCliente.jRadioButton2.setSelected(true);
//            frmRegistroCliente.jFormattedTextFieldRuc.setText(text_dni.getText());
//            frmRegistroCliente.jPanel3.setVisible(true);
            frmClienteRegRuc frm=new frmClienteRegRuc();
            frm.setVisible(true);
            frmClienteRegRuc.jFormattedRUC1.setText(text_dni.getText());
            jRadioButtonFactura.setSelected(true);
            jRadioButtonBoleta.setSelected(false);
            fac();
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
            
        }
    }
    public void bol(){
        if(jRadioButtonBoleta.isSelected()){
            double suma = 0;
        double igv=0;
        double total=0;
        int tamFila = table_fac.getRowCount();
        for (int i = 0; i < tamFila; i++) {
            suma += Double.parseDouble(table_fac.getValueAt(i, 5).toString()); 
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
            suma += Double.parseDouble(table_fac.getValueAt(i, 5).toString()); 
        }
        igv=suma*0.18;
        total=suma+igv;
        String cadena = Double.toString(suma);
        text_subtotal.setText(cadena);      
        text_iva.setText(""+igv);
        text_total.setText(""+total);
        }
        
    }
    public void reportedBoleta(){
        int bol=Integer.parseInt(""+frmPrueba.table_factu.getValueAt(0, 0));

        try {
            Conexion conn=new Conexion();
            Connection con=conn.conectar();
            JasperReport reporte=null;
            String path="src\\Reportes\\rBoleta.jasper";
            Map parametro=new HashMap();
            pueb();         
            parametro.put("p_bol", bol);
            reporte=(JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint=JasperFillManager.fillReport(reporte,parametro,con);
            JasperViewer view=new JasperViewer(jprint,false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            
        } catch (Exception e) {
            Logger.getLogger(JDialogVer_Venta.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public void reportedFactura(){
        int bol=Integer.parseInt(""+frmPrueba.table_factu.getValueAt(0, 0));
        try {
            Conexion conn=new Conexion();
            Connection con=conn.conectar();
            JasperReport reporte=null;
            String path="src\\Reportes\\rFactura.jasper";
            Map parametro=new HashMap();
            pueb();         
            parametro.put("p_fact", bol);
            reporte=(JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint=JasperFillManager.fillReport(reporte,parametro,con);
            JasperViewer view=new JasperViewer(jprint,false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            
        } catch (Exception e) {
            Logger.getLogger(JDialogVer_Venta.class.getName()).log(Level.SEVERE, null, e);
        }
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
        jButton5 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        btn_clean = new javax.swing.JButton();
        pnl_inf1 = new javax.swing.JPanel();
        lblprod = new javax.swing.JLabel();
        text_producto = new javax.swing.JTextField();
        lblcodprod = new javax.swing.JLabel();
        text_cod_pro = new javax.swing.JTextField();
        lblprecio1 = new javax.swing.JLabel();
        text_precio = new javax.swing.JTextField();
        lblstok1 = new javax.swing.JLabel();
        text_stok = new javax.swing.JTextField();
        lblcantidad1 = new javax.swing.JLabel();
        text_cant1 = new javax.swing.JTextField();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("REGISTRO DE VENTAS");
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
        panel_all.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 90, 60));

        pnel_inf.setBackground(new java.awt.Color(255, 255, 255));
        pnel_inf.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnel_inf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        text_dni.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_dni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_dniKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                text_dniKeyTyped(evt);
            }
        });
        pnel_inf.add(text_dni, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 300, -1));

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
        lbldir.setText("Cliente/RS:");
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
        lblProveedor1.setText("DNI / RUC:");
        pnel_inf.add(lblProveedor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, -1, 20));

        text_cod.setEnabled(false);
        text_cod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_codActionPerformed(evt);
            }
        });
        pnel_inf.add(text_cod, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 70, -1));

        panel_all.add(pnel_inf, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 630, 130));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadir.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        panel_all.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 90, 70));

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
        lblprod.setText("Producto:");
        pnl_inf1.add(lblprod, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, 20));

        text_producto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_producto.setEnabled(false);
        pnl_inf1.add(text_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 320, -1));

        lblcodprod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcodprod.setText("Código:");
        pnl_inf1.add(lblcodprod, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 20));

        text_cod_pro.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cod_pro.setEnabled(false);
        pnl_inf1.add(text_cod_pro, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 10, 50, -1));

        lblprecio1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblprecio1.setText("Precio:");
        pnl_inf1.add(lblprecio1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 20));

        text_precio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_precio.setEnabled(false);
        pnl_inf1.add(text_precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 50, -1));

        lblstok1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblstok1.setText("Stok:");
        pnl_inf1.add(lblstok1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 30, 20));

        text_stok.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_stok.setEnabled(false);
        pnl_inf1.add(text_stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 70, -1));

        lblcantidad1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcantidad1.setText("Cantidad:");
        pnl_inf1.add(lblcantidad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 60, 20));

        text_cant1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_cant1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_cant1KeyReleased(evt);
            }
        });
        pnl_inf1.add(text_cant1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 50, -1));

        panel_all.add(pnl_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 540, 80));

        btn_bus_prod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar_prod.png"))); // NOI18N
        btn_bus_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bus_prodActionPerformed(evt);
            }
        });
        panel_all.add(btn_bus_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 200, 90, 80));

        table_fac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table_fac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_facMouseClicked(evt);
            }
        });
        table_fac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                table_facKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                table_facKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(table_fac);

        panel_all.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, 540, 130));

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
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(text_total, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_all.add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 410, 190, 110));

        btn_agregar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar01.png"))); // NOI18N
        btn_agregar.setText(" Guardar");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        panel_all.add(btn_agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 430, 120, 30));

        pnl_cab.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Ventas");

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
        jLabel2.setText("Codigo Venta:");
        panel_all.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 140, -1, 20));

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

        getContentPane().add(panel_all, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 520));

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
       // new JDialogVer_cli(this, false).setVisible(true);
    }//GEN-LAST:event_btn_bus_cliActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(text_cant1.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Complete el cuadro de Cantidad", "Antención", JOptionPane.WARNING_MESSAGE, null);
        }
        else{
            insertventa2();
            text_stok.setText("");
            //cantporPre();
            cargarVenta();
            cargarDetalle2();
        //suma();
             bol();
        fac();
        }
        
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btn_cleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cleanActionPerformed
        // TODO add your handling code here:
        ver();
        reiniciar();
      
    }//GEN-LAST:event_btn_cleanActionPerformed

    private void btn_bus_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bus_prodActionPerformed
        // TODO add your handling code here:
        new JDialogVerProductos(this, false).setVisible(true);
    }//GEN-LAST:event_btn_bus_prodActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        // TODO add your handling code here:
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
        if(jRadioButtonBoleta.isSelected()){
            actualizarDatos();
        }else if(jRadioButtonFactura.isSelected()){
            actualizarDatos2();
        }
        limpiarTabla();
        text_cod.setText("");
        insertVerVenta();
        if(jRadioButtonBoleta.isSelected()){
            reportedBoleta();
        }
        else if(jRadioButtonFactura.isSelected()){
            reportedFactura();
        }
//         new JDialogVer_Venta(this, false).setVisible(true);

    }//GEN-LAST:event_btn_agregarActionPerformed

    private void text_codActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_codActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_codActionPerformed

    private void text_cant1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_cant1KeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_text_cant1KeyReleased

    private void jRadioButtonBoletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonBoletaActionPerformed
        // TODO add your handling code here:
        double suma = 0;
        double igv=0;
        double total=0;
        int tamFila = table_fac.getRowCount();
        for (int i = 0; i < tamFila; i++) {
            suma += Double.parseDouble(table_fac.getValueAt(i, 5).toString()); 
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
            suma += Double.parseDouble(table_fac.getValueAt(i, 5).toString()); 
        }
        igv=suma*0.18;
        total=suma+igv;
        String cadena = Double.toString(suma);
        text_subtotal.setText(cadena);      
        text_iva.setText(""+igv);
        text_total.setText(""+total);
    }//GEN-LAST:event_jRadioButtonFacturaActionPerformed

    private void text_dniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_dniKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            Clientes();
            prueb();
        }
    }//GEN-LAST:event_text_dniKeyPressed

    private void text_dniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_dniKeyTyped
        // TODO add your handling code here:
        Clientes2();
    }//GEN-LAST:event_text_dniKeyTyped

    private void text_cant1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_cant1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            insertventa2();
            text_stok.setText("");
            //cantporPre();
            cargarVenta();
            cargarDetalle2();
             bol();
            fac();
        }
    }//GEN-LAST:event_text_cant1KeyPressed

    private void table_facKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_facKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_table_facKeyPressed

    private void table_facMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_facMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_table_facMouseClicked

    private void table_facKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_facKeyReleased
        // TODO add your handling code here:
//        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
//                    
//            eliminarventa();
//        cargarVenta();
//        cargarDetalle2();
//    }
    }//GEN-LAST:event_table_facKeyReleased

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
            java.util.logging.Logger.getLogger(frmPrueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPrueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPrueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPrueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPrueba().setVisible(true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JTextField text_cant1;
    public static javax.swing.JTextField text_cliente;
    public static javax.swing.JTextField text_cod;
    public static javax.swing.JTextField text_cod_pro;
    public static javax.swing.JTextField text_dni;
    private javax.swing.JTextField text_iva;
    public static javax.swing.JTextField text_precio;
    public static javax.swing.JTextField text_producto;
    public static javax.swing.JTextField text_stok;
    private javax.swing.JTextField text_subtotal;
    public static javax.swing.JTextField text_telf;
    private javax.swing.JTextField text_total;
    // End of variables declaration//GEN-END:variables
}
