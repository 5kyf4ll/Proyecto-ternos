/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VentanasFormulario;
import Clases.Conexion;
import Clases.limpiar;

import Clases2.Controlador;
import Formularios.frmPersona;
import static Formularios.frmPersona.jFormattedDni;
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
public class frmRegistroCliente extends javax.swing.JFrame {

    /**
     * Creates new form frmRegistroCliente
     */
    DefaultTableModel tabla = new DefaultTableModel();
    Conexion cn = new Conexion();
    limpiar lim = new limpiar();
    Controlador control=new Controlador();
    Controlador ctl=new Controlador();
    public frmRegistroCliente() {
        initComponents();
        radiobutton();
        jLabel13.setVisible(false);
        
    }
    public void nombresTitulos() {
        String[] tit = {"DNI","Nombre", "Apellido Paterno","Apellido Materno", "Dirección", "Telefono", "Correo Electronico"};
        tabla.setColumnIdentifiers(tit);
        jTable1.setModel(tabla);
        jFormattedDni.requestFocus();
    }
    public void radiobutton(){
        if(jRadioButton1.isSelected()){
            jPanel2.setVisible(true);
            jPanel3.setVisible(false);
            nombresTitulos();
            cargarDatos();
            
        }
        else if (jRadioButton2.isSelected()){
            jPanel3.setVisible(true);
            jPanel2.setVisible(false);
            nombresTitulosRuc();
            cargarDatosRuc();
        }
        else{
             jPanel2.setVisible(false);
             jPanel3.setVisible(false);
        }
    }
    

    public void limpiarCampos() {
        jFormattedDni.setText("");
        jTextField_nombre.setText("");
        jTextField_apellido_p.setText("");
        jTextField_direccion.setText("");
        jFormattedTelefono.setText("");
        jTextField_email.setText("");
        jTextField_apellido_m.setText("");
        jFormattedTextFieldRuc.setText("");
        jTextFieldSocial.setText("");
        jTextFieldDirec.setText("");

    }
    public void llenarDatos() {
        int filas;
        int n = jTable1.getSelectedRow();
        jFormattedDni.setText(jTable1.getValueAt(n, 0).toString());
        jTextField_nombre.setText(jTable1.getValueAt(n, 1).toString());
        jTextField_apellido_p.setText(jTable1.getValueAt(n, 2).toString());
        jTextField_apellido_m.setText(jTable1.getValueAt(n, 3).toString());
        jTextField_direccion.setText(jTable1.getValueAt(n, 4).toString());
        jFormattedTelefono.setText(""+jTable1.getValueAt(n, 5).toString());
        jTextField_email.setText(jTable1.getValueAt(n, 6).toString());
        

        filas = n;
    }
    public void llenarDatosRuc() {
        int filas;
        int n = jTable1.getSelectedRow();
        jFormattedTextFieldRuc.setText(jTable1.getValueAt(n, 0).toString());
        jTextFieldSocial.setText(jTable1.getValueAt(n, 1).toString());
        jTextFieldDirec.setText(jTable1.getValueAt(n, 2).toString());
        filas = n;
    }

