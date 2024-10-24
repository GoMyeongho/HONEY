package controller;

import dao.CategoryDAO;
import vo.PostsVO;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PostMakeController {
    private final CategoryDAO categoryDAO = new CategoryDAO();
    Scanner sc = new Scanner(System.in);

    public void postMakeConsole() {
        boolean isRunning = true;
        PostsVO postsVO = new PostsVO();

        while (isRunning) {
            System.out.println("=======================");
            System.out.println("===== 글 작성 메뉴 ======");
            System.out.println("=======================");
            System.out.println();
            cateSelection(postsVO);   // 카테고리 메뉴 출력 및 입력 받는 메서드
            System.out.println("=======================");
            System.out.println("===== 글 제목 작성 ======");
            System.out.println("=======================");
            System.out.println();
            titleSelection(postsVO);    // 글 제목 메뉴 호출 메서드
            System.out.println("=======================");
            System.out.println("===== 글 내용 작성 ======");
            System.out.println("=======================");
            System.out.println();
            contentBuilder(postsVO);  // 글 내용 메뉴 호출 메서드
            System.out.println("작성된 글 내용:\n" + postsVO.getContent());
            break;
        }
    }

    public void cateSelection(PostsVO postsVO) {
        List<String> categories = categoryDAO.getCategories();  // 카테고리 DAO 에서 만든 메서드 출력
        boolean isValid = false;
        while (!isValid)
            try {
                System.out.println("======카테고리 선택목록======");
                for (int i = 0; i < categories.size(); i++) {
                    System.out.println((i + 1) + "- " + categories.get(i)); // for 문으로 카테고리를 번호와 카테고리로 출력
                    System.out.print("카테고리를 선택 해 주세요 : ");
                    int cateOpt = sc.nextInt(); // 카테고리 입력 값을 변수에 담아둠
                    if (cateOpt > 0 && cateOpt < categories.size()) {
                        String selectedCategory = categories.get(cateOpt - 1);  // 위에 값에 +1을 해준것
                        postsVO.setCategory(selectedCategory);
                        sc.nextLine();  // 겹치는 줄 제거
                        System.out.println(categories + "를 선택 하셨습니다.");
                        isValid = true;
                    } else {
                        System.out.println("올바르지 않는 입력입니다, 1~4번 으로 입력 해 주세요.");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("숫자만 입력 해 주세요");
                sc.nextLine();
            }
    }

    public void titleSelection(PostsVO postsVO) {
        String title;
        boolean isValid = false;
        while (!isValid) {
            System.out.println("*주의* 제목은 100자 이내로 입력 하세요.");
            System.out.print("제목을 입력 해 주세요 : ");
            title = sc.nextLine();
            try {
                if (title.length() < 100) {
                    break;
                } else {
                    System.out.println("제목은 100글자 이내로 입력 해 주세요.");
                }
            } catch (NullPointerException e) {
                System.out.println("제목을 입력 해 주세요");
            }
            postsVO.setTitle(title);
            isValid = true;
//            if (title.length() < 100 && !title.isEmpty()) {
//                postsVO.setTitle(title);
//                isValid = true;
//            } else {
//                System.out.println("제목을 다시 입력 해 주세요.");
//            }
        }
    }

    public String contentBuilder(PostsVO postsVO) {
        StringBuilder content = new StringBuilder();
        String line;
        System.out.print("글 내용을 입력 하세요 (스페이스 두번 누를시 종료) :");

        while (true) {
            line = sc.nextLine();
            if(line.isEmpty()) {
                break;
            }
            if (content.length() + line.length() > 200) {
                System.out.println("허용된 최대 글자수를 초과 했습니다.");
                break;
            }
            content.append(line).append("\n");
        }
        postsVO.setContent(content.toString());
        return content.toString();
    }
}