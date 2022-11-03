package formularios;

import Clases.Conexion;
import Clases.limpiar;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_ENTER;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class frmArticulo extends javax.swing.JFrame {

    DefaultTableModel tabla = new DefaultTableModel();
    Conexion cn = new Conexion();
    limpiar lim = new limpiar();

    public frmArticulo() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/articulo.png")).getImage());
        nombresTitulos();
        cargarDatos();
        jTextField_id_articulo.setEnabled(false);
    }

    /*                             Complementarios
    ============================================================================
     */
    public void nombresTitulos() {
        String[] tit = {"ID Articulo", "FK Tipo Prenda", "FK Marca", "FK Modelo ", "FK Tallas ", "FK Material", "Precio", "Descrpcion", "Stock"};
        tabla.setColumnIdentifiers(tit);
        jTable1.setModel(tabla);
    }

    public void limpiarCampos() {
        jTextField_id_articulo.setText("");
        jTextField_fk_id_marca.setText("");
        jTextField_fk_id_modelo.setText("");
        jTextField_fk_id_talla.setText("");
        jTextField_fk_id_material.setText("");
        jTextField_buscar.setText("");
        jTextField_precio.setText("");
        jTextField_descripcion.setText("");
        jTextField1_stock.setText("");

    }

    public void llenarDatos() {
//        int filas;
//        int n = jTable1.getSelectedRow();
//        String opcion1="TIP001";
//        String opcion2="TIP002";
//        String opcion3="TIP003";
//        String opcion4="TIP004";
//        String opcion5="TIP005";
//        String opcion6="TIP006";
//        String opcion7="TIP007";
//        String opcion8="TIP008";
//        String opcion9="TIP009";
//        String opcion10="TIP010";
//        String opcion11="TIP011";
//        String opcion12="TIP012";
//        String opcion13="TIP013";
//        String opcion14="TIP014";
//        jTextField_id_articulo.setText(jTable1.getValueAt(n, 0).toString());
//        if(opcion1==jTable1.getValueAt(n, 1).toString()){
//            jComboBox2.setSelectedIndex(1);
//        }
//        if(opcion2==(jTable1.getValueAt(n, 1).toString())){
//            jComboBox2.setSelectedIndex(2);
//            System.out.println(opcion2);
//        }else if(opcion3==jTable1.getValueAt(n, 1).toString()){
//            jComboBox2.setSelectedIndex(3);
//        }else if(opcion4==jTable1.getValueAt(n, 1).toString()){
//            jComboBox2.setSelectedIndex(4);
//        }else if(opcion5==jTable1.getValueAt(n, 1).toString()){
//            jComboBox2.setSelectedIndex(5);
//        }else if(opcion6==jTable1.getValueAt(n, 1).toString()){
//            jComboBox2.setSelectedIndex(6);
//        }else if(opcion7==jTable1.getValueAt(n, 1).toString()){
//            jComboBox2.setSelectedIndex(7);
//        }else if(opcion8==jTable1.getValueAt(n, 1).toString()){
//            jComboBox2.setSelectedIndex(8);
//        }else if(opcion9==jTable1.getValueAt(n, 1).toString()){
//            jComboBox2.setSelectedIndex(9);
//        }else if(opcion10==jTable1.getValueAt(n, 1).toString()){
//            jComboBox2.setSelectedIndex(10);
//        }else if(opcion11==jTable1.getValueAt(n, 1).toString()){
//            jComboBox2.setSelectedIndex(11);
//        }else if(opcion12==jTable1.getValueAt(n, 1).toString()){
//            jComboBox2.setSelectedIndex(12);
//        }else if(opcion13==jTable1.getValueAt(n, 1).toString()){
//            jComboBox2.setSelectedIndex(13);
//        }else if(opcion14==jTable1.getValueAt(n, 1).toString()){
//            jComboBox2.setSelectedIndex(14);
//        }
//        
//        
//        jTextField_fk_id_marca.setText(jTable1.getValueAt(n, 2).toString());
//        jTextField_fk_id_modelo.setText(jTable1.getValueAt(n, 3).toString());
//        jTextField_fk_id_talla.setText(jTable1.getValueAt(n, 4).toString());
//        jTextField_fk_id_material.setText(jTable1.getValueAt(n, 5).toString());
//        jTextField_precio.setText(jTable1.getValueAt(n, 6).toString());
//        jTextField_descripcion.setText(jTable1.getValueAt(n, 7).toString());
//        jTextField1_stock.setText(jTable1.getValueAt(n, 8).toString());
//        filas = n;
    }

    public void reinicioDatos() {
        lim.limpiarTabla(jTable1, tabla);
        cargarDatos();
        limpiarCampos();
        jTextField_id_articulo.requestFocus();
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
            String sql = "select * from Articulo ";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {200, 200, 200, 200, 200, 200, 200, 200, 200};
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
            jTextField_id_articulo.setText(Integer.toString(i));
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
            ps = con.prepareStatement("insert into articulo(idarticulo,fk_idtipo_prenda,fk_idmarca,fk_idmodelo,fk_idTallas,precio,stock) values (?,?,?,?,?,?,?);");
            ps.setString(1, jTextField_id_articulo.getText());
            ps.setString(2, "TIP001");
            ps.setString(3, jTextField_fk_id_marca.getText());
            ps.setString(4, jTextField_fk_id_modelo.getText());
            ps.setString(5, jTextField_fk_id_talla.getText());          
            ps.setString(6, jTextField_precio.getText());
            ps.setString(7, jTextField1_stock.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardo la camisa con éxito");
            cargarDatos();
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
            ps = con.prepareStatement("insert into articulo(idarticulo,fk_idmodelo,fk_idmaterial,precio,stock) values (?,?,?,?,?,?);");
            ps.setString(1, jTextField_id_articulo.getText());
            ps.setString(2, "TIP002");
            ps.setString(3, jTextField_fk_id_modelo.getText());
            ps.setString(4, jTextField_fk_id_material.getText());          
            ps.setString(5, jTextField_precio.getText());
            ps.setString(6, jTextField1_stock.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardo la correa con éxito");
            cargarDatos();
            limpiarCampos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    public void guardarDatos3() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into articulo(idarticulo,fk_idtipo_prenda,fk_idmodelo,fk_idTallas,precio,stock) values (?,?,?,?,?,?);");
            ps.setString(1, jTextField_id_articulo.getText());
            ps.setString(2, "TIP003");
            ps.setString(3, jTextField_fk_id_modelo.getText());
            ps.setString(4, jTextField_fk_id_talla.getText());          
            ps.setString(5, jTextField_precio.getText());
            ps.setString(6, jTextField1_stock.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardo el blazer con éxito");
            cargarDatos();
            limpiarCampos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    public void guardarDatos4() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into articulo(idarticulo,fk_idtipo_prenda,fk_idTallas,precio,descripcion,stock) values (?,?,?,?,?,?);");
            ps.setString(1, jTextField_id_articulo.getText());
            ps.setString(2, "TIP004");
            ps.setString(3, jTextField_fk_id_talla.getText());
            ps.setString(4, jTextField_precio.getText());          
            ps.setString(5, jTextField_descripcion.getText());
            ps.setString(6, jTextField1_stock.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardo la casaca con éxito");
            cargarDatos();
            limpiarCampos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    public void guardarDatos5() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into articulo(idarticulo,fk_idtipo_prenda,fk_idTallas,precio,descripcion,stock) values (?,?,?,?,?,?);");
            ps.setString(1, jTextField_id_articulo.getText());
            ps.setString(2, "TIP005");
            ps.setString(3, jTextField_fk_id_talla.getText());
            ps.setString(4, jTextField_precio.getText());          
            ps.setString(5, jTextField_descripcion.getText());
            ps.setString(6, jTextField1_stock.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardo la chompa con éxito");
            cargarDatos();
            limpiarCampos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    public void guardarDatos6() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into articulo(idarticulo,fk_idtipo_prenda,fk_idTallas,precio,descripcion,stock) values (?,?,?,?,?,?);");
            ps.setString(1, jTextField_id_articulo.getText());
            ps.setString(2, "TIP006");
            ps.setString(3, jTextField_fk_id_talla.getText());
            ps.setString(4, jTextField_precio.getText());          
            ps.setString(5, jTextField_descripcion.getText());
            ps.setString(6, jTextField1_stock.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardo el abrigo con éxito");
            cargarDatos();
            limpiarCampos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    public void guardarDatos7() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into articulo(idarticulo,fk_idtipo_prenda,precio,descripcion,stock) values (?,?,?,?,?);");
            ps.setString(1, jTextField_id_articulo.getText());
            ps.setString(2, "TIP007");
            ps.setString(3, jTextField_precio.getText());          
            ps.setString(4, jTextField_descripcion.getText());
            ps.setString(5, jTextField1_stock.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardo la corbata con éxito");
            cargarDatos();
            limpiarCampos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    public void guardarDatos8() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into articulo(idarticulo,fk_idtipo_prenda,fk_idmodelo,fk_idTallas,fk_idmaterial,precio,stock) values (?,?,?,?,?,?,?);");
            ps.setString(1, jTextField_id_articulo.getText());
            ps.setString(2, "TIP008");
            ps.setString(3, jTextField_fk_id_modelo.getText());
            ps.setString(4, jTextField_fk_id_talla.getText());
            ps.setString(5, jTextField_fk_id_material.getText());
            ps.setString(6, jTextField_precio.getText());          
            ps.setString(7, jTextField1_stock.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardo el chaleco con éxito");
            cargarDatos();
            limpiarCampos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    public void guardarDatos9() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into articulo(idarticulo,fk_idtipo_prenda,fk_idmaterial,precio,stock) values (?,?,?,?,?);");
            ps.setString(1, jTextField_id_articulo.getText());
            ps.setString(2, "TIP009");
            ps.setString(3, jTextField_fk_id_material.getText());
            ps.setString(4, jTextField_precio.getText());          
            ps.setString(5, jTextField1_stock.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardo el maletin con éxito");
            cargarDatos();
            limpiarCampos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    public void guardarDatos10() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into articulo(idarticulo,fk_idtipo_prenda,fk_idmaterial,precio,stock) values (?,?,?,?,?);");
            ps.setString(1, jTextField_id_articulo.getText());
            ps.setString(2, "TIP010");
            ps.setString(3, jTextField_fk_id_material.getText());
            ps.setString(4, jTextField_precio.getText());          
            ps.setString(5, jTextField1_stock.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardo el morral con éxito");
            cargarDatos();
            limpiarCampos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    public void guardarDatos11() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into articulo(idarticulo,fk_idtipo_prenda,fk_idmodelo,fk_idTallas,precio,stock) values (?,?,?,?,?,?);");
            ps.setString(1, jTextField_id_articulo.getText());
            ps.setString(2, "TIP011");
            ps.setString(3, jTextField_fk_id_modelo.getText());
            ps.setString(4, jTextField_fk_id_talla.getText()); 
            ps.setString(5, jTextField_precio.getText());
            ps.setString(6, jTextField1_stock.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardo el pantalon con éxito");
            cargarDatos();
            limpiarCampos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    public void guardarDatos12() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into articulo(idarticulo,fk_idtipo_prenda,fk_idmodelo,precio,stock) values (?,?,?,?,?);");
            ps.setString(1, jTextField_id_articulo.getText());
            ps.setString(2, "TIP012");
            ps.setString(3, jTextField_fk_id_modelo.getText());
            ps.setString(4, jTextField_precio.getText()); 
            ps.setString(5, jTextField1_stock.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardo el relog con éxito");
            cargarDatos();
            limpiarCampos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    public void guardarDatos13() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into articulo(idarticulo,fk_idtipo_prenda,fk_idmodelo,precio,stock) values (?,?,?,?,?);");
            ps.setString(1, jTextField_id_articulo.getText());
            ps.setString(2, "TIP013");
            ps.setString(3, jTextField_fk_id_modelo.getText());
            ps.setString(4, jTextField_precio.getText()); 
            ps.setString(5, jTextField1_stock.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardo la billetera con éxito");
            cargarDatos();
            limpiarCampos();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e.toString());
        }
    }
    public void guardarDatos14() {
        PreparedStatement ps = null;
        try {
            Connection con = cn.conectar();
            ps = con.prepareStatement("insert into articulo(idarticulo,fk_idtipo_prenda,precio,descripcion,stock) values (?,?,?,?,?);");
            ps.setString(1, jTextField_id_articulo.getText());
            ps.setString(2, "TIP013");
            ps.setString(3, jTextField_precio.getText());
            ps.setString(4, jTextField_descripcion.getText()); 
            ps.setString(5, jTextField1_stock.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardo el tirante con éxito");
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

            ps = con.prepareStatement("delete from Articulo where idarticulo=?;");
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
            ps = con.prepareStatement("update Articulo set fk_idtipo_prenda=?,fk_idmarca=?,fk_idmodelo=?,fk_idTallas=?,fk_idmaterial=?,precio=?,descripcion=?,stock=?  where idarticulo=?;");
            ps.setString(1, "TIP001");
            ps.setString(2, jTextField_fk_id_marca.getText());
            ps.setString(3, jTextField_fk_id_modelo.getText());
            ps.setString(4, jTextField_fk_id_talla.getText());
            ps.setString(5, jTextField_fk_id_material.getText());
            ps.setString(6, jTextField_precio.getText());
            ps.setString(7, jTextField_descripcion.getText());
            ps.setString(8, jTextField1_stock.getText());
            ps.setString(9, jTextField_id_articulo.getText());
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
        if (jComboBox1.getSelectedItem() == "ID Articulo") {
            where = " where idarticulo='";
        } else if (jComboBox1.getSelectedItem() == "Precio") {
            where = " where precio='";
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
            String sql = "select * from Articulo" + where;
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsnd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsnd.getColumnCount();
            int anchos[] = {200, 200, 200, 200, 200,200,200,200,200};
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField_id_articulo = new javax.swing.JTextField();
        jTextField_fk_id_marca = new javax.swing.JTextField();
        jTextField_fk_id_modelo = new javax.swing.JTextField();
        jTextField_fk_id_talla = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField_fk_id_material = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField_precio = new javax.swing.JTextField();
        jTextField_descripcion = new javax.swing.JTextField();
        jTextField1_stock = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jToolBar2 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel12 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        lblcountipRop = new javax.swing.JLabel();
        lblcab = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField_buscar = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ARTICULOS");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Codigo:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 15, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Tipo:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 56, -1, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Marca:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 96, -1, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Modelo:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 133, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Talla :");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 15, -1, -1));

        jTextField_id_articulo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jPanel2.add(jTextField_id_articulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 10, 160, -1));

        jTextField_fk_id_marca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_fk_id_marcaKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_fk_id_marca, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 91, 160, -1));

        jTextField_fk_id_modelo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_fk_id_modeloKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_fk_id_modelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 128, 160, -1));

        jTextField_fk_id_talla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_fk_id_tallaKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_fk_id_talla, new org.netbeans.lib.awtextra.AbsoluteConstraints(424, 10, 160, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Material :");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 56, -1, -1));

        jTextField_fk_id_material.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_fk_id_materialKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_fk_id_material, new org.netbeans.lib.awtextra.AbsoluteConstraints(424, 51, 160, -1));

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
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 219, -1, -1));

        jButton7.setBackground(new java.awt.Color(51, 153, 255));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/actualizar (2).png"))); // NOI18N
        jButton7.setText("Actualizar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(344, 219, -1, 30));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Precio :");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 96, -1, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("Descripcion :");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 173, -1, -1));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("Stock :");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 133, -1, -1));

        jTextField_precio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_precioKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(424, 91, 86, -1));
        jPanel2.add(jTextField_descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 168, 441, -1));

        jTextField1_stock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1_stockKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField1_stock, new org.netbeans.lib.awtextra.AbsoluteConstraints(424, 128, 86, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione...", "Camisa", "Correa", "Blazer", "Casaca", "Chompa", "Abrigo", "Corbata", "Chaleco", "Maletin", "Morral", "Pantalon", "Relog", "Billetera", "Tirante" }));
        jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox2MouseClicked(evt);
            }
        });
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 51, 160, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 610, 270));

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

        jLabel12.setText("Cantidad de Registros:");
        jToolBar2.add(jLabel12);
        jToolBar2.add(jSeparator1);

        lblcountipRop.setText("0");
        jToolBar2.add(lblcountipRop);

        jPanel1.add(jToolBar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 780, 40));

        lblcab.setBackground(new java.awt.Color(204, 204, 204));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("ARTICULO");

        javax.swing.GroupLayout lblcabLayout = new javax.swing.GroupLayout(lblcab);
        lblcab.setLayout(lblcabLayout);
        lblcabLayout.setHorizontalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblcabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(712, Short.MAX_VALUE))
        );
        lblcabLayout.setVerticalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(lblcab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 30));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID Articulo", "Precio", " " }));
        jComboBox1.setToolTipText("");
        jPanel4.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 100, -1));
        jPanel4.add(jTextField_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 330, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 510, 40));

        jButton5.setBackground(new java.awt.Color(51, 51, 255));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/buscar (1).png"))); // NOI18N
        jButton5.setText("Buscar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 350, 110, 40));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 660, 200));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 650));

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

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if(jComboBox2.getSelectedItem()=="Camisa"){
            guardarDatos();
        } if(jComboBox2.getSelectedItem()=="Correa"){
            guardarDatos2();
        }if(jComboBox2.getSelectedItem()=="Blazer"){
            guardarDatos3();
        }if(jComboBox2.getSelectedItem()=="Casaca"){
            guardarDatos4();
        }if(jComboBox2.getSelectedItem()=="Chompa"){
            guardarDatos5();
        }if(jComboBox2.getSelectedItem()=="Abrigo"){
            guardarDatos6();
        }if(jComboBox2.getSelectedItem()=="Corbata"){
            guardarDatos7();
        }if(jComboBox2.getSelectedItem()=="Chaleco"){
            guardarDatos8();
        }if(jComboBox2.getSelectedItem()=="Maletin"){
            guardarDatos9();
        }if(jComboBox2.getSelectedItem()=="Morral"){
            guardarDatos10();
        }if(jComboBox2.getSelectedItem()=="Pantalon"){
            guardarDatos11();
        }if(jComboBox2.getSelectedItem()=="Relog"){
            guardarDatos12();
        }if(jComboBox2.getSelectedItem()=="Billetera"){
            guardarDatos13();
        }if(jComboBox2.getSelectedItem()=="Tirante"){
            guardarDatos14();
        }
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        buscarDatos();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        reinicioDatos();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        llenarDatos();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jComboBox2MouseClicked

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        if(jComboBox2.getSelectedItem()=="Camisa"){
            jTextField_descripcion.setEditable(false);
            jTextField_fk_id_material.setEditable(false);
            
        } if(jComboBox2.getSelectedItem()=="Correa") {
            jTextField_fk_id_marca.setEditable(false);
            jTextField_fk_id_talla.setEditable(false);
            jTextField_descripcion.setEditable(false);
        }if(jComboBox2.getSelectedItem()=="Blazer") {
            jTextField_fk_id_marca.setEditable(false);
            jTextField_fk_id_material.setEditable(false);
            jTextField_descripcion.setEditable(false);
            jTextField_fk_id_modelo.requestFocus();
        }if(jComboBox2.getSelectedItem()=="Casaca") {
            jTextField_fk_id_marca.setEditable(false);
            jTextField_fk_id_modelo.setEditable(false);
            jTextField_fk_id_material.setEditable(false);
            jTextField_descripcion.requestFocus();
        }if(jComboBox2.getSelectedItem()=="Chompa") {
            jTextField_fk_id_marca.setEditable(false);
            jTextField_fk_id_modelo.setEditable(false);
            jTextField_fk_id_material.setEditable(false);
            jTextField_descripcion.requestFocus();
        }if(jComboBox2.getSelectedItem()=="Abrigo") {
            jTextField_fk_id_marca.setEditable(false);
            jTextField_fk_id_modelo.setEditable(false);
            jTextField_fk_id_material.setEditable(false);
            jTextField_descripcion.requestFocus();
        }if(jComboBox2.getSelectedItem()=="Corbata") {
            jTextField_fk_id_marca.setEditable(false);
            jTextField_fk_id_modelo.setEditable(false);
            jTextField_fk_id_material.setEditable(false);
            jTextField_fk_id_talla.setEditable(false);
            jTextField_descripcion.requestFocus();
        }if(jComboBox2.getSelectedItem()=="Chaleco") {
            jTextField_descripcion.setEditable(false);
            jTextField_fk_id_marca.requestFocus();
        }if(jComboBox2.getSelectedItem()=="Maletin") {
            jTextField_descripcion.setEditable(false);
            jTextField_fk_id_marca.setEditable(false);
            jTextField_fk_id_modelo.setEditable(false);
            jTextField_fk_id_talla.setEditable(false);
            jTextField_fk_id_material.requestFocus();
        }if(jComboBox2.getSelectedItem()=="Morral") {
            jTextField_descripcion.setEditable(false);
            jTextField_fk_id_marca.setEditable(false);
            jTextField_fk_id_modelo.setEditable(false);
            jTextField_fk_id_talla.setEditable(false);
            jTextField_fk_id_material.requestFocus();
        }if(jComboBox2.getSelectedItem()=="Pantalon") {
            jTextField_descripcion.setEditable(false);
            jTextField_fk_id_marca.setEditable(false);
            jTextField_fk_id_material.setEditable(false);
            jTextField_fk_id_modelo.requestFocus();
        }if(jComboBox2.getSelectedItem()=="Relog") {
            jTextField_descripcion.setEditable(false);
            jTextField_fk_id_marca.setEditable(false);
            jTextField_fk_id_talla.setEditable(false);
            jTextField_fk_id_material.setEditable(false);
            jTextField_fk_id_modelo.requestFocus();
        }if(jComboBox2.getSelectedItem()=="Billetera") {
            jTextField_descripcion.setEditable(false);
            jTextField_fk_id_marca.setEditable(false);
            jTextField_fk_id_talla.setEditable(false);
            jTextField_fk_id_material.setEditable(false);
            jTextField_fk_id_modelo.requestFocus();
        }if(jComboBox2.getSelectedItem()=="Tirante") {
            jTextField_fk_id_modelo.setEditable(false);
            jTextField_fk_id_marca.setEditable(false);
            jTextField_fk_id_talla.setEditable(false);
            jTextField_fk_id_material.setEditable(false);
            jTextField_descripcion.requestFocus();
        }
//        
        
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTextField_precioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_precioKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
            
        }
    }//GEN-LAST:event_jTextField_precioKeyTyped

    private void jTextField1_stockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1_stockKeyTyped
        // TODO add your handling code here:
         char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
            
        }
    }//GEN-LAST:event_jTextField1_stockKeyTyped

    private void jTextField_fk_id_marcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_fk_id_marcaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextField_fk_id_marcaKeyTyped

    private void jTextField_fk_id_modeloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_fk_id_modeloKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextField_fk_id_modeloKeyTyped

    private void jTextField_fk_id_tallaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_fk_id_tallaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextField_fk_id_tallaKeyTyped

    private void jTextField_fk_id_materialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_fk_id_materialKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextField_fk_id_materialKeyTyped

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
            java.util.logging.Logger.getLogger(frmArticulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmArticulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmArticulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmArticulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmArticulo().setVisible(true);
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
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1_stock;
    private javax.swing.JTextField jTextField_buscar;
    private javax.swing.JTextField jTextField_descripcion;
    private javax.swing.JTextField jTextField_fk_id_marca;
    private javax.swing.JTextField jTextField_fk_id_material;
    private javax.swing.JTextField jTextField_fk_id_modelo;
    private javax.swing.JTextField jTextField_fk_id_talla;
    private javax.swing.JTextField jTextField_id_articulo;
    private javax.swing.JTextField jTextField_precio;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPanel lblcab;
    private javax.swing.JLabel lblcountipRop;
    // End of variables declaration//GEN-END:variables
}
