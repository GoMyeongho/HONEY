package dao;

import common.Common;
import vo.LikesVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Scanner;

public class LikesDAO {
    private final static String[] heart = {"♡", "♥"};
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement psmt = null;
    ResultSet rs = null;
    Scanner sc = null;
    public LikesDAO() {
        sc = new Scanner(System.in);
    }

    public HashSet<LikesVO> likeSet (String nName) {
        HashSet<LikesVO> set = new HashSet<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT POSTNO FROM LIKES WHERE NNAME = '"+ nName + "';");
            while (rs.next()) new LikesVO(rs.getInt("POSTNO"),nName);
        }catch (Exception e) {
            System.out.println(e + "의 이유로 연결에 실패했습니다.");
        }
        return set;
    }

    public HashSet<LikesVO> likeSet (int postNo) {
        HashSet<LikesVO> set = new HashSet<>();
        String sql = "SELECT NNAME FROM LIKES WHERE POSTNO = ?;";
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) new LikesVO(postNo, rs.getString("NNAME"));
        }catch (Exception e) {
            System.out.println(e + "의 이유로 연결에 실패했습니다.");
        }
        return set;
    }

    public boolean isLike(HashSet<LikesVO> set, String nName) {
        for (LikesVO vo : set) {
            if (vo.getnName() == nName) {
                return true;
            }
        }
        return false;
    }

    public String likeMark(HashSet<LikesVO> set, int POSTNO) {
        for (LikesVO vo : set) {
            if (vo.getPostNo() == POSTNO) {
                return heart[1];
            }
        }
        return heart[0];
    }

    public void cancelLike(int postNo, String nName) {
        String sql = "DELETE FROM LIKES WHERE NNAME = ? AND POSTNO = ?;";

        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, nName);
            psmt.setInt(2, postNo);
            psmt.executeUpdate();

        } catch (Exception e) {
            System.out.println(e + "취소 실패");
        }
        Common.close(psmt);
        Common.close(conn);
    }
    public void addLike(int postNo, String nName) {
        String sql = "INSERT INTO LIKES (NNAME, POSTNO) VALUES (?, ?);";
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, nName);
            psmt.setInt(2, postNo);
            psmt.executeUpdate();

        } catch (Exception e) {
            System.out.println(e + "추가 실패");
        }
        Common.close(psmt);
        Common.close(conn);
    }
}
