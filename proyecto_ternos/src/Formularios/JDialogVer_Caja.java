/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Formularios;

import Clases.Conexion;
import Clases.Funciones;
import Clases2.Controlador;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class JDialogVer_Caja extends javax.swing.JDialog {
    DefaultTableModel tabla = new DefaultTableModel();
    Conexion cn = new Conexion();
    String cpro;

    /**
     * Creates new form JDialogVer_Caja
     */
    String fecha2;
    String fecha3;
    Funciones funciones =new Funciones();
    Controlador control=new Controlador();
    public JDialogVer_Caja(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        nombresTitulos();
    }
     public void nombresTitulos() {
        String[] tit = {"COMPROBANTE", "DNI/RUC", "CLIENTE", "COSTO"};
        tabla.setColumnIdentifiers(tit);
        tblvntasC.setModel(tabla);
    }
    public void  limpiarTabla(JTable tb, DefaultTableModel md){
        while(tb.getRowCount()>0){
            md.removeRow(0);
        }
    }
    public void fechaNac(){
        SimpleDateFormat fff=new SimpleDateFormat("yyyy-MM-dd");
        Date fecha=jDateChooserFecha.getDate();
        fecha2=fff.format(fecha);

    }
    public void cargarVerVenta(){
        try {
            fechaNac();
            limpiarTabla(tblvntasC, tabla);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="call p_ventadehoy(?);";
            ps=con.prepareStatement(sql);
            ps.setString(1, ""+fecha2);
            rs=ps.executeQuery();
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadcolumnas=rsMd.getColumnCount();
            int [] anchos={130,200,130,130};
            for(int i=0;i<tblvntasC.getColumnCount();i++){
                tblvntasC.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            while(rs.next()){
                Object[] filas=new  Object[cantidadcolumnas];
                for(int i=0;i<cantidadcolumnas;i++){
                    filas[i]=rs.getObject(i+1);
                }
                tabla.addRow(filas);
            }
            int filas = tabla.getRowCount();
            lblCount.setText(String.valueOf(filas));
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
    }
    public void suma(){
        double suma = 0;
        double total=0;
        int tamFila = tblvntasC.getRowCount();
        for (int i = 0; i < tamFila; i++) {
            suma += Double.parseDouble(tblvntasC.getValueAt(i, 3).toString());
        }
        String cadena = Double.toString(suma);
        txtTot.setText(""+cadena);
    }
    public void limpiarTabla(){
        int fila=tblvntasC.getRowCount();
        for(int i=fila-1;i>=0;i--){
            tabla.removeRow(i);
        }
       jDateChooserFecha.setDate(funciones.StringADate(""));
        txtTot.setText("");
        
    }
   
    
    public void indivdual(){
        String fact=tblvntasC.getValueAt(tblvntasC.getSelectedRow(),0).toString();
        System.out.println(""+fact);
        if(control.VerificaConsulta("SELECT v.id_venta FROM venta_prenda v INNER JOIN comprobante c on v.fk_idComprobante=c.idComprobante WHERE c.fk_Num_boleta='"+fact+"'")){
            String bole=control.DevolverRegistroDato("SELECT v.fk_idComprobante FROM venta_prenda v INNER JOIN comprobante c on v.fk_idComprobante=c.idComprobante WHERE c.fk_Num_boleta='"+fact+"'", 1);
            int num=Integer.parseInt(bole);
            try {
                Conexion conn=new Conexion();
                Connection con=conn.conectar();
                JasperReport reporte=null;
                String path="src\\Reportes\\rBoleta.jasper";
                Map parametro=new HashMap();        
                parametro.put("p_bol", num);
                reporte=(JasperReport) JRLoader.loadObjectFromFile(path);
                JasperPrint jprint=JasperFillManager.fillReport(reporte,parametro,con);
                JasperViewer view=new JasperViewer(jprint,false);
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                view.setVisible(true);
            
            } catch (Exception e) {
                Logger.getLogger(JDialogVer_Venta.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        else if(control.VerificaConsulta("SELECT v.id_venta FROM venta_prenda v INNER JOIN comprobante c on v.fk_idComprobante=c.idComprobante WHERE c.fk_Num_factura='"+fact+"'")){
            String fac=control.DevolverRegistroDato("SELECT v.fk_idComprobante FROM venta_prenda v INNER JOIN comprobante c on v.fk_idComprobante=c.idComprobante WHERE c.fk_Num_factura='"+fact+"'", 1);
            int num=Integer.parseInt(fac);
            try {
                Conexion conn=new Conexion();
                Connection con=conn.conectar();
                JasperReport reporte=null;
                String path="src\\Reportes\\rFactura.jasper";
                Map parametro=new HashMap();
                parametro.put("p_fact", num);
                reporte=(JasperReport) JRLoader.loadObjectFromFile(path);
                JasperPrint jprint=JasperFillManager.fillReport(reporte,parametro,con);
                JasperViewer view=new JasperViewer(jprint,false);
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                view.setVisible(true);
            
            } catch (Exception e) {
                Logger.getLogger(JDialogVer_Venta.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "No ha sleccionado ningun comprobante");
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

        pnl_cab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnel_cab2 = new javax.swing.JPanel();
        btn_busc = new javax.swing.JButton();
        txtTot = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblvntasC = new javax.swing.JTable();
        lblCount = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jDateChooserFecha = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_cab.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("CAJA");

        javax.swing.GroupLayout pnl_cabLayout = new javax.swing.GroupLayout(pnl_cab);
        pnl_cab.setLayout(pnl_cabLayout);
        pnl_cabLayout.setHorizontalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(842, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        getContentPane().add(pnl_cab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 30));

        pnel_cab2.setBackground(new java.awt.Color(255, 255, 255));
        pnel_cab2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_busc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_busc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        btn_busc.setText("Buscar");
        btn_busc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscActionPerformed(evt);
            }
        });
        pnel_cab2.add(btn_busc, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, -1, -1));

        txtTot.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTot.setEnabled(false);
        pnel_cab2.add(txtTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 350, 80, -1));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setText("TOTAL VENTAS:");
        pnel_cab2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 350, -1, 20));

        tblvntasC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblvntasC.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblvntasC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblvntasCMouseClicked(evt);
            }
        });
        tblvntasC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblvntasCKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblvntasC);

        pnel_cab2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 72, 860, 262));

        lblCount.setText("0");
        pnel_cab2.add(lblCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 0, 20, 20));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("Cantidad de Ventas:");
        pnel_cab2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(735, 1, -1, 20));

        jButton1.setText("Reiniciar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pnel_cab2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, -1, -1));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setText("Fecha Inicio:");
        pnel_cab2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 30));

        jDateChooserFecha.setDateFormatString("MM/dd/yyyy");
        pnel_cab2.add(jDateChooserFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 160, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/impresora.png"))); // NOI18N
        jButton2.setText("Imprimir");
        pnel_cab2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 380, -1, -1));

        getContentPane().add(pnel_cab2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 880, 440));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_buscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscActionPerformed
    
    
    if(jDateChooserFecha.getDate()==null){
        JOptionPane.showMessageDialog(null, "Debe ingresar una fecha", "IMPORTANTE", JOptionPane.ERROR_MESSAGE, null);
    }
    else{
        cargarVerVenta();
        suma(); 
    }
    
    }//GEN-LAST:event_btn_buscActionPerformed

    private void tblvntasCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblvntasCMouseClicked
        // TODO add your handling code here:
        //verificar si se quiere imprimir la factura
        indivdual();
    }//GEN-LAST:event_tblvntasCMouseClicked

    private void tblvntasCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblvntasCKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_tblvntasCKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        limpiarTabla();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogVer_Caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogVer_Caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogVer_Caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogVer_Caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogVer_Caja dialog = new JDialogVer_Caja(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_busc;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDateChooserFecha;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCount;
    private javax.swing.JPanel pnel_cab2;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JTable tblvntasC;
    private javax.swing.JTextField txtTot;
    // End of variables declaration//GEN-END:variables
}
