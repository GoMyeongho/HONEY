package dao;

import common.Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Scanner;

public class CommentsDAO {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement psmt = null;
    ResultSet rs = null;
    Scanner sc = null;
    public CommentsDAO() {
        sc = new Scanner(System.in);
    }

    public HashSet<Integer> name2Comments(String nName) {
        HashSet<Integer> set = new HashSet<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT POSTNO FROM COMMENTS WHERE NNAME = '"+ nName + "';");
            while (rs.next()) set.add(rs.getInt("POSTNO"));
        }catch (Exception e) {
            System.out.println(e + "의 이유로 연결에 실패했습니다.");
        }
        return set;
    }



}
