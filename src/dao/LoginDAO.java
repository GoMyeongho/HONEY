package dao;

import common.Common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginDAO {
    Connection conn;
    Statement stmt;
    ResultSet rs;

    public LoginDAO() {
    }

    public String login(String ID, String password) {
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM VM_LOGIN WHERE USERID = '"+ ID +"';");
            rs.next();
            if (rs.getString("PASSWORD").equals(password)) return rs.getString("NNAME");
            else return null;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
