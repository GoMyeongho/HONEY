package dao;

import common.Common;
import vo.PostsVO;

import java.sql.Connection;
import java.sql.PreparedStatement;


//국형씨가 사용할 DAO 자바 파일
public class PostMakeDAO {
    Connection conn = null;
    PreparedStatement psmt = null;

    public PostMakeDAO() {
    }

    public boolean postInsert(PostsVO vo) { // 글 작성시 쿼리 포맷
        // 글 작성 할때 시간이 자동으로 적용 되므로 쿼리 내에 SYSDATE 로 대체후 CATE 부분을 앞당겨서 입력 받음
        String sql = "INSERT INTO POSTS(POSTNO, TITLE, NNAME, PCONTERNT, PDATE, CATE) VALUES(POSTS_SEQ.NEXTVAL,?,?,?,SYSDATE,?)";
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(sql);
            //psmt.setInt(1, vo.getPostno());                   // 시퀀스 쿼리 사용으로 필요없음
            psmt.setString(1, vo.getTitle());       // 글 제목 입력값 데이터베이스 전달
            psmt.setString(2, vo.getAuthor());      // 글 쓴이 입력값 데이터베이스 전달
            psmt.setString(3, vo.getContent());     // 글 본문 입력값 데이터베이스 전달
            //psmt.setDate(5, vo.getDate());                    // DATE 또한 쿼리로 받기 때문에 필요 없음
            psmt.setString(4, vo.getCategory());    // 카테고리 입력값 데이터베이스 전달
            psmt.executeUpdate();   // SQL 쿼리 실행
            System.out.println("글 작성을 완료 하였습니다.");
            return true;
        } catch (Exception e) {
            System.out.println("글 작성에 실패 하였습니다.");
            return false;
        } finally { // psmt -> conn 순서로 데이터베이스 닫기
            Common.close(psmt);
            Common.close(conn);
        }
    }

}

