package vo;

import java.sql.Date;

public class UsersVO {
    private String userID;
    private String userPW;
    private String nName;
    private String phone;
    private Date updateDATE;
    private String pwLOCK;
    private String pwKey;

    // 생성자
    public UsersVO(String userID, String userPW, String nName, String phone, Date updateDATE, String pwLOCK, String pwKey) {
        this.userID = userID;
        this.userPW = userPW;
        this.nName = nName;
        this.phone = phone;
        this.updateDATE = updateDATE;
        this.pwLOCK = pwLOCK;
        this.pwKey = pwKey;
    }

    // 빈 생성자
    public UsersVO() {}

    // getter
    public String getUserID() {
        return userID;
    }

    public String getUserPW() {
        return userPW;
    }

    public String getnName() {
        return nName;
    }

    public String getPhone() {
        return phone;
    }

    public Date getUpdateDATE() {
        return updateDATE;
    }

    public String getPwLOCK() {
        return pwLOCK;
    }

    public String getPwKey() {
        return pwKey;
    }

    // setter
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserPW(String userPW) {
        this.userPW = userPW;
    }

    public void setnName(String nName) {
        this.nName = nName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUpdateDATE(Date updateDATE) {
        this.updateDATE = updateDATE;
    }

    public void setPwLOCK(String pwLOCK) {
        this.pwLOCK = pwLOCK;
    }

    public void setPwKey(String pwKey) {
        this.pwKey = pwKey;
    }

    testtesttesttest
}
