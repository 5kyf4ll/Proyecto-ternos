/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VentanasFormulario;

import Clases.Conexion;
import Clases2.Controlador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PERSONAL
 */
public class frmEnvio extends javax.swing.JFrame {

    /**
     * Creates new form frmEnvio
     */
    DefaultTableModel tabla = new DefaultTableModel();
    DefaultTableModel tabla2 = new DefaultTableModel();
    DefaultTableModel tabla3 = new DefaultTableModel();
    Conexion cn = new Conexion();
    Controlador control=new Controlador();
    String fecha2;
    public frmEnvio() {
        initComponents();
        nombresTitulos();
        nombresTitulos2();
        nombresTitulos3();
        cargarDatos();
        cargarConfeccionador();
        fechaNac();
    }
     public void nombresTitulos() {
        String[] tit = {"DNI/RUC", "CLIENTE"};
        tabla.setColumnIdentifiers(tit);
        jTableCliente.setModel(tabla);
    }
    public void nombresTitulos2() {
        String[] tit = {"MODELO TELA", "MARCA","CODIGO","ESTADO","CONFECCIONADOR"};
        tabla2.setColumnIdentifiers(tit);
        jTable2.setModel(tabla2);
    }
    public void nombresTitulos3() {
        String[] tit = {"N°", "CONFECCIONADOR","ESTADO"};
        tabla3.setColumnIdentifiers(tit);
        jTable1.setModel(tabla3);
    }
     public void  limpiarTabla(JTable tb, DefaultTableModel md){
        while(tb.getRowCount()>0){
            md.removeRow(0);
        }
    }
    public void cargarDatos() {
        try {
            limpiarTabla(jTableCliente, tabla);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = cn.conectar();
            String sql = "select * from  v_clir";
            //String sql = "select * from  v_cliPedido";
            //v_clipedido
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {150, 250};
            for (int i = 0; i < jTableCliente.getColumnCount(); i++) {
                jTableCliente.getColumnModel().getColumn(i).setPreferredWidth(anchosEs[i]);

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
    public void cargarVerVenta(){
        try {
            int comp=0;
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            int tamFila = jTableCliente.getRowCount();
            String aux=jTableCliente.getValueAt(jTableCliente.getSelectedRow(),0).toString();
            if(control.VerificaConsulta("SELECT id_cliente FROM cliente WHERE fk_id_persona='"+aux+"'")){
                String aux2=control.DevolverRegistroDato("SELECT id_cliente FROM cliente WHERE fk_id_persona='"+aux+"'", 1);
                comp=Integer.parseInt(control.DevolverRegistroDato("SELECT idComprobante FROM comprobante WHERE fk_id_cliente='"+aux2+"'", 1));
                
            }
            else{
                
            }
            
            limpiarTabla(jTable2, tabla2);
            
            String sql="call p_per (?);";
            //p_Verped
            ps=con.prepareStatement(sql);
            ps.setString(1,""+comp);
            rs=ps.executeQuery();
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadcolumnas=rsMd.getColumnCount();
            int [] anchos={200,150,100,180,280};
            for(int i=0;i<jTable2.getColumnCount();i++){
                jTable2.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            while(rs.next()){
                Object[] filas=new  Object[cantidadcolumnas];
                for(int i=0;i<cantidadcolumnas;i++){
                    filas[i]=rs.getObject(i+1);
                }
                tabla2.addRow(filas);
            }
            
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
    }
     public void cargarConfeccionador() {
        try {
            limpiarTabla(jTable1, tabla3);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = cn.conectar();
            String sql = "select * from  v_confeccionador";
            //String sql = "select * from  v_cliPedido";
            //v_clipedido
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {35, 250,150};
            for (int i = 0; i < jTable1.getColumnCount(); i++) {
                jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchosEs[i]);

            }
            while (rs.next()) {
                Object[] filas = new Object[canatidadColumnasEs];
                for (int i = 0; i < canatidadColumnasEs; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                tabla3.addRow(filas);
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    public void fechaNac(){
        Date fecha=new Date();
        SimpleDateFormat fff=new SimpleDateFormat("yyyy-MM-dd");
        fecha2=fff.format(fecha);
        System.out.println(""+fecha2);

    }
    public void crear(){
        int filas;
        int n = jTable1.getSelectedRow();
        int m = jTable2.getSelectedRow();
       
        if(jTable1.getSelectedRow()>=0 && jTable2.getSelectedRow()>=0){
            String id=jTable1.getValueAt(n, 0).toString();
            System.out.println(""+id);
            String condi=control.DevolverRegistroDato("SELECT c.estado from confeccionador c WHERE c.idconfeccionador="+id+"", 1);
            String confec=control.DevolverRegistroDato("SELECT c.confeccionador from v_confeccionador c WHERE c.id="+id+"", 1);
            String venta=jTable2.getValueAt(m, 2).toString();
            if("SATURADO".equals(condi)){
                JOptionPane.showMessageDialog(null, "El confeccionador "+confec+"\nEsta SATURADO", "AVISO", JOptionPane.WARNING_MESSAGE, null);
            }
            else{
                if(control.VerificaConsulta("SELECT * FROM esconfec_x_confec e WHERE e.fk_idesconfec='"+venta+"';")){
                JOptionPane.showMessageDialog(null, "Este terno ya ha sido solicidato\n"
                        + "Su confección esta siendo realizo por: "+confec);
                }
                else{
                    fechaNac();
                    String venta1=jTable2.getValueAt(m, 2).toString();
                    String id4=control.DevolverRegistroDato("SELECT e.idesconfec from esconfec e WHERE e.fk_idventa_confec='"+venta1+"'", 1);
                    control.ActualizarRegistro("update esconfec set fechpedido='"+fecha2+"' where idesconfec='"+id4+"'");
                    String id2=control.DevolverRegistroDato("SELECT e.idesconfec from esconfec e WHERE e.fk_idventa_confec='"+venta+"'", 1);
                    control.ActualizarRegistro("insert into esconfec_x_confec(fk_idesconfec,fk_idconfeccionador,estado) values('"
                    + id2+"','"+id+"','EN PROCESO')");
                }
                
            }
        }
        else if(jTable2.getSelectedRow()<0 && jTable1.getSelectedRow()<0) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado nigun modelo tela ni confeccionador", "AVISO", JOptionPane.INFORMATION_MESSAGE, null);
        }
        else if(jTable1.getSelectedRow()<0){
            JOptionPane.showMessageDialog(null, "No ha seleccionado nigun confecionador", "AVISO", JOptionPane.INFORMATION_MESSAGE, null);
        }
        else if(jTable2.getSelectedRow()<0){
            JOptionPane.showMessageDialog(null, "No ha seleccionado nigun modelo tela", "AVISO", JOptionPane.INFORMATION_MESSAGE, null);
        }
        
        filas = n;
        cargarVerVenta();
    }
    
    public void editar(){
        int filas;
        int n = jTable1.getSelectedRow();
        int m = jTable2.getSelectedRow();
        
        if(jTable1.getSelectedRow()>-1 && jTable2.getSelectedRow()>-1){
            String id=jTable1.getValueAt(n, 0).toString();
            
            String condi=control.DevolverRegistroDato("SELECT c.estado from confeccionador c WHERE c.idconfeccionador="+id+"", 1);
            String confec=control.DevolverRegistroDato("SELECT c.confeccionador from v_confeccionador c WHERE c.id="+id+"", 1);
            System.out.println(""+condi);
            
            if("SATURADO".equals(condi)){
                JOptionPane.showMessageDialog(null, "El confeccionador "+confec+"\nEsta SATURADO", "AVISO", JOptionPane.WARNING_MESSAGE, null);
            }
            else{
                    fechaNac();
                    String venta=jTable2.getValueAt(m, 2).toString();
                    String id3=jTable1.getValueAt(n, 0).toString();
                    String id2=control.DevolverRegistroDato("SELECT e.idesconfec from esconfec e WHERE e.fk_idventa_confec='"+venta+"'", 1);
                    control.ActualizarRegistro("update esconfec set fechpedido='"+fecha2+"' where idesconfec='"+id2+"'");
                    
                    control.ActualizarRegistro("update esconfec_x_confec set fk_idconfeccionador='"+id3+"', estado='EN PROCESO' "
                            + "where fk_idesconfec='"+id2+"'");
                
                
            }
        }
        else if(jTable2.getSelectedRow()<0 && jTable1.getSelectedRow()<0) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado nigun modelo tela ni confeccionador", "AVISO", JOptionPane.INFORMATION_MESSAGE, null);
        }
        else if(jTable1.getSelectedRow()<0){
            JOptionPane.showMessageDialog(null, "No ha seleccionado nigun confecionador", "AVISO", JOptionPane.INFORMATION_MESSAGE, null);
        }
        else if(jTable2.getSelectedRow()<0){
            JOptionPane.showMessageDialog(null, "No ha seleccionado nigun modelo tela", "AVISO", JOptionPane.INFORMATION_MESSAGE, null);
        }
        
        filas = n;
        cargarVerVenta();
    }
    public void MostrarCientes(){
     control.LlenarJtable(tabla,"select * from v_clir where DNI like'%"
     +jTextFieldBuscar.getText()+"%'",2);    
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextFieldBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCliente = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Envio Confeccionador");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente Pedido", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 600, 313));

        jTextFieldBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldBuscarKeyTyped(evt);
            }
        });
        jPanel2.add(jTextFieldBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 180, -1));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("DNI/RUC");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        jTableCliente.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableClienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableCliente);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 300, 313));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 970, 440));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Confeccionador", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 15))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jScrollPane3.setViewportView(jTable1);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 350, 240));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 80, 390, 300));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/enviar.png"))); // NOI18N
        jButton1.setText("Enviar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/actu.png"))); // NOI18N
        jButton2.setText("Actualizar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1420, 560));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableClienteMouseClicked
        // TODO add your handling code here:
        cargarVerVenta();
    }//GEN-LAST:event_jTableClienteMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        crear();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        //crear();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        editar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextFieldBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBuscarKeyTyped
        // TODO add your handling code here:
        MostrarCientes();
    }//GEN-LAST:event_jTextFieldBuscarKeyTyped

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
            java.util.logging.Logger.getLogger(frmEnvio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmEnvio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmEnvio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmEnvio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmEnvio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTableCliente;
    private javax.swing.JTextField jTextFieldBuscar;
    // End of variables declaration//GEN-END:variables
}
