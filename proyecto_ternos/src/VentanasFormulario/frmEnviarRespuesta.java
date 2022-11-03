/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VentanasFormulario;
import Clases.Conexion;
import Clases2.Controlador;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author PERSONAL
 */
public class frmEnviarRespuesta extends javax.swing.JFrame {

    /**
     * Creates new form frmEnviarRespuesta
     */
    DefaultTableModel tabla = new DefaultTableModel();
    Conexion cn = new Conexion();
    Controlador control=new Controlador();
    String fecha2;
    public frmEnviarRespuesta() {
        initComponents();
        //jLabelIDcon.setVisible(false);
       nombresTitulos();
       cargarVerVenta();
       fechaNac();
       boton();
    }
    public void nombresTitulos() {
        String[] tit = {"CODIGO ", "MARCA","MODELO","DNI","ESTADO"};
        tabla.setColumnIdentifiers(tit);
        jTable1.setModel(tabla);
        
    }
    public void  limpiarTabla(JTable tb, DefaultTableModel md){
        while(tb.getRowCount()>0){
            md.removeRow(0);
        }
    }
    public void cargarVerVenta(){
        
        try {
           
            PreparedStatement ps=null;
            ResultSet rs=null;
            Connection con=cn.conectar();
            
            int num=Integer.parseInt(""+frmPrin.jTextField2.getText());
            
           
            limpiarTabla(jTable1, tabla);
             
            String sql="call p_trabajos (?)";
            ps=con.prepareStatement(sql);
            
           
            ps.setString(1,""+num);
            rs=ps.executeQuery();
            ResultSetMetaData rsMd=(ResultSetMetaData) rs.getMetaData();
            int cantidadcolumnas=rsMd.getColumnCount();
            int [] anchos={100,150,150,130,130};
            for(int i=0;i<jTable1.getColumnCount();i++){
                jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
            while(rs.next()){
                Object[] filas=new  Object[cantidadcolumnas];
                for(int i=0;i<cantidadcolumnas;i++){
                    filas[i]=rs.getObject(i+1);
                }
                tabla.addRow(filas);
            }
            
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
    }
    public void mostrarMedidas(){
        String aux=jTable1.getValueAt(jTable1.getSelectedRow(),3).toString();
        String idesc=jTable1.getValueAt(jTable1.getSelectedRow(),0).toString();
        String aux2=control.DevolverRegistroDato("SELECT c.fk_idMedidasaco from cliente_medidas c WHERE c.idCleinteM='"+aux+"'", 1);
        jTextFieldCinturaS.setText(control.DevolverRegistroDato("SELECT m.cintura from medidasaco m WHERE m.idMedidasaco='"+aux+"'", 1));
        jTextFieldCadera.setText(control.DevolverRegistroDato("SELECT m.cadera from medidasaco m WHERE m.idMedidasaco='"+aux+"'", 1));
        jTextFieldEspalda.setText(control.DevolverRegistroDato("SELECT m.espalda from medidasaco m WHERE m.idMedidasaco='"+aux+"'", 1));
        jTextFieldTalle.setText(control.DevolverRegistroDato("SELECT m.talle from medidasaco m WHERE m.idMedidasaco='"+aux+"'", 1));
        jTextFieldHombro.setText(control.DevolverRegistroDato("SELECT m.hombro from medidasaco m WHERE m.idMedidasaco='"+aux+"'", 1));
        jTextFieldManga.setText(control.DevolverRegistroDato("SELECT m.manga from medidasaco m WHERE m.idMedidasaco='"+aux+"'", 1));
        jTextFieldLargo.setText(control.DevolverRegistroDato("SELECT m.largo from medidasaco m WHERE m.idMedidasaco='"+aux+"'", 1));
        jTextFieldPecho1.setText(control.DevolverRegistroDato("SELECT m.pecho from medidasaco m WHERE m.idMedidasaco='"+aux+"'", 1));
        jTextFieldBusto.setText(control.DevolverRegistroDato("SELECT m.busto from medidasaco m WHERE m.idMedidasaco='"+aux+"'", 1));
        jTextFieldSeparacion.setText(control.DevolverRegistroDato("SELECT m.separacion from medidasaco m WHERE m.idMedidasaco='"+aux+"'", 1));
        
        jTextFieldCintutaP.setText(control.DevolverRegistroDato("select cintura from medidapantalon WHERE idmedidapantalon='"+aux+"'", 1));
        jTextFieldCasdera2.setText(control.DevolverRegistroDato("select cadera from medidapantalon WHERE idmedidapantalon='"+aux+"'", 1));
        jTextFieldmuslo.setText(control.DevolverRegistroDato("select muslo from medidapantalon WHERE idmedidapantalon='"+aux+"'", 1));
        jTextFieldRodilla.setText(control.DevolverRegistroDato("select rodilla from medidapantalon WHERE idmedidapantalon='"+aux+"'", 1));
        jTextFieldBoca.setText(control.DevolverRegistroDato("select boca from medidapantalon WHERE idmedidapantalon='"+aux+"'", 1));
        jTextFieldJareta.setText(control.DevolverRegistroDato("select jareta from medidapantalon WHERE idmedidapantalon='"+aux+"'", 1));
        jTextFieldLsrgo2.setText(control.DevolverRegistroDato("select largo from medidapantalon WHERE idmedidapantalon='"+aux+"'", 1));
        
        jLabel3.setText(control.DevolverRegistroDato("SELECT e.fechpedido from esconfec e WHERE e.idesconfec='"+idesc+"'", 1));
        jLabel2.setText(control.DevolverRegistroDato("SELECT concat_ws(' ',p.nombre,p.apellido_p,p.apellido_m) FROM persona p WHERE p.DNI='"+aux+"'", 1));
        String condi=control.DevolverRegistroDato("SELECT e.estado FROM esconfec_x_confec e WHERE e.fk_idesconfec='"+idesc+"'", WIDTH);
        jLabelEntrega.setText(control.DevolverRegistroDato("SELECT e.fechestimada from esconfec e WHERE e.idesconfec='"+idesc+"'", 1));
        if((""+condi).equals(jRadioButton1.getText())){
            jRadioButton1.setSelected(true);
        }
        if((""+condi).equals(jRadioButton2.getText())){
            jRadioButton2.setSelected(true);
        }
        if((""+condi).equals(jRadioButton3.getText())){
            jRadioButton3.setSelected(true);
        }
    }
    public void fechaNac(){
        Date fecha=new Date();
        SimpleDateFormat fff=new SimpleDateFormat("yyyy-MM-dd");
        fecha2=fff.format(fecha);
        jLabelFecha.setText(fecha2);

    }
    
    public void enviar(){
        int filas;
        int n = jTable1.getSelectedRow();
        String aux=jTable1.getValueAt(jTable1.getSelectedRow(),3).toString();
        String aux2=control.DevolverRegistroDato("SELECT concat_ws(' ',p.nombre,p.apellido_p,p.apellido_m) FROM persona p WHERE p.DNI='"+aux+"'", 1);
        if(jTable1.getSelectedRow()>=0 && (jRadioButton1.isSelected() || jRadioButton2.isSelected() || jRadioButton3.isSelected() )){
            String id=jTable1.getValueAt(n, 0).toString();
            if(JOptionPane.showConfirmDialog(null,"Deseas enviar el trabajo\n "
                    + "del cliente"+aux,"Confirmar",0)==0){
                if(jRadioButton1.isSelected()){
                    int num=Integer.parseInt(""+frmPrin.jTextField2.getText());
                    
                    control.ActualizarRegistro("update esconfec set fechtermino='"+jLabelFecha.getText()+"' where idesconfec='"+id+"'");
                control.ActualizarRegistro("update esconfec_x_confec set fk_idconfeccionador='"+num+"', estado='EN PROCESO' "
                        + "where fk_idesconfec='"+id+"'");
                }
                else if(jRadioButton2.isSelected()){
                    int num=Integer.parseInt(""+frmPrin.jTextField2.getText());
                    control.ActualizarRegistro("update esconfec set fechtermino='"+jLabelFecha.getText()+"' where idesconfec='"+id+"'");
                control.ActualizarRegistro("update esconfec_x_confec set fk_idconfeccionador='"+num+"', estado='TERMINADO' "
                        + "where fk_idesconfec='"+id+"'");
                }
                else if(jRadioButton3.isSelected()){
                    int num=Integer.parseInt(""+frmPrin.jTextField2.getText());
                    control.ActualizarRegistro("update esconfec set fechtermino='"+jLabelFecha.getText()+"' where idesconfec='"+id+"'");
                control.ActualizarRegistro("update esconfec_x_confec set fk_idconfeccionador='"+num+"', estado='ENTREGADO' "
                        + "where fk_idesconfec='"+id+"'");
                }
                
            }
            
        }
        
        else{
            JOptionPane.showMessageDialog(null, "No ha seleccionado nigun trabajo", "AVISO", JOptionPane.INFORMATION_MESSAGE, null);
        }
        cargarVerVenta();
    }
    public void boton(){
        int num=Integer.parseInt(""+frmPrin.jTextField2.getText());
        jButton1.setText(control.DevolverRegistroDato("SELECT c.estado FROM confeccionador c WHERE c.idconfeccionador="+num+"", 1));
        if("LIBRE".equals(jButton1.getText())){
            jButton1.setBackground(Color.GREEN);
        }
        else if("SATURADO".equals(jButton1.getText())){
            jButton1.setBackground(Color.red);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jTextFieldCinturaS = new javax.swing.JTextField();
        jTextFieldCadera = new javax.swing.JTextField();
        jTextFieldEspalda = new javax.swing.JTextField();
        jTextFieldTalle = new javax.swing.JTextField();
        jTextFieldHombro = new javax.swing.JTextField();
        jTextFieldPecho1 = new javax.swing.JTextField();
        jTextFieldBusto = new javax.swing.JTextField();
        jTextFieldSeparacion = new javax.swing.JTextField();
        jTextFieldLargo = new javax.swing.JTextField();
        jTextFieldManga = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jTextFieldCintutaP = new javax.swing.JTextField();
        jTextFieldCasdera2 = new javax.swing.JTextField();
        jTextFieldmuslo = new javax.swing.JTextField();
        jTextFieldRodilla = new javax.swing.JTextField();
        jTextFieldBoca = new javax.swing.JTextField();
        jTextFieldJareta = new javax.swing.JTextField();
        jTextFieldLsrgo2 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jLabel42 = new javax.swing.JLabel();
        jLabelFecha = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        lblcab = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelEntrega = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Enviar Trabajo");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TRABAJOS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 15))); // NOI18N

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 460, 570));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel27.setText("Cintura:");
        jPanel6.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 60, 40));

