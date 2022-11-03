/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Formularios;

import Clases.Conexion;
import Clases2.Conexion2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USUARIO
 */
public class JDialogCamisas extends javax.swing.JDialog {
    DefaultTableModel model=new DefaultTableModel();
    DefaultTableModel model2=new DefaultTableModel();
    Conexion cn=new Conexion();

    /**
     * Creates new form JDialogCamisas
     */
    public JDialogCamisas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        titulos();
        cargarCamisa();
        titulos2();
        cargarCamisa2();
    }
    public void titulos(){
        String[] tit={"ID ARTICULO","PRENDA","MARCA","TALLA","PRECIO","STOCK"};
        model.setColumnIdentifiers(tit);
        jTableCamisa.setModel(model);
    }
    public void titulos2(){
        String[] tit={"ID ARTICULO","PRENDA","MARCA","MODELO","TALLA","PRECIO","STOCK"};
        model2.setColumnIdentifiers(tit);
        jtable2.setModel(model2);
    }
    public void  limpiarTabla(JTable tb, DefaultTableModel md){
        while(tb.getRowCount()>0){
            md.removeRow(0);
        }
    }
    public void cargarCamisa(){
        try {
            limpiarTabla(jTableCamisa, model);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="Select * from v_camisaMod;";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadcolumnas=rsMd.getColumnCount();
            int [] anchos={70,130,120,70,80,80};
            for(int i=0;i<jTableCamisa.getColumnCount();i++){
                jTableCamisa.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
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
    public void cargarCamisa2(){
        try {
            limpiarTabla(jtable2, model2);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="Select * from v_camisas;";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadcolumnas=rsMd.getColumnCount();
            int [] anchos={70,130,120,130,70,80,80};
            for(int i=0;i<jtable2.getColumnCount();i++){
                jtable2.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
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
    
    public void buscarTalla(){
        String aux=jComboBoxBusqueda.getSelectedItem().toString();
        String campo=jTextFieldBusqueda.getText();
        String where="";
        if(!"".equals(campo)){
            where="WHERE "+aux+"='"+campo+"'";
            
        }
        try {
            jTableCamisa.setModel(model);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="SELECT idarticulo,tipo_prenda,marca,talla,precio,stock FROM v_camisaMod "+where;
            System.out.println(sql);
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas=rsMd.getColumnCount();
            int []anchos={70,130,120,70,80,80};
            for(int i=0;i<jTableCamisa.getColumnCount();i++){
                jTableCamisa.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            limpiarTabla(jTableCamisa, model);
            while(rs.next()){
                Object[] filas=new Object[cantidadColumnas];
                for(int i=0;i<cantidadColumnas;i++){
                    filas[i]=rs.getObject(i+1);
                    
                }
                model.addRow(filas);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    public void reiniciar(){
        limpiarTabla(jTableCamisa, model);
        jComboBoxBusqueda.setSelectedIndex(0);
        cargarCamisa();
        jTextFieldBusqueda.setText("");
    }
    
    public void buscarTalla2(){
        String aux=jComboBoxBusqueda2.getSelectedItem().toString();
        String campo=jTextFieldBusqueda2.getText();
        String where="";
        if(!"".equals(campo)){
            where="WHERE "+aux+"='"+campo+"'";
            
        }
        try {
            jtable2.setModel(model2);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="SELECT idarticulo,tipo_prenda,marca,nom_modelo,talla,precio,stock FROM v_camisas "+where;
            System.out.println(sql);
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas=rsMd.getColumnCount();
            int []anchos={70,130,120,130,70,80,80};
            for(int i=0;i<jtable2.getColumnCount();i++){
                jtable2.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            limpiarTabla(jtable2, model2);
            while(rs.next()){
                Object[] filas=new Object[cantidadColumnas];
                for(int i=0;i<cantidadColumnas;i++){
                    filas[i]=rs.getObject(i+1);
                    
                }
                model2.addRow(filas);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    public void reiniciar2(){
        limpiarTabla(jtable2, model2);
        jComboBoxBusqueda2.setSelectedIndex(0);
        cargarCamisa2();
        jTextFieldBusqueda2.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnel_all = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCamisa = new javax.swing.JTable();
        pnel_inf1 = new javax.swing.JPanel();
        jTextFieldBusqueda = new javax.swing.JTextField();
        jComboBoxBusqueda = new javax.swing.JComboBox<>();
        btnBuscarTodo = new javax.swing.JButton();
        btnBuscarTodo1 = new javax.swing.JButton();
        pnel_all_prov = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtable2 = new javax.swing.JTable();
        pnel_inf2 = new javax.swing.JPanel();
        jTextFieldBusqueda2 = new javax.swing.JTextField();
        jComboBoxBusqueda2 = new javax.swing.JComboBox<>();
        btnBuscar2 = new javax.swing.JButton();
        btnReiniciar = new javax.swing.JButton();
        lblcab = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTabbedPane1.setBackground(new java.awt.Color(204, 204, 204));

        pnel_all.setBackground(new java.awt.Color(255, 255, 255));
        pnel_all.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableCamisa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableCamisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCamisaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableCamisa);

        pnel_all.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 610, 320));

        pnel_inf1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204), 2));
        pnel_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextFieldBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldBusquedaKeyReleased(evt);
            }
        });
        pnel_inf1.add(jTextFieldBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 130, -1));

        jComboBoxBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione...", "idarticulo", "tipo_prenda", "marca", "talla", "precio", "stock" }));
        pnel_inf1.add(jComboBoxBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 140, -1));

        pnel_all.add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 350, 40));

        btnBuscarTodo.setBackground(new java.awt.Color(255, 153, 51));
        btnBuscarTodo.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarTodo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reiniciar3.png"))); // NOI18N
        btnBuscarTodo.setText("Reiniciar");
        btnBuscarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarTodoActionPerformed(evt);
            }
        });
        pnel_all.add(btnBuscarTodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 30, 110, 40));

        btnBuscarTodo1.setBackground(new java.awt.Color(51, 51, 255));
        btnBuscarTodo1.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarTodo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar2.png"))); // NOI18N
        btnBuscarTodo1.setText("Buscar");
        btnBuscarTodo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarTodo1ActionPerformed(evt);
            }
        });
        pnel_all.add(btnBuscarTodo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 110, 40));

        jTabbedPane1.addTab("Camisas", pnel_all);

        pnel_all_prov.setBackground(new java.awt.Color(255, 255, 255));
        pnel_all_prov.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtable2);

        pnel_all_prov.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 610, 330));

        pnel_inf2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204), 2));
        pnel_inf2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextFieldBusqueda2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldBusqueda2KeyReleased(evt);
            }
        });
        pnel_inf2.add(jTextFieldBusqueda2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 150, -1));

        jComboBoxBusqueda2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione...", "idarticulo", "tipo_prenda", "marca", "nom_modelo", "talla", "precio", "stock" }));
        pnel_inf2.add(jComboBoxBusqueda2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 140, -1));

        pnel_all_prov.add(pnel_inf2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 350, 40));

        btnBuscar2.setBackground(new java.awt.Color(51, 51, 255));
        btnBuscar2.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar2.png"))); // NOI18N
        btnBuscar2.setText("Buscar");
        btnBuscar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscar2ActionPerformed(evt);
            }
        });
        pnel_all_prov.add(btnBuscar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 110, 40));

        btnReiniciar.setBackground(new java.awt.Color(255, 153, 51));
        btnReiniciar.setForeground(new java.awt.Color(255, 255, 255));
        btnReiniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reiniciar3.png"))); // NOI18N
        btnReiniciar.setText("Reiniciar");
        btnReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarActionPerformed(evt);
            }
        });
        pnel_all_prov.add(btnReiniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 30, 110, 40));

        jTabbedPane1.addTab("Camisas-Modelo", pnel_all_prov);

        lblcab.setBackground(new java.awt.Color(204, 204, 204));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("CAMISAS");

        javax.swing.GroupLayout lblcabLayout = new javax.swing.GroupLayout(lblcab);
        lblcab.setLayout(lblcabLayout);
        lblcabLayout.setHorizontalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblcabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        lblcabLayout.setVerticalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblcab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblcab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTableCamisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCamisaMouseClicked
        // TODO add your handling code here:
       

    }//GEN-LAST:event_jTableCamisaMouseClicked

    private void jtable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtable2MouseClicked
        
    }//GEN-LAST:event_jtable2MouseClicked

    private void jTextFieldBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBusquedaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBusquedaKeyReleased

    private void btnBuscarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarTodoActionPerformed
        // TODO add your handling code here:
        reiniciar();
    }//GEN-LAST:event_btnBuscarTodoActionPerformed

    private void btnBuscarTodo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarTodo1ActionPerformed
        // TODO add your handling code here:
        buscarTalla();
    }//GEN-LAST:event_btnBuscarTodo1ActionPerformed

    private void jTextFieldBusqueda2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBusqueda2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBusqueda2KeyReleased

    private void btnBuscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar2ActionPerformed
        // TODO add your handling code here:
        buscarTalla2();
    }//GEN-LAST:event_btnBuscar2ActionPerformed

    private void btnReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarActionPerformed
        // TODO add your handling code here:
        reiniciar2();
    }//GEN-LAST:event_btnReiniciarActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogCamisas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogCamisas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogCamisas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogCamisas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogCamisas dialog = new JDialogCamisas(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBuscar2;
    private javax.swing.JButton btnBuscarTodo;
    private javax.swing.JButton btnBuscarTodo1;
    private javax.swing.JButton btnReiniciar;
    private javax.swing.JComboBox<String> jComboBoxBusqueda;
    private javax.swing.JComboBox<String> jComboBoxBusqueda2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableCamisa;
    private javax.swing.JTextField jTextFieldBusqueda;
    private javax.swing.JTextField jTextFieldBusqueda2;
    private javax.swing.JTable jtable2;
    private javax.swing.JPanel lblcab;
    private javax.swing.JPanel pnel_all;
    private javax.swing.JPanel pnel_all_prov;
    private javax.swing.JPanel pnel_inf1;
    private javax.swing.JPanel pnel_inf2;
    // End of variables declaration//GEN-END:variables
}
