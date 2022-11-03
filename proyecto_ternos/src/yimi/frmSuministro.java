package yimi;

import yimi.frmProveedor;
import Clases.Conexion;
import Clases.Funciones;
import Clases.limpiar;
import Clases2.Controlador;
import Formularios.frmModelo_Tela;
import yimi.frmArticulo;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
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
    Funciones funciones = new Funciones();
    Calendar c = new GregorianCalendar();
    Controlador control = new Controlador();

    public frmSuministro() {
        initComponents();
        titulosDeLATablaSuministro();
        llenarDatosDeLaTablaSuministro();
        titulosDeLaTablaArticulo();
        llenarDatosDeLaTablaArticulo();
        titulosDeLaTablaTela();
        llenarDatosDeLaTablaTela();
        titulosDeLaTablaProveedor();
        llenarDatosDeLaTablaProveedor();
        noMostarCampos();

        jTextField_fecha.setText(fechaActual());

        nombreTablaPre();
        cargarIdSuministro();
        //repetir();

    }

    /*llenar combo
    ============================================================================
     */
    public void cargarComboPedido() {
        control.LlenarJCombo(jComboBox_pedido, "Select talla from tallas", 1);
        jComboBox_pedido.setSelectedIndex(0);
    }

    /*combo tela o articulo
    ============================================================================
     */
    String tipoPedido = "";

    public void selecionarCombo() {
        tipoPedido = (String) jComboBox_pedido.getSelectedItem();
        if (tipoPedido == "Articulo") {
            jLabel10.setText("Precio de compra :");
            jLabel4.setText("Cantidad :");
            jLabel7.setText("Ariculo :");
            jLabel10.setVisible(true);
            jLabel4.setVisible(true);
            jLabel7.setVisible(true);

        } else if (tipoPedido == "Tela") {
            jLabel7.setText("Tela :");
            jLabel4.setText("Cantidad de tela :");
            jLabel10.setVisible(true);
            jLabel4.setVisible(true);
            jLabel7.setVisible(true);

        }

    }

    public void noMostarCampos() {

        jLabel4.setVisible(false);
        jLabel7.setVisible(false);
    }

    /*fecha actual
    ============================================================================
     */
    public static String fechaActual() {
        Date fecha = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat(
                "yyyy-MM-dd");
        return formatoFecha.format(fecha);
    }

    /*                             Complementarios
    ============================================================================
     */
    public void titulosDeLATablaSuministro() {
        String[] tit = {"ID Suministro", "Fecha", "Precio", "Cantidad", "Proveedor", "Artículo", "Modelo Tela"};
        tabla.setColumnIdentifiers(tit);
        jTable1.setModel(tabla);
    }

    public void limpiarCampos() {
        jTextField_cantidad.setText("");
        jFormattedTextField1.setText("");
        jTextField_id_Articulo_Tela.setText("");
        jTextField_buscar.setText("");
        jTextField_Precio.setText("");

    }

    public void reinicioDatos() {
        lim.limpiarTabla(jTable1, tabla);
        limpiarCampos();
        llenarDatosDeLaTablaSuministro();
        jTextField_id_suministro.requestFocus();
    }

    /*                                Cargar Datos suministro
    ============================================================================
     */
    public void llenarDatosDeLaTablaSuministro() {
        try {
            lim.limpiarTabla(jTable1, tabla);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = cn.conectar();
            String sql = "select * from  v_suminstro";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {200, 200, 200, 100, 200, 200, 200};
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

    public void cargarIdSuministro() {
        int i = jTable1.getRowCount() + 1;
        jTextField_id_suministro.setText(Integer.toString(i));
    }

    public void llenarFechaConsulta() {
        int filas;
        int n = jTable1.getSelectedRow();
        jFormattedTextField2.setText(jTable1.getValueAt(n, 1).toString());
        filas = n;
    }

    /*                                Cargar Datos Articulo
    ============================================================================
     */
    public void titulosDeLaTablaArticulo() {
        String[] tit = {"ID Articulo", "Tipo Prenda", "Marca", "Modelo ", "Tallas ", "Material", "Descripcion", "Precio", "Stock"};
        tabla1.setColumnIdentifiers(tit);
        jTable2.setModel(tabla1);
    }

    public void llenarDatosDeLaTablaArticulo() {
        try {
            lim.limpiarTabla(jTable2, tabla1);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = cn.conectar();
            String sql = "select * from v_productos";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {200, 400, 400, 400, 400, 400, 400, 400, 400};
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
        jTextField_id_Articulo_Tela.setText(jTable2.getValueAt(n, 0).toString());
        filas = n;
    }

    /*                                Cargar Datos Modelo Tela
    ============================================================================
     */
    public void titulosDeLaTablaTela() {
        String[] tit = {"ID Modelo Tela", "Nombre Modelo", "Precio", "Metros", "FK ID MArca Tela"};
        tabla2.setColumnIdentifiers(tit);
        jTable3.setModel(tabla2);
    }

    public void llenarDatosDeLaTablaTela() {
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
        jTextField_id_Articulo_Tela.setText(jTable3.getValueAt(n, 0).toString());
        filas = n;
    }

    /*                                Cargar Datos Proveedor
    ============================================================================
     */
    public void titulosDeLaTablaProveedor() {
        String[] tit = {"ID Proveedor", "Razón Social", "Dirección", "Telefono", "Pagina Web", "Ruc"};
        tabla3.setColumnIdentifiers(tit);
        jTable4.setModel(tabla3);

    }

    public void llenarDatosDeLaTablaProveedor() {
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
        jFormattedTextField1.setText(jTable4.getValueAt(n, 5).toString());
        filas = n;
    }

    /*                              Guardar Datos
    ============================================================================
     */
    public void guardarDatos() {
        PreparedStatement ps = null;
        tipoPedido = (String) jComboBox_pedido.getSelectedItem();
        int n = jTable1.getSelectedRow();
        Connection con = cn.conectar();
        if (tipoPedido == "Articulo") {
            for (int i = 0; i < table_preGuar.getRowCount(); i++) {
                try {
                    ps = con.prepareStatement("insert into suministro(idSuministro,Fecha,precio_s,Cantidad,fk_idProveedor,fk_idarticulo) values (?,?,?,?,?,?);");
                    ps.setString(1, table_preGuar.getValueAt(i, 0).toString());
                    String Proveedor = table_preGuar.getValueAt(i, 1).toString();
                    String idProveedor = control.DevolverRegistroDato("Select idProveedor from proveedor where ruc='" + Proveedor + "';", 1);
                    ps.setString(2, table_preGuar.getValueAt(i, 2).toString());
                    ps.setString(3, table_preGuar.getValueAt(i, 3).toString());
                    ps.setString(4, table_preGuar.getValueAt(i, 4).toString());
                    ps.setString(5, idProveedor);
                    ps.setString(6, table_preGuar.getValueAt(i, 5).toString());
                    ps.execute();
                    llenarDatosDeLaTablaSuministro();
                    llenarDatosDeLaTablaArticulo();
                    llenarDatosDeLaTablaTela();
                    llenarDatosDeLaTablaProveedor();
                    limpiarCampos();

                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error!");
                    System.out.println(e.toString());
                }

            }

        } else if (tipoPedido == "Tela") {

            for (int i = 0; i < table_preGuar.getRowCount(); i++) {
                System.out.print(table_preGuar.getValueAt(i, 0));
                System.out.println();
                try {
                    ps = con.prepareStatement("insert into suministro(idSuministro,Fecha,precio_s,Cantidad,fk_idProveedor,fk_idmodelo_tela) values (?,?,?,?,?,?);");
                    ps.setString(1, table_preGuar.getValueAt(i, 0).toString());
                    String Proveedor = table_preGuar.getValueAt(i, 1).toString();
                    String idProveedor = control.DevolverRegistroDato("Select idProveedor from proveedor where ruc='" + Proveedor + "';", 1);
                    ps.setString(2, table_preGuar.getValueAt(i, 2).toString());
                    ps.setString(3, table_preGuar.getValueAt(i, 3).toString());
                    ps.setString(4, table_preGuar.getValueAt(i, 4).toString());
                    ps.setString(5, idProveedor);
                    ps.setString(6, table_preGuar.getValueAt(i, 5).toString());
                    ps.execute();
                    llenarDatosDeLaTablaSuministro();
                    llenarDatosDeLaTablaArticulo();
                    llenarDatosDeLaTablaTela();
                    llenarDatosDeLaTablaProveedor();
                    limpiarCampos();

                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error");
                    System.out.println(e.toString());
                }
            }
        }
    }

    /*Insertar tados total 
    ============================================================================
     */
    public void cargaMultiple() {
        for (int i = 0; i < table_preGuar.getRowCount(); i++) {
            System.out.print(table_preGuar.getValueAt(i, 0));
            System.out.println();
            PreparedStatement ps = null;
            try {
                Connection con = cn.conectar();
                String codigo = table_preGuar.getValueAt(i, 0).toString();
                ps = con.prepareStatement("delete from venta_prenda where id_venta=?;");
                ps.setString(1, codigo);
                ps.execute();
                //model2.removeRow(i);
                //JOptionPane.showMessageDialog(null, "Registro elimanado con exito");
                //limpiar();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Error ");
                System.out.println(e.toString());
            }
        }
    }


    /*                              Eliminar datos
    ============================================================================
     */
    public void ver() {
        for (int i = 0; i < table_preGuar.getRowCount(); i++) {

            System.out.print(table_preGuar.getValueAt(i, 0));

            System.out.println();
            PreparedStatement ps = null;
            try {
                Connection con = cn.conectar();

                String codigo = table_preGuar.getValueAt(i, 0).toString();

                ps = con.prepareStatement("delete from venta_prenda where id_venta=?;");
                ps.setString(1, codigo);
                ps.execute();

            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Error en eliminacion!");
                System.out.println(e.toString());
            }

        }

    }

    /*                             Actualizar  datos
    ============================================================================
     */
    public void actualizarDatos() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            //  fechaSumnistro();
            ps = con.prepareStatement("update Suministro set Fecha=?,precio_s=?,Cantidad=?,fk_idProveedor=?,fk_idarticulo=?,fk_idmodelo_tela=? where idSuministro=?;");
            ps.setString(1, fecha2);
            ps.setString(2, jTextField_cantidad.getText());
            ps.setString(3, jTextField_Precio.getText());
            ps.setString(4, jFormattedTextField1.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se actualizo con éxito ");
            limpiarCampos();
            llenarDatosDeLaTablaSuministro();
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
    public void mostrardato() {
        if (control.VerificaConsulta("SELECT * from proveedor where  ruc='" + jFormattedTextField1.getText() + "';") == true) {

        } else {
            frmProveedor p = new frmProveedor();
            p.setVisible(true);
            p.setLocationRelativeTo(null);
            String da = jFormattedTextField1.getText();
            p.jTextField_fk_id_ruc.setText(da);

        }
    }

    /*
    ============================================================================
     */
    DefaultTableModel modelo;

    public void nombreTablaPre() {
        modelo = new DefaultTableModel();
        tipoPedido = (String) jComboBox_pedido.getSelectedItem();
        modelo.addColumn("ID Suministro");
        modelo.addColumn("Ruc Proveedor");
        modelo.addColumn("Fecha");
        modelo.addColumn("Precio de compra");
        if (tipoPedido == "Articulo") {

            modelo.addColumn("Cantidad");
            modelo.addColumn("ID Articulo");
        } else if (tipoPedido == "Tela") {

            modelo.addColumn("Cantidad de tela");
            modelo.addColumn("ID Modelo Tela");
        }

        table_preGuar.setModel(modelo);
    }

    public void pregurdadoPrenda() {
        String[] info = new String[6];
        info[0] = jTextField_id_suministro.getText();
        info[1] = jFormattedTextField1.getText();
        info[2] = jTextField_fecha.getText();
        info[3] = jTextField_Precio.getText();
        info[4] = jTextField_cantidad.getText();
        info[5] = jTextField_id_Articulo_Tela.getText();
        modelo.addRow(info);

    }

    public void eliminarPregurdado() {
        while (table_preGuar.getRowCount() > 0) {
            modelo.removeRow(0);
        }

    }

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
        jTextField_id_suministro = new javax.swing.JTextField();
        jTextField_cantidad = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField_Precio = new javax.swing.JTextField();
        jComboBox_pedido = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField_fecha = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField_id_Articulo_Tela = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jToolBar2 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
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
        jButton9 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        table_preGuar = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

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
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Fecha  :");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Cantidad  :");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Ruc Proveedor :");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        jTextField_id_suministro.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextField_id_suministro.setEnabled(false);
        jTextField_id_suministro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_id_suministroActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField_id_suministro, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 160, -1));

        jTextField_cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_cantidadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_cantidadKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, 160, -1));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("Precio de compra :");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

        jTextField_Precio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_PrecioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_PrecioKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_Precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 160, -1));

        jComboBox_pedido.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione...", "Articulo", "Tela" }));
        jComboBox_pedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_pedidoActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox_pedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 160, -1));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Pedido:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icon_proveed.png"))); // NOI18N
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, -1, 180));

        jTextField_fecha.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextField_fecha.setEnabled(false);
        jPanel2.add(jTextField_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 160, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Articulo :");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, -1, 20));
        jPanel2.add(jTextField_id_Articulo_Tela, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 160, -1));

        jButton10.setText("...");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 240, -1, -1));

        try {
            jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField1KeyPressed(evt);
            }
        });
        jPanel2.add(jFormattedTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 160, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 520, 290));

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

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 460, 40));

        jButton5.setBackground(new java.awt.Color(51, 51, 255));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/buscar (1).png"))); // NOI18N
        jButton5.setText("Buscar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 80, -1, 40));

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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Proveedor", jPanel7);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, 570, 240));

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/quitar.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 90, 60));

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadir.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 90, 70));

        table_preGuar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(table_preGuar);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 380, 570, 130));

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
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 530, -1, -1));

        jButton7.setText("Consultar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 430, -1, -1));

        try {
            jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField2ActionPerformed(evt);
            }
        });
        jPanel1.add(jFormattedTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 430, 100, -1));

        jLabel6.setText("Consultar Suministro :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 430, 140, -1));

        jButton4.setBackground(new java.awt.Color(102, 204, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/actu.png"))); // NOI18N
        jButton4.setText("Recargar");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 10, 130, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 610));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        //llenarDatos();
        llenarFechaConsulta();

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        guardarDatos();
        eliminarPregurdado();


    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        buscarDatos();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        llenarDatosDeLaTablaArticulo();
        llenarDatosDeLaTablaProveedor();
        llenarDatosDeLaTablaSuministro();
        llenarDatosDeLaTablaTela();


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        llenarDatos1();
    }//GEN-LAST:event_jTable2MouseClicked
    /**/

    private void jTextField_id_suministroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_id_suministroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_id_suministroActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
        llenarDatos3();
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTextField_PrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_PrecioKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '.') {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_PrecioKeyTyped

    private void jTextField_cantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_cantidadKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '.') {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_cantidadKeyTyped

    private void jTextField_PrecioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_PrecioKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            jTextField_cantidad.requestFocus();
        }
    }//GEN-LAST:event_jTextField_PrecioKeyPressed

    private void jTextField_cantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_cantidadKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            jFormattedTextField1.requestFocus();
        }
    }//GEN-LAST:event_jTextField_cantidadKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        //new JDialogInventario(this, false).setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        // TODO add your handling code here:
        llenarDatos4();
    }//GEN-LAST:event_jTable4MouseClicked

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

        eliminarPregurdado();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        pregurdadoPrenda();
        int dato = Integer.parseInt(jTextField_id_suministro.getText());
        dato = dato + 1;
        jTextField_id_suministro.setText("" + dato);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        tipoPedido = (String) jComboBox_pedido.getSelectedItem();
        frmArticulo ar = new frmArticulo();
        frmModelo_Tela tela = new frmModelo_Tela();
        if (tipoPedido == "Articulo") {

            ar.setVisible(true);
            ar.setLocationRelativeTo(null);

        } else if (tipoPedido == "Tela") {

            tela.setVisible(true);
            tela.setLocationRelativeTo(null);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un Pedido", "Advertencia", JOptionPane.WARNING_MESSAGE);

        }


    }//GEN-LAST:event_jButton10ActionPerformed

    private void jComboBox_pedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_pedidoActionPerformed
        // TODO add your handling code here:
        selecionarCombo();
        nombreTablaPre();

    }//GEN-LAST:event_jComboBox_pedidoActionPerformed

    private void jFormattedTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        control.imprimirParametro("rfechaSuministro", "fech", jFormattedTextField2.getText());

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jFormattedTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            mostrardato();
        }
    }//GEN-LAST:event_jFormattedTextField1KeyPressed
    public void repetir() {
        Timer timer = new Timer();
        frmSuministro.MitareaTimer tarea = new frmSuministro.MitareaTimer();
        timer.schedule(tarea, 5000, 5000);
    }

    public class MitareaTimer extends TimerTask {

        @Override
        public void run() {

            llenarDatosDeLaTablaSuministro();

            llenarDatosDeLaTablaArticulo();

            llenarDatosDeLaTablaTela();

            llenarDatosDeLaTablaProveedor();

        }
    }

    /* formato de texto
    ===========================================================================
     */
    public void formetoTextoFecha() {

    }

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
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox_pedido;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
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
    private javax.swing.JScrollPane jScrollPane5;
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
    private javax.swing.JTextField jTextField_fecha;
    private javax.swing.JTextField jTextField_id_Articulo_Tela;
    private javax.swing.JTextField jTextField_id_suministro;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPanel lblcab;
    private javax.swing.JLabel lblcountipRop;
    private javax.swing.JTable table_preGuar;
    // End of variables declaration//GEN-END:variables
}