    public void reinicioDatos() {
        lim.limpiarTabla(jTable1, tabla);
        cargarDatos();
        limpiarCampos();
        jFormattedDni.requestFocus();
    }
     public void cargarDatos() {
         
        try {
            lim.limpiarTabla(jTable1, tabla);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con=cn.conectar();
            String sql = "select * from v_verCliente";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {100, 200, 200, 250, 250, 200, 200};
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
            ps = con.prepareStatement("insert into cliente(id_cliente,fk_id_persona)values (?,?);");
            
            String msg=control.DevolverRegistroDato("SELECT p.id_persona from persona p where p.dni='"+jFormattedDni.getText()+"';", 1);
            ps.setString(1, null);
            ps.setString(2, msg);


            ps.execute();
            //JOptionPane.showMessageDialog(null, "Se guardo con éxito");
            String aux2=control.DevolverRegistroDato("Select id_persona from persona where DNI='"+jFormattedDni.getText()+"'", 1);
            String aux=control.DevolverRegistroDato("Select id_Cliente from cliente where fk_id_Persona='"+aux2+"';", 1);
            frmPrueba.text_cod.setText(""+aux);
            frmPrueba.text_dni.setText(""+jFormattedDni.getText());
            frmPrueba.text_cliente.setText(jTextField_nombre.getText()+" "+jTextField_apellido_p.getText()+" "+jTextField_apellido_m.getText());
            frmPrueba.text_telf.setText(jFormattedTelefono.getText());
            frmPrueba.text_Correo.setText(jTextField_email.getText());
            frmPrueba.text_Direccion.setText(jTextField_direccion.getText());
            cargarDatos();
            limpiarCampos();

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    public void mostrardato(){
        String cant=control.DevolverRegistroDato(" select CHARACTER_LENGTH('"+jFormattedDni.getText()+"');", 1);
        if(control.VerificaConsulta("SELECT * from v_dni where DNI='"+jFormattedDni.getText()+"'") ){
            jTextField_nombre.setText(control.DevolverRegistroDato("SELECT p.nombre from persona p where p.dni='"+jFormattedDni.getText()+"';", 1));
            jTextField_apellido_p.setText(control.DevolverRegistroDato("SELECT p.apellido_p from persona p where p.dni='"+jFormattedDni.getText()+"';", 1));
            jTextField_apellido_m.setText(control.DevolverRegistroDato("SELECT p.apellido_m from persona p where p.dni='"+jFormattedDni.getText()+"';", 1));
            jTextField_direccion.setText(control.DevolverRegistroDato("SELECT p.direccion from persona p where p.dni='"+jFormattedDni.getText()+"';", 1));
            jFormattedTelefono.setText(control.DevolverRegistroDato("SELECT p.telefono from persona p where p.dni='"+jFormattedDni.getText()+"';", 1));
            jTextField_email.setText(control.DevolverRegistroDato("SELECT p.email from persona p where p.dni='"+jFormattedDni.getText()+"';", 1));
        jLabel10.setVisible(false);
        }
        
        else if("8".equals(cant)){
            frmPersona frm=new frmPersona();
            frm.setVisible(true);
            frmPersona.jFormattedDni.setText(""+jFormattedDni.getText());
            
        }
        else{
            jLabel13.setVisible(true);
        }
        System.out.println(""+jFormattedDni.getText());
        System.out.println(cant);
    
    }
    
    public void actualizarDatos() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("update persona set DNI=?,nombre=?,apellido_p=?,apellido_m=?,direccion=?,telefono=?,email=?,fk_idtipo_persona=?  where id_persona=?;");
            String ca=control.DevolverRegistroDato("select fk_idtipo_persona from persona WHERE DNI='"+jFormattedDni.getText()+"';", 1);
            String id=control.DevolverRegistroDato("select id_persona from persona WHERE DNI='"+jFormattedDni.getText()+"';", 1);
            ps.setString(1, jFormattedDni.getText());
            ps.setString(2, jTextField_nombre.getText());
            ps.setString(3, jTextField_apellido_p.getText());
            ps.setString(4, jTextField_apellido_m.getText());
            ps.setString(5, jTextField_direccion.getText());
            ps.setString(6, jFormattedTelefono.getText());
            ps.setString(7, jTextField_email.getText());
            ps.setString(8, ca);
            ps.setString(9, id);

            ps.execute();
            JOptionPane.showMessageDialog(null, "Se actualizo con éxito ");
            limpiarCampos();
            cargarDatos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar");
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
            //JOptionPane.showMessageDialog(null, "Se guardo con éxito");
            String aux2=control.DevolverRegistroDato("Select id_persona from persona where ruc='"+jFormattedTextFieldRuc.getText()+"'", 1);
            String aux=control.DevolverRegistroDato("Select id_Cliente from cliente where fk_id_Persona='"+aux2+"';", 1);
            frmPrueba.text_cod.setText(""+aux);
            frmPrueba.text_dni.setText(""+jFormattedTextFieldRuc.getText());
            frmPrueba.text_cliente.setText(jTextFieldSocial.getText());
            frmPrueba.text_Direccion.setText(jTextFieldDirec.getText());
            cargarDatosRuc();
            limpiarCampos();

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    public void mostrardatoRuc(){
        String cant=control.DevolverRegistroDato(" select CHARACTER_LENGTH('"+jFormattedTextFieldRuc.getText()+"');", 1);
        if(control.VerificaConsulta("SELECT * from v_ruc where ruc='"+jFormattedTextFieldRuc.getText()+"'") ){
            jTextFieldSocial.setText(control.DevolverRegistroDato("SELECT p.razon from persona p where p.ruc='"+jFormattedTextFieldRuc.getText()+"';", 1));
            jTextFieldDirec.setText(control.DevolverRegistroDato("SELECT p.direccion from persona p where p.ruc='"+jFormattedTextFieldRuc.getText()+"';", 1));
        
        }
        
        else if("11".equals(cant)){
            frmRUC frm=new frmRUC();
            frm.setVisible(true);
            frmRUC.jFormattedRUC.setText(""+jFormattedTextFieldRuc.getText());
            
        }
        else{
            jLabel13.setVisible(true);
        }
        System.out.println(""+jFormattedDni.getText());
        System.out.println(cant);
    
    }
    public void nombresTitulosRuc() {
        String[] tit = {"RUC", "RAZON SOCIAL", "DIRECCION"};
        tabla.setColumnIdentifiers(tit);
        jTable1.setModel(tabla);
        jFormattedDni.requestFocus();
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
    public void actualizarDatosRuc() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("update persona set ruc=?,razon=?,direccion=?,fk_idtipo_persona=? where id_persona=?;");
            String ca=control.DevolverRegistroDato("select fk_idtipo_persona from persona WHERE ruc='"+jFormattedDni.getText()+"';", 1);
            String id=control.DevolverRegistroDato("select id_persona from persona WHERE ruc='"+jFormattedDni.getText()+"';", 1);
            ps.setString(1, jFormattedTextFieldRuc.getText());
            ps.setString(2, jTextFieldSocial.getText());
            ps.setString(3, jTextFieldDirec.getText());
            ps.setString(4, ca);
            ps.setString(5, id);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se actualizo con éxito ");
            limpiarCampos();
            cargarDatosRuc();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar");
            System.out.println(e.toString());
        }
    }
    public void eliminarDatos() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            int fila = jTable1.getSelectedRow();
            String codigo = jTable1.getValueAt(fila, 0).toString();
            String id=control.DevolverRegistroDato("SELECT id_persona FROM persona where ruc='"+codigo+"' or DNI='"+codigo+"';", 1);
            String idClien=control.DevolverRegistroDato("SELECT id_cliente FROM cliente where fk_id_persona='"+id+"';", 1);
            ps = con.prepareStatement("delete from cliente where id_cliente=?;");
            ps.setString(1, idClien);
            ps.execute();
            tabla.removeRow(fila);
            JOptionPane.showMessageDialog(null, "Se elimino con éxito");
            limpiarCampos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar");
            System.out.println(e.toString());
        }
        
