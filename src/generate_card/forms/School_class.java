package generate_card.forms;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    
import generate_card.classes.MConnection;
import generate_card.classes.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.sql.*;  
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Marvin
 */
public class School_class extends javax.swing.JFrame {
    String action = "";
    /**
     * Creates new form School_class
     */
    public School_class() {
        initComponents();
        ImageIcon logo = new javax.swing.ImageIcon(getClass().getResource("/generate_card/images_or_icon/projectIcon.jpg"));
        setIconImage(logo.getImage());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        
        if(!User.userType.equals("administrator")){
            menu1.setVisible(false);
            menu2.setText("HOME"); 
        }
            
        timePicker1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txt1.setText(timePicker1.getSelectedTime());
            }
        } );
        timePicker2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txt2.setText(timePicker2.getSelectedTime());
            }
        } );
        updateTable();
    }
    
    private void ena(Boolean flag){
        this.setEnabled(flag);
    }
    private void updateTable(){
        action = "";
        
        edit.setVisible(false);
        create.setVisible(true);
        delete.setVisible(false);
        confirm.setVisible(false);
        deselect.setVisible(false);
        panel2.setVisible(false);
        
        m.setSelected(false);
        t.setSelected(false);
        w.setSelected(false);
        th.setSelected(false);
        f.setSelected(false);
        s.setSelected(false);
        
        txt1.setText("");
        txt2.setText("");
        
        
        aname.setText("Click here to assign a teacher");
        subj.setText("Click here to assign a subject");
        
        try{
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            String QUERY = "";
            QUERY = "SELECT * FROM class INNER JOIN subject on class.subject_id = subject.id INNER JOIN school on subject.school_id = school.id WHERE school.principal_id = ?";
            PreparedStatement pst = conn.prepareStatement(QUERY);
            pst.setInt(1, User.principal_id);
            rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            /// display all result at the table
            while(rs.next()){
                int id = rs.getInt("ID");
                String teacher_name = User.getName("teacher", rs.getInt("teacher_id"));
                String subject_name = User.getName("subject", rs.getInt("subject_id"));
                String days = rs.getString("Schedule_day");
                String start_t = rs.getString("start_time");
                String end_t = rs.getString("end_time");
                int start_y = rs.getInt("Start_year");
                int end_y = rs.getInt("End_year");
                model.addRow(new Object[]{id, teacher_name, subject_name, days, start_t + " to " + end_t, start_y, end_y});
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void setSchedDay(String sched){
        String[] split = sched.split(" ");
        for(int i = 0; i < split.length; i++){
            switch(split[i]){
                case "M":{
                    m.setSelected(true);
                    break;}
                 case "T":{
                    t.setSelected(true);
                    break;}
                 case "W":{
                    w.setSelected(true);
                    break;}
                 case "TH":{
                    th.setSelected(true);
                    break;}
                 case "F":{
                    f.setSelected(true);
                    break;}
                 case "S":{
                    s.setSelected(true);
                    break;}
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

        timePicker1 = new com.raven.swing.TimePicker();
        timePicker2 = new com.raven.swing.TimePicker();
        jPanel1 = new javax.swing.JPanel();
        tablepanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        edit = new javax.swing.JButton();
        create = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        deselect = new javax.swing.JButton();
        search = new javax.swing.JTextField();
        searchcombo = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        aname = new javax.swing.JTextField();
        subj = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        m = new javax.swing.JRadioButton();
        t = new javax.swing.JRadioButton();
        w = new javax.swing.JRadioButton();
        th = new javax.swing.JRadioButton();
        f = new javax.swing.JRadioButton();
        s = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        txt1 = new javax.swing.JTextField();
        txt2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        confirm = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        starty = new javax.swing.JTextField();
        endy = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menu1 = new javax.swing.JMenu();
        menu2 = new javax.swing.JMenu();
        subteacher1 = new javax.swing.JMenuItem();
        subsubject1 = new javax.swing.JMenuItem();
        subclass1 = new javax.swing.JMenuItem();
        substudent1 = new javax.swing.JMenuItem();

        timePicker1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Starting time", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 3, 18))); // NOI18N

        timePicker2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ending time", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 3, 18))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 255, 128));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        tablepanel2.setBackground(new java.awt.Color(0, 255, 128));
        tablepanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "List of class", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial Black", 1, 18))); // NOI18N

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Teacher", "Subject", "Schedule days", "Schedule time", "School year(Start)", "School year(End)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
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
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        edit.setText("EDIT");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });

        create.setText("CREATE");
        create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createActionPerformed(evt);
            }
        });

        delete.setText("DELETE");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

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

        searchcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "TEACHER", "SUBJECT", "SCHOOL YEAR" }));
        searchcombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                searchcomboItemStateChanged(evt);
            }
        });

        jLabel11.setText("Search by: ");

        javax.swing.GroupLayout tablepanel2Layout = new javax.swing.GroupLayout(tablepanel2);
        tablepanel2.setLayout(tablepanel2Layout);
        tablepanel2Layout.setHorizontalGroup(
            tablepanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablepanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(tablepanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tablepanel2Layout.createSequentialGroup()
                        .addComponent(deselect, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(tablepanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tablepanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(search, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(create, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(tablepanel2Layout.createSequentialGroup()
                        .addComponent(edit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
                .addContainerGap())
        );
        tablepanel2Layout.setVerticalGroup(
            tablepanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablepanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tablepanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(tablepanel2Layout.createSequentialGroup()
                        .addGroup(tablepanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(searchcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(deselect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(create, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tablepanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(edit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        panel2.setBackground(new java.awt.Color(0, 255, 128));
        panel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "CLASS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 18))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(0, 255, 128));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel1.setText("Instructor");

        jLabel2.setText("Subject");

        aname.setEditable(false);
        aname.setForeground(new java.awt.Color(153, 153, 153));
        aname.setText("Click here to assign a teacher");
        aname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                anameMouseClicked(evt);
            }
        });

        subj.setEditable(false);
        subj.setForeground(new java.awt.Color(153, 153, 153));
        subj.setText("Click here to assign a subject");
        subj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                subjMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(aname, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subj, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(aname, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subj, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 255, 128));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Schedule"));

        m.setText("Monday");

        t.setText("Tuesday");

        w.setText("Wednesday");

        th.setText("Thursday");

        f.setText("Friday");

        s.setText("Saturday");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(m, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(t, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(w, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(th, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(f, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(s, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(m)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(t)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(w)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(th)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(f)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(s)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(0, 255, 128));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Schedule time"));

        txt1.setEditable(false);

        txt2.setEditable(false);

        jLabel3.setText("Starting time");

        jLabel4.setText("Ending time");

        jLabel5.setText("TO");

        jButton1.setText("SET TIME");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("SET TIME");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(31, 31, 31)
                .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(110, 110, 110))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(98, 98, 98))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)))
        );

        confirm.setText("CONFIRM");
        confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 255, 128));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "School Year"));

        jLabel7.setText("TO");

        jLabel8.setText("Starting year");

        jLabel9.setText("Ending year");

        starty.setEditable(false);

        endy.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(starty, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(endy, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(110, 110, 110))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(285, 285, 285)
                    .addComponent(jLabel7)
                    .addContainerGap(286, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(starty, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endy))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(38, 38, 38)
                    .addComponent(jLabel7)
                    .addContainerGap(41, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(confirm, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(confirm, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(389, 389, 389))
        );

        jLabel6.setText("VISAYAS STATE UNIVERSITY");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tablepanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(307, 307, 307)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(tablepanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(38, 38, 38))
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

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        edit.setVisible(true);
        create.setVisible(false);
        delete.setVisible(true);
        panel2.setVisible(true);
        confirm.setVisible(false);
        deselect.setVisible(true);
        
        m.setSelected(false);
        t.setSelected(false);
        w.setSelected(false);
        th.setSelected(false);
        f.setSelected(false);
        s.setSelected(false);
        
        int id, row;
        row = table.getSelectedRow();
        id = (int) table.getModel().getValueAt(row, 0);
        String teacher = (String) table.getModel().getValueAt(row, 1);
        String subject = (String) table.getModel().getValueAt(row, 2);
        aname.setForeground(new Color(0, 0, 0));
        aname.setText(teacher);
        subj.setForeground(new Color(0, 0, 0));
        subj.setText(subject);
        String days = "";
        String[] sched_days = table.getModel().getValueAt(row, 3).toString().split(" ");
        setSchedDay(table.getModel().getValueAt(row, 3).toString());
        String[] sched_time = table.getModel().getValueAt(row, 4).toString().split(" to ");
        txt1.setText(sched_time[0]);
        txt2.setText(sched_time[1]);
        starty.setText((int) table.getModel().getValueAt(row, 5)+"");
        endy.setText((int) table.getModel().getValueAt(row, 6)+"");
        
        
        
    }//GEN-LAST:event_tableMouseClicked

    private void deselectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deselectActionPerformed
        updateTable();
    }//GEN-LAST:event_deselectActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        confirm.setVisible(true);
        action = "edit";
        panel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ("Edit class").toUpperCase(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 2, 18), new java.awt.Color(255, 255, 255)));       
    }//GEN-LAST:event_editActionPerformed

    private void createActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createActionPerformed
        
        LocalDate today = LocalDate.now();
        starty.setText(today.getYear()+"");
        endy.setText((today.getYear()+1)+"");  
        
        
        ///clear form
        aname.setText("Click here to assign a teacher");
        aname.setForeground(new Color(153, 153, 153));
        subj.setText("Click here to assign a subject");
        subj.setForeground(new Color(153, 153, 153));
        txt1.setText("");
        txt2.setText("");
        m.setSelected(false);
        t.setSelected(false);
        w.setSelected(false);
        th.setSelected(false);
        f.setSelected(false);
        s.setSelected(false);
        
        panel2.setVisible(true);
        confirm.setVisible(true);
        action = "insert";
        panel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ("Create class").toUpperCase(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 2, 18), new java.awt.Color(255, 255, 255)));
    }//GEN-LAST:event_createActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        confirm.setVisible(true);
        action = "delete";
    }//GEN-LAST:event_deleteActionPerformed

    private void confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmActionPerformed
        String[] options = new String[] {"Yes", "No"};
        int response = JOptionPane.showOptionDialog(null, "Are you sure?", "CERTAINTY",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        if(response == 0){
            int id, row;                            
            row = table.getSelectedRow();
            MConnection mc = new MConnection();
            String teacher_name = aname.getText();
            String[] split1 = teacher_name.split(",");
            int teacher_id = Integer.valueOf(split1[0]);
            String subject_name = subj.getText();
            String[] split2 = subject_name.split(",");
            int subject_id = Integer.valueOf(split2[0]);
            String starts = txt1.getText();
            String end = txt2.getText();
            String sdays = "";
            for(int i = 0; i < 6; i++){
                if(m.isSelected()){
                    sdays += "M";
                   m.setSelected(false);
                }else if(t.isSelected()){
                    sdays += "T";
                   t.setSelected(false);
                }else if(w.isSelected()){
                    sdays += "W";
                   w.setSelected(false);
                }else if(th.isSelected()){
                    sdays += "TH";
                   th.setSelected(false);
                }else if(f.isSelected()){
                    sdays += "F";
                   f.setSelected(false);
                }else if(s.isSelected()){
                    sdays += "S";
                   s.setSelected(false);
                }
                if(i < 5)
                    sdays+=" ";
            }
            int sty = Integer.valueOf(starty.getText());
            int eny = Integer.valueOf(endy.getText());
            
            if(! (sdays.equals("") || starts.equals("") || end.equals("") || teacher_id > -1 || sty > -1 || eny > -1 || action.equals(""))){

                if(action.equals("insert")){
                    mc.insertOrEditClass(sdays, starts, end, subject_id, teacher_id, sty, eny, action, 0);
                }
                else if(action.equals("edit")){
                id = (int) table.getModel().getValueAt(row, 0);
                    mc.insertOrEditClass(sdays, starts, end, subject_id, teacher_id, sty, eny, action, id);
                }
            }else if(action.equals("insert") || action.equals("edit"))
                JOptionPane.showMessageDialog(new JFrame(), "Error!!! Invalid input", "Dialog", JOptionPane.ERROR_MESSAGE); 
            if(action.equals("delete")){
                id = (int) table.getModel().getValueAt(row, 0);
                User.deleteTable("class", "class", id, "");
            }
        }
        updateTable();  
    }//GEN-LAST:event_confirmActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        timePicker1.showPopup(this, 100, 100);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        timePicker2.showPopup(this, 100, 100);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void substudent1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_substudent1ActionPerformed
        this.setVisible(false);
        new School_student().setVisible(true);
    }//GEN-LAST:event_substudent1ActionPerformed

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
            String queryclassid = "SELECT * FROM class INNER JOIN teacher on class.teacher_id = teacher.id INNER JOIN school on teacher.school_id = school.id WHERE school.principal_id = "+ User.principal_id +" and class.id LIKE ?";
            String queryteachername = "SELECT * FROM class INNER JOIN teacher on class.teacher_id = teacher.id INNER JOIN school on teacher.school_id = school.id WHERE school.principal_id = "+ User.principal_id +" and (teacher.first_name LIKE ? or teacher.middle_name LIKE ? or teacher.last_name LIKE ? or teacher.prefix LIKE ?)";                             
            String querysubjectname = "SELECT * FROM class INNER JOIN subject on class.subject_id = subject.id INNER JOIN school on subject.school_id = school.id WHERE school.principal_id = "+ User.principal_id +" and (subject.Title LIKE ? or subject.subTitle LIKE ?)";
            String querysy = "SELECT * FROM class INNER JOIN subject on class.subject_id = subject.id INNER JOIN school on subject.school_id = school.id WHERE school.principal_id = "+ User.principal_id +" and (class.Start_year LIKE ? or class.end_year LIKE ?)";
            String Query = "";
            try{
                String search_name = searchcombo.getSelectedItem().toString().toLowerCase();

                switch(search_name){
                    case "id" -> {
                        Query = queryclassid;
                    }
                    case "teacher" -> {
                        Query = queryteachername;
                    }
                    case "subject" -> {
                        Query = querysubjectname;
                    }
                    case "school year" -> {
                        Query = querysy;
                    }
                }

                ///List all of principals
                MConnection mc = new MConnection();
                Statement stmt = mc.getStmt();
                Connection conn = mc.getConn();
                PreparedStatement pst = conn.prepareStatement(Query);
                if(search_name.equals("teacher")){
                    String tofind = "%"+search.getText()+"%";
                    pst.setString(1, tofind);
                    pst.setString(2, tofind);
                    pst.setString(3, tofind);
                    pst.setString(4, tofind);
                }
                else if(search_name.equals("id")){
                    String tofind = "%"+search.getText()+"%";
                    pst.setString(1, tofind);
                }
                else if(search_name.equals("subject")){
                    String tofind = "%"+search.getText()+"%";
                    pst.setString(1, tofind);
                    pst.setString(2, tofind);
                }
                else if(search_name.equals("school year")){
                    String[] sy = search.getText().split(" to ");
                    if(sy.length > 2){
                        pst.setString(1, "%" + sy[0] + "%");
                        pst.setString(2, "%" + sy[0] + "%");
                    } else if(sy.length == 2){
                        querysy = "SELECT * FROM class INNER JOIN subject on class.subject_id = subject.id INNER JOIN school on subject.school_id = school.id WHERE school.principal_id = "+ User.principal_id +" and (class.Start_year LIKE ? and class.end_year LIKE ?)";
                        pst = conn.prepareStatement(querysy);
                        pst.setString(1, "%" + sy[0] + "%");
                        pst.setString(2, "%" + sy[1] + "%");
                    } else{
                        querysy = "SELECT * FROM class INNER JOIN subject on class.subject_id = subject.id INNER JOIN school on subject.school_id = school.id WHERE school.principal_id = "+ User.principal_id +" and class.Start_year LIKE ?";
                        pst = conn.prepareStatement(querysy);
                        String year = "";
                        if(search.getText().length() > 4)
                            year = search.getText().substring(0, 4);
                        else
                            year = search.getText();
                        pst.setString(1, "%" + year + "%");
                    }
                }
                ResultSet rs = pst.executeQuery();
                DefaultTableModel model = (DefaultTableModel)table.getModel();
                int rowCount = model.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    model.removeRow(i);
                }
                /// display all result at the table
                    while(rs.next()){
                    int id = rs.getInt("ID");
                    String teacher_name = User.getName("teacher", rs.getInt("teacher_id"));
                    String subject_name = User.getName("subject", rs.getInt("subject_id"));
                    String days = rs.getString("Schedule_day");
                    String start_t = rs.getString("start_time");
                    String end_t = rs.getString("end_time");
                    int start_y = rs.getInt("Start_year");
                    int end_y = rs.getInt("End_year");
                    model.addRow(new Object[]{id, teacher_name, subject_name, days, start_t + " to " + end_t, start_y, end_y});
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }else updateTable();
        table.clearSelection();
        panel2.setVisible(false);
        aname.setText("Click here to assign a teacher");
        aname.setText("Click here to assign a subject");
        txt1.setText("");
        txt2.setText("");
        starty.setText("");
        endy.setText("");
        m.setSelected(false);
        t.setSelected(false);
        w.setSelected(false);
        th.setSelected(false);
        f.setSelected(false);
        s.setSelected(false);
    }//GEN-LAST:event_searchKeyReleased

    private void searchcomboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_searchcomboItemStateChanged
        if(searchcombo.getSelectedItem().toString().equals("school year"))
            search.setText("TYPE THE YEAR ONLY TO SEARCH");
    }//GEN-LAST:event_searchcomboItemStateChanged

    private void subsubject1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subsubject1ActionPerformed
        this.setVisible(false);
        new School_subject().setVisible(true);
    }//GEN-LAST:event_subsubject1ActionPerformed

    private void anameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_anameMouseClicked
        this.enable(false);
       
        final JTable table1 = new JTable();
       
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

        javax.swing.JTextField search1 = new javax.swing.JTextField("SEARCH");
        KeyListener akl = new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }


            @Override
            public void keyReleased(KeyEvent e) {
                table1.clearSelection();
                String Query = "";
                if(!search.getText().equals("")){
                    Query = "SELECT * FROM teacher INNER JOIN school on teacher.school_id = school.id WHERE school.principal_id = "+ User.principal_id +" and (teacher.id LIKE ? or teacher.first_name LIKE ? or teacher.middle_name LIKE ? or teacher.last_name LIKE ? or teacher.prefix LIKE ?)";
                    try{
                        ///List all of principals
                        MConnection mc = new MConnection();
                        Statement stmt = mc.getStmt();
                        Connection conn = mc.getConn();
                        PreparedStatement pst = conn.prepareStatement(Query);
                        String tofind = "%"+search1.getText()+"%";
                        pst.setString(1, tofind);
                        pst.setString(2, tofind);
                        pst.setString(3, tofind);
                        pst.setString(4, tofind);
                        pst.setString(5, tofind);
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
                }else {
                    try{
                        ///List all of adviser
                        MConnection mc = new MConnection();
                        Connection conn = mc.getConn();
                        ResultSet rs = null;
                        String QUERY = "";
                        QUERY = "SELECT * FROM teacher INNER JOIN school on teacher.school_id = school.id  WHERE school.principal_id = " +User.principal_id;
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
                }


            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        
        };
        
        search1.addKeyListener(akl);
        
        FocusListener fl = new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
                search1.setForeground(new Color(0, 0, 0));
                search1.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(search1.getText().equals("")){
                    search1.setForeground(new Color(153, 153, 153));
                    search1.setText("SEARCH");
                }
            }
        };
        search1.addFocusListener(fl);
        
        table1.setShowVerticalLines(false);
        //jScrollPane18.setViewportView(table1);

        if (table1.getColumnModel().getColumnCount() > 0) {
            table1.getColumnModel().getColumn(0).setPreferredWidth(10);
            table1.getColumnModel().getColumn(1).setPreferredWidth(300);
        }
        
        try{
            ///List all of adviser
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            String QUERY = "";
            QUERY = "SELECT * FROM teacher INNER JOIN school on teacher.school_id = school.id  WHERE school.principal_id = " +User.principal_id;
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

        final JFrame frame = new JFrame();
        final JScrollPane jps = new JScrollPane(table1);
        final JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        JButton b1 = new JButton("CONFIRM");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        
        
        JButton deselect = new JButton("CANCEL");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        
        panel.add(search1, c);
        panel.add(b1);
        panel.add(deselect);
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.getContentPane().add(jps, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);

        b1.setVisible(false);
        
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                
                int id = -1, row = -1;
                row = table1.getSelectedRow();
                id = (int) table1.getModel().getValueAt(row, 0);
                aname.setText(User.getName("teacher", id ));
                aname.setForeground(new Color(0,0,0));
                ena(true);
                frame.setVisible(false);
            }
           
        };
        b1.addActionListener(actionListener);
        
        ActionListener actionListener1 = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ena(true);
                frame.setVisible(false);
            }
        };
        deselect.addActionListener(actionListener1);

        
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b1.setVisible(true);
            }
        });
        
        frame.pack();
        frame.setVisible(true);
    
    }//GEN-LAST:event_anameMouseClicked

    private void subjMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subjMouseClicked
        this.enable(false);
       
        final JTable table1 = new JTable();
       
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

        javax.swing.JTextField search1 = new javax.swing.JTextField("SEARCH");
        KeyListener akl = new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }


            @Override
            public void keyReleased(KeyEvent e) {
                table1.clearSelection();
                String Query = "";
                if(!search.getText().equals("")){
                    Query = "SELECT * FROM subject INNER JOIN school on subject.school_id = school.id WHERE school.principal_id = "+ User.principal_id +" and (subject.id LIKE ? or subject.title LIKE ? or subject.subTitle LIKE ?)";
                    try{
                        ///List all of principals
                        MConnection mc = new MConnection();
                        Statement stmt = mc.getStmt();
                        Connection conn = mc.getConn();
                        PreparedStatement pst = conn.prepareStatement(Query);
                        String tofind = "%"+search1.getText()+"%";
                        pst.setString(1, tofind);
                        pst.setString(2, tofind);
                        pst.setString(3, tofind);
                        ResultSet rs = pst.executeQuery();
                        DefaultTableModel model = (DefaultTableModel)table1.getModel();
                        int rowCount = model.getRowCount();
                        for (int i = rowCount - 1; i >= 0; i--) {
                            model.removeRow(i);
                        }
                        /// display all result at the table
                        while(rs.next()){
                            int SelectedId = rs.getInt("ID");
                            String name = User.getName1("subject", SelectedId);
                            model.addRow(new Object[]{SelectedId, name});
                        }
                    } catch (SQLException ex) {
                        System.out.print(ex);
                    }
                }else {
                    try{
                        ///List all of adviser
                        MConnection mc = new MConnection();
                        Connection conn = mc.getConn();
                        ResultSet rs = null;
                        String QUERY = "";
                        QUERY = "SELECT * FROM subject INNER JOIN school on subject.school_id = school.id WHERE school.principal_id = "+ User.principal_id;
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
                            String name = User.getName1("subject", SelectedId);
                            model.addRow(new Object[]{SelectedId, name});
                        }
                        rs.close();
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }


            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        
        };
        
        search1.addKeyListener(akl);
        
        FocusListener fl = new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
                search1.setForeground(new Color(0, 0, 0));
                search1.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(search1.getText().equals("")){
                    search1.setForeground(new Color(153, 153, 153));
                    search1.setText("SEARCH");
                }
            }
        };
        search1.addFocusListener(fl);
        
        table1.setShowVerticalLines(false);
        //jScrollPane18.setViewportView(table1);

        if (table1.getColumnModel().getColumnCount() > 0) {
            table1.getColumnModel().getColumn(0).setPreferredWidth(10);
            table1.getColumnModel().getColumn(1).setPreferredWidth(300);
        }
        
        try{
            ///List all of adviser
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            String QUERY = "";
            QUERY = "SELECT * FROM subject INNER JOIN school on subject.school_id = school.id WHERE school.principal_id = "+ User.principal_id;
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
                String name = User.getName1("subject", SelectedId);
                model.addRow(new Object[]{SelectedId, name});
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }

        final JFrame frame = new JFrame();
        final JScrollPane jps = new JScrollPane(table1);
        final JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        JButton b1 = new JButton("CONFIRM");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        
        
        JButton deselect = new JButton("CANCEL");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        
        panel.add(search1, c);
        panel.add(b1);
        panel.add(deselect);
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.getContentPane().add(jps, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);

        b1.setVisible(false);
        
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                
                int id = -1, row = -1;
                row = table1.getSelectedRow();
                id = (int) table1.getModel().getValueAt(row, 0);
                subj.setText(User.getName("subject", id ));
                subj.setForeground(new Color(0,0,0));
                ena(true);
                frame.setVisible(false);
            }
           
        };
        b1.addActionListener(actionListener);
        
        ActionListener actionListener1 = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ena(true);
                frame.setVisible(false);
            }
        };
        deselect.addActionListener(actionListener1);

        
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b1.setVisible(true);
            }
        });
        
        frame.pack();
        frame.setVisible(true);
    
    }//GEN-LAST:event_subjMouseClicked

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
            java.util.logging.Logger.getLogger(School_class.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(School_class.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(School_class.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(School_class.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new School_class().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aname;
    private javax.swing.JButton confirm;
    private javax.swing.JButton create;
    private javax.swing.JButton delete;
    private javax.swing.JButton deselect;
    private javax.swing.JButton edit;
    private javax.swing.JTextField endy;
    private javax.swing.JRadioButton f;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton m;
    private javax.swing.JMenu menu1;
    private javax.swing.JMenu menu2;
    private javax.swing.JPanel panel2;
    private javax.swing.JRadioButton s;
    private javax.swing.JTextField search;
    private javax.swing.JComboBox<String> searchcombo;
    private javax.swing.JTextField starty;
    private javax.swing.JMenuItem subclass1;
    private javax.swing.JTextField subj;
    private javax.swing.JMenuItem substudent1;
    private javax.swing.JMenuItem subsubject1;
    private javax.swing.JMenuItem subteacher1;
    private javax.swing.JRadioButton t;
    private javax.swing.JTable table;
    private javax.swing.JPanel tablepanel2;
    private javax.swing.JRadioButton th;
    private com.raven.swing.TimePicker timePicker1;
    private com.raven.swing.TimePicker timePicker2;
    private javax.swing.JTextField txt1;
    private javax.swing.JTextField txt2;
    private javax.swing.JRadioButton w;
    // End of variables declaration//GEN-END:variables
}
