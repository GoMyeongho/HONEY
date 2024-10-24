package dao;

import common.Common;
import vo.PostsVO;

import java.sql.*;
import java.util.Scanner;

// 명호 사용 DAO 자바 파일
public class PostViewDAO {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement psmt = null;
    ResultSet rs = null;
    Scanner sc = null;
    public PostViewDAO() {
        sc = new Scanner(System.in);
    }

    public PostsVO viewPost(int postNo) {
        PostsVO post = new PostsVO();
        try {
            conn = Common.getConnection();  // 오라클 DB 연결
            stmt = conn.createStatement();
            String sql = "select * from VM_POST where POSTNO = '" + postNo + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                post.setPostno(rs.getInt("POSTNO"));
                post.setTitle(rs.getString("TITLE"));
                post.setContent(rs.getString("PCONTENT"));
                post.setAuthor(rs.getString("NNAME"));
                post.setDate(rs.getString("PDATE"));
                post.setCategory(rs.getString("CATE"));
                post.setUserID(rs.getString("USERID"));
            }
        }catch (Exception e) {
            System.out.println(e + " 의 이유로 연결 실패");
        } finally {
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        }
        return post;
    }
    public boolean updatePost(PostsVO vo) { // 글 작성시 쿼리 포맷
        // 글 작성 할때 시간이 자동으로 적용 되므로 쿼리 내에 SYSDATE 로 대체후 CATE 부분을 앞당겨서 입력 받음
        String sql = "UPDATE  POSTS SET TITLE = ?, PCONTENT = ?, CATEGORY = ? WHERE POSTNO = ?";
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, vo.getTitle());       // 글 제목 입력값 데이터베이스 전달
            psmt.setString(2, vo.getContent());     // 글 본문 입력값 데이터베이스 전달
            psmt.setString(3, vo.getCategory());    // 카테고리 입력값 데이터베이스 전달
            psmt.setInt(4, vo.getPostno());
            psmt.executeUpdate();   // SQL 쿼리 실행
            return true;
        } catch (Exception e) {
            System.out.println(e + "글 수정에 실패 하였습니다.");
            return false;
        } finally { // psmt -> conn 순서로 데이터베이스 닫기
            Common.close(psmt);
            Common.close(conn);
        }
    }
    public boolean deletePost(PostsVO vo) {
        String sql = "DELETE FROM POSTS WHERE POSTNO = ?";
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, vo.getPostno());
            psmt.executeUpdate();   // SQL 쿼리 실행
            return true;
        } catch (Exception e) {
            System.out.println(e + "글 삭제에 실패 하였습니다.");
            return false;
        } finally { // psmt -> conn 순서로 데이터베이스 닫기
            Common.close(psmt);
            Common.close(conn);
        }
    }



}
