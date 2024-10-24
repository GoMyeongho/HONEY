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

    public HashSet<LikesVO> likeSet (int postNo, String id) {
        HashSet<LikesVO> set = new HashSet<>();
        String sql = "SELECT NNAME FROM VM_LIKE WHERE POSTNO = ?";
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, postNo);
            rs = psmt.executeQuery();
            while (rs.next()) set.add(new LikesVO(postNo, rs.getString("NNAME"),id));
        }catch (Exception e) {
            System.out.println(e + "의 이유로 연결에 실패했습니다.");
        }
        return set;
    }

    public HashSet<LikesVO> likeSet (String name, String id) {
        HashSet<LikesVO> set = new HashSet<>();
        String sql = "SELECT POSTNO FROM VM_LIKE WHERE USERID = ?";
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, id);
            rs = psmt.executeQuery();
            while (rs.next()) set.add(new LikesVO(rs.getInt("POSTNO"), name, id));
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            Common.close(rs);
            Common.close(psmt);
            Common.close(conn);
        }
        return set;
    }

    public boolean isLike(HashSet<LikesVO> set, String nName) {
        for (LikesVO vo : set) {
            if (vo.getnName().equals(nName)) {
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

    public void cancelLike(int postNo, String userId) {
        String sql = "DELETE FROM LIKES WHERE USERID = ? AND POSTNO = ?";

        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, userId);
            psmt.setInt(2, postNo);
            psmt.executeUpdate();

        } catch (Exception e) {
            System.out.println(e + "취소 실패");
        }
        Common.close(psmt);
        Common.close(conn);
    }
    public void addLike(int postNo, String userId) {
        String sql = "INSERT INTO LIKES (USERID, POSTNO) VALUES (?, ?)";
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, userId);
            psmt.setInt(2, postNo);
            psmt.executeUpdate();

        } catch (Exception e) {
            System.out.println(e + "추가 실패");
        }
        Common.close(psmt);
        Common.close(conn);
    }
}
