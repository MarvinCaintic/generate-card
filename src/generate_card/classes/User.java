package generate_card.classes;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import generate_card.forms.Admin;
import generate_card.forms.Teacher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marvin
 */



public class User {
    public static String username = "";
    public static String password = "";
    public static String userType = "";
    public static int id = -1;
    public static Boolean isStudent = false;
    public static String lrn = "";
    public static int admin_id;
    public static int principal_id;
    public static int school_id;
    public static int teacher_id;
    public static int subject_id;
    public static int student_id;
                                /*            Integer to Roman              A recursive function to get the roman numeral equivalent of the integer*/
    private static final int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
    private static final String[] romanLiterals = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
    
    public static final String integerToRoman(int number) {

            int i = getFloorIndex(number);
            if (number == values[i]) {
                    return romanLiterals[i];
            }

            return romanLiterals[i] + integerToRoman(number - values[i]);
    }

    private static int getFloorIndex(int number) {
            for (int i = 0; i < values.length; i++) {
                    while (number >= values[i]) {
                            return i;
                    }
            }
            return -1;
    }
                                   
    ///test if the mobile number is valid
    public static boolean isValidmobile(String s)
    {
 
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // The number should be of 11 digits.
 
        // Creating a Pattern class object
        Pattern p = Pattern.compile("^\\d{11}$");
 
        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression for which
        // object of Matcher class is created
        Matcher m = p.matcher(s);
 
        // Returning bollean value
        return (m.matches());
    }
    
    ///test if the email is valid
    public static boolean isValidEmail(String email) {
      String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
      return email.matches(regex);
   }
    
    ///get section of the adviser through its id
    public static String getSection(int id){
    String section = "";
        try{;
            ///List all of principals
            MConnection mc = new MConnection();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            String QUERY = "";
            QUERY = "SELECT * FROM teacher WHERE id = ? and isAdviser = true";
            PreparedStatement pst = conn.prepareStatement(QUERY);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while(rs.next()){   
                 section = rs.getString("section");
            }
            rs.close();
            conn.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return section;
    }
    
    ///function to login username at login form
    public static boolean login(String username, String password) throws SQLException{
        MConnection mc = new MConnection();
        Connection conn = mc.getConn();
        String ans = "false";
        String[] userType = {"administrator", "principal", "teacher", "student", "staff"};
        Boolean flag = false;
        int i = 0;
        try{
            OUTER:
            for (i = 0; i < 5; i++) {
                String QUERY = "SELECT * FROM `"+ userType[i] +"` WHERE `username` LIKE '"+ username +"' AND `password` LIKE '"+ password +"'";
                ResultSet rs = conn.prepareStatement(QUERY).executeQuery();
                if(!rs.next())  continue;
                switch (i) {
                    case 0 -> {                             /// login as admin
                        User.id = rs.getInt("id");          //set the user id
                        flag = true;
                        break OUTER;
                    }
                    case 1 -> {                             /// login as principal
                        User.id = rs.getInt("id");          //set the user id
                        flag = true;
                        
                        QUERY = "SELECT * FROM school WHERE principal_id = ?";
                        PreparedStatement pst = conn.prepareStatement(QUERY);
                        pst.setInt(1, User.id);
                        rs = pst.executeQuery();
                        /// display all result at the table
                        while(rs.next()){
                            User.principal_id = rs.getInt("principal_id");  //set principal id
                            User.school_id = rs.getInt("id");               //set school id
                        }
                        break OUTER;
                    }
                    case 2 -> {                             /// login as teacher
                        ///Search for the school first
                        User.id = rs.getInt("id");          //set the user id
                        flag = true;
                        QUERY = "SELECT * FROM school INNER JOIN teacher on school.id = teacher.school_id WHERE teacher.id = ?";
                        PreparedStatement pst = conn.prepareStatement(QUERY);
                        pst.setInt(1, User.id);
                        rs = pst.executeQuery();
                        /// display all result at the table
                        while(rs.next()){
                            User.principal_id = rs.getInt("principal_id");  //set principal id
                            User.school_id = rs.getInt("id");               //set school id
                        }
                        break OUTER;
                    }
                    case 3 -> {                             /// login as student
                        User.isStudent = true;
                        User.lrn = rs.getString("lrn");
                        flag = true;
                        QUERY = "SELECT * FROM school INNER JOIN teacher on school.id = teacher.school_id INNER JOIN student on teacher.id = student.adviser_id WHERE student.lrn = ?";
                        PreparedStatement pst = conn.prepareStatement(QUERY);
                        pst.setString(1, User.lrn);
                        rs = pst.executeQuery();
                        /// display all result at the table
                        while(rs.next()){
                            User.principal_id = rs.getInt("principal_id");  //set principal id
                            User.school_id = rs.getInt("id");               //set school id
                        }
                        QUERY = "SELECT * FROM teacher INNER JOIN student on teacher.id = student.adviser_id WHERE student.lrn = ?";
                        pst = conn.prepareStatement(QUERY);
                        pst.setString(1, User.lrn);
                        rs = pst.executeQuery();
                        /// display all result at the table
                        while(rs.next()){
                            User.teacher_id = rs.getInt("id");              //set teacher id
                        }
                        break OUTER;
                    }
                    case 4 -> {                             /// login as staff
                        User.id = rs.getInt("id");          //set the user id
                        flag = true;
                        
                        QUERY = "SELECT * FROM school WHERE id = ?";
                        PreparedStatement pst = conn.prepareStatement(QUERY);
                        pst.setInt(1, rs.getInt("school_id"));
                        rs = pst.executeQuery();
                        /// display all result at the table
                        while(rs.next()){
                            User.principal_id = rs.getInt("principal_id");  //set principal id
                            User.school_id = rs.getInt("id");               //set school id
                        }
                        break OUTER;
                    }
                    default -> {
                    }
                }
            }
        }catch(Exception ex){ ex.printStackTrace();
        }
        if(i >= 0 && i < 5)
            User.userType = userType[i];
        
        conn.close();
        return flag;
        
    }
    ///get the id of the teacher of subject using the id of the class where teacher is teaching in subject lesson
    public static int getTableId(String table, int id){
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
                    cid = rs1.getInt("subject_id");                 ///get the id of subject being tackled in class
                else if(table.equals("teacher"))
                    cid = rs1.getInt("teacher_id");                 /// get the id of teacher teaching in class
            }
            rs1.close();
            conn1.close();
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cid;
    }
    
