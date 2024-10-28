package dao;

import common.Common;
import vo.UsersVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static dao.UsersDAO.stmt;

// 진기씨가 사용할 DAO 자바파일
public class MyPageDAO {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    Scanner sc = new Scanner(System.in);
    UsersDAO uDao = new UsersDAO();

    ///////////////////////////////// 내 정보 관련 메소드 /////////////////////////////////

    // 회원정보 조회
    // 현재 로그인 된 회원 정보를 불러와서 UsersVO 객체에 담아주는 메소드
    public UsersVO currUserInfo(String userID) {
        UsersVO currUser = null;
        try {
            conn = Common.getConnection(); // 데이터 베이스에 연결
            String sql = "SELECT USERID, USERPW, NNAME, PHONE, UDATE, PWLOCK, PWKEY From USERS WHERE USERID = ?"; // 특정 USER_ID를 기준으로 사용자의 정보를 조회하는 쿼리
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userID); // userID는 첫번째 ?인 1을 의미
            rs = pstmt.executeQuery();
            if (rs.next()){
                String userPW = rs.getString("USERPW"); // 비밀번호
                String nName = rs.getString("NNAME"); // 닉네임
                String phone = rs.getString("PHONE"); // 휴대폰번호
                Date updateDATE = rs.getDate("UDATE"); // 가입날짜
                String pwLOCK = rs.getString("PWLOCK"); // 제시문
                String pwKey = rs.getString("PWKEY"); // 제시어

                currUser = new UsersVO(userID, userPW, nName, phone, updateDATE, pwLOCK, pwKey);

            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            // close() 메소드를 쓰는 이유 : 데이터베이스 자원을 안전하게 해제, 메모리 누수와 데이터베이스 연결부족을 방지하기 위함
            // -> 시스템 자원을 효율적으로 관리하고, 코드의 재사용성과 가독성을 높일 수 있다.
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
        System.out.println("닉네임 : " + cui.getNName());
        System.out.println("휴대폰번호 : " + cui.getPhone());
        System.out.println("가입 날짜 : " + cui.getUpdateDATE());
        System.out.println("제시문 : " + cui.getPwLOCK());
        System.out.println("제시어 : " + cui.getPwKey());
        System.out.println("=".repeat(24));
    }

    // 내 정보 수정
    public void usersUpdate(UsersVO cui, String userID) {
        // 중복확인을 위해 만들어둔 전체 회원정보를 리스트로 담아주는 selectUsersInfo 활용
        List<UsersVO> uv1 = selectUsersInfo();

        // 수정할 비밀번호 입력
        String userPW = "";
        while(true) {
            System.out.print("변경할 비밀번호(8자 이상 16자 이하)(기존 비밀번호 유지는 no 입력) : ");
            userPW = sc.next();

            Pattern passPattern1 = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~!@#\\$%\\^&\\*]).{8,16}$");
            // 정규식표현(비밀번호)
            // ^문자열 : 특정 문자열로 시작(시작점)
            // (?=.*[a-zA-Z]) : 영어 대문자 또는 소문자가 최소 한 번 포함되어야 함
            // (?=.*\\d) : 숫자가 최소 한 번 포함되어야 함
            // (?=.*\\W) : 특수 문자가 최소 한 번 포함되어야 함
            // .{8,16} : 전체 길이가 8자 이상 16자 이하이어야 함
            // 문자열$ : 특정 문자열로 끝남(종착점)
            Matcher passMatcher1 = passPattern1.matcher(userPW);

            if(userPW.equalsIgnoreCase("no")) break;
            else if (userPW.length() < 8) System.out.println("비밀번호는 8자 이상 입력해주세요.");
            else if (userPW.getBytes().length > 16) System.out.print("비밀번호는 16자 이하로 입력해주세요.");
            else if (!passMatcher1.find()) System.out.println("비밀번호는 8자 이상 16자 이하의 영문 대소문자, 숫자, 특수문자를 포함해야 합니다."); // passpattern1에 정의된 패턴과 일치하지 않는 경우
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
            if (uv1.stream().filter(n -> check.equals(n.getNName())).findAny().orElse(null) !=null) {
                System.out.println("이미 사용중인 닉네임입니다.");
                // 조건이 일치하면, 즉 값이 존재하면, 결과가 null이 아니라는 뜻으로, uv1에 존재하는 기존 닉네임과 check에 입력한 새로운 닉네임이 동일하다는 의미로서
                // null이 아니면 이미 사용 중인 닉네임으로 중복이되었음을 의미한다.
                // stream 메소드는 데이터의 흐름을 표현하는 추상적인 개념으로, 요소를 처리하는 다양한 메소드를 제공
                // filter 메소드는 스트림의 각 요소에 대해 조건을 적용하여, 조건을 만족하는 요소만을 포함하는 새로운 스트림을 생성
                // n -> check.equals(n.getNName()) : 람다 표현식. n : 어떤 객체, n.getPhone() : 그 객체의 전화번호 가져옴
                // check와 n.getPhone()의 값을 비교하여 같으면 true, 다르면 false를 반환
                // findAny() : 이 메소드는 스트림에서 조건을 만족하는 아무 요소나 찾아 반환. 만약 해당 조건을 만족하는 요소가 없다면, Optional 객체를 반환
                // orElse(null) : 이 메소드는 Optional 객체에 값이 없을 때, 즉 값이 존재하지 않는 경우 null을 반환하도록 설정합니다.
                // 이를 통해 다음과 같은 효과를 기대할 수 있습니다: 조건을 만족하는 객체가 존재하지 않을 경우 null을 반환하게 되므로, 후속 연산에서 null 체크를 통해 추가적인 처리를 할 수 있습니다.
                // !=null : 이 부분은 이전 연산의 결과가 null이 아닌지를 확인. 만약 null이 아니라면, 이는 조건에 맞는 객체가 존재함을 의미
            } else if (nName.equalsIgnoreCase("no")) {
                nName = cui.getNName();
                break;
            }
            else if (intN < 2) System.out.println("닉네임은 2자 이상으로 입력해주세요");
            else if (intN > 30) System.out.println("닉네임은 30자 이하로 입력해주세요");
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

        // 수정할 제시문 입력
        String pwLOCK = "";
        while (true) {
            System.out.println("변경할 제시문를 입력하세요 (기존 제시문 유지는 no 입력) : ");
            pwLOCK = sc.next();

            int intL = pwLOCK.getBytes().length;

            if(pwLOCK.equalsIgnoreCase("no")) {
                pwLOCK = cui.getPwLOCK();
                break;
            }
            else if (intL < 8) System.out.println("제시문는 8자 이상으로 입력해주세요.");
            else if (intL > 20) System.out.print("제시문는 20자 이하로 입력해주세요.");
            else break;
        }

        // 수정할 제시어 입력
        String pwKEY = "";
        while (true) {
            System.out.println("변경할 제시어를 입력하세요 (기존 제시어 유지는 no 입력) : ");
            pwKEY = sc.next();

            if(pwKEY.equalsIgnoreCase("no")) {
                pwKEY = cui.getPwKey();
                break;
            }
            else if (pwKEY.length() > 8) System.out.println("제시어는 8자 이하 입력해주세요.");
            else break;
        }

        ///////////////////////////////// SQL 쿼리문 /////////////////////////////////

        String sql = "";
        // 1. 비밀번호를 수정하지 않는 경우
        if (userPW.equalsIgnoreCase("no")) {
            sql = "UPDATE USERS SET NNAME=?, PHONE=?, PWLOCK=?, PWKey=? WHERE USERID = ?"; // 비밀번호를 제외한 사용자 정보를 업데이트하는 SQL쿼리
            try {
                conn = Common.getConnection(); // 메소드를 호출하여 데이터베이스 연결을 얻습니다.
                pstmt = conn.prepareStatement(sql); // 미리 준비된 SQL문 실행을 위해서 prepareStatement 객체를 생성, 쿼리의 ? 자리에 실제 값들을 나중에 바인딩할 수 있게 해줍니다.
                // 각 ? 자리에, 해당 변수 값을 순서대로 설정합니다.
                pstmt.setString(1, nName);  // 닉네임을 쿼리에 바인딩(첫번째 ?에 nName을 바인딩)
                pstmt.setString(2, phone);  // 전화번호를 쿼리에 바인딩
                pstmt.setString(3, pwLOCK); // PW_LOCK를 쿼리에 바인딩
                pstmt.setString(4, pwKEY);  // PW_KEY를 쿼리에 바인딩
                pstmt.setString(5, userID); // USER_ID을 쿼리에 바인딩
                pstmt.executeUpdate(); // 쿼리를 실행하고 데이터베이스에 업데이트를 적용
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else {
        // 2. 비밀번호를 수정하는 경우
            sql = "UPDATE USERS SET USERPW = ?, NNAME=?, PHONE=?, PWLOCK=?, PWKey=? WHERE USERID = ?"; // 비밀번호를 포함하여 모든 사용자 정보를 업데이트하는 SQL쿼리
            try{
                conn = Common.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, userPW);
                pstmt.setString(2, nName);
                pstmt.setString(3, phone);
                pstmt.setString(4, pwLOCK);
                pstmt.setString(5, pwKEY);
                pstmt.setString(6, userID);
                pstmt.executeUpdate(); // 쿼리를 실행하고 데이터베이스에 업데이트를 적용
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        Common.close(pstmt);
        Common.close(conn);
        System.out.println("회원정보 수정이 완료되었습니다.");
    }
    public List<UsersVO> selectUsersInfo() {
        List<UsersVO> list = new ArrayList<UsersVO>();
        String sql = "SELECT * From USERS";
        try {
            conn = Common.getConnection();  // 오라클 DB 연결
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String usersID = rs.getString("USERID");
                String usersPW = rs.getString("USERPW");
                String nName = rs.getString("NNAME");
                String phone = rs.getString("PHONE");
                String pwLock = rs.getString("PWLOCK");
                String pwKey = rs.getString("PWKEY");
                UsersVO vo = new UsersVO(usersID,usersPW, nName, phone, pwLock, pwKey);
                list.add(vo);
            }
        } catch (Exception e) {
            System.out.println(e + " 의 이유로 연결 실패");
        } finally {
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        }
        return list;
    }
}
