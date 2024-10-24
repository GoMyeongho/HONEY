package controller;


import dao.LoginDAO;
import dao.PostListDAO;
import dao.PostViewDAO;
import dao.UsersDAO;

import java.util.Scanner;

public class MainController {
    Scanner sc = new Scanner(System.in);
    UsersDAO uDAO = new UsersDAO();
    MyPageController mpCon = new MyPageController();
    PostMakeController pmCon = new PostMakeController();
    private String name;
    private int postSel;
    private int addr = 0;
    private String ID;


    public MainController() {

        System.out.println(LOGO);
        System.out.println("시작하시려면 Enter 키를 눌러주세요");
        sc.nextLine();
        while(true) {
            if (mainPage()){
            while (loginMain());
            }
        }
    }

    public boolean mainPage(){
        UsersController uCon = new UsersController();
        System.out.println("=".repeat(60));
        System.out.println("[1] 로그인 하기");
        System.out.println("=".repeat(60));
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("[2] 회원가입 하기");
        System.out.println("=".repeat(60));
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("[3] ID 찾기");
        System.out.println("=".repeat(60));
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("[4] PW 찾기");
        System.out.println("=".repeat(60));
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("[0] 종료하기");
        System.out.println("=".repeat(60));
        System.out.println();
        while(true) {
            String sel = sc.next();
            switch (sel) {
                case "1":
                    while (true) {
                        name = loginPage();
                        if (name == null) {
                            System.out.println("존재하지 않는 ID 이거나 비밀번호가 틀렸습니다.");
                            System.out.println("다시 시도하시겠습니까?");
                            System.out.println("[1] 네   [그외] 아니요");
                            if (sc.next().equals("1")) continue;
                            return false;
                        }
                        System.out.println("=".repeat(60));
                        System.out.println(name + "님의 로그인이 성공했습니다.");
                        System.out.println("=".repeat(60));
                        return true;
                    }


                case "2":
                    uCon.JoinUser();
                    return false;
                case "3":
                    uCon.findUserID();
                    return false;
                case "4":
                    uCon.findUserPassword();
                    return false;
                case "0":
                    System.exit(1);
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }

    }
    public String loginPage(){
        String PW;
        System.out.print("아이디를 입력하세요 :");
        ID = sc.next();
        sc.nextLine();
        System.out.print("비밀번호를 입력하세요 :");
        PW = sc.next();
        sc.nextLine();
        return new LoginDAO().login(ID,PW);
    }
    public boolean loginMain() {
        sc.nextLine();
        System.out.println("=".repeat(60));
        System.out.println("[1] 게시판 확인 하기");
        System.out.println("=".repeat(60));
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("[2] 내 정보 보기");
        System.out.println("=".repeat(60));
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("[3] 글 작성하기");
        System.out.println("=".repeat(60));
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("[9] 로그아웃 하기");
        System.out.println("=".repeat(60));
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("[0] 종료하기");
        System.out.println("=".repeat(60));
        System.out.println();
        String sel = sc.next();
        switch (sel) {
            case "1":
                while (listMain()){}
                return true;
            case "2":
                System.out.println();
                System.out.println("=".repeat(60));
                mpCon.showMenu();
                return true;
            case "3":
                new PostMakeController().postMakeConsole();
                return true;
            case "9":
                name = null;
                postSel = 0;
                return false;
            case "0":
                System.exit(1);
            default:
                System.out.println("잘못된 입력입니다.");
                return true;
        }
    }

    public boolean listMain() {
        while (true) {
            System.out.println();
            System.out.println("=".repeat(60));
            System.out.println("보기 설정을 골라주세요");
            System.out.println("=".repeat(60));
            System.out.println("[1] 전체 글 보기  [2] 카테고리별 보기  [3] 글 검색하기 [0] 나가기");
            String sel = sc.next();
            switch (sel) {
                case "1":
                    new PostListController(0,name, new PostListDAO());
                    break;
                case "2":
                    new PostListController(1,name, new PostListDAO());
                    break;
                case "3":
                    System.out.println("[1] 제목 검색  [2] 닉네임 검색  [0] 나가기");
                    while (true) {
                        sel = sc.next();
                        switch (sel) {
                            case "1":
                                new PostListController(3,name, new PostListDAO());
                                break;
                            case "2":
                                new PostListController(2,name, new PostListDAO());
                                break;
                            case "0":
                                return true;
                            default:
                                System.out.println("잘못된 입력입니다.");
                                break;
                        }

                    }
                case "0":
                    return false;
                default:
                    System.out.println("잘못된 입력입니다.");
                    break;
            }
        }
    }




    final static String LOGO ="\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠄⡂⡐⡀⡂⡐⡀⡂⡐⡀⡂⡐⡀⡂⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠠⠡⢁⢂⠂⡂⡂⢂⢂⢂⠂⡂⡂⡂⢂⠂⢅⠡⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠠⠡⡁⡂⡂⠅⢂⠂⠅⡐⡐⠨⢀⢂⠂⠅⠌⡐⠨⠠⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠄⠅⠅⡂⠔⠠⢑⢐⠨⢐⢐⠠⢑⢐⢐⠨⠨⢐⠨⢈⠌⠨⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⢀⢐⠁⢅⢁⠂⠅⠅⡂⡂⠌⡐⠠⢊⠠⢂⢐⠨⠐⡁⠔⢁⠌⠌⡐⠠⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⢀⢐⢐⠨⢐⠠⠡⢁⠊⠄⡂⠅⡂⠅⡂⠌⡐⡐⠨⢐⠠⢑⢐⠠⢁⠂⠅⢂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⡐⡐⠨⢐⠈⢌⢐⠨⢐⠠⢁⠂⠅⡂⠅⢂⠂⠅⡂⠌⡐⠠⠨⢐⠨⢈⠀⠀⠀⠀⡀⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠂⠅⡂⠌⡐⠄⢌⢐⠨⢐⠨⢐⠠⢁⠂⢅⢁⠂⠅⡂⠅⠅⡂⠌⠀⠀⠀⠠⠡⢐⠡⠠⠡⠡⠡⠡⠡⠡⢁⠅⠡⡁⡊⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠈⡐⠄⠅⡂⠅⡂⡂⠌⡐⠨⢐⠨⢐⠨⢐⠠⠡⢁⠂⠅⠌⠄⠈⠀⠀⡈⠄⡑⡐⠨⢈⠌⠨⢐⠡⠨⠨⢐⠨⢐⢐⠠⠡⠡⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠌⡐⠄⠅⡂⠄⠅⡂⠅⡂⠌⡐⡐⡐⠨⡈⠔⡈⠌⠄⠁⠀⠀⠂⠄⠅⡂⢂⠅⡂⠌⠌⡐⠨⢐⢁⠢⠨⢐⠠⢈⠂⠅⠅⠂⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠂⡁⡂⡂⠅⡡⠂⡁⡂⠅⡂⠔⠠⡁⡂⡂⡂⠡⠐⠀⠀⠌⠨⠨⢐⠨⠐⡐⠠⠡⢁⠂⠅⡂⡂⠌⡐⡐⠨⢐⠨⢈⠌⠨⢀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠂⡐⠠⢁⠂⠌⡐⠠⢁⠂⠡⢁⠂⡐⡀⠂⠀⠀⠀⠄⠅⠅⠅⡂⠌⡂⠌⠌⠌⡐⠨⢐⢐⠠⢁⢂⠂⠅⡂⠌⡐⠨⠨⢀⠂⡀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠐⠀⠀⠂⠀⠐⠀⠀⠁⠀⠀⠀⠀⠀⠀   ⠈⠄⠅⠅⡡⠂⡁⡂⠅⡊⡐⠨⠨⢐⠠⢊⠠⠂⠌⡂⡂⠅⡂⠅⠅⡂⠅⠠⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠄⡀⠄⡀⠄⡀⠠⠀⠄⠠⡀⠄⠠⠀⠀  ⠀⠅⠅⡡⠂⠌⡐⠠⡁⡂⢂⠅⠅⡂⠌⡐⠨⠨⢐⢐⠠⢁⠢⢈⢂⠂⠅⠁⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡐⠠⡁⡂⠅⢂⢁⠂⠅⠅⠅⠅⡂⠅⠅⡊⢀⠀⠀⠁⠌⡐⠨⢐⠨⢐⢐⠠⢁⠂⠅⡂⠅⡂⠅⡊⠄⡂⠌⡐⠨⢐⠐⡈⠄⠁⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⡐⠨⢐⢐⠠⢑⢐⠠⠡⠡⢁⢊⠐⠄⠅⡂⡂⡂⠄⠀⠀⠂⠨⠨⢐⠨⢀⠢⠨⢐⠨⢐⠠⢁⠂⠅⡐⡐⠄⠅⡂⠅⡂⠌⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⡐⠄⡑⡐⡐⠨⡀⠢⢈⠂⠅⡂⢂⠅⠡⡁⡂⠔⢐⠈⠄⠀⠀⠈⡐⢐⠨⢐⠨⠠⢁⢂⠢⠨⢐⠨⢐⢐⠠⠡⢁⠂⠅⡐⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⢀⢂⠂⠅⡂⠔⠠⡁⡂⠅⡂⠅⡡⠂⡡⠨⢐⢐⠠⢑⠠⠡⠡⢈⠀⠀⠀⠂⠌⡐⠄⡑⡐⡐⠨⢈⢐⠨⢐⢀⢊⠨⢐⠨⠐⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⡀⠢⢐⠨⢐⠠⠡⢁⠢⠐⠡⠠⡁⠢⠨⢐⠨⢐⠠⠨⢐⠨⢐⢁⢂⠐⠀⠀⠈⠐⠠⢁⠂⠔⠠⠑⡐⢐⢈⢐⢀⠂⠌⡐⠀⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⡐⠠⢑⠐⡨⠐⡈⠌⡐⠨⡈⠌⡂⢂⠅⠅⡂⠌⡐⠨⢈⢐⠨⢀⢂⠂⠅⡁⠄⠀⠀⠁⠀⠀⠈⠀⠀⠀⠀⠀⠀⠀⠀⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠠⠑⠠⡁⡂⠅⠂⠅⡂⠅⡂⡁⡂⡂⡂⠅⡂⠅⡂⠅⡂⡂⠌⡐⡐⠨⠐⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⢈⢐⢐⠠⠡⠡⢁⠂⢅⢐⢐⢐⠠⢂⢁⢂⢂⠂⠅⡂⡐⠡⢐⠠⢁⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⡐⠨⢐⢁⠂⠅⡂⡂⠔⡀⡊⠄⡂⢂⠂⠌⡂⡂⠌⠌⡐⡈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠅⢂⠂⠅⡡⢂⢐⠡⢐⠠⢁⠂⠅⠌⡂⢂⠂⠅⡁⢂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠐⠠⠁⠅⡂⢂⠔⢈⢐⠨⢐⠨⠨⢐⠠⠡⡈⡂⠌⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠡⢁⠂⠅⡐⠡⢐⠨⠐⠠⢑⠐⠨⢐⢀⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠁⠀⠀⠀⠀⠈⠀⠀⠀⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n";
}

