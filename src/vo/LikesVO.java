package vo;

public class LikesVO {
    private int postNo;
    private String nName;
    private String id;

    public LikesVO(int postNo, String nName, String id) {
        this.postNo = postNo;
        this.nName = nName;
        this.id = id;
    }


    public LikesVO() {}

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
