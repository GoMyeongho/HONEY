package dao;

import common.Common;
import vo.UsersVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 가희씨가 사용할 DAO 자바 파일
public class UsersDAO {
    static Connection conn = null;
    static Statement stmt = null;
    PreparedStatement psmt = null;
    static ResultSet rs = null;
    static Scanner sc = null;
    public UsersDAO() {
        sc = new Scanner(System.in);
    }

    // !!!!!회원가입
    public void joinMember() {

        System.out.println("=====신규 회원 가입=====");
    // 아이디 생성
        while (true) {
            System.out.println("아이디는 8자 이상 16자 이하의 영문 및 숫자이어야 합니다. (중복 불가능)");
            System.out.print("아이디: ");
            String userID = noKor();
            List<String> IDList = new ArrayList<>();
            try {
                conn = Common.getConnection();  // 오라클 DB 연결
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT USERID FROM VM_LOGIN");
                while(rs.next()){
                    String ID = rs.getString("USERID");
                    IDList.add(ID);
                }
            } catch (Exception e){
                System.out.println(e + " 연결 실패");
            } finally {
                Common.close(rs);
                Common.close(stmt);
                Common.close(conn);
            }
            if (userID.length() >= 8 && userID.length() <= 16) {

            } else {
                System.out.println("아이디 생성 조건을 다시 확인 후 입력 해 주세요.");
                continue;
            }
            for (String e : IDList) {
                // if 중복된 아이디 확인
                if (userID.equals(e)){
                    System.out.println("이미 생성된 아이디 입니다.");
                    continue;
                }
            }
            break;
        }
        // 비밀번호 생성
        while (true) {
            System.out.println("비밀번호는 8자 이상 16자 이하의 영문 및 숫자이어야 합니다.");
            System.out.println("비밀번호는 특수문자 및 영문 대소문자가 모두 포함되어야 합니다.");
            System.out.print("비밀번호: ");
            String userPW = noKor();
            if (userPW.length() >= 8 && userPW.length() <= 16) {
                // if 비밀번호 특수문자 및 영문 대소문자 확인 및 한글이 포함되어있는지 확인
                break;
            } else {
                System.out.println("비밀번호 생성 조건을 다시 확인 후 입력 해 주세요.");
            }
        }
        // 닉네임 생성
        while (true) {
            System.out.println("(사이트)에서 사용하실 닉네임을 입력 해 주세요");
            System.out.println("닉네임은 한글 기준 8자 까지 그리고 영어, 숫자 기준 16자까지 가능하며 중복 불가능합니다.");
            System.out.print("닉네임: ");
            String nName = sc.next();
            List<String> nNameList = new ArrayList<>();
            try {
                conn = Common.getConnection();  // 오라클 DB 연결
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT USERID FROM !!NNAME!!");
                while(rs.next()){
                    String NName = rs.getString("NNAME");
                    nNameList.add(NName);
                }
            } catch (Exception e){
                System.out.println(e + " 연결 실패");
            } finally {
                Common.close(rs);
                Common.close(stmt);
                Common.close(conn);
            }
            if (nName.length() <= 8) {  // 바이트 기준
            } else {
                System.out.println("닉네임 생성 조건을 다시 확인 후 입력 해 주세요.");
                continue;
            }
            for (String e : nNameList) {
                // if 중복된 아이디 확인
                if (nNameList.equals(e)){
                    System.out.println("이미 생성된 닉네임 입니다.");
                    continue;
                }
            }
            break;
        }
        // 전화번호 입력
        while (true) {
            System.out.println("전화번호를 입력 해 주세요(010-0000-0000, 형태로 하이픈을 포함하여 입력 해 주세요)");
            System.out.print("전화번호: ");
            String phone = inputPhone();
            List<String> phonList = new ArrayList<>();
            try {
                conn = Common.getConnection();  // 오라클 DB 연결
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT USERID FROM VM_LOGIN");
                while(rs.next()){
                    String Phone = rs.getString("Phone");
                    phonList.add(Phone);
                }
            } catch (Exception e){
                System.out.println(e + " 연결 실패");
            } finally {
                Common.close(rs);
                Common.close(stmt);
                Common.close(conn);
            }
            if (phone.length() == 13) {
            } else {
                System.out.print("전화번호를 확인 후 다시 입력 해 주세요");
                continue;
            }
            for (String e : phonList) {
                // if 중복된 전화번호 확인
                if (phonList.equals(e)){
                    System.out.println("이미 가입된 번호 입니다.");
                    continue;
                }
            }
            break;
        }
        // 비밀번호 찾기 시 사용 할 질문
        while (true) {
            System.out.println("비밀번호를 찾을 시 사용 할 질문을 입력 해 주세요");
            System.out.println("질문에는 비밀번호가 포함되어 있으면 안되며 한글 기준 20자 이내로 입력 해 주세요.");
            System.out.print("질문 입력: ");
            String pwLOCK = sc.next();
            if (pwLOCK.length() <= 20) {
                continue;
            } else {
                System.out.print("질문 생성 조건을 확인 후 다시 입력 해 주세요.");
            }
            if(pwLOCK.contains(userPW) < 0) {
                break;
            }
        }
        while (true) {
            System.out.println("질문에 대한 키워드를 입력 해 주세요");
            System.out.println("키워드는 한글 기준 8자 이내로 입력 해 주세요");
            System.out.print("키워드: ");
            String pwKey = sc.next();
            if (pwKey.length() <= 8) {
            } else {
                System.out.print("키워드 생성 조건을 확인 후 다시 입력 해 주세요.");
                continue;
            }
            if(pwKey.contains(userPW) < 0) {
                break;
            }
        }
        String query = "INSERT INTO USERS (userID, userPW, nName, phone, updateDATE, pwLOCK, pwKey) " +
                "VALUES (userID, userPW, nName, phone, updateDATE, pwLOCK, pwKey)"; //?????

        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            int ret = stmt.executeUpdate(query);
            System.out.println("Return: " + ret);

        } catch (SQLException e) {
            System.out.println("회원가입에 실패하였습니다.");
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        }


