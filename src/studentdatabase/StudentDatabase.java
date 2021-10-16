/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this tstulate file, choose Tools | Tstulates
 * and open the tstulate in the editor.
 */
package studentdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author nemishappy
 */
public class StudentDatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        /*String derbyEmbeddedDriver = "org.apache.derby.jdbc.EmbeddedDriver";
        String msAccessDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
        String msSQlDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String oracleDriver = "oracle.jdbc.driver.OracleDriver";*/
        String derbyClientDriver = "org.apache.derby.jdbc.ClientDriver";
        //String mySqlDriver = "com.mysql.cj.jdbc.Driver";
        //load driver
        Class.forName(derbyClientDriver);
        //Class.forName(mySqlDriver);
        //create connection
        /*
         * String url="jdbc:mysql://server[:port]/databaseName"; //for mySQL
         * String url="jdbc:derby:databaseName"; //for DerbyEmbedded
         * String url= "jdbc:odbc:Driver=:datasourceNameOfODBC" //for MS Accces
         * String url= "jdbc:sqlserver://server[:port]:database="databaseName" //for MS SQL Server 
         * String url= "jdbc:oracle:thin:@server:port:databaseName" //for Oracle
         */
        String url = "jdbc:derby://localhost:1527/student";
        //String url="jdbc:mysql://localhost:3306/student?serverTimezone=UTC";
        String user = "app";
        //String user = "root";
        String passwd = "app";
        //String passwd = "root";

        Connection con = DriverManager.getConnection(url, user, passwd);
        //create statement
        Statement stmt = con.createStatement();
        Student stu1 = new Student(1, "John", 3.99);
        Student stu2 = new Student(2, "Marry", 4.00);
        //insert
        insertStudent(stmt, stu1);
        insertStudent(stmt, stu2);
        //update
        stu1.setGPA(2.00);
        updateStudentGPA(stmt, stu1);
        //delete
        deleteStudent(stmt, stu1);
        //Student stu = getStudentById(stmt, 1);
        //stu.setGPA(12346);
        //updateStudentGPA(stmt, stu);
        //stu.setName("Jack");
        //updateStudentName(stmt, stu);
        //deleteStudent(stmt, stu);
        //Student stu3 = new Student(3,"Markus", 14578);
        //Student stu4 = new Student(4,"Mark", 24579);
        //insertStudentPreparedStatement(con, stu1);
        //insertStudentPreparedStatement(con, stu2);

        //Student stu = getStudentByIdPreparedStatement(con, 1);
        //stu.setName("Jack");
        //stu.setGPA(98765);
        //updateStudentNamePreparedStatement(con, stu);
        //updateStudentGPAPreparedStatement(con, stu);
        //deleteStudentPreparedStatement(con, stu);
        //ArrayList<Student> studentList = getAllStudent(con);
        //printAllStudent(studentList);
        //close connection
        stmt.close();
        con.close();
    }

    public static void printAllStudent(ArrayList<Student> studentList) {
        for (Student stu : studentList) {
            System.out.print(stu.getId() + " ");
            System.out.print(stu.getName() + " ");
            System.out.println(stu.getGPA() + " ");
        }
    }

    public static ArrayList<Student> getAllStudent(Connection con) throws SQLException {
        String sql = "select * from student order by id";
        PreparedStatement ps = con.prepareStatement(sql);
        ArrayList<Student> studentList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Student student = new Student();
            student.setId(rs.getInt("id"));
            student.setName(rs.getString("name"));
            student.setGPA(rs.getDouble("GPA"));
            studentList.add(student);
        }
        rs.close();
        return studentList;

    }

    public static Student getStudentById(Statement stmt, int id) throws SQLException {
        Student stu = null;
        String sql = "select * from student where id = " + id;
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            stu = new Student();
            stu.setId(rs.getInt("id"));
            stu.setName(rs.getString("name"));
            stu.setGPA(rs.getDouble("GPA"));
        }
        return stu;
    }

    public static void insertStudent(Statement stmt, Student stu) throws SQLException {
        /*String sql = "insert into student (id, name, GPA)" +
                     " values (5, 'Mark', 12345)";*/
        String sql = "insert into student (id, name, GPA)"
                + " values (" + stu.getId() + "," + "'" + stu.getName() + "'" + "," + stu.getGPA() + ")";
        int result = stmt.executeUpdate(sql);
        System.out.println("Insert " + result + " row");
    }

    public static void deleteStudent(Statement stmt, Student stu) throws SQLException {
        String sql = "delete from student where id = " + stu.getId();
        int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("delete " + result + " row");
    }

    public static void updateStudentGPA(Statement stmt, Student stu) throws SQLException {
        String sql = "update student set GPA  = " + stu.getGPA()
                + " where id = " + stu.getId();
        int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("update " + result + " row");
    }

    public static void updateStudentName(Statement stmt, Student stu) throws SQLException {
        String sql = "update student set name  = '" + stu.getName() + "'"
                + " where id = " + stu.getId();
        int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("update " + result + " row");
    }

    public static void insertStudentPreparedStatement(Connection con, Student stu) throws SQLException {
        String sql = "insert into student (id, name, GPA)"
                + " values (?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, stu.getId());
        ps.setString(2, stu.getName());
        ps.setDouble(3, stu.getGPA());
        int result = ps.executeUpdate();
        //display result
        System.out.println("Insert " + result + " row");
    }

    public static void deleteStudentPreparedStatement(Connection con, Student stu) throws SQLException {
        String sql = "delete from student where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, stu.getId());
        int result = ps.executeUpdate();
        //display result
        System.out.println("Delete " + result + " row");
    }

    public static void updateStudentGPAPreparedStatement(Connection con, Student stu) throws SQLException {
        String sql = "update student set GPA  = ? where id = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1, stu.getGPA());
        ps.setInt(2, stu.getId());
        int result = ps.executeUpdate();
        //display result
        System.out.println("update " + result + " row");
    }

    public static void updateStudentNamePreparedStatement(Connection con, Student stu) throws SQLException {
        String sql = "update student set name  = ? where id = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, stu.getName());
        ps.setInt(2, stu.getId());
        int result = ps.executeUpdate();
        //display result
        System.out.println("update " + result + " row");
    }

    public static Student getStudentByIdPreparedStatement(Connection con, int id) throws SQLException {
        Student stu = null;
        String sql = "select * from student where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            stu = new Student();
            stu.setId(rs.getInt("id"));
            stu.setName(rs.getString("name"));
            stu.setGPA(rs.getDouble("GPA"));
        }
        return stu;
    }

}
