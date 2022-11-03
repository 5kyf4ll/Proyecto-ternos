/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Formularios;

import Clases.Conexion;
import Clases.limpiar;
import Clases2.Controlador;
import formularios.frmArticulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USUARIO
 */
public class JDialogVerProductos extends javax.swing.JDialog {
    DefaultTableModel tabla = new DefaultTableModel();
    Conexion cn = new Conexion();
    limpiar lim = new limpiar();

    /**
     * Creates new form JDialogVerProductos
     */
    Controlador control=new Controlador();
    Controlador ctl=new Controlador();
    public JDialogVerProductos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/articulo.png")).getImage());
        nombresTitulos();
        cargarDatos();
    }
     public void nombresTitulos() {
        String[] tit = {"ID Articulo", "Tipo Prenda", "Marca", "Modelo ", "Tallas ", "Material","Descrpcion","Precio", "Stock"};
        tabla.setColumnIdentifiers(tit);
        jTableTalla.setModel(tabla);
    }
      public void limpiarCampos() {
        jTextFieldBusqueda.setText("");
      }
    public void reinicioDatos() {
        lim.limpiarTabla(jTableTalla, tabla);
        cargarDatos();
        limpiarCampos();
        jTextFieldBusqueda.requestFocus();
    }
     public void cargarDatos() {
        try {
            lim.limpiarTabla(jTableTalla, tabla);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = cn.conectar();
            String sql = "select * from v_Productos";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {200, 200, 200, 200, 200, 200, 200, 200, 200};
            for (int i = 0; i < jTableTalla.getColumnCount(); i++) {
                jTableTalla.getColumnModel().getColumn(i).setPreferredWidth(anchosEs[i]);

            }
            while (rs.next()) {
                Object[] filas = new Object[canatidadColumnasEs];
                for (int i = 0; i < canatidadColumnasEs; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                tabla.addRow(filas);
            }
            int filas = tabla.getRowCount();
            lblcountProd.setText(String.valueOf(filas));
        } catch (SQLException e) {
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
            jTableTalla.setModel(tabla);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="SELECT idarticulo,tipo_prenda,marca,nom_modelo,talla,material,descripcion,precio,stock FROM v_productos "+where;
            System.out.println(sql);
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas=rsMd.getColumnCount();
            int []anchos={200, 200, 200, 200, 200, 200, 200, 200, 200};
            for(int i=0;i<jTableTalla.getColumnCount();i++){
                jTableTalla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            lim.limpiarTabla(jTableTalla, tabla);
            while(rs.next()){
                Object[] filas=new Object[cantidadColumnas];
                for(int i=0;i<cantidadColumnas;i++){
                    filas[i]=rs.getObject(i+1);
                    
                }
                tabla.addRow(filas);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    public void reiniciar(){
        lim.limpiarTabla(jTableTalla, tabla);
        jComboBoxBusqueda.setSelectedIndex(0);
        cargarDatos();
        limpiarCampos();
        jTextFieldBusqueda.requestFocus();
    }
    public void MostrarProducto(){
     ctl.LlenarJtable(tabla,"select * from v_Productos where tipo_prenda like'%"
     +jTextFieldBusqueda.getText()+"%'",9);    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pneinf = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTalla = new javax.swing.JTable();
        panel_cabez = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        tbar = new javax.swing.JToolBar();
        jComboBoxBusqueda = new javax.swing.JComboBox<>();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jTextFieldBusqueda = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btn_busc_todo = new javax.swing.JButton();
        btn_prod = new javax.swing.JButton();
        btn_rfres = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        lblcountProd = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("VISTA PRODUCTOS");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pneinf.setBackground(new java.awt.Color(255, 255, 255));
        pneinf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        pneinf.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 74, 920, 430));

        panel_cabez.setBackground(new java.awt.Color(204, 204, 204));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Listado de Productos");

        javax.swing.GroupLayout panel_cabezLayout = new javax.swing.GroupLayout(panel_cabez);
        panel_cabez.setLayout(panel_cabezLayout);
        panel_cabezLayout.setHorizontalGroup(
            panel_cabezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cabezLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(783, Short.MAX_VALUE))
        );
        panel_cabezLayout.setVerticalGroup(
            panel_cabezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        pneinf.add(panel_cabez, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 30));

        tbar.setRollover(true);

        jComboBoxBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione...", "idarticulo", "tipo_prenda" }));
        tbar.add(jComboBoxBusqueda);
        tbar.add(jSeparator4);

        jTextFieldBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldBusquedaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldBusquedaKeyTyped(evt);
            }
        });
        tbar.add(jTextFieldBusqueda);
        tbar.add(jSeparator1);

        btn_busc_todo.setBackground(new java.awt.Color(51, 153, 255));
        btn_busc_todo.setForeground(new java.awt.Color(255, 255, 255));
        btn_busc_todo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar4.png"))); // NOI18N
        btn_busc_todo.setText("Todo");
        btn_busc_todo.setFocusable(false);
        btn_busc_todo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_busc_todo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_busc_todoActionPerformed(evt);
            }
        });
        tbar.add(btn_busc_todo);

        btn_prod.setBackground(new java.awt.Color(51, 153, 255));
        btn_prod.setForeground(new java.awt.Color(255, 255, 255));
        btn_prod.setText("Productos");
        btn_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_prodActionPerformed(evt);
            }
        });
        tbar.add(btn_prod);

        btn_rfres.setBackground(new java.awt.Color(51, 153, 255));
        btn_rfres.setForeground(new java.awt.Color(255, 255, 255));
        btn_rfres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reiniciar3.png"))); // NOI18N
        btn_rfres.setText("Refres");
        btn_rfres.setFocusable(false);
        btn_rfres.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_rfres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rfresActionPerformed(evt);
            }
        });
        tbar.add(btn_rfres);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Cantidad de Productos:");
        tbar.add(jLabel5);
        tbar.add(jSeparator2);

        lblcountProd.setText("0");
        tbar.add(lblcountProd);
        tbar.add(jSeparator3);

        pneinf.add(tbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 920, 40));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Clic para seleccionar");
        pneinf.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, -1, 40));

        getContentPane().add(pneinf, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 550));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTableTallaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableTallaMouseClicked
        // TODO add your handling code here:
        
        int filas;
        int n = jTableTalla.getSelectedRow();
        frmPrueba.text_cod_pro.setText(jTableTalla.getValueAt(n, 0).toString());
        frmPrueba.text_producto.setText(jTableTalla.getValueAt(n, 1).toString());
        frmPrueba.text_precio.setText(jTableTalla.getValueAt(n, 7).toString());
        frmPrueba.text_stok.setText(jTableTalla.getValueAt(n, 8).toString());
        
