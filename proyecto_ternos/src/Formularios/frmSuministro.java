package Formularios;

import Clases.Conexion;
import Clases.Funciones;
import Clases.limpiar;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class frmSuministro extends javax.swing.JFrame {

    DefaultTableModel tabla = new DefaultTableModel();
    DefaultTableModel tabla1 = new DefaultTableModel();
    DefaultTableModel tabla2 = new DefaultTableModel();
    DefaultTableModel tabla3 = new DefaultTableModel();
    Conexion cn = new Conexion();
    limpiar lim = new limpiar();
    String fecha2;
    Funciones funciones =new Funciones();

    public frmSuministro() {
        initComponents();
        nombresTitulos();
        cargarDatos();
        nombresTitulos1();
        cargarDatos2();
        nombresTitulos3();
        cargarDatos3();
        nombresTitulos4();
        cargarDatos4();
        //jTextField_id_suministro.setEditable(false);
    }

    /*                             Complementarios
    ============================================================================
     */
    public void nombresTitulos() {
        String[] tit = {"ID Suministro", "Fecha", "Precio","Cantidad", "FK ID Proveedor", "FK ID Artículo", "FK ID Modelo Tela"};
        tabla.setColumnIdentifiers(tit);
        jTable1.setModel(tabla);
    }

    public void limpiarCampos() {
        jTextField_id_suministro.setText("");
        jTextField_cantidad.setText("");
        jTextField_fk_id_proveedor.setText("");
        jTextField_fk_id_articulo.setText("");
        jTextField_fk_id_modelo_tela.setText("");
        jTextField_buscar.setText("");
        jTextField_Precio.setText("");
        jDateChooserInicio.setDate(funciones.StringADate(""));

    }

    public void llenarDatos() {
        int filas;
        int n = jTable1.getSelectedRow();
        jTextField_id_suministro.setText(jTable1.getValueAt(n, 0).toString());
        jDateChooserInicio.setDate(funciones.StringADate(jTable1.getValueAt(n, 1).toString()));
        jTextField_Precio.setText(jTable1.getValueAt(n, 2).toString());
        jTextField_cantidad.setText(jTable1.getValueAt(n, 3).toString());
        jTextField_fk_id_proveedor.setText(jTable1.getValueAt(n, 4).toString());
        jTextField_fk_id_articulo.setText(""+jTable1.getValueAt(n, 5).toString());
        jTextField_fk_id_modelo_tela.setText(""+jTable1.getValueAt(n, 6).toString());
        filas = n;
        jTextField_Precio.requestFocus();
    }

    public void reinicioDatos() {
        lim.limpiarTabla(jTable1, tabla);
        limpiarCampos();
        cargarDatos();
        jTextField_id_suministro.requestFocus();
    }
    public void fechaSumnistro(){
        SimpleDateFormat fff=new SimpleDateFormat("yyyy/MM/dd");
        Date fecha=jDateChooserInicio.getDate();
        fecha2=fff.format(fecha);

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
            String sql = "select * from  Suministro";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {200, 200, 200, 200, 200, 200,200};
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
            int i=jTable1.getRowCount()+1;
            jTextField_id_suministro.setText(Integer.toString(i));
            int filas = tabla.getRowCount();
            lblcountipRop.setText(String.valueOf(filas));
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    /*                                Cargar Datos Articulo
    ============================================================================
     */
    public void nombresTitulos1() {
        String[] tit = {"ID Articulo", "FK Tipo Prenda", "FK Marca", "FK Modelo ", "FK Tallas ", "FK Material", "Precio", "Descrpcion", "Stock"};
        tabla1.setColumnIdentifiers(tit);
        jTable2.setModel(tabla1);
    }
    public void cargarDatos2() {
        try {
            lim.limpiarTabla(jTable2, tabla1);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = cn.conectar();
            String sql = "select * from Articulo ";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {200, 200, 200, 200, 200, 200, 200, 200, 200};
            for (int i = 0; i < jTable2.getColumnCount(); i++) {
                jTable2.getColumnModel().getColumn(i).setPreferredWidth(anchosEs[i]);

            }
            while (rs.next()) {
                Object[] filas = new Object[canatidadColumnasEs];
                for (int i = 0; i < canatidadColumnasEs; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                tabla1.addRow(filas);
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
     public void llenarDatos1() {
        int filas;
        int n = jTable2.getSelectedRow();
        jTextField_fk_id_articulo.setText(jTable2.getValueAt(n, 0).toString());
        filas = n;
    }
     /*                                Cargar Datos Model Tela
    ============================================================================
     */
     public void nombresTitulos3() {
        String[] tit = {"ID Modelo Tela", "Nombre Modelo", "Precio", "Metros", "FK ID MArca Tela"};
        tabla2.setColumnIdentifiers(tit);
        jTable3.setModel(tabla2);
    }
    public void cargarDatos3() {
        try {
            lim.limpiarTabla(jTable3, tabla2);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = cn.conectar();
            String sql = "select * from modelo_tela ";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {200, 200, 200, 200, 200};
            for (int i = 0; i < jTable3.getColumnCount(); i++) {
                jTable3.getColumnModel().getColumn(i).setPreferredWidth(anchosEs[i]);

            }
            while (rs.next()) {
                Object[] filas = new Object[canatidadColumnasEs];
                for (int i = 0; i < canatidadColumnasEs; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                tabla2.addRow(filas);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
     public void llenarDatos3() {
        int filas;
        int n = jTable3.getSelectedRow();
        jTextField_fk_id_modelo_tela.setText(jTable3.getValueAt(n, 0).toString());
        filas = n;
    }
     /*                                Cargar Datos 
    ============================================================================
     */
     public void nombresTitulos4() {
        String[] tit = {"ID Proveedor", "Razón Social", "Dirección", "Telefono", "Pagina Web", "Representante"};
        tabla3.setColumnIdentifiers(tit);
        jTable4.setModel(tabla3);
        
    }
     public void cargarDatos4() {
        try {
            lim.limpiarTabla(jTable4, tabla3);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = cn.conectar();
            String sql = "select * from Proveedor";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {100, 200, 300, 150, 250, 100};
            for (int i = 0; i < jTable4.getColumnCount(); i++) {
                jTable4.getColumnModel().getColumn(i).setPreferredWidth(anchosEs[i]);

            }
            while (rs.next()) {
                Object[] filas = new Object[canatidadColumnasEs];
                for (int i = 0; i < canatidadColumnasEs; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                tabla3.addRow(filas);
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
      public void llenarDatos4() {
        int filas;
        int n = jTable4.getSelectedRow();
        jTextField_fk_id_proveedor.setText(jTable4.getValueAt(n, 0).toString());
        filas = n;
    }
    


    /*                              Guardar Datos
    ============================================================================
     */
    public void guardarDatos() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            fechaSumnistro();
            ps = con.prepareStatement("insert into suministro(idSuministro,Fecha,precio_s,Cantidad,fk_idProveedor,fk_idarticulo) values (?,?,?,?,?,?);");
            ps.setString(1, jTextField_id_suministro.getText());
            ps.setString(2, fecha2);
            ps.setString(3, jTextField_Precio.getText());
            ps.setString(4, jTextField_cantidad.getText());
            ps.setString(5, jTextField_fk_id_proveedor.getText());
            ps.setString(6, jTextField_fk_id_articulo.getText());
            
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardo con éxito");
            cargarDatos();
            cargarDatos2();
            cargarDatos3();
            limpiarCampos();
            

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
     public void guardarDatos2() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            fechaSumnistro();
            ps = con.prepareStatement("insert into suministro(idSuministro,Fecha,precio_s,Cantidad,fk_idProveedor,fk_idmodelo_tela) values (?,?,?,?,?,?);");
            ps.setString(1, jTextField_id_suministro.getText());
            ps.setString(2, fecha2);
            ps.setString(3, jTextField_Precio.getText());
            ps.setString(4, jTextField_cantidad.getText());
            ps.setString(5, jTextField_fk_id_proveedor.getText());
            ps.setString(6, jTextField_fk_id_modelo_tela.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardo con éxito");
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

            ps = con.prepareStatement("delete from Suministro where idSuministro=?;");
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
            fechaSumnistro();
            ps = con.prepareStatement("update Suministro set Fecha=?,precio_s=?,Cantidad=?,fk_idProveedor=?,fk_idarticulo=?,fk_idmodelo_tela=? where idSuministro=?;");

            ps.setString(1, fecha2);
            ps.setString(2, jTextField_cantidad.getText());
            ps.setString(3, jTextField_Precio.getText());
            ps.setString(4, jTextField_fk_id_proveedor.getText());
            ps.setString(5, jTextField_fk_id_articulo.getText());
            ps.setString(6, jTextField_fk_id_modelo_tela.getText());
            ps.setString(7, jTextField_id_suministro.getText());
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
        if (jComboBox1.getSelectedItem() == "ID Suministro") {
            where = " where idSuministro='";
        } else if (jComboBox1.getSelectedItem() == "Fecha") {
            where = " where Fecha='";
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
            String sql = "select * from Suministro" + where;
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsnd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsnd.getColumnCount();
            int anchos[] = {200, 200, 200, 200, 200, 200};
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

        jComboBox2 = new javax.swing.JComboBox<>();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField_id_suministro = new javax.swing.JTextField();
        jTextField_cantidad = new javax.swing.JTextField();
        jTextField_fk_id_proveedor = new javax.swing.JTextField();
        jTextField_fk_id_articulo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField_fk_id_modelo_tela = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jTextField_Precio = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jDateChooserInicio = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel11 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        lblcountipRop = new javax.swing.JLabel();
        lblcab = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField_buscar = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("ID Suministro :");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Fecha  :");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Cantidad  :");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("ID Proveedor :");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("ID Artículo :");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, 20));

        jTextField_id_suministro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_id_suministroActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField_id_suministro, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 160, -1));

        jTextField_cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_cantidadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_cantidadKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 160, -1));

        jTextField_fk_id_proveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_fk_id_proveedorKeyPressed(evt);
            }
        });
        jPanel2.add(jTextField_fk_id_proveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, 160, -1));

        jTextField_fk_id_articulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_fk_id_articuloActionPerformed(evt);
            }
        });
        jTextField_fk_id_articulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_fk_id_articuloKeyPressed(evt);
            }
        });
        jPanel2.add(jTextField_fk_id_articulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, 160, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("ID Modelo Tela:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, -1));
        jPanel2.add(jTextField_fk_id_modelo_tela, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 160, -1));

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
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, -1, -1));

        jButton7.setBackground(new java.awt.Color(51, 153, 255));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/actualizar (2).png"))); // NOI18N
        jButton7.setText("Actualizar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 340, -1, 30));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("Precio  :");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        jTextField_Precio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_PrecioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_PrecioKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_Precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 160, -1));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione...", "Articulo", "Tela" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 160, -1));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Pedido:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        jDateChooserInicio.setDateFormatString("yyyy/MM/d");
        jPanel2.add(jDateChooserInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 160, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icon_proveed.png"))); // NOI18N
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 570, 390));

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
        jToolBar2.add(jSeparator3);

        jButton3.setBackground(new java.awt.Color(51, 204, 0));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/inventario.png"))); // NOI18N
        jButton3.setText("Inventario");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton3);
        jToolBar2.add(jSeparator2);

        jLabel11.setText("Cantidad de Registros:");
        jToolBar2.add(jLabel11);
        jToolBar2.add(jSeparator1);

        lblcountipRop.setText("0");
        jToolBar2.add(lblcountipRop);

        jPanel1.add(jToolBar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 740, 40));

        lblcab.setBackground(new java.awt.Color(204, 204, 204));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("SUMINISTRO");

        javax.swing.GroupLayout lblcabLayout = new javax.swing.GroupLayout(lblcab);
        lblcab.setLayout(lblcabLayout);
        lblcabLayout.setHorizontalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblcabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addContainerGap(656, Short.MAX_VALUE))
        );
        lblcabLayout.setVerticalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(lblcab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 30));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID Suministro", "Fecha" }));
        jComboBox1.setToolTipText("");
        jPanel4.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, -1));
        jPanel4.add(jTextField_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 320, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, 460, 40));

        jButton5.setBackground(new java.awt.Color(51, 51, 255));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/buscar (1).png"))); // NOI18N
        jButton5.setText("Buscar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 490, -1, 40));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Suministro", jPanel3);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Articulo", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Modelo Tela", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Proveedor", jPanel7);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 570, 240));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 790));

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
        if(jComboBox3.getSelectedItem()=="Articulo"){
            guardarDatos();
        } if(jComboBox3.getSelectedItem()=="Tela"){
            guardarDatos2();
        }
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        buscarDatos();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        llenarDatos1();
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTextField_fk_id_articuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_fk_id_articuloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_fk_id_articuloActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        if(jComboBox3.getSelectedItem()=="Articulo"){
            jTextField_fk_id_modelo_tela.setEditable(false);
            jTextField_fk_id_articulo.setEditable(true);
            
            
        }
        else if(jComboBox3.getSelectedItem()=="Tela"){
            jTextField_fk_id_articulo.setEditable(false);
            jTextField_fk_id_modelo_tela.setEditable(true);
        }
