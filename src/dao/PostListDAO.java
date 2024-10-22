package dao;

import common.Common;
import vo.PostsVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostListDAO {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement psmt = null;
    ResultSet rs = null;
    Scanner sc = null;
    public PostListDAO() {
        sc = new Scanner(System.in);
    }

    // 공통 페이지 조회 기능 구현
    public List<PostsVO> selectPage(String sql) {
        List<PostsVO> list = new ArrayList<PostsVO>();
        try {
            conn = Common.getConnection();  // 오라클 DB 연결
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int postNo = rs.getInt("POSTNO");
                String title = rs.getString("TITLE");
                String category = rs.getString("CATE");
                String date = rs.getString("PDATE");
                String author = rs.getString("NNAME");
                PostsVO vo = new PostsVO(postNo, title, author, category, date);
                list.add(vo);
            }
        } catch (Exception e) {
            System.out.println(e + " 의 이유로 연결 실패");
        } finally {
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        }
        return list;
    }
    public String authorSearch(String author) {
        return "SELECT * From VM_POSTS_PAGE WHERE NNAME LIKE '%" + author + "%';";
    }
    public String myPostsSearch(String nName) {
        return "SELECT * From VM_POSTS_PAGE WHERE NNAME = '" + nName + "';";
    }
    public String titleSearch(String title) {
        return "SELECT * From VM_POSTS_PAGE WHERE TITLE LIKE '%" + title + "%';";
    }
    public String categorySearch(String category) {
        return "SELECT * From VM_POSTS_PAGE WHERE CATE = '" + category + "';";
    }
    public String LikeSearch(String nName) {
        return "SELECT * From VM_POSTS_PAGE WHERE POSTNO IN " +
                "(SELECT POSTNO FROM LIKES " +
                "WHERE NNAME = '" + nName + "';";
    }
    public final static String allSearch = "SELECT * From VM_POSTS_PAGE";

}
