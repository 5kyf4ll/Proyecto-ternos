/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VentanasFormulario;
import Clases.Conexion;
import Clases.limpiar;
import Clases2.Controlador;
import static Formularios.frmPersona.jFormattedDni;
import Formularios.frmPrueba;
import static VentanasFormulario.frmRUC.jFormattedRUC;
import VentanasFormulario.frmRegistroCliente;
import static VentanasFormulario.frmRegistroCliente.jFormattedTextFieldRuc;
import static VentanasFormulario.frmRegistroCliente.jTextFieldDirec;
import static VentanasFormulario.frmRegistroCliente.jTextFieldSocial;
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
public class frmMedidadRuc extends javax.swing.JFrame {

    /**
     * Creates new form frmMedidadRuc
     */
    DefaultTableModel tabla = new DefaultTableModel();
    Conexion cn = new Conexion();
    limpiar lim = new limpiar();
    Controlador control=new Controlador();
    Controlador ctl=new Controlador();
    public frmMedidadRuc() {
        initComponents();
        nombresTitulos();
        cargarDatos();
    }
    public void nombresTitulos() {
        String[] tit = {"RUC", "RAZON SOCIAL", "DIRECCION","TIPO PERSONA"};
        tabla.setColumnIdentifiers(tit);
        jTable1.setModel(tabla);
        jFormattedTextFieldRuc.requestFocus();
    }

    public void limpiarCampos() {
        jFormattedTextFieldRuc.setText("");
        jTextField_rSocial.setText("");
        jTextField_direccion.setText("");
        jTextField_fk_id_tipo_persona.setText("");
    }

    public void llenarDatos() {
        int filas;
        int n = jTable1.getSelectedRow();
        jFormattedTextFieldRuc.setText(jTable1.getValueAt(n, 1).toString());
        jTextField_rSocial.setText(jTable1.getValueAt(n, 2).toString());
        jTextField_direccion.setText(jTable1.getValueAt(n, 3).toString());
        jTextField_fk_id_tipo_persona.setText(jTable1.getValueAt(n, 4).toString());

        filas = n;
    }

    public void reinicioDatos() {
        lim.limpiarTabla(jTable1, tabla);
        cargarDatos();
        limpiarCampos();
        jFormattedTextFieldRuc.requestFocus();
    }

