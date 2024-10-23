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
    public static List<UsersVO> joinMember() {
        List<UsersVO> list = new ArrayList<>();
        System.out.println("=====신규 회원 가입=====");
    try {
    // 아이디 생성
    while (true) {
        System.out.println("아이디는 8자 이상 16자 이하의 영문 및 숫자이어야 합니다. (중복 불가능)");
        System.out.print("아이디: ");
        String userID = sc.next();
        if (userID.length() >= 8 && userID.length() <= 16) {
            // 아이디가 중복 될 경우 다시 돌아가기?
            // 중복된 아이디입니다. 다시 확인 해 주세요
            break;
        } else {
            System.out.println("아이디 생성 조건을 다시 확인 후 입력 해 주세요.");
        }
    }
    // 비밀번호 생성
    while (true) {
        System.out.println("비밀번호는 8자 이상 16자 이하의 영문 및 숫자이어야 합니다.");
        System.out.println("비밀번호는 특수문자 및 영문 대소문자가 모두 포함되어야 합니다.");
        System.out.print("비밀번호: ");
        String userPW = sc.next();
        if (userPW.length() >= 8 && userPW.length() <= 16) {
            // 비밀번호 특수문자 및 영문 대소문자 확인하기
            break;
        } else {
            System.out.println("비밀번호 생성 조건을 다시 확인 후 입력 해 주세요.");
        }
    }
    // 닉네임 생성
    while (true) {
        System.out.println("(사이트)에서 사용하실 닉네임을 입력 해 주세요");
        System.out.println("닉네임은 한글 기준 8자 까지 가능하며 중복 불가능합니다.");
        System.out.print("닉네임: ");
        String nName = sc.next();
        if (nName.length() <= 8) {
            // 중복 확인 필요
            break;
        } else {
            System.out.println("닉네임 생성 조건을 다시 확인 후 입력 해 주세요.");
        }
    }
    // 전화번호 입력
    while (true) {
        System.out.println("전화번호를 입력 해 주세요(010-0000-0000, 형태로 하이픈을 포함하여 입력 해 주세요)");
        System.out.print("전화번호: ");
        String phone = sc.next();
        if (phone.length() == 13) {
            // 중복 확인 필요
            break;
        } else {
            System.out.print("전화번호를 확인 후 다시 입력 해 주세요");
        }
    }
    // 비밀번호 찾기 시 사용 할 질문
    while (true) {
        System.out.println("비밀번호를 찾을 시 사용 할 질문을 입력 해 주세요");
        System.out.println("질문에는 비밀번호가 포함되어 있으면 안되며 한글 기준 20자 이내로 입력 해 주세요.");
        System.out.print("질문 입력: ");
        String pwLOCK = sc.next();
        if (pwLOCK.length() <= 20) {
            // 비밀번호와 중복 확인 필요
            break;
        } else {
            System.out.print("질문 생성 조건을 확인 후 다시 입력 해 주세요.");
        }
    }
    while (true) {
        System.out.println("질문에 대한 키워드를 입력 해 주세요");
        System.out.println("키워드는 한글 기준 8자 이내로 입력 해 주세요");
        System.out.print("키워드: ");
        String pwKey = sc.next();
        if (pwKey.length() <= 8) {
            // 비밀번호 및 키워드 내용과 중복 확인 필요
            break;
        } else {
            System.out.print("키워드 생성 조건을 확인 후 다시 입력 해 주세요.");
        }

        }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            System.out.println("-----");
        }
        // 회원 리스트에 추가 필요
        return new UsersVO();   //
    }


    // !!!!!아이디 찾기
    public void findID() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=====ID 찾기=====");
        String phone;
        String userID;
        while (true) {
            System.out.println("가입 시 사용했던 전화번호를 입력 해 주세요");
            System.out.println("전화번호는 010-0000-0000 형식으로 하이픈을 포함하여 입력 해 주세요");
            System.out.print("전화번호: ");
            phone = sc.next();
            if (phone.length() == 13) {
                // if 존재하는 전화번호인지 확인
                // 존재하지 않는 전화번호 입니다.
                System.out.print("아이디는 " + userID + "입니다.");
                // 아이디 끌어오기 필요
                //
                break;
            } else {
                System.out.print("전화번호 입력 조건을 확인 후 다시 입력 해 주세요.");
            }
        }
    }


    // !!!!!!비밀번호 찾기
    public void findPW() {
        Scanner sc = new Scanner(System.in);
        String userID;
        String pwLOCK;
        String pwKey;
        String userPW;
        while (true) {
            System.out.println("가입한 아이디를 입력 해 주세요");
            System.out.print("아이디: ");
            System.out.println();
            userID = sc.next();
            if (userID.length() >= 8 && userID.length() <= 16) {
                // if 존재하는 아이디인지 확인
                //
                break;
            } else {
                System.out.println("아이디 입력 조건을 다시 확인 해 주세요");
            }
        }
        while (true) {
            System.out.println("제시문: " + pwLOCK);
            System.out.println("키워드를 입력 해 주세요. 키워드는 한글 기준 8자 이하 입니다.");
            System.out.print("키워드: ");
            pwKey = sc.next();
            if (pwKey.length() <= 8) {
                // 정답 확인
                break;
            } else {
                System.out.println("키워드 입력 조건을 다시 확인 해 주세요");
            }
        }
        System.out.print("비밀번호는 " + userPW + "입니다.");
        // 끌어오기 필요
    }
}


// !!! 모두 중간 탈출 구간과 끝났을 때 다시 메인으로 돌아가도록 확인 필요