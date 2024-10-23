package dao;

import common.Common;
import vo.UsersVO;

import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 진기씨가 사용할 DAO 자바파일
public class MyPageDAO {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);
    UsersDAO uDao = new UsersDAO();

    ///////////////////////////////// 내 정보 보기 관련 메소드 ////////////////////////////

    // 회원 정보 조회
    // 현재 로그인 된 회원 정보를 불러와서 UsersVO 객체에 담아주는 메소드
    public UsersVO currUserInfo(String userID) {
        UsersVO currUser = null;
        try {
            conn = Common.getConnection();
            String sql = "SELECT USER_ID, USER_PW, NNAME, PHONE, UPDATE_DATE, PW_LOCK, PW_KEY From USERS WHERE USER_ID = ?";
            pstmt = conn.prepareStatement(sql);
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
            e.getStackTrace();
        } finally {
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        }
        return currUser;
    }

    // 내 정보 보기

    // 매개변수로 currUserInfo(cui) 활용
    public void printUserInfo(UsersVO cui) {
        System.out.println("=".repeat(7) + "내 정보" + "=".repeat(7));
        System.out.println("아이디 : " + cui.getUserID());
        System.out.println("비밀번호 : " + cui.getUserPW());
        System.out.println("닉네임 : " + cui.getnName());
        System.out.println("휴대폰번호 : " + cui.getUpdateDATE());
        System.out.println("제시어 : " + cui.getPwLOCK());
        System.out.println("제시어 답 : " + cui.getPwKey());
        System.out.println("=".repeat(24));
    }

    // 내 정보 수정
    public void usersUpdate(UsersVO cui, String userID) {
        // 중복확인을 위해 만들어둔 전체 회원정보를 리스트로 담아주는 selectUsersInfo 활용
        List<UsersVO> uv1 = uDao.selectUsersInfo();

        // 수정할 비밀번호 입력
        String userPW = "";
        while(true) {
            System.out.print("변경할 비밀번호(8자 이상 20자 이하)(기존 비밀번호 유지는 no 입력) : ");
            userPW = sc.next();

            Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\\\d)(?=.*\\\\W).{8,20}$"); 
            // 영어 대문자 또는 소문자가 최소 한 번 포함되어야 함
            // 숫자가 최소 한 번 포함되어야 함
            // 특수 문자가 최소 한 번 포함되어야 함
            // 전체 길이가 8자 이상 20자 이하이어야 함
            Matcher passMatcher1 = passPattern1.matcher(userPW);

            if(userPW.equalsIgnoreCase("no")) break;
            else if (userPW.length() < 8) System.out.println("비밀번호는 8자 이상 입력해주세요.");
            else if (userPW.getBytes().length > 20) System.out.print("비밀번호는 20자 이하 영문자와 특수문자(&를 제외)로 입력해주세요.");
            else if (!passMatcher1.find()) System.out.println("비밀번호는 영문자, 숫자, 특수기호만 사용 할 수 있습니다."); // passpattern1에 정의된 패턴과 일치하지 않는 경우
            else if (userPW.indexOf('&') >= 0) System.out.println("&는 비밀번호로 사용할 수 없습니다.");
            else break;
        }

        // 수정할 닉네임 입력
        String nName;
        while(true) {
            System.out.print("변경할 닉네임을 입력하세요 (기존 닉네임 유지는 no 입력) : ");
            nName = sc.next();
            String check = nName;

            int intN = nName.getBytes().length;

            //중복 체크
            if (uv1.stream().filter(n -> check.equals(n.getnName())).findAny().orElse(null) !=null) {
                System.out.println("이미 사용중인 닉네임입니다.");
            } else if (nName.equalsIgnoreCase("no")) {
                nName = cui.getnName();
                break;
            }
            else if (intN < 2) System.out.println("닉네임은 2자 이상 입력해주세요");
            else if (intN > 30) System.out.println("닉네임은 30자 미만으로 입력해주세요");
            else break;
        }

        // 수정할 휴대폰번호 입력
        String phone = "";
        while (true) {
            System.out.println("변경할 휴대폰번호를 입력하세요 (기존 휴대폰번호 유지는 no 입력) : ");
            phone = sc.next();
            String check = phone;

            //중복 체크
            if (uv1.stream().filter(n -> check.equals(n.getPhone())).findAny().orElse(null) !=null){
                System.out.println("이미 사용중인 휴대폰번호입니다.");
            } else if (phone.equalsIgnoreCase("no")) {
                phone = cui.getPhone();
                break;
            }
            else if (phone.length() !=13) System.out.println("휴대폰번호는 (-)포함 13글자로 작성해주세요");
            else break;
        }

        // 수정할 제시어 문제 입력
        String pwLOCK = "";
        while (true) {
            System.out.println("변경할 제시어를 입력하세요 (기존 제시어 유지는 no 입력) : ");
            pwLOCK = sc.next();
            String check = pwLOCK;

            //중복 체크
            if (uv1.stream().filter(n -> check.equals(n.getPwLOCK())).findAny().orElse(null) !=null) {
                System.out.println("이미 사용중인 제시어입니다.");
            } else if (pwLOCK.equalsIgnoreCase("no")) {
                pwLOCK = cui.getPwLOCK();
                break;
            }
            else if (pwLOCK.length() < 8) System.out.println("제시어는 8자 이상 입력해주세요.");
            else if (pwLOCK.getBytes().length > 20) System.out.print("제시어는 20자 이하 영문자와 특수문자(&를 제외)로 입력해주세요.");
            else break;
        }

        // 수정할 제시어 답 입력
        String pwKEY = "";
        while (true) {
            System.out.println("변경할 제시어 답을 입력하세요 (기존 제시어 답 유지는 no 입력) : ");
            pwKEY = sc.next();
            String check = pwKEY;

            //중복체크
            if (uv1.stream().filter(n -> check.equals(n.getPwKey())).findAny().orElse(null) !=null) {
                System.out.println("이미 사용중인 제시어 답입니다.");
            } else if (pwKEY.equalsIgnoreCase("no")) {
                pwKEY = cui.getPwKey();
                break;
            }
            else if (pwKEY.length() > 8) System.out.println("제시어는 8자 이하 입력해주세요.");
            else break;
        }

        String sql = "";
        if (userPW.equalsIgnoreCase("no")) { // 비밀번호 수정 안하는 경우
            sql = "UPDATE USERS SET NNAME=?, PHONE=?, PW_LOCK=?, PW_Key=? WHERE USER_ID = ?";
            try {
                conn = Common.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, nName);
                pstmt.setString(2, phone);
                pstmt.setString(3, pwLOCK);
                pstmt.setString(4, pwKEY);
                pstmt.setString(5, userID);
                pstmt.executeUpdate();
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else { // 비밀번호 수정하는 경우
            sql = "UPDATE USERS SET USER_PW = ?, NNAME=?, PHONE=?, PW_LOCK=?, PW_Key=? WHERE USER_ID = ?";
            try{
                conn = Common.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, userPW);
                pstmt.setString(2, nName);
                pstmt.setString(3, phone);
                pstmt.setString(4, pwLOCK);
                pstmt.setString(5, pwKEY);
                pstmt.setString(6, userID);
                pstmt.executeUpdate();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        Common.close(pstmt);
        Common.close(conn);
        System.out.println("회원정보 수정이 완료되었습니다.");
    }
}