    /*                                Cargar Datos
    ============================================================================
     */
    public void cargarDatos() {
        try {
            lim.limpiarTabla(jTable1, tabla);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = cn.conectar();
            String sql = "select * from v_ruc";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {200, 200, 200, 250};
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
            int filas = tabla.getRowCount();
            lblcountipRop.setText(String.valueOf(filas));
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    
    public void guardarDatos() {
        PreparedStatement ps = null;

        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into persona(ruc,razon,direccion,fk_idtipo_persona)values (?,?,?,?);");
           
          
            ps.setString(1, jFormattedTextFieldRuc.getText());
            ps.setString(2, jTextField_rSocial.getText());
            ps.setString(3, jTextField_direccion.getText());
            ps.setString(4, jTextField_fk_id_tipo_persona.getText());

            ps.execute();
            
            cargarDatos();

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    
    public void guardarDatosRUC() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into cliente(id_cliente,fk_id_persona)values (?,?);");
            
            String msg=control.DevolverRegistroDato("SELECT p.id_persona from persona p where p.ruc='"+jFormattedTextFieldRuc.getText()+"';", 1);
            ps.setString(1, null);
            ps.setString(2, msg);
            ps.execute();
            
            frmRegistroMedidas.jFormattedTextFieldRu.setText(jFormattedTextFieldRuc.getText());
            frmRegistroMedidas.jTextFieldSocial.setText(jTextField_rSocial.getText());
            frmRegistroMedidas.jTextFieldDirec.setText(jTextField_direccion.getText());
            
            
            cargarDatos();

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField_rSocial = new javax.swing.JTextField();
        jTextField_direccion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField_fk_id_tipo_persona = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jFormattedTextFieldRuc = new javax.swing.JFormattedTextField();
        lblcab = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pnel_inf1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField_buscar = new javax.swing.JTextField();
        jToolBar2 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel11 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        lblcountipRop = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jTextField1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("RUC:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Razon Social:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 48, -1, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Direccion:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 78, -1, -1));

        jTextField_rSocial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_rSocialKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_rSocialKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_rSocial, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 340, -1));

        jTextField_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_direccionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_direccionKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 73, 400, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("Tipo Persona :");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));
        jPanel2.add(jTextField_fk_id_tipo_persona, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 80, -1));

        jButton7.setBackground(new java.awt.Color(51, 153, 255));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/actualizar (2).png"))); // NOI18N
        jButton7.setText("Actualizar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, -1, -1));

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
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, -1, -1));

        jButton3.setText("...");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, -1, 20));

        try {
            jFormattedTextFieldRuc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldRuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextFieldRucKeyPressed(evt);
            }
        });
        jPanel2.add(jFormattedTextFieldRuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 190, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 580, 180));

        lblcab.setBackground(new java.awt.Color(204, 204, 204));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("RUC");

        javax.swing.GroupLayout lblcabLayout = new javax.swing.GroupLayout(lblcab);
        lblcab.setLayout(lblcabLayout);
        lblcabLayout.setHorizontalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblcabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addContainerGap(759, Short.MAX_VALUE))
        );
        lblcabLayout.setVerticalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblcabLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(lblcab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 30));

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
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 760, 220));

        pnel_inf1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204), 2));
        pnel_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI", "Nombre", "Apellido Paterno", "Apellido Materno" }));
        jComboBox1.setToolTipText("");
        pnel_inf1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, -1));
        pnel_inf1.add(jTextField_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 300, -1));

        jPanel1.add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 510, 50));

        jToolBar2.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar2.setRollover(true);

        jButton1.setBackground(new java.awt.Color(102, 204, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/eliminar.png"))); // NOI18N
        jButton1.setText("Eliminar");
        jButton1.setFocusable(false);
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
        jToolBar2.add(jSeparator2);

        jLabel11.setText("Cantidad de Registros:");
        jToolBar2.add(jLabel11);
        jToolBar2.add(jSeparator1);

        lblcountipRop.setText("0");
        jToolBar2.add(lblcountipRop);
        jToolBar2.add(jSeparator4);

        jTextField1.setBackground(new java.awt.Color(102, 102, 255));
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setText("Imprimir");
        jToolBar2.add(jTextField1);

        jPanel1.add(jToolBar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 530, -1));

        jButton5.setBackground(new java.awt.Color(51, 51, 255));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/buscar (1).png"))); // NOI18N
        jButton5.setText("Buscar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 270, -1, 50));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icono-clientes.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 70, 190, 180));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 570));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_rSocialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_rSocialKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_direccion.requestFocus();
        }
    }//GEN-LAST:event_jTextField_rSocialKeyPressed

    private void jTextField_rSocialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_rSocialKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);

        }
    }//GEN-LAST:event_jTextField_rSocialKeyTyped

    private void jTextField_direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_direccionKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            // jTextField_apellido_m.requestFocus();
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

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // actualizarDatos();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        guardarDatos();
        guardarDatosRUC();
        
        dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        // new Tipo_personas().setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        //llenarDatos();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //eliminarDatos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        //buscarDatos();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jFormattedTextFieldRucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextFieldRucKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_rSocial.requestFocus();
        }
    }//GEN-LAST:event_jFormattedTextFieldRucKeyPressed

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
            java.util.logging.Logger.getLogger(frmMedidadRuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMedidadRuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMedidadRuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMedidadRuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMedidadRuc().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    public static javax.swing.JFormattedTextField jFormattedTextFieldRuc;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField_buscar;
    private javax.swing.JTextField jTextField_direccion;
    private javax.swing.JTextField jTextField_fk_id_tipo_persona;
    private javax.swing.JTextField jTextField_rSocial;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPanel lblcab;
    private javax.swing.JLabel lblcountipRop;
    private javax.swing.JPanel pnel_inf1;
    // End of variables declaration//GEN-END:variables
}