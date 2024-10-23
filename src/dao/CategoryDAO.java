package dao;

import common.Common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();    // 카테고리를 Array에 담는다
        String sql = "SELECT CATE FROM CATEGORY";   // 카테고리 옵션 쿼리문

        try {   // 기존에 데이터베이스에 존재하는 카테고리 정보를 가져오기 위한 연결
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                categories.add(rs.getString("CATE"));
            }
        } catch (Exception e) {
            System.out.println("카테고리 연결 실패 하였습니다.");
        } finally {
            Common.close(stmt);
            Common.close(conn);
        }
        return categories;
    }
}
