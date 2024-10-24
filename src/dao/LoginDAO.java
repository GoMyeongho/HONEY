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
        String sql = "select * from VM_LOGIN where USERID = '" + ID + "'";
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            if (rs.getString("USERPW").equals(password)) return rs.getString("NNAME");
            else return null;
        }catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}
