package generate_card.forms;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import javax.swing.JFrame;
import generate_card.classes.MConnection;
import generate_card.classes.User;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;


/**
 *
 * @author Marvin
 */
public class School_teacher extends javax.swing.JFrame {
    byte[] person_image = null;
    String path = "";
    ButtonGroup btg = new ButtonGroup();
    int principal_id;
    int school_id;
    /**
     * Creates new form add_human
     */
    
    public School_teacher(int pid) {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        ImageIcon logo = new javax.swing.ImageIcon(getClass().getResource("/generate_card/images_or_icon/projectIcon.jpg"));
        setIconImage(logo.getImage());
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i = 0; i < table1.getColumnCount(); i++)
            table1.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        
        if(!User.userType.equals("administrator")){
            if(User.userType.equals("staff"))
                jPanel2.setVisible(false);
            menu1.setVisible(false);
            menu2.setText("HOME");
        }

        ///CREATE A BUTTON GROUP FOR GENDER
        btg.add(tgmale);
        btg.add(tgfemale);  
        
        tedit.setVisible(false);
        tfire.setVisible(false);
        
        this.principal_id = User.principal_id = pid;
        updateTable();
        
    }
    private void setSchool_id(){
        try {
            MConnection mc = new MConnection();
            Statement stmt = mc.getStmt();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            String QUERY = "";
            QUERY = "SELECT * FROM school WHERE school.principal_id = "+User.principal_id;
            rs = stmt.executeQuery(QUERY);
            while(rs.next()){
                school_id = rs.getInt("id");
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateTable(){
        clearTeacher();
        etrecord.setVisible(false);
        try{
            String table_name = listTitle.getSelectedItem().toString().toLowerCase();
            tablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), (table_name + "'s").toUpperCase(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 2, 18), new java.awt.Color(255, 255, 255)));
            Boolean flag = false;
            if(table_name.equals("adviser")) flag = true;
            ///List all of principals
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            String QUERY = "";
            if(flag)
                QUERY = "SELECT * FROM teacher INNER JOIN school on teacher.school_id = school.id  WHERE school.principal_id = " +User.principal_id+" and teacher.isAdviser ="+table_name.equals("adviser");
            else    QUERY = "SELECT * FROM teacher INNER JOIN school on teacher.school_id = school.id  WHERE school.principal_id = " +User.principal_id;
            PreparedStatement pst = conn.prepareStatement(QUERY);
            rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel)table1.getModel();
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
              model.removeRow(i);
            }
            /// display all result at the table
            while(rs.next()){
                int SelectedId = rs.getInt("ID");
                String name = "";
                name = rs.getString("first_name") +" "+ rs.getString("middle_name") +" "+ rs.getString("last_name") +" "+ rs.getString("prefix");
                model.addRow(new Object[]{SelectedId, name});
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        setSchool_id();
        isteacher(true);
    }
    
    public void isteacher(Boolean flag){
        
        if(flag){
            advisory_class.setVisible(false);
            adviser.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Teacher", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 18)));
        }
        else{
            advisory_class.setVisible(true);
            adviser.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
            advisory_class.setVisible(true);
        }
    }
    
    public void hireOreditTeacher(String decision){
        ///make sure every field is filled up
        String[] options = new String[] {"Yes", "No"};
        int response = JOptionPane.showOptionDialog(null, "Are you sure?", "CERTAINTY",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        if(response == 0){
            
            int id = -1, row = -1;
            if(decision.equals("edit")){
                row = table1.getSelectedRow();
                id = (int) table1.getModel().getValueAt(row, 0);}
            
            MConnection mc = new MConnection();
            String fname = tfname.getText();
            String mname = tmname.getText();
            String lname = tlname.getText();
            String username = tusername.getText();
            String password = tpassword.getText();
            String gender = "";
            if(tgmale.isSelected()) gender = "Male";
            else if(tgfemale.isSelected())  gender ="Female";
            String bdate = tbdate.getText();
            String mnumber = tmnumber.getText();
            String email = temail.getText();
            String country = tcountry.getText();
            String region = tregion.getText();
            String province = tprovince.getText();
            String city = tcity.getText();
            String brgy = tbrgy.getText();
            String street = tstreet.getText();
            Boolean userFlag = false;
            if(decision.equals("build"))    userFlag = mc.usernameIsValid(username);
            else    userFlag = mc.usernameIsValid("teacher", username, id);
            String pre_fix = "";
            pre_fix = tprefix.getText();
            if(!fname.equals("") && 
                    !fname.equals("First name") && 
                    !mname.equals("") && 
                    !mname.equals("Middle name") && 
                    !lname.equals("") && 
                    !lname.equals("Last name") && 
                    !username.equals("") && userFlag  && !username.equals("Username") && 
                    !password.equals("") && !password.equals("Password") && 
                    !gender.equals("") && 
                    !bdate.equals("") && 
                    userFlag &&
                    User.isValidmobile(mnumber) &&
                    User.isValidEmail(email) && 
                    !country.equals("") && !country.equals("Country") &&  
                    !region.equals("") && !region.equals("Region") && 
                    !province.equals("") && !province.equals("Province") && 
                    !city.equals("") && !city.equals("City") && 
                    !brgy.equals("") && !brgy.equals("Barangay") && 
                    !street.equals("") && !street.equals("Sitio / Blk & lot / Street") && 
                    (!path.equals("") || !decision.equals("hire"))){
                if(pre_fix.equals("Prefix e.g(Jr.)") || pre_fix.equals("")){
                    pre_fix = "";
                }
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
                Period age = Period.between(birth_date, today);
                String address = street +","+ brgy +","+ city +","+ province +","+ region +","+ country;
                int agey = age.getYears();
                Statement stmt = mc.getStmt();
                ///set image as byte
                if(decision.equals("hire")){
                    InputStream picture = null;
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
                        JOptionPane.showMessageDialog(new JFrame(), "242", "Dialog", JOptionPane.ERROR_MESSAGE);
                    }
                }
                int grades = -1;
                String sections = "";
                Boolean isAdviserB = false;
                if(decision.equals("hire"))
                    id = school_id;
                if(isAdviser.isSelected()){
                    if(!grade.getSelectedItem().toString().equals(" ")){
                        grades = Integer.valueOf(grade.getSelectedItem().toString());
                        if(!(section.getText().equals("section") || section.getText().equals(""))){
                            sections = section.getText().toString();
                            isAdviserB = true;
                            mc.insertOrEditTeacher(decision, fname, mname, lname, pre_fix, agey, gender, birth_date+"", mnumber, email, address, username, password,  person_image, grades, sections, isAdviserB, id);
                        }
                        else    JOptionPane.showMessageDialog(new JFrame(), "Error!!! Invalid section", "Dialog", JOptionPane.ERROR_MESSAGE);
                    }
                    else    JOptionPane.showMessageDialog(new JFrame(), "Error!!! Invalid grade, you choose blank", "Dialog", JOptionPane.ERROR_MESSAGE);
                }
                else
                    mc.insertOrEditTeacher(decision, fname, mname, lname, pre_fix, agey, gender, birth_date+"", mnumber, email, address, username, password,  person_image, grades, sections, isAdviserB, id);
            }else   {
                if(!userFlag)    JOptionPane.showMessageDialog(new JFrame(), "Error!!! Username is already taken", "Dialog", JOptionPane.ERROR_MESSAGE);
                else JOptionPane.showMessageDialog(new JFrame(), "Error!!! Invalid input", "Dialog", JOptionPane.ERROR_MESSAGE);}
            clearTeacher();
            updateTable();
        }
    }
    
    public void setColorTeacher(int a, int b, int c){
        
        tprefix.setForeground(new Color(a,b,c));
        tfname.setForeground(new Color(a,b,c));
        tmname.setForeground(new Color(a,b,c));
        tlname.setForeground(new Color(a,b,c));
        tbdate.setForeground(new Color(a,b,c));
        tmnumber.setForeground(new Color(a,b,c));
        temail.setForeground(new Color(a,b,c));
        tcountry.setForeground(new Color(a,b,c));
        tregion.setForeground(new Color(a,b,c));
        tprovince.setForeground(new Color(a,b,c));
        tcity.setForeground(new Color(a,b,c));
        tbrgy.setForeground(new Color(a,b,c));
        tstreet.setForeground(new Color(a,b,c));    
        tusername.setForeground(new Color(a,b,c));
        tpassword.setForeground(new Color(a,b,c));
        section.setForeground(new Color(a,b,c));
    }
    
    public void clearTeacher(){
        tfname.setText("First name");
        tmname.setText("Middle name");
        tlname.setText("Last name");
        this.btg.clearSelection();
        tbdate.setText(null);
        tbdate.setCurrent(null);
        tmnumber.setText("09123456789");
        temail.setText("example@gmail.com");
        tcountry.setText("Country");
        tregion.setText("Region");
        tprovince.setText("Province");
        tcity.setText("City");
        tbrgy.setText("Barangay");
        tstreet.setText("Sitio / Blk & lot / Street");
        tusername.setText("Username");
        tpassword.setText("Password");
        tprefix.setText("Prefix e.g(Jr.)");
        profile.setIcon(null);
        section.setText("Section");
        grade.setSelectedIndex(0);
        isteacher(true);
        isAdviser.setSelected(false);
        setColorTeacher(153, 153, 153);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tablePanel = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        listTitle = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        deselect = new javax.swing.JButton();
        search = new javax.swing.JTextField();
        searchcombo = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        adviser = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        tlname = new javax.swing.JTextField();
        tfname = new javax.swing.JTextField();
        tmname = new javax.swing.JTextField();
        tprefix = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        tregion = new javax.swing.JTextField();
        tcountry = new javax.swing.JTextField();
        tprovince = new javax.swing.JTextField();
        tcity = new javax.swing.JTextField();
        tstreet = new javax.swing.JTextField();
        tbrgy = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        bdatelbl = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tgmale = new javax.swing.JRadioButton();
        tgfemale = new javax.swing.JRadioButton();
        tbdate = new datechooser.beans.DateChooserCombo();
        tmnumber = new javax.swing.JFormattedTextField();
        temail = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        tpassword = new javax.swing.JTextField();
        tusername = new javax.swing.JTextField();
        isAdviser = new javax.swing.JRadioButton();
        tfire = new javax.swing.JButton();
        thire = new javax.swing.JButton();
        tedit = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        profile = new javax.swing.JLabel();
        updateProfile = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        advisory_class = new javax.swing.JPanel();
        erwa = new javax.swing.JPanel();
        section = new javax.swing.JTextField();
        grade = new javax.swing.JComboBox<>();
        glabel = new javax.swing.JLabel();
        etrecord = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menu1 = new javax.swing.JMenu();
        menu2 = new javax.swing.JMenu();
        subteacher1 = new javax.swing.JMenuItem();
        subsubject1 = new javax.swing.JMenuItem();
        subclass1 = new javax.swing.JMenuItem();
        substudent1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 255, 128));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        tablePanel.setBackground(new java.awt.Color(0, 255, 128));
        tablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ADVISER'S", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 18))); // NOI18N

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table1.setShowVerticalLines(false);
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        jScrollPane18.setViewportView(table1);
        if (table1.getColumnModel().getColumnCount() > 0) {
            table1.getColumnModel().getColumn(0).setPreferredWidth(10);
            table1.getColumnModel().getColumn(1).setPreferredWidth(300);
        }

        listTitle.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Adviser", "Teacher" }));
        listTitle.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                listTitleItemStateChanged(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(0, 255, 128));
        jLabel4.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel4.setText("List of :");

        deselect.setText("de-select");
        deselect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deselectActionPerformed(evt);
            }
        });

        search.setForeground(new java.awt.Color(153, 153, 153));
        search.setText("SEARCH");
        search.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchFocusLost(evt);
            }
        });
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchKeyReleased(evt);
            }
        });

        searchcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "NAME" }));

        jLabel11.setText("Search by: ");

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(deselect, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(search, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tablePanelLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tablePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(listTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128))
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tablePanelLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(deselect))
                    .addGroup(tablePanelLayout.createSequentialGroup()
                        .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(searchcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(listTitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        adviser.setBackground(new java.awt.Color(0, 255, 128));
        adviser.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        adviser.setForeground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(0, 255, 128));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Personal information", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial Black", 1, 18))); // NOI18N
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(0, 255, 128));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Name"));

        tlname.setForeground(new java.awt.Color(153, 153, 153));
        tlname.setText("Last name");
        tlname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tlnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tlnameFocusLost(evt);
            }
        });

        tfname.setForeground(new java.awt.Color(153, 153, 153));
        tfname.setText("First name");
        tfname.setToolTipText("");
        tfname.setName(""); // NOI18N
        tfname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfnameFocusLost(evt);
            }
        });

        tmname.setForeground(new java.awt.Color(153, 153, 153));
        tmname.setText("Middle name");
        tmname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tmnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tmnameFocusLost(evt);
            }
        });

        tprefix.setForeground(new java.awt.Color(153, 153, 153));
        tprefix.setText("Prefix e.g(Jr.)");
        tprefix.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tprefixFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tprefixFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfname)
                    .addComponent(tmname)
                    .addComponent(tlname)
                    .addComponent(tprefix, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(tfname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tmname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tlname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tprefix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel11.setBackground(new java.awt.Color(0, 255, 128));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Address"));

        tregion.setForeground(new java.awt.Color(153, 153, 153));
        tregion.setText("Region");
        tregion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tregionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tregionFocusLost(evt);
            }
        });

        tcountry.setForeground(new java.awt.Color(153, 153, 153));
        tcountry.setText("Country");
        tcountry.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tcountryFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tcountryFocusLost(evt);
            }
        });

        tprovince.setForeground(new java.awt.Color(153, 153, 153));
        tprovince.setText("Province");
        tprovince.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tprovinceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tprovinceFocusLost(evt);
            }
        });

        tcity.setForeground(new java.awt.Color(153, 153, 153));
        tcity.setText("City");
        tcity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tcityFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tcityFocusLost(evt);
            }
        });

        tstreet.setForeground(new java.awt.Color(153, 153, 153));
        tstreet.setText("Sitio / Blk & lot / Street");
        tstreet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tstreetFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tstreetFocusLost(evt);
            }
        });

        tbrgy.setForeground(new java.awt.Color(153, 153, 153));
        tbrgy.setText("Barangay");
        tbrgy.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbrgyFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbrgyFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tstreet, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(tbrgy, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                        .addComponent(tcity, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tprovince, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tregion, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tcountry, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(29, 29, 29))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tcountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tregion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tprovince, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tcity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbrgy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tstreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel12.setBackground(new java.awt.Color(0, 255, 128));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Other information"));

        bdatelbl.setText("Birth date:");

        jLabel2.setText("Gender:");

        tgmale.setText("Male");

        tgfemale.setText("Female");

        try {
            tbdate.setDefaultPeriods(new datechooser.model.multiple.PeriodSet());
        } catch (datechooser.model.exeptions.IncompatibleDataExeption e1) {
            e1.printStackTrace();
        }

        tmnumber.setForeground(new java.awt.Color(153, 153, 153));
        tmnumber.setText("09123456789");
        tmnumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tmnumberFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tmnumberFocusLost(evt);
            }
        });
        tmnumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tmnumberKeyTyped(evt);
            }
        });

        temail.setForeground(new java.awt.Color(153, 153, 153));
        temail.setText("example@gmail.com");
        temail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                temailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                temailFocusLost(evt);
            }
        });

        jLabel3.setText("Mobile no.");

        jLabel5.setText("Email:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bdatelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(tgmale)
                        .addGap(11, 11, 11)
                        .addComponent(tgfemale))
                    .addComponent(tbdate, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(temail, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(tmnumber)))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bdatelbl)
                    .addComponent(tbdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tmnumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tgmale)
                    .addComponent(tgfemale)
                    .addComponent(temail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(0, 255, 128));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Account"));

        tpassword.setForeground(new java.awt.Color(153, 153, 153));
        tpassword.setText("Password");
        tpassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ptpasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ptpasswordFocusLost(evt);
            }
        });

        tusername.setForeground(new java.awt.Color(153, 153, 153));
        tusername.setText("Username");
        tusername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ptusernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ptusernameFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tusername, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .addComponent(tpassword))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(tusername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        isAdviser.setText("isAdviser");
        isAdviser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isAdviserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(isAdviser))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(isAdviser))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tfire.setText("FIRE");
        tfire.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfireMouseClicked(evt);
            }
        });
        tfire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfireActionPerformed(evt);
            }
        });

        thire.setText("HIRE");
        thire.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                thireMouseClicked(evt);
            }
        });

        tedit.setText("EDIT");
        tedit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                teditMouseClicked(evt);
            }
        });

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Profile picture", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Times New Roman", 0, 12))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profile, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(profile, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
        );

        updateProfile.setText("Upload profile picture");
        updateProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateProfileMouseClicked(evt);
            }
        });

        jLabel1.setText("VISAYAS STATE UNIVERSITY");

        javax.swing.GroupLayout adviserLayout = new javax.swing.GroupLayout(adviser);
        adviser.setLayout(adviserLayout);
        adviserLayout.setHorizontalGroup(
            adviserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adviserLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(adviserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(adviserLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(adviserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(updateProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(thire, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(adviserLayout.createSequentialGroup()
                                .addComponent(tedit, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tfire, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(adviserLayout.createSequentialGroup()
                        .addGap(394, 394, 394)
                        .addComponent(jLabel1)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        adviserLayout.setVerticalGroup(
            adviserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adviserLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(adviserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(adviserLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(thire, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(adviserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfire)
                            .addComponent(tedit))))
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        advisory_class.setBackground(new java.awt.Color(0, 255, 128));
        advisory_class.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Adviser", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 18))); // NOI18N

        erwa.setBackground(new java.awt.Color(0, 255, 128));
        erwa.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Class"));

        section.setForeground(new java.awt.Color(153, 153, 153));
        section.setText("Section");
        section.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                psectionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                psectionFocusLost(evt);
            }
        });

        grade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7", "8", "9", "10" }));

        glabel.setText("Grade");

        javax.swing.GroupLayout erwaLayout = new javax.swing.GroupLayout(erwa);
        erwa.setLayout(erwaLayout);
        erwaLayout.setHorizontalGroup(
            erwaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(erwaLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(erwaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(section, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(erwaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(glabel)
                        .addComponent(grade, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        erwaLayout.setVerticalGroup(
            erwaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(erwaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(glabel)
                .addGap(2, 2, 2)
                .addComponent(grade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(section, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout advisory_classLayout = new javax.swing.GroupLayout(advisory_class);
        advisory_class.setLayout(advisory_classLayout);
        advisory_classLayout.setHorizontalGroup(
            advisory_classLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(advisory_classLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(erwa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        advisory_classLayout.setVerticalGroup(
            advisory_classLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(advisory_classLayout.createSequentialGroup()
                .addComponent(erwa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );

        etrecord.setText("Enter teacher's record");
        etrecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etrecordMouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 255, 128));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Create / Edit / Fire     Staff's", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24))); // NOI18N

        jButton1.setText("GO TO STAFF'S");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(adviser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(advisory_class, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(etrecord, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etrecord, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(advisory_class, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(adviser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        etrecord.setVisible(true);

        tfire.setVisible(true);
        tedit.setVisible(true);
        thire.setVisible(false);
        int id, row;
    
        row = table1.getSelectedRow();
        id = (int) table1.getModel().getValueAt(row, 0);
        
        try{
            String table_name = listTitle.getSelectedItem().toString().toLowerCase();
            tablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), (table_name + "'s").toUpperCase(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 2, 18), new java.awt.Color(255, 255, 255)));
            
            ///List all of principals
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            String QUERY = "";
            QUERY = "SELECT * FROM teacher WHERE id = ?"; 
            PreparedStatement pst = conn.prepareStatement(QUERY);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            /// display all result at the table
            while(rs.next()){
                etrecord.setVisible(true);
                setColorTeacher(0, 0, 0);
                principal_id = rs.getInt("id");
                tfname.setText(rs.getString("first_name"));
                tmname.setText(rs.getString("middle_name"));
                tlname.setText(rs.getString("last_name"));
                tprefix.setText(rs.getString("prefix"));
                String d = rs.getString("bdate");
                try {
                    java.util.Date date = new SimpleDateFormat("yyyy-mm-dd").parse(d);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    tbdate.setSelectedDate(c);
                } catch (ParseException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(rs.getString("gender").equals("Male")){
                    tgmale.setSelected(true);
                    tgfemale.setSelected(false);
                }
                else{
                    tgmale.setSelected(false);
                    tgfemale.setSelected(true);
                }
                tmnumber.setText(rs.getString("mobile_no"));
                temail.setText(rs.getString("email"));
                String add = rs.getString("address");
                String split[] = add.split(",");
                int ctr = 0;
                for(String s: split){
                    switch(ctr){
                        case 0:
                            tstreet.setText(s);
                        case 1:
                            tbrgy.setText(s);
                        case 2:
                            tcity.setText(s);
                        case 3:
                            tprovince.setText(s);
                        case 4:
                            tregion.setText(s);
                        case 5:
                            tcountry.setText(s);
                        default:;
                    }
                    ctr++;
                }
                tusername.setText(rs.getString("username"));
                tpassword.setText(rs.getString("password"));
                isAdviser.setSelected(rs.getBoolean("isAdviser"));
                
                person_image = rs.getBytes("picture");
                
                ImageIcon icon = new ImageIcon(person_image);
                Image image = icon.getImage().getScaledInstance(profile.getWidth(), profile.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon i = new ImageIcon(image);
                profile.setIcon(i);
                if(isAdviser.isSelected()){
                    isteacher(false);
                    grade.setSelectedItem(rs.getInt("grade")+"");
                    section.setText(rs.getString("section"));
                }else
                    isteacher(true);           
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_table1MouseClicked

    private void deselectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deselectActionPerformed
        updateTable();
    }//GEN-LAST:event_deselectActionPerformed

    private void tlnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tlnameFocusGained
        if(tlname.getText().trim().equals("Last name"))
        tlname.setText("");
        tlname.setForeground(Color.BLACK);
    }//GEN-LAST:event_tlnameFocusGained

    private void tlnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tlnameFocusLost
        if(tlname.getText().trim().equals("")){
            tlname.setText("Last name");
            tlname.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_tlnameFocusLost

    private void tfnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfnameFocusGained
        if(tfname.getText().trim().equals("First name"))
        tfname.setText("");
        tfname.setForeground(Color.BLACK);
    }//GEN-LAST:event_tfnameFocusGained

    private void tfnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfnameFocusLost
        if(tfname.getText().trim().equals("")){
            tfname.setText("First name");
            tfname.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_tfnameFocusLost

    private void tmnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tmnameFocusGained
        if(tmname.getText().trim().equals("Middle name"))
        tmname.setText("");
        tmname.setForeground(Color.BLACK);
    }//GEN-LAST:event_tmnameFocusGained

    private void tmnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tmnameFocusLost
        if(tmname.getText().trim().equals("")){
            tmname.setText("Middle name");
            tmname.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_tmnameFocusLost

    private void tprefixFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tprefixFocusGained
        if(tprefix.getText().trim().equals("Prefix e.g(Jr.)"))
        tprefix.setText("");
        tprefix.setForeground(Color.BLACK);
    }//GEN-LAST:event_tprefixFocusGained

    private void tprefixFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tprefixFocusLost
        if(tprefix.getText().trim().equals("")){
            tprefix.setText("Prefix e.g(Jr.)");
            tprefix.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_tprefixFocusLost

    private void tregionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tregionFocusGained
        if(tregion.getText().trim().equals("Region"))
        tregion.setText("");
        tregion.setForeground(Color.BLACK);
    }//GEN-LAST:event_tregionFocusGained

    private void tregionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tregionFocusLost
        if(tregion.getText().trim().equals("")){
            tregion.setText("Region");
            tregion.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_tregionFocusLost

    private void tcountryFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tcountryFocusGained
        if(tcountry.getText().trim().equals("Country"))
        tcountry.setText("");
        tcountry.setForeground(Color.BLACK);
    }//GEN-LAST:event_tcountryFocusGained

    private void tcountryFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tcountryFocusLost
        if(tcountry.getText().trim().equals("")){
            tcountry.setText("Country");
            tcountry.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_tcountryFocusLost

    private void tprovinceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tprovinceFocusGained
        if(tprovince.getText().trim().equals("Province"))
        tprovince.setText("");
        tprovince.setForeground(Color.BLACK);
    }//GEN-LAST:event_tprovinceFocusGained

    private void tprovinceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tprovinceFocusLost
        if(tprovince.getText().trim().equals("")){
            tprovince.setText("Province");
            tprovince.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_tprovinceFocusLost

    private void tcityFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tcityFocusGained
        if(tcity.getText().trim().equals("City"))
        tcity.setText("");
        tcity.setForeground(Color.BLACK);
    }//GEN-LAST:event_tcityFocusGained

    private void tcityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tcityFocusLost
        if(tcity.getText().trim().equals("")){
            tcity.setText("City");
            tcity.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_tcityFocusLost

    private void tstreetFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tstreetFocusGained
        if(tstreet.getText().trim().equals("Sitio / Blk & lot / Street"))
        tstreet.setText("");
        tstreet.setForeground(Color.BLACK);
    }//GEN-LAST:event_tstreetFocusGained

    private void tstreetFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tstreetFocusLost
        if(tstreet.getText().trim().equals("")){
            tstreet.setText("Sitio / Blk & lot / Street");
            tstreet.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_tstreetFocusLost

    private void tbrgyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbrgyFocusGained
        if(tbrgy.getText().trim().equals("Barangay"))
        tbrgy.setText("");
        tbrgy.setForeground(Color.BLACK);
    }//GEN-LAST:event_tbrgyFocusGained

    private void tbrgyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbrgyFocusLost
        if(tbrgy.getText().trim().equals("")){
            tbrgy.setText("Barangay");
            tbrgy.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_tbrgyFocusLost

    private void tmnumberFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tmnumberFocusGained
        if(tmnumber.getText().trim().equals("09123456789"))
        tmnumber.setText("");
        tmnumber.setForeground(Color.BLACK);
    }//GEN-LAST:event_tmnumberFocusGained

    private void tmnumberFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tmnumberFocusLost
        if(tmnumber.getText().trim().equals("")){
            tmnumber.setText("09123456789");
            tmnumber.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_tmnumberFocusLost

    private void temailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_temailFocusGained
        if(temail.getText().trim().equals("example@gmail.com"))
        temail.setText("");
        temail.setForeground(Color.BLACK);
    }//GEN-LAST:event_temailFocusGained

    private void temailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_temailFocusLost
        if(temail.getText().trim().equals("")){
            temail.setText("example@gmail.com");
            temail.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_temailFocusLost

    private void ptpasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ptpasswordFocusGained
        if(tpassword.getText().trim().equals("Password"))
        tpassword.setText("");
        tpassword.setForeground(Color.BLACK);
    }//GEN-LAST:event_ptpasswordFocusGained

    private void ptpasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ptpasswordFocusLost
        if(tpassword.getText().trim().equals("")){
            tpassword.setText("Password");
            tpassword.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_ptpasswordFocusLost

    private void ptusernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ptusernameFocusGained
        if(tusername.getText().trim().equals("Username"))
        tusername.setText("");
        tusername.setForeground(Color.BLACK);
    }//GEN-LAST:event_ptusernameFocusGained

    private void ptusernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ptusernameFocusLost
        if(tusername.getText().trim().equals("")){
            tusername.setText("Username");
            tusername.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_ptusernameFocusLost

    private void thireMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thireMouseClicked
        hireOreditTeacher("hire");
    }//GEN-LAST:event_thireMouseClicked

    private void teditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_teditMouseClicked
        hireOreditTeacher("edit");
    }//GEN-LAST:event_teditMouseClicked

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

    private void psectionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_psectionFocusGained
        if(section.getText().trim().equals("Section"))
            section.setText("");
        section.setForeground(Color.BLACK);
    }//GEN-LAST:event_psectionFocusGained

    private void psectionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_psectionFocusLost
        if(section.getText().trim().equals("")){
            section.setText("Section");
            section.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_psectionFocusLost

    private void listTitleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_listTitleItemStateChanged
        search.setText("SEARCH");
        search.setForeground(new Color(153,153,153));
        searchcombo.setSelectedItem("ID");
        updateTable();
    }//GEN-LAST:event_listTitleItemStateChanged

    private void etrecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etrecordMouseClicked
        int row = table1.getSelectedRow();
        int id = (int) table1.getModel().getValueAt(row, 0);this.setVisible(false);
        this.setVisible(false);
        new Teacher(id).setVisible(true);

    }//GEN-LAST:event_etrecordMouseClicked

    private void tfireMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfireMouseClicked
        String[] options = new String[] {"Yes", "No"};
        int response = JOptionPane.showOptionDialog(null, "By doing so, all the record under this teacher will be lost forever", "Are you sure?",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        if(response == 0){
            int id, row;

            row = table1.getSelectedRow();
            id = (int) table1.getModel().getValueAt(row, 0);

            User.deleteTable("teacher", "teacher", id, "");
            updateTable();
        }
    }//GEN-LAST:event_tfireMouseClicked

    private void isAdviserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isAdviserActionPerformed
        String table_name = "";
        if(isAdviser.isSelected())
            table_name = "adviser";
        else
            table_name = "teacher";
        
        if(table_name.equals("teacher")){
            advisory_class.setVisible(false);
            adviser.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Teacher", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 18)));
        }
        else{
            advisory_class.setVisible(true);
            adviser.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
            advisory_class.setVisible(true);
        }
    }//GEN-LAST:event_isAdviserActionPerformed

    private void tfireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfireActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfireActionPerformed

    private void menu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu1MouseClicked
        if(User.userType.equals("administrator")){
            this.setVisible(false);
            new Admin().setVisible(true);
        }
    }//GEN-LAST:event_menu1MouseClicked

    private void subteacher1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subteacher1ActionPerformed
        this.setVisible(false);
        new School_teacher(User.principal_id).setVisible(true);
    }//GEN-LAST:event_subteacher1ActionPerformed

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

    private void searchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFocusGained
        if(search.getText().trim().equals("SEARCH"))
            search.setText("");
        search.setForeground(Color.BLACK);
    }//GEN-LAST:event_searchFocusGained

    private void searchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFocusLost
        if(search.getText().trim().equals("")){
            search.setText("SEARCH");
            search.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_searchFocusLost

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
        if(!search.getText().equals("")){
            String queryadviserid = "SELECT * FROM teacher INNER JOIN school on teacher.school_id = school.id WHERE school.principal_id = "+ User.principal_id +" and teacher.id LIKE ? and teacher.isAdviser = true";
            String queryadvisername = "SELECT * FROM teacher INNER JOIN school on teacher.school_id = school.id WHERE school.principal_id = "+ User.principal_id +" and (teacher.first_name LIKE ? or teacher.middle_name LIKE ? or teacher.last_name LIKE ? or teacher.prefix LIKE ?) and isAdviser = true";                             
            String queryteacherid = "SELECT * FROM teacher INNER JOIN school on teacher.school_id = school.id WHERE school.principal_id = "+ User.principal_id +" and teacher.id LIKE ?";
            String queryteachername = "SELECT * FROM teacher INNER JOIN school on teacher.school_id = school.id WHERE school.principal_id = "+ User.principal_id +" and (teacher.first_name LIKE ? or teacher.middle_name LIKE ? or teacher.last_name LIKE ? or teacher.prefix LIKE ?)";
            String Query = "";
            try{
                String table_name = listTitle.getSelectedItem().toString().toLowerCase();
                String search_name = searchcombo.getSelectedItem().toString().toLowerCase();

                switch(table_name){
                    case "adviser" -> {
                        switch(search_name){
                            case "id" -> {
                                Query = queryadviserid;
                            }
                            case "name" -> {
                                Query = queryadvisername;
                            }

                        }
                    }
                    case "teacher" -> {
                        switch(search_name){
                            case "id" -> {
                                Query = queryteacherid;
                            }
                            case "name" -> {
                                Query = queryteachername;
                            }
                        }
                    }
                }

                ///List all of principals
                MConnection mc = new MConnection();
                Statement stmt = mc.getStmt();
                Connection conn = mc.getConn();
                PreparedStatement pst = conn.prepareStatement(Query);
                String tofind = "%"+search.getText()+"%";
                pst.setString(1, tofind);
                if(search_name.equals("name")){
                    pst.setString(2, tofind);
                    pst.setString(3, tofind);
                    pst.setString(4, tofind);
                }
                ResultSet rs = pst.executeQuery();
                DefaultTableModel model = (DefaultTableModel)table1.getModel();
                int rowCount = model.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    model.removeRow(i);
                }
                /// display all result at the table
                while(rs.next()){
                    int SelectedId = rs.getInt("ID");
                    String name = rs.getString("first_name") +" "+ rs.getString("middle_name") +" "+ rs.getString("last_name") +" "+ rs.getString("prefix");
                    model.addRow(new Object[]{SelectedId, name});
                }
            } catch (SQLException ex) {
                System.out.print(ex);
            }
        }else updateTable();
    }//GEN-LAST:event_searchKeyReleased

    private void tmnumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tmnumberKeyTyped
        char c = evt.getKeyChar();
        String text = tmnumber.getText();
        if ((!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) ||  text.length() >= 11) {
             evt.consume();
        }
        else if(Character.isDigit(c)){
            if(Integer.valueOf(c+"") != 0 && text.isEmpty())
                evt.consume();
            else if(Integer.valueOf(c+"") != 9 && text.length() == 1)
                evt.consume();
        } 
    }//GEN-LAST:event_tmnumberKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.enable(false);
        new Staff_UI(this).setVisible(true);
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
            java.util.logging.Logger.getLogger(School_teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(School_teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(School_teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(School_teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel adviser;
    private javax.swing.JPanel advisory_class;
    private javax.swing.JLabel bdatelbl;
    private javax.swing.JButton deselect;
    private javax.swing.JPanel erwa;
    private javax.swing.JButton etrecord;
    private javax.swing.JLabel glabel;
    private javax.swing.JComboBox<String> grade;
    private javax.swing.JRadioButton isAdviser;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JComboBox<String> listTitle;
    private javax.swing.JMenu menu1;
    private javax.swing.JMenu menu2;
    private javax.swing.JLabel profile;
    private javax.swing.JTextField search;
    private javax.swing.JComboBox<String> searchcombo;
    private javax.swing.JTextField section;
    private javax.swing.JMenuItem subclass1;
    private javax.swing.JMenuItem substudent1;
    private javax.swing.JMenuItem subsubject1;
    private javax.swing.JMenuItem subteacher1;
    private javax.swing.JTable table1;
    private javax.swing.JPanel tablePanel;
    private datechooser.beans.DateChooserCombo tbdate;
    private javax.swing.JTextField tbrgy;
    private javax.swing.JTextField tcity;
    private javax.swing.JTextField tcountry;
    private javax.swing.JButton tedit;
    private javax.swing.JFormattedTextField temail;
    private javax.swing.JButton tfire;
    private javax.swing.JTextField tfname;
    private javax.swing.JRadioButton tgfemale;
    private javax.swing.JRadioButton tgmale;
    private javax.swing.JButton thire;
    private javax.swing.JTextField tlname;
    private javax.swing.JTextField tmname;
    private javax.swing.JFormattedTextField tmnumber;
    private javax.swing.JTextField tpassword;
    private javax.swing.JTextField tprefix;
    private javax.swing.JTextField tprovince;
    private javax.swing.JTextField tregion;
    private javax.swing.JTextField tstreet;
    private javax.swing.JTextField tusername;
    private javax.swing.JButton updateProfile;
    // End of variables declaration//GEN-END:variables
}
