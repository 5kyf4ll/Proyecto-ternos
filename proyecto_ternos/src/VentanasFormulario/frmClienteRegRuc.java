/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VentanasFormulario;
import Clases.Conexion;
import Clases.limpiar;

import Clases2.Controlador;
import Formularios.frmPrueba;
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
public class frmClienteRegRuc extends javax.swing.JFrame {

    /**
     * Creates new form frmClienteRegRuc
     */
    DefaultTableModel tabla = new DefaultTableModel();
    Conexion cn = new Conexion();
    limpiar lim = new limpiar();
    Controlador control=new Controlador();
    Controlador ctl=new Controlador();
     String idFacultad="";
    public frmClienteRegRuc() {
        initComponents();
        nombresTitulosRuc();
        cargarDatosRuc();
        jLabel13.setVisible(false);
    }
    public void nombresTitulosRuc() {
        String[] tit = {"RUC", "RAZON SOCIAL", "DIRECCION"};
        tabla.setColumnIdentifiers(tit);
        jTable1.setModel(tabla);
        jFormattedRUC1.requestFocus();
    }
    public void limpiarCampos(){
        jFormattedRUC1.setText("");
        jTextField_rSocial1.setText("");
        jTextField_direccion.setText("");
    }
    public void reinicioDatos() {
        lim.limpiarTabla(jTable1, tabla);
        cargarDatosRuc();
        limpiarCampos();
        jFormattedRUC1.requestFocus();
    }
    public void cargarDatosRuc() {
         
        try {
            lim.limpiarTabla(jTable1, tabla);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con=cn.conectar();
            String sql = "select * from v_verClienteRuc";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {100, 200, 200};
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
    public void guardarPersonaRuc(){
        if(idFacultad.length()>0){
                 JOptionPane.showMessageDialog(null, "Estamos en modo Modificar");
                 return;
             }
          if(jTextField_direccion.getText().length()==0 || jTextField_rSocial1.getText().length()==0 
                  || jFormattedRUC1.getText().length()==0){
           JOptionPane.showMessageDialog(null, "Faltan Datos");
           jFormattedRUC1.grabFocus();
          }
          else{
           if(ctl.VerificaConsulta("select * from persona where ruc='"
           +jFormattedRUC1.getText()+"'")){
            JOptionPane.showMessageDialog(null, "El ruc "+jFormattedRUC1.getText()+" Ya Existe");
           }   
           else{
//            int num=Integer.parseInt(jFormattedRUC1.getText());
//            int aux=num/100000;
//            String aux1=""+aux;
//               System.out.println(aux1);
//            ctl.ActualizarRegistro("insert into persona(id_persona,ruc,razon,direccion,fk_idtipo_persona) values('"
//            +aux1+"','"+jFormattedRUC1.getText()+"','"+jTextField_rSocial1.getText()+"','"
//                   +jTextField_direccion.getText()
//                    +"','2')");
//            
//            String msg=control.DevolverRegistroDato("SELECT p.id_persona from persona p where p.ruc='"+jFormattedRUC1.getText()+"';", 1);
//            ctl.ActualizarRegistro("insert into cliente(fk_id_persona) values('"+msg+"')");
//            
//            limpiarCampos();
                PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into persona(id_persona,ruc,razon,direccion,fk_idtipo_persona)values (?,?,?,?,?);");
            
            String cod=control.DevolverRegistroDato("SELECT SUBSTRING('"+jFormattedRUC1.getText()+"', -5);", 1);
            
            ps.setString(1, cod);
            ps.setString(2, jFormattedRUC1.getText());
            ps.setString(3, jTextField_rSocial1.getText());
            ps.setString(4, jTextField_direccion.getText());
            ps.setString(5, "2");

            ps.execute();
              String msg=control.DevolverRegistroDato("SELECT p.id_persona from persona p where p.ruc='"+jFormattedRUC1.getText()+"';", 1);
            ctl.ActualizarRegistro("insert into cliente(fk_id_persona) values('"+msg+"')");
            
            String aux2=control.DevolverRegistroDato("Select id_persona from persona where ruc='"+jFormattedRUC1.getText()+"'", 1);
            String aux=control.DevolverRegistroDato("Select id_Cliente from cliente where fk_id_Persona='"+aux2+"';", 1);
            frmPrueba.text_cod.setText(""+aux);
            frmPrueba.text_dni.setText(""+jFormattedRUC1.getText());
            frmPrueba.text_cliente.setText(jTextField_rSocial1.getText());
            frmPrueba.text_Direccion.setText(jTextField_direccion.getText());
            limpiarCampos();

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
           }
           cargarDatosRuc();
          }
    }
    public void mostrardatoRuc(){
        String cant=control.DevolverRegistroDato(" select CHARACTER_LENGTH('"+jFormattedRUC1.getText()+"');", 1);
        if(control.VerificaConsulta("SELECT * from v_ruc where ruc='"+jFormattedRUC1.getText()+"'") ){
            jTextField_rSocial1.setText(control.DevolverRegistroDato("SELECT p.razon from persona p where p.ruc='"+jFormattedRUC1.getText()+"';", 1));
            jTextField_direccion.setText(control.DevolverRegistroDato("SELECT p.direccion from persona p where p.ruc='"+jFormattedRUC1.getText()+"';", 1));
        
        }
        
        else if("11".equals(cant)){
            jLabel13.setVisible(true);
            
        }
        else{
            jLabel13.setVisible(true);
        }
        System.out.println(""+jFormattedRUC1.getText());
        System.out.println(cant);
    
    }
    
    public void MostrarCliente(){
     ctl.LlenarJtable(tabla,"select * from v_verClienteRuc where ruc like'%"
     +jTextField_buscar.getText()+"%'",3);    
    }
    public void actualizarDatos() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("update persona set ruc=?,razon=?,direccion=?,fk_idtipo_persona=?  where id_persona=?;");
            String ca=control.DevolverRegistroDato("select fk_idtipo_persona from persona WHERE ruc='"+jFormattedRUC1.getText()+"';", 1);
            String id=control.DevolverRegistroDato("select id_persona from persona WHERE ruc='"+jFormattedRUC1.getText()+"';", 1);
            ps.setString(1, jFormattedRUC1.getText());
            ps.setString(2, jTextField_rSocial1.getText());
            ps.setString(3, jTextField_direccion.getText());
            ps.setString(4, ca);
            ps.setString(5, id);

            ps.execute();
            //JOptionPane.showMessageDialog(null, "Se actualizo con éxito ");
            limpiarCampos();
            cargarDatosRuc();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar");
            System.out.println(e.toString());
        }
    }
    public void llenarDatos() {
        int filas;
        int n = jTable1.getSelectedRow();
        jFormattedRUC1.setText(jTable1.getValueAt(n, 0).toString());
        jTextField_rSocial1.setText(jTable1.getValueAt(n, 1).toString());
        jTextField_direccion.setText(jTable1.getValueAt(n, 2).toString());
        filas = n;
    }
    public void Eliminar(){
     
      if(JOptionPane.showConfirmDialog(null,"Deseas eliminar la persona "+jTable1.getValueAt(jTable1.getSelectedRow(),1).toString(),"Confirmar",0)==0){
          int fila = jTable1.getSelectedRow();
        String codigo = jTable1.getValueAt(fila, 0).toString();
        String id=control.DevolverRegistroDato("SELECT id_persona FROM persona where ruc='"+codigo+"';", 1);
        String idClien=control.DevolverRegistroDato("SELECT id_cliente FROM cliente where fk_id_persona='"+id+"';", 1);  
        ctl.ActualizarRegistro("delete from cliente where id_cliente='"+idClien+"'");
        ctl.ActualizarRegistro("delete from persona where id_persona='"+id+"'");
        idFacultad="";
       
       cargarDatosRuc(); 
       limpiarCampos();
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

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField_rSocial1 = new javax.swing.JTextField();
        jTextField_direccion = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jFormattedRUC1 = new javax.swing.JFormattedTextField();
        lblcab = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pnel_inf1 = new javax.swing.JPanel();
        jTextField_buscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
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
        setTitle("Registro de clientes");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("RUC:");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Razon Social:");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 48, -1, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Direccion:");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 78, -1, -1));

        jTextField_rSocial1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_rSocial1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_rSocial1KeyTyped(evt);
            }
        });
        jPanel4.add(jTextField_rSocial1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 340, -1));

        jTextField_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_direccionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_direccionKeyTyped(evt);
            }
        });
        jPanel4.add(jTextField_direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 73, 400, -1));

        jButton7.setBackground(new java.awt.Color(51, 153, 255));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/actualizar (2).png"))); // NOI18N
        jButton7.setText("Actualizar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, -1, -1));

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
        jPanel4.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 51));
        jLabel13.setText("RUC no encontrada");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 160, -1));

        try {
            jFormattedRUC1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedRUC1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedRUC1KeyPressed(evt);
            }
        });
        jPanel4.add(jFormattedRUC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 160, -1));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 580, 180));

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

        jPanel3.add(lblcab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 30));

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

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 760, 220));

        pnel_inf1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204), 2));
        pnel_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_buscarKeyTyped(evt);
            }
        });
        pnel_inf1.add(jTextField_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 300, -1));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("RUC");
        pnel_inf1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jPanel3.add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 410, 50));

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

        jPanel3.add(jToolBar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 530, -1));

        jButton5.setBackground(new java.awt.Color(51, 51, 255));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/buscar (1).png"))); // NOI18N
        jButton5.setText("Buscar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, -1, 50));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icono-clientes.png"))); // NOI18N
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 70, 190, 180));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 570));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_rSocial1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_rSocial1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_direccion.requestFocus();
        }
    }//GEN-LAST:event_jTextField_rSocial1KeyPressed

    private void jTextField_rSocial1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_rSocial1KeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);

        }
    }//GEN-LAST:event_jTextField_rSocial1KeyTyped

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
        actualizarDatos();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        guardarPersonaRuc();
        dispose();
        //dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        llenarDatos();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Eliminar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        //buscarDatos();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jFormattedRUC1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedRUC1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            mostrardatoRuc();
        }
    }//GEN-LAST:event_jFormattedRUC1KeyPressed

    private void jTextField_buscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_buscarKeyTyped
        // TODO add your handling code here:
        MostrarCliente();
    }//GEN-LAST:event_jTextField_buscarKeyTyped

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
            java.util.logging.Logger.getLogger(frmClienteRegRuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmClienteRegRuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmClienteRegRuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmClienteRegRuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmClienteRegRuc().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    public static javax.swing.JFormattedTextField jFormattedRUC1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField_buscar;
    private javax.swing.JTextField jTextField_direccion;
    private javax.swing.JTextField jTextField_rSocial1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPanel lblcab;
    private javax.swing.JLabel lblcountipRop;
    private javax.swing.JPanel pnel_inf1;
    // End of variables declaration//GEN-END:variables
}
