package generate_card.forms;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import generate_card.classes.MConnection;
import generate_card.classes.User;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.sql.SQLException;
import java.sql.*;  
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author Marvin
 */
public class Teacher extends javax.swing.JFrame {
    byte[] person_image = null;
    String path = "";
    public ButtonGroup btg = new ButtonGroup();
            
    int upperTable;
    /**
     * Creates new form Teacher
     */
    public Teacher(int pid) {
        initComponents();
        ImageIcon logo = new javax.swing.ImageIcon(getClass().getResource("/generate_card/images_or_icon/projectIcon.jpg"));
        setIconImage(logo.getImage());
        
        try {
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            String QUERY = "SELECT * FROM teacher WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(QUERY);
            pst.setInt(1, pid);
            rs = pst.executeQuery();
            if(rs.next()){
                if(!rs.getBoolean("isAdviser")){
                    listTitle.setVisible(false);
                    listTitlelbl.setVisible(false);
                }    
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i = 0; i < table1.getColumnCount(); i++)
            table1.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        for(int i = 0; i < table2.getColumnCount(); i++)
            table2.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        
        upperTable = pid;
        
        if(User.userType.equals("principal") || User.userType.equals("staff")){
            menu1.setVisible(false);
            menu2.setText("HOME");
        }
        else if(User.userType.equals("teacher")){
            menu1.setVisible(false);
            menu2.setVisible(false);
            menu3.setText("HOME");
        }
        
        
        updateTable();
        
    }
    
    private void setButtonVisibility(Boolean d1, Boolean d2, Boolean enter){
        deselect1.setVisible(d1);
        deselect2.setVisible(d2);
        esr.setVisible(enter);
        edit.setVisible(enter);
    }

    public void setEditable(Boolean flag){
        sfname.setEditable(flag);
        smname.setEditable(flag);
        slname.setEditable(flag);
        sprefix.setEditable(flag);
        sbdate.setEnabled(flag);
        smnumber.setEditable(flag);
        semail.setEditable(flag);
        scountry.setEditable(flag);
        sregion.setEditable(flag);
        sprovince.setEditable(flag);
        scity.setEditable(flag);
        sbrgy.setEditable(flag);
        sstreet.setEditable(flag);  
        susername.setEditable(flag);
        spassword.setEditable(flag);
        
        
        gfname.setEditable(flag);
        gmname.setEditable(flag);
        glname.setEditable(flag);
        gprefix.setEditable(flag);
        gcountry.setEditable(flag);
        gregion.setEditable(flag);
        gprovince.setEditable(flag);
        gcity.setEditable(flag);
        gbrgy.setEditable(flag);
        gstreet.setEditable(flag);
        gmnumber.setEditable(flag);
        goccupation.setEditable(flag);
        
        
        
        spbcountry.setEditable(flag);
        spbregion.setEditable(flag);
        spbprovince.setEditable(flag);
        spbcity.setEditable(flag);
        
        total.setEditable(flag);
        icc.setEditable(flag);
        average.setEditable(flag);
        slrn.setEditable(flag);
        syear.setEditable(flag);
        Curriculum.setEditable(flag);
        updateProfile.setVisible(false);
        aname.setEditable(false);
    }
    public void updateTable(){
        setEditable(false);
        ec.setVisible(false);
        String fullname = listTitle.getSelectedItem().toString().toLowerCase();
        String split[] = fullname.split(" ");
        String table_name = split[0];
        if(table_name.equals("student")){
            try {
                tablePanel1.setVisible(false);
                tablePanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Student at advisory class", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial Black", 1, 18)));
                MConnection mc = new MConnection();
                Connection conn = mc.getConn();
                ResultSet rs = null;
                String QUERY = "SELECT * FROM student WHERE adviser_id = ?";
                PreparedStatement pst = conn.prepareStatement(QUERY);
                pst.setInt(1, upperTable);
                rs = pst.executeQuery();
                DefaultTableModel model = (DefaultTableModel)table2.getModel();
                int rowCount = model.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    model.removeRow(i);
                }
                /// display all result at the table
                