//        else {
//            jTextField_fk_id_articulo.setEditable(true);
//            jTextField_fk_id_modelo_tela.setEditable(true);
//        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jTextField_id_suministroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_id_suministroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_id_suministroActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
        llenarDatos3();
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTextField_PrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_PrecioKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_PrecioKeyTyped

    private void jTextField_cantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_cantidadKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_cantidadKeyTyped

    private void jTextField_PrecioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_PrecioKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_cantidad.requestFocus();
        }
    }//GEN-LAST:event_jTextField_PrecioKeyPressed

    private void jTextField_cantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_cantidadKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_fk_id_proveedor.requestFocus();
        }
    }//GEN-LAST:event_jTextField_cantidadKeyPressed

    private void jTextField_fk_id_proveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_fk_id_proveedorKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_fk_id_articulo.requestFocus();
        }
    }//GEN-LAST:event_jTextField_fk_id_proveedorKeyPressed

    private void jTextField_fk_id_articuloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_fk_id_articuloKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextField_fk_id_modelo_tela.requestFocus();
        }
    }//GEN-LAST:event_jTextField_fk_id_articuloKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
         new JDialogInventario(this, false).setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        // TODO add your handling code here:
        llenarDatos4();
    }//GEN-LAST:event_jTable4MouseClicked

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
            java.util.logging.Logger.getLogger(frmSuministro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmSuministro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmSuministro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmSuministro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmSuministro().setVisible(true);
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
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooserInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField_Precio;
    private javax.swing.JTextField jTextField_buscar;
    private javax.swing.JTextField jTextField_cantidad;
    private javax.swing.JTextField jTextField_fk_id_articulo;
    private javax.swing.JTextField jTextField_fk_id_modelo_tela;
    private javax.swing.JTextField jTextField_fk_id_proveedor;
    private javax.swing.JTextField jTextField_id_suministro;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPanel lblcab;
    private javax.swing.JLabel lblcountipRop;
    // End of variables declaration//GEN-END:variables
}
