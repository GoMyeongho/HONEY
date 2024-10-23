package dao;

import common.Common;
import java.sql.Date;
import vo.CommentsVO;

import java.math.BigDecimal;
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

    public HashSet<CommentsVO> commentsSet (int postNo) {
        HashSet<CommentsVO> set = new HashSet<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM COMMENTS WHERE POSTNO = '"+ postNo + "';");
            while (rs.next()) {
                BigDecimal commNo = rs.getBigDecimal("COMMNO");
                String content = rs.getString("CONTENT");
                String author = rs.getString("NNAME");
                Date date = rs.getDate("DATE");

                set.add(new CommentsVO(commNo,postNo,author,content,date));
            }
        }catch (Exception e) {
            System.out.println(e + "의 이유로 연결에 실패했습니다.");
        }
        return set;
    }

    public HashSet<CommentsVO> myComments (String author) {
        HashSet<CommentsVO> set = new HashSet<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM COMMENTS WHERE NNAME = '"+ author + "';");
            while (rs.next()) {
                BigDecimal commNo = rs.getBigDecimal("COMMNO");
                int postNo = rs.getInt("NNAME");
                Date date = rs.getDate("DATE");

                set.add(new CommentsVO(commNo,postNo,author,"content",date));
            }
        }catch (Exception e) {
            System.out.println(e + "의 이유로 연결에 실패했습니다.");
        }
        return set;
    }



}
