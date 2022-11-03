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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Acer
 */
public class Medida_pantalon extends javax.swing.JFrame {

    DefaultTableModel model=  new DefaultTableModel();
    Conexion cn = new Conexion();
    
    
    /**
     * Creates new form NewJFrame
     */
    public Medida_pantalon() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/pantalon.png")).getImage());
        titulos();
        cargarmalla();
    }

    public void titulos(){
        
     String[] titulo = {"ID PANTALON ","CINTURA","CADERA","MUSLO","RODILLA","BOCA","JARETA","LARGO"};
        model.setColumnIdentifiers(titulo);
      jTableMedida_Pantalon.setModel(model);
    }
    
    public void limpiarTabla (JTable tb, DefaultTableModel md) {
      while(tb.getRowCount()>0){md.removeRow(0);}
   }
    
   public void cargarmalla(){
        try {
           limpiarTabla(jTableMedida_Pantalon ,model);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar() ;
            String sql="select*from medidapantalon;";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadcolumnas=rsMd.getColumnCount();
            int [] anchos={50,50,50,50,50,50,50,50};
            for(int i=0;i<jTableMedida_Pantalon.getColumnCount();i++){
                jTableMedida_Pantalon.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            while(rs.next()){
                Object[] filas=new  Object[cantidadcolumnas];
                for(int i=0;i<cantidadcolumnas;i++){
                    filas[i]=rs.getObject(i+1);
                }
                model.addRow(filas);
            }
            int i=jTableMedida_Pantalon.getRowCount()+1;
            
            
            
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
  }
    public void insertdepartamentos(){
        PreparedStatement ps=null;
        try {
          Connection con=cn.conectar();
          ps=con.prepareStatement("call p_insertMp(?,?,?,?,?,?,?,?)");
          
          ps.setString(1,jFormattedTextField1.getText());
          ps.setString(2,jTextFieldCintuta.getText());
           ps.setString(3,jTextFieldCasdera.getText());
            ps.setString(4,jTextFieldmuslo.getText());
            ps.setString(5,jTextFieldRodilla.getText());
            ps.setString(6,jTextFieldBoca.getText());
            ps.setString(7,jTextFieldJareta.getText());
            ps.setString(8,jTextFieldLsrgo.getText());
                    
          ps.execute();
          JOptionPane.showMessageDialog(null, "Medida Pantalon guaradado!!");
          cargarmalla();
          limpiar();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar registro!!");
            System.out.println(e);
        }
    }
    public void limpiar(){
         jFormattedTextField1.setText("");
         jTextFieldCintuta.setText("");
         jTextFieldCasdera.setText("");
         jTextFieldmuslo.setText("");
         jTextFieldRodilla.setText("");
         jTextFieldBoca.setText("");
         jTextFieldBoca.setText("");
         jTextFieldJareta.setText("");
         jTextFieldLsrgo.setText("");
                 
     jFormattedTextField1.requestFocus();
    }
    public void eliminar(){
        PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
            int fila=jTableMedida_Pantalon.getSelectedRow();
            String codigo=jTableMedida_Pantalon.getValueAt(fila, 0).toString();
            
            ps=con.prepareStatement("delete from medidapantalon where idmedidapantalon=?;");
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
        int fila=jTableMedida_Pantalon.getSelectedRow()+1;
        PreparedStatement ps=null;
        try {
            Connection con=cn.conectar();
           
            
            ps=con.prepareStatement("call p_updateMp(?,?,?,?,?,?,?,?);;");
           
           ps.setString(1,jFormattedTextField1.getText());
           ps.setString(2,jTextFieldCintuta.getText());
           ps.setString(3,jTextFieldCasdera.getText());
            ps.setString(4,jTextFieldmuslo.getText());
            ps.setString(5,jTextFieldRodilla.getText());
            ps.setString(6,jTextFieldBoca.getText());
            ps.setString(7,jTextFieldJareta.getText());
            ps.setString(8,jTextFieldLsrgo.getText());
            
        
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
        String aux=jComboBoxBusqueda.getSelectedItem().toString();
        String campo=jTextFieldBusqueda.getText();
        String where="";
        if(!"".equals(campo)){
            where="WHERE "+aux+"='"+campo+"'";
            
        }
        try {
            jTableMedida_Pantalon.setModel(model);
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            String sql="SELECT idmedidapantalon,cintura,cadera,muslo,rodilla,boca,jareta,largo FROM medidapantalon "+where;
            System.out.println(sql);
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas=rsMd.getColumnCount();
            int [] anchos={50,50,50,50,50,50,50,50};
            for(int i=0;i<jTableMedida_Pantalon.getColumnCount();i++){
                jTableMedida_Pantalon.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            limpiarTabla(jTableMedida_Pantalon, model);
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
    
        limpiarTabla(jTableMedida_Pantalon, model);
        cargarmalla();
        limpiar();
        jFormattedTextField1.requestFocus();
    
    }

     public void cargarDatos(){
        int filas;
        int n=jTableMedida_Pantalon.getSelectedRow();
        jFormattedTextField1.setText(jTableMedida_Pantalon.getValueAt(n, 0).toString());
        jTextFieldCintuta.setText(jTableMedida_Pantalon.getValueAt(n, 1).toString());
        jTextFieldCasdera.setText(jTableMedida_Pantalon.getValueAt(n, 2).toString());
        jTextFieldmuslo.setText(jTableMedida_Pantalon.getValueAt(n, 3).toString());
        jTextFieldRodilla.setText(jTableMedida_Pantalon.getValueAt(n, 4).toString());
        jTextFieldBoca.setText(jTableMedida_Pantalon.getValueAt(n, 5).toString());
        jTextFieldJareta.setText(jTableMedida_Pantalon.getValueAt(n, 6).toString());
        jTextFieldLsrgo.setText(jTableMedida_Pantalon.getValueAt(n, 7).toString());
        filas=n;
    }
    
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jLabel1 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        jToolBar1 = new javax.swing.JToolBar();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jButton7 = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldCintuta = new javax.swing.JTextField();
        jTextFieldCasdera = new javax.swing.JTextField();
        jTextFieldmuslo = new javax.swing.JTextField();
        jTextFieldRodilla = new javax.swing.JTextField();
        jTextFieldBoca = new javax.swing.JTextField();
        jTextFieldJareta = new javax.swing.JTextField();
        jTextFieldLsrgo = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jButtonActualizar = new javax.swing.JButton();
        jButtonAgregar = new javax.swing.JButton();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        jButtonEliminar = new javax.swing.JButton();
        jButtonReniciar = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMedida_Pantalon = new javax.swing.JTable();
        lblcab = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        pnel_inf1 = new javax.swing.JPanel();
        jTextFieldBusqueda = new javax.swing.JTextField();
        jComboBoxBusqueda = new javax.swing.JComboBox<>();
        btnBuscarTodo = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        jToolBar1.setRollover(true);
        jToolBar1.add(jSeparator1);

        jButton2.setText("Actualizar");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton2);
        jToolBar1.add(jSeparator2);

        jButton3.setText("Eliminar");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton3);
        jToolBar1.add(jSeparator3);

        jButton4.setText("Agregar");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton4);
        jToolBar1.add(jSeparator4);

        jButton7.setText("Salir");
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton7);
        jToolBar1.add(jSeparator5);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("REGISTRO DE PANTALON");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Codigo:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 13, 60, 30));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Cintura:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 53, 59, 30));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Cadera:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 94, 69, 30));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Muslo:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 133, 37, 30));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Largo:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, 37, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Boca:");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 37, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Rodilla:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, 65, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("Jareta:");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, -1, -1));

        jTextFieldCintuta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCintutaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCintutaKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldCintuta, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 118, -1));

        jTextFieldCasdera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCasderaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCasderaKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldCasdera, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 118, -1));

        jTextFieldmuslo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldmusloKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldmusloKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldmuslo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 118, -1));

        jTextFieldRodilla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldRodillaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldRodillaKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldRodilla, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 105, -1));

        jTextFieldBoca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBocaActionPerformed(evt);
            }
        });
        jTextFieldBoca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldBocaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldBocaKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldBoca, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 105, -1));

        jTextFieldJareta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldJaretaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldJaretaKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldJareta, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 105, -1));

        jTextFieldLsrgo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldLsrgoKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldLsrgo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 105, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos del proyecto final/pantalon-satinado-microfibra-para-hombre (1).png"))); // NOI18N
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 30, 145, -1));

        jButtonActualizar.setBackground(new java.awt.Color(51, 153, 255));
        jButtonActualizar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos del proyecto final/actualizar (2).png"))); // NOI18N
        jButtonActualizar.setText("Actualizar");
        jButtonActualizar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jButtonActualizar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonActualizar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButtonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualizarActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 180, -1, 30));

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
        jPanel3.add(jButtonAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, -1, 30));

        try {
            jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("MEP###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField1KeyPressed(evt);
            }
        });
        jPanel3.add(jFormattedTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 120, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 610, 237));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 749, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 305, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(711, 405, -1, -1));

        jToolBar3.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar3.setRollover(true);

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

        jButtonReniciar.setBackground(new java.awt.Color(102, 204, 255));
        jButtonReniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos del proyecto final/rehacer.png"))); // NOI18N
        jButtonReniciar.setText("Reniciar");
        jButtonReniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReniciarActionPerformed(evt);
            }
        });
        jToolBar3.add(jButtonReniciar);

        jButtonSalir.setBackground(new java.awt.Color(102, 204, 255));
        jButtonSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos del proyecto final/puerta-de-salida.png"))); // NOI18N
        jButtonSalir.setText("Salir");
        jButtonSalir.setFocusable(false);
        jButtonSalir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });
        jToolBar3.add(jButtonSalir);

        jPanel1.add(jToolBar3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 670, -1));

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("MEDIDA DE PANTALON");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(954, 6, 232, 18));

        jTableMedida_Pantalon.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableMedida_Pantalon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMedida_PantalonMouseClicked(evt);
            }
        });
        jTableMedida_Pantalon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableMedida_PantalonKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTableMedida_Pantalon);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 620, 180));

        lblcab.setBackground(new java.awt.Color(204, 204, 204));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("MEDIDAS PANTALON");

        javax.swing.GroupLayout lblcabLayout = new javax.swing.GroupLayout(lblcab);
        lblcab.setLayout(lblcabLayout);
        lblcabLayout.setHorizontalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblcabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addContainerGap(587, Short.MAX_VALUE))
        );
        lblcabLayout.setVerticalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(lblcab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 30));

        pnel_inf1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204), 2));
        pnel_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextFieldBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldBusquedaKeyReleased(evt);
            }
        });
        pnel_inf1.add(jTextFieldBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 250, -1));

        jComboBoxBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione...", "idmedidapantalon", "cintura", "cadera", "muslo", "rodilla", "boca", "jareta", "largo" }));
        pnel_inf1.add(jComboBoxBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, -1));

        jPanel1.add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 480, 40));

        btnBuscarTodo.setBackground(new java.awt.Color(51, 51, 255));
        btnBuscarTodo.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarTodo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar4.png"))); // NOI18N
        btnBuscarTodo.setText("Buscar");
        btnBuscarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarTodoActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarTodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 330, 110, 40));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Confecciones Y&L");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, 160, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 661, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldBocaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBocaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBocaActionPerformed

    private void jTextFieldBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBusquedaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBusquedaKeyReleased

    private void btnBuscarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarTodoActionPerformed
        // TODO add your handling code here:
        buscarCiclo();
    }//GEN-LAST:event_btnBuscarTodoActionPerformed

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
        // TODO add your handling code here:
        insertdepartamentos();
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jButtonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualizarActionPerformed
        // TODO add your handling code here:
        modifidepartamneto();
    }//GEN-LAST:event_jButtonActualizarActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        // TODO add your handling code here:
        eliminar();
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonReniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReniciarActionPerformed
        // TODO add your handling code here:
        reniciar();
    }//GEN-LAST:event_jButtonReniciarActionPerformed

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButtonSalirActionPerformed

    private void jTableMedida_PantalonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableMedida_PantalonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableMedida_PantalonKeyPressed

    private void jTableMedida_PantalonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMedida_PantalonMouseClicked
        // TODO add your handling code here:
        cargarDatos();
    }//GEN-LAST:event_jTableMedida_PantalonMouseClicked

    private void jTextFieldCintutaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCintutaKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldCasdera.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldCintutaKeyPressed

    private void jTextFieldCasderaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCasderaKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldmuslo.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldCasderaKeyPressed

    private void jTextFieldmusloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldmusloKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldRodilla.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldmusloKeyPressed

    private void jTextFieldRodillaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldRodillaKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldBoca.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldRodillaKeyPressed

    private void jTextFieldBocaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBocaKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldJareta.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldBocaKeyPressed

    private void jTextFieldJaretaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldJaretaKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldLsrgo.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldJaretaKeyPressed

    private void jFormattedTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldCintuta.requestFocus();
        }
    }//GEN-LAST:event_jFormattedTextField1KeyPressed

    private void jTextFieldCintutaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCintutaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldCintutaKeyTyped

    private void jTextFieldCasderaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCasderaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldCasderaKeyTyped

    private void jTextFieldmusloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldmusloKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldmusloKeyTyped

    private void jTextFieldRodillaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldRodillaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldRodillaKeyTyped

    private void jTextFieldBocaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBocaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldBocaKeyTyped

    private void jTextFieldJaretaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldJaretaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldJaretaKeyTyped

    private void jTextFieldLsrgoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldLsrgoKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldLsrgoKeyTyped

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
            java.util.logging.Logger.getLogger(Medida_pantalon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Medida_pantalon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Medida_pantalon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Medida_pantalon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Medida_pantalon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarTodo;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButtonActualizar;
    private javax.swing.JButton jButtonAgregar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonReniciar;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JComboBox<String> jComboBoxBusqueda;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTable jTableMedida_Pantalon;
    private javax.swing.JTextField jTextFieldBoca;
    private javax.swing.JTextField jTextFieldBusqueda;
    private javax.swing.JTextField jTextFieldCasdera;
    private javax.swing.JTextField jTextFieldCintuta;
    private javax.swing.JTextField jTextFieldJareta;
    private javax.swing.JTextField jTextFieldLsrgo;
    private javax.swing.JTextField jTextFieldRodilla;
    private javax.swing.JTextField jTextFieldmuslo;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JPanel lblcab;
    private javax.swing.JPanel pnel_inf1;
    // End of variables declaration//GEN-END:variables
}
