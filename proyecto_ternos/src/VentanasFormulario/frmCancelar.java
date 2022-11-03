/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VentanasFormulario;
import Clases.Conexion;
import Clases.limpiar;

import Clases2.Controlador;
import Formularios.JDialogVer_VentaConfec;
import Formularios.frmPersona;
import Formularios.frmPrueba;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
 * @author PERSONAL
 */
public class frmCancelar extends javax.swing.JFrame {

    /**
     * Creates new form frmCancelar
     */
     Conexion cn = new Conexion();
     Controlador control=new Controlador();
    Controlador ctl=new Controlador();
     DefaultTableModel tabla = new DefaultTableModel();
    public frmCancelar() {
        initComponents();
        nombresTitulos2();
    }
    public void nombresTitulos2() {
        String[] tit = {"MODELO TELA", "MARCA","CODIGO","ESTADO","CONFECCIONADOR"};
        tabla.setColumnIdentifiers(tit);
        jTable1.setModel(tabla);
    }
     public void  limpiarTabla(JTable tb, DefaultTableModel md){
        while(tb.getRowCount()>0){
            md.removeRow(0);
        }
    }
    public void verRecibo(){
        String cant=control.DevolverRegistroDato(" select CHARACTER_LENGTH('"+jTextFieldRecibo.getText()+"');", 1);
        String cli=control.DevolverRegistroDato("SELECT c.id_cliente FROM cliente c WHERE c.fk_id_persona='"+jTextFieldRecibo.getText()+"';", 1);
        if(control.VerificaConsulta("select fk_idComprobante from venta_confec WHERE idventa_confec='"+jTextFieldRecibo.getText()+"'" )|| control.VerificaConsulta("SELECT c.idComprobante from comprobante c WHERE c.fk_id_cliente='"+cli+"';")){
            if("8".equals(cant)){
                String com=control.DevolverRegistroDato("SELECT c.idComprobante from comprobante c WHERE c.fk_id_cliente='"+cli+"';", 1);
                jTextFieldAdelanto.setText(control.DevolverRegistroDato("select adelanto from comprobante WHERE idComprobante='"+com+"';", 1));
                jTextFieldDebe.setText(control.DevolverRegistroDato("select debe from comprobante WHERE idComprobante='"+com+"';", 1));
                jTextFieldCliente.setText(control.DevolverRegistroDato("SELECT concat_ws(' ',p.nombre,p.apellido_p,p.apellido_m) FROM persona p WHERE p.DNI='"+jTextFieldRecibo.getText()+"'", 1));
                String aux="B000"+com;
                String aux2=control.DevolverRegistroDato("SELECT v.idventa_confec from venta_confec v WHERE v.fk_idComprobante='"+com+"'", 1);
                String aux3=control.DevolverRegistroDato("SELECT e.fechestimada FROM esconfec e WHERE e.fk_idventa_confec='"+aux2+"'", 1);
                jTextFieldFechaEmision.setText(control.DevolverRegistroDato("SELECT b.fecha_bol FROM boleta b WHERE b.Num_boleta='"+aux+"'", 1));
                jTextFieldFechaEntrega.setText(aux3);
                try {
                String com2=control.DevolverRegistroDato("SELECT c.idComprobante from comprobante c WHERE c.fk_id_cliente='"+cli+"';", 1);
                PreparedStatement ps=null;
                ResultSet rs=null;
                Connection con=cn.conectar();


                limpiarTabla(jTable1, tabla);

                String sql="call p_per (?);";
                //p_Verped
                ps=con.prepareStatement(sql);
                ps.setString(1,""+com2);
                rs=ps.executeQuery();
                ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
                int cantidadcolumnas=rsMd.getColumnCount();
                int [] anchos={200,150,100,180,280};
                for(int i=0;i<jTable1.getColumnCount();i++){
                    jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
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
            else{
                String reg=control.DevolverRegistroDato("select fk_idComprobante from venta_confec WHERE idventa_confec='"+jTextFieldRecibo.getText()+"' or fk_idCleinteM='"+jTextFieldRecibo.getText()+"';",1);
                jTextFieldAdelanto.setText(control.DevolverRegistroDato("select adelanto from comprobante WHERE idComprobante='"+reg+"';", 1));
                jTextFieldDebe.setText(control.DevolverRegistroDato("select debe from comprobante WHERE idComprobante='"+reg+"';", 1));
                String cli2=control.DevolverRegistroDato("SELECT c.fk_id_cliente from comprobante c WHERE c.idComprobante='"+reg+"'", 1);
                String pe=control.DevolverRegistroDato("SELECT c.fk_id_persona FROM cliente c WHERE c.id_cliente='"+cli2+"'", 1);
                String per=control.DevolverRegistroDato("SELECT concat_ws(' ',p.nombre,p.apellido_p,p.apellido_m) FROM persona p WHERE p.id_persona='"+pe+"'", 1);
                jTextFieldCliente.setText(per);
                String aux="B000"+reg;
                
                String aux3=control.DevolverRegistroDato("SELECT e.fechestimada FROM esconfec e WHERE e.fk_idventa_confec='"+jTextFieldRecibo.getText()+"'", 1);
                jTextFieldFechaEmision.setText(control.DevolverRegistroDato("SELECT b.fecha_bol FROM boleta b WHERE b.Num_boleta='"+aux+"'", 1));
                jTextFieldFechaEntrega.setText(aux3);
                try {
                
                PreparedStatement ps=null;
                ResultSet rs=null;
                Connection con=cn.conectar();


                limpiarTabla(jTable1, tabla);

                String sql="call p_per (?);";
                //p_Verped
                ps=con.prepareStatement(sql);
                ps.setString(1,""+reg);
                rs=ps.executeQuery();
                ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
                int cantidadcolumnas=rsMd.getColumnCount();
                int [] anchos={200,150,100,180,280};
                for(int i=0;i<jTable1.getColumnCount();i++){
                    jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
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
            
            
        }
//        else{
//            JOptionPane.showMessageDialog(null, "N° ficha o DNI incorrecto","AVISO", JOptionPane.INFORMATION_MESSAGE, null);
//        }
    }
    
    public void guardar(){
        double debe=Double.parseDouble(jTextFieldDebe.getText());
        double pago=Double.parseDouble(jTextFieldPago.getText());
        String reg=control.DevolverRegistroDato("select fk_idComprobante from venta_confec WHERE idventa_confec='"+jTextFieldRecibo.getText()+"';",1);
        if(debe==pago){
            ctl.ActualizarRegistro("update comprobante set restante='"+jTextFieldPago.getText()
                    +"' where idComprobante='"+reg+"'");
            
            String bol=control.DevolverRegistroDato("SELECT b.Num_boleta FROM venta_confec vc "
                    + "INNER JOIN comprobante c on vc.fk_idComprobante=c.idComprobante "
                    + "INNER JOIN boleta b on c.fk_Num_boleta=b.Num_boleta "
                    + "WHERE c.idComprobante='"+reg+"';", 1);
            String fact=control.DevolverRegistroDato("SELECT f.Num_factura FROM venta_confec vc "
                    + "INNER JOIN comprobante c on vc.fk_idComprobante=c.idComprobante "
                    + "INNER JOIN factura f on c.fk_Num_factura=f.Num_factura "
                    + "WHERE c.idComprobante='"+reg+"';", 1);
            
            if("F".equals(fact)){
                reportedBoleta();
            }
            else if("B".equals(bol)){
                reportedFactura();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "El pago debe ser igual", "Advertencia", JOptionPane.WARNING_MESSAGE, null);
        }
    }
    public void reportedBoleta(){
        try {
            Conexion conn=new Conexion();
            Connection con=conn.conectar();
            JasperReport reporte=null;
            String path="src\\Reportes\\rBoletaConfec.jasper";
            Map parametro=new HashMap();  
            String reg=control.DevolverRegistroDato("select fk_idComprobante from venta_confec WHERE idventa_confec='"+jTextFieldRecibo.getText()+"';",1);
            int bol=Integer.parseInt(reg);
            parametro.put("p_bo", bol);
            reporte=(JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint=JasperFillManager.fillReport(reporte,parametro,con);
            JasperViewer view=new JasperViewer(jprint,false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            
        } catch (Exception e) {
            Logger.getLogger(JDialogVer_VentaConfec.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public void reportedFactura(){
        try {
            Conexion conn=new Conexion();
            Connection con=conn.conectar();
            JasperReport reporte=null;
            String path="src\\Reportes\\rFacturaConfec.jasper";
            Map parametro=new HashMap(); 
            String reg=control.DevolverRegistroDato("select fk_idComprobante from venta_confec WHERE idventa_confec='"+jTextFieldRecibo.getText()+"';",1);
            parametro.put("p_fc", reg);
            reporte=(JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint=JasperFillManager.fillReport(reporte,parametro,con);
            JasperViewer view=new JasperViewer(jprint,false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            
        } catch (Exception e) {
            Logger.getLogger(JDialogVer_VentaConfec.class.getName()).log(Level.SEVERE, null, e);
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
        jLabel4 = new javax.swing.JLabel();
        jTextFieldPago = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldDebe = new javax.swing.JTextField();
        jTextFieldAdelanto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldRecibo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldCliente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldFechaEmision = new javax.swing.JTextField();
        jTextFieldFechaEntrega = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cancelar");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Pago");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, -1));

        jTextFieldPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPagoKeyTyped(evt);
            }
        });
        jPanel2.add(jTextFieldPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 90, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Debe");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        jTextFieldDebe.setEnabled(false);
        jPanel2.add(jTextFieldDebe, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 90, -1));

        jTextFieldAdelanto.setEnabled(false);
        jPanel2.add(jTextFieldAdelanto, new org.netbeans.lib.awtextra.AbsoluteConstraints(101, 91, 90, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Adelanto");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 100, -1, -1));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("N° Ficha");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 42, -1, -1));

        jTextFieldRecibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldReciboActionPerformed(evt);
            }
        });
        jTextFieldRecibo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldReciboKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldReciboKeyTyped(evt);
            }
        });
        jPanel2.add(jTextFieldRecibo, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 33, 90, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo1.png"))); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 200, 160));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton1.setBackground(new java.awt.Color(204, 102, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sueldo.png"))); // NOI18N
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jButton1)
                .addContainerGap(126, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jButton1)
                .addContainerGap(30, Short.MAX_VALUE))
        );

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
        jScrollPane1.setViewportView(jTable1);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Cliente");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));
        jPanel4.add(jTextFieldCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 320, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Fecha Emision");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Fecha Entrega");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));
        jPanel4.add(jTextFieldFechaEmision, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 190, -1));
        jPanel4.add(jTextFieldFechaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, 190, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(310, 310, 310)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 740));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldReciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldReciboActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextFieldReciboActionPerformed

    private void jTextFieldReciboKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldReciboKeyTyped
        // TODO add your handling code here:
         
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
            verRecibo();
        }
       
    }//GEN-LAST:event_jTextFieldReciboKeyTyped

    private void jTextFieldReciboKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldReciboKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            verRecibo();
        }
    }//GEN-LAST:event_jTextFieldReciboKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int filas;
        int n = jTable1.getSelectedRow();
        String condi="";
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            
                    condi=jTable1.getValueAt(i, 3).toString();
            
            System.out.println();
            PreparedStatement ps=null;
        }
        if("EN PROCESO".equals(condi)){
             JOptionPane.showMessageDialog(null, "El trabajo no esta FINALIZADO", "AVISO", JOptionPane.WARNING_MESSAGE, null);
        }
        else{
           
            if("".equals(jTextFieldAdelanto.getText())||"".equals(jTextFieldPago.getText())){
            if("".equals(jTextFieldAdelanto.getText())&&"".equals(jTextFieldPago.getText())){
                JOptionPane.showMessageDialog(null, "Ingrese el Numero de ficha y el Pago", "Advertencia", JOptionPane.WARNING_MESSAGE, null);
            }
            else if("".equals(jTextFieldPago.getText())){
                JOptionPane.showMessageDialog(null, "Ingrese el Pago", "Advertencia", JOptionPane.WARNING_MESSAGE, null);
            }
            else if("".equals(jTextFieldAdelanto.getText())){
                JOptionPane.showMessageDialog(null, "Ingrese el Numero de Ficha", "Advertencia", JOptionPane.WARNING_MESSAGE, null);
            }
            }
            else{
                guardar();
                System.out.println("HOLA");
                jTextFieldAdelanto.setText("");
                jTextFieldDebe.setText("");
                jTextFieldPago.setText("");
                jTextFieldRecibo.setText("");
            }
        }
        filas = n;
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextFieldPagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPagoKeyTyped
        // TODO add your handling code here:
         char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldPagoKeyTyped

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
            java.util.logging.Logger.getLogger(frmCancelar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCancelar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCancelar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCancelar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCancelar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldAdelanto;
    private javax.swing.JTextField jTextFieldCliente;
    private javax.swing.JTextField jTextFieldDebe;
    private javax.swing.JTextField jTextFieldFechaEmision;
    private javax.swing.JTextField jTextFieldFechaEntrega;
    private javax.swing.JTextField jTextFieldPago;
    private javax.swing.JTextField jTextFieldRecibo;
    // End of variables declaration//GEN-END:variables
}
