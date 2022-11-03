/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Formularios;

import Clases.Conexion;
import Clases.limpiar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USUARIO
 */
public class JDialogVer_cliMedidas extends javax.swing.JDialog {
    DefaultTableModel model = new DefaultTableModel();
    Conexion cn = new Conexion();
    limpiar lim = new limpiar();

    /**
     * Creates new form JDialogVer_cliMedidas
     */
    public JDialogVer_cliMedidas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        titulosV();
        cargarPersona();
    }
    public void titulosV() {
        String[] tit = {"CLIENTE MEDIDAS","CODIGO","DNI", "CLIENTE", "TELEFONO","EMAIL","DIRECCION"};
        model.setColumnIdentifiers(tit);
        jTable2.setModel(model);
    }
//    public void limpiarCampos() {
//        jTextField_id_representante.setText("");
//        jTextField_ruc.setText("");
//        jFormattedTextField1.setText("");
//
//    }
     public void cargarPersona() {
        try {
            lim.limpiarTabla(jTable2, model);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = cn.conectar();
            String sql = "select * from v_MedidasCliente";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {130,130, 100, 300, 100,100,200};
            for (int i = 0; i < jTable2.getColumnCount(); i++) {
                jTable2.getColumnModel().getColumn(i).setPreferredWidth(anchosEs[i]);

            }
            while (rs.next()) {
                Object[] filas = new Object[canatidadColumnasEs];
                for (int i = 0; i < canatidadColumnasEs; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                model.addRow(filas);
            }
            int filas = model.getRowCount();
            lbl_cant_cli.setText(String.valueOf(filas));
           
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    public void llenarDa() {
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnel_all = new javax.swing.JPanel();
        pnl_cab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        tbar = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        text_busca_prove = new javax.swing.JTextField();
        btn_all = new javax.swing.JButton();
        btn_cli = new javax.swing.JButton();
        btn_rfres = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        lbl_cant_cli = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnel_all.setBackground(new java.awt.Color(255, 255, 255));
        pnel_all.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_cab.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Listado de Clientes");

        javax.swing.GroupLayout pnl_cabLayout = new javax.swing.GroupLayout(pnl_cab);
        pnl_cab.setLayout(pnl_cabLayout);
        pnl_cabLayout.setHorizontalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(698, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        pnel_all.add(pnl_cab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 30));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable2);

        pnel_all.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 68, 820, 420));

        tbar.setRollover(true);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Cedula/Nombres:");
        tbar.add(jLabel1);
        tbar.add(jSeparator1);

        text_busca_prove.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_busca_proveKeyReleased(evt);
            }
        });
        tbar.add(text_busca_prove);

        btn_all.setText("Ver Todo");
        btn_all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_allActionPerformed(evt);
            }
        });
        tbar.add(btn_all);

        btn_cli.setText("Registrar Cliente");
        btn_cli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cliActionPerformed(evt);
            }
        });
        tbar.add(btn_cli);

        btn_rfres.setText("Recargar");
        btn_rfres.setFocusable(false);
        btn_rfres.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_rfres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rfresActionPerformed(evt);
            }
        });
        tbar.add(btn_rfres);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Cantidad de Clientes:");
        tbar.add(jLabel5);
        tbar.add(jSeparator2);

        lbl_cant_cli.setText("0");
        tbar.add(lbl_cant_cli);
        tbar.add(jSeparator3);

        pnel_all.add(tbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 820, 40));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Doble clic para seleccionar");
        pnel_all.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, -1, 20));

        getContentPane().add(pnel_all, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 520));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        int filas;
        int n = jTable2.getSelectedRow();
        frmVenta_Confec.text_Medidas.setText(jTable2.getValueAt(n, 0).toString());
        frmVenta_Confec.text_cod.setText(jTable2.getValueAt(n, 1).toString());
        frmVenta_Confec.text_dni.setText(jTable2.getValueAt(n, 2).toString());
        frmVenta_Confec.text_cliente.setText(jTable2.getValueAt(n, 3).toString());
        frmVenta_Confec.text_telf.setText(jTable2.getValueAt(n, 4).toString());
        frmVenta_Confec.text_Correo.setText(jTable2.getValueAt(n, 5).toString());
        frmVenta_Confec.text_Direccion.setText(jTable2.getValueAt(n, 6).toString());

        filas = n;

    }//GEN-LAST:event_jTable2MouseClicked

    private void text_busca_proveKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_busca_proveKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_text_busca_proveKeyReleased

    private void btn_allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_allActionPerformed

    }//GEN-LAST:event_btn_allActionPerformed

    private void btn_cliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cliActionPerformed

    }//GEN-LAST:event_btn_cliActionPerformed

    private void btn_rfresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rfresActionPerformed

    }//GEN-LAST:event_btn_rfresActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogVer_cliMedidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogVer_cliMedidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogVer_cliMedidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogVer_cliMedidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogVer_cliMedidas dialog = new JDialogVer_cliMedidas(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_all;
    private javax.swing.JButton btn_cli;
    private javax.swing.JButton btn_rfres;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lbl_cant_cli;
    private javax.swing.JPanel pnel_all;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JToolBar tbar;
    private javax.swing.JTextField text_busca_prove;
    // End of variables declaration//GEN-END:variables
}
