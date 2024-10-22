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
            String sql = "select * from POSTS where POSTNO = '" + postNo + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                post.setPostno(rs.getInt("POSTNO"));
                post.setTitle(rs.getString("TITLE"));
                post.setContent(rs.getString("CONTENT"));
                post.setAuthor(rs.getString("NNAME"));
                post.setDate(rs.getString("PDATE"));
                post.setCategory(rs.getString("CATE"));
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

}
