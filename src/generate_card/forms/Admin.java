package generate_card.forms;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Image;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import generate_card.classes.MConnection;
import java.sql.SQLException;
import java.sql.*;    
import generate_card.classes.User;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Marvin
 */



public class Admin extends javax.swing.JFrame {
    ///Declaration
    byte[] person_image = null;
    String path = "";
    
    ///This buttong group is for the gender
    public ButtonGroup btg = new ButtonGroup();
    
    
    public Admin() {
        initComponents();
        ImageIcon logo = new javax.swing.ImageIcon(getClass().getResource("/generate_card/images_or_icon/projectIcon.jpg"));
        setIconImage(logo.getImage());
        
        //Full screen the jframe
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        //Every time the administrator open his account, the age of all participant will be updated
        User.updateAllAge();
        
        //Centralzed the content inside the table
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );

        ///SET IMAGE / ICON for statue
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        ImageIcon myImage = new javax.swing.ImageIcon(getClass().getResource("/generate_card/images_or_icon/statue.jpg"));
        Image img = myImage.getImage();
        Image img2 = img.getScaledInstance(statue.getWidth(), statue.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon i = new ImageIcon(img2);
        statue.setIcon(i);

        ///SET IMAGE / ICON for entering school
        myImage = new javax.swing.ImageIcon(getClass().getResource("/generate_card/images_or_icon/add school.jpg"));
        img = myImage.getImage();
        img2 = img.getScaledInstance(senter.getWidth(), senter.getHeight(), Image.SCALE_SMOOTH);
        i = new ImageIcon(img2);
        senter.setIcon(i);

        //assign pgmale and pgfemale radio button to the button group
        btg.add(pgmale);
        btg.add(pgfemale);  
        
        //hide the following button
        pedit.setVisible(false);
        pfire.setVisible(false);
        senter.setVisible(false);
        sedit.setVisible(false);
        sdestroy.setVisible(false);
        sbuild.setVisible(false);
        
        //update the table
        updateTable();
    }
    
    ////Updating the contents of the table
    private void updateTable(){
        clearPrincipal();
        clearSchool();
        try{
            
            //Change the title of the border at the panel where table is inside into the selected list title
            String table_name = listTitle.getSelectedItem().toString().toLowerCase();
            tablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (table_name + "'s").toUpperCase(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 2, 18), new java.awt.Color(255, 255, 255)));
            
            //Start the connection between the program and mysql database
            MConnection mc = new MConnection();
            Statement stmt = mc.getStmt();
            
