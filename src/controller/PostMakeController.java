package controller;

import java.util.Scanner;

public class PostMakeController {

    public void postMakeMenu() {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("===== 글 작성 메뉴 ======");
            System.out.println("=======================");
            printCateSelection();   // 카테고리 메뉴 호출
        }
    }

    public void printCateSelection() {
        System.out.println("======카테고리 목록======");
        System.out.println("1. ");
        System.out.print("원하시는 글의 해당하는 카테고리를 선택 해 주세요 : ");
    }

}
