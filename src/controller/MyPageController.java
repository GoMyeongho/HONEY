package controller;

import dao.LoginDAO;
import dao.MyPageDAO;
import dao.PostListDAO;
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
    public void showMenu(String userId) {
        while (true) {
            System.out.println("1. 내 정보 보기");
            System.out.println("2. 내 정보 수정");
            System.out.println("3. 내가 쓴 글 보기");
            System.out.println("4. 좋아요 한 글 검색");
            System.out.println("5. 댓글 쓴 글 보기");
            System.out.println("0. 종료");
            System.out.print("원하는 작업을 선택하세요: ");

            int choice = sc.nextInt();
            sc.nextLine(); // 버퍼 비우기
            switch (choice) {
                case 1: // 1. 내 정보 보기
                    viewUserInfo(userId);
                    break;
                case 2: // 2. 내 정보 수정
                    updateUserInfo(userId);
                    break;
                case 3: // 3. 내가 쓴 글 보기
                    new PostListController(4,new LoginDAO().getName(userId),userId, new PostListDAO());
                    break;
                case 4: // 4. 좋아요 한 글 보기
                    new PostListController(5,new LoginDAO().getName(userId),userId, new PostListDAO());
                    break;
                case 5: // 5. 댓글 쓴 글 보기
                    new PostListController(6,new LoginDAO().getName(userId),userId, new PostListDAO());
                    break;
                case 0: // 0. 종료
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도하세요.");
            }
        }
    }

}
