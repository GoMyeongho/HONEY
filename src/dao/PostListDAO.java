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
    public List<PostsVO> selectPage(String value, int sel) {
        List<PostsVO> list = new ArrayList<PostsVO>();

        String[] sql = {"SELECT * From VM_POSTS_PAGE WHERE NNAME LIKE ?",
                "SELECT * From VM_POSTS_PAGE WHERE NNAME = ?",
                "SELECT * From VM_POSTS_PAGE WHERE TITLE LIKE ?",
                "SELECT * From VM_POSTS_PAGE WHERE CATE = ?",
                "SELECT * From VM_POSTS_PAGE WHERE POSTNO IN " +
                        "(SELECT POSTNO FROM LIKES WHERE NNAME = ?)",
                "SELECT vpp.POSTNO as POSTNO, TITLE, CATE, PDATE, c.NNAME as NNAME FROM VM_POST_PAGE vpp " +
                        "JOIN COMMENTS c ON vpp.POSTNO = c.POSTNO WHERE NNAME = ?"
        };
        try {
            conn = Common.getConnection();  // 오라클 DB 연결
            psmt = conn.prepareStatement(sql[sel]);
            psmt.setString(1, value);
            rs = psmt.executeQuery();
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
    public List<PostsVO> selectPage() {
        List<PostsVO> list = new ArrayList<PostsVO>();
        try {
            conn = Common.getConnection();  // 오라클 DB 연결
            stmt = conn.createStatement();
            rs = stmt.executeQuery(PostListDAO.allSearch);
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

    public final static String allSearch = "SELECT * FROM VM_POSTS_PAGE";



}