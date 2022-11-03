/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import Clases.Conexion;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.chrono.JapaneseEra;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;





/**
 *
 * @author Acer
 */
public class Medida_saco extends javax.swing.JFrame {
    DefaultTableModel model=  new DefaultTableModel();
    Conexion cn = new Conexion();
    
    
    
    
    /**
     * Creates new form Medida_saco
     */
    public Medida_saco() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/saco.png")).getImage());
        titulos();
        cargarmalla();
        jFormattedCod.requestFocus();
    }
    public void titulos(){
        
     String[] titulo = {"ID SACO","CINTURA","CADERA","ESPALDA","TALLE","HOMBRO","MANGA","LARGO","PECHO","ALTO DE BUSTO","SEPARACION"};
        model.setColumnIdentifiers(titulo);
      jTable1.setModel(model);
    }
      public void limpiarTabla (JTable tb, DefaultTableModel md) {
      while(tb.getRowCount()>0){md.removeRow(0);}
   }
    
   public void cargarmalla(){
        try {
           limpiarTabla(jTable1 ,model);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar() ;
            String sql="select*from medidasaco;";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadcolumnas=rsMd.getColumnCount();
            int [] anchos={50,50,50,50,50,50,50,50,50,50,50};
            for(int i=0;i<jTable1.getColumnCount();i++){
                jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            while(rs.next()){
                Object[] filas=new  Object[cantidadcolumnas];
                for(int i=0;i<cantidadcolumnas;i++){
                    filas[i]=rs.getObject(i+1);
                }
                model.addRow(filas);
            }
            int filas = model.getRowCount();
            lblcountipRop.setText(String.valueOf(filas));
            int i=jTable1.getRowCount()+1;
            
            
            
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
  }
   public void insertdepartamentos(){
        PreparedStatement ps=null;
        try {
          Connection con=cn.conectar();
          ps=con.prepareStatement("call p_insertMs(?,?,?,?,?,?,?,?,?,?,?)");
          
          ps.setString(1,jFormattedCod.getText());
          ps.setString(2,jTextFieldCintura.getText());
           ps.setString(3,jTextFieldCadera.getText());
            ps.setString(4,jTextFieldEspalda.getText());
            ps.setString(5,jTextFieldTalle.getText());
            ps.setString(6,jTextFieldHombro.getText());
            ps.setString(7,jTextFieldManga.getText());
            ps.setString(8,jTextFieldLargo.getText());
             ps.setString(9,jTextFieldPecho1.getText());     
              ps.setString(10,jTextFieldBusto.getText());
                ps.setString(11,jTextFieldSeparacion.getText());
          ps.execute();
          JOptionPane.showMessageDialog(null, "Medida saco guaradado!!");
          cargarmalla();
          limpiar();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar registro!!");
            System.out.println(e);
        }
    }
    
    public void cargarDatos(){
        int filas;
        int n=jTable1.getSelectedRow();
        jFormattedCod.setText(jTable1.getValueAt(n, 0).toString());
        jTextFieldCintura.setText(jTable1.getValueAt(n, 1).toString());
        jTextFieldCadera.setText(jTable1.getValueAt(n, 2).toString());
        jTextFieldEspalda.setText(jTable1.getValueAt(n, 3).toString());
        jTextFieldTalle.setText(jTable1.getValueAt(n, 4).toString());
        jTextFieldHombro.setText(jTable1.getValueAt(n, 5).toString());
        jTextFieldManga.setText(jTable1.getValueAt(n, 6).toString());
        jTextFieldLargo.setText(jTable1.getValueAt(n, 7).toString());
        jTextFieldPecho1.setText(jTable1.getValueAt(n, 8).toString());
        jTextFieldBusto.setText(jTable1.getValueAt(n, 9).toString());
        jTextFieldSeparacion.setText(jTable1.getValueAt(n, 10).toString());
        filas=n;
    }
     public void limpiar(){
         jFormattedCod.setText("");
         jTextFieldCintura.setText("");
         jTextFieldCadera.setText("");
         jTextFieldEspalda.setText("");
         jTextFieldTalle.setText("");
         jTextFieldHombro.setText("");
         jTextFieldManga.setText("");
         jTextFieldLargo.setText("");
         jTextFieldPecho1.setText("");
         jTextFieldBusto.setText("");
         jTextFieldSeparacion.setText("");
         jFormattedCod.requestFocus();
    }
    
     public void eliminar(){
        PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
            int fila=jTable1.getSelectedRow();
            String codigo=jTable1.getValueAt(fila, 0).toString();
            
            ps=con.prepareStatement("call p_deleteMs(?);");
            ps.setString(1, codigo);
            
            ps.execute();
            model.removeRow(fila);
            JOptionPane.showMessageDialog(null, "Registro elimanado con exito");
            limpiar();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en eliminacion!");
            System.out.println(e.toString());
        }
        cargarmalla();
    }
    public void modifidepartamneto(){
        int fila=jTable1.getSelectedRow()+1;
        PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
           
            
            ps=con.prepareStatement("call p_updateMs(?,?,?,?,?,?,?,?,?,?,?);");
         
           ps.setString(1,jFormattedCod.getText());
          ps.setString(2,jTextFieldCintura.getText());
           ps.setString(3,jTextFieldCadera.getText());
            ps.setString(4,jTextFieldEspalda.getText());
            ps.setString(5,jTextFieldTalle.getText());
            ps.setString(6,jTextFieldHombro.getText());
            ps.setString(7,jTextFieldManga.getText());
            ps.setString(8,jTextFieldLargo.getText());
             ps.setString(9,jTextFieldPecho1.getText());     
              ps.setString(10,jTextFieldBusto.getText());
                ps.setString(11,jTextFieldSeparacion.getText());
            ps.execute();
            
            JOptionPane.showMessageDialog(null, "Registro actualizado con exito");
            limpiar();
            cargarmalla();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en actualizacion!");
            System.out.println(e.toString());
        }
        
    }
      public void buscarCiclo(){
        String aux=jComboBox1.getSelectedItem().toString();
        String campo=jTextFieldbuscar.getText();
        String where="";
        if(!"".equals(campo)){
            where="WHERE "+aux+"='"+campo+"'";
            
        }
        try {
            jTable1.setModel(model);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="SELECT idMedidasaco,cintura,cadera,espalda,talle,hombro,manga,largo,pecho,busto,separacion FROM medidasaco "+where;
            System.out.println(sql);
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas=rsMd.getColumnCount();
            int [] anchos={50,50,50,50,50,50,50,50,50,50,50};
            for(int i=0;i<jTable1.getColumnCount();i++){
                jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            limpiarTabla(jTable1, model);
            while(rs.next()){
                Object[] filas=new Object[cantidadColumnas];
                for(int i=0;i<cantidadColumnas;i++){
                    filas[i]=rs.getObject(i+1);
                    
                }
                model.addRow(filas);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    
    public void reniciar(){
    
        limpiarTabla(jTable1, model);
        cargarmalla();
        limpiar();
        jFormattedCod.requestFocus();
         jComboBox1.setSelectedIndex(0);
         jTextFieldbuscar.setText("");
    
    }

    
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        jButtonReniciar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel18 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        lblcountipRop = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jFormattedCod = new javax.swing.JFormattedTextField();
        jButtonAgregar = new javax.swing.JButton();
        jButtonActualizar = new javax.swing.JButton();
        jTextFieldCintura = new javax.swing.JTextField();
        jTextFieldCadera = new javax.swing.JTextField();
        jTextFieldEspalda = new javax.swing.JTextField();
        jTextFieldTalle = new javax.swing.JTextField();
        jTextFieldHombro = new javax.swing.JTextField();
        jTextFieldPecho1 = new javax.swing.JTextField();
        jTextFieldBusto = new javax.swing.JTextField();
        jTextFieldSeparacion = new javax.swing.JTextField();
        jTextFieldLargo = new javax.swing.JTextField();
        jTextFieldManga = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblcab = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jButtonbuscar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jTextFieldbuscar = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("REGISTRO DE SACO");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jToolBar3.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar3.setRollover(true);

        jButtonReniciar.setBackground(new java.awt.Color(102, 204, 255));
        jButtonReniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos del proyecto final/rehacer.png"))); // NOI18N
        jButtonReniciar.setText("Reniciar");
        jButtonReniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReniciarActionPerformed(evt);
            }
        });
        jToolBar3.add(jButtonReniciar);

        jButtonEliminar.setBackground(new java.awt.Color(102, 204, 255));
        jButtonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos del proyecto final/eliminar.png"))); // NOI18N
        jButtonEliminar.setText("Eliminar");
        jButtonEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });
        jToolBar3.add(jButtonEliminar);

        jButtonSalir.setBackground(new java.awt.Color(102, 204, 255));
        jButtonSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos del proyecto final/puerta-de-salida.png"))); // NOI18N
        jButtonSalir.setText("Salir");
        jButtonSalir.setFocusable(false);
        jButtonSalir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jToolBar3.add(jButtonSalir);

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jToolBar3.add(jSeparator2);

        jLabel18.setText("Cantidad de Registros:");
        jToolBar3.add(jLabel18);

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jToolBar3.add(jSeparator1);

        lblcountipRop.setText("0");
        jToolBar3.add(lblcountipRop);

        jPanel1.add(jToolBar3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 710, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("Codigo:");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setText("Cintura:");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 60, 40));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setText("Cadera:");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 49, 20));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setText("Espalda:");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 49, -1));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setText("Alto de busto:");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, 80, 20));

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setText("Pecho:");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, -1, 30));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Talle:");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 37, 20));

        jLabel19.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel19.setText("Separación:");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 180, 84, 30));

        jLabel20.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel20.setText("Hombro:");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, 20));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos del proyecto final/sacos-gamarra-02-Mckeover (1).png"))); // NOI18N
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 40, 140, -1));

        try {
            jFormattedCod.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("MES###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedCodKeyPressed(evt);
            }
        });
        jPanel4.add(jFormattedCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 118, -1));

        jButtonAgregar.setBackground(new java.awt.Color(255, 102, 0));
        jButtonAgregar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos del proyecto final/agregar-archivo (4).png"))); // NOI18N
        jButtonAgregar.setText("Agregar");
        jButtonAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, -1, -1));

        jButtonActualizar.setBackground(new java.awt.Color(51, 153, 255));
        jButtonActualizar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos del proyecto final/actualizar (2).png"))); // NOI18N
        jButtonActualizar.setText("Actualizar");
        jButtonActualizar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualizarActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 260, -1, -1));

        jTextFieldCintura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCinturaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCinturaKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldCintura, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 120, -1));

        jTextFieldCadera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCaderaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCaderaKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldCadera, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 118, -1));

        jTextFieldEspalda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldEspaldaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldEspaldaKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldEspalda, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 118, -1));

        jTextFieldTalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldTalleKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldTalleKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldTalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 120, -1));

        jTextFieldHombro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldHombroKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldHombroKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldHombro, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, 120, -1));

        jTextFieldPecho1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldPecho1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPecho1KeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldPecho1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, 118, -1));

        jTextFieldBusto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBustoActionPerformed(evt);
            }
        });
        jTextFieldBusto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldBustoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldBustoKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldBusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 118, -1));

        jTextFieldSeparacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldSeparacionKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldSeparacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, 118, -1));

        jTextFieldLargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLargoActionPerformed(evt);
            }
        });
        jTextFieldLargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldLargoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldLargoKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldLargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, 118, -1));

        jTextFieldManga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldMangaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldMangaKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldManga, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 118, -1));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setText("Manga: ");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, 60, -1));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel15.setText("Largo:");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, -1, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 670, 310));

        lblcab.setBackground(new java.awt.Color(204, 204, 204));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("MEDIDAS SACO");

        javax.swing.GroupLayout lblcabLayout = new javax.swing.GroupLayout(lblcab);
        lblcab.setLayout(lblcabLayout);
        lblcabLayout.setHorizontalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblcabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        lblcabLayout.setVerticalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(lblcab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 30));

        jButtonbuscar.setBackground(new java.awt.Color(51, 51, 255));
        jButtonbuscar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos del proyecto final/buscar (1).png"))); // NOI18N
        jButtonbuscar.setText("Buscar");
        jButtonbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonbuscarActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonbuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 400, 110, 40));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel5.add(jTextFieldbuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 279, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione...", "idMedidasaco", "cintura", "cadera", "espalda", "talle", "hombro", "manga", "largo", "pecho", "busto", "separacion" }));
        jPanel5.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 136, -1));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, 490, 40));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 670, 160));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Confecciones Y&L");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 610, 160, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldLargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLargoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLargoActionPerformed

    private void jTextFieldBustoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBustoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBustoActionPerformed

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
        // TODO add your handling code here:
        insertdepartamentos();
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        cargarDatos();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        // TODO add your handling code here:
        eliminar();
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonReniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReniciarActionPerformed
        // TODO add your handling code here:
        reniciar();
    }//GEN-LAST:event_jButtonReniciarActionPerformed

    private void jButtonbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonbuscarActionPerformed
        // TODO add your handling code here:
        buscarCiclo();
    }//GEN-LAST:event_jButtonbuscarActionPerformed

    private void jButtonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualizarActionPerformed
        // TODO add your handling code here:
        modifidepartamneto();
    }//GEN-LAST:event_jButtonActualizarActionPerformed

    private void jTextFieldCinturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCinturaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldCadera.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldCinturaKeyPressed

    private void jTextFieldCaderaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCaderaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldEspalda.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldCaderaKeyPressed

    private void jTextFieldEspaldaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEspaldaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldTalle.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldEspaldaKeyPressed

    private void jTextFieldTalleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTalleKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldHombro.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldTalleKeyPressed

    private void jTextFieldHombroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldHombroKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldManga.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldHombroKeyPressed

    private void jTextFieldMangaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMangaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldLargo.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldMangaKeyPressed

    private void jTextFieldLargoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldLargoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldPecho1.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldLargoKeyPressed

    private void jTextFieldPecho1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPecho1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldBusto.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldPecho1KeyPressed

    private void jTextFieldBustoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBustoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldSeparacion.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldBustoKeyPressed

    private void jFormattedCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedCodKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldCintura.requestFocus();
        }
    }//GEN-LAST:event_jFormattedCodKeyPressed

    private void jTextFieldCinturaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCinturaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldCinturaKeyTyped

    private void jTextFieldCaderaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCaderaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldCaderaKeyTyped

    private void jTextFieldEspaldaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEspaldaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldEspaldaKeyTyped

    private void jTextFieldTalleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTalleKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldTalleKeyTyped

    private void jTextFieldHombroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldHombroKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldHombroKeyTyped

    private void jTextFieldMangaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMangaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldMangaKeyTyped

    private void jTextFieldLargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldLargoKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldLargoKeyTyped

    private void jTextFieldPecho1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPecho1KeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldPecho1KeyTyped

    private void jTextFieldBustoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBustoKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldBustoKeyTyped

    private void jTextFieldSeparacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSeparacionKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldSeparacionKeyTyped

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
            java.util.logging.Logger.getLogger(Medida_saco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Medida_saco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Medida_saco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Medida_saco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Medida_saco().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonActualizar;
    private javax.swing.JButton jButtonAgregar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonReniciar;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JButton jButtonbuscar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFormattedTextField jFormattedCod;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldBusto;
    private javax.swing.JTextField jTextFieldCadera;
    private javax.swing.JTextField jTextFieldCintura;
    private javax.swing.JTextField jTextFieldEspalda;
    private javax.swing.JTextField jTextFieldHombro;
    private javax.swing.JTextField jTextFieldLargo;
    private javax.swing.JTextField jTextFieldManga;
    private javax.swing.JTextField jTextFieldPecho1;
    private javax.swing.JTextField jTextFieldSeparacion;
    private javax.swing.JTextField jTextFieldTalle;
    private javax.swing.JTextField jTextFieldbuscar;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JPanel lblcab;
    private javax.swing.JLabel lblcountipRop;
    // End of variables declaration//GEN-END:variables
}
