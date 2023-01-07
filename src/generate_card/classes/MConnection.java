package generate_card.classes;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import generate_card.forms.Admin;
import java.sql.*;                  ///Import all for sql
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;


/**
 *
 * @author Marvin
 */


public class MConnection {
    
    ///Principal
    String insertPrincipal = "INSERT INTO `principal` (`id`, `first_name`, `middle_name`, `last_name`, `prefix`, `age`, `gender`, `bdate`, `mobile_no`, `email`, `address`, `username`, `password`, `picture`, `admin_id`) VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    String editPrincipal = "UPDATE `principal` SET `first_name` = ?, `middle_name` = ?, `last_name` = ?, `prefix` = ?, `age` = ?, `gender` = ?, `bdate` = ?, `mobile_no` = ?, `email` = ?, `address` = ?, `username` = ?, `password` = ?, `picture` = ? WHERE `principal`.`id` = ?";
    
    ///School
    String insertSchool = "INSERT INTO `school` (`id`, `name`, `address`, `mission`, `vision`, `core_values`, `principal_id`, `admin_id`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?);";
    String editSchool = "UPDATE `school` SET `name` = ?, `address` = ?, `mission` = ?, `vision` = ?, `core_values` = ?, `principal_id` = ? WHERE `school`.`id` = ?;";
    
    ///Teacher
    String insertTeacher = "INSERT INTO `teacher` (`id`, `first_name`, `middle_name`, `last_name`, `prefix`, `age`, `gender`, `bdate`, `mobile_no`, `email`, `address`, `username`, `password`, `picture`, `grade`, `section`, `isAdviser`, `school_id`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String editTeacher = "UPDATE `teacher` SET `first_name` = ?, `middle_name` = ?, `last_name` = ?, `prefix` = ?, `age` = ?, `gender` = ?, `bdate` = ?, `mobile_no` = ?, `email` = ?, `address` = ?, `username` = ?, `password` = ?, `picture` = ?, `grade` = ?, `section` = ?, `isAdviser` = ? WHERE `teacher`.`id` = ?";
    
    ///Subject
    String insertSubject = "INSERT INTO `subject` (`ID`, `title`, `subTitle`, `Credits_Earned`, `Curriculum_Year`, `school_id`) VALUES (NULL, ?, ?, ?, ?, ?)";
    String editSubject = "UPDATE `subject` SET `title` = ?, `subTitle` = ?, `Credits_Earned` = ?, `Curriculum_Year` = ? WHERE `subject`.`ID` = ?";
    
    ///Class
    String insertClass = "INSERT INTO `class` (`id`, `Schedule_day`, `start_time`, `end_time`, `subject_id`, `teacher_id`, `Start_year`, `End_year`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
    String editClass = "UPDATE `class` SET `Schedule_day` = ?, `start_time` = ?, `end_time` = ?, `subject_id` = ?, `teacher_id` = ?, `Start_year` = ?, `End_year` = ? WHERE `class`.`id` = ?";
    