        jLabel28.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel28.setText("Cadera:");
        jPanel6.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 49, 20));

        jLabel29.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel29.setText("Espalda:");
        jPanel6.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 49, -1));

        jLabel30.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel30.setText("Alto de busto:");
        jPanel6.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, 80, 20));

        jLabel31.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel31.setText("Pecho:");
        jPanel6.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, -1, 30));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Talle:");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 37, 20));

        jLabel32.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel32.setText("Separación:");
        jPanel6.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 180, 84, 30));

        jLabel33.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel33.setText("Hombro:");
        jPanel6.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, 20));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos del proyecto final/sacos-gamarra-02-Mckeover (1).png"))); // NOI18N
        jPanel6.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 40, 140, -1));

        jTextFieldCinturaS.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldCinturaS.setEnabled(false);
        jTextFieldCinturaS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCinturaSKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCinturaSKeyTyped(evt);
            }
        });
        jPanel6.add(jTextFieldCinturaS, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 120, -1));

        jTextFieldCadera.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldCadera.setEnabled(false);
        jTextFieldCadera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCaderaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCaderaKeyTyped(evt);
            }
        });
        jPanel6.add(jTextFieldCadera, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 118, -1));

        jTextFieldEspalda.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldEspalda.setEnabled(false);
        jTextFieldEspalda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldEspaldaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldEspaldaKeyTyped(evt);
            }
        });
        jPanel6.add(jTextFieldEspalda, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 118, -1));

        jTextFieldTalle.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldTalle.setEnabled(false);
        jTextFieldTalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldTalleKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldTalleKeyTyped(evt);
            }
        });
        jPanel6.add(jTextFieldTalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 120, -1));

        jTextFieldHombro.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldHombro.setEnabled(false);
        jTextFieldHombro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldHombroKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldHombroKeyTyped(evt);
            }
        });
        jPanel6.add(jTextFieldHombro, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 120, -1));

        jTextFieldPecho1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldPecho1.setEnabled(false);
        jTextFieldPecho1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldPecho1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPecho1KeyTyped(evt);
            }
        });
        jPanel6.add(jTextFieldPecho1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, 118, -1));

        jTextFieldBusto.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldBusto.setEnabled(false);
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
        jPanel6.add(jTextFieldBusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 118, -1));

        jTextFieldSeparacion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldSeparacion.setEnabled(false);
        jTextFieldSeparacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldSeparacionKeyTyped(evt);
            }
        });
        jPanel6.add(jTextFieldSeparacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, 118, -1));

        jTextFieldLargo.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldLargo.setEnabled(false);
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
        jPanel6.add(jTextFieldLargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, 118, -1));

        jTextFieldManga.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldManga.setEnabled(false);
        jTextFieldManga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldMangaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldMangaKeyTyped(evt);
            }
        });
        jPanel6.add(jTextFieldManga, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 118, -1));

        jLabel35.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel35.setText("Largo:");
        jPanel6.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, -1, -1));

        jLabel36.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel36.setText("Manga: ");
        jPanel6.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 60, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 210, 670, 230));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Cintura:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 59, 30));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Cadera:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 69, 30));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Muslo:");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 37, 30));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Largo:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, 37, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("Boca:");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 37, -1));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("Rodilla:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 65, -1));

        jLabel37.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel37.setText("Jareta:");
        jPanel3.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 110, -1, -1));

        jTextFieldCintutaP.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldCintutaP.setEnabled(false);
        jTextFieldCintutaP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCintutaPKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCintutaPKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldCintutaP, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 118, -1));

        jTextFieldCasdera2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldCasdera2.setEnabled(false);
        jTextFieldCasdera2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCasdera2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCasdera2KeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldCasdera2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 118, -1));

        jTextFieldmuslo.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldmuslo.setEnabled(false);
        jTextFieldmuslo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldmusloKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldmusloKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldmuslo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 118, -1));

        jTextFieldRodilla.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldRodilla.setEnabled(false);
        jTextFieldRodilla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldRodillaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldRodillaKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldRodilla, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 105, -1));

        jTextFieldBoca.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldBoca.setEnabled(false);
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

        jTextFieldJareta.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldJareta.setEnabled(false);
        jTextFieldJareta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldJaretaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldJaretaKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldJareta, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 105, -1));

        jTextFieldLsrgo2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextFieldLsrgo2.setEnabled(false);
        jTextFieldLsrgo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldLsrgo2KeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldLsrgo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 105, -1));

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos del proyecto final/pantalon-satinado-microfibra-para-hombre (1).png"))); // NOI18N
        jPanel3.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 160, 170));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 450, 670, -1));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ENVIO DE TRABAJO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 15))); // NOI18N

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel41.setText("Condición del Trabajo");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("EN PROCESO");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("TERMINADO");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("ENTREGADO");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton2)
                    .addComponent(jLabel41)
                    .addComponent(jRadioButton1))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel41)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton1)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jRadioButton3)
                .addContainerGap())
        );

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel42.setText("FECHA");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosFrm/enviar.png"))); // NOI18N
        jButton2.setText("ENVIAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(36, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(72, 72, 72))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(57, 57, 57))))
        );

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 650, -1, 240));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ESTADO CONFECCIONADOR", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 15))); // NOI18N

        jButton1.setBackground(new java.awt.Color(51, 255, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("LIBRE");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 650, -1, 231));

        lblcab.setBackground(new java.awt.Color(204, 204, 204));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("CLIENTE MEDIDAS");

        javax.swing.GroupLayout lblcabLayout = new javax.swing.GroupLayout(lblcab);
        lblcab.setLayout(lblcabLayout);
        lblcabLayout.setHorizontalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblcabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43)
                .addContainerGap(1033, Short.MAX_VALUE))
        );
        lblcabLayout.setVerticalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(lblcab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 30));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("MEDIDAS CLIENTE");
        jPanel10.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 25));

        jLabel39.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel39.setText("FECHA RECIBIDA:");
        jPanel10.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, 29));

        jLabel40.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel40.setText("FECHA ENTREGA:");
        jPanel10.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel10.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(169, 8, 284, 25));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel10.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(169, 46, 284, 26));

        jLabelEntrega.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel10.add(jLabelEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 284, 30));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginIma/user.png"))); // NOI18N
        jPanel10.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 20, -1, -1));

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 70, 670, 130));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1205, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 907, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldCinturaSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCinturaSKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldCadera.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldCinturaSKeyPressed

    private void jTextFieldCinturaSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCinturaSKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldCinturaSKeyTyped

    private void jTextFieldCaderaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCaderaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldEspalda.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldCaderaKeyPressed

    private void jTextFieldCaderaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCaderaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldCaderaKeyTyped

    private void jTextFieldEspaldaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEspaldaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldTalle.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldEspaldaKeyPressed

    private void jTextFieldEspaldaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEspaldaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldEspaldaKeyTyped

    private void jTextFieldTalleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTalleKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldHombro.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldTalleKeyPressed

    private void jTextFieldTalleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTalleKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldTalleKeyTyped

    private void jTextFieldHombroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldHombroKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldManga.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldHombroKeyPressed

    private void jTextFieldHombroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldHombroKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldHombroKeyTyped

    private void jTextFieldPecho1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPecho1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldBusto.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldPecho1KeyPressed

    private void jTextFieldPecho1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPecho1KeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldPecho1KeyTyped

    private void jTextFieldBustoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBustoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBustoActionPerformed

    private void jTextFieldBustoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBustoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldSeparacion.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldBustoKeyPressed

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

    private void jTextFieldLargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLargoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLargoActionPerformed

    private void jTextFieldLargoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldLargoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldPecho1.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldLargoKeyPressed

    private void jTextFieldLargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldLargoKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldLargoKeyTyped

    private void jTextFieldMangaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMangaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldLargo.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldMangaKeyPressed

    private void jTextFieldMangaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMangaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldMangaKeyTyped

    private void jTextFieldCintutaPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCintutaPKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldCasdera2.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldCintutaPKeyPressed

    private void jTextFieldCintutaPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCintutaPKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldCintutaPKeyTyped

    private void jTextFieldCasdera2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCasdera2KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldmuslo.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldCasdera2KeyPressed

    private void jTextFieldCasdera2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCasdera2KeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldCasdera2KeyTyped

    private void jTextFieldmusloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldmusloKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldRodilla.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldmusloKeyPressed

    private void jTextFieldmusloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldmusloKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldmusloKeyTyped

    private void jTextFieldRodillaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldRodillaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldBoca.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldRodillaKeyPressed

    private void jTextFieldRodillaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldRodillaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldRodillaKeyTyped

    private void jTextFieldBocaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBocaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBocaActionPerformed

    private void jTextFieldBocaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBocaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldJareta.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldBocaKeyPressed

    private void jTextFieldBocaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBocaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldBocaKeyTyped

    private void jTextFieldJaretaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldJaretaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            jTextFieldLsrgo2.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldJaretaKeyPressed

    private void jTextFieldJaretaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldJaretaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldJaretaKeyTyped

    private void jTextFieldLsrgo2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldLsrgo2KeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)&& c !='.'){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldLsrgo2KeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        enviar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        mostrarMedidas();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int num=Integer.parseInt(""+frmPrin.jTextField2.getText());
        jButton1.setText(control.DevolverRegistroDato("SELECT c.estado FROM confeccionador c WHERE c.idconfeccionador="+num+"", 1));
        if("LIBRE".equals(jButton1.getText())){
            jButton1.setBackground(Color.red);
            jButton1.setText("SATURADO");
            control.ActualizarRegistro("update confeccionador set estado='SATURADO' where idconfeccionador="+num);
        }
        else if("SATURADO".equals(jButton1.getText())){
            jButton1.setBackground(Color.GREEN);
            jButton1.setText("LIBRE");
            control.ActualizarRegistro("update confeccionador set estado='LIBRE' where idconfeccionador="+num);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(frmEnviarRespuesta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmEnviarRespuesta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmEnviarRespuesta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmEnviarRespuesta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmEnviarRespuesta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelEntrega;
    private javax.swing.JLabel jLabelFecha;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldBoca;
    private javax.swing.JTextField jTextFieldBusto;
    private javax.swing.JTextField jTextFieldCadera;
    private javax.swing.JTextField jTextFieldCasdera2;
    private javax.swing.JTextField jTextFieldCinturaS;
    private javax.swing.JTextField jTextFieldCintutaP;
    private javax.swing.JTextField jTextFieldEspalda;
    private javax.swing.JTextField jTextFieldHombro;
    private javax.swing.JTextField jTextFieldJareta;
    private javax.swing.JTextField jTextFieldLargo;
    private javax.swing.JTextField jTextFieldLsrgo2;
    private javax.swing.JTextField jTextFieldManga;
    private javax.swing.JTextField jTextFieldPecho1;
    private javax.swing.JTextField jTextFieldRodilla;
    private javax.swing.JTextField jTextFieldSeparacion;
    private javax.swing.JTextField jTextFieldTalle;
    private javax.swing.JTextField jTextFieldmuslo;
    private javax.swing.JPanel lblcab;
    // End of variables declaration//GEN-END:variables
}