    // !!!!!아이디 찾기
    public void findID() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=====ID 찾기=====");
        String phone;
        List<String> phonList = new ArrayList<>();
        try {
            conn = Common.getConnection();  // 오라클 DB 연결
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT USERID FROM VM_LOGIN");
            while(rs.next()){
                String Phone = rs.getString("Phone");
                phonList.add(Phone);
            }
        } catch (Exception e){
            System.out.println(e + " 연결 실패");
        } finally {
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        }
        while (true) {
            System.out.println("가입 시 사용했던 전화번호를 입력 해 주세요");
            System.out.println("전화번호는 010-0000-0000 형식으로 하이픈을 포함하여 입력 해 주세요");
            System.out.print("전화번호: ");
            phone = sc.next();
            if (phone.length() == 13) {
                // if 존재하는 전화번호인지 확인
                // 존재하지 않는 전화번호 입니다.

                // 아이디 끌어오기
                //
            } else {
                System.out.print("전화번호 입력 조건을 확인 후 다시 입력 해 주세요.");
                continue;
            }
            for (String e : phonList) {
                // if 중복된 전화번호 확인
                if (phonList.equals(e)){
                    System.out.print("아이디는 " + userID + "입니다.");
                    break;
                }
                else continue;
            }
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
    }


    // !!!!!!비밀번호 찾기
    public void findPW() {
        Scanner sc = new Scanner(System.in);
        List<String> IDList = new ArrayList<>();
        try {
            conn = Common.getConnection();  // 오라클 DB 연결
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT USERID FROM VM_LOGIN");
            while(rs.next()){
                String ID = rs.getString("USERID");
                IDList.add(ID);
            }
        } catch (Exception e){
            System.out.println(e + " 연결 실패");
        } finally {
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        }
        while (true) {
            System.out.println("가입한 아이디를 입력 해 주세요");
            System.out.print("아이디: ");
            System.out.println();
            String userID = noKor();
            if (userID.length() >= 8 && userID.length() <= 16) {
            } else {
                System.out.println("아이디 입력 조건을 다시 확인 해 주세요");
                continue;
            }
            for (String e : IDList) {
                if (IDList.equals(e)){
                    break;
                }
                else continue;
            }
        }
        while (true) {
            System.out.println("제시문: " + pwLOCK);
            System.out.println("키워드를 입력 해 주세요. 키워드는 한글 기준 8자 이하 입니다.");
            System.out.print("키워드: ");
            String pwKey = noKor();
            List<String> pwKeyList = new ArrayList<>();
            try {
                conn = Common.getConnection();  // 오라클 DB 연결
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT USERID FROM VM_LOGIN");
                while(rs.next()){
                    String pwKEY = rs.getString("pwKEY");
                    IDList.add(pwKEY);
                }
            } catch (Exception e){
                System.out.println(e + " 연결 실패");
            } finally {
                Common.close(rs);
                Common.close(stmt);
                Common.close(conn);
            }

            if (pwKey.length() <= 8) {

            } else {
                System.out.println("키워드 입력 조건을 다시 확인 해 주세요");
            } for (String e : pwKeyList) {
                if (pwKeyList.equals(e)){
                    break;
                }
                else continue;
            }
        }
        System.out.print("비밀번호는 " + userPW + "입니다.");
        // 비밀번호 끌어오기
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
    }



    // 한글이 들어가지 않는지 확인하는 메서드(존재할 시 null리턴)
    public String noKor() {
        String name = sc.next();
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) < 33 || name.charAt(i) > 126) return null;
        }
        return name;
    }

    // 숫자와 - 외의 문자가 들어가지 않는지 확인하는 메서드(존재할 시 null리턴)
    public String inputPhone() {
        String phone = sc.next();
        for (int i = 0; i < phone.length(); i++) {
            if (phone.charAt(i) >= 48 || phone.charAt(i) <= 57 || phone.charAt(i) == 45)
                return phone;
        }
        return null;
    }
}


// !!! 모두 중간 탈출 구간과 끝났을 때 다시 메인으로 돌아가도록 확인 필요