    ///get the name of the table given the table type and the id
    public static String getName(String table_name, int id){
        //Declaration
        String ans = "";
        String QUERY = "";
        
        //choose the query with respect of the table type
        if(table_name.equals("principal"))
            QUERY = "SELECT * FROM principal WHERE id = ?";
        else if(table_name.equals("teacher"))
            QUERY = "SELECT * FROM teacher WHERE id = ?";
        else if(table_name.equals("subject"))
            QUERY = "SELECT * FROM subject WHERE id = ?";
        else if(table_name.equals("school"))
            QUERY = "SELECT * FROM school WHERE id = ?";
        try{
            //Start the connection
            MConnection mc = new MConnection();
            Statement stmt = mc.getStmt();
            Connection conn = mc.getConn();
            
            //Declaration of result set and prepared statement
            ResultSet rs = null;
            PreparedStatement pst = null;
            
            //Assign the selected query to the prepared statement
            pst = conn.prepareStatement(QUERY);
            //set the parameter
            pst.setInt(1, id);
            //execute the query and assign the result to a result set named rs
            rs = pst.executeQuery();
            //if there's such a table type with the id given then it will enter this if statement then chooose the name format with respect to the table type
            if(rs.next()){   
                ans = rs.getInt("id")+",";      // the name shall be putted after the id and comma
                if(table_name.equals("principal") || table_name.equals("teacher"))
                    ans = ans + rs.getString("first_name")+" "+rs.getString("middle_name")+" "+rs.getString("Last_name")+" "+rs.getString("prefix");          
                else if(table_name.equals("subject"))        ans = ans + rs.getString("title")+" ("+rs.getString("subTitle")+")";
                else    ans = ans + rs.getString("name");
            }
            rs.close();
            conn.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans; //return the name
    }
    
    ///Get the name of the student with given lrn
    public static String getName(String lrn){
        String ans = "";
        String QUERY =  "SELECT * FROM student WHERE lrn = ?";
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
                ans = ans + rs.getString("Last_name")+" "+rs.getString("prefix")+", "+rs.getString("first_name")+" "+rs.getString("middle_name").toUpperCase().charAt(0)+".";  
            }
            rs.close();
            conn.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }
    