    //Student
    String insertStudent = "INSERT INTO `student` (`LRN`, `first_name`, `middle_name`, `Last_name`, `prefix`, `mobile_number`, `email`, `gender`, `age`, `address`, `Date_of_Birth`, `Place_of_Birth`, `Guardian_first_name`, `Guardian_middle_name`, `Guardian_last_name`, `Guardian_prefix`, `Guardian_occupation`, `Guardian_Address`, `Guardian_mobile_number`, `year`, `adviser_id`, `Curriculum`, `username`, `password`, `picture`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String editStudent = "UPDATE `student` SET `LRN` = ?, `first_name` = ?, `middle_name` = ?, `Last_name` = ?, `prefix` = ?, `mobile_number` = ?, `email` = ?, `gender` = ?, `age` = ?, `address` = ?, `Date_of_Birth` = ?, `Place_of_Birth` = ?, `Guardian_first_name` = ?, `Guardian_middle_name` = ?, `Guardian_last_name` = ?, `Guardian_prefix` = ?, `Guardian_occupation` = ?, `Guardian_Address` = ?, `Guardian_mobile_number` = ?, `year` = ?, `adviser_id` = ?, `Curriculum` = ?, `username` = ?, `password` = ?, `picture` = ? WHERE `student`.`LRN` = ?";
        //For school only
    String insertStudent1 = "INSERT INTO `student` (`LRN`, `first_name`, `middle_name`, `Last_name`, `prefix`, `mobile_number`, `email`, `gender`, `age`, `address`, `Date_of_Birth`, `Place_of_Birth`, `Guardian_first_name`, `Guardian_middle_name`, `Guardian_last_name`, `Guardian_prefix`, `Guardian_occupation`, `Guardian_Address`, `Guardian_mobile_number`, `icc`, `General_Average`, `total_num`, `year`, `adviser_id`, `Curriculum`, `username`, `password`, `picture`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String editStudent1 = "UPDATE `student` SET `LRN` = ?, `first_name` = ?, `middle_name` = ?, `Last_name` = ?, `prefix` = ?, `mobile_number` = ?, `email` = ?, `gender` = ?, `age` = ?, `address` = ?, `Date_of_Birth` = ?, `Place_of_Birth` = ?, `Guardian_first_name` = ?, `Guardian_middle_name` = ?, `Guardian_last_name` = ?, `Guardian_prefix` = ?, `Guardian_occupation` = ?, `Guardian_Address` = ?, `Guardian_mobile_number` = ?, `icc` = ?, `General_Average` = ?, `total_num` = ?, `year` = ?, `adviser_id` = ?, `Curriculum` = ?, `username` = ?, `password` = ?, `picture` = ? WHERE `student`.`LRN` = ?";
    
    //Staff
    String insertStaff = "INSERT INTO `staff` (`id`, `first_name`, `middle_name`, `Last_name`, `prefix`, `username`, `password`, `school_id`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
    String editStaff = "UPDATE `staff` SET `first_name` = ?, `middle_name` = ?, `Last_name` = ?, `prefix` = ?, `username` = ?, `password` = ? WHERE `staff`.`id` = ?";
    
