/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuarios;

import Clases.Conexion;
import Clases2.Controlador;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import principal.MenuEmpleado;
import principal.Principal;
import VentanasFormulario.frmEnviarRespuesta;
import VentanasFormulario.frmPrin;
import principal.MenuConfeccionador;




/**
 *
 * @author Yimi
 */
public class Login extends javax.swing.JFrame {
    Conexion cn=new Conexion();
    Connection cone=cn.conectar();
     Controlador control=new Controlador();
    /**
     * Creates new form Login
     */
     String pass;
     String usuario;
    public Login() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/persona.png")).getImage());
    }
    public void login(){
        int resultado=1;
        String pass=String.valueOf(jPasswordField1.getPassword());
        String usuario=jTextField1.getText();
        String sql="select * from usuario where nombre='"+usuario+"' and password='"+pass+"' ";
        try {
            Statement st=cone.createStatement();
            ResultSet rs=st.executeQuery(sql);
            if(rs.next()){
                resultado=1;
                if(resultado==1){
                    principal.Principal frm = new Principal();
                    frm.setVisible(true);                  
                    this.dispose();

                }
            }else{
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas. Vuelve a intentar de nuevo.");
            }
        } catch (Exception e) {
            System.err.print(e.toString());
            JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado.\nFavor comunicarse con el administrador.");
        }
    }
    public void login2(){
        int resultado=1;
        String pass=String.valueOf(jPasswordField1.getPassword());
        String usuario=jTextField1.getText();
        String sql="select * from usuario where nombre='"+usuario+"' and password='"+pass+"' ";
        
        
        try {
            Statement st=cone.createStatement();
            ResultSet rs=st.executeQuery(sql);
            if(rs.next()){
                resultado=1;
                if(resultado==1){
                    principal.MenuEmpleado fr = new MenuEmpleado();
                    fr.setVisible(true);                  
                    this.dispose();

                }
            }else{
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas. Vuelve a intentar de nuevo.");
            }
        } catch (Exception e) {
            System.err.print(e.toString());
            JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado.\nFavor comunicarse con el administrador.");
        }
    }
    public void prueba(){
        pass=String.valueOf(jPasswordField1.getPassword());
        usuario=jTextField1.getText();
        
        
        if(control.VerificaConsulta("select * from usuario where nombre='"+usuario+"' and password='"+pass+"' ")){
           String sql="select * from usuario where nombre='"+usuario+"' and password='"+pass+"' ";
            String con=control.DevolverRegistroDato("SELECT u.idusuario from usuario u WHERE u.nombre='"+usuario+"' and u.password='"+pass+"';", 1);
            int con1=Integer.parseInt(con);
            int ID=Integer.parseInt(control.DevolverRegistroDato("CALL p_confeccionador1("+con1+");", 1));
            String nombre=control.DevolverRegistroDato("CALL p_confeccionador2("+con1+");", 1);
            String idper=control.DevolverRegistroDato("SELECT u.fk_id_persona FROM usuario u WHERE u.idusuario="+con1, 1);
            String nombre2=control.DevolverRegistroDato("SELECT p.nombre FROM persona p WHERE p.id_persona='"+idper+"'", 1);
            String ape=control.DevolverRegistroDato("SELECT concat_ws(' ',p.apellido_p,p.apellido_m) FROM persona p WHERE p.id_persona='"+idper+"'", 1);
            principal.MenuConfeccionador ff=new MenuConfeccionador();
            ff.setVisible(true);
            VentanasFormulario.frmPrin fr=new frmPrin();
            //fr.setVisible(true);
            frmPrin.jTextField1.setText(""+nombre);
            frmPrin.jTextField2.setText(""+ID);
            MenuConfeccionador.jLabelNombre.setText(""+nombre2);
            MenuConfeccionador.jLabelApe.setText(ape);
            MenuConfeccionador.jLabelIDPer.setText(""+ID);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas. Vuelve a intentar de nuevo.");
        }
        
    }
    public void login3(){
        int resultado=1;
        String pass=String.valueOf(jPasswordField1.getPassword());
        String usuario=jTextField1.getText();
        String sql="select * from usuario where nombre='"+usuario+"' and password='"+pass+"' ";
        String con=control.DevolverRegistroDato("SELECT u.idusuario from usuario u WHERE u.nombre='"+usuario+"' and u.password='"+pass+"';", 1);
        int con1=Integer.parseInt(con);
        int ID=Integer.parseInt(control.DevolverRegistroDato("CALL p_confeccionador1("+con1+");", 1));
        String nombre=control.DevolverRegistroDato("CALL p_confeccionador2("+con1+");", 1);
        
        try {
            Statement st=cone.createStatement();
            ResultSet rs=st.executeQuery(sql);
            if(rs.next()){
                resultado=1;
                if(resultado==1){
                    frmEnviarRespuesta frm=new frmEnviarRespuesta();
                    frm.setVisible(true);
//                    frmEnviarRespuesta.jLabelNombreConfec.setText(""+nombre);
//                    frmEnviarRespuesta.jLabelIDcon.setText(""+ID);
                    this.dispose();
                    
                }
            }else{
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas. Vuelve a intentar de nuevo.");
            }
        } catch (Exception e) {
            System.err.print(e.toString());
            JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado.\nFavor comunicarse con el administrador.");
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
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("INICIO DE SESION");
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginIma/logoTrabajoFinal.png"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 290, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Usuario ");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Contraseña");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        jTextField1.setBackground(new java.awt.Color(255, 153, 0));
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 280, -1));

        jPasswordField1.setBackground(new java.awt.Color(255, 153, 0));
        jPanel2.add(jPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 280, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginIma/user.png"))); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, -1, -1));

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setForeground(new java.awt.Color(0, 153, 153));

        jLabel6.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel6.setText("BAZAR Y CONFECCIONES Y&L");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginIma/tie.png"))); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginIma/wedding-suit (1).png"))); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginIma/corbata.png"))); // NOI18N

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginIma/reloj.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(256, 256, 256))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8))
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addGap(12, 12, 12))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton1.setText("Acceder");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 100, 30));

        jComboBox1.setBackground(new java.awt.Color(255, 153, 0));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione...", "Gerente", "Empleado", "Confeccionador" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 170, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 330, 330));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginIma/Frame 6.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 600, 400));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (jComboBox1.getSelectedIndex()==0 ){
            JOptionPane.showMessageDialog(null, "Selecione su rol");

        }
        else if (jComboBox1.getSelectedIndex()==1){
            login();
        }else if(jComboBox1.getSelectedIndex()==2){
            login2();
            
        }
        else if(jComboBox1.getSelectedIndex()==3){
            prueba();    
        }
        else{
            JOptionPane.showMessageDialog(null, "Selecione su rol");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jPasswordField1.requestFocus();
        }
    }//GEN-LAST:event_jTextField1KeyPressed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
