package controller;

import dao.UsersDAO;
import java.util.Scanner;

public class UsersController {
    private UsersDAO usersDAO;
    private Scanner scanner;

    public UsersController() {
        usersDAO = new UsersDAO();
        scanner = new Scanner(System.in);
    }

    public void mainMenu() {    // 이름 변경 필요
        while (true) {
            System.out.println("===== 메뉴 =====");
            System.out.println("1. 회원가입");
            System.out.println("2. 아이디 찾기");
            System.out.println("3. 비밀번호 찾기");
            System.out.println("4. 종료");
            System.out.print("메뉴를 선택하세요: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    JoinUser();
                    break;
                case 2:
                    findUserID();
                    break;
                case 3:
                    findUserPassword();
                    break;
                case 4:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해 주세요.");
            }
        }
    }

    // 회원가입
    public void JoinUser() {
        System.out.println("===== 회원가입 =====");
        try {
            usersDAO.joinMember();
            System.out.println("회원가입이 완료되었습니다.");
        } catch (Exception e) {
            System.out.println(e + "오류가 발생했습니다.");
        }
    }

    // 아이디 찾기
    public void findUserID() {
        System.out.println("===== 아이디 찾기 =====");
        try {
            usersDAO.findID();
        } catch (Exception e) {
            System.out.println(e + "오류가 발생했습니다.");
        }
    }

    // 비밀번호 찾기
    public void findUserPassword() {
        System.out.println("===== 비밀번호 찾기 =====");
        try {
            usersDAO.findPW();
        } catch (Exception e) {
            System.out.println(e + "오류가 발생했습니다.");
        }
    }
}
