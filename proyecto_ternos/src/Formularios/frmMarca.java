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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USUARIO
 */
public class frmMarca extends javax.swing.JFrame {
    DefaultTableModel model=new DefaultTableModel();
    Conexion cn=new Conexion();

    /**
     * Creates new form frmMarca
     */
    public frmMarca() {
        initComponents();
        titulos();
        cargarMarca();
        jFormattedCod.requestFocus();
    }
     public void titulos(){
        String[] tit={"CODIGO","MARCA"};
        model.setColumnIdentifiers(tit);
        jTableMarca.setModel(model);
    }
    public void  limpiarTabla(JTable tb, DefaultTableModel md){
        while(tb.getRowCount()>0){
            md.removeRow(0);
        }
    }
    public void cargarMarca(){
        try {
            limpiarTabla(jTableMarca, model);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="Select * from marca;";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadcolumnas=rsMd.getColumnCount();
            int [] anchos={70,100};
            for(int i=0;i<jTableMarca.getColumnCount();i++){
                jTableMarca.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
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
          ps=con.prepareStatement("call p_insertMarc(?,?);");
          ps.setString(1, jFormattedCod.getText());
          ps.setString(2,jTextFieldMarca.getText());
          ps.execute();
          JOptionPane.showMessageDialog(null, "Marca guaradada!!");
          cargarMarca();
          limpiar();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar registro!!");
            System.out.println(e);
        }
    }
    public void limpiar(){
        jFormattedCod.setText("");
        jTextFieldMarca.setText("");
        jFormattedCod.requestFocus();
    }
    public void eliminarMarca(){
        PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
            int fila=jTableMarca.getSelectedRow();
            String codigo=jTableMarca.getValueAt(fila, 0).toString();
            
            ps=con.prepareStatement("call p_deleteMarc(?);");
            ps.setString(1, codigo);
            ps.execute();
            model.removeRow(fila);
            JOptionPane.showMessageDialog(null, "Registro elimanado con exito");
            limpiar();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en eliminacion!");
            System.out.println(e.toString());
        }
        cargarMarca();
        
    }
    public void cargarDatos(){
        int filas;
        int n=jTableMarca.getSelectedRow();
        jFormattedCod.setText(jTableMarca.getValueAt(n, 0).toString());
        jTextFieldMarca.setText(jTableMarca.getValueAt(n, 1).toString());
        filas=n;
    }
    public void modificarMarca(){
        
        PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
           
            
            ps=con.prepareStatement("call p_updateMarc(?,?)");
            ps.setString(1, jFormattedCod.getText());
            ps.setString(2, jTextFieldMarca.getText());
            ps.execute();
            
            JOptionPane.showMessageDialog(null, "Registro actualizado con exito");
            limpiar();
            cargarMarca();
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
            jTableMarca.setModel(model);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="SELECT idmarca,marca FROM marca "+where;
            System.out.println(sql);
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas=rsMd.getColumnCount();
            int []anchos={70,100};
            for(int i=0;i<jTableMarca.getColumnCount();i++){
                jTableMarca.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            limpiarTabla(jTableMarca, model);
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
        limpiarTabla(jTableMarca, model);
        jComboBoxBusqueda.setSelectedIndex(0);
        cargarMarca();
        limpiar();
        jFormattedCod.requestFocus();
        jTextFieldBusqueda.setText("");
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
        pnel_all = new javax.swing.JPanel();
        pnel_inf = new javax.swing.JPanel();
        lblpais = new javax.swing.JLabel();
        jTextFieldMarca = new javax.swing.JTextField();
        lblcod = new javax.swing.JLabel();
        jFormattedCod = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMarca = new javax.swing.JTable();
        pnel_inf1 = new javax.swing.JPanel();
        jTextFieldBusqueda = new javax.swing.JTextField();
        jComboBoxBusqueda = new javax.swing.JComboBox<>();
        btnBuscarTodo = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblcab = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        tbar = new javax.swing.JToolBar();
        btn_new_pais = new javax.swing.JButton();
        btn_clean_pais = new javax.swing.JButton();
        btn_delete_pais = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel10 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        lblcountipRop = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnel_all.setBackground(new java.awt.Color(255, 255, 255));
        pnel_all.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnel_inf.setBackground(new java.awt.Color(255, 255, 255));
        pnel_inf.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_inf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblpais.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblpais.setText("Marca:");
        pnel_inf.add(lblpais, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, 20));

        jTextFieldMarca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldMarcaFocusLost(evt);
            }
        });
        jTextFieldMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldMarcaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldMarcaKeyTyped(evt);
            }
        });
        pnel_inf.add(jTextFieldMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 170, -1));

        lblcod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcod.setText("Código:");
        pnel_inf.add(lblcod, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        try {
            jFormattedCod.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("MC###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedCodActionPerformed(evt);
            }
        });
        jFormattedCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedCodKeyPressed(evt);
            }
        });
        pnel_inf.add(jFormattedCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 90, -1));

        pnel_all.add(pnel_inf, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 270, 90));

        jTableMarca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableMarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMarcaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableMarca);

        pnel_all.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 430, 180));

        pnel_inf1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204), 2));
        pnel_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextFieldBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldBusquedaKeyReleased(evt);
            }
        });
        pnel_inf1.add(jTextFieldBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 130, -1));

        jComboBoxBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione...", "idmarca", "marca" }));
        pnel_inf1.add(jComboBoxBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 140, -1));

        pnel_all.add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 320, 40));

        btnBuscarTodo.setBackground(new java.awt.Color(51, 51, 255));
        btnBuscarTodo.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarTodo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar4.png"))); // NOI18N
        btnBuscarTodo.setText("Buscar");
        btnBuscarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarTodoActionPerformed(evt);
            }
        });
        pnel_all.add(btnBuscarTodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 110, 40));

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregar4.png"))); // NOI18N
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pnel_all.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        jButton2.setBackground(new java.awt.Color(51, 153, 255));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar4.png"))); // NOI18N
        jButton2.setText("Actualizar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        pnel_all.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, -1, -1));

        jLabel3.setFont(new java.awt.Font("Castellar", 1, 65)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("ma");
        pnel_all.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 130, 60));

        jPanel1.add(pnel_all, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 730, 250));

        lblcab.setBackground(new java.awt.Color(204, 204, 204));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("MARCA");

        javax.swing.GroupLayout lblcabLayout = new javax.swing.GroupLayout(lblcab);
        lblcab.setLayout(lblcabLayout);
        lblcabLayout.setHorizontalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblcabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(678, Short.MAX_VALUE))
        );
        lblcabLayout.setVerticalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(lblcab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 30));

        tbar.setBackground(new java.awt.Color(255, 255, 255));
        tbar.setRollover(true);
        tbar.setEnabled(false);

        btn_new_pais.setBackground(new java.awt.Color(102, 204, 255));
        btn_new_pais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Reniciar4.png"))); // NOI18N
        btn_new_pais.setText("Reiniciar");
        btn_new_pais.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btn_new_pais.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_new_pais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_new_paisActionPerformed(evt);
            }
        });
        tbar.add(btn_new_pais);

        btn_clean_pais.setBackground(new java.awt.Color(102, 204, 255));
        btn_clean_pais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar4.png"))); // NOI18N
        btn_clean_pais.setText("Eliminar");
        btn_clean_pais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clean_paisActionPerformed(evt);
            }
        });
        tbar.add(btn_clean_pais);

        btn_delete_pais.setBackground(new java.awt.Color(102, 204, 255));
        btn_delete_pais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir4.png"))); // NOI18N
        btn_delete_pais.setText("Salir");
        btn_delete_pais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_paisActionPerformed(evt);
            }
        });
        tbar.add(btn_delete_pais);
        tbar.add(jSeparator2);

        jLabel10.setText("Cantidad de Registros:");
        tbar.add(jLabel10);
        tbar.add(jSeparator1);

        lblcountipRop.setText("0");
        tbar.add(lblcountipRop);

        jPanel1.add(tbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 720, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldMarcaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldMarcaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMarcaFocusLost

    private void jTextFieldMarcaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMarcaKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextFieldMarcaKeyReleased

    private void jTableMarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMarcaMouseClicked
        // TODO add your handling code here:
        cargarDatos();
    }//GEN-LAST:event_jTableMarcaMouseClicked

    private void btn_new_paisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_new_paisActionPerformed
        reiniciar();
    }//GEN-LAST:event_btn_new_paisActionPerformed

    private void btn_clean_paisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clean_paisActionPerformed
        // TODO add your handling code here:
        eliminarMarca();
    }//GEN-LAST:event_btn_clean_paisActionPerformed

    private void btn_delete_paisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_paisActionPerformed
        // TODO add your handling code here:

        

    }//GEN-LAST:event_btn_delete_paisActionPerformed

    private void jTextFieldBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBusquedaKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextFieldBusquedaKeyReleased

    private void btnBuscarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarTodoActionPerformed
        // TODO add your handling code here:
        buscarTalla();
    }//GEN-LAST:event_btnBuscarTodoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        insertTalla();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        modificarMarca();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jFormattedCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedCodActionPerformed

    private void jFormattedCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedCodKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldMarca.requestFocus();
        }
    }//GEN-LAST:event_jFormattedCodKeyPressed

    private void jTextFieldMarcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMarcaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextFieldMarcaKeyTyped

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
            java.util.logging.Logger.getLogger(frmMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMarca().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarTodo;
    private javax.swing.JButton btn_clean_pais;
    private javax.swing.JButton btn_delete_pais;
    private javax.swing.JButton btn_new_pais;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBoxBusqueda;
    private javax.swing.JFormattedTextField jFormattedCod;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JTable jTableMarca;
    private javax.swing.JTextField jTextFieldBusqueda;
    private javax.swing.JTextField jTextFieldMarca;
    private javax.swing.JPanel lblcab;
    private javax.swing.JLabel lblcod;
    private javax.swing.JLabel lblcountipRop;
    private javax.swing.JLabel lblpais;
    private javax.swing.JPanel pnel_all;
    private javax.swing.JPanel pnel_inf;
    private javax.swing.JPanel pnel_inf1;
    private javax.swing.JToolBar tbar;
    // End of variables declaration//GEN-END:variables
}