            //Declare and assign value to the conn variable
            try (Connection conn = mc.getConn()) {
                
                //Declare and assin  query
                String QUERY = "SELECT * FROM "+table_name+" WHERE admin_id = " +User.id;
                
                //Get the result and assign it to the result set variable named rs
                try (ResultSet rs = stmt.executeQuery(QUERY)) {
                    
                    //Remove all rows at the table
                    DefaultTableModel model = (DefaultTableModel)table.getModel();
                    int rowCount = model.getRowCount();
                    for (int i = rowCount - 1; i >= 0; i--) {
                        model.removeRow(i);
                    }
                    
                    /// display all result at the table
                    while(rs.next()){
                        int SelectedId = rs.getInt("ID");
                        String name = "";
                        if(table_name.equals("principal"))
                            name = rs.getString("first_name") +" "+ rs.getString("middle_name") +" "+ rs.getString("last_name") +" "+ rs.getString("prefix");
                        else name = rs.getString("name");
                        model.addRow(new Object[]{SelectedId, name});
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR", "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Set the foreground color of the text, especially the jtextfield in prncipal panel/side
    public void setColorPrincipal(int a, int b, int c){
        
        prefix.setForeground(new Color(a,b,c));
        pfname.setForeground(new Color(a,b,c));
        pmname.setForeground(new Color(a,b,c));
        plname.setForeground(new Color(a,b,c));
        pbdate.setForeground(new Color(a,b,c));
        pmnumber.setForeground(new Color(a,b,c));
        pemail.setForeground(new Color(a,b,c));
        pcountry.setForeground(new Color(a,b,c));
        pregion.setForeground(new Color(a,b,c));
        pprovince.setForeground(new Color(a,b,c));
        pcity.setForeground(new Color(a,b,c));
        pbrgy.setForeground(new Color(a,b,c));
        pstreet.setForeground(new Color(a,b,c));    
        pusername.setForeground(new Color(a,b,c));
        ppassword.setForeground(new Color(a,b,c));
    }
    
    //Set the foreground color of the text in school panel/side
    public void setColorSchool(int a, int b, int c){
        
        sname.setForeground(new Color(a,b,c));
        smission.setForeground(new Color(a,b,c));
        scvalues.setForeground(new Color(a,b,c));
        svision.setForeground(new Color(a,b,c));
        scountry.setForeground(new Color(a,b,c));
        sregion.setForeground(new Color(a,b,c));
        sprovince.setForeground(new Color(a,b,c));
        scity.setForeground(new Color(a,b,c));
        sbrgy.setForeground(new Color(a,b,c)); 
    }
    
    //Clear all the inputs in the principal's panel/side
    public void clearPrincipal(){
        pfname.setText("First name");
        pmname.setText("Middle name");
        plname.setText("Last name");
        this.btg.clearSelection();
        pbdate.setText(null);
        pbdate.setCurrent(null);
        pmnumber.setText("09123456789");
        pemail.setText("example@gmail.com");
        pcountry.setText("Country");
        pregion.setText("Region");
        pprovince.setText("Province");
        pcity.setText("City");
        pbrgy.setText("Barangay");
        pstreet.setText("Sitio / Blk & lot / Street");
        pusername.setText("Username");
        ppassword.setText("Password");
        prefix.setText("Prefix e.g(Jr.)");
        setColorPrincipal(153, 153, 153);  // set the color of the text's at the principal's foreground
        profile.setIcon(null);  //erase the picture or image
    }
    
    //Clear all the inputs in school's panel/side
    public void clearSchool(){
        sname.setText("Name of School");
        smission.setText("");
        svision.setText("");
        scvalues.setText("");
        scountry.setText("Country");
        sregion.setText("Region");
        sprovince.setText("Province");
        scity.setText("City");
        sbrgy.setText("Barangay");
        setColorSchool(153, 153, 153);  // set the color of the text's at the school's foreground
    }
    
    ///Insert /  edit data at table type school
    public void buildOrEditSchool(String decision){
        //Ask the certainty of the decision
        String[] options = new String[] {"Yes", "No"};
        int response = JOptionPane.showOptionDialog(null, "Are you sure?", "CERTAINTY",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        
        //If the user response as yes
        if(response == 0){
            
            //declaration and assignment of the varable 
            String name = sname.getText();
            String mission = smission.getText();
            String vision = svision.getText();
            String core_values = scvalues.getText();
            String country = scountry.getText();
            String region = sregion.getText();
            String province = sprovince.getText();
            String city = scity.getText();
            String barangay = sbrgy.getText();
            
            //if all inputs are invald
            if(name.isEmpty() || mission.isEmpty() || vision.isEmpty() || core_values.isEmpty() || country.isEmpty() || region.isEmpty() || province.isEmpty() || city.isEmpty() || barangay.isEmpty() || name.equals("Name of School") || country.equals("Country") || region.equals("Region") || province.equals("Province") || city.equals("City") || barangay.equals("Barangay"))
                JOptionPane.showMessageDialog(new JFrame(), "Error!!! Invalid input", "Dialog", JOptionPane.ERROR_MESSAGE);
            else{   //the inputs are valid
                MConnection mc = new MConnection();
                mc.insertOrEditSchool(decision, name, barangay, city, province, region, country, mission, vision, core_values, User.principal_id);
            }
            
            //update the table again
            updateTable();
        }    
    }
    
    public void hireOreditPrincipal(String decision){
        //Ask thecertainty of the decison
        String[] options = new String[] {"Yes", "No"};
        int response = JOptionPane.showOptionDialog(null, "Are you sure?", "CERTAINTY",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        //if the user's response is yes
        if(response == 0){
            //declaration and partal ntialzation of  id and row
            int id = -1, row = -1;
            
            //when the user wanted is to edit rather than hire a principal
            if(decision.equals("edit")){
                row = table.getSelectedRow();
                id = (int) table.getModel().getValueAt(row, 0);
            }
            
            //Create a connection between program and mysql databasse
            MConnection mc = new MConnection();
            
            //Declaration and assignment of variables
            String fname = this.pfname.getText();
            String mname = pmname.getText();
            String lname = plname.getText();
            String username = pusername.getText();
            String password = ppassword.getText();
            String gender = "";
            String bdate = pbdate.getText();
            String mnumber = pmnumber.getText();
            String email = pemail.getText();
            String country = pcountry.getText();
            String region = pregion.getText();
            String province = pprovince.getText();
            String city = pcity.getText();
            String brgy = pbrgy.getText();
            String street = pstreet.getText();
            
            //assignment of gender with respect to the selected radio button
            if(pgmale.isSelected()) gender = "Male";
            else if(pgfemale.isSelected())  gender ="Female";
            
            //Declaration of the userflag               userflag test if the username is valid for creation or edting
            Boolean userFlag = false;
            
            //userFlag assignment with respect to the decision made by the user
            if(decision.equals("hire"))    userFlag = mc.usernameIsValid(username);
            else    userFlag = mc.usernameIsValid("principal", username, id);
            
            //Validation of the user input              if the user is valid
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
                
                //Declaration of prefix which is part of the name which not necessary to have
                String pre_fix;
                
                //assignment of the prefix name
                pre_fix = prefix.getText();
                
                //Avoiding the user value prefix e.g(jr.)
                if(pre_fix.equals("Prefix e.g(Jr.)")){
                    pre_fix = "";
                }
                
                //Finding the value of the age with respect to the birth date and date now as well as formatting the birth date
                LocalDate today = LocalDate.now();
                Date bdate2 = null;
                try {
                    bdate2 = new SimpleDateFormat("m/d/yy").parse(bdate);
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
                int agey = age.getYears();  //age of the person
                
                ///set image as byte                        if the path is not empty            it means that the user change the picture of the person
                if(!path.equals("")){
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
                        JOptionPane.showMessageDialog(new JFrame(), "Error!!! An error occured! Image not converted to byte", "Dialog", JOptionPane.ERROR_MESSAGE);
                    }
                }else if(decision.equals("edit")){          //if the path is empty or the picture is not edited while the intention of the user is just to edit the table
                    try {
                        
                        //Get the image from label variable named profile
                        Icon icons = profile.getIcon();
                        BufferedImage bi = new BufferedImage(icons.getIconWidth(), icons.getIconHeight(), BufferedImage.TYPE_INT_RGB);
                        Graphics g = bi.createGraphics();
                        icons.paintIcon(null, g, 0, 0);
                        g.setColor(Color.WHITE);
                        g.drawString(profile.getText(), 10, 20);
                        g.dispose();
                        
                        // convert image into byte array
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        ImageIO.write(bi, "jpg", os);
                        InputStream fis = new ByteArrayInputStream(os.toByteArray());
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024];
                        for (int readNum; (readNum = fis.read(buf)) != -1;) {
                            bos.write(buf, 0, readNum);
                            }
                         byte[] bytes = bos.toByteArray();
                         person_image = bytes;      //set the byte array person_image equals to bytes
                    } catch (IOException d) {
                        JOptionPane.showMessageDialog(rootPane, d);
                    }
                }
                
                //Access the function insertOrEditPrincipal from the MConnection            //there will be the insertion of the data will take place
                mc.insertOrEditPrincipal(decision, id, fname, mname, lname, pre_fix, agey, gender, birth_date+"", mnumber, email, address, username, password,  person_image);
            }else   {       //if any user input is invalid
                
                //if the username inputted is already taken                 there shall be no user with the same username to avoid duplication
                if(!userFlag)    JOptionPane.showMessageDialog(new JFrame(), "Error!!! Username is already taken", "Dialog", JOptionPane.ERROR_MESSAGE);
                
                //if the user input is invalid aside from the username
                else JOptionPane.showMessageDialog(new JFrame(), "Error!!! Invalid input", "Dialog", JOptionPane.ERROR_MESSAGE);
            }
            
            //update the table again
            updateTable();
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

        jScrollPane5 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        tablePanel = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        listTitle = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        deselect = new javax.swing.JButton();
        search = new javax.swing.JTextField();
        searchcombo = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        sprovince = new javax.swing.JTextField();
        scountry = new javax.swing.JTextField();
        sregion = new javax.swing.JTextField();
        scity = new javax.swing.JTextField();
        sbrgy = new javax.swing.JTextField();
        sname = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        smission = new javax.swing.JTextPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        svision = new javax.swing.JTextPane();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        scvalues = new javax.swing.JTextPane();
        senter = new javax.swing.JButton();
        sdestroy = new javax.swing.JButton();
        sedit = new javax.swing.JButton();
        sbuild = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        plname = new javax.swing.JTextField();
        pfname = new javax.swing.JTextField();
        pmname = new javax.swing.JTextField();
        prefix = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        pregion = new javax.swing.JTextField();
        pcountry = new javax.swing.JTextField();
        pprovince = new javax.swing.JTextField();
        pcity = new javax.swing.JTextField();
        pstreet = new javax.swing.JTextField();
        pbrgy = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        bdatelbl = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pgmale = new javax.swing.JRadioButton();
        pgfemale = new javax.swing.JRadioButton();
        pbdate = new datechooser.beans.DateChooserCombo();
        pmnumber = new javax.swing.JFormattedTextField();
        pemail = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        ppassword = new javax.swing.JTextField();
        pusername = new javax.swing.JTextField();
        pfire = new javax.swing.JButton();
        phire = new javax.swing.JButton();
        pedit = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        profile = new javax.swing.JLabel();
        updateProfile = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        statue = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel1.setBackground(new java.awt.Color(0, 255, 128));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablePanel.setBackground(new java.awt.Color(0, 255, 128));
        tablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "PRINCIPAL'S", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 2, 18))); // NOI18N

        table.setModel(new javax.swing.table.DefaultTableModel(
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
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane18.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(10);
            table.getColumnModel().getColumn(1).setPreferredWidth(300);
        }

        listTitle.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Principal", "School" }));
        listTitle.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                listTitleItemStateChanged(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
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
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tablePanelLayout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(listTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tablePanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tablePanelLayout.createSequentialGroup()
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tablePanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tablePanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(deselect, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(264, 264, 264)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchcombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tablePanelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(deselect))
                    .addComponent(jLabel11)
                    .addComponent(searchcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(listTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(tablePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 575, 807));

        jPanel2.setBackground(new java.awt.Color(0, 255, 128));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "SCHOOL'S INFORMATION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 2, 18))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(0, 255, 128));
        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(0, 255, 128));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Address"));

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

        scountry.setForeground(new java.awt.Color(153, 153, 153));
        scountry.setText("Country");
        scountry.setToolTipText("");
        scountry.setName(""); // NOI18N
        scountry.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                scountryFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                scountryFocusLost(evt);
            }
        });

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

        scity.setForeground(new java.awt.Color(153, 153, 153));
        scity.setText("City");
        scity.setToolTipText("");
        scity.setName(""); // NOI18N
        scity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                scityFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                scityFocusLost(evt);
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

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sprovince, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                    .addComponent(sregion)
                    .addComponent(scountry))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scity, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sbrgy, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sregion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sbrgy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sprovince, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 0, 343, -1));

        sname.setForeground(new java.awt.Color(153, 153, 153));
        sname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sname.setText("Name of School");
        sname.setToolTipText("");
        sname.setName(""); // NOI18N
        sname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                snameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                snameFocusLost(evt);
            }
        });
        jPanel7.add(sname, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 240, 32));

        jPanel6.setBackground(new java.awt.Color(0, 255, 128));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Mission"));

        jScrollPane1.setViewportView(smission);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 49, 240, -1));

        jPanel8.setBackground(new java.awt.Color(0, 255, 128));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Vision"));

        jScrollPane2.setViewportView(svision);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 112, 343, 132));

        jPanel14.setBackground(new java.awt.Color(0, 255, 128));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Core values"));

        jScrollPane3.setViewportView(scvalues);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        jPanel7.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 249, 240, 166));

        senter.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        senter.setForeground(new java.awt.Color(50, 205, 50));
        senter.setText("ENTER SCHOOL");
        senter.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        senter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                senterMouseClicked(evt);
            }
        });
        jPanel7.add(senter, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 262, 343, 95));

        sdestroy.setText("DESTROY");
        sdestroy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sdestroyMouseClicked(evt);
            }
        });
        jPanel7.add(sdestroy, new org.netbeans.lib.awtextra.AbsoluteConstraints(517, 400, 100, 14));

        sedit.setText("EDIT");
        sedit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seditMouseClicked(evt);
            }
        });
        jPanel7.add(sedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 400, 100, 15));

        sbuild.setText("BUILD");
        sbuild.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sbuildMouseClicked(evt);
            }
        });
        jPanel7.add(sbuild, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 366, 343, -1));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 28, -1, 430));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(591, 15, 657, 479));

        jPanel3.setBackground(new java.awt.Color(0, 255, 128));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "PRINCIPAL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 2, 18))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(0, 255, 128));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Personal information", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 18))); // NOI18N
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(0, 255, 128));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Name"));

        plname.setForeground(new java.awt.Color(153, 153, 153));
        plname.setText("Last name");
        plname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                plnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                plnameFocusLost(evt);
            }
        });

        pfname.setForeground(new java.awt.Color(153, 153, 153));
        pfname.setText("First name");
        pfname.setToolTipText("");
        pfname.setName(""); // NOI18N
        pfname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pfnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pfnameFocusLost(evt);
            }
        });

        pmname.setForeground(new java.awt.Color(153, 153, 153));
        pmname.setText("Middle name");
        pmname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pmnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pmnameFocusLost(evt);
            }
        });

        prefix.setForeground(new java.awt.Color(153, 153, 153));
        prefix.setText("Prefix e.g(Jr.)");
        prefix.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                prefixFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                prefixFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pfname)
                    .addComponent(pmname)
                    .addComponent(plname)
                    .addComponent(prefix, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(pfname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pmname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(prefix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel11.setBackground(new java.awt.Color(0, 255, 128));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Address"));

        pregion.setForeground(new java.awt.Color(153, 153, 153));
        pregion.setText("Region");
        pregion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pregionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pregionFocusLost(evt);
            }
        });

        pcountry.setForeground(new java.awt.Color(153, 153, 153));
        pcountry.setText("Country");
        pcountry.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pcountryFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pcountryFocusLost(evt);
            }
        });

        pprovince.setForeground(new java.awt.Color(153, 153, 153));
        pprovince.setText("Province");
        pprovince.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pprovinceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pprovinceFocusLost(evt);
            }
        });

        pcity.setForeground(new java.awt.Color(153, 153, 153));
        pcity.setText("City");
        pcity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pcityFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pcityFocusLost(evt);
            }
        });

        pstreet.setForeground(new java.awt.Color(153, 153, 153));
        pstreet.setText("Sitio / Blk & lot / Street");
        pstreet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pstreetFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pstreetFocusLost(evt);
            }
        });

        pbrgy.setForeground(new java.awt.Color(153, 153, 153));
        pbrgy.setText("Barangay");
        pbrgy.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pbrgyFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pbrgyFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pbrgy, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pcity, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pprovince, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pregion, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pcountry, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pstreet, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pcountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pregion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pprovince, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pcity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbrgy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pstreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(0, 255, 128));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Other information"));

        bdatelbl.setText("Birth date:");

        jLabel2.setText("Gender:");

        pgmale.setText("Male");

        pgfemale.setText("Female");

        try {
            pbdate.setDefaultPeriods(new datechooser.model.multiple.PeriodSet());
        } catch (datechooser.model.exeptions.IncompatibleDataExeption e1) {
            e1.printStackTrace();
        }

        pmnumber.setForeground(new java.awt.Color(153, 153, 153));
        pmnumber.setText("09123456789");
        pmnumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pmnumberFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pmnumberFocusLost(evt);
            }
        });
        pmnumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pmnumberKeyTyped(evt);
            }
        });

        pemail.setForeground(new java.awt.Color(153, 153, 153));
        pemail.setText("example@gmail.com");
        pemail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pemailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pemailFocusLost(evt);
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
                        .addComponent(pgmale)
                        .addGap(11, 11, 11)
                        .addComponent(pgfemale))
                    .addComponent(pbdate, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pemail, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(pmnumber)))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bdatelbl)
                    .addComponent(pbdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(pmnumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(pgmale)
                    .addComponent(pgfemale)
                    .addComponent(pemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(0, 255, 128));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Account"));

        ppassword.setForeground(new java.awt.Color(153, 153, 153));
        ppassword.setText("Password");
        ppassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pppasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pppasswordFocusLost(evt);
            }
        });

        pusername.setForeground(new java.awt.Color(153, 153, 153));
        pusername.setText("Username");
        pusername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ppusernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ppusernameFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pusername, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(ppassword))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(pusername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ppassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 39, 648, -1));

        pfire.setText("FIRE");
        pfire.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pfireMouseClicked(evt);
            }
        });
        jPanel3.add(pfire, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 280, -1, -1));

        phire.setText("HIRE");
        phire.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                phireMouseClicked(evt);
            }
        });
        jPanel3.add(phire, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 250, 219, 18));

        pedit.setText("EDIT");
        pedit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                peditMouseClicked(evt);
            }
        });
        jPanel3.add(pedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 280, -1, -1));

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

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(674, 28, 219, -1));

        updateProfile.setText("Upload profile picture");
        updateProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateProfileMouseClicked(evt);
            }
        });
        jPanel3.add(updateProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(674, 225, 219, 12));

        jLabel1.setText("VISAYAS STATE UNIVERSITY");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 290, -1, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(591, 508, 909, -1));
        jPanel1.add(statue, new org.netbeans.lib.awtextra.AbsoluteConstraints(1252, 11, 248, 483));

        jScrollPane4.setViewportView(jPanel1);

        jScrollPane5.setViewportView(jScrollPane4);

        jMenuBar1.setBackground(new java.awt.Color(0, 255, 128));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jMenu1.setBackground(new java.awt.Color(0, 255, 128));
        jMenu1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jMenu1.setText("HOME ");
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
     
    
                                       /*          Focuse gained and lost functions for the jtextfield's            */
    private void pemailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pemailFocusLost
        if(pemail.getText().trim().equals("")){
            pemail.setText("example@gmail.com");
            pemail.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_pemailFocusLost

    private void pemailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pemailFocusGained
        if(pemail.getText().trim().equals("example@gmail.com"))
        pemail.setText("");
        pemail.setForeground(Color.BLACK);
    }//GEN-LAST:event_pemailFocusGained

    private void pmnumberFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pmnumberFocusLost
        if(pmnumber.getText().trim().equals("")){
            pmnumber.setText("09123456789");
            pmnumber.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_pmnumberFocusLost

    private void pmnumberFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pmnumberFocusGained
        if(pmnumber.getText().trim().equals("09123456789"))
        pmnumber.setText("");
        pmnumber.setForeground(Color.BLACK);
    }//GEN-LAST:event_pmnumberFocusGained

    private void pbrgyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pbrgyFocusLost
        if(pbrgy.getText().trim().equals("")){
            pbrgy.setText("Barangay");
            pbrgy.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_pbrgyFocusLost

    private void pbrgyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pbrgyFocusGained
        if(pbrgy.getText().trim().equals("Barangay"))
        pbrgy.setText("");
        pbrgy.setForeground(Color.BLACK);
    }//GEN-LAST:event_pbrgyFocusGained

    private void pstreetFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pstreetFocusLost
        if(pstreet.getText().trim().equals("")){
            pstreet.setText("Sitio / Blk & lot / Street");
            pstreet.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_pstreetFocusLost

    private void pstreetFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pstreetFocusGained
        if(pstreet.getText().trim().equals("Sitio / Blk & lot / Street"))
        pstreet.setText("");
        pstreet.setForeground(Color.BLACK);
    }//GEN-LAST:event_pstreetFocusGained

    private void pcityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pcityFocusLost
        if(pcity.getText().trim().equals("")){
            pcity.setText("City");
            pcity.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_pcityFocusLost

    private void pcityFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pcityFocusGained
        if(pcity.getText().trim().equals("City"))
        pcity.setText("");
        pcity.setForeground(Color.BLACK);
    }//GEN-LAST:event_pcityFocusGained

    private void pprovinceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pprovinceFocusLost
        if(pprovince.getText().trim().equals("")){
            pprovince.setText("Province");
            pprovince.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_pprovinceFocusLost

    private void pprovinceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pprovinceFocusGained
        if(pprovince.getText().trim().equals("Province"))
        pprovince.setText("");
        pprovince.setForeground(Color.BLACK);
    }//GEN-LAST:event_pprovinceFocusGained

    private void pcountryFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pcountryFocusLost
        if(pcountry.getText().trim().equals("")){
            pcountry.setText("Country");
            pcountry.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_pcountryFocusLost

    private void pcountryFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pcountryFocusGained
        if(pcountry.getText().trim().equals("Country"))
        pcountry.setText("");
        pcountry.setForeground(Color.BLACK);
    }//GEN-LAST:event_pcountryFocusGained

    private void pregionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pregionFocusLost
        if(pregion.getText().trim().equals("")){
            pregion.setText("Region");
            pregion.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_pregionFocusLost

    private void pregionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pregionFocusGained
        if(pregion.getText().trim().equals("Region"))
        pregion.setText("");
        pregion.setForeground(Color.BLACK);
    }//GEN-LAST:event_pregionFocusGained

    private void pmnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pmnameFocusLost
        if(pmname.getText().trim().equals("")){
            pmname.setText("Middle name");
            pmname.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_pmnameFocusLost

    private void pmnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pmnameFocusGained
        if(pmname.getText().trim().equals("Middle name"))
        pmname.setText("");
        pmname.setForeground(Color.BLACK);
    }//GEN-LAST:event_pmnameFocusGained

    private void pfnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pfnameFocusLost
        if(pfname.getText().trim().equals("")){
            pfname.setText("First name");
            pfname.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_pfnameFocusLost

    private void pfnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pfnameFocusGained
        if(pfname.getText().trim().equals("First name"))
        pfname.setText("");
        pfname.setForeground(Color.BLACK);
    }//GEN-LAST:event_pfnameFocusGained

    private void plnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_plnameFocusLost
        if(plname.getText().trim().equals("")){
            plname.setText("Last name");
            plname.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_plnameFocusLost

    private void plnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_plnameFocusGained
        if(plname.getText().trim().equals("Last name"))
        plname.setText("");
        plname.setForeground(Color.BLACK);
    }//GEN-LAST:event_plnameFocusGained

    private void snameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_snameFocusLost
        if(sname.getText().trim().equals("")){
            sname.setText("Name of School");
            sname.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_snameFocusLost

    private void snameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_snameFocusGained
        if(sname.getText().trim().equals("Name of School"))
        sname.setText("");
        sname.setForeground(Color.BLACK);
    }//GEN-LAST:event_snameFocusGained

    private void sbrgyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sbrgyFocusLost
        if(sbrgy.getText().trim().equals("")){
            sbrgy.setText("Barangay");
            sbrgy.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_sbrgyFocusLost

    private void sbrgyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sbrgyFocusGained
        if(sbrgy.getText().trim().equals("Barangay"))
        sbrgy.setText("");
        sbrgy.setForeground(Color.BLACK);
    }//GEN-LAST:event_sbrgyFocusGained

    private void scityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_scityFocusLost
        if(scity.getText().trim().equals("")){
            scity.setText("City");
            scity.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_scityFocusLost

    private void scityFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_scityFocusGained
        if(scity.getText().trim().equals("City"))
        scity.setText("");
        scity.setForeground(Color.BLACK);
    }//GEN-LAST:event_scityFocusGained

    private void sregionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sregionFocusLost
        if(sregion.getText().trim().equals("")){
            sregion.setText("Region");
            sregion.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_sregionFocusLost

    private void sregionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sregionFocusGained
        if(sregion.getText().trim().equals("Region"))
        sregion.setText("");
        sregion.setForeground(Color.BLACK);
    }//GEN-LAST:event_sregionFocusGained

    private void scountryFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_scountryFocusLost
        if(scountry.getText().trim().equals("")){
            scountry.setText("Country");
            scountry.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_scountryFocusLost

    private void scountryFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_scountryFocusGained
        if(scountry.getText().trim().equals("Country"))
        scountry.setText("");
        scountry.setForeground(Color.BLACK);
    }//GEN-LAST:event_scountryFocusGained

    private void sprovinceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sprovinceFocusLost
        if(sprovince.getText().trim().equals("")){
            sprovince.setText("Province");
            sprovince.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_sprovinceFocusLost

    private void sprovinceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sprovinceFocusGained
        if(sprovince.getText().trim().equals("Province"))
        sprovince.setText("");
        sprovince.setForeground(Color.BLACK);
    }//GEN-LAST:event_sprovinceFocusGained

    
    //If the button to hire is clicked
    private void phireMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phireMouseClicked
        hireOreditPrincipal("hire");
    }//GEN-LAST:event_phireMouseClicked
    
    //If the button to edit is clicked
    private void peditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_peditMouseClicked
        hireOreditPrincipal("edit");
    }//GEN-LAST:event_peditMouseClicked

    //if the button to fire the principal is clicked
    private void pfireMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pfireMouseClicked
        //Asking for the certainty of the user's decision
        String[] options = new String[] {"Yes", "No"};
        int response = JOptionPane.showOptionDialog(null, "By doing so, all the record under this principal will be lost forever", "Are you sure?",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        
        //If yes then the table will be lost forever as well as the table under it
        if(response == 0){
            int id, row;

            row = table.getSelectedRow();
            id = (int) table.getModel().getValueAt(row, 0);

            User.deleteTable("principal", "principal", id, "");
            deselect.doClick();
            updateTable();
        }
    }//GEN-LAST:event_pfireMouseClicked

    
    //Whenever the user will change the item selected in listTitle      it will come here
    private void listTitleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_listTitleItemStateChanged
        deselect.doClick();
        updateTable();
    }//GEN-LAST:event_listTitleItemStateChanged

    //If the user clicked the button below the image to update the image above or the picture of the principal
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
                                /*        Continuation of the jtextfield's focus gained and lost          */
    private void ppusernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ppusernameFocusLost
        if(pusername.getText().trim().equals("")){
            pusername.setText("Username");
            pusername.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_ppusernameFocusLost

    private void ppusernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ppusernameFocusGained
        if(pusername.getText().trim().equals("Username"))
        pusername.setText("");
        pusername.setForeground(Color.BLACK);
    }//GEN-LAST:event_ppusernameFocusGained

    private void pppasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pppasswordFocusLost
        if(ppassword.getText().trim().equals("")){
            ppassword.setText("Password");
            ppassword.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_pppasswordFocusLost

    private void pppasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pppasswordFocusGained
        if(ppassword.getText().trim().equals("Password"))
        ppassword.setText("");
        ppassword.setForeground(Color.BLACK);
    }//GEN-LAST:event_pppasswordFocusGained

    
    //Whenever the user click the table             or choose any principal or school in the table
    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        //First set the foreground to black
        setColorPrincipal(0, 0, 0);

        //button's visibility
        pfire.setVisible(true);
        pedit.setVisible(true);
        phire.setVisible(false);
        
        //Declaration of id and row
        int id, row;
    
        //assignment of row and id of the selected item
        row = table.getSelectedRow();
        id = (int) table.getModel().getValueAt(row, 0);
        
        try{
            
            //Declaration and assignment of  table name which is selected by the user
            String table_name = listTitle.getSelectedItem().toString().toLowerCase();
            
            //setting the border of the table panel
            tablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (table_name + "'s").toUpperCase(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 2, 18), new java.awt.Color(255, 255, 255)));
            
            //Start a connection between the program and mysql database         Declaration and assignment/initialization of the variables
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            String QUERY = "";
            
            //Choosing the apprpriate query with respect to the table name selected from the combo box listtitle
            if(table_name.equals("principal"))
                QUERY = "SELECT * FROM principal WHERE id = ?";
            else    QUERY = "SELECT * FROM principal INNER JOIN school on principal.id = school.principal_id WHERE school.id = ?"; 
            
            //Declaration and assignment of prepared statement using the query then set the parameter id            as well as executing the query and getting the result set
            PreparedStatement pst = conn.prepareStatement(QUERY);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            //show the information of the selected item in the table            both principal and school
            if(rs.next()){
                
                //assignment of the principal id        with the id of the selected principal
                User.principal_id = rs.getInt("id");
                
                //showing the information of the selected item
                pfname.setText(rs.getString("first_name"));
                pmname.setText(rs.getString("middle_name"));
                plname.setText(rs.getString("last_name"));
                prefix.setText(rs.getString("prefix"));
                String d = rs.getString("bdate");
                try {
                    Date date = new SimpleDateFormat("yyyy-mm-dd").parse(d);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    pbdate.setSelectedDate(c);
                } catch (ParseException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(rs.getString("gender").equals("Male")){
                    pgmale.setSelected(true);
                    pgfemale.setSelected(false);
                }
                else{
                    pgmale.setSelected(false);
                    pgfemale.setSelected(true);
                }
                pmnumber.setText(rs.getString("mobile_no"));
                pemail.setText(rs.getString("email"));
                
                //Spliting the addresses taken from the mysql database
                String add = rs.getString("address");
                String split[] = add.split(",");
                int ctr = 0;
                for(String s: split){
                    switch(ctr){
                        case 0:
                            pstreet.setText(s);
                        case 1:
                            pbrgy.setText(s);
                        case 2:
                            pcity.setText(s);
                        case 3:
                            pprovince.setText(s);
                        case 4:
                            pregion.setText(s);
                        case 5:
                            pcountry.setText(s);
                        default:;
                    }
                    ctr++;
                }
                
                pusername.setText(rs.getString("username"));
                ppassword.setText(rs.getString("password"));
                
                person_image = rs.getBytes("picture");
                
                ImageIcon icon = new ImageIcon(person_image);
                Image image = icon.getImage().getScaledInstance(profile.getWidth(), profile.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon i = new ImageIcon(image);
                profile.setIcon(i);
            }
            
            
            //Get the respective school under the principal selected
            String QUERY2  = "SELECT * FROM school WHERE school.principal_id = ?"; 
            PreparedStatement pst2 = conn.prepareStatement(QUERY2);
            pst2.setInt(1, User.principal_id);
            ResultSet rs2 = pst2.executeQuery();
            if(rs2.next()){
                User.school_id = rs2.getInt("id");
                
                sbuild.setVisible(false);
                senter.setVisible(true);
                sedit.setVisible(true);
                sdestroy.setVisible(true);
                setColorSchool(0, 0, 0);
                sname.setText(rs2.getString("name"));
                smission.setText(rs2.getString("mission"));
                svision.setText(rs2.getString("vision"));
                scvalues.setText(rs2.getString("core_values"));
                String address = rs2.getString("address");
                
                //showing the appropriate address taken from the mysql database
                String split[] = address.split(",");
                int ctr2 = 0;
                for(String s: split){
                    switch(ctr2){
                        case 0:
                            pbrgy.setText(s);
                        case 1:
                            scity.setText(s);
                        case 2:
                            sprovince.setText(s);
                        case 3:
                            sregion.setText(s);
                        case 4:
                            scountry.setText(s);
                        default:;
                    }
                    //counter's incrementation
                    ctr2++;
                }
            }
            else{
                
                //Clear the school's panel as well as setting the button's visibility
                clearSchool();
                sbuild.setVisible(true);
                senter.setVisible(false);
                sedit.setVisible(false);
                sdestroy.setVisible(false);
            }
            
            //Close the result set and connection to the database
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tableMouseClicked
                            /*      Continuation of the focus gained and loss           */
    private void prefixFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_prefixFocusGained
        if(prefix.getText().trim().equals("Prefix e.g(Jr.)"))
        prefix.setText("");
        prefix.setForeground(Color.BLACK);
    }//GEN-LAST:event_prefixFocusGained

    private void prefixFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_prefixFocusLost
        if(prefix.getText().trim().equals("")){
            prefix.setText("Prefix e.g(Jr.)");
            prefix.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_prefixFocusLost

    
    //Clicked the deselect button to deselect the selected item from the table
    private void deselectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deselectActionPerformed
        pfire.setVisible(false);
        pedit.setVisible(false);
        phire.setVisible(true);
        sbuild.setVisible(false);
        senter.setVisible(false);
        sedit.setVisible(false);
        sdestroy.setVisible(false);
        table.clearSelection();
        clearPrincipal();
        clearSchool();
    }//GEN-LAST:event_deselectActionPerformed

    //Clicked the build button to build a school
    private void sbuildMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sbuildMouseClicked
        buildOrEditSchool("build");
    }//GEN-LAST:event_sbuildMouseClicked
    
    //Clicked the enter button to enter the school selected
    private void senterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_senterMouseClicked
        this.setVisible(false);
        new School_teacher(User.principal_id).setVisible(true);
    }//GEN-LAST:event_senterMouseClicked
    
    //Clicked edit button to edit the school
    private void seditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seditMouseClicked
        buildOrEditSchool("edit");
    }//GEN-LAST:event_seditMouseClicked

    private void sdestroyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sdestroyMouseClicked
        String[] options = new String[] {"Yes", "No"};
        int response = JOptionPane.showOptionDialog(null, "By doing so, all the record under this school will be lost forever", "Are you sure?",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        if(response == 0){
            try {
                int id, row;
                row = table.getSelectedRow();
                id = (int) table.getModel().getValueAt(row, 0);
                
                
                MConnection mc = new MConnection();
                Connection conn = mc.getConn();
                String QUERY2 = "SELECT * FROM school WHERE school.principal_id = ?";
                PreparedStatement pst2 = conn.prepareStatement(QUERY2);
                pst2.setInt(1, id);
                ResultSet rs2 = pst2.executeQuery();
                if(rs2.next()){
                    User.deleteTable("school", "school", rs2.getInt("id"), "");
                }   
                deselect.doClick();
                updateTable();
            } catch (SQLException ex) {
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_sdestroyMouseClicked

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

    //When typing in the search textfield       it will automatically search using this function
    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
        //if the search jtextfield  is not empty
        if(!search.getText().equals("")){
            
            //Declare the possible queries of what to search for
            String queryprincipalid = "SELECT * FROM principal WHERE id LIKE ?";
            String queryprincipalname = "SELECT * FROM principal WHERE first_name LIKE ? or middle_name LIKE ? or last_name LIKE ? or prefix LIKE ?";                             
            String queryschoolid = "SELECT * FROM school WHERE id LIKE ?";
            String queryschoolname = "SELECT * FROM school WHERE name LIKE ?";
            
            //Initial declaration and initialization of the Query
            String Query = "";
            
            //Query assignment ofthe appropriate query with respect to the selected item in listTitle and searchcombo combo box
            try{
                String table_name = listTitle.getSelectedItem().toString().toLowerCase();
                String search_name = searchcombo.getSelectedItem().toString().toLowerCase();

                switch(table_name){
                    case "principal" -> {
                        switch(search_name){
                            case "id" -> {
                                Query = queryprincipalid;
                            }
                            case "name" -> {
                                Query = queryprincipalname;
                            }

                        }
                    }
                    case "school" -> {
                        switch(search_name){
                            case "id" -> {
                                Query = queryschoolid;
                            }
                            case "name" -> {
                                Query = queryschoolname;
                            }
                        }
                    }
                }

                //Start the connection between the program and mysql database
                MConnection mc = new MConnection();
                Statement stmt = mc.getStmt();
                Connection conn = mc.getConn();
                PreparedStatement pst = conn.prepareStatement(Query);
                
                //Declaration and assignment as to what to find
                String tofind = "%"+search.getText()+"%";
                
                //Assigning the value of the parameter in query
                pst.setString(1, tofind);
                if(table_name.equals("principal") && search_name.equals("name")){
                    pst.setString(2, tofind);
                    pst.setString(3, tofind);
                    pst.setString(4, tofind);
                }
                
                //Get the results
                ResultSet rs = pst.executeQuery();
                
                //Remove all rows in the table
                DefaultTableModel model = (DefaultTableModel)table.getModel();
                int rowCount = model.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    model.removeRow(i);
                }
                
                
                /// display the result of the query, specifically the id and name
                while(rs.next()){
                    int SelectedId = rs.getInt("ID");
                    String name = "";
                    if(table_name.equals("principal"))
                        name = rs.getString("first_name") +" "+ rs.getString("middle_name") +" "+ rs.getString("last_name") +" "+ rs.getString("prefix");
                    else name = rs.getString("name");
                    model.addRow(new Object[]{SelectedId, name});
                }
            } catch (SQLException ex) {
                System.out.print(ex);
            }
        }else updateTable();    //if the search jtext field is empty then just update the table
        
        //Clear the principal, school and clear the selection in the table regardless of the search jtextfield's content
        clearPrincipal();
        clearSchool();
        table.clearSelection();
    }//GEN-LAST:event_searchKeyReleased

    //Ensure that the pmnumber jtextfield's could only accept an mobile number starting from 09*********
    private void pmnumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pmnumberKeyTyped
        char c = evt.getKeyChar();
        String text = pmnumber.getText();
        if ((!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) ||  text.length() >= 11) {
             evt.consume();
        }
        else if(Character.isDigit(c)){
            if(Integer.valueOf(c+"") != 0 && text.isEmpty())
                evt.consume();
            else if(Integer.valueOf(c+"") != 9 && text.length() == 1)
                evt.consume();
        } 
    }//GEN-LAST:event_pmnumberKeyTyped

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
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JLabel bdatelbl;
    private javax.swing.JButton deselect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JComboBox<String> listTitle;
    private datechooser.beans.DateChooserCombo pbdate;
    private javax.swing.JTextField pbrgy;
    private javax.swing.JTextField pcity;
    private javax.swing.JTextField pcountry;
    private javax.swing.JButton pedit;
    private javax.swing.JFormattedTextField pemail;
    private javax.swing.JButton pfire;
    private javax.swing.JTextField pfname;
    private javax.swing.JRadioButton pgfemale;
    private javax.swing.JRadioButton pgmale;
    private javax.swing.JButton phire;
    private javax.swing.JTextField plname;
    private javax.swing.JTextField pmname;
    private javax.swing.JFormattedTextField pmnumber;
    private javax.swing.JTextField ppassword;
    private javax.swing.JTextField pprovince;
    private javax.swing.JTextField prefix;
    private javax.swing.JTextField pregion;
    private javax.swing.JLabel profile;
    private javax.swing.JTextField pstreet;
    private javax.swing.JTextField pusername;
    private javax.swing.JTextField sbrgy;
    private javax.swing.JButton sbuild;
    private javax.swing.JTextField scity;
    private javax.swing.JTextField scountry;
    private javax.swing.JTextPane scvalues;
    private javax.swing.JButton sdestroy;
    private javax.swing.JTextField search;
    private javax.swing.JComboBox<String> searchcombo;
    private javax.swing.JButton sedit;
    private javax.swing.JButton senter;
    private javax.swing.JTextPane smission;
    private javax.swing.JTextField sname;
    private javax.swing.JTextField sprovince;
    private javax.swing.JTextField sregion;
    private javax.swing.JLabel statue;
    private javax.swing.JTextPane svision;
    private javax.swing.JTable table;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JButton updateProfile;
    // End of variables declaration//GEN-END:variables
}
