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
public class frmClienteReg extends javax.swing.JFrame {

    /**
     * Creates new form frmClienteReg
     */
    DefaultTableModel tabla = new DefaultTableModel();
    Conexion cn = new Conexion();
    limpiar lim = new limpiar();
    Controlador control=new Controlador();
    Controlador ctl=new Controlador();
     String idFacultad="";
    public frmClienteReg() {
        initComponents();
        nombresTitulos();
        cargarDatos();
        jLabel13.setVisible(false);
    }
    public void nombresTitulos() {
        String[] tit = {"DNI","Nombre", "Apellido Paterno","Apellido Materno", "Dirección", "Telefono", "Correo Electronico"};
        tabla.setColumnIdentifiers(tit);
        jTableCliente.setModel(tabla);
        jFormattedDNI.requestFocus();
    }
    public void limpiarCampos() {
        jFormattedDNI.setText("");
        jTextField_nombre.setText("");
        jTextField_apellido_p.setText("");
        jTextField_direccion.setText("");
        jFormattedTelefono.setText("");
        jTextField_email.setText("");
        jTextField_apellido_m.setText("");
    }
    public void llenarDatos() {
        int filas;
        int n = jTableCliente.getSelectedRow();
        jFormattedDNI.setText(jTableCliente.getValueAt(n, 0).toString());
        jTextField_nombre.setText(jTableCliente.getValueAt(n, 1).toString());
        jTextField_apellido_p.setText(jTableCliente.getValueAt(n, 2).toString());
        jTextField_apellido_m.setText(jTableCliente.getValueAt(n, 3).toString());
        jTextField_direccion.setText(jTableCliente.getValueAt(n, 4).toString());
        jFormattedTelefono.setText(""+jTableCliente.getValueAt(n, 5).toString());
        jTextField_email.setText(jTableCliente.getValueAt(n, 6).toString());
        

        filas = n;
    }
    public void reinicioDatos() {
        lim.limpiarTabla(jTableCliente, tabla);
        cargarDatos();
        limpiarCampos();
        jFormattedDNI.requestFocus();
    }
     public void cargarDatos() {
         
        try {
            lim.limpiarTabla(jTableCliente, tabla);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con=cn.conectar();
            String sql = "select * from v_verCliente";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {200, 200, 200, 250, 250, 200, 200};
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
            int filas = tabla.getRowCount();
            lblcountipRop.setText(String.valueOf(filas));
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
     
    public void guardarPersona(){
        if(idFacultad.length()>0){
                 JOptionPane.showMessageDialog(null, "Estamos en modo Modificar");
                 return;
             }
          if(jTextField_nombre.getText().length()==0 || jTextField_apellido_m.getText().length()==0 ||jTextField_apellido_p.getText().length()==0 
                  || jFormattedDNI.getText().length()==0){
           JOptionPane.showMessageDialog(null, "Faltan Datos");
           jFormattedDNI.grabFocus();
          }
          else{
           if(ctl.VerificaConsulta("select * from persona where DNI='"
           +jFormattedDNI.getText()+"'")){
            JOptionPane.showMessageDialog(null, "El dni "+jFormattedDNI.getText()+" Ya Existe");
           }   
           else{
            ctl.ActualizarRegistro("insert into persona(id_persona,DNI,nombre,apellido_p,apellido_m,direccion,telefono,email,fk_idtipo_persona) values('"
            +jFormattedDNI.getText()+"','"+jFormattedDNI.getText()+"','"+jTextField_nombre.getText()+"','"+jTextField_apellido_p.getText()+"','"+jTextField_apellido_m.getText()+
                    "','"+jTextField_direccion.getText()+"','"+jFormattedTelefono.getText()+"','"+jTextField_email.getText()
                    +"','1')");
            
            String msg=control.DevolverRegistroDato("SELECT p.id_persona from persona p where p.dni='"+jFormattedDNI.getText()+"';", 1);
            ctl.ActualizarRegistro("insert into cliente(fk_id_persona) values('"+msg+"')");
            String aux2=control.DevolverRegistroDato("Select id_persona from persona where DNI='"+jFormattedDNI.getText()+"'", 1);
            String aux=control.DevolverRegistroDato("Select id_Cliente from cliente where fk_id_Persona='"+aux2+"';", 1);
            frmPrueba.text_cod.setText(""+aux);
            frmPrueba.text_dni.setText(""+jFormattedDNI.getText());
            frmPrueba.text_cliente.setText(jTextField_nombre.getText()+" "+jTextField_apellido_p.getText()+" "+jTextField_apellido_m.getText());
            frmPrueba.text_telf.setText(jFormattedTelefono.getText());
            frmPrueba.text_Correo.setText(jTextField_email.getText());
            frmPrueba.text_Direccion.setText(jTextField_direccion.getText());
            limpiarCampos();
           }
           cargarDatos();
          }
    }
    public void mostrardato(){
        String cant=control.DevolverRegistroDato(" select CHARACTER_LENGTH('"+jFormattedDNI.getText()+"');", 1);
        if(control.VerificaConsulta("SELECT * from v_dni where DNI='"+jFormattedDNI.getText()+"'") ){
            jTextField_nombre.setText(control.DevolverRegistroDato("SELECT p.nombre from persona p where p.dni='"+jFormattedDNI.getText()+"';", 1));
            jTextField_apellido_p.setText(control.DevolverRegistroDato("SELECT p.apellido_p from persona p where p.dni='"+jFormattedDNI.getText()+"';", 1));
            jTextField_apellido_m.setText(control.DevolverRegistroDato("SELECT p.apellido_m from persona p where p.dni='"+jFormattedDNI.getText()+"';", 1));
            jTextField_direccion.setText(control.DevolverRegistroDato("SELECT p.direccion from persona p where p.dni='"+jFormattedDNI.getText()+"';", 1));
            jFormattedTelefono.setText(control.DevolverRegistroDato("SELECT p.telefono from persona p where p.dni='"+jFormattedDNI.getText()+"';", 1));
            jTextField_email.setText(control.DevolverRegistroDato("SELECT p.email from persona p where p.dni='"+jFormattedDNI.getText()+"';", 1));
            
        }
        
        else if("8".equals(cant)){
//            frmPersona frm=new frmPersona();
//            frm.setVisible(true);
//            frmPersona.jFormattedDni.setText(""+jFormattedDni.getText());
            jLabel13.setVisible(true);
            
        }
        else{
            jLabel13.setVisible(true);
        }
        System.out.println(""+jFormattedDNI.getText());
        System.out.println(cant);
    
    }
    public void MostrarCliente(){
     ctl.LlenarJtable(tabla,"select * from v_verCliente where DNI like'%"
     +jTextField_buscar.getText()+"%'",7);    
    }
    
    public void actualizarDatos() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("update persona set DNI=?,nombre=?,apellido_p=?,apellido_m=?,direccion=?,telefono=?,email=?,fk_idtipo_persona=?  where id_persona=?;");
            String ca=control.DevolverRegistroDato("select fk_idtipo_persona from persona WHERE DNI='"+jFormattedDNI.getText()+"';", 1);
            String id=control.DevolverRegistroDato("select id_persona from persona WHERE DNI='"+jFormattedDNI.getText()+"';", 1);
            ps.setString(1, jFormattedDNI.getText());
            ps.setString(2, jTextField_nombre.getText());
            ps.setString(3, jTextField_apellido_p.getText());
            ps.setString(4, jTextField_apellido_m.getText());
            ps.setString(5, jTextField_direccion.getText());
            ps.setString(6, jFormattedTelefono.getText());
            ps.setString(7, jTextField_email.getText());
            ps.setString(8, ca);
            ps.setString(9, id);

            ps.execute();
            //JOptionPane.showMessageDialog(null, "Se actualizo con éxito ");
            limpiarCampos();
            cargarDatos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar");
            System.out.println(e.toString());
        }
    }
    public void llenarDatos2() {
        int filas;
        int n = jTableCliente.getSelectedRow();
        jFormattedDNI.setText(jTableCliente.getValueAt(n, 0).toString());
        jTextField_nombre.setText(jTableCliente.getValueAt(n, 1).toString());
        jTextField_apellido_p.setText(jTableCliente.getValueAt(n, 2).toString());
        jTextField_apellido_m.setText(jTableCliente.getValueAt(n, 3).toString());
        jTextField_direccion.setText(jTableCliente.getValueAt(n, 4).toString());
        jFormattedTelefono.setText(jTableCliente.getValueAt(n, 5).toString());
        jTextField_email.setText(jTableCliente.getValueAt(n, 6).toString());
        

        filas = n;
    }
    
