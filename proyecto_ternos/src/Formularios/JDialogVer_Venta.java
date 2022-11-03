/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Formularios;

import Clases.Conexion;
import static Formularios.frmPrueba.text_cod;
import static formularios.frmComprobante.jTable1;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
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
public class JDialogVer_Venta extends javax.swing.JDialog {
     DefaultTableModel tabla = new DefaultTableModel();
    Conexion cn = new Conexion();
    String cpro;
    int bol;

    /**
     * Creates new form JDialogVer_Venta
     */
    public JDialogVer_Venta(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/venta1.png")).getImage());
        initComponents();
        nombresTitulos();
        cargarVerVenta();
    }
    public void nombresTitulos() {
        String[] tit = {"ID COMPROBANTE", "TIPO PRENDA", "MARCA", "MODELO","MATERIAL","TALLA","DESCRIPCION","CANTIDAD","P/U","TOTAL"};
        tabla.setColumnIdentifiers(tit);
        jTableTalla.setModel(tabla);
    }
    public void  limpiarTabla(JTable tb, DefaultTableModel md){
        while(tb.getRowCount()>0){
            md.removeRow(0);
        }
    }
    public void cargarVerVenta(){
        try {
            limpiarTabla(jTableTalla, tabla);
            PreparedStatement ps=null;
            ResultSet rs=null;
            pueb();
            Connection con=cn.conectar();
            String sql="call p_verventa(?);";
            ps=con.prepareStatement(sql);
            ps.setString(1, ""+cpro);
            rs=ps.executeQuery();
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadcolumnas=rsMd.getColumnCount();
            int [] anchos={70,200,200,200,200,200,200,200,200,200};
            for(int i=0;i<jTableTalla.getColumnCount();i++){
                jTableTalla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            while(rs.next()){
                Object[] filas=new  Object[cantidadcolumnas];
                for(int i=0;i<cantidadcolumnas;i++){
                    filas[i]=rs.getObject(i+1);
                }
                tabla.addRow(filas);
            }
            
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
    }
    public void pueb(){
        for (int i = 0; i < frmPrueba.table_factu.getRowCount(); i++) {
            cpro=""+frmPrueba.table_factu.getValueAt(i, 0);
            bol=Integer.parseInt(""+frmPrueba.table_factu.getValueAt(i, 0));
        }
       
    }
    public void reportedBoleta(){
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTalla = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButtonImprimir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Venta Articulo");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTableTalla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTableTalla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableTallaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableTalla);

        jLabel1.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("VENTA REALIZADA");

        jButtonImprimir.setBackground(new java.awt.Color(102, 102, 255));
        jButtonImprimir.setForeground(new java.awt.Color(255, 255, 255));
        jButtonImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/impresion.png"))); // NOI18N
        jButtonImprimir.setText("IMPRIMIR");
        jButtonImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImprimirActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo5.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel2)
                .addGap(312, 312, 312)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 962, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonImprimir)
                        .addGap(400, 400, 400))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(33, 33, 33)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonImprimir)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTableTallaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableTallaMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTableTallaMouseClicked

    private void jButtonImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImprimirActionPerformed
        // TODO add your handling code here:
        if(frmPrueba.jRadioButtonBoleta.isSelected()){
            reportedBoleta();
        }
        else if(frmPrueba.jRadioButtonFactura.isSelected()){
            reportedFactura();
        }
    }//GEN-LAST:event_jButtonImprimirActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogVer_Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogVer_Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogVer_Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogVer_Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogVer_Venta dialog = new JDialogVer_Venta(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButtonImprimir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableTalla;
    // End of variables declaration//GEN-END:variables
}
