package vo;

public class PostsVO {
    private int postno;
    private String title;
    private String content;
    private String author;
    private String date;
    private String category;

    public PostsVO() {
    }
    // 글 작성용 생성자
    public PostsVO(String category, String author, String content, String title, int postno) {
        this.category = category;
        this.author = author;
        this.content = content;
        this.title = title;
        this.postno = postno;
    }
    // 페이지용 생성자
    public PostsVO(int postno, String title, String author, String category, String date) {
        this.category = category;
        this.author = author;
        this.title = title;
        this.date = date;
        this.postno = postno;
    }
    // 글 보여주기용 생성자
    public PostsVO(int postno, String title, String content, String author, String date, String category) {
        this.postno = postno;
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = date;
        this.category = category;
    }

    public int getPostno() {
        return postno;
    }

    public void setPostno(int postno) {
        this.postno = postno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }
}
