package dao;

import common.Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginDAO {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement psmt = null;
    ResultSet rs = null;

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
        }finally {
            Common.close(rs);
            Common.close(psmt);
            Common.close(conn);
        }
        return null;
    }
    public String getName(String userId) {
        String sql = "select * from VM_LOGIN where USERID = ? ";
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,userId);
            rs = psmt.executeQuery();
            while (rs.next()) return rs.getString("NNAME");
            return null;
        }catch (Exception e) {
            System.out.println(e);
        }finally {
            Common.close(rs);
            Common.close(psmt);
            Common.close(conn);
        }
        return null;
    }

}