    //Get the credits earned of the subject using the id of the subject
    public static float getCreditsEarned(int id){
        float ans = 0;
        String QUERY = "SELECT * FROM subject WHERE id = ?";
        try{
            ///List all of principals
            MConnection mc = new MConnection();
            Statement stmt = mc.getStmt();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            PreparedStatement pst = null;
            pst = conn.prepareStatement(QUERY);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while(rs.next()){   
                ans = rs.getFloat("Credits_Earned");
            }
            rs.close();
            conn.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }
    
    //Get the curriculum year of the subject given the id of the subject
    public static float getCurriculumYear(int id){
        float ans = 0;
        String QUERY = "SELECT * FROM subject WHERE id = ?";
        try{
            ///List all of principals
            MConnection mc = new MConnection();
            Statement stmt = mc.getStmt();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            PreparedStatement pst = null;
            pst = conn.prepareStatement(QUERY);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while(rs.next()){   
                ans = rs.getFloat("Curriculum_Year");
            }
            rs.close();
            conn.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }
    
    
    ///Get the name of the tabletype with respect to the given id           however, this method dont display id with the name
    public static String getName1(String table_name, int id){
        String ans = "";
        String QUERY = "";
        if(table_name.equals("principal"))
            QUERY = "SELECT * FROM principal WHERE id = ?";
        else if(table_name.equals("teacher"))
            QUERY = "SELECT * FROM teacher WHERE id = ?";
        else if(table_name.equals("student"))
            QUERY = "SELECT * FROM student WHERE id = ?";
        else if(table_name.equals("subject"))
            QUERY = "SELECT * FROM subject WHERE id = ?";
        else if(table_name.equals("school"))
            QUERY = "SELECT * FROM school WHERE id = ?";
        try{
            ///List all of principals
            MConnection mc = new MConnection();
            Statement stmt = mc.getStmt();
            Connection conn = mc.getConn();
            ResultSet rs = null;
            PreparedStatement pst = null;
            pst = conn.prepareStatement(QUERY);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while(rs.next()){   
                if(table_name.equals("principal") || table_name.equals("teacher") || table_name.equals("student"))
                    ans = ans + rs.getString("first_name")+" "+rs.getString("middle_name")+" "+rs.getString("Last_name")+" "+rs.getString("prefix");          
                else if(table_name.equals("subject"))        ans = ans + rs.getString("title")+" ("+rs.getString("subTitle")+")";
                else    ans = ans + rs.getString("name");
            }
            rs.close();
            conn.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }
    
    ///Recursive approach to delete the table and those table below it
    public static void deleteTable(String mainTable, String table_name, int id, String lrn){
        
        try {
            switch (table_name) {
                case "principal" ->                     {                               
                        MConnection mc = new MConnection();
                        Connection conn = mc.getConn();
                        String QUERY = "SELECT * FROM school WHERE principal_id = ?";
                        PreparedStatement pst = conn.prepareStatement(QUERY);
                        pst.setInt(1, id);
                        System.out.println(pst);
                        ResultSet rs = pst.executeQuery();
                        while(rs.next()){
                            deleteTable(mainTable, "school", rs.getInt("id"), "");
                        }       
                        rs.close();
                        conn.close();
                        pst.close();
                        
                        
                        ////
                        MConnection mc1 = new MConnection();
                        Connection conn1 = mc1.getConn();
                        QUERY = "DELETE FROM school where principal_id = ?";
                        PreparedStatement pst1 = conn1.prepareStatement(QUERY);
                        pst1.setInt(1, id);
                        System.out.println(pst1);
                        pst1.execute();
                        conn1.close();
                        pst1.close();
                        if(mainTable.equals(table_name)){
                            ////
                            MConnection mc4 = new MConnection();
                            Connection conn4 = mc4.getConn();
                            QUERY = "DELETE FROM principal where id = ?";
                            PreparedStatement pst4 = conn4.prepareStatement(QUERY);
                            pst4.setInt(1, id);
                            System.out.println(pst4);
                            pst4.execute();
                            conn4.close();
                            pst4.close();
                        }
                    break;
                    }
                case "school" ->                     {
                    
                    MConnection mcs = new MConnection();
                    Connection conns = mcs.getConn();
                    String QUERYs = "DELETE FROM staff where school_id = ?";
                    PreparedStatement psts = conns.prepareStatement(QUERYs);
                    psts.setInt(1, id);
                    psts.execute();
                    conns.close();
                    psts.close();

                    MConnection mc = new MConnection();
                    Connection conn = mc.getConn();
                    String QUERY = "SELECT * FROM teacher WHERE school_id = ?";
                    PreparedStatement pst = conn.prepareStatement(QUERY);
                    pst.setInt(1, id);
                    System.out.println(pst);
                    ResultSet rs = pst.executeQuery();
                    while(rs.next()){
                        deleteTable(mainTable, "teacher", rs.getInt("id"), "");
                    }       
                    rs.close();
                    conn.close();
                    pst.close();


                    ///
                    MConnection mc1 = new MConnection();
                    Connection conn1 = mc1.getConn();
                    QUERY = "DELETE FROM teacher where school_id = ?";
                    PreparedStatement pst1 = conn1.prepareStatement(QUERY);
                    pst1.setInt(1, id);
                    System.out.println(pst1);
                    pst1.execute();
                    conn1.close();
                    pst1.close();

                    ///
                    MConnection mc2 = new MConnection();
                    Connection conn2 = mc2.getConn();
                    QUERY = "SELECT * FROM subject WHERE school_id = ?";
                    PreparedStatement pst2 = conn2.prepareStatement(QUERY);
                    pst2.setInt(1, id);
                    System.out.println(pst2);
                    ResultSet rs2 = pst2.executeQuery();
                    while(rs2.next()){
                        deleteTable(mainTable, "subject", rs2.getInt("id"), "");
                    }   
                    rs2.close();
                    conn2.close();
                    pst2.close();
                    ////
                    MConnection mc3 = new MConnection();
                    Connection conn3 = mc3.getConn();
                    QUERY = "DELETE FROM subject where school_id = ?";
                    PreparedStatement pst3 = conn3.prepareStatement(QUERY);
                    pst3.setInt(1, id);
                    System.out.println(pst3);
                    pst3.execute();
                    conn3.close();
                    pst3.close();

                    if(mainTable.equals(table_name)){
                        ////
                        MConnection mc4 = new MConnection();
                        Connection conn4 = mc4.getConn();
                        QUERY = "DELETE FROM school where id = ?";
                        PreparedStatement pst4 = conn4.prepareStatement(QUERY);
                        pst4.setInt(1, id);
                        System.out.println(pst4);
                        pst4.execute();
                        conn4.close();
                        pst4.close();
                    }
                    break;
                    }
                case "teacher" ->                     {
                        MConnection mc = new MConnection();
                        Connection conn = mc.getConn();
                        String QUERY = "SELECT * FROM student WHERE adviser_id = ?";
                        PreparedStatement pst = conn.prepareStatement(QUERY);
                        pst.setInt(1, id);
                        System.out.println(pst);
                        ResultSet rs = pst.executeQuery();
                        while(rs.next()){
                            deleteTable(mainTable, "student", 0, rs.getString("lrn"));
                        }       
                        rs.close();
                        conn.close();
                        pst.close();
                        
                        ////
                        MConnection mc1 = new MConnection();
                        Connection conn1 = mc1.getConn();
                        QUERY = "DELETE FROM student where adviser_id = ?";
                        PreparedStatement pst1 = conn1.prepareStatement(QUERY);
                        pst1.setInt(1, id);
                        System.out.println(pst1);
                        pst1.execute();
                        conn1.close();
                        pst1.close();
                        
                        ////
                        MConnection mc2 = new MConnection();
                        Connection conn2 = mc2.getConn();
                        QUERY = "SELECT * FROM class WHERE teacher_id = ?";
                        PreparedStatement pst2 = conn2.prepareStatement(QUERY);
                        pst2.setInt(1, id);
                        System.out.println(pst2);
                        ResultSet rs2 = pst2.executeQuery();
                        while(rs2.next()){
                            deleteTable(mainTable, "class", rs2.getInt("id"), "");
                        }       
                        rs2.close();
                        conn2.close();
                        pst2.close();
                        
                        ////
                        MConnection mc3 = new MConnection();
                        Connection conn3 = mc3.getConn();
                        QUERY = "DELETE FROM class where teacher_id = ?";
                        PreparedStatement pst3 = conn3.prepareStatement(QUERY);
                        pst3.setInt(1, id);
                        System.out.println(pst3);
                        pst3.execute();
                        conn3.close();
                        pst3.close();
                        
                        
                        if(mainTable.equals(table_name)){
                            ////
                            MConnection mc4 = new MConnection();
                            Connection conn4 = mc4.getConn();
                            QUERY = "DELETE FROM teacher where id = ?";
                            PreparedStatement pst4 = conn4.prepareStatement(QUERY);
                            pst4.setInt(1, id);
                            System.out.println(pst4);
                            pst4.execute();
                            conn4.close();
                            pst4.close();
                        }
                    break;
                    }
                case "subject" -> {
                    if(mainTable.equals(table_name)){
                        MConnection mc = new MConnection();
                        Connection conn = mc.getConn();
                        String QUERY = "SELECT * FROM class WHERE subject_id = ?";
                        PreparedStatement pst = conn.prepareStatement(QUERY);
                        pst.setInt(1, id);
                        System.out.println(pst);
                        ResultSet rs = pst.executeQuery();
                        while(rs.next()){
                            deleteTable(mainTable, "class", rs.getInt("id"), "");
                        }
                        rs.close();
                        conn.close();
                        pst.close();
                        


                        ///
                        MConnection mc1 = new MConnection();
                        Connection conn1 = mc1.getConn();
                        QUERY = "DELETE FROM class where subject_id = ?";
                        PreparedStatement pst1 = conn1.prepareStatement(QUERY);
                        pst1.setInt(1, id);
                        System.out.println(pst1);
                        pst1.execute();
                        pst1.close();
                        
                        ////
                        MConnection mc4 = new MConnection();
                        Connection conn4 = mc4.getConn();
                        QUERY = "DELETE FROM school where id = ?";
                        PreparedStatement pst4 = conn4.prepareStatement(QUERY);
                        pst4.setInt(1, id);
                        System.out.println(pst4);
                        pst4.execute();
                        conn4.close();
                        pst4.close();
                    }
                    break;
                }
                case "student" ->                     {
                    MConnection mc = new MConnection();
                    Connection conn = mc.getConn();
                    String QUERY = "DELETE FROM grades where lrn = ?";
                    PreparedStatement pst = conn.prepareStatement(QUERY);
                    pst.setString(1, lrn);
                    System.out.println(pst);
                    pst.execute();
                    conn.close();
                    pst.close();
                    
                    if(mainTable.equals(table_name)){
                        ////
                        MConnection mc4 = new MConnection();
                        Connection conn4 = mc4.getConn();
                        QUERY = "DELETE FROM student where lrn = ?";
                        PreparedStatement pst4 = conn4.prepareStatement(QUERY);
                        pst4.setInt(1, id);
                        pst4.execute();
                        conn4.close();
                        pst4.close();
                    }
                    break;
                    }
                case "class" -> {
                    if(mainTable.equals(table_name)){
                        MConnection mc = new MConnection();
                        Connection conn = mc.getConn();
                        String QUERY = "DELETE FROM grades where class_id = ?";
                        PreparedStatement pst = conn.prepareStatement(QUERY);
                        pst.setInt(1, id);
                        pst.execute();
                        conn.close();
                        pst.close();
                        
                        
                        
                        
                        ////
                        MConnection mc4 = new MConnection();
                        Connection conn4 = mc4.getConn();
                        QUERY = "DELETE FROM class where id = ?";
                        PreparedStatement pst4 = conn4.prepareStatement(QUERY);
                        pst4.setInt(1, id);
                        System.out.println(pst4);
                        pst4.execute();
                        conn4.close();
                        pst4.close();
                    }
                    break;
                }
                default -> {
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    ///Get the age given the bdate
    public static int getAge(String bdate){
        //Assign the date now in today variable
        LocalDate today = LocalDate.now();
        //declaration of the bdate2 type Date which will get the Date formatted String bdate from the parameter
        java.util.Date bdate2 = null;
        try {       //Change the Date format into m-d-yy
            bdate2 = new SimpleDateFormat("M-D-YY").parse(bdate);
        } catch (ParseException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar c = Calendar.getInstance();
        c.setTime(bdate2);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        LocalDate birth_date = LocalDate.of(year, month, date);
        Period age = Period.between(birth_date, today);     //Get the period between the birth date and the current date
        return age.getYears();  // return the gap between birth date and current date in years
    }
    
    ///Update all the ages of the person
    public static void updateAllAge(){
        try {  //Assign all the possible select query which include table type as a person
            Vector<String> Query1 = new Vector<String>();
            Query1.add("Select * From principal WHERE 1");
            Query1.add("Select * From teacher WHERE 1");
            Query1.add("Select * From student WHERE 1");
            
            //Assign all the possible update query with the same index number to the first vector wth respect to the table type
            Vector<String> Query2 = new Vector<String>();
            Query2.add("UPDATE `principal` SET `age` = ? WHERE `principal`.`id` = ?");
            Query2.add("UPDATE `teacher` SET `age` = ? WHERE `teacher`.`id` = ?");
            Query2.add("UPDATE `student` SET `age` = ? WHERE `student`.`lrn` = ?");
            
            //Iterate all the select query to select or those person under principal, teacher and student category
            for(String selectQuery : Query1){
                //Start the connection between the program and mysql database
                MConnection mc = new MConnection();
                Statement stmt = mc.getStmt();
                Connection conn = mc.getConn();
                
                //Preapare statement and result set declaration and assignment to select the person with respect to the selectQuery 
                PreparedStatement pst;
                pst = conn.prepareStatement(selectQuery);
                ResultSet rsSelectQuery = pst.executeQuery();   //Execute the Query and assign the result to rsSelectQuery
                
                //repeat this method while there is still an 
                while(rsSelectQuery.next()){
                    //Start another connection between the program and mysql database but this time, to insert data in it
                    MConnection mcInsert = new MConnection();
                    Statement stmtInsert = mcInsert.getStmt();
                    Connection connInsert = mcInsert.getConn();
                    //Declaration of Preapared statement as well as the assinment of prepare statement witht the element at query to which same in index as the first query
                    PreparedStatement pstInsert = conn.prepareStatement(Query2.elementAt(Query1.indexOf(selectQuery)));
                    //if the table type is not a student since student's variable in mysql and the othoers variable in my sql for the birth date is different
                    if(Query1.indexOf(selectQuery) != 2){
                        pstInsert.setInt(1, getAge(rsSelectQuery.getString("bdate")+""));
                        pstInsert.setInt(2, rsSelectQuery.getInt("id"));
                    }
                    else{//if the table type is a student
                        pstInsert.setInt(1, getAge(rsSelectQuery.getString("Date_of_Birth")+""));
                        pstInsert.setString(2, rsSelectQuery.getString("lrn"));
                    }
                    
                    //Execute the query and insert the data
                    pstInsert.execute();
                    
                    //close the connection to insert data
                    connInsert.close();
                }
                //close the connection to select data as welll as the result set taken from the query
                conn.close();
                rsSelectQuery.close();
            }
        
        //Catch errors with relationship with mysql database
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
