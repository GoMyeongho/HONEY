package vo;

import java.math.BigDecimal;
import java.sql.Date;

public class CommentsVO {
    private int postNo;
    private String nName;
    private String content;
    private Date cDate;
    private BigDecimal commNo;
    // 댓글 보이는 순서를 위해 추가한 값
    // 날짜순으로 하지 않는 이유는 대댓글 기능을 위해서

    // 날짜는 SYSDATE 를 사용할 것이기 때문에
    // SELECT 용은 cDate를 받지 않음
    public CommentsVO(int postNo, String nName, String content) {
        this.postNo = postNo;
        this.nName = nName;
        this.content = content;
    }

    // 출력은 날짜도 해줘야 하기 때문에 생성자에 cDate 추가함
    public CommentsVO(int postNo, String nName, String content, Date cDate) {
        this.postNo = postNo;
        this.nName = nName;
        this.content = content;
        this.cDate = cDate;
    }
    // 빈 생성자
    public CommentsVO() {}


    // Getter 와 Setter
    public int getPostNo() {
        return postNo;
    }

    public void setPostNo(int postNo) {
        this.postNo = postNo;
    }

    public String getnName() {
        return nName;
    }

    public void setnName(String nName) {
        this.nName = nName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getcDate() {
        return cDate;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }
}