                while(rs.next()){
                    String lrn = rs.getString("LRN");
                    String name = "";
                    name = rs.getString("first_name") +" "+ rs.getString("middle_name") +" "+ rs.getString("last_name") +" "+ rs.getString("prefix");
                    int teachid = rs.getInt("adviser_id");
                    String section = User.getSection(teachid);
                    int year = rs.getInt("year");
                    model.addRow(new Object[]{lrn, name, section, year});
                }
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }else{ 
            try {
                tablePanel1.setVisible(true);
                tablePanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Student at the subject class", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial Black", 1, 18)));
                MConnection mc = new MConnection();
                Connection conn = mc.getConn();
                ResultSet rs = null;
                String QUERY = QUERY = "SELECT * FROM class WHERE teacher_id = ?";
                PreparedStatement pst = conn.prepareStatement(QUERY);
                pst.setInt(1, upperTable);
                rs = pst.executeQuery();
                DefaultTableModel model = (DefaultTableModel)table1.getModel();
                int rowCount = model.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    model.removeRow(i);
                }
                DefaultTableModel model2 = (DefaultTableModel)table2.getModel();
                int rowCount2 = model2.getRowCount();
                for (int i = rowCount2 - 1; i >= 0; i--) {
                    model2.removeRow(i);
                }
                /// display all result at the table
                while(rs.next()){
                    int id2 = rs.getInt("id");
                    String teach = User.getName1("teacher", rs.getInt("teacher_id"));
                    String subj = User.getName1("subject", rs.getInt("subject_id"));
                    String sd = rs.getString("Schedule_day");
                    String st = rs.getString("start_time") + " to " + rs.getString("end_time");
                    model.addRow(new Object[]{id2, teach, subj, sd, st});
                }
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        setButtonVisibility(false, false, false);
    }

    
    
    public void setColor(int a, int b, int c){
        
        sfname.setForeground(new Color(a,b,c));
        smname.setForeground(new Color(a,b,c));
        slname.setForeground(new Color(a,b,c));
        sprefix.setForeground(new Color(a,b,c));
        sbdate.setForeground(new Color(a,b,c));
        smnumber.setForeground(new Color(a,b,c));
        semail.setForeground(new Color(a,b,c));
        scountry.setForeground(new Color(a,b,c));
        sregion.setForeground(new Color(a,b,c));
        sprovince.setForeground(new Color(a,b,c));
        scity.setForeground(new Color(a,b,c));
        sbrgy.setForeground(new Color(a,b,c));
        sstreet.setForeground(new Color(a,b,c));    
        susername.setForeground(new Color(a,b,c));
        spassword.setForeground(new Color(a,b,c));
        
        
        gfname.setForeground(new Color(a,b,c));
        gmname.setForeground(new Color(a,b,c));
        glname.setForeground(new Color(a,b,c));
        gprefix.setForeground(new Color(a,b,c));
        gcountry.setForeground(new Color(a,b,c));
        gregion.setForeground(new Color(a,b,c));
        gprovince.setForeground(new Color(a,b,c));
        gcity.setForeground(new Color(a,b,c));
        gbrgy.setForeground(new Color(a,b,c));
        gstreet.setForeground(new Color(a,b,c));
        gmnumber.setForeground(new Color(a,b,c));
        goccupation.setForeground(new Color(a,b,c));
        
        
        
        spbcountry.setForeground(new Color(a,b,c));
        spbregion.setForeground(new Color(a,b,c));
        spbprovince.setForeground(new Color(a,b,c));
        spbcity.setForeground(new Color(a,b,c));
    }
    public void clear(){
        // Clear student
        sfname.setText("First name");
        smname.setText("Middle name");
        slname.setText("Last name");
        sprefix.setText("Prefix e.g(Jr.)");
        this.btg.clearSelection();
        sbdate.setText(null);
        sbdate.setCurrent(null);
        smnumber.setText("09123456789");
        semail.setText("example@gmail.com");
        scountry.setText("Country");
        sregion.setText("Region");
        sprovince.setText("Province");
        scity.setText("City");
        sbrgy.setText("Barangay");
        sstreet.setText("Sitio / Blk & lot / Street");
        susername.setText("Username");
        spassword.setText("Password");
        profile.setIcon(null);
        
        spbcountry.setText("Country");
        spbregion.setText("Region");
        spbprovince.setText("Province");
        spbcity.setText("City");
        
        //Clear Guardian
        gfname.setText("First name");
        gmname.setText("Middle name");
        glname.setText("Last name");
        gprefix.setText("Prefix e.g(Jr.)");
        gcountry.setText("Country");
        gregion.setText("Region");
        gprovince.setText("Province");
        gcity.setText("City");
        gbrgy.setText("Barangay");
        gstreet.setText("Sitio / Blk & lot / Street");
        gmnumber.setText("09123456789");
        goccupation.setText("Occupation");
        
        
        
        ///Clear student
        slrn.setText("");
        syear.setSelectedItem(" ");
        aname.setText("");
        ssection.setText("");
        
        setColor(153, 153, 153);
    }

 
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        tablePanel2 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        table2 = new javax.swing.JTable();
        deselect2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        slname = new javax.swing.JTextField();
        sfname = new javax.swing.JTextField();
        smname = new javax.swing.JTextField();
        sprefix = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        bdatelbl = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        sgmale = new javax.swing.JRadioButton();
        sgfemale = new javax.swing.JRadioButton();
        sbdate = new datechooser.beans.DateChooserCombo();
        smnumber = new javax.swing.JFormattedTextField();
        semail = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        sregion = new javax.swing.JTextField();
        scountry = new javax.swing.JTextField();
        sprovince = new javax.swing.JTextField();
        scity = new javax.swing.JTextField();
        sstreet = new javax.swing.JTextField();
        sbrgy = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        glname = new javax.swing.JTextField();
        gfname = new javax.swing.JTextField();
        gmname = new javax.swing.JTextField();
        gprefix = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        gregion = new javax.swing.JTextField();
        gcountry = new javax.swing.JTextField();
        gprovince = new javax.swing.JTextField();
        gcity = new javax.swing.JTextField();
        gstreet = new javax.swing.JTextField();
        gbrgy = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        spassword = new javax.swing.JTextField();
        susername = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        spbcountry = new javax.swing.JTextField();
        spbregion = new javax.swing.JTextField();
        spbprovince = new javax.swing.JTextField();
        spbcity = new javax.swing.JTextField();
        icc = new javax.swing.JTextField();
        average = new javax.swing.JTextField();
        total = new javax.swing.JTextField();
        goccupation = new javax.swing.JTextField();
        gmnumber = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        slrn = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        syear = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ssection = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Curriculum = new javax.swing.JTextField();
        aname = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        profile = new javax.swing.JLabel();
        updateProfile = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tablePanel1 = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        deselect1 = new javax.swing.JButton();
        ec = new javax.swing.JButton();
        listTitle = new javax.swing.JComboBox<>();
        listTitlelbl = new javax.swing.JLabel();
        esr = new javax.swing.JButton();
        edit = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menu1 = new javax.swing.JMenu();
        subteacher = new javax.swing.JMenuItem();
        subsubject = new javax.swing.JMenuItem();
        subclass = new javax.swing.JMenuItem();
        substudent = new javax.swing.JMenuItem();
        menu2 = new javax.swing.JMenu();
        subteacher1 = new javax.swing.JMenuItem();
        subsubject1 = new javax.swing.JMenuItem();
        subclass1 = new javax.swing.JMenuItem();
        substudent1 = new javax.swing.JMenuItem();
        menu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel1.setBackground(new java.awt.Color(0, 255, 128));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        tablePanel2.setBackground(new java.awt.Color(0, 255, 128));
        tablePanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Student at advisory class", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial Black", 1, 18))); // NOI18N
        tablePanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "LRN", "Name", "Section", "Year"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table2MouseClicked(evt);
            }
        });
        jScrollPane18.setViewportView(table2);
        if (table2.getColumnModel().getColumnCount() > 0) {
            table2.getColumnModel().getColumn(0).setResizable(false);
            table2.getColumnModel().getColumn(0).setPreferredWidth(50);
            table2.getColumnModel().getColumn(1).setResizable(false);
            table2.getColumnModel().getColumn(1).setPreferredWidth(200);
            table2.getColumnModel().getColumn(2).setResizable(false);
            table2.getColumnModel().getColumn(3).setResizable(false);
        }

        tablePanel2.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 74, 620, 270));

        deselect2.setText("de-select");
        deselect2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deselect2ActionPerformed(evt);
            }
        });
        tablePanel2.add(deselect2, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 33, 100, -1));

        jPanel2.setBackground(new java.awt.Color(0, 255, 128));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Student Background information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 18))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(0, 255, 128));
        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(0, 255, 128));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Name"));

        slname.setForeground(new java.awt.Color(153, 153, 153));
        slname.setText("Last name");
        slname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                slnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                slnameFocusLost(evt);
            }
        });

        sfname.setForeground(new java.awt.Color(153, 153, 153));
        sfname.setText("First name");
        sfname.setToolTipText("");
        sfname.setName(""); // NOI18N
        sfname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sfnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sfnameFocusLost(evt);
            }
        });

        smname.setForeground(new java.awt.Color(153, 153, 153));
        smname.setText("Middle name");
        smname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                smnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                smnameFocusLost(evt);
            }
        });

        sprefix.setForeground(new java.awt.Color(153, 153, 153));
        sprefix.setText("Prefix e.g(Jr.)");
        sprefix.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sprefixFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sprefixFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sfname)
                    .addComponent(smname)
                    .addComponent(slname)
                    .addComponent(sprefix, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(sfname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(smname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sprefix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 250, -1));

        jPanel12.setBackground(new java.awt.Color(0, 255, 128));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Other information"));

        bdatelbl.setText("Birth date:");

        jLabel2.setText("Gender:");

        sgmale.setText("Male");

        sgfemale.setText("Female");

        try {
            sbdate.setDefaultPeriods(new datechooser.model.multiple.PeriodSet());
        } catch (datechooser.model.exeptions.IncompatibleDataExeption e1) {
            e1.printStackTrace();
        }

        smnumber.setForeground(new java.awt.Color(153, 153, 153));
        smnumber.setText("09123456789");
        smnumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                smnumberFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                smnumberFocusLost(evt);
            }
        });
        smnumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                smnumberKeyTyped(evt);
            }
        });

        semail.setForeground(new java.awt.Color(153, 153, 153));
        semail.setText("example@gmail.com");
        semail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                semailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                semailFocusLost(evt);
            }
        });

        jLabel3.setText("Mobile no:");

        jLabel5.setText("Email:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bdatelbl)
                            .addComponent(jLabel2))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(sgmale)
                                .addGap(11, 11, 11)
                                .addComponent(sgfemale))
                            .addComponent(sbdate, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(smnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(semail, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bdatelbl)
                    .addComponent(sbdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(sgmale)
                    .addComponent(sgfemale))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(smnumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(semail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 250, 150));

        jPanel11.setBackground(new java.awt.Color(0, 255, 128));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Address"));

        sregion.setForeground(new java.awt.Color(153, 153, 153));
        sregion.setText("Region");
        sregion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sregionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sregionFocusLost(evt);
            }
        });

        scountry.setForeground(new java.awt.Color(153, 153, 153));
        scountry.setText("Country");
        scountry.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                scountryFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                scountryFocusLost(evt);
            }
        });

        sprovince.setForeground(new java.awt.Color(153, 153, 153));
        sprovince.setText("Province");
        sprovince.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sprovinceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sprovinceFocusLost(evt);
            }
        });

        scity.setForeground(new java.awt.Color(153, 153, 153));
        scity.setText("City");
        scity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                scityFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                scityFocusLost(evt);
            }
        });

        sstreet.setForeground(new java.awt.Color(153, 153, 153));
        sstreet.setText("Sitio / Blk & lot / Street");
        sstreet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sstreetFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sstreetFocusLost(evt);
            }
        });

        sbrgy.setForeground(new java.awt.Color(153, 153, 153));
        sbrgy.setText("Barangay");
        sbrgy.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sbrgyFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sbrgyFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sstreet, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(sbrgy)
                    .addComponent(scity)
                    .addComponent(sprovince)
                    .addComponent(sregion)
                    .addComponent(scountry))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(scountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sregion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sprovince, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sbrgy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sstreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 200, -1));

        jPanel5.setBackground(new java.awt.Color(0, 255, 128));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Guardian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 14))); // NOI18N

        jPanel13.setBackground(new java.awt.Color(0, 255, 128));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Name"));

        glname.setForeground(new java.awt.Color(153, 153, 153));
        glname.setText("Last name");
        glname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                glnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                glnameFocusLost(evt);
            }
        });

        gfname.setForeground(new java.awt.Color(153, 153, 153));
        gfname.setText("First name");
        gfname.setToolTipText("");
        gfname.setName(""); // NOI18N
        gfname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                gfnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                gfnameFocusLost(evt);
            }
        });

        gmname.setForeground(new java.awt.Color(153, 153, 153));
        gmname.setText("Middle name");
        gmname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                gmnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                gmnameFocusLost(evt);
            }
        });

        gprefix.setForeground(new java.awt.Color(153, 153, 153));
        gprefix.setText("Prefix e.g(Jr.)");
        gprefix.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                gprefixFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                gprefixFocusLost(evt);
            }
        });
        gprefix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gprefixActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(gprefix, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(glname)
                    .addComponent(gmname, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(gfname))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(gfname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gmname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(glname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(gprefix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel14.setBackground(new java.awt.Color(0, 255, 128));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Address"));

        gregion.setForeground(new java.awt.Color(153, 153, 153));
        gregion.setText("Region");
        gregion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                gregionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                gregionFocusLost(evt);
            }
        });

        gcountry.setForeground(new java.awt.Color(153, 153, 153));
        gcountry.setText("Country");
        gcountry.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                gcountryFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                gcountryFocusLost(evt);
            }
        });

        gprovince.setForeground(new java.awt.Color(153, 153, 153));
        gprovince.setText("Province");
        gprovince.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                gprovinceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                gprovinceFocusLost(evt);
            }
        });

        gcity.setForeground(new java.awt.Color(153, 153, 153));
        gcity.setText("City");
        gcity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                gcityFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                gcityFocusLost(evt);
            }
        });

        gstreet.setForeground(new java.awt.Color(153, 153, 153));
        gstreet.setText("Sitio / Blk & lot / Street");
        gstreet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                gstreetFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                gstreetFocusLost(evt);
            }
        });

        gbrgy.setForeground(new java.awt.Color(153, 153, 153));
        gbrgy.setText("Barangay");
        gbrgy.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                gbrgyFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                gbrgyFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(gprovince, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addComponent(gregion, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gcountry, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gstreet, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gcity, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gbrgy, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gcountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gcity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gregion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gbrgy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gprovince, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gstreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(89, 89, 89))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 480, 160));

        jPanel15.setBackground(new java.awt.Color(0, 255, 128));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Account"));

        spassword.setForeground(new java.awt.Color(153, 153, 153));
        spassword.setText("Password");
        spassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pspasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pspasswordFocusLost(evt);
            }
        });

        susername.setForeground(new java.awt.Color(153, 153, 153));
        susername.setText("Username");
        susername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                psusernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                psusernameFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(susername, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(spassword))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(susername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 210, 200, 80));

        jPanel8.setBackground(new java.awt.Color(0, 255, 128));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Place of Birth:"));

        spbcountry.setForeground(new java.awt.Color(153, 153, 153));
        spbcountry.setText("Country");
        spbcountry.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                spbcountryFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                spbcountryFocusLost(evt);
            }
        });

        spbregion.setForeground(new java.awt.Color(153, 153, 153));
        spbregion.setText("Region");
        spbregion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                spbregionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                spbregionFocusLost(evt);
            }
        });

        spbprovince.setForeground(new java.awt.Color(153, 153, 153));
        spbprovince.setText("Province");
        spbprovince.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                spbprovinceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                spbprovinceFocusLost(evt);
            }
        });

        spbcity.setForeground(new java.awt.Color(153, 153, 153));
        spbcity.setText("City");
        spbcity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                spbcityFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                spbcityFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spbcountry)
                    .addComponent(spbregion)
                    .addComponent(spbprovince, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(spbcity))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spbcountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spbregion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spbprovince, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spbcity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, 220, 140));

        icc.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "ICC - Past school"));
        jPanel7.add(icc, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 230, 220, 50));

        average.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Average (Past school)"));
        average.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                averageKeyTyped(evt);
            }
        });
        jPanel7.add(average, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 290, 220, 50));

        total.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total number of years to complete elementary course", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 8))); // NOI18N
        total.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                totalKeyTyped(evt);
            }
        });
        jPanel7.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 180, 220, 40));

        goccupation.setForeground(new java.awt.Color(153, 153, 153));
        goccupation.setText("Occupation");
        goccupation.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                goccupationFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                goccupationFocusLost(evt);
            }
        });
        jPanel7.add(goccupation, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 420, 118, -1));

        gmnumber.setForeground(new java.awt.Color(153, 153, 153));
        gmnumber.setText("09123456789");
        gmnumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                gmnumberFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                gmnumberFocusLost(evt);
            }
        });
        gmnumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                gmnumberKeyTyped(evt);
            }
        });
        jPanel7.add(gmnumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 390, 118, -1));

        jLabel6.setText("Mobile no:");
        jPanel7.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 370, -1, -1));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 28, 770, 450));

        jPanel3.setBackground(new java.awt.Color(0, 255, 128));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Student School information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 18))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(0, 255, 128));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "___________________", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(0, 255, 128));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel7.setText("LRN");

        jLabel8.setText("Year");

        syear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "7", "8", "9", "10" }));

        jLabel9.setText("Adviser name:");

        jLabel10.setText("Section");

        ssection.setEditable(false);

        jLabel4.setText("Curriculum");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(52, 52, 52)
                        .addComponent(ssection))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(37, 37, 37)
                        .addComponent(Curriculum))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(aname))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addGap(65, 65, 65)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(syear, 0, 120, Short.MAX_VALUE)
                            .addComponent(slrn))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(slrn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(syear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(aname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(ssection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Curriculum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 39, 260, -1));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Profile picture", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Times New Roman", 0, 12))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profile, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, 219, -1));

        updateProfile.setText("Upload profile picture");
        updateProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateProfileMouseClicked(evt);
            }
        });
        jPanel3.add(updateProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, 219, 30));

        jLabel1.setText("VISAYAS STATE UNIVERSITY");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 270, -1, -1));

        tablePanel1.setBackground(new java.awt.Color(0, 255, 128));
        tablePanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Class handled", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial Black", 1, 18))); // NOI18N

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Teacher", "Subject", "Schedule days", "Schedule time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        jScrollPane19.setViewportView(table1);
        if (table1.getColumnModel().getColumnCount() > 0) {
            table1.getColumnModel().getColumn(0).setResizable(false);
            table1.getColumnModel().getColumn(0).setPreferredWidth(10);
            table1.getColumnModel().getColumn(1).setResizable(false);
            table1.getColumnModel().getColumn(1).setPreferredWidth(100);
            table1.getColumnModel().getColumn(2).setResizable(false);
            table1.getColumnModel().getColumn(2).setPreferredWidth(100);
            table1.getColumnModel().getColumn(3).setResizable(false);
            table1.getColumnModel().getColumn(3).setPreferredWidth(50);
            table1.getColumnModel().getColumn(4).setResizable(false);
            table1.getColumnModel().getColumn(4).setPreferredWidth(75);
        }

        deselect1.setText("de-select");
        deselect1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deselect1ActionPerformed(evt);
            }
        });

        ec.setText("Enter class");
        ec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ecActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tablePanel1Layout = new javax.swing.GroupLayout(tablePanel1);
        tablePanel1.setLayout(tablePanel1Layout);
        tablePanel1Layout.setHorizontalGroup(
            tablePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(tablePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tablePanel1Layout.createSequentialGroup()
                        .addComponent(deselect1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 433, Short.MAX_VALUE)
                        .addComponent(ec)
                        .addContainerGap())
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        tablePanel1Layout.setVerticalGroup(
            tablePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(tablePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deselect1)
                    .addComponent(ec))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        listTitle.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Class handled", "Student at advisory class" }));
        listTitle.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                listTitleItemStateChanged(evt);
            }
        });

        listTitlelbl.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        listTitlelbl.setText("List of :");

        esr.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        esr.setText("Go to the student record");
        esr.setActionCommand("Enter\nStudent\nRecord");
        esr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                esrMouseClicked(evt);
            }
        });

        edit.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        edit.setText("EDIT");
        edit.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        edit.setInheritsPopupMenu(true);
        edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(360, 360, 360)
                        .addComponent(listTitlelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(listTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tablePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tablePanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(esr, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(edit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(25, 25, 25))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 835, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(listTitlelbl)
                            .addComponent(listTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(tablePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(tablePanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(esr, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(edit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        jMenuBar1.setBackground(new java.awt.Color(0, 255, 128));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        menu1.setBackground(new java.awt.Color(0, 255, 128));
        menu1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        menu1.setText("HOME ");
        menu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu1MouseClicked(evt);
            }
        });

        subteacher.setBackground(new java.awt.Color(0, 255, 128));
        subteacher.setText("TEACHER");
        subteacher.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        subteacher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subteacherActionPerformed(evt);
            }
        });
        menu1.add(subteacher);

        subsubject.setBackground(new java.awt.Color(0, 255, 128));
        subsubject.setText("SUBJECT");
        subsubject.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        subsubject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subsubjectActionPerformed(evt);
            }
        });
        menu1.add(subsubject);

        subclass.setBackground(new java.awt.Color(0, 255, 128));
        subclass.setText("CLASS");
        subclass.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        subclass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subclassActionPerformed(evt);
            }
        });
        menu1.add(subclass);

        substudent.setBackground(new java.awt.Color(0, 255, 128));
        substudent.setText("STUDENT");
        substudent.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        substudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                substudentActionPerformed(evt);
            }
        });
        menu1.add(substudent);

        jMenuBar1.add(menu1);

        menu2.setBackground(new java.awt.Color(0, 255, 128));
        menu2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        menu2.setText("SCHOOL");

        subteacher1.setBackground(new java.awt.Color(0, 255, 128));
        subteacher1.setText("TEACHER");
        subteacher1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        subteacher1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subteacher1ActionPerformed(evt);
            }
        });
        menu2.add(subteacher1);

        subsubject1.setBackground(new java.awt.Color(0, 255, 128));
        subsubject1.setText("SUBJECT");
        subsubject1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        subsubject1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subsubject1ActionPerformed(evt);
            }
        });
        menu2.add(subsubject1);

        subclass1.setBackground(new java.awt.Color(0, 255, 128));
        subclass1.setText("CLASS");
        subclass1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        subclass1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subclass1ActionPerformed(evt);
            }
        });
        menu2.add(subclass1);

        substudent1.setBackground(new java.awt.Color(0, 255, 128));
        substudent1.setText("STUDENT");
        substudent1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        substudent1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                substudent1ActionPerformed(evt);
            }
        });
        menu2.add(substudent1);

        jMenuBar1.add(menu2);

        menu3.setBackground(new java.awt.Color(0, 255, 128));
        menu3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        menu3.setText("TEACHER");
        jMenuBar1.add(menu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 806, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void table2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table2MouseClicked
        int  row;
        String lrn = "";
        row = table2.getSelectedRow();
        lrn =  (String) table2.getModel().getValueAt(row, 0);
        try {
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            String QUERY = "SELECT * FROM student WHERE lrn = ?";
            PreparedStatement pst = conn.prepareStatement(QUERY);
            pst.setString(1, lrn);
            rs = pst.executeQuery();
            while(rs.next()){
                deselect2.setVisible(false);
                if(listTitle.getSelectedItem().toString().equals("Student at advisory class")){
                    setButtonVisibility(true, true, true);
                    setEditable(true);
                }
                else    {
                    setButtonVisibility(true, true, false);
                    setEditable(false);
                }
                setColor(0, 0, 0);
                slrn.setText(lrn);
                sfname.setText(rs.getString("first_name"));
                smname.setText(rs.getString("middle_name"));
                slname.setText(rs.getString("last_name"));
                sprefix.setText(rs.getString("prefix"));
                String d = rs.getString("Date_of_Birth");
                try {
                    Date date = new SimpleDateFormat("yyyy-mm-dd").parse(d);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    sbdate.setSelectedDate(c);
                } catch (ParseException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(rs.getString("gender").equals("Male")){
                    sgmale.setSelected(true);
                    sgfemale.setSelected(false);
                }
                else{
                    sgmale.setSelected(false);
                    sgfemale.setSelected(true);
                }
                smnumber.setText(rs.getString("mobile_number"));
                semail.setText(rs.getString("email"));
                String add = rs.getString("address");
                String split[] = add.split(",");
                int ctr = 0;
                for(String s: split){
                    switch(ctr){
                        case 0:
                        sstreet.setText(s);
                        case 1:
                        sbrgy.setText(s);
                        case 2:
                        scity.setText(s);
                        case 3:
                        sprovince.setText(s);
                        case 4:
                        sregion.setText(s);
                        case 5:
                        scountry.setText(s);
                        default:;
                    }
                    ctr++;
                }
                String add2 = rs.getString("Place_of_Birth");
                String split2[] = add2.split(",");
                int ctr2 = 0;
                for(String s: split2){
                    switch(ctr2){
                        case 0:
                        spbcity.setText(s);
                        case 1:
                        spbprovince.setText(s);
                        case 2:
                        spbregion.setText(s);
                        case 3:
                        spbcountry.setText(s);
                        default:;
                    }
                    ctr2++;
                }
                String add3 = rs.getString("Guardian_Address");
                String split3[] = add3.split(",");
                int ctr3 = 0;
                for(String s: split3){
                    switch(ctr3){
                        case 0:
                        gstreet.setText(s);
                        case 1:
                        gbrgy.setText(s);
                        case 2:
                        gcity.setText(s);
                        case 3:
                        gprovince.setText(s);
                        case 4:
                        gregion.setText(s);
                        case 5:
                        gcountry.setText(s);
                        default:;
                    }
                    ctr3++;
                }
                icc.setText(rs.getString("icc"));
                average.setText(rs.getFloat("General_Average") + "");
                total.setText(rs.getInt("Total_num") + "");
                gmnumber.setText(rs.getString("Guardian_mobile_number"));
                goccupation.setText(rs.getString("Guardian_occupation"));
                susername.setText(rs.getString("username"));
                spassword.setText(rs.getString("password"));

                person_image = rs.getBytes("picture");

                ImageIcon icon = new ImageIcon(person_image);
                Image image = icon.getImage().getScaledInstance(profile.getWidth(), profile.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon i = new ImageIcon(image);
                profile.setIcon(i);

                gfname.setText(rs.getString("first_name"));
                gmname.setText(rs.getString("middle_name"));
                glname.setText(rs.getString("last_name"));
                gprefix.setText(rs.getString("prefix"));
                Curriculum.setText(rs.getString("Curriculum"));
                int y = rs.getInt("year");
                syear.setSelectedItem(y+"");
                aname.setText(User.getName("teacher", rs.getInt("adviser_id")));
                ssection.setText(User.getSection(rs.getInt("adviser_id")));
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_table2MouseClicked

    private void deselect2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deselect2ActionPerformed
        clear();
        setButtonVisibility(true, false, false);
    }//GEN-LAST:event_deselect2ActionPerformed

    private void updateProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateProfileMouseClicked
        String path = "";
        Image image = null;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();
            ImageIcon icon = new ImageIcon(path);
            image = icon.getImage().getScaledInstance(profile.getWidth(), profile.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon i = new ImageIcon(image);
            profile.setIcon(i);
            this.path = path;

        }

    }//GEN-LAST:event_updateProfileMouseClicked

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        ec.setVisible(true);
        int id, row;
        
        row = table1.getSelectedRow();
        id = (int) table1.getModel().getValueAt(row, 0);

        try{
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            String QUERY = "";
            QUERY = "SELECT * FROM student INNER JOIN grades on student.lrn = grades.lrn WHERE grades.CLASS_ID = ?";
            PreparedStatement pst = conn.prepareStatement(QUERY);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel)table2.getModel();
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
              model.removeRow(i);
            }
            /// display all result at the table
            while(rs.next()){
                setButtonVisibility(true, false, false);
                String lrn = rs.getString("LRN");
                String name = name = rs.getString("first_name")+" "+rs.getString("middle_name")+" "+rs.getString("Last_name")+" "+rs.getString("prefix");               
                int teachid = rs.getInt("adviser_id");
                String section = User.getSection(teachid);
                int year = rs.getInt("year");
                model.addRow(new Object[]{lrn, name, section, year});
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_table1MouseClicked

    private void listTitleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_listTitleItemStateChanged
        if(listTitle.getSelectedItem().toString().equals("Class handled"))
            esr.setVisible(false);
        else
            esr.setVisible(true);
        deselect1.doClick();
        updateTable();
    }//GEN-LAST:event_listTitleItemStateChanged

    private void deselect1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deselect1ActionPerformed
        clear();
        setButtonVisibility(false, false, false);

        DefaultTableModel model = (DefaultTableModel)table2.getModel();
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
          model.removeRow(i);
        }
    }//GEN-LAST:event_deselect1ActionPerformed

    private void esrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_esrMouseClicked
                                         
        int  row;
        String lrn = "";
        row = table2.getSelectedRow();
        lrn =  (String) table2.getModel().getValueAt(row, 0);
        
        this.setVisible(false);
        new Student_grades(lrn).setVisible(true);
        
    }//GEN-LAST:event_esrMouseClicked

    private void subsubject1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subsubject1ActionPerformed
        this.setVisible(false);
        new School_subject().setVisible(true);
    }//GEN-LAST:event_subsubject1ActionPerformed

    private void subclass1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subclass1ActionPerformed
        this.setVisible(false);
        new School_class().setVisible(true);
    }//GEN-LAST:event_subclass1ActionPerformed

    private void substudent1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_substudent1ActionPerformed
        this.setVisible(false);
        new School_student().setVisible(true);
    }//GEN-LAST:event_substudent1ActionPerformed

    private void subteacher1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subteacher1ActionPerformed
        this.setVisible(false);
        new School_teacher(User.principal_id).setVisible(true);
    }//GEN-LAST:event_subteacher1ActionPerformed

    private void subsubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subsubjectActionPerformed
        this.setVisible(false);
        new School_subject().setVisible(true);
    }//GEN-LAST:event_subsubjectActionPerformed

    private void subclassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subclassActionPerformed
        this.setVisible(false);
        new School_class().setVisible(true);
    }//GEN-LAST:event_subclassActionPerformed

    private void substudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_substudentActionPerformed
        this.setVisible(false);
        new School_student().setVisible(true);
    }//GEN-LAST:event_substudentActionPerformed

    private void subteacherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subteacherActionPerformed
        this.setVisible(false);
        new School_teacher(User.principal_id).setVisible(true);
    }//GEN-LAST:event_subteacherActionPerformed

    private void menu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu1MouseClicked
        if(User.userType.equals("administrator")){
            this.setVisible(false);
            new Admin().setVisible(true);
        }
    }//GEN-LAST:event_menu1MouseClicked

    private void ecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ecActionPerformed
        int id, row;
        
        row = table1.getSelectedRow();
        id = (int) table1.getModel().getValueAt(row, 0);
        this.setVisible(false);
        new Class_record(id).setVisible(true);
    }//GEN-LAST:event_ecActionPerformed

    private void slnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_slnameFocusGained
        if(slname.getText().trim().equals("Last name"))
        slname.setText("");
        slname.setForeground(Color.BLACK);
    }//GEN-LAST:event_slnameFocusGained

    private void slnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_slnameFocusLost
        if(slname.getText().trim().equals("")){
            slname.setText("Last name");
            slname.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_slnameFocusLost

    private void sfnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sfnameFocusGained
        if(sfname.getText().trim().equals("First name"))
        sfname.setText("");
        sfname.setForeground(Color.BLACK);
    }//GEN-LAST:event_sfnameFocusGained

    private void sfnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sfnameFocusLost
        if(sfname.getText().trim().equals("")){
            sfname.setText("First name");
            sfname.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_sfnameFocusLost

    private void smnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_smnameFocusGained
        if(smname.getText().trim().equals("Middle name"))
        smname.setText("");
        smname.setForeground(Color.BLACK);
    }//GEN-LAST:event_smnameFocusGained

    private void smnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_smnameFocusLost
        if(smname.getText().trim().equals("")){
            smname.setText("Middle name");
            smname.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_smnameFocusLost

    private void sprefixFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sprefixFocusGained
        if(sprefix.getText().trim().equals("Prefix e.g(Jr.)"))
        sprefix.setText("");
        sprefix.setForeground(Color.BLACK);
    }//GEN-LAST:event_sprefixFocusGained

    private void sprefixFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sprefixFocusLost
        if(sprefix.getText().trim().equals("")){
            sprefix.setText("Prefix e.g(Jr.)");
            sprefix.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_sprefixFocusLost

    private void smnumberFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_smnumberFocusGained
        if(smnumber.getText().trim().equals("09123456789"))
        smnumber.setText("");
        smnumber.setForeground(Color.BLACK);
    }//GEN-LAST:event_smnumberFocusGained

    private void smnumberFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_smnumberFocusLost
        if(smnumber.getText().trim().equals("")){
            smnumber.setText("09123456789");
            smnumber.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_smnumberFocusLost

    private void smnumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_smnumberKeyTyped
        char c = evt.getKeyChar();
        String text = smnumber.getText();
        if ((!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) ||  text.length() >= 11) {
            evt.consume();
        }
        else if(Character.isDigit(c)){
            if(Integer.valueOf(c+"") != 0 && text.isEmpty())
            evt.consume();
            else if(Integer.valueOf(c+"") != 9 && text.length() == 1)
            evt.consume();
        }
    }//GEN-LAST:event_smnumberKeyTyped

    private void semailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_semailFocusGained
        if(semail.getText().trim().equals("example@gmail.com"))
        semail.setText("");
        semail.setForeground(Color.BLACK);
    }//GEN-LAST:event_semailFocusGained

    private void semailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_semailFocusLost
        if(semail.getText().trim().equals("")){
            semail.setText("example@gmail.com");
            semail.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_semailFocusLost

    private void sregionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sregionFocusGained
        if(sregion.getText().trim().equals("Region"))
        sregion.setText("");
        sregion.setForeground(Color.BLACK);
    }//GEN-LAST:event_sregionFocusGained

    private void sregionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sregionFocusLost
        if(sregion.getText().trim().equals("")){
            sregion.setText("Region");
            sregion.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_sregionFocusLost

    private void scountryFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_scountryFocusGained
        if(scountry.getText().trim().equals("Country"))
        scountry.setText("");
        scountry.setForeground(Color.BLACK);
    }//GEN-LAST:event_scountryFocusGained

    private void scountryFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_scountryFocusLost
        if(scountry.getText().trim().equals("")){
            scountry.setText("Country");
            scountry.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_scountryFocusLost

    private void sprovinceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sprovinceFocusGained
        if(sprovince.getText().trim().equals("Province"))
        sprovince.setText("");
        sprovince.setForeground(Color.BLACK);
    }//GEN-LAST:event_sprovinceFocusGained

    private void sprovinceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sprovinceFocusLost
        if(sprovince.getText().trim().equals("")){
            sprovince.setText("Province");
            sprovince.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_sprovinceFocusLost

    private void scityFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_scityFocusGained
        if(scity.getText().trim().equals("City"))
        scity.setText("");
        scity.setForeground(Color.BLACK);
    }//GEN-LAST:event_scityFocusGained

    private void scityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_scityFocusLost
        if(scity.getText().trim().equals("")){
            scity.setText("City");
            scity.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_scityFocusLost

    private void sstreetFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sstreetFocusGained
        if(sstreet.getText().trim().equals("Sitio / Blk & lot / Street"))
        sstreet.setText("");
        sstreet.setForeground(Color.BLACK);
    }//GEN-LAST:event_sstreetFocusGained

    private void sstreetFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sstreetFocusLost
        if(sstreet.getText().trim().equals("")){
            sstreet.setText("Sitio / Blk & lot / Street");
            sstreet.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_sstreetFocusLost

    private void sbrgyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sbrgyFocusGained
        if(sbrgy.getText().trim().equals("Barangay"))
        sbrgy.setText("");
        sbrgy.setForeground(Color.BLACK);
    }//GEN-LAST:event_sbrgyFocusGained

    private void sbrgyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sbrgyFocusLost
        if(sbrgy.getText().trim().equals("")){
            sbrgy.setText("Barangay");
            sbrgy.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_sbrgyFocusLost

    private void glnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_glnameFocusGained
        if(glname.getText().trim().equals("Last name"))
        glname.setText("");
        glname.setForeground(Color.BLACK);
    }//GEN-LAST:event_glnameFocusGained

    private void glnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_glnameFocusLost
        if(glname.getText().trim().equals("")){
            glname.setText("Last name");
            glname.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_glnameFocusLost

    private void gfnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gfnameFocusGained
        if(gfname.getText().trim().equals("First name"))
        gfname.setText("");
        gfname.setForeground(Color.BLACK);
    }//GEN-LAST:event_gfnameFocusGained

    private void gfnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gfnameFocusLost
        if(gfname.getText().trim().equals("")){
            gfname.setText("First name");
            gfname.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_gfnameFocusLost

    private void gmnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gmnameFocusGained
        if(gmname.getText().trim().equals("Middle name"))
        gmname.setText("");
        gmname.setForeground(Color.BLACK);
    }//GEN-LAST:event_gmnameFocusGained

    private void gmnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gmnameFocusLost
        if(gmname.getText().trim().equals("")){
            gmname.setText("Middle name");
            gmname.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_gmnameFocusLost

    private void gprefixFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gprefixFocusGained
        if(gprefix.getText().trim().equals("Prefix e.g(Jr.)"))
        gprefix.setText("");
        gprefix.setForeground(Color.BLACK);
    }//GEN-LAST:event_gprefixFocusGained

    private void gprefixFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gprefixFocusLost
        if(gprefix.getText().trim().equals("")){
            gprefix.setText("Prefix e.g(Jr.)");
            gprefix.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_gprefixFocusLost

    private void gprefixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gprefixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gprefixActionPerformed

    private void gregionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gregionFocusGained
        if(gregion.getText().trim().equals("Region"))
        gregion.setText("");
        gregion.setForeground(Color.BLACK);
    }//GEN-LAST:event_gregionFocusGained

    private void gregionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gregionFocusLost
        if(gregion.getText().trim().equals("")){
            gregion.setText("Region");
            gregion.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_gregionFocusLost

    private void gcountryFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gcountryFocusGained
        if(gcountry.getText().trim().equals("Country"))
        gcountry.setText("");
        gcountry.setForeground(Color.BLACK);
    }//GEN-LAST:event_gcountryFocusGained

    private void gcountryFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gcountryFocusLost
        if(gcountry.getText().trim().equals("")){
            gcountry.setText("Country");
            gcountry.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_gcountryFocusLost

    private void gprovinceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gprovinceFocusGained
        if(gprovince.getText().trim().equals("Province"))
        gprovince.setText("");
        gprovince.setForeground(Color.BLACK);
    }//GEN-LAST:event_gprovinceFocusGained

    private void gprovinceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gprovinceFocusLost
        if(gprovince.getText().trim().equals("")){
            gprovince.setText("Province");
            gprovince.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_gprovinceFocusLost

    private void gcityFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gcityFocusGained
        if(gcity.getText().trim().equals("City"))
        gcity.setText("");
        gcity.setForeground(Color.BLACK);
    }//GEN-LAST:event_gcityFocusGained

    private void gcityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gcityFocusLost
        if(gcity.getText().trim().equals("")){
            gcity.setText("City");
            gcity.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_gcityFocusLost

    private void gstreetFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gstreetFocusGained
        if(gstreet.getText().trim().equals("Sitio / Blk & lot / Street"))
        gstreet.setText("");
        gstreet.setForeground(Color.BLACK);
    }//GEN-LAST:event_gstreetFocusGained

    private void gstreetFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gstreetFocusLost
        if(gstreet.getText().trim().equals("")){
            gstreet.setText("Sitio / Blk & lot / Street");
            gstreet.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_gstreetFocusLost

    private void gbrgyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gbrgyFocusGained
        if(gbrgy.getText().trim().equals("Barangay"))
        gbrgy.setText("");
        gbrgy.setForeground(Color.BLACK);
    }//GEN-LAST:event_gbrgyFocusGained

    private void gbrgyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gbrgyFocusLost
        if(gbrgy.getText().trim().equals("")){
            gbrgy.setText("Barangay");
            gbrgy.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_gbrgyFocusLost

    private void pspasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pspasswordFocusGained
        if(spassword.getText().trim().equals("Password"))
        spassword.setText("");
        spassword.setForeground(Color.BLACK);
    }//GEN-LAST:event_pspasswordFocusGained

    private void pspasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pspasswordFocusLost
        if(spassword.getText().trim().equals("")){
            spassword.setText("Password");
            spassword.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_pspasswordFocusLost

    private void psusernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_psusernameFocusGained
        if(susername.getText().trim().equals("Username"))
        susername.setText("");
        susername.setForeground(Color.BLACK);
    }//GEN-LAST:event_psusernameFocusGained

    private void psusernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_psusernameFocusLost
        if(susername.getText().trim().equals("")){
            susername.setText("Username");
            susername.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_psusernameFocusLost

    private void spbcountryFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spbcountryFocusGained
        if(spbcountry.getText().trim().equals("Country"))
        spbcountry.setText("");
        spbcountry.setForeground(Color.BLACK);
    }//GEN-LAST:event_spbcountryFocusGained

    private void spbcountryFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spbcountryFocusLost
        if(spbcountry.getText().trim().equals("")){
            spbcountry.setText("Country");
            spbcountry.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_spbcountryFocusLost

    private void spbregionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spbregionFocusGained
        if(spbregion.getText().trim().equals("Region"))
        spbregion.setText("");
        spbregion.setForeground(Color.BLACK);
    }//GEN-LAST:event_spbregionFocusGained

    private void spbregionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spbregionFocusLost
        if(spbregion.getText().trim().equals("")){
            spbregion.setText("Region");
            spbregion.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_spbregionFocusLost

    private void spbprovinceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spbprovinceFocusGained
        if(spbprovince.getText().trim().equals("Province"))
        spbprovince.setText("");
        spbprovince.setForeground(Color.BLACK);
    }//GEN-LAST:event_spbprovinceFocusGained

    private void spbprovinceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spbprovinceFocusLost
        if(spbprovince.getText().trim().equals("")){
            spbprovince.setText("Province");
            spbprovince.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_spbprovinceFocusLost

    private void spbcityFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spbcityFocusGained
        if(spbcity.getText().trim().equals("City"))
        spbcity.setText("");
        spbcity.setForeground(Color.BLACK);
    }//GEN-LAST:event_spbcityFocusGained

    private void spbcityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spbcityFocusLost
        if(spbcity.getText().trim().equals("")){
            spbcity.setText("City");
            spbcity.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_spbcityFocusLost

    private void averageKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_averageKeyTyped
        char c = evt.getKeyChar();
        String text = average.getText();
        if ((!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) ||  ((c == '.') && text.indexOf('.') < 0))) ||  text.length() >= 6) {
            evt.consume();
        }
        else if(Character.isDigit(c)){
            if(Float.valueOf(text + c) > 100)
            evt.consume();
        }
    }//GEN-LAST:event_averageKeyTyped

    private void totalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_totalKeyTyped
        char c = evt.getKeyChar();
        String text = total.getText();
        if ((!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) ||  text.length() >= 3) {
            evt.consume();
        }
    }//GEN-LAST:event_totalKeyTyped

    private void goccupationFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_goccupationFocusGained
        if(goccupation.getText().trim().equals("Occupation"))
        goccupation.setText("");
        goccupation.setForeground(Color.BLACK);
    }//GEN-LAST:event_goccupationFocusGained

    private void goccupationFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_goccupationFocusLost
        if(goccupation.getText().trim().equals("")){
            goccupation.setText("Occupation");
            goccupation.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_goccupationFocusLost

    private void gmnumberFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gmnumberFocusGained
        if(gmnumber.getText().trim().equals("09123456789"))
        gmnumber.setText("");
        gmnumber.setForeground(Color.BLACK);
    }//GEN-LAST:event_gmnumberFocusGained

    private void gmnumberFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gmnumberFocusLost
        if(gmnumber.getText().trim().equals("")){
            gmnumber.setText("09123456789");
            gmnumber.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_gmnumberFocusLost

    private void gmnumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gmnumberKeyTyped
        char c = evt.getKeyChar();
        String text = gmnumber.getText();
        if ((!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) ||  text.length() >= 11) {
            evt.consume();
        }
        else if(Character.isDigit(c)){
            if(Integer.valueOf(c+"") != 0 && text.isEmpty())
            evt.consume();
            else if(Integer.valueOf(c+"") != 9 && text.length() == 1)
            evt.consume();
        }
    }//GEN-LAST:event_gmnumberKeyTyped

    private void editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMouseClicked
        String action = "edit";
        String[] options = new String[] {"Yes", "No"};
        int response = JOptionPane.showOptionDialog(null, "Are you sure?", "CERTAINTY",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        if(response == 0){
            int row = -1;
            String lrn = "";
            if(action.equals("edit")){
                row = table2.getSelectedRow();
                lrn = (String) table2.getModel().getValueAt(row, 0);         //////////
            }
            MConnection mc = new MConnection();
            String lrn2 = slrn.getText();           //////////////////
            String first_name = sfname.getText();           //////////
            String middle_name = smname.getText();      ///////////
            String last_name = slname.getText();        /////////////////
            String prefix = sprefix.getText();
            if(prefix.equals("Prefix e.g(Jr.)"))    prefix = "";
            String mobile_no = smnumber.getText();      //////////////
            String email = semail.getText();        /////
            String gender = "";             ///////
            if(sgfemale.isSelected())
                gender = "Female";//
            else if(sgmale.isSelected())
                gender = "Male";//
            String address = sstreet.getText() +","+ sbrgy.getText() +","+ scity.getText() +","+ sprovince.getText() +","+ sregion.getText() +","+ scountry.getText();    //////
            String bdate = sbdate.getText(); ///
            String pob = spbcity.getText() + "," + spbprovince.getText() + "," + spbregion.getText() + "," + spbcountry.getText();          /////
            String gfname = this.gfname.getText();
            String gmname = this.gmname.getText();
            String glname = this.glname.getText();
            String gprefix = this.gprefix.getText();
            if(gprefix.equals("Prefix e.g(Jr.)"))    gprefix = "";
            String go = goccupation.getText();
            String ga = gstreet.getText() +","+ gbrgy.getText() +","+ gcity.getText() +","+ gprovince.getText() +","+ gregion.getText() +","+ gcountry.getText();
            String gmn = gmnumber.getText();
            String username = susername.getText();
            String password = spassword.getText();
            String curr = Curriculum.getText();
            Boolean userFlag = false;
            if(action.equals("insert"))    userFlag = mc.usernameIsValid(username);
            else    userFlag = mc.usernameIsValid(username, lrn2);
            String pastSchool = icc.getText();
            String gen_ave = average.getText();
            String total_number = total.getText();
            if(!first_name.equals("") && !first_name.equals("First name") && 
                    !middle_name.equals("") && !middle_name.equals("Middle name") && 
                    !last_name.equals("") && !last_name.equals("Last name") && 
                    !username.equals("") && userFlag  && !username.equals("Username") && 
                    !password.equals("") && !password.equals("Password") && 
                    !gender.equals("") && 
                    !bdate.equals("") && 
                    !pastSchool.equals("") && 
                    !gen_ave.equals("") && 
                    !total_number.equals("") && 
                    User.isValidmobile(mobile_no) &&
                    User.isValidEmail(email) && 
                    !scountry.equals("") && !scountry.equals("Country") &&  
                    !sregion.equals("") && !sregion.equals("Region") && 
                    !sprovince.equals("") && !sprovince.equals("Province") && 
                    !scity.equals("") && !scity.equals("City") && 
                    !sbrgy.equals("") && !sbrgy.equals("Barangay") && 
                    !sstreet.equals("") && !sstreet.equals("Sitio / Blk & lot / Street") && 
                    
                    !gfname.equals("") && !gfname.equals("First name") && 
                    !gmname.equals("") && !gmname.equals("Middle name") && 
                    !glname.equals("") && !glname.equals("Last name") && 
                    User.isValidmobile(gmn) &&
                    !goccupation.equals("") && !goccupation.equals("Occupation") &&  
                    !gcountry.equals("") && !gcountry.equals("Country") &&  
                    !gregion.equals("") && !gregion.equals("Region") && 
                    !gprovince.equals("") && !gprovince.equals("Province") && 
                    !gcity.equals("") && !gcity.equals("City") && 
                    !gbrgy.equals("") && !gbrgy.equals("Barangay") && 
                    !gstreet.equals("") && !gstreet.equals("Sitio / Blk & lot / Street") && 
                    (!path.equals("") || !action.equals("hire")) && 
                    !lrn2.equals("") && 
                    !(syear.getSelectedItem()+"").equals("") &&
                    !(aname.getText()+"").equals("") &&
                    !curr.equals("")){
                           
                LocalDate today = LocalDate.now();
                Date bdate2 = null;
                try {
                    bdate2 = new SimpleDateFormat("M/D/YY").parse(bdate);
                } catch (ParseException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
                Calendar c = Calendar.getInstance();
                c.setTime(bdate2);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int date = c.get(Calendar.DATE);
                LocalDate birth_date = LocalDate.of(year, month, date);
                Period agey = Period.between(birth_date, today);    
                int age = agey.getYears();      
                 
                Float general_average = Float.valueOf(gen_ave);
                int total_numb = Integer.valueOf(total_number);
                
                int y = Integer.valueOf(syear.getSelectedItem()+"");
                //// general average is not included since student can exist without average
                //// total number of subject is not create as well during creation of student
                String[] id_and_adviser = (aname.getText()+"").toString().split(",");
                int ai = Integer.valueOf(id_and_adviser[0]);
                
                if(prefix.equals("Prefix e.g(Jr.)"))
                    prefix = "";
                if(gprefix.equals("Prefix e.g(Jr.)"))
                    gprefix = "";
                
                
                InputStream picture = null;
                if(!path.equals("")){
                    try{
                        picture = new FileInputStream(path);
                        ByteArrayOutputStream baos=new ByteArrayOutputStream();
                        byte[] buf=new byte[1024];
                        for(int readnum; (readnum=picture.read(buf)) !=-1;)
                        {
                            baos.write(buf,0,readnum);
                        }
                        person_image=baos.toByteArray();
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(rootPane, e);
                    }
                }else if(action.equals("edit")){
                    try {
                        Icon icons = profile.getIcon();
                        BufferedImage bi = new BufferedImage(icons.getIconWidth(), icons.getIconHeight(), BufferedImage.TYPE_INT_RGB);
                        Graphics g = bi.createGraphics();
                        icons.paintIcon(null, g, 0, 0);
                        g.setColor(Color.WHITE);
                        g.drawString(profile.getText(), 10, 20);
                        g.dispose();

                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        ImageIO.write(bi, "jpg", os);
                        InputStream fis = new ByteArrayInputStream(os.toByteArray());
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024];
                        for (int readNum; (readNum = fis.read(buf)) != -1;) {
                            bos.write(buf, 0, readNum);
                            }
                         byte[] bytes = bos.toByteArray();
                        person_image = bytes;
                    } catch (IOException d) {
                        JOptionPane.showMessageDialog(rootPane, d);
                    }
                }

                
                if(action.equals("insert") || action.equals("edit")){
                    mc.insertOrEditStudent(lrn, lrn2, first_name, middle_name, last_name, prefix, mobile_no, email, gender, age, address, birth_date+"", pob, gfname, gmname, glname, gprefix, go, ga, gmn, pastSchool, general_average, total_numb, y, ai, curr, username, password, person_image, action);
                }else
                    User.deleteTable("student", "student", 0, lrn);
            }else   {
                if(!userFlag)    JOptionPane.showMessageDialog(new JFrame(), "Error!!! Username is already taken", "Dialog", JOptionPane.ERROR_MESSAGE);
                else JOptionPane.showMessageDialog(new JFrame(), "Error!!! Invalid input", "Dialog", JOptionPane.ERROR_MESSAGE);}
            clear();
            updateTable();
        }
    }//GEN-LAST:event_editMouseClicked

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
            java.util.logging.Logger.getLogger(Teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Curriculum;
    private javax.swing.JTextField aname;
    private javax.swing.JTextField average;
    private javax.swing.JLabel bdatelbl;
    private javax.swing.JButton deselect1;
    private javax.swing.JButton deselect2;
    private javax.swing.JButton ec;
    private javax.swing.JButton edit;
    private javax.swing.JButton esr;
    private javax.swing.JTextField gbrgy;
    private javax.swing.JTextField gcity;
    private javax.swing.JTextField gcountry;
    private javax.swing.JTextField gfname;
    private javax.swing.JTextField glname;
    private javax.swing.JTextField gmname;
    private javax.swing.JFormattedTextField gmnumber;
    private javax.swing.JTextField goccupation;
    private javax.swing.JTextField gprefix;
    private javax.swing.JTextField gprovince;
    private javax.swing.JTextField gregion;
    private javax.swing.JTextField gstreet;
    private javax.swing.JTextField icc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JComboBox<String> listTitle;
    private javax.swing.JLabel listTitlelbl;
    private javax.swing.JMenu menu1;
    private javax.swing.JMenu menu2;
    private javax.swing.JMenu menu3;
    private javax.swing.JLabel profile;
    private datechooser.beans.DateChooserCombo sbdate;
    private javax.swing.JTextField sbrgy;
    private javax.swing.JTextField scity;
    private javax.swing.JTextField scountry;
    private javax.swing.JFormattedTextField semail;
    private javax.swing.JTextField sfname;
    private javax.swing.JRadioButton sgfemale;
    private javax.swing.JRadioButton sgmale;
    private javax.swing.JTextField slname;
    private javax.swing.JTextField slrn;
    private javax.swing.JTextField smname;
    private javax.swing.JFormattedTextField smnumber;
    private javax.swing.JTextField spassword;
    private javax.swing.JTextField spbcity;
    private javax.swing.JTextField spbcountry;
    private javax.swing.JTextField spbprovince;
    private javax.swing.JTextField spbregion;
    private javax.swing.JTextField sprefix;
    private javax.swing.JTextField sprovince;
    private javax.swing.JTextField sregion;
    private javax.swing.JTextField ssection;
    private javax.swing.JTextField sstreet;
    private javax.swing.JMenuItem subclass;
    private javax.swing.JMenuItem subclass1;
    private javax.swing.JMenuItem substudent;
    private javax.swing.JMenuItem substudent1;
    private javax.swing.JMenuItem subsubject;
    private javax.swing.JMenuItem subsubject1;
    private javax.swing.JMenuItem subteacher;
    private javax.swing.JMenuItem subteacher1;
    private javax.swing.JTextField susername;
    private javax.swing.JComboBox<String> syear;
    private javax.swing.JTable table1;
    private javax.swing.JTable table2;
    private javax.swing.JPanel tablePanel1;
    private javax.swing.JPanel tablePanel2;
    private javax.swing.JTextField total;
    private javax.swing.JButton updateProfile;
    // End of variables declaration//GEN-END:variables
}