        if(jRadioButton1.isSelected()){
            cargarDatos();
        }
        else if(jRadioButton2.isSelected()){
            cargarDatosRuc();
        }
    }
    public void verCli(){
       
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
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
        jFormattedDni = new javax.swing.JFormattedTextField();
        jFormattedTelefono = new javax.swing.JFormattedTextField();
        jButton3 = new javax.swing.JButton();
        jTextField_apellido_m = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
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
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldSocial = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldDirec = new javax.swing.JTextField();
        jFormattedTextFieldRuc = new javax.swing.JFormattedTextField();
        jButton8 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        try {
            jFormattedDni.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedDniKeyPressed(evt);
            }
        });
        jPanel2.add(jFormattedDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 160, -1));

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

        jButton3.setText("...");
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, -1, -1));
        jPanel2.add(jTextField_apellido_m, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 160, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Apellido Materno:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 51));
        jLabel13.setText("Ingrese 8 numeros");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 580, 350));

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
                .addContainerGap(945, Short.MAX_VALUE))
        );
        lblcabLayout.setVerticalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblcabLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(lblcab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 30));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 590, 980, 220));

        pnel_inf1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204), 2));
        pnel_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI", "RUC", " " }));
        jComboBox1.setToolTipText("");
        pnel_inf1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, -1));
        pnel_inf1.add(jTextField_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 300, -1));

        jPanel1.add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 530, 510, 50));

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
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 530, -1, 50));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("RUC");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("Razon Social");

        jTextFieldSocial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSocialKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldSocialKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setText("Direccion");

        jTextFieldDirec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldDirecKeyTyped(evt);
            }
        });

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

        jButton8.setText("...");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextFieldSocial)
                        .addComponent(jTextFieldDirec, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jFormattedTextFieldRuc, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jFormattedTextFieldRuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextFieldSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldDirec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 120, 390, 200));

        jButton7.setBackground(new java.awt.Color(51, 153, 255));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/actualizar (2).png"))); // NOI18N
        jButton7.setText("Actualizar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 490, -1, -1));

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
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 490, -1, -1));

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("RUC");
        jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton2MouseClicked(evt);
            }
        });
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, -1, -1));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("DNI");
        jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton1MouseClicked(evt);
            }
        });
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel8.setText("Tipo");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 820));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if(jRadioButton1.isSelected()){
            actualizarDatos();
        }
        else if(jRadioButton2.isSelected()){
            actualizarDatosRuc();
        }
        else{
            JOptionPane.showMessageDialog(null, "Eliga DNI O RUC");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton1.isSelected()){
            guardarDatos();
        }
        else if(jRadioButton2.isSelected()){
            guardarDatosRUC();
        }
        else{
            JOptionPane.showMessageDialog(null, "Eliga DNI O RUC");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jFormattedDniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedDniKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            mostrardato();
            radiobutton();
        }
    }//GEN-LAST:event_jFormattedDniKeyPressed

    private void jFormattedTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTelefonoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_email.requestFocus();
        }
    }//GEN-LAST:event_jFormattedTelefonoKeyPressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if(jRadioButton1.isSelected()){
            llenarDatos();
        }
        else if(jRadioButton2.isSelected()){
            llenarDatosRuc();
        }
        else{
            JOptionPane.showMessageDialog(null, "Eliga DNI O RUC");
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       if(jRadioButton1.isSelected()){
            eliminarDatos();
        }
        else if(jRadioButton2.isSelected()){
            eliminarDatos();
        }
        else{
            JOptionPane.showMessageDialog(null, "Eliga DNI O RUC");
        }
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
       // buscarDatos();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jRadioButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton1MouseClicked
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_jRadioButton1MouseClicked

    private void jRadioButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton2MouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jRadioButton2MouseClicked

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        radiobutton();
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        radiobutton();
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jFormattedTextFieldRucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextFieldRucKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            mostrardatoRuc();
            radiobutton();
        }
    }//GEN-LAST:event_jFormattedTextFieldRucKeyPressed

    private void jTextFieldSocialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSocialKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
            
        }
    }//GEN-LAST:event_jTextFieldSocialKeyTyped

    private void jTextFieldDirecKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDirecKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
            
        }
    }//GEN-LAST:event_jTextFieldDirecKeyTyped

    private void jTextFieldSocialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSocialKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldDirec.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldSocialKeyPressed

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
            java.util.logging.Logger.getLogger(frmRegistroCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmRegistroCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmRegistroCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmRegistroCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmRegistroCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox1;
    public static javax.swing.JFormattedTextField jFormattedDni;
    public static javax.swing.JFormattedTextField jFormattedTelefono;
    public static javax.swing.JFormattedTextField jFormattedTextFieldRuc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    public static javax.swing.JPanel jPanel3;
    public static javax.swing.JRadioButton jRadioButton1;
    public static javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    public static javax.swing.JTextField jTextFieldDirec;
    public static javax.swing.JTextField jTextFieldSocial;
    public static javax.swing.JTextField jTextField_apellido_m;
    public static javax.swing.JTextField jTextField_apellido_p;
    private javax.swing.JTextField jTextField_buscar;
    public static javax.swing.JTextField jTextField_direccion;
    public static javax.swing.JTextField jTextField_email;
    public static javax.swing.JTextField jTextField_nombre;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPanel lblcab;
    private javax.swing.JLabel lblcountipRop;
    private javax.swing.JPanel pnel_inf1;
    // End of variables declaration//GEN-END:variables
}
