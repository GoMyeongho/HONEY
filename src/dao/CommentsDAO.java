package dao;

import common.Common;
import java.sql.Date;
import vo.CommentsVO;
import vo.PostsVO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class CommentsDAO {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement psmt = null;
    ResultSet rs = null;
    Scanner sc = null;
    static LoginDAO loDAO = new LoginDAO();
    public CommentsDAO() {
        sc = new Scanner(System.in);
    }


    public List<CommentsVO> commentsSet (int postNo) {
        List<CommentsVO> set = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM VM_COMM WHERE POSTNO = '"+ postNo + "'");
            while (rs.next()) {
                int commNo = rs.getInt("COMMNO");
                int subNo = rs.getInt("SUBNO");
                String content = rs.getString("CONTENT");
                String userid = rs.getString("USERID");
                String author = loDAO.getName(userid);
                Date date = rs.getDate("DATE");

                set.add(new CommentsVO(commNo, subNo, postNo, author, userid, content, date));
            }
        }catch (Exception e) {
            System.out.println(e + "의 이유로 연결에 실패했습니다.");
        }finally {
            Common.close(rs);
            Common.close(psmt);
            Common.close(conn);
        }
        return set;
    }

    public HashSet<CommentsVO> myComments (String author) {
        HashSet<CommentsVO> set = new HashSet<>();
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement("SELECT * FROM VM_COMM WHERE NNAME = ?");
            psmt.setString(1,author);
            rs = psmt.executeQuery();
            while (rs.next()) {
                int commNo = rs.getInt("COMMNO");
                int subNo = rs.getInt("SUBNO");
                int postNo = rs.getInt("POSTNO");
                String userId = rs.getString("USERID");
                Date date = rs.getDate("DATE");

                set.add(new CommentsVO(commNo,subNo,postNo,author,userId,"content",date));
            }
        }catch (Exception e) {
            System.out.println(e + "의 이유로 연결에 실패했습니다.");
        }finally {
            Common.close(rs);
            Common.close(psmt);
            Common.close(conn);
        }
        return set;
    }

    public boolean addComment (CommentsVO vo) {
            String sql = "INSERT INTO COMMENTS (COMMNO, SUBNO, POSTNO, USERID, CCONTENT, CDATE) VALUES(?, ?, ?, ?, ?, SYSDATE)";
            try {
                conn = Common.getConnection();
                psmt = conn.prepareStatement(sql);
                psmt.setInt(1, vo.getCommNo());
                psmt.setInt(2, vo.getSubNo());
                psmt.setInt(3, vo.getPostNo());
                psmt.setString(4, vo.getUserId());
                psmt.setString(5, vo.getContent());
                psmt.executeUpdate();   // SQL 쿼리 실행
                return true;
            } catch (Exception e) {
                System.out.println(e + "글 작성에 실패 하였습니다.");
                return false;
            } finally { // psmt -> conn 순서로 데이터베이스 닫기
                Common.close(psmt);
                Common.close(conn);
            }
    }
    public boolean updateComment (CommentsVO vo) {
        String sql = "UPDATE COMMENTS SET CCONTENT = ?,  WHERE POSTNO = ? AND COMMNO = ? AND SUBNO = ?";
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(2, vo.getPostNo());
            psmt.setInt(3, vo.getCommNo());
            psmt.setInt(4, vo.getSubNo());
            psmt.setString(1, vo.getContent());
            psmt.executeUpdate();   // SQL 쿼리 실행
            return true;
        } catch (Exception e) {
            System.out.println(e + "연결에 실패 했습니다.");
            return false;
        } finally { // psmt -> conn 순서로 데이터베이스 닫기
            Common.close(psmt);
            Common.close(conn);
        }
    }
    public boolean deleteComment (CommentsVO vo) {
        String sql = "DELETE FROM COMMENTS WHERE POSTNO = ? and COMMNO = ? and SUBNO = ?";
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, vo.getPostNo());
            psmt.setInt(2, vo.getCommNo());
            psmt.setInt(3, vo.getSubNo());
            psmt.executeUpdate();   // SQL 쿼리 실행
            return true;
        } catch (Exception e) {
            System.out.println("글 작성에 실패 하였습니다.");
            return false;
        } finally { // psmt -> conn 순서로 데이터베이스 닫기
            Common.close(psmt);
            Common.close(conn);
        }
    }
    public int getMaxSubNo(CommentsVO vo) {
        String sql = "SELECT MAX(SUBNO) FROM COMMENTS WHERE POSTNO = ? and COMMNO = ? ";
        int maxNo;
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, vo.getPostNo());
            psmt.setInt(2, vo.getCommNo());
            psmt.executeQuery();
            while(rs.next()) maxNo = rs.getInt(1);
            return rs.getInt(1) + 1;
        } catch (Exception e){
            System.out.println(e + "의 이유로 연결실패");
            return -1;
        } finally {
            Common.close(rs);
            Common.close(psmt);
            Common.close(conn);
        }
    }
    public int getMaxCommNo(int postNo) {
        String sql = "SELECT MAX(SUBNO) FROM COMMENTS WHERE POSTNO = ?";
        int maxNo;
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, postNo);
            psmt.executeQuery();
            while(rs.next()) maxNo = rs.getInt(1);
            return rs.getInt(1) + 1;
        } catch (Exception e){
            System.out.println(e + "의 이유로 연결실패");
            return -1;
        } finally {
            Common.close(rs);
            Common.close(psmt);
            Common.close(conn);
        }
    }





}
