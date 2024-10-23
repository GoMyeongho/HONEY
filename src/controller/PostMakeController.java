package controller;

import dao.CategoryDAO;

import java.util.List;
import java.util.Scanner;

public class PostMakeController {
    private CategoryDAO categoryDAO = new CategoryDAO();
    Scanner sc = new Scanner(System.in);
    public void postMakeMenu() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("===== 글 작성 메뉴 ======");
            System.out.println("=======================");
            printCateSelection();   // 카테고리 메뉴 호출
        }
    }

    public void printCateSelection() {
        List<String> categories = categoryDAO.getCategories();  // 카테고리 DAO 에서 만든 메서드 출력
        System.out.println("======카테고리 목록======");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + "- " + categories.get(i)); // for 문으로 카테고리를 번호와 카테고리로 출력
        }
        System.out.print("카테고리를 선택 해 주세요 : ");
        int cateOpt = sc.nextInt(); // 카테고리 입력 값을 변수에 담아둠
        sc.nextLine();  // 겹치는 줄 제거
        System.out.println(categories + "를 선택 하셨습니다.");
    }

    public void writeTitle() {
        System.out.println();
    }

}
