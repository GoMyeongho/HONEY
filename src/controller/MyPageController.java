package controller;

import dao.MyPageDAO;
import vo.UsersVO;

import java.util.Scanner;

public class MyPageController {
    private MyPageDAO myPageDAO = new MyPageDAO();
    private Scanner sc = new Scanner(System.in);

    // 1. 내 정보 보기
    public void viewUserInfo(String userID) {
        UsersVO userInfo = myPageDAO.currUserInfo(userID);
        if (userInfo != null) {
            myPageDAO.printUserInfo(userInfo);
        } else {
            System.out.println("사용자 정보를 찾을 수 없습니다.");
        }
    }

    // 2. 내 정보 수정
    public void updateUserInfo(String userID) {
        UsersVO currentUserInfo = myPageDAO.currUserInfo(userID);
        if (currentUserInfo != null) {
            myPageDAO.usersUpdate(currentUserInfo, userID);
        } else {
            System.out.println("사용자 정보를 찾을 수 없습니다.");
        }
    }

    // 메뉴 표시
    public void showMenu() {
        while (true) {
            System.out.println("1. 내 정보 보기");
            System.out.println("2. 내 정보 수정");
            System.out.println("3. 종료");
            System.out.print("원하는 작업을 선택하세요: ");

            int choice = sc.nextInt();
            sc.nextLine(); // 버퍼 비우기
            switch (choice) {
                case 1: // 1. 내 정보 보기
                    System.out.print("사용자 ID를 입력하세요: ");
                    String viewUserID = sc.nextLine();
                    viewUserInfo(viewUserID);
                    break;
                case 2: // 2. 내 정보 수정
                    System.out.print("사용자 ID를 입력하세요: ");
                    String updateUserID = sc.nextLine();
                    updateUserInfo(updateUserID);
                    break;
                case 3: // 3. 종료
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도하세요.");
            }
        }
    }

}