    public void Persona2(){
        String cant=control.DevolverRegistroDato(" select CHARACTER_LENGTH('"+jFormattedDNI.getText()+"');", 1);
        System.out.println(""+cant);
        if("8".equals(cant)){
        
        }
        else if("11".equals(cant)){
            
        }
        else{
            
            limpiarCampos();
            
        }
    }
    
    public void Eliminar(){
     
      if(JOptionPane.showConfirmDialog(null,"Deseas eliminar la persona "+jTableCliente.getValueAt(jTableCliente.getSelectedRow(),1).toString(),"Confirmar",0)==0){
          int fila = jTableCliente.getSelectedRow();
        String codigo = jTableCliente.getValueAt(fila, 0).toString();
        String id=control.DevolverRegistroDato("SELECT id_persona FROM persona where DNI='"+codigo+"';", 1);
        String idClien=control.DevolverRegistroDato("SELECT id_cliente FROM cliente where fk_id_persona='"+id+"';", 1);  
        ctl.ActualizarRegistro("delete from cliente where id_cliente='"+idClien+"'");
        ctl.ActualizarRegistro("delete from persona where id_persona='"+id+"'");
        idFacultad="";
       
       cargarDatos(); 
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

        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblcab = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField_nombre = new javax.swing.JTextField();
        jTextField_apellido_p = new javax.swing.JTextField();
        jTextField_direccion = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField_email = new javax.swing.JTextField();
        jTextField_apellido_m = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jFormattedDNI = new javax.swing.JFormattedTextField();
        jFormattedTelefono = new javax.swing.JFormattedTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        pnel_inf1 = new javax.swing.JPanel();
        jTextField_buscar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCliente = new javax.swing.JTable();

        jLabel8.setText("jLabel8");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro Clientes");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblcab.setBackground(new java.awt.Color(204, 204, 204));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("CLIENTE");

        javax.swing.GroupLayout lblcabLayout = new javax.swing.GroupLayout(lblcab);
        lblcab.setLayout(lblcabLayout);
        lblcabLayout.setHorizontalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblcabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addContainerGap(715, Short.MAX_VALUE))
        );
        lblcabLayout.setVerticalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblcabLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(lblcab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 30));

        jToolBar2.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar2.setRollover(true);

        jButton1.setBackground(new java.awt.Color(102, 204, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/eliminar.png"))); // NOI18N
        jButton1.setText("Eliminar");
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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("DNI :");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Nombre :");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Apellido Paterno :");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Direccion :");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        jTextField_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_nombreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_nombreKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 160, -1));

        jTextField_apellido_p.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_apellido_pKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_apellido_pKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_apellido_p, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 160, -1));

        jTextField_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_direccionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_direccionKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 369, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Telefono :");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Correo :");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));
        jPanel2.add(jTextField_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 300, 250, -1));

        jTextField_apellido_m.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_apellido_mKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_apellido_mKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_apellido_m, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 160, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Apellido Materno:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 51));
        jLabel13.setText("Persona no encontrada");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 160, -1));

        try {
            jFormattedDNI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedDNI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedDNIKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFormattedDNIKeyTyped(evt);
            }
        });
        jPanel2.add(jFormattedDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 160, -1));

        try {
            jFormattedTelefono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTelefonoKeyPressed(evt);
            }
        });
        jPanel2.add(jFormattedTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 160, -1));

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
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 340, -1, -1));

        jButton7.setBackground(new java.awt.Color(51, 153, 255));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/actualizar (2).png"))); // NOI18N
        jButton7.setText("Actualizar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 340, -1, 30));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icono-clientes.png"))); // NOI18N
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, 190, 180));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 740, 390));

        pnel_inf1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204), 2));
        pnel_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_buscarKeyTyped(evt);
            }
        });
        pnel_inf1.add(jTextField_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 300, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("DNI");
        pnel_inf1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 15, -1, 20));

        jPanel1.add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 490, 420, 50));

        jButton5.setBackground(new java.awt.Color(51, 51, 255));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/buscar (1).png"))); // NOI18N
        jButton5.setText("Buscar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 490, 120, 50));

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
        jScrollPane2.setViewportView(jTableCliente);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 560, 700, 250));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 830));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Eliminar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
        jTextField_buscar.setText("");

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_nombreKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_apellido_p.requestFocus();
        }
    }//GEN-LAST:event_jTextField_nombreKeyPressed

    private void jTextField_nombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_nombreKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);

        }
    }//GEN-LAST:event_jTextField_nombreKeyTyped

    private void jTextField_apellido_pKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_apellido_pKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_apellido_m.requestFocus();
        }
    }//GEN-LAST:event_jTextField_apellido_pKeyPressed

    private void jTextField_apellido_pKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_apellido_pKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);

        }
    }//GEN-LAST:event_jTextField_apellido_pKeyTyped

    private void jTextField_direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_direccionKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jFormattedTelefono.requestFocus();
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

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        
        guardarPersona();
        jLabel13.setVisible(false);
        jTextField_buscar.setText("");
        dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        actualizarDatos();
        jTextField_buscar.setText("");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        MostrarCliente();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField_apellido_mKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_apellido_mKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);

        }
    }//GEN-LAST:event_jTextField_apellido_mKeyTyped

    private void jTextField_apellido_mKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_apellido_mKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_direccion.requestFocus();
        }
    }//GEN-LAST:event_jTextField_apellido_mKeyPressed

    private void jTextField_buscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_buscarKeyTyped
        // TODO add your handling code here:
        MostrarCliente();
    }//GEN-LAST:event_jTextField_buscarKeyTyped

    private void jFormattedDNIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedDNIKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            mostrardato();
        }
    }//GEN-LAST:event_jFormattedDNIKeyPressed

    private void jFormattedDNIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedDNIKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedDNIKeyTyped

    private void jTableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableClienteMouseClicked
        // TODO add your handling code here:
        jFormattedTelefono.setText("");
        llenarDatos();
    }//GEN-LAST:event_jTableClienteMouseClicked

    private void jFormattedTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTelefonoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_email.requestFocus();
        }
    }//GEN-LAST:event_jFormattedTelefonoKeyPressed

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
            java.util.logging.Logger.getLogger(frmClienteReg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmClienteReg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmClienteReg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmClienteReg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmClienteReg().setVisible(true);
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
    public static javax.swing.JFormattedTextField jFormattedDNI;
    private javax.swing.JFormattedTextField jFormattedTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JTable jTableCliente;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField_apellido_m;
    private javax.swing.JTextField jTextField_apellido_p;
    private javax.swing.JTextField jTextField_buscar;
    private javax.swing.JTextField jTextField_direccion;
    private javax.swing.JTextField jTextField_email;
    private javax.swing.JTextField jTextField_nombre;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPanel lblcab;
    private javax.swing.JLabel lblcountipRop;
    private javax.swing.JPanel pnel_inf1;
    // End of variables declaration//GEN-END:variables
}
