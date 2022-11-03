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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author USUARIO
 */
public class JDialogInventario extends javax.swing.JDialog {
    DefaultTableModel tabla = new DefaultTableModel();
    DefaultTableModel tabla2 = new DefaultTableModel();
    Conexion cn = new Conexion();
    limpiar lim = new limpiar();

    /**
     * Creates new form JDialogInventario
     */
    public JDialogInventario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/inventario.png")).getImage());
        initComponents();
        nombresTitulos();
        cargarDatos();
        nombresTitulos2();
        cargarDatos2();
    }
     public void nombresTitulos() {
        String[] tit = {"Codigo", "Tipo Prenda", "Marca", "Modelo ", "Tallas ", "Material","Descrpcion","Precio", "Stock"};
        tabla.setColumnIdentifiers(tit);
        tbl_inventario.setModel(tabla);
    }
      public void limpiarCampos() {
        txt_busc.setText("");
      }
    public void reinicioDatos() {
        lim.limpiarTabla(tbl_inventario, tabla);
        cargarDatos();
        limpiarCampos();
        txt_busc.requestFocus();
    }
     public void cargarDatos() {
        try {
            lim.limpiarTabla(tbl_inventario, tabla);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = cn.conectar();
            String sql = "select * from v_Productos";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {200, 200, 200, 200, 200, 200, 200, 200, 200};
            for (int i = 0; i < tbl_inventario.getColumnCount(); i++) {
                tbl_inventario.getColumnModel().getColumn(i).setPreferredWidth(anchosEs[i]);

            }
            while (rs.next()) {
                Object[] filas = new Object[canatidadColumnasEs];
                for (int i = 0; i < canatidadColumnasEs; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                tabla.addRow(filas);
            }
            int filas = tabla.getRowCount();
            lbl_count.setText(String.valueOf(filas));
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
     public void buscarTalla(){
        String aux=jComboBox1.getSelectedItem().toString();
        String campo=txt_busc.getText();
        String where="";
        if(!"".equals(campo)){
            where="WHERE idarticulo = "+campo+" and tipo_prenda = '"+aux+"'";
            System.out.println(where);
        }
        try {
            tbl_inventario.setModel(tabla);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="SELECT idarticulo,tipo_prenda,marca,nom_modelo,talla,material,descripcion,precio,stock FROM v_productos "+where;
            System.out.println(sql);
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas=rsMd.getColumnCount();
            int []anchos={200, 200, 200, 200, 200, 200, 200, 200, 200};
            for(int i=0;i<tbl_inventario.getColumnCount();i++){
                tbl_inventario.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            lim.limpiarTabla(tbl_inventario, tabla);
            while(rs.next()){
                Object[] filas=new Object[cantidadColumnas];
                for(int i=0;i<cantidadColumnas;i++){
                    filas[i]=rs.getObject(i+1);
                    
                }
                tabla.addRow(filas);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    public void reiniciar(){
        lim.limpiarTabla(tbl_inventario, tabla);
        jComboBox1.setSelectedIndex(0);
        cargarDatos();
        limpiarCampos();
        txt_busc.requestFocus();
    }
    
    // Tela
     public void nombresTitulos2() {
        String[] tit = {"ID MODELO TELA", "MARCA", "NOMBRE MODELO", "PRECIO", "STOCK (METROS)"};
        tabla2.setColumnIdentifiers(tit);
        tbl_Tela.setModel(tabla2);
    }
      public void limpiarCampos2() {
        txt_buscTela.setText("");
      }
    public void reinicioDatos2() {
        lim.limpiarTabla(tbl_Tela, tabla2);
        cargarDatos();
        limpiarCampos();
        txt_buscTela.requestFocus();
    }
     public void cargarDatos2() {
        try {
            lim.limpiarTabla(tbl_Tela, tabla2);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = cn.conectar();
            String sql = "select * from v_ModeloTela";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int canatidadColumnasEs = rsMd.getColumnCount();
            int[] anchosEs = {200, 300, 300, 200, 200};
            for (int i = 0; i < tbl_Tela.getColumnCount(); i++) {
                tbl_Tela.getColumnModel().getColumn(i).setPreferredWidth(anchosEs[i]);

            }
            while (rs.next()) {
                Object[] filas = new Object[canatidadColumnasEs];
                for (int i = 0; i < canatidadColumnasEs; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                tabla2.addRow(filas);
            }
            int filas = tabla2.getRowCount();
            lbl_count1.setText(String.valueOf(filas));
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
      public void buscarTela(){
        String aux=jComboBoxMarca.getSelectedItem().toString();
        String aux2=jComboBoxModelo.getSelectedItem().toString();
        String campo=txt_buscTela.getText();
        String where="";
        if(!"".equals(campo)){
            where="WHERE idmodelo_tela= '"+campo+"' and marca= '"+aux+"' and nombre_modelo= '"+aux2+"'";
            
        }
        try {
            tbl_Tela.setModel(tabla2);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="select idmodelo_tela,marca,nombre_modelo,precio,metros from v_ModeloTela "+where;
            System.out.println(sql);
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas=rsMd.getColumnCount();
            int []anchos={200, 300, 300, 200, 200};
            for(int i=0;i<tbl_Tela.getColumnCount();i++){
                tbl_Tela.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            lim.limpiarTabla(tbl_Tela, tabla2);
            while(rs.next()){
                Object[] filas=new Object[cantidadColumnas];
                for(int i=0;i<cantidadColumnas;i++){
                    filas[i]=rs.getObject(i+1);
                    
                }
                tabla2.addRow(filas);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    public void reiniciar2(){
        lim.limpiarTabla(tbl_Tela, tabla2);
        jComboBoxMarca.setSelectedIndex(0);
        jComboBoxModelo.setSelectedIndex(0);
        cargarDatos2();
        limpiarCampos2();
        txt_buscTela.requestFocus();
    }
    
     public void reported(){
        try {
            Conexion conn=new Conexion();
            Connection con=conn.conectar();
            JasperReport reporte=null;
            String path="src\\Reportes\\rInventarioArt.jasper";
            Map parametro=new HashMap();
            int num;
            num=Integer.parseInt(txt_busc.getText());
            parametro.put("p_art", num);
            parametro.put("p_tipo", jComboBox1.getSelectedItem().toString());
            reporte=(JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint=JasperFillManager.fillReport(reporte,parametro,con);
            JasperViewer view=new JasperViewer(jprint,false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            
        } catch (Exception e) {
            Logger.getLogger(JDialogInventario.class.getName()).log(Level.SEVERE, null, e);
        }
    }
     
     public void reportedTelas(){
        try {
            Conexion conn=new Conexion();
            Connection con=conn.conectar();
            JasperReport reporte=null;
            String path="src\\Reportes\\rInventarioTela.jasper";
            Map parametro=new HashMap();
            parametro.put("p_cod", txt_buscTela.getText());
            parametro.put("p_marc", jComboBoxMarca.getSelectedItem().toString());
            parametro.put("p_mod", jComboBoxModelo.getSelectedItem().toString());
            reporte=(JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint=JasperFillManager.fillReport(reporte,parametro,con);
            JasperViewer view=new JasperViewer(jprint,false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            
        } catch (Exception e) {
            Logger.getLogger(JDialogInventario.class.getName()).log(Level.SEVERE, null, e);
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

        pnl_cab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        pnel_all = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_inventario = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel4 = new javax.swing.JLabel();
        txt_busc = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jComboBox1 = new javax.swing.JComboBox<>();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        lbl_count = new javax.swing.JLabel();
        btn_imprimirArt = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        pnel_all1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_Tela = new javax.swing.JTable();
        jToolBar2 = new javax.swing.JToolBar();
        jLabel6 = new javax.swing.JLabel();
        txt_buscTela = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        jLabel7 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        jComboBoxMarca = new javax.swing.JComboBox<>();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        jLabel1 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JToolBar.Separator();
        jComboBoxModelo = new javax.swing.JComboBox<>();
        jSeparator14 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        jButton5 = new javax.swing.JButton();
        jSeparator11 = new javax.swing.JToolBar.Separator();
        jLabel8 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JToolBar.Separator();
        lbl_count1 = new javax.swing.JLabel();
        btn_imprimirConfec = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("INVENTARIO");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_cab.setBackground(new java.awt.Color(0, 153, 102));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Inventario");

        javax.swing.GroupLayout pnl_cabLayout = new javax.swing.GroupLayout(pnl_cab);
        pnl_cab.setLayout(pnl_cabLayout);
        pnl_cabLayout.setHorizontalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(850, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        getContentPane().add(pnl_cab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, -1));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        pnel_all.setBackground(new java.awt.Color(255, 255, 255));
        pnel_all.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_inventario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tbl_inventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbl_inventario);

        pnel_all.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 890, 420));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setRollover(true);
        jToolBar1.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Buscar por Código:");
        jToolBar1.add(jLabel4);

        txt_busc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_buscActionPerformed(evt);
            }
        });
        txt_busc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_buscKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscKeyReleased(evt);
            }
        });
        jToolBar1.add(txt_busc);
        jToolBar1.add(jSeparator4);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Tipo de articulo:");
        jToolBar1.add(jLabel2);
        jToolBar1.add(jSeparator2);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione...                 ", "CAMISAS", "CORREAS", "BLAZER", "CHOMPAS", "ABRIGOS", "CHALECO", "MALETIN", "MORRAL", "PANTALONES", "RELOG", "BILLETERA", "TIRANTES", "CASACAS" }));
        jToolBar1.add(jComboBox1);
        jToolBar1.add(jSeparator5);

        jButton2.setBackground(new java.awt.Color(102, 102, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        jButton2.setText("Buscar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);
        jToolBar1.add(jSeparator6);

        jButton4.setBackground(new java.awt.Color(51, 153, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Ver Todo");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);
        jToolBar1.add(jSeparator3);

        jLabel5.setText("Cantidad de Productos:");
        jToolBar1.add(jLabel5);
        jToolBar1.add(jSeparator1);

        lbl_count.setText("0");
        jToolBar1.add(lbl_count);

        pnel_all.add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 900, 30));

        btn_imprimirArt.setBackground(new java.awt.Color(26, 81, 115));
        btn_imprimirArt.setForeground(new java.awt.Color(255, 255, 255));
        btn_imprimirArt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/impre.png"))); // NOI18N
        btn_imprimirArt.setText("Imprimir");
        btn_imprimirArt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirArtActionPerformed(evt);
            }
        });
        pnel_all.add(btn_imprimirArt, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 460, 110, 30));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnel_all, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnel_all, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Articulos", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        pnel_all1.setBackground(new java.awt.Color(255, 255, 255));
        pnel_all1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_Tela.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tbl_Tela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tbl_Tela);

        pnel_all1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 890, 420));

        jToolBar2.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar2.setRollover(true);
        jToolBar2.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Buscar por Código:");
        jToolBar2.add(jLabel6);

        txt_buscTela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_buscTelaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscTelaKeyReleased(evt);
            }
        });
        jToolBar2.add(txt_buscTela);
        jToolBar2.add(jSeparator7);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Marca");
        jToolBar2.add(jLabel7);
        jToolBar2.add(jSeparator8);

        jComboBoxMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione...    ", "CASIMIR", "LANILLA", "CARDIFF", "BARRINTON" }));
        jToolBar2.add(jComboBoxMarca);
        jToolBar2.add(jSeparator9);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Modelo");
        jToolBar2.add(jLabel1);
        jToolBar2.add(jSeparator13);

        jComboBoxModelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione...", "GOLD COLOR ENTERO", "GOLD RAYAS", "SUPER 100", "MANCHESTER", "BARTON", "TACTO EN LANA", "GOLD", "BARRINTON", "CARDIFF", "RICHWOOL", "SUPER 100", "SUPER 120" }));
        jToolBar2.add(jComboBoxModelo);
        jToolBar2.add(jSeparator14);

        jButton3.setBackground(new java.awt.Color(102, 102, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        jButton3.setText("Buscar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton3);
        jToolBar2.add(jSeparator10);

        jButton5.setBackground(new java.awt.Color(51, 153, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Ver Todo");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton5);
        jToolBar2.add(jSeparator11);

        jLabel8.setText("Cantidad de Productos:");
        jToolBar2.add(jLabel8);
        jToolBar2.add(jSeparator12);

        lbl_count1.setText("0");
        jToolBar2.add(lbl_count1);

        pnel_all1.add(jToolBar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 900, 30));

        btn_imprimirConfec.setBackground(new java.awt.Color(26, 81, 115));
        btn_imprimirConfec.setForeground(new java.awt.Color(255, 255, 255));
        btn_imprimirConfec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/impre.png"))); // NOI18N
        btn_imprimirConfec.setText("Imprimir");
        btn_imprimirConfec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirConfecActionPerformed(evt);
            }
        });
        pnel_all1.add(btn_imprimirConfec, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 460, 110, 30));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(pnel_all1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(pnel_all1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Telas", jPanel2);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 910, 550));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_buscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscKeyPressed

    }//GEN-LAST:event_txt_buscKeyPressed

    private void txt_buscKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_buscKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        buscarTalla();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        reiniciar();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btn_imprimirArtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirArtActionPerformed
        // TODO add your handling code here:
        //  btn_imprimir
        reported();

    }//GEN-LAST:event_btn_imprimirArtActionPerformed

    private void txt_buscTelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscTelaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_buscTelaKeyPressed

    private void txt_buscTelaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscTelaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_buscTelaKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        buscarTela();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        reiniciar2();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btn_imprimirConfecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirConfecActionPerformed
        // TODO add your handling code here:
        reportedTelas();
    }//GEN-LAST:event_btn_imprimirConfecActionPerformed

    private void txt_buscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_buscActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_buscActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogInventario dialog = new JDialogInventario(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_imprimirArt;
    private javax.swing.JButton btn_imprimirConfec;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBoxMarca;
    private javax.swing.JComboBox<String> jComboBoxModelo;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JToolBar.Separator jSeparator11;
    private javax.swing.JToolBar.Separator jSeparator12;
    private javax.swing.JToolBar.Separator jSeparator13;
    private javax.swing.JToolBar.Separator jSeparator14;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lbl_count;
    private javax.swing.JLabel lbl_count1;
    private javax.swing.JPanel pnel_all;
    private javax.swing.JPanel pnel_all1;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JTable tbl_Tela;
    private javax.swing.JTable tbl_inventario;
    private javax.swing.JTextField txt_busc;
    private javax.swing.JTextField txt_buscTela;
    // End of variables declaration//GEN-END:variables
}
