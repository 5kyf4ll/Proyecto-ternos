package Formularios;

import Clases.Conexion;
import Clases.limpiar;
import VentanasFormulario.frmRegistroCliente;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class frmPersona extends javax.swing.JFrame {

    DefaultTableModel tabla = new DefaultTableModel();
    Conexion cn = new Conexion();
    limpiar lim = new limpiar();

    public frmPersona() {
        initComponents();
        nombresTitulos();
        cargarDatos();
    }

    /*                             Complementarios
    ============================================================================
     */
    public void nombresTitulos() {
        String[] tit = {"DNI", "Nombre", "Apellido Paterno", "Apellido Materno", "Dirección", "Telefono", "Correo Electronico"};
        tabla.setColumnIdentifiers(tit);
        jTable1.setModel(tabla);
        jFormattedDni.requestFocus();
    }

    public void limpiarCampos() {
        jFormattedDni.setText("");
        jTextField_nombre.setText("");
        jTextField_apellido_p.setText("");
        jTextField_apellido_m.setText("");
        jTextField_direccion.setText("");
        jFormattedTelefono.setText("");
        jTextField_email.setText("");
        jTextField_fk_id_tipo_persona.setText("");

    }

    public void llenarDatos() {
        int filas;
        int n = jTable1.getSelectedRow();
        jFormattedDni.setText(jTable1.getValueAt(n, 0).toString());
        jTextField_nombre.setText(jTable1.getValueAt(n, 1).toString());
        jTextField_apellido_p.setText(jTable1.getValueAt(n, 2).toString());
        jTextField_apellido_m.setText(jTable1.getValueAt(n, 3).toString());
        jTextField_direccion.setText(jTable1.getValueAt(n, 4).toString());
        jFormattedTelefono.setText(jTable1.getValueAt(n, 5).toString());
        jTextField_email.setText(jTable1.getValueAt(n, 6).toString());
        //jTextField_fk_id_tipo_persona.setText(jTable1.getValueAt(n, 7).toString());

        filas = n;
    }

    public void reinicioDatos() {
        lim.limpiarTabla(jTable1, tabla);
        cargarDatos();
        limpiarCampos();
        jFormattedDni.requestFocus();
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
            String sql = "select * from v_dni";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {200, 200, 200, 250, 250, 200, 200};
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

    /*                              Guardar Datos
    ============================================================================
     */
    public void guardarDatos() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into persona(id_persona,DNI,nombre,apellido_p,apellido_m,direccion,telefono,email,fk_idtipo_persona)values (?,?,?,?,?,?,?,?,?);");
            ps.setString(1, jFormattedDni.getText());
             ps.setString(2, jFormattedDni.getText());
            ps.setString(3, jTextField_nombre.getText());
            ps.setString(4, jTextField_apellido_p.getText());
            ps.setString(5, jTextField_apellido_m.getText());
            ps.setString(6, jTextField_direccion.getText());
            ps.setString(7, jFormattedTelefono.getText());
            ps.setString(8, jTextField_email.getText());
            ps.setString(9, jTextField_fk_id_tipo_persona.getText());

            ps.execute();
            //JOptionPane.showMessageDialog(null, "Se guardo con éxito");
            frmRegistroCliente.jFormattedDni.setText(""+jFormattedDni.getText());
            frmRegistroCliente.jTextField_nombre.setText(""+jTextField_nombre.getText());
            frmRegistroCliente.jTextField_apellido_p.setText(""+jTextField_apellido_p.getText());
            frmRegistroCliente.jTextField_apellido_m.setText(""+jTextField_apellido_m.getText());
            frmRegistroCliente.jTextField_direccion.setText(""+jTextField_direccion.getText());
            frmRegistroCliente.jFormattedTelefono.setText(""+jFormattedTelefono.getText());
            frmRegistroCliente.jTextField_email.setText(""+jTextField_email.getText());
            cargarDatos();
            limpiarCampos();

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }

    /*                              Eliminar datos
    ============================================================================
     */
    public void eliminarDatos() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            int fila = jTable1.getSelectedRow();
            String codigo = jTable1.getValueAt(fila, 0).toString();

            ps = con.prepareStatement("delete from persona where DNI=?;");
            ps.setString(1, codigo);
            ps.execute();
            tabla.removeRow(fila);
            JOptionPane.showMessageDialog(null, "Se elimino con éxito");
            limpiarCampos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar");
            System.out.println(e.toString());
        }
        cargarDatos();
    }

    /*                             Actualizar  datos
    ============================================================================
     */
    public void actualizarDatos() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("update persona set nombre=?,apellido_p=?,apellido_m=?,direccion=?,telefono=?,email=?,fk_idtipo_persona=?  where DNI=?;");

            ps.setString(1, jTextField_nombre.getText());
            ps.setString(2, jTextField_apellido_p.getText());
            ps.setString(3, jTextField_apellido_m.getText());
            ps.setString(4, jTextField_direccion.getText());
            ps.setString(5, jFormattedTelefono.getText());
            ps.setString(6, jTextField_email.getText());
            ps.setString(7, jTextField_fk_id_tipo_persona.getText());
            ps.setString(8, jFormattedDni.getText());

            ps.execute();
            JOptionPane.showMessageDialog(null, "Se actualizo con éxito ");
            limpiarCampos();
            cargarDatos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar");
            System.out.println(e.toString());
        }
    }

    /*                              Buscar datos
    ============================================================================
     */
    public void buscarDatos() {
        String where = "";
        if (jComboBox1.getSelectedItem() == "DNI") {
            where = " where DNI='";
        } else if (jComboBox1.getSelectedItem() == "Nombre") {
            where = " where nombre='";
        } else if (jComboBox1.getSelectedItem() == "Apellido Paterno") {
            where = " where apellido_p='";
        } else if (jComboBox1.getSelectedItem() == "Apellido Materno") {
            where = " where apellido_m='";
        }

        String campo = jTextField_buscar.getText();

        if (!"".equals(campo)) {
            where = where + campo + "'";
        }
        try {
            jTable1.setModel(tabla);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = cn.conectar();
            String sql = "select * from persona" + where;
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsnd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsnd.getColumnCount();
            int anchos[] = {100, 200, 200, 250, 250, 200, 200, 200};
            for (int i = 0; i < jTable1.getColumnCount(); i++) {

                jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

            }
            lim.limpiarTabla(jTable1, tabla);
            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                tabla.addRow(filas);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    /*
    ============================================================================
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator3 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField_nombre = new javax.swing.JTextField();
        jTextField_apellido_p = new javax.swing.JTextField();
        jTextField_apellido_m = new javax.swing.JTextField();
        jTextField_direccion = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField_email = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField_fk_id_tipo_persona = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jFormattedDni = new javax.swing.JFormattedTextField();
        jFormattedTelefono = new javax.swing.JFormattedTextField();
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
        jLabel2.setText("DNI :");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Nombre :");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 48, -1, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Apellido Paterno :");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 78, -1, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Apellido Materno :");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 115, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Direccion :");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 149, -1, -1));

        jTextField_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_nombreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_nombreKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 160, -1));

        jTextField_apellido_p.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_apellido_pKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_apellido_pKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_apellido_p, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 73, 160, -1));

        jTextField_apellido_m.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_apellido_mKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_apellido_mKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_apellido_m, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 160, -1));

        jTextField_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_direccionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_direccionKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(149, 144, 369, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Telefono :");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 183, -1, -1));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Correo :");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 217, -1, -1));
        jPanel2.add(jTextField_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(149, 212, 250, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("Tipo Persona :");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, -1));
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
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, -1, -1));

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
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, -1, -1));

        jButton3.setText("...");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, -1, 20));

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
        jPanel2.add(jFormattedDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 160, -1));

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
        jPanel2.add(jFormattedTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 160, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 580, 290));

        lblcab.setBackground(new java.awt.Color(204, 204, 204));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("PERSONA");

        javax.swing.GroupLayout lblcabLayout = new javax.swing.GroupLayout(lblcab);
        lblcab.setLayout(lblcabLayout);
        lblcabLayout.setHorizontalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblcabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addContainerGap(726, Short.MAX_VALUE))
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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 760, 220));

        pnel_inf1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204), 2));
        pnel_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI", "Nombre", "Apellido Paterno", "Apellido Materno" }));
        jComboBox1.setToolTipText("");
        pnel_inf1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, -1));
        pnel_inf1.add(jTextField_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 300, -1));

        jPanel1.add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 510, 40));

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
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 370, -1, 40));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icono-clientes.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 120, 190, 180));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 650));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        actualizarDatos();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        eliminarDatos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        llenarDatos();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        guardarDatos();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        buscarDatos();
        dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new Tipo_personas().setVisible(true);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jFormattedDniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedDniKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_nombre.requestFocus();
        }
    }//GEN-LAST:event_jFormattedDniKeyPressed

    private void jTextField_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_nombreKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_apellido_p.requestFocus();
        }
    }//GEN-LAST:event_jTextField_nombreKeyPressed

    private void jTextField_apellido_pKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_apellido_pKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_apellido_m.requestFocus();
        }
    }//GEN-LAST:event_jTextField_apellido_pKeyPressed

    private void jTextField_apellido_mKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_apellido_mKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_direccion.requestFocus();
        }
    }//GEN-LAST:event_jTextField_apellido_mKeyPressed

    private void jTextField_direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_direccionKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jFormattedTelefono.requestFocus();
        }
    }//GEN-LAST:event_jTextField_direccionKeyPressed

    private void jFormattedTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTelefonoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_email.requestFocus();
        }
    }//GEN-LAST:event_jFormattedTelefonoKeyPressed

    private void jTextField_nombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_nombreKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
            
        }
        
    }//GEN-LAST:event_jTextField_nombreKeyTyped

    private void jTextField_apellido_pKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_apellido_pKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
            
        }
    }//GEN-LAST:event_jTextField_apellido_pKeyTyped

    private void jTextField_apellido_mKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_apellido_mKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
           
        }
    }//GEN-LAST:event_jTextField_apellido_mKeyTyped

    private void jTextField_direccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_direccionKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
            
        }
    }//GEN-LAST:event_jTextField_direccionKeyTyped

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
            java.util.logging.Logger.getLogger(frmPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPersona().setVisible(true);
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
    public static javax.swing.JFormattedTextField jFormattedDni;
    private javax.swing.JFormattedTextField jFormattedTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField_apellido_m;
    private javax.swing.JTextField jTextField_apellido_p;
    private javax.swing.JTextField jTextField_buscar;
    private javax.swing.JTextField jTextField_direccion;
    private javax.swing.JTextField jTextField_email;
    private javax.swing.JTextField jTextField_fk_id_tipo_persona;
    private javax.swing.JTextField jTextField_nombre;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPanel lblcab;
    private javax.swing.JLabel lblcountipRop;
    private javax.swing.JPanel pnel_inf1;
    // End of variables declaration//GEN-END:variables
}
