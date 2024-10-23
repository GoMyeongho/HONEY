package dao;

import common.Common;
import vo.UsersVO;

import java.sql.*;
import java.util.Scanner;

// 진기씨가 사용할 DAO 자바파일
public class MyPageDAO {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);

    UsersDAO uDao = new UsersDAO();

    public UsersVO currUserInfo(String userID) {
        UsersVO currUser = null;
        try {
            con = Common.getConnection();
            String sql = "SELECT USER_ID, USER_PW, NNAME, PHONE, UPDATE_DATE, PW_LOCK, PW_Key = ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()){
                String userPW = rs.getString("USER_PW"); // 비밀번호
                String nName = rs.getString("NNAME"); // 닉네임
                String phone = rs.getString("PHONE"); // 휴대폰번호
                Date updateDATE = rs.getDate("UPDATE_DATE"); // 가입날짜
                String pwLOCK = rs.getString("PW_LOCK"); // 제시어 문제
                String pwKey = rs.getString("PW_KEY"); // 제시어 답

                currUser = new UsersVO(userID, userPW, nName, phone, updateDATE, pwLOCK, pwKey);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs)
        Common.close(pstmt);
        Common.close(con);
        return currUser;
    }

    // 내 정보 보기 출력
    // 매개변수로 currUserInfo(cui) 활용
    public void printUserInfo(UsersVO cui) {
        System.out.println("=".repeat(7) + "내 정보" + "=".repeat(7));
        System.out.println("아이디 : " + cui.getUserID());
        System.out.println("비밀번호 : " + cui.getUserPW());
        System.out.println("닉네임 : " + cui.getnName());
        System.out.println("휴대폰번호 : " + cui.getUpdateDATE());
        System.out.println("제시어 문제 : " + cui.getPwLOCK());
        System.out.println("제시어 답 : " + cui.getPwKey());
        System.out.println("=".repeat(24));






    }
