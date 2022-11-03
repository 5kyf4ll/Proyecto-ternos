/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import Clases.Conexion;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USUARIO
 */
public class frmMarcaTela extends javax.swing.JFrame {
    DefaultTableModel model=new DefaultTableModel();
    Conexion cn=new Conexion();

    /**
     * Creates new form frmMarcaTela
     */
    public frmMarcaTela() {
        initComponents();
        titulos();
        cargarFacultad();
        jTextFieldCodi.requestFocus();
    }
    public void titulos(){
        String[] tit={"CODIGO","MARCA TELA"};
        model.setColumnIdentifiers(tit);
        jTableMaterial.setModel(model);
    }
    public void  limpiarTabla(JTable tb, DefaultTableModel md){
        while(tb.getRowCount()>0){
            md.removeRow(0);
        }
    }
    public void cargarFacultad(){
        try {
            limpiarTabla(jTableMaterial, model);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="Select * from marca_tela;";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadcolumnas=rsMd.getColumnCount();
            int [] anchos={70,130};
            for(int i=0;i<jTableMaterial.getColumnCount();i++){
                jTableMaterial.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
                
            }
            
            while(rs.next()){
                Object[] filas=new  Object[cantidadcolumnas];
                for(int i=0;i<cantidadcolumnas;i++){
                    filas[i]=rs.getObject(i+1);
                }
                model.addRow(filas);
            }
            int filas = model.getRowCount();
            lblcountipRop.setText(String.valueOf(filas)); 
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
    }
    public void insertTalla(){
        PreparedStatement ps=null;
        try {
          Connection con=cn.conectar();
          ps=con.prepareStatement("call p_insertMte(?,?);");
          ps.setString(1, jTextFieldCodi.getText());
          ps.setString(2,jTextFieldMaterial.getText());
          ps.execute();
          JOptionPane.showMessageDialog(null, "Material guaradada!!");
          cargarFacultad();
          limpiar();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar registro!!");
            System.out.println(e);
        }
    }
    public void limpiar(){
        jTextFieldCodi.setText("");
        jTextFieldMaterial.setText("");
        jTextFieldCodi.requestFocus();
    }
    public void eliminarTalla(){
        PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
            int fila=jTableMaterial.getSelectedRow();
            String codigo=jTableMaterial.getValueAt(fila, 0).toString();
            
            ps=con.prepareStatement("call p_deleteMte(?);");
            ps.setString(1, codigo);
            ps.execute();
            model.removeRow(fila);
            JOptionPane.showMessageDialog(null, "Registro elimanado con exito");
            limpiar();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en eliminacion!");
            System.out.println(e.toString());
        }
        cargarFacultad();
        
    }
    public void cargarDatos(){
        int filas;
        int n=jTableMaterial.getSelectedRow();
        jTextFieldCodi.setText(jTableMaterial.getValueAt(n, 0).toString());
        jTextFieldMaterial.setText(jTableMaterial.getValueAt(n, 1).toString());
        filas=n;
    }
    public void modificarTalla(){
        
        PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
           
            
            ps=con.prepareStatement("call p_updateMte(?,?)");
            ps.setString(1, jTextFieldCodi.getText());
            ps.setString(2, jTextFieldMaterial.getText());
            ps.execute();
            
            JOptionPane.showMessageDialog(null, "Registro actualizado con exito");
            limpiar();
            cargarFacultad();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en actualizacion!");
            System.out.println(e.toString());
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
            jTableMaterial.setModel(model);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="SELECT idmarca_tela,marca FROM marca_tela "+where;
            System.out.println(sql);
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas=rsMd.getColumnCount();
            int []anchos={70,130};
            for(int i=0;i<jTableMaterial.getColumnCount();i++){
                jTableMaterial.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            limpiarTabla(jTableMaterial, model);
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
        limpiarTabla(jTableMaterial, model);
        jComboBoxBusqueda.setSelectedIndex(0);
        cargarFacultad();
        limpiar();
        jTextFieldCodi.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_all = new javax.swing.JPanel();
        pnel_inf1 = new javax.swing.JPanel();
        jTextFieldBusqueda = new javax.swing.JTextField();
        jComboBoxBusqueda = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMaterial = new javax.swing.JTable();
        pnel_inf = new javax.swing.JPanel();
        lbltipr = new javax.swing.JLabel();
        lblcod = new javax.swing.JLabel();
        jTextFieldMaterial = new javax.swing.JTextField();
        jTextFieldCodi = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        tbar = new javax.swing.JToolBar();
        btn_new = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_delete1 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel10 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        lblcountipRop = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnBuscarTodo = new javax.swing.JButton();
        pnl_cab = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_all.setBackground(new java.awt.Color(255, 255, 255));
        panel_all.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnel_inf1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204), 2));
        pnel_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextFieldBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldBusquedaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldBusquedaKeyTyped(evt);
            }
        });
        pnel_inf1.add(jTextFieldBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 130, -1));

        jComboBoxBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione...", "idmaterial", "material" }));
        pnel_inf1.add(jComboBoxBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 140, -1));

        panel_all.add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 350, 40));

        jTableMaterial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTableMaterial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMaterialMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableMaterial);

        panel_all.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 460, 130));

        pnel_inf.setBackground(new java.awt.Color(255, 255, 255));
        pnel_inf.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbltipr.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbltipr.setText("Marca:");

        lblcod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcod.setText("Código:");

        jTextFieldMaterial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldMaterialKeyTyped(evt);
            }
        });

        jTextFieldCodi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCodiKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCodiKeyTyped(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregar4.png"))); // NOI18N
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(51, 153, 255));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar4.png"))); // NOI18N
        jButton2.setText("Actualizar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnel_infLayout = new javax.swing.GroupLayout(pnel_inf);
        pnel_inf.setLayout(pnel_infLayout);
        pnel_infLayout.setHorizontalGroup(
            pnel_infLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnel_infLayout.createSequentialGroup()
                .addGroup(pnel_infLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnel_infLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(pnel_infLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblcod)
                            .addComponent(lbltipr))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnel_infLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnel_infLayout.createSequentialGroup()
                                .addComponent(jTextFieldMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE))
                            .addGroup(pnel_infLayout.createSequentialGroup()
                                .addComponent(jTextFieldCodi, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnel_infLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addGap(36, 36, 36))
        );
        pnel_infLayout.setVerticalGroup(
            pnel_infLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnel_infLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnel_infLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblcod)
                    .addComponent(jTextFieldCodi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(pnel_infLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbltipr, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnel_infLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        panel_all.add(pnel_inf, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 290, 140));

        tbar.setBackground(new java.awt.Color(255, 255, 255));
        tbar.setRollover(true);

        btn_new.setBackground(new java.awt.Color(102, 204, 255));
        btn_new.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Reniciar4.png"))); // NOI18N
        btn_new.setText("Reiniciar");
        btn_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newActionPerformed(evt);
            }
        });
        tbar.add(btn_new);

        btn_delete.setBackground(new java.awt.Color(102, 204, 255));
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar4.png"))); // NOI18N
        btn_delete.setText("Eliminar");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        tbar.add(btn_delete);

        btn_delete1.setBackground(new java.awt.Color(102, 204, 255));
        btn_delete1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir4.png"))); // NOI18N
        btn_delete1.setText("Salir");
        btn_delete1.setFocusable(false);
        btn_delete1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_delete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete1ActionPerformed(evt);
            }
        });
        tbar.add(btn_delete1);
        tbar.add(jSeparator2);

        jLabel10.setText("Cantidad de Registros:");
        tbar.add(jLabel10);
        tbar.add(jSeparator1);

        lblcountipRop.setText("0");
        tbar.add(lblcountipRop);

        panel_all.add(tbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 450, 40));

        jLabel3.setFont(new java.awt.Font("Castellar", 1, 65)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("mT");
        panel_all.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, -1, 60));

        btnBuscarTodo.setBackground(new java.awt.Color(51, 51, 255));
        btnBuscarTodo.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarTodo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar4.png"))); // NOI18N
        btnBuscarTodo.setText("Buscar");
        btnBuscarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarTodoActionPerformed(evt);
            }
        });
        panel_all.add(btnBuscarTodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 210, 110, 40));

        pnl_cab.setBackground(new java.awt.Color(204, 204, 204));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("MARCA TELA");

        javax.swing.GroupLayout pnl_cabLayout = new javax.swing.GroupLayout(pnl_cab);
        pnl_cab.setLayout(pnl_cabLayout);
        pnl_cabLayout.setHorizontalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(374, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        panel_all.add(pnl_cab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 30));
        panel_all.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ok_icon.png"))); // NOI18N
        panel_all.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 150, -1, -1));

        getContentPane().add(panel_all, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 407));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBusquedaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBusquedaKeyReleased

    private void jTableMaterialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMaterialMouseClicked
        // TODO add your handling code here:
        cargarDatos();
    }//GEN-LAST:event_jTableMaterialMouseClicked

    private void jTextFieldCodiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCodiKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldMaterial.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldCodiKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        insertTalla();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        modificarTalla();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newActionPerformed

        reiniciar();
    }//GEN-LAST:event_btn_newActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        eliminarTalla();
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_delete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete1ActionPerformed
        // TODO add your handling code here:
        //dispose();
        new frmMarca().setVisible(true);
    }//GEN-LAST:event_btn_delete1ActionPerformed

    private void btnBuscarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarTodoActionPerformed
        // TODO add your handling code here:
        buscarTalla();
    }//GEN-LAST:event_btnBuscarTodoActionPerformed

    private void jTextFieldCodiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCodiKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
        }
    
    }//GEN-LAST:event_jTextFieldCodiKeyTyped

    private void jTextFieldMaterialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMaterialKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextFieldMaterialKeyTyped

    private void jTextFieldBusquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBusquedaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextFieldBusquedaKeyTyped

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
            java.util.logging.Logger.getLogger(frmMarcaTela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMarcaTela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMarcaTela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMarcaTela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMarcaTela().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarTodo;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_delete1;
    private javax.swing.JButton btn_new;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBoxBusqueda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JTable jTableMaterial;
    private javax.swing.JTextField jTextFieldBusqueda;
    private javax.swing.JTextField jTextFieldCodi;
    private javax.swing.JTextField jTextFieldMaterial;
    private javax.swing.JLabel lblcod;
    private javax.swing.JLabel lblcountipRop;
    private javax.swing.JLabel lbltipr;
    private javax.swing.JPanel panel_all;
    private javax.swing.JPanel pnel_inf;
    private javax.swing.JPanel pnel_inf1;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JToolBar tbar;
    // End of variables declaration//GEN-END:variables
}