    ///Get the connection between program to mysql
    public Connection getConn() {
        return conn;
    }
    ///Set the connection between program to mysql
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    ///Get statement
    public Statement getStmt() {
        return stmt;
    }
    ///Set statement
    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }
    
    ///sql variables
    private static final String DB_URL = "jdbc:mysql://localhost:3306/report_card";
    private static final String USER = "root";
    private static final String PASS = "";
    private Connection conn = null;
    private Statement stmt = null;
    
    /// function to connect program into mysql           Constructor
    public MConnection(){
        try {
            this.conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
            this.stmt = this.conn.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame(), "Cannot connect to the server", "Dialog", JOptionPane.ERROR_MESSAGE);
        } 
    }
    
                                                /**     function to insert/edit the following table     **/
    ///Principal function
    public void insertOrEditPrincipal(String decision, int id, String fname, String mname, String lname, String prefix, int agey, String gender, String birth_date, String mnumber, String email, String address, String username, String password,  byte[] person_image){
        PreparedStatement pst = null;
        try {
            if(decision.equals("hire")){
                pst=conn.prepareStatement(insertPrincipal);
                pst.setInt(14, User.id);
            }
            else {
                pst=conn.prepareStatement(editPrincipal);
                pst.setInt(14, id);
            }
            pst.setString(1, fname);
            pst.setString(2, mname);
            pst.setString(3, lname);
            pst.setString(4, prefix);
            pst.setInt(5, agey);
            pst.setString(6, gender);
            pst.setString(7, birth_date+"");
            pst.setString(8, mnumber);
            pst.setString(9, email);
            pst.setString(10, address);
            pst.setString(11, username);
            pst.setString(12, password);
            pst.setBytes(13, person_image);
            pst.execute();
            conn.close();
            stmt.close();
            pst.close();
        } catch (Exception e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(new JFrame(), "Cannot connect to the server", "Dialog", JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    
    
    ///School function
    public void insertOrEditSchool(String decision, String name, String barangay, String city, String province, String region, String country, String mission, String vision, String core_values, int principal_id){
        try {
            PreparedStatement pst = null;
            if(decision.equals("build")){
                pst=conn.prepareStatement(insertSchool);
                pst.setInt(7, User.id);
            }
            else {pst=conn.prepareStatement(editSchool);}
            pst.setString(1, name);
            pst.setString(2, barangay + "," + city + "," + province + "," + region + "," + country);
            pst.setString(3, mission);
            pst.setString(4, vision);
            pst.setString(5, core_values);
            pst.setInt(6, principal_id);
            pst.execute();
            conn.close();
            stmt.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    ///teacher function
    public void insertOrEditTeacher(String decision, String fname, String mname, String lname, String pre_fix, int agey, String gender, String birth_date, String mnumber, String email, String address, String username, String password,  byte[] person_image, int grade, String section, Boolean isAdviser, int id){
        try {
            PreparedStatement pst = null;
            if(decision.equals("hire")) pst=conn.prepareStatement(insertTeacher);
            
            else pst=conn.prepareStatement(editTeacher);
            pst.setString(1, fname);
            pst.setString(2, mname);
            pst.setString(3, lname);
            pst.setString(4, pre_fix);
            pst.setInt(5, agey);
            pst.setString(6, gender);
            pst.setString(7, birth_date);
            pst.setString(8, mnumber);
            pst.setString(9, email);
            pst.setString(10, address);
            pst.setString(11, username);
            pst.setString(12, password);
            pst.setBytes(13, person_image);
            pst.setInt(14, grade);
            pst.setString(15, section);
            pst.setBoolean(16, isAdviser);
            pst.setInt(17, id);
            pst.execute();
            conn.close();
            stmt.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    ///Student function
    public void insertOrEditStudent(String whereLrn, String lrn, String first_name, String middle_name, String last_name, String prefix, String mobile_no, String email, String gender, int age, String address, String dob, String pob, String gfname, String gmname, String glname, String gprefix, String go, String ga, String gmn, int y, int ai, String curriculum, String username, String password, byte[] pic, String decision){
        try {
            PreparedStatement pst = null;
            if(decision.equals("insert"))   pst=conn.prepareStatement(insertStudent);
            else {
                pst=conn.prepareStatement(editStudent);
                pst.setString(26, whereLrn);
            }
            pst.setString(1, lrn);
            pst.setString(2, first_name);
            pst.setString(3, middle_name);
            pst.setString(4, last_name);
            pst.setString(5, prefix);
            pst.setString(6, mobile_no);
            pst.setString(7, email);
            pst.setString(8, gender);
            pst.setInt(9, age);
            pst.setString(10, address);
            pst.setString(11, dob);
            pst.setString(12, pob);
            pst.setString(13, gfname);
            pst.setString(14, gmname);
            pst.setString(15, glname);
            pst.setString(16, gprefix);
            pst.setString(17, go);
            pst.setString(18, ga);
            pst.setString(19, gmn);
            pst.setInt(20, y);
            pst.setInt(21, ai);
            pst.setString(22, curriculum);
            pst.setString(23, username);
            pst.setString(24, password);
            pst.setBytes(25, pic);
            pst.execute();
            conn.close();
            stmt.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void insertOrEditStudent(String whereLrn, String lrn, String first_name, String middle_name, String last_name, String prefix, String mobile_no, String email, String gender, int age, String address, String dob, String pob, String gfname, String gmname, String glname, String gprefix, String go, String ga, String gmn, String past_school, Float general_average, int total_num, int y, int ai, String curriculum, String username, String password, byte[] pic, String decision){
        try {
            PreparedStatement pst = null;
            if(decision.equals("insert"))   pst=conn.prepareStatement(insertStudent1);
            else {
                pst=conn.prepareStatement(editStudent1);
                pst.setString(29, whereLrn);
            }
            pst.setString(1, lrn);
            pst.setString(2, first_name);
            pst.setString(3, middle_name);
            pst.setString(4, last_name);
            pst.setString(5, prefix);
            pst.setString(6, mobile_no);
            pst.setString(7, email);
            pst.setString(8, gender);
            pst.setInt(9, age);
            pst.setString(10, address);
            pst.setString(11, dob);
            pst.setString(12, pob);
            pst.setString(13, gfname);
            pst.setString(14, gmname);
            pst.setString(15, glname);
            pst.setString(16, gprefix);
            pst.setString(17, go);
            pst.setString(18, ga);
            pst.setString(19, gmn);
            pst.setString(20, past_school);
            pst.setFloat(21, general_average);
            pst.setInt(22, total_num);
            pst.setInt(23, y);
            pst.setInt(24, ai);
            pst.setString(25, curriculum);
            pst.setString(26, username);
            pst.setString(27, password);
            pst.setBytes(28, pic);
            pst.execute();
            conn.close();
            stmt.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    ///Subject function
    public void insertOrEditSubject(String title, String subTitle, float credits_earned, int curriculum_year, int id, String decision){
        try {
            PreparedStatement pst = null;
            if(decision.equals("insert"))   pst=conn.prepareStatement(insertSubject);
            else pst=conn.prepareStatement(editSubject);
            pst.setString(1, title);
            pst.setString(2, subTitle);
            pst.setFloat(3, credits_earned);
            pst.setInt(4, curriculum_year);
            pst.setInt(5, id);
            pst.execute();
            conn.close();
            stmt.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    ///Class function
    public void insertOrEditClass(String schedule_day, String startTime, String endTime, int subj_id, int teach_id, int start_year, int end_year, String decision, int id){
        try {///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////ID COULD BE NULL OR O IF THE DECIDED ACTION IS INSERT
            PreparedStatement pst = null;
            if(decision.equals("insert"))   pst=conn.prepareStatement(insertClass);
            else {pst=conn.prepareStatement(editClass);
                pst.setInt(6, id);
            }
            pst.setString(1, schedule_day);
            pst.setString(2, startTime);
            pst.setString(3, endTime);
            pst.setInt(4, subj_id);
            pst.setInt(5, teach_id);
            pst.setInt(6, start_year);
            pst.setInt(7, end_year);
            pst.execute();
            conn.close();
            stmt.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Insert or edit data into staff
    public void insertOrEditStaff(String first_name, String middle_name, String last_name, String prefix, String username, String password, String decision, int id){
        try {
            PreparedStatement pst = null;
            if(decision.equals("insert"))   pst=conn.prepareStatement(insertStaff);
            else pst=conn.prepareStatement(editStaff);
            pst.setString(1, first_name);
            pst.setString(2, middle_name);
            pst.setString(3, last_name);
            pst.setString(4, prefix);
            pst.setString(5, username);
            pst.setString(6, password);
            pst.setInt(7, id);
            pst.execute();
            conn.close();
            stmt.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    ///test if the username is valid upon creation
    public boolean usernameIsValid(String username){
        String[] userType = {"administrator", "principal", "teacher", "student", "staff"};
        Boolean flag = true;
        int i = 0;
        try{
            ResultSet rs = null;
            OUTER:
            for (i = 0; i < 5; i++) {
                String QUERY = "SELECT * FROM `"+ userType[i] +"` WHERE `username` LIKE '"+ username +"'";
                rs = this.stmt.executeQuery(QUERY);
                if(rs.next())  {
                    flag = false;
                    break; 
                }
            }
        }catch(Exception ex){ ex.printStackTrace();}
        return flag;
    }
    
    ///test if the username is valid upon editing
    public boolean usernameIsValid(String tableType, String username, int id){
        String[] userType = {"administrator", "principal", "teacher", "student", "staff"};
        Boolean flag = true;
        int i = 0;
        try{
            ResultSet rs = null;
            OUTER:
            for (i = 0; i < 5; i++) {
                String QUERY = "SELECT * FROM `"+ userType[i] +"` WHERE `username` LIKE '"+ username +"'";
                rs = this.stmt.executeQuery(QUERY);
                if(rs.next())  {
                    if(userType[i].equals(tableType)){
                            if(id != rs.getInt("id"))
                                flag = false;
                    }
                    else
                        flag = false;
                    break;
                }   
            }
        }catch(Exception ex){ ex.printStackTrace();}
        return flag;
    }
    
    ///test if the username is valid upon editing            (student table)
    public boolean usernameIsValid(String username, String lrn){
        String[] userType = {"administrator", "principal", "teacher", "student"};
        Boolean flag = true;
        int i = 0;
        try{
            ResultSet rs = null;
            OUTER:
            for (i = 0; i < 4; i++) {
                String QUERY = "SELECT * FROM `"+ userType[i] +"` WHERE `username` LIKE '"+ username +"'";
                rs = this.stmt.executeQuery(QUERY);
                if(rs.next())  {
                    if(userType[i].equals("student")){
                            if(lrn.equals(rs.getString("lrn")))
                                flag = true;
                            else flag = false;
                    }
                    else
                        flag = false;
                    break;
                }   
            }
        }catch(Exception ex){ ex.printStackTrace();}
        return flag;
    }

    
    
    
    
}
