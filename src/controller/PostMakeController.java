package controller;

import dao.CategoryDAO;
import dao.PostMakeDAO;
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
        new PostMakeDAO().postInsert(postsVO);
    }

    public void cateSelection(PostsVO postsVO) {
        List<String> categories = categoryDAO.getCategories();  // 카테고리 DAO 에서 만든 메서드 출력
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.println("======카테고리 선택목록======");
                for (int i = 0; i < categories.size(); i++) {
                    System.out.println((i + 1) + "- " + categories.get(i)); // for 문으로 카테고리를 번호와 카테고리로 출력
                }
                System.out.print("카테고리를 선택 해 주세요: ");
                int cateOpt = sc.nextInt(); // 카테고리 입력 값을 변수에 담아둠

                if (cateOpt > 0 && cateOpt <= categories.size()) {  // 카테고리 범위 수정
                    String selectedCategory = categories.get(cateOpt - 1);  // 위에 값에 +1을 해준것
                    postsVO.setCategory(selectedCategory);  // 선택된 카테고리를 VO로 반환
                    sc.nextLine();  // 겹치는 줄 제거
                    System.out.println(selectedCategory + "를 선택하셨습니다.");    // 카테고리 선택후 한번더 확인을 위해 사용자에게 출력
                    isValid = true; // 올바르게 작동시 While 종료
                } else {    // 카테고리 선택옵션 범위를 벗어날때 예외처리
                    System.out.println("올바르지 않은 입력입니다" + categories.size() + " 사이의 번호를 입력해 주세요.");
                }
            } catch (InputMismatchException e) {    // 숫자가 아닌 문자를 입력 받았을때 예외처리 구문
                System.out.println("숫자만 입력 해 주세요.");
                sc.nextLine();  // 입력 버퍼 비우기
            }
        }
    }

    public void titleSelection(PostsVO postsVO) {   // 카테고리 선택 후에 제목 입력을 위한 메서드
        String title;   // 스캐너로 입력 받았을때 글자수 체크를 위한 변수설정
        boolean isValid = false;    // While 문 종료를 위한 Boolean 변수
        while (!isValid) {
            System.out.println("*주의* 제목은 100자 이내로 입력 하세요.");
            System.out.print("제목을 입력 해 주세요 : ");
            title = sc.nextLine();  // 입력값을 title 변수에 담아둔다
                if (title.length() < 100) { // 글자수 체크
                    postsVO.setTitle(title);    // 이상 없으면 값을 VO로 전달
                    System.out.println("제목입력을 완료 하였습니다.");
                    isValid = true;
                } else if (title.trim().isEmpty()) {
                    System.out.println("제목을 입력 해 주세요.");
                } else {
                    System.out.println("제목은 100글자 이내로 입력 해 주세요.");  // 글자수 제한이 걸릴시 뒤로 돌아감
            }
        }
    }
    public void contentBuilder(PostsVO postsVO) {
        StringBuilder content = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        String line;
        int emptyLineCount = 0; // 빈 줄의 수를 카운트
        System.out.print("글 내용을 입력 하세요 (엔터 두 번 누를 시 입력 완료) :");
        while (true) {
            line = sc.nextLine();
            if (line.isEmpty()) { // 사용자가 엔터를 눌렀을 때
                emptyLineCount++; // 빈 줄 카운트 증가
                if (emptyLineCount >= 1) { // 한번의 빈 줄 입력 시
                    if (content.length() > 0) { // 내용이 있는 경우에만 종료
                        System.out.println("내용 입력을 완료 하였습니다.");
                        break;
                    } else {
                        System.out.print("내용이 없습니다. 재입력 해 주세요 : "); // 내용이 없으면 오류
                        emptyLineCount = 0; // 빈 줄 카운트를 초기화하여 재입력 대기
                    }
                }
                continue; // 다음 루프 반복
            }
            // 빈 줄 카운트 초기화
            emptyLineCount = 0;
            // 최대 글자 수 초과 체크
            if (content.length() + line.length() > 200) {
                System.out.print("허용된 최대 글자수를 초과했습니다. 내용을 재입력 해 주세요 : ");
                continue; // 최대 글자 수 초과 시 계속 입력 받기
            }
            // 공백 체크
            if (line.trim().isEmpty()) {
                System.out.print("내용이 없습니다. 재입력 해 주세요 : "); // 공백만 입력 시 오류
                continue; // 계속 입력 받기
            }
            content.append(line).append("\n"); // 줄바꿈을 돕는 Append \n으로 추가
        }
        postsVO.setContent(content.toString()); // 본문 내용 VO로 전달
    }
}