//        frmPrueba.text_telf.setText(jTable1.getValueAt(n, 3).toString());
//        frmPrueba.text_Correo.setText(jTable1.getValueAt(n, 4).toString());
//        frmPrueba.text_Direccion.setText(jTable1.getValueAt(n, 5).toString());
        
        filas = n;
        dispose();
    }//GEN-LAST:event_jTableTallaMouseClicked

    private void jTextFieldBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBusquedaKeyReleased
        // TODO add your handling code here:
//        loadTable(rp.busc_cod(text_buscar.getText()));
    }//GEN-LAST:event_jTextFieldBusquedaKeyReleased

    private void btn_busc_todoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_busc_todoActionPerformed
        // TODO add your handling code here:
        buscarTalla();
    }//GEN-LAST:event_btn_busc_todoActionPerformed

    private void btn_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prodActionPerformed
        // TODO add your handling code here:
        new frmArticulo().setVisible(true);
    }//GEN-LAST:event_btn_prodActionPerformed

    private void btn_rfresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rfresActionPerformed
        // TODO add your handling code here:
        reiniciar();
    }//GEN-LAST:event_btn_rfresActionPerformed

    private void jTextFieldBusquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBusquedaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
        }
        MostrarProducto();
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
            java.util.logging.Logger.getLogger(JDialogVerProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogVerProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogVerProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogVerProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogVerProductos dialog = new JDialogVerProductos(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_busc_todo;
    private javax.swing.JButton btn_prod;
    private javax.swing.JButton btn_rfres;
    private javax.swing.JComboBox<String> jComboBoxBusqueda;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JTable jTableTalla;
    private javax.swing.JTextField jTextFieldBusqueda;
    private javax.swing.JLabel lblcountProd;
    private javax.swing.JPanel panel_cabez;
    private javax.swing.JPanel pneinf;
    private javax.swing.JToolBar tbar;
    // End of variables declaration//GEN-END:variables
}
