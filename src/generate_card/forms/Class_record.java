package generate_card.forms;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import generate_card.classes.MConnection;
import generate_card.classes.User;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.sql.*;  
import java.time.LocalDate;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Table;
/**
 *
 * @author Marvin
 */
public class Class_record extends javax.swing.JFrame {
    //Declaration and initialization of the class id
    int class_id = 0;
    int curriculum_year_integer = 0;
    //Declaration and initialization of the grades
    Float firstquarter = (float) 0, secondquarter = (float) 0, thirdquarter = (float) 0, fourthquarter = (float) 0;
    
    /**
     * Creates new form Class_record
     * @param class_id
     */
    public Class_record(int class_id) {
        initComponents();
        
        ImageIcon logo = new javax.swing.ImageIcon(getClass().getResource("/generate_card/images_or_icon/projectIcon.jpg"));
        setIconImage(logo.getImage());
        //Full screen the jframe
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
                        /*          Setting the visibility of the components        */
        if(User.userType.equals("principal") || User.userType.equals("staff")){
            menu1.setVisible(false);
            menu2.setText("HOME");
        }else if(User.userType.equals("teacher")){
            menu1.setVisible(false);
            menu2.setVisible(false);
            menu3.setText("HOME");
        }
        //class id assignment with the parameter in constructor
        this.class_id = class_id;
        
        //Setting the border of the panel 2
        tablePanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Class ID : "+class_id + "   Subject: " + User.getName1("subject", User.getTableId("subject", class_id)), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial Black", 1, 18))); // NOI18N
        
        //Centralizing the content of the tables
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        for(int i = 0; i < table2.getColumnCount(); i++)
            table2.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        
        //Get the years of student to be accepted which is curriculum year of the student and subject
        try{
            //Start connection between the program and mysql database
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            
            //Assign query statement
            String QUERY = "SELECT * FROM subject WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(QUERY);
            
            //Set value for the parameter in query
            pst.setInt(1, User.getTableId("subject", this.class_id));
            rs = pst.executeQuery();
            
            
            if(rs.next()){
                this.curriculum_year_integer = rs.getInt("Curriculum_Year");
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        //update the frame
        update();
    }
    
    //Function to update the frame
    private void update(){
        
        //Set the visibility of the editing panel into false
        editingpanel.setVisible(false);
        
        //search jtextfield edit text and color just like when it gained focus
        search.setText("TYPE WHOM YOU WANTED TO SEARCH");
        search.setForeground(new Color(153,153,153));
        
        //remove all rows in table2             this table consist the list of students enrolled in the same school as the teacher holding this class and the subject being tackled
        DefaultTableModel model1 = (DefaultTableModel)table2.getModel();
        int rowCount1 = model1.getRowCount();
        for (int i = rowCount1 - 1; i >= 0; i--) {
            model1.removeRow(i);
        }
        try{
            //Start the connection between the program and mysql database
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            
            //Assign query to get all the grades affilated with a certain class
            String QUERY = "SELECT * FROM grades WHERE class_id = ?";
            PreparedStatement pst = conn.prepareStatement(QUERY);
            
            //Set value for the parameters of the query
            pst.setInt(1, this.class_id);
            rs = pst.executeQuery();
            
            //remove all rows in table          // this table consist of the class record consisting student with their respective grades
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            
            //loop while theres a result
            while(rs.next()){
                
                //set the lrn
                String lrn = rs.getString("lrn");
                String studentName = "";
                
                //Start another connection between the program ang mysql database
                MConnection mc1 = new MConnection();
                Connection conn1 = mc1.getConn();
                
                //Assign a query into query1 variable
                String QUERY1 = "SELECT * FROM student WHERE lrn = ?";
                PreparedStatement pst1 = conn1.prepareStatement(QUERY1);
                
                //Set value for the parameter in the query
                pst1.setString(1, rs.getString("lrn"));
                
                //Execute the query1
                ResultSet rs1 = pst1.executeQuery();
                
                //Get the name of the student with respect to the lrn
                if(rs1.next()){
                    studentName = rs1.getString("Last_name").toUpperCase() + " " + rs1.getString("prefix") + ", " + rs1.getString("first_name").toUpperCase() + " " + rs1.getString("middle_name").toUpperCase().charAt(0) + ".";
                }
                
                //Assing query into query1 variable again but this time, to get the grades affiliated with the student before
                QUERY1 = "SELECT * FROM grades WHERE class_id = ? and lrn = ?";
                
                //prepared statement preparation
                pst1 = conn1.prepareStatement(QUERY1);
                
                //Set the value of the parameter in query
                pst1.setInt(1, this.class_id);
                pst1.setString(2, rs.getString("lrn"));
                rs1 = pst1.executeQuery();
                
                //Declaration and initialization of the grades and action taken
                float firstq = 0, secondq = 0, thirdq = 0, fourthq = 0, ave = 0;
                String at = "Pending";
                
                //Get the grades of the student in this class
                if(rs1.next()){
                    firstq = rs1.getFloat("first_quarter");
                    secondq = rs1.getFloat("Second_quarter");
                    thirdq = rs1.getFloat("Third_quarter");
                    fourthq = rs1.getFloat("Fourth_quarter");
                    ave = rs1.getFloat("Commulative_Final_Rating");
                    at = rs1.getString("Action_Taken");
                }
                
                //Display things to the table
                model.addRow(new Object[]{lrn, studentName, firstq, secondq, thirdq, fourthq, ave, at});
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            //Start connection between the program and mysql database
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            
            //Assign query statement
            String QUERY = "SELECT * FROM student INNER JOIN teacher on student.adviser_id = teacher.id INNER JOIN school on teacher.school_id = school.id WHERE school.principal_id = ? and year = ?";
            PreparedStatement pst = conn.prepareStatement(QUERY);
            
            //Set value for the parameter in query
            pst.setInt(1, User.principal_id);
            pst.setInt(2, this.curriculum_year_integer);
            rs = pst.executeQuery();
            
            //Erase all the row in table2
            DefaultTableModel model = (DefaultTableModel)table2.getModel();
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            
            //Display the resulted student's lrn and name into table 2
            while(rs.next()){
                String lrn = rs.getString("lrn");
                String studentName = rs.getString("Last_name").toUpperCase() + " " + rs.getString("prefix") + ", " + rs.getString("first_name").toUpperCase() + " " + rs.getString("middle_name").toUpperCase().charAt(0) + ".";
                model.addRow(new Object[]{lrn, studentName});
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        tablePanel2 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        deselect2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        search = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        searchcombo = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        editingpanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        fq = new javax.swing.JTextField();
        tq = new javax.swing.JTextField();
        sq = new javax.swing.JTextField();
        foq = new javax.swing.JTextField();
        confirmEditing = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menu1 = new javax.swing.JMenu();
        menu2 = new javax.swing.JMenu();
        subteacher1 = new javax.swing.JMenuItem();
        subsubject1 = new javax.swing.JMenuItem();
        subclass1 = new javax.swing.JMenuItem();
        substudent1 = new javax.swing.JMenuItem();
        menu3 = new javax.swing.JMenu();
        menu4 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 255, 128));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        tablePanel2.setBackground(new java.awt.Color(0, 255, 128));
        tablePanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Class ID : ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial Black", 1, 18))); // NOI18N
        tablePanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "LRN", "Name", "First Quarter", "Second Quarter", "Third Quarter", "Fourth Quarter", "Commulative final rating", "Action taken"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setShowVerticalLines(false);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane18.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(0).setPreferredWidth(50);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(2).setPreferredWidth(75);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(3).setPreferredWidth(75);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(4).setPreferredWidth(75);
            table.getColumnModel().getColumn(5).setResizable(false);
            table.getColumnModel().getColumn(5).setPreferredWidth(75);
            table.getColumnModel().getColumn(6).setResizable(false);
            table.getColumnModel().getColumn(6).setPreferredWidth(100);
            table.getColumnModel().getColumn(7).setResizable(false);
            table.getColumnModel().getColumn(7).setPreferredWidth(100);
        }

        tablePanel2.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 76, 940, 680));

        deselect2.setText("de-select");
        deselect2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deselect2ActionPerformed(evt);
            }
        });
        tablePanel2.add(deselect2, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 35, 169, 35));

        jPanel2.setBackground(new java.awt.Color(0, 255, 128));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Enroll student", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 18))); // NOI18N

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

        table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "LRN", "Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
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
        table2.setShowVerticalLines(false);
        jScrollPane1.setViewportView(table2);
        if (table2.getColumnModel().getColumnCount() > 0) {
            table2.getColumnModel().getColumn(0).setResizable(false);
            table2.getColumnModel().getColumn(0).setPreferredWidth(50);
            table2.getColumnModel().getColumn(1).setResizable(false);
            table2.getColumnModel().getColumn(1).setPreferredWidth(150);
        }

        jButton1.setText("Enroll Student");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        searchcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LRN", "NAME", "SECTION" }));
        searchcombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                searchcomboItemStateChanged(evt);
            }
        });

        jLabel1.setText("Search by: ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(search)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(42, 42, 42)
                        .addComponent(searchcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(jButton1)
                .addContainerGap(173, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(8, 8, 8)
                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel2.setText("VISAYAS STATE UNIVERSITY");

        editingpanel.setBackground(new java.awt.Color(0, 255, 128));
        editingpanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Edit grades", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 14))); // NOI18N

        fq.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "First Quarter"));
        fq.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fqFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                fqFocusLost(evt);
            }
        });
        fq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fqKeyTyped(evt);
            }
        });

        tq.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Third Quarter"));
        tq.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tqFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tqFocusLost(evt);
            }
        });
        tq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tqKeyTyped(evt);
            }
        });

        sq.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Second Quarter"));
        sq.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sqFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sqFocusLost(evt);
            }
        });
        sq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sqKeyTyped(evt);
            }
        });

        foq.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Fourth Quarter"));
        foq.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                foqFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                foqFocusLost(evt);
            }
        });
        foq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                foqKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fq, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(tq)
                    .addComponent(sq, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(foq))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fq, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sq, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tq, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(foq, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        confirmEditing.setText("EDIT");
        confirmEditing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmEditingActionPerformed(evt);
            }
        });

        jButton2.setText("DROP");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout editingpanelLayout = new javax.swing.GroupLayout(editingpanel);
        editingpanel.setLayout(editingpanelLayout);
        editingpanelLayout.setHorizontalGroup(
            editingpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editingpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editingpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(editingpanelLayout.createSequentialGroup()
                        .addComponent(confirmEditing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        editingpanelLayout.setVerticalGroup(
            editingpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editingpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editingpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(confirmEditing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(tablePanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 973, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(editingpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(240, 240, 240))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(editingpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tablePanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 774, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        jScrollPane2.setViewportView(jPanel1);

        jMenuBar1.setBackground(new java.awt.Color(0, 255, 128));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        menu1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        menu1.setText("HOME ");
        menu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(menu1);

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
        menu4.setText("CLASS RECORD");
        jMenuBar1.add(menu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1481, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Deselect button
    private void deselect2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deselect2ActionPerformed
        update();
    }//GEN-LAST:event_deselect2ActionPerformed
    
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

    
    ///Search text field
    private void searchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFocusGained
        if(search.getText().trim().equals("TYPE WHOM YOU WANTED TO SEARCH"))
        search.setText("");
            search.setForeground(Color.BLACK);
    }//GEN-LAST:event_searchFocusGained

    private void searchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFocusLost
        if(search.getText().trim().equals("")){
            search.setText("TYPE WHOM YOU WANTED TO SEARCH");
            search.setForeground(new Color(153,153,153));}
    }//GEN-LAST:event_searchFocusLost

    private void searchcomboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_searchcomboItemStateChanged
        search.setText("TYPE WHOM YOU WANTED TO SEARCH");
        search.setForeground(new Color(153,153,153));
        
    }//GEN-LAST:event_searchcomboItemStateChanged

    
    //Student enrollment to the class
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Boolean flag = true;
        
        //Get the row of the selected table as well as the lrn of the student which is selected
        int row = table2.getSelectedRow();
        String lrn =  (String) table2.getModel().getValueAt(row, 0);
        
        //test if the student is already 
        for(int i = 0; i < table.getRowCount(); i++){
            if(lrn.equals(table.getValueAt(i, 0))){
                flag = false;
                break;
            }
        }
        
        //Get the certainty of the user if the user really wants to enroll the student
        String[] options = new String[] {"Yes", "No"};
        int response = JOptionPane.showOptionDialog(null, "Are you sure?", "CERTAINTY",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        if(response == 0 && flag){
            String Query = "INSERT INTO `grades` (`Class_ID`, `LRN`, `year`, `section`) VALUES (?, ?, ?, ?)";
            try{
                MConnection mc = new MConnection();
                Statement stmt = mc.getStmt();
                Connection conn = mc.getConn();
                PreparedStatement pst = conn.prepareStatement(Query);
                
                int y = 0;
                String section = "";
                MConnection mc1 = new MConnection();
                Connection conn1 = mc1.getConn();
                String QUERY1 = "SELECT * FROM student WHERE lrn = ?";
                PreparedStatement pst1 = conn1.prepareStatement(QUERY1);
                pst1.setString(1, lrn);
                ResultSet rs1 = pst1.executeQuery();
                if(rs1.next()){
                    y = rs1.getInt("year");
                    section = User.getSection(rs1.getInt("adviser_id"));
                }
                
                
                pst.setInt(1, this.class_id);
                pst.setString(2, lrn);    
                pst.setInt(3, y);
                pst.setString(4, section);    
                
                pst.execute();
            } catch (SQLException ex) {
                System.out.print(ex);
            }
        }
        
        update();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
        if(!search.getText().equals("")){
            String querylrn = "SELECT * FROM student INNER JOIN teacher on student.adviser_id = teacher.id INNER JOIN school on teacher.school_id = school.id WHERE school.principal_id = "+ User.principal_id +" and student.year = " + this.curriculum_year_integer + " and student.lrn LIKE ?";
            String queryname = "SELECT * FROM student INNER JOIN teacher on student.adviser_id = teacher.id INNER JOIN school on teacher.school_id = school.id WHERE school.principal_id = "+ User.principal_id +" and student.year = " + this.curriculum_year_integer + " and (student.first_name LIKE ? or student.middle_name LIKE ? or student.last_name LIKE ? or student.prefix LIKE ?)";                             
            String querysection = "SELECT * FROM student INNER JOIN teacher on student.adviser_id = teacher.id INNER JOIN school on teacher.school_id = school.id WHERE school.principal_id = "+ User.principal_id +" and student.year = " + this.curriculum_year_integer + " and teacher.section LIKE ?";
            String Query = "";
            try{
                String search_name = searchcombo.getSelectedItem().toString().toLowerCase();

                switch(search_name){
                    case "lrn" -> {
                        Query = querylrn;
                    }
                    case "name" -> {
                        Query = queryname;
                    }
                    case "section" -> {
                        Query = querysection;
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
                DefaultTableModel model = (DefaultTableModel)table2.getModel();
                int rowCount = model.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    model.removeRow(i);
                }
                /// display all result at the table
                while(rs.next()){
                String lrn = rs.getString("lrn");
                String studentName = rs.getString("Last_name").toUpperCase() + " " + rs.getString("prefix") + ", " + rs.getString("first_name").toUpperCase() + " " + rs.getString("middle_name").toUpperCase().charAt(0) + ".";
                model.addRow(new Object[]{lrn, studentName});
                }
            } catch (SQLException ex) {
                System.out.print(ex);
            }
        }else {
            try{
                //Start connection between the program and mysql database
                MConnection mc = new MConnection();
                Connection conn = mc.getConn();
                ResultSet rs = null;

                //Assign query statement
                String QUERY = "SELECT * FROM student INNER JOIN teacher on student.adviser_id = teacher.id INNER JOIN school on teacher.school_id = school.id WHERE school.principal_id = ?";
                PreparedStatement pst = conn.prepareStatement(QUERY);

                //Set value for the parameter in query
                pst.setInt(1, User.principal_id);
                rs = pst.executeQuery();

                //Erase all the row in table2
                DefaultTableModel model = (DefaultTableModel)table2.getModel();
                int rowCount = model.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    model.removeRow(i);
                }

                //Display the resulted student's lrn and name into table 2
                while(rs.next()){
                    String lrn = rs.getString("lrn");
                    String studentName = rs.getString("Last_name").toUpperCase() + " " + rs.getString("prefix") + ", " + rs.getString("first_name").toUpperCase() + " " + rs.getString("middle_name").toUpperCase().charAt(0) + ".";
                    model.addRow(new Object[]{lrn, studentName});
                }
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_searchKeyReleased

    private void menu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu3MouseClicked
        String QUERY = "SELECT * FROM class WHERE id = ?";
        try{
            ///List all of principals
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            PreparedStatement pst = null;
            pst = conn.prepareStatement(QUERY);
            pst.setInt(1, this.class_id);
            rs = pst.executeQuery();
            while(rs.next()){
                this.setVisible(false);
                new Teacher(rs.getInt("teacher_id")).setVisible(true);
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menu3MouseClicked

    private void confirmEditingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmEditingActionPerformed
        int  row;
        String lrn = "";
        row = table.getSelectedRow();
        lrn =  (String) table.getModel().getValueAt(row, 0);
        String[] options = new String[] {"Yes", "No"};
        int response = JOptionPane.showOptionDialog(null, "Are you sure?", "CERTAINTY",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        if(response == 0){
            editingpanel.setVisible(false);
            String Query = "UPDATE `grades` SET `First_Quarter`=?,`Second_Quarter`=?,`Third_Quarter`=?,`Fourth_Quarter`=?,`Commulative_Final_Rating`=?,`Action_Taken`=? WHERE `Class_ID`=? and`LRN`=?";
            try{
                float firstq = 0, secondq = 0, thirdq = 0, fourthq = 0, cfa = 0;
                if(!fq.getText().equals("")) 
                    firstq = Float.valueOf(fq.getText());
                if(!sq.getText().equals(""))
                    secondq = Float.valueOf(sq.getText());
                if(!tq.getText().equals(""))
                    thirdq = Float.valueOf(tq.getText());
                if(!foq.getText().equals(""))
                    fourthq = Float.valueOf(foq.getText());
                cfa = (firstq + secondq + thirdq + fourthq)/4;
                String at = (firstq > 0 && secondq > 0 && thirdq > 0 && fourthq > 0)? (cfa < 75)? "Failed": "Passed" : "INC";
                MConnection mc = new MConnection();
                Statement stmt = mc.getStmt();
                Connection conn = mc.getConn();
                PreparedStatement pst = conn.prepareStatement(Query);
                pst.setFloat(1, firstq);
                pst.setFloat(2, secondq);
                pst.setFloat(3, thirdq);
                pst.setFloat(4, fourthq);
                pst.setFloat(5, cfa);
                pst.setString(6, at);
                pst.setInt(7, this.class_id);
                pst.setString(8, lrn);
                
                pst.execute();
            } catch (SQLException ex) {
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        update();
    }//GEN-LAST:event_confirmEditingActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        try {
            editingpanel.setVisible(true);
            fq.setText("");
            sq.setText("");
            tq.setText("");
            foq.setText("");
            
            int row = table.getSelectedRow();
            String lrn =  (String) table.getModel().getValueAt(row, 0);
            String studentName = "";
            
            // get name of student
            MConnection mc1 = new MConnection();
            Connection conn1 = mc1.getConn();
            String QUERY1 = "SELECT * FROM grades WHERE lrn = ? and class_id = ?";
            PreparedStatement pst1 = conn1.prepareStatement(QUERY1);
            pst1.setString(1, lrn);
            pst1.setInt(2, this.class_id);
            ResultSet rs1 = pst1.executeQuery();
            if(rs1.next()){
                fq.setText(rs1.getFloat("First_Quarter")+"");
                sq.setText(rs1.getFloat("Second_Quarter")+"");
                tq.setText(rs1.getFloat("Third_Quarter")+"");
                foq.setText(rs1.getFloat("Fourth_Quarter")+"");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Class_record.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tableMouseClicked

    private void fqKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fqKeyTyped
        char c = evt.getKeyChar();
        String text = fq.getText();
        if ((!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) ||  ((c == '.') && text.indexOf('.') < 0))) ||  text.length() >= 6) {
             evt.consume();
        }
        else if(Character.isDigit(c)){
            if(Float.valueOf(text + c) > 100)
                evt.consume();
        } 
    }//GEN-LAST:event_fqKeyTyped

    private void sqKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sqKeyTyped
        char c = evt.getKeyChar();
        String text = sq.getText();
        if ((!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) ||  ((c == '.') && text.indexOf('.') < 0))) ||  text.length() >= 6) {
             evt.consume();
        }
        else if(Character.isDigit(c)){
            if(Float.valueOf(text + c) > 100)
                evt.consume();
        } 
    }//GEN-LAST:event_sqKeyTyped

    private void tqKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tqKeyTyped
        char c = evt.getKeyChar();
        String text = tq.getText();
        if ((!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) ||  ((c == '.') && text.indexOf('.') < 0))) ||  text.length() >= 6) {
             evt.consume();
        }
        else if(Character.isDigit(c)){
            if(Float.valueOf(text + c) > 100)
                evt.consume();
        } 
    }//GEN-LAST:event_tqKeyTyped

    private void foqKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_foqKeyTyped
        char c = evt.getKeyChar();
        String text = foq.getText();
        if ((!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) ||  ((c == '.') && text.indexOf('.') < 0))) ||  text.length() >= 6) {
             evt.consume();
        }
        else if(Character.isDigit(c)){
            if(Float.valueOf(text + c) > 100)
                evt.consume();
        } 
    }//GEN-LAST:event_foqKeyTyped

    private void fqFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fqFocusGained
        Float value = (float) 0;
        if(!fq.getText().equals(""))
            value = Float.valueOf(fq.getText());
        this.firstquarter = value;
        fq.setText("");
    }//GEN-LAST:event_fqFocusGained

    private void fqFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fqFocusLost
       if(fq.getText().trim().equals(""))
            fq.setText(this.firstquarter + "");
    }//GEN-LAST:event_fqFocusLost

    private void sqFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sqFocusGained
        Float value = (float) 0;
        if(!sq.getText().equals(""))
            value = Float.valueOf(sq.getText());
        this.secondquarter = value;
        sq.setText("");
    }//GEN-LAST:event_sqFocusGained

    private void sqFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sqFocusLost
        if(sq.getText().trim().equals(""))
            sq.setText(this.secondquarter + "");
    }//GEN-LAST:event_sqFocusLost

    private void tqFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tqFocusGained
        Float value = (float) 0;
        if(!tq.getText().equals(""))
            value = Float.valueOf(tq.getText());
        this.thirdquarter = value;
        tq.setText("");
    }//GEN-LAST:event_tqFocusGained

    private void tqFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tqFocusLost
        if(tq.getText().trim().equals(""))
            tq.setText(this.thirdquarter + "");
    }//GEN-LAST:event_tqFocusLost

    private void foqFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_foqFocusGained
        Float value = (float) 0;
        if(!foq.getText().equals(""))
            value = Float.valueOf(foq.getText());
        this.fourthquarter = value;
        foq.setText("");
    }//GEN-LAST:event_foqFocusGained

    private void foqFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_foqFocusLost
        if(foq.getText().trim().equals(""))
            foq.setText(this.fourthquarter + "");
    }//GEN-LAST:event_foqFocusLost

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int  row;
        String lrn = "";
        row = table.getSelectedRow();
        lrn =  (String) table.getModel().getValueAt(row, 0);
        String[] options = new String[] {"Yes", "No"};
        int response = JOptionPane.showOptionDialog(null, "Are you sure?", "CERTAINTY",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        if(response == 0){
            try {
                MConnection mc = new MConnection();
                Connection conn = mc.getConn();
                String QUERY = "DELETE FROM grades where class_id = ? and lrn = ?";
                PreparedStatement pst = conn.prepareStatement(QUERY);
                pst.setInt(1, this.class_id);
                pst.setString(2, lrn);
                pst.execute();
                conn.close();
                pst.close();
                update();
            } catch (SQLException ex) {
                Logger.getLogger(Class_record.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Class_record.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Class_record.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Class_record.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Class_record.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton confirmEditing;
    private javax.swing.JButton deselect2;
    private javax.swing.JPanel editingpanel;
    private javax.swing.JTextField foq;
    private javax.swing.JTextField fq;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu menu1;
    private javax.swing.JMenu menu2;
    private javax.swing.JMenu menu3;
    private javax.swing.JMenu menu4;
    private javax.swing.JTextField search;
    private javax.swing.JComboBox<String> searchcombo;
    private javax.swing.JTextField sq;
    private javax.swing.JMenuItem subclass1;
    private javax.swing.JMenuItem substudent1;
    private javax.swing.JMenuItem subsubject1;
    private javax.swing.JMenuItem subteacher1;
    private javax.swing.JTable table;
    private javax.swing.JTable table2;
    private javax.swing.JPanel tablePanel2;
    private javax.swing.JTextField tq;
    // End of variables declaration//GEN-END:variables
}
