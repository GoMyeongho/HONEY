package vo;


import java.sql.Date;

// 객체 멤버변수 설정
public class PostsVO {
    private int postNo;
    private String title;
    private String nickName;
    private String postContent;
    private Date postDate ;
    private String cate;

    public PostsVO(int postNo, String title, String nickName, String postContent, Date postDate, String cate) {
        this.postNo = postNo;
        this.title = title;
        this.nickName = nickName;
        this.postContent = postContent;
        this.postDate = postDate;
        this.cate = cate;
    }

    // 빈 생성자
    public PostsVO() {
    }

    public int getPostNo() {
        return postNo;
    }

    public String getTitle() {
        return title;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPostContent() {
        return postContent;
    }

    public Date getPostDate() {
        return postDate;
    }

    public String getCate() {
        return cate;
    }

    public void setPostNo(int postNo) {
        this.postNo = postNo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }
}
