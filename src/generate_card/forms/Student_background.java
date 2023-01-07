package generate_card.forms;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mysql.cj.xdevapi.Statement;
import generate_card.classes.MConnection;
import generate_card.classes.User;
import java.awt.Color;
import java.awt.Image;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.sql.*;  
import generate_card.classes.MConnection;
import generate_card.classes.User;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author Marvin
 */
public class Student_background extends javax.swing.JFrame {
    byte[] person_image = null;
    String path = "";
    public ButtonGroup btg = new ButtonGroup();
            
    String lrn = "";
    /**
     * Creates new form Student
     * @param lrn
     */
    public Student_background(String lrn) {
        initComponents();
        ImageIcon logo = new javax.swing.ImageIcon(getClass().getResource("/generate_card/images_or_icon/projectIcon.jpg"));
        setIconImage(logo.getImage());
        setLocationRelativeTo(null);
        if(User.userType.equals("principal") || User.userType.equals("staff")){
            menu1.setVisible(false);
            menu2.setText("HOME");
        }else if(User.userType.equals("teacher")){
            menu1.setVisible(false);
            menu2.setVisible(false);
            menu3.setText("HOME");
        }else if(User.userType.equals("student")){
            menu2.setVisible(false);
            menu3.setVisible(false);
            menu1.setVisible(false);
            menu4.setText("HOME");
        }
        try{
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            String QUERY = "";
            QUERY = "SELECT * FROM teacher INNER JOIN school on teacher.school_id = school.id WHERE school.principal_id = ? and teacher.isAdviser = true";
            PreparedStatement pst = conn.prepareStatement(QUERY);
            pst.setInt(1, User.principal_id);
            rs = pst.executeQuery();
            /// display all result at the table
            while(rs.next()){
                aname.addItem( User.getName("teacher", rs.getInt("id")));
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        this.lrn = lrn;
        updateTable();
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
        Curriculum.setForeground(new Color(a,b,c));
        
        
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
    
    private void updateTable(){
        try {
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            String QUERY = "SELECT * FROM student WHERE lrn = ?";
            PreparedStatement pst = conn.prepareStatement(QUERY);
            pst.setString(1, lrn);
            rs = pst.executeQuery();
            while(rs.next()){
                
                
                
                setColor(0, 0, 0);
                slrn.setText(lrn);
                sfname.setText(rs.getString("first_name"));
                smname.setText(rs.getString("middle_name"));
                slname.setText(rs.getString("last_name"));
                sprefix.setText(rs.getString("prefix"));
                String d = rs.getString("Date_of_Birth");
                try {
                    java.util.Date date = new SimpleDateFormat("yyyy-mm-dd").parse(d);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    sbdate.setSelectedDate(c);
                } catch (ParseException ex) {
                    java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                aname.setSelectedItem(User.getName("teacher", rs.getInt("adviser_id")));
                ssection.setText(User.getSection(rs.getInt("adviser_id")));
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        panel2 = new javax.swing.JPanel();
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
        goccupation = new javax.swing.JTextField();
        gmnumber = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        spassword = new javax.swing.JTextField();
        susername = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        spbcountry = new javax.swing.JTextField();
        spbregion = new javax.swing.JTextField();
        spbprovince = new javax.swing.JTextField();
        spbcity = new javax.swing.JTextField();
        panel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        slrn = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        syear = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ssection = new javax.swing.JTextField();
        aname = new javax.swing.JComboBox<>();
        Curriculum = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        profile = new javax.swing.JLabel();
        updateProfile = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        confirm = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menu1 = new javax.swing.JMenu();
        menu2 = new javax.swing.JMenu();
        subteacher1 = new javax.swing.JMenuItem();
        subsubject1 = new javax.swing.JMenuItem();
        subclass1 = new javax.swing.JMenuItem();
        substudent1 = new javax.swing.JMenuItem();
        menu3 = new javax.swing.JMenu();
        menu4 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 255, 128));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        panel2.setBackground(new java.awt.Color(0, 255, 128));
        panel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Student Background information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 18))); // NOI18N

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
                .addContainerGap(35, Short.MAX_VALUE))
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
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(sbrgy, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scity, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sprovince, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sregion, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scountry, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sstreet, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
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
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Guardian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 18))); // NOI18N

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

        jLabel6.setText("Mobile no:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(goccupation)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gmnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gmnumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(goccupation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 600, 160));

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
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(susername, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(spassword))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(susername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
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
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(spbprovince, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(spbregion, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(spbcountry, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spbcity, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 130, 160));

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
        );

        panel3.setBackground(new java.awt.Color(0, 255, 128));
        panel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Student School information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 2, 18))); // NOI18N
        panel3.setForeground(new java.awt.Color(255, 255, 255));
        panel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(0, 255, 128));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "----------------------------", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial Black", 3, 18))); // NOI18N
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(0, 255, 128));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel7.setText("LRN");

        slrn.setEditable(false);

        jLabel8.setText("Year");

        syear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "7", "8", "9", "10", "11", "12" }));

        jLabel9.setText("Adviser name:");

        jLabel10.setText("Section");

        ssection.setEditable(false);

        jLabel11.setText("Curriculum");

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
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addGap(65, 65, 65)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(syear, 0, 120, Short.MAX_VALUE)
                            .addComponent(slrn))
                        .addGap(0, 47, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(aname, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(37, 37, 37)
                        .addComponent(Curriculum)))
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
                    .addComponent(jLabel11))
                .addContainerGap(19, Short.MAX_VALUE))
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
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 34, Short.MAX_VALUE))
        );

        panel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 39, 310, 230));

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

        panel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 219, -1));

        updateProfile.setText("Upload profile picture");
        updateProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateProfileMouseClicked(evt);
            }
        });
        panel3.add(updateProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 240, 219, 30));

        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("VISAYAS STATE UNIVERSITY");
        panel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, -1, -1));

        confirm.setText("CONFIRM EDIT");
        confirm.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        confirm.setInheritsPopupMenu(true);
        confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(confirm, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(confirm, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)))
                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        substudent1.setText("STUDENT");
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
        menu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu3MouseClicked(evt);
            }
        });
        jMenuBar1.add(menu3);

        menu4.setBackground(new java.awt.Color(0, 255, 128));
        menu4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        menu4.setText("STUDENT");

        jMenuItem1.setBackground(new java.awt.Color(0, 255, 128));
        jMenuItem1.setText("GRADES");
        jMenuItem1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menu4.add(jMenuItem1);

        jMenuItem2.setBackground(new java.awt.Color(0, 255, 128));
        jMenuItem2.setText("BACKGROUND");
        jMenuItem2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        menu4.add(jMenuItem2);

        jMenuBar1.add(menu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.setVisible(false);
        new Student_grades(lrn).setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
        if(sfname.getText().trim().equals("First name"))
        sfname.setText("");
        sfname.setForeground(Color.BLACK);
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

    private void updateProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateProfileMouseClicked
        String path = "";
        Image image = null;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
            path = chooser.getSelectedFile().getAbsolutePath();
            System.out.println(path);
            ImageIcon icon = new ImageIcon(path);
            image = icon.getImage().getScaledInstance(profile.getWidth(), profile.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon i = new ImageIcon(image);
            profile.setIcon(i);
            this.path = path;

        }
    }//GEN-LAST:event_updateProfileMouseClicked

    private void substudent1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_substudent1ActionPerformed
        this.setVisible(false);
        new School_student().setVisible(true);
    }//GEN-LAST:event_substudent1ActionPerformed

    private void subclass1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subclass1ActionPerformed
        this.setVisible(false);
        new School_class().setVisible(true);
    }//GEN-LAST:event_subclass1ActionPerformed

    private void subsubject1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subsubject1ActionPerformed
        this.setVisible(false);
        new School_subject().setVisible(true);
    }//GEN-LAST:event_subsubject1ActionPerformed

    private void subteacher1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subteacher1ActionPerformed
        this.setVisible(false);
        new School_teacher(User.principal_id).setVisible(true);
    }//GEN-LAST:event_subteacher1ActionPerformed

    private void menu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu1MouseClicked
        if(User.userType.equals("administrator")){
            this.setVisible(false);
            new Admin().setVisible(true);
        }
    }//GEN-LAST:event_menu1MouseClicked

    private void confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmActionPerformed
        String action = "edit";
        String[] options = new String[] {"Yes", "No"};
        int response = JOptionPane.showOptionDialog(null, "Are you sure?", "CERTAINTY",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        if(response == 0){
            int row = -1;
            String lrn = "";
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
                gender = "Female";
            else if(sgmale.isSelected())
                gender = "Male";
            String address = sstreet.getText() +","+ sbrgy.getText() +","+ scity.getText() +","+ sprovince.getText() +","+ sregion.getText() +","+ scountry.getText();    //////
            String bdate = sbdate.getText();            
            LocalDate today = LocalDate.now();
            java.util.Date bdate2 = null;
            try {
                bdate2 = new SimpleDateFormat("M/D/YY").parse(bdate);
            } catch (ParseException ex) {
                java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            Calendar c = Calendar.getInstance();
            c.setTime(bdate2);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            int date = c.get(Calendar.DATE);
            LocalDate birth_date = LocalDate.of(year, month, date);
            String Birth = birth_date + "";     ///////
            Period agey = Period.between(birth_date, today);    ////
            int age = agey.getYears();      ////
            String pob = spbcity.getText() + "," + spbprovince.getText() + "," + spbregion.getText() + "," + spbcountry.getText();          /////
            String gfname = this.gfname.getText();
            String gmname = this.gmname.getText();
            String glname = this.glname.getText();
            String gprefix = this.gprefix.getText();
            if(gprefix.equals("Prefix e.g(Jr.)"))    gprefix = "";
            String go = goccupation.getText();
            String ga = gstreet.getText() +","+ gbrgy.getText() +","+ gcity.getText() +","+ gprovince.getText() +","+ gregion.getText() +","+ gcountry.getText();
            String gmn = gmnumber.getText();
            int y = Integer.valueOf(syear.getSelectedItem()+"");
            //// general average is not included since student can exist without average
            //// total number of subject is not create as well during creation of student
            String[] id_and_adviser = (aname.getSelectedItem()+"").toString().split(",");
            int ai = Integer.valueOf(id_and_adviser[0]);
            String curr = Curriculum.getText();
            String username = susername.getText();
            String password = spassword.getText();
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
                    System.out.print(e);
                }
            }else{
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
                        System.out.println("read " + readNum + " bytes,");
                        }
                     byte[] bytes = bos.toByteArray();
                    person_image = bytes;
                } catch (IOException d) {
                    JOptionPane.showMessageDialog(rootPane, d);
                }
            }
            if(action.equals("insert") || action.equals("edit")){
                mc.insertOrEditStudent(lrn, lrn2, first_name, middle_name, last_name, prefix, mobile_no, email, gender, age, address, birth_date+"", pob, gfname, gmname, glname, gprefix, go, ga, gmn, y, ai, curr, username, password, person_image, action);
            }else
                User.deleteTable("student", "student", 0, lrn);
            updateTable();
        }
    }//GEN-LAST:event_confirmActionPerformed

    private void menu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu3MouseClicked
        String QUERY = "SELECT * FROM student WHERE lrn = ?";
        try{
            ///List all of principals
            MConnection mc = new MConnection();
            java.sql.Statement stmt = mc.getStmt();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            PreparedStatement pst = null;
            pst = conn.prepareStatement(QUERY);
            pst.setString(1, lrn);
            rs = pst.executeQuery();
            while(rs.next()){   
                this.setVisible(false);
                new Teacher(rs.getInt("adviser_id")).setVisible(true);
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menu3MouseClicked

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
            java.util.logging.Logger.getLogger(Student_background.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Student_background.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Student_background.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Student_background.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Curriculum;
    private javax.swing.JComboBox<String> aname;
    private javax.swing.JLabel bdatelbl;
    private javax.swing.JButton confirm;
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu menu1;
    private javax.swing.JMenu menu2;
    private javax.swing.JMenu menu3;
    private javax.swing.JMenu menu4;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
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
    private javax.swing.JMenuItem subclass1;
    private javax.swing.JMenuItem substudent1;
    private javax.swing.JMenuItem subsubject1;
    private javax.swing.JMenuItem subteacher1;
    private javax.swing.JTextField susername;
    private javax.swing.JComboBox<String> syear;
    private javax.swing.JButton updateProfile;
    // End of variables declaration//GEN-END:variables
}
