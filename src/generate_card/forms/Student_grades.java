package generate_card.forms;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import generate_card.classes.MConnection;
import generate_card.classes.User;
import generate_card.classes.exel_file;
import java.net.URL;
import java.sql.SQLException;
import java.sql.*;  
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Marvin
 */
public class Student_grades extends javax.swing.JFrame {
    String lrn;
    Vector<String> allgradeYear = new Vector<String>();
    /**
     * Creates new form Student_grades
     */
    public Student_grades(String sid) {
        initComponents();
        ImageIcon logo = new javax.swing.ImageIcon(getClass().getResource("/generate_card/images_or_icon/projectIcon.jpg"));
        setIconImage(logo.getImage());
        lrn = sid;
        if(!User.userType.equals("student"))
            form138.setVisible(false);
        
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
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        setSchoolYear();
    }
    private void setSchoolYear(){
        try{
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            String QUERY = "SELECT * FROM class INNER JOIN grades on class.id = grades.class_id INNER JOIN student on grades.lrn = student.lrn INNER JOIN teacher on student.adviser_id = teacher.id INNER JOIN school on teacher.school_id = school.id WHERE school.principal_id = "+ User.principal_id +" and grades.lrn = ?";
            PreparedStatement pst = conn.prepareStatement(QUERY);
            pst.setString(1, lrn);
            rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            /// display all result at the table
            sy.addItem("");
            while(rs.next()){
                Boolean flag = true;
                for(int i = 0; i < sy.getItemCount(); i++){
                    if((rs.getInt("Start_year")+ " to " + rs.getInt("End_year")).equals(sy.getItemAt(i))){
                        flag = false;
                        break;
                    }
                }
                if(flag)   {
                    sy.addItem(rs.getInt("Start_year")+ " to " + rs.getInt("End_year"));
                    allgradeYear.add(rs.getInt("Start_year")+ " to " + rs.getInt("End_year"));
                }
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private int getTableId(String table, int id){
        int cid = 0;
        try{
            MConnection mc1 = new MConnection();
            Connection conn1 = mc1.getConn();
            ResultSet rs1 = null;
            String QUERY1 = "";
            QUERY1 = "SELECT * FROM class WHERE id = ?";
            PreparedStatement pst1 = conn1.prepareStatement(QUERY1);
            pst1.setInt(1,id);
            rs1 = pst1.executeQuery();
            /// display all result at the table
            while(rs1.next()){
                if(table.equals("subject"))
                    cid = rs1.getInt("subject_id");
                else if(table.equals("teacher"))
                    cid = rs1.getInt("teacher_id");
            }
            rs1.close();
            conn1.close();
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cid;
    }
    private void updateTable(){
        if(!sy.getSelectedItem().toString().equals("")){
            String[] schoolyear = sy.getSelectedItem().toString().split(" to ");
            int startyear = Integer.valueOf(schoolyear[0]);
            int endyear = Integer.valueOf(schoolyear[1]);
            if(listTitle.getSelectedItem().equals("Grades")){
                if(!sy.getSelectedItem().equals("")){
                    try{
                        MConnection mc = new MConnection();
                        Connection conn = mc.getConn();
                        ResultSet rs = null;
                        String QUERY = "SELECT * FROM grades INNER JOIN class on grades.class_id = class.id WHERE grades.lrn = ? and class.Start_year = ? and class.End_year = ?";
                        PreparedStatement pst = conn.prepareStatement(QUERY);
                        pst.setString(1, lrn);
                        pst.setInt(2, startyear);
                        pst.setInt(3, endyear);
                        rs = pst.executeQuery();
                        DefaultTableModel model = (DefaultTableModel)table.getModel();
                        int rowCount = model.getRowCount();
                        for (int i = rowCount - 1; i >= 0; i--) {
                            model.removeRow(i);
                        }
                        /// display all result at the table

                        while(rs.next()){
                            /// get the class first
                            int cid = getTableId("subject", rs.getInt("class_id"));
                            int tid = getTableId("teacher", rs.getInt("class_id"));
                            model.addRow(new Object[]{User.getName1("subject", cid), User.getName1("teacher", tid), rs.getFloat("First_Quarter"), rs.getFloat("Second_Quarter"), rs.getFloat("Third_Quarter"), rs.getFloat("Fourth_Quarter"), rs.getFloat("Commulative_Final_Rating")});
                        }
                        rs.close();
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else{
                try{
                    MConnection mc = new MConnection();
                    Connection conn = mc.getConn();
                    ResultSet rs = null;
                    String QUERY = "SELECT * FROM class INNER JOIN grades on class.id = grades.class_id WHERE grades.lrn = ? and class.Start_year = ? and class.End_year = ?";
                    PreparedStatement pst = conn.prepareStatement(QUERY);
                    pst.setString(1, lrn);
                    pst.setInt(2, startyear);
                    pst.setInt(3, endyear);
                    rs = pst.executeQuery();
                    DefaultTableModel model = (DefaultTableModel)table.getModel();
                    int rowCount = model.getRowCount();
                    for (int i = rowCount - 1; i >= 0; i--) {
                        model.removeRow(i);
                    }
                    /// display all result at the table
                    while(rs.next()){
                        /// get the class first

                        int tid = getTableId("teacher", rs.getInt("class_id"));
                        model.addRow(new Object[]{rs.getInt("id"), User.getName1("subject", rs.getInt("subject_id")), rs.getString("Schedule_day"), rs.getString("start_time") + " to " + rs.getString("end_time"), User.getCreditsEarned(rs.getInt("subject_id")), User.getName1("teacher", rs.getInt("teacher_id"))});
                    }
                    rs.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
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

        jPanel1 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        sy = new javax.swing.JComboBox<>();
        sylbl = new javax.swing.JLabel();
        jScrollBar1 = new javax.swing.JScrollBar();
        form137 = new javax.swing.JButton();
        form138 = new javax.swing.JButton();
        listTitle = new javax.swing.JComboBox<>();
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
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel16.setBackground(new java.awt.Color(0, 255, 128));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Grades", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 18))); // NOI18N
        jPanel16.setForeground(new java.awt.Color(255, 255, 255));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subject", "Instructor", "First Quarter", "Second Quarter", "Third Quarter", "Fourt Quarter", "Commulative final rating"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setColumnSelectionAllowed(true);
        table.setShowGrid(true);
        table.setShowVerticalLines(false);
        jScrollPane2.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(5).setResizable(false);
        }

        sy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                syItemStateChanged(evt);
            }
        });

        sylbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sylbl.setText("School year");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(264, 264, 264)
                        .addComponent(sylbl, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sy, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1015, Short.MAX_VALUE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sylbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73))
        );

        jPanel1.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1590, 680));

        form137.setText("FORM 137");
        form137.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                form137ActionPerformed(evt);
            }
        });
        jPanel1.add(form137, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 750, 180, -1));

        form138.setText("FORM 138");
        form138.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                form138ActionPerformed(evt);
            }
        });
        jPanel1.add(form138, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 750, 180, -1));

        listTitle.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Grades", "My class" }));
        listTitle.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                listTitleItemStateChanged(evt);
            }
        });
        jPanel1.add(listTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(1466, 20, 130, 40));

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
        menu4.add(jMenuItem1);

        jMenuItem2.setBackground(new java.awt.Color(0, 255, 128));
        jMenuItem2.setText("BACKGROUND");
        jMenuItem2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        menu4.add(jMenuItem2);

        jMenuBar1.add(menu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1608, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listTitleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_listTitleItemStateChanged
        String title = listTitle.getSelectedItem().toString();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        sy.setSelectedIndex(0);
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), title, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 18))); // NOI18N

        if(title.equals("My class")){
            
            if(!User.userType.equals("student")){
                form137.setVisible(false);
                form138.setVisible(false);
            }
            //set the model of table

            table.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "ID", "Subject", "Days", "Time", "Credist earned", "Instructor"
                }
            ) {
                Class[] types = new Class [] {
                    java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class
                };
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false
                };
                

                @Override
                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });

            table.setShowVerticalLines(false);

            jScrollPane2.setViewportView(table);

            if (table.getColumnModel().getColumnCount() > 0) {
                table.getColumnModel().getColumn(0).setResizable(false);
                table.getColumnModel().getColumn(0).setPreferredWidth(10);
                table.getColumnModel().getColumn(1).setResizable(false);
                table.getColumnModel().getColumn(1).setPreferredWidth(150);
                table.getColumnModel().getColumn(2).setResizable(false);
                table.getColumnModel().getColumn(3).setResizable(false);
                table.getColumnModel().getColumn(4).setResizable(false);
                table.getColumnModel().getColumn(5).setResizable(false);
                table.getColumnModel().getColumn(5).setPreferredWidth(150);
            }
            for(int i = 0; i < 6; i++)
                table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
            
        }
        else{
            
            if(!User.userType.equals("student")){
                form137.setVisible(true);
                form138.setVisible(false);
            }
            table = new javax.swing.JTable();

            table.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "Subject", "Instructor", "First Quarter", "Second Quarter", "Third Quarter", "Fourt Quarter", "Commulative final rating"
                }
            ) {
                Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.String.class
                };
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false, false
                };

                @Override
                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });

            table.setShowVerticalLines(false);

            jScrollPane2.setViewportView(table);

            table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            if (table.getColumnModel().getColumnCount() > 0) {
                table.getColumnModel().getColumn(0).setResizable(false);
                table.getColumnModel().getColumn(0).setPreferredWidth(100);
                table.getColumnModel().getColumn(1).setResizable(false);
                table.getColumnModel().getColumn(1).setPreferredWidth(100);
                table.getColumnModel().getColumn(2).setResizable(false);
                table.getColumnModel().getColumn(2).setPreferredWidth(30);
                table.getColumnModel().getColumn(3).setResizable(false);
                table.getColumnModel().getColumn(3).setPreferredWidth(30);
                table.getColumnModel().getColumn(4).setResizable(false);
                table.getColumnModel().getColumn(4).setPreferredWidth(30);
                table.getColumnModel().getColumn(5).setResizable(false);
                table.getColumnModel().getColumn(5).setPreferredWidth(30);
                table.getColumnModel().getColumn(6).setResizable(false);
            }
            
            for(int i = 0; i < 7; i++)
                table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        }
        
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        updateTable();
        //end of seting the table
    }//GEN-LAST:event_listTitleItemStateChanged

    private void syItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_syItemStateChanged
        updateTable();
        if(!User.userType.equals("student")){
            if(listTitle.getSelectedItem().toString().equals("Grades")){
                if(!sy.getSelectedItem().toString().equals(""))
                    form138.setVisible(true);
                else    form138.setVisible(false);
                form137.setVisible(true);
            }else{
                form138.setVisible(false);
                form137.setVisible(false);
            }
        }
    }//GEN-LAST:event_syItemStateChanged

    private void menu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu1MouseClicked
        if(User.userType.equals("administrator")){
            this.setVisible(false);
            new Admin().setVisible(true);
        }
    }//GEN-LAST:event_menu1MouseClicked

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

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        this.setVisible(false);
        new Student_background(lrn).setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void menu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu3MouseClicked
        String QUERY = "SELECT * FROM student WHERE lrn = ?";
        try{
            ///List all of principals
            MConnection mc = new MConnection();
            Statement stmt = mc.getStmt();
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
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menu3MouseClicked

    private void form138ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_form138ActionPerformed
        String[] options = new String[] {"Yes", "No"};
        int response = JOptionPane.showOptionDialog(null, "Are you sure?", "CERTAINTY",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        if(response == 0){
            int student_grade = 0;
            String filenamepath = "/generate_card/xlsx/vsuform138.xlsx";
            URL path = getClass().getResource(filenamepath);
            String file = path.getPath();
            exel_file excel = new exel_file(file, "Form 138 (base)");

            try{

                String school_year = sy.getSelectedItem().toString();
                String[] schoolyear = school_year.split(" to ");
                int startyear = Integer.valueOf(schoolyear[0]);
                int endyear = Integer.valueOf(schoolyear[1]);

                MConnection mc = new MConnection();
                Connection conn = mc.getConn();
                ResultSet rs = null;


                String QUERY = "SELECT * FROM student where lrn = ?";
                PreparedStatement pst = conn.prepareStatement(QUERY);
                pst.setString(1, lrn);
                rs = pst.executeQuery();
                if(rs.next()){
                    excel.setValueAt(8, 2, User.getName(lrn));
                    excel.setValueAt(8, 12, "Age: "+rs.getInt("age"));
                    excel.setValueAt(8, 15, "Sex: "+rs.getString("gender").toUpperCase().charAt(0));
                    excel.setValueAt(10, 3, rs.getString("Curriculum"));
                    student_grade = rs.getInt("year");
                    excel.setValueAt(10, 13, "GRADE: "+student_grade);
                    excel.setValueAt(11, 3, school_year);
                    excel.setValueAt(11, 13, User.getSection(rs.getInt("adviser_id")));
                    excel.setValueAt(12, 0, "LRN: "+rs.getString("lrn"));
                }


                QUERY = "SELECT * FROM grades INNER JOIN class on grades.class_id = class.id WHERE grades.lrn = ? and class.Start_year = ? and class.End_year = ?";
                pst = conn.prepareStatement(QUERY);
                pst.setString(1, lrn);
                pst.setInt(2, startyear);
                pst.setInt(3, endyear);
                rs = pst.executeQuery();
                int iterator = 16;
                float gpa = 0;
                float units = 0;
                Boolean ok = true;
                Vector<String> lackSubjects = new Vector<String>();
                while(rs.next()){
                    float unit = User.getCreditsEarned(getTableId("subject", rs.getInt("class_id")));
                    units += unit;
                    Boolean flag = true;
                    for(int i = 0; i < 8; i++){
                        if(i == 0){
                            excel.newSubject(iterator, i, User.getName1("subject", getTableId("subject", rs.getInt("class_id"))));
                        }else if(i == 1){
                            excel.setValueAt(iterator, i+9, rs.getFloat("first_Quarter"));
                            if(rs.getFloat("first_Quarter") < 1)    flag = false;
                        }else if(i == 2){
                            excel.setValueAt(iterator, i+9, rs.getFloat("second_Quarter"));
                            if(rs.getFloat("second_Quarter") < 1)    flag = false;
                        }else if(i == 3){
                            excel.setValueAt(iterator, i+9, rs.getFloat("third_Quarter"));
                            if(rs.getFloat("third_Quarter") < 1)    flag = false;
                        }else if(i == 4){
                            excel.setValueAt(iterator, i+9, rs.getFloat("fourth_Quarter"));
                            if(rs.getFloat("fourth_Quarter") < 1)    flag = false;
                        }else if(i == 5){
                            if(flag){
                                float cfr = rs.getFloat("Commulative_Final_Rating");
                                excel.setValueAt(iterator, i+9, rs.getFloat("Commulative_Final_Rating"));
                                gpa = gpa + (cfr * unit);
                            }
                        }else if(i == 6){
                            excel.setValueAt(iterator, i+9, rs.getString("Action_Taken"));
                        }else if(i == 7){
                            if(flag)
                                excel.setValueAt(iterator, i+9, unit);
                        }
                    }
                    if(!flag){
                        ok = false;
                        lackSubjects.add(User.getName1("subject", getTableId("subject", rs.getInt("class_id"))));
                    }
                    iterator++;
                }
                gpa = gpa/units;
                if(ok){
                    excel.setValueAt(iterator, 4, "GPA = "+gpa);
                    excel.setValueAt(iterator, 10, "TOTAL NO. OF UNITS : "+units);
                }
                iterator+=5;
                if(gpa >= 75){
                    excel.setValueAt(iterator++, 11, "Grade " + student_grade);
                    excel.setValueAt(iterator++, 11, "NONE");
                    excel.setValueAt(iterator, 11, "NONE");
                }
                else{
                    String lack = "";
                    excel.setValueAt(++iterator, 11, "NONE");
                    for(int s = 0; s < lackSubjects.size(); s++){
                        lack += lackSubjects.elementAt(s);
                        if(s < lackSubjects.size()-1)
                            lack += ", ";
                    }
                    excel.setValueAt(++iterator, 11, lack);
                }
                iterator+=2;
                LocalDate today = LocalDate.now();
                String day = today.getMonth() + "/ " + today.getDayOfMonth() + "/ " + today.getYear();
                excel.setValueAt(iterator, 2, day);


                rs.close();
                conn.close();

                excel.writeout();
            } catch (SQLException ex) {
                Logger.getLogger(Student_grades.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_form138ActionPerformed

    private void form137ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_form137ActionPerformed
        Collections.sort(allgradeYear);
        int student_grade = 0;
        String filenamepath = "/generate_card/xlsx/vsuform137.xlsx";
        URL path = getClass().getResource(filenamepath);
        String file = path.getPath();
        exel_file excel = new exel_file(file, "Form 137 (base)");

        try{
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;


            String QUERY = "SELECT * FROM student where lrn = ?";
            PreparedStatement pst = conn.prepareStatement(QUERY);
            pst.setString(1, lrn);
            rs = pst.executeQuery();
            if(rs.next()){
                excel.setValueAt(8, 1, User.getName(lrn));
                java.util.Date date = null;
                date = new SimpleDateFormat("M-D-YY").parse(rs.getString("Date_of_birth"));
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int dayInMonth = c.get(Calendar.DATE);
                excel.setValueAt(8, 12, year);
                excel.setValueAt(8, 15, month);
                excel.setValueAt(8, 21, dayInMonth);
                String add = rs.getString("address");
                String split[] = add.split(",");
                int ctr = 0;
                for(String s: split){
                    switch(ctr){
                        case 1:
                            excel.setValueAt(9, 16, s);
                        case 2:
                            excel.setValueAt(9, 10, s);
                        case 3:
                            excel.setValueAt(9, 4, s);
                        default:;
                    }
                    ctr++;
                }
                excel.setValueAt(10, 3, rs.getString("Guardian_first_name")+" "+rs.getString("Guardian_middle_name")+" "+rs.getString("Guardian_Last_name")+" "+rs.getString("Guardian_prefix"));
                excel.setValueAt(10, 13, rs.getString("Guardian_occupation"));
                excel.setValueAt(11, 5, rs.getString("Guardian_address"));
                excel.setValueAt(12, 6, rs.getString("icc"));
                excel.setValueAt(12, 13, rs.getString("Guardian_address"));
                excel.setValueAt(12, 13, "Grade " + User.integerToRoman(rs.getInt("year")));
                excel.setValueAt(12, 20, rs.getFloat("General_Average"));
                excel.setValueAt(13, 1, rs.getString("LRN"));
                excel.setValueAt(16, 15, rs.getInt("total_num"));

            }
            int iterator = 23;
            int abc = 0;

            for(String syear : allgradeYear){
                int x = iterator;
                String[] schoolyear = syear.split(" to ");
                int startyear = Integer.valueOf(schoolyear[0]);
                int endyear = Integer.valueOf(schoolyear[1]);
                QUERY = "SELECT * FROM grades INNER JOIN class on grades.class_id = class.id WHERE grades.lrn = ? and class.Start_year = ? and class.End_year = ?";
                pst = conn.prepareStatement(QUERY);
                pst.setString(1, lrn);
                pst.setInt(2, startyear);
                pst.setInt(3, endyear);
                rs = pst.executeQuery();
                float gpa = 0;
                float units = 0;

                int y = 0;
                String section = "";

                while(rs.next()){
                    if(y == 0 && section.equals("")){
                        y = rs.getInt("year");
                        section = rs.getString("section");
                    }else if(y != 0 && !section.equals("") && y != rs.getInt("year") && !section.equals("section")){
                        JOptionPane.showMessageDialog(new JFrame(), "Error!!! Student consist of another year and section during the same school year", "Dialog", JOptionPane.ERROR_MESSAGE);
                    }

                    float unit = User.getCreditsEarned(getTableId("subject", rs.getInt("class_id")));
                    units += unit;
                    for(int i = 0; i < 9; i++){
                        if(i == 0){
                            excel.setValueAt(iterator, i, User.getCurriculumYear(getTableId("subject", rs.getInt("class_id"))));
                        }
                        else if(i == 1){
                            excel.setValueAt(iterator, i, User.getName1("subject", getTableId("subject", rs.getInt("class_id"))));
                        }else if(i == 2){
                            excel.setValueAt(iterator, i+7, rs.getFloat("first_Quarter"));
                        }else if(i == 3){
                            excel.setValueAt(iterator, i+7, rs.getFloat("second_Quarter"));
                        }else if(i == 4){
                            excel.setValueAt(iterator, i+7, rs.getFloat("third_Quarter"));
                        }else if(i == 5){
                            excel.setValueAt(iterator, i+7, rs.getFloat("fourth_Quarter"));
                        }else if(i == 6){
                            float cfr = rs.getFloat("Commulative_Final_Rating");
                            excel.setValueAt(iterator, i+7, rs.getFloat("Commulative_Final_Rating"));
                            gpa = gpa + (cfr * unit);
                        }else if(i == 7){
                            excel.setValueAt(iterator, i+7, rs.getString("Action_Taken"));
                        }else if(i == 8){
                            excel.setValueAt(iterator, i+7, unit);
                        }
                    }
                    iterator++;
                }
                /// set year and section
                gpa = gpa / units;
                if(abc == 0){
                    excel.setValueAt(41, 15, units);
                    excel.setValueAt(43, 15, gpa);
                    excel.setValueAt(17, 0, "Grade " + User.integerToRoman(y) + " - " + section);
                    excel.setValueAt(18, 5, User.getName1("school", User.school_id));
                    excel.setValueAt(18, 14, syear);
                    iterator = 51;
                }
                else if(abc == 1){
                    excel.setValueAt(69, 15, units);
                    excel.setValueAt(71, 15, gpa);
                    excel.setValueAt(45, 0, "Grade " + User.integerToRoman(y) + " - " + section);
                    excel.setValueAt(46, 5, User.getName1("school", User.school_id));
                    excel.setValueAt(46, 14, syear);
                    iterator = 84;
                }
                else if(abc == 2){
                    excel.setValueAt(103, 15, units);
                    excel.setValueAt(105, 15, gpa);
                    excel.setValueAt(75, 1, User.getName(lrn));
                    excel.setValueAt(78, 0, "Grade " + User.integerToRoman(y) + " - " + section);
                    excel.setValueAt(79, 5, User.getName1("school", User.school_id));
                    excel.setValueAt(79, 14, syear);
                    iterator = 113;
                }
                else if(abc == 3){
                    excel.setValueAt(131, 15, units);
                    excel.setValueAt(133, 15, gpa);
                    excel.setValueAt(107, 0, "Grade " + User.integerToRoman(y) + " - " + section);
                    excel.setValueAt(108, 5, User.getName1("school", User.school_id));
                    excel.setValueAt(108, 14, syear);
                }
                abc++;

            }
            excel.writeout();
            rs.close();
            conn.close();

        }catch (SQLException ex) {
                Logger.getLogger(Student_grades.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Student_grades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_form137ActionPerformed

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
            java.util.logging.Logger.getLogger(Student_grades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Student_grades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Student_grades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Student_grades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton form137;
    private javax.swing.JButton form138;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> listTitle;
    private javax.swing.JMenu menu1;
    private javax.swing.JMenu menu2;
    private javax.swing.JMenu menu3;
    private javax.swing.JMenu menu4;
    private javax.swing.JMenuItem subclass1;
    private javax.swing.JMenuItem substudent1;
    private javax.swing.JMenuItem subsubject1;
    private javax.swing.JMenuItem subteacher1;
    private javax.swing.JComboBox<String> sy;
    private javax.swing.JLabel sylbl;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
