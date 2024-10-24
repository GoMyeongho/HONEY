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
                    System.out.println("올바르지 않은 입력입니다, 1~4만 입력 가능합니다." + categories.size() + " 사이의 번호를 입력해 주세요.");
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
            try {
                if (title.length() < 100) { // 글자수 체크
                    postsVO.setTitle(title);    // 이상 없으면 값을 VO로 전달
                    System.out.println("제목입력을 완료 하였습니다.");
                    isValid = true;
                } else {
                    System.out.println("제목은 100글자 이내로 입력 해 주세요.");  // 글자수 제한이 걸릴시 뒤로 돌아감
                }
            } catch (NullPointerException e) {  // 제목에 Null 값이 들어 왔을때에 대비한 예외처리
                System.out.println("제목을 입력 해 주세요");
            }
        }
    }

    public void contentBuilder(PostsVO postsVO) {
        StringBuilder content = new StringBuilder();
        String line;    //  엔터키를 한번 눌렀을때 빈 라인을 생성하기 위한 변수, 기존 엔터는 입력을 받지만 여기서는 한번은 줄바꿈 두번은 입력완료로 간주
        System.out.print("글 내용을 입력 하세요 (엔터 두번 누를시 입력완료) :");
        while (true) {
            try {
                line = sc.nextLine();
                if(line.isEmpty()) {    // 빈칸인 상태에서 엔터를 누르면 입력으로 간주 된다. 2줄띄기는 수정이 필요함*
                    break;
                }
                if (content.length() + line.length() > 200) {   // 한글 기준으로 200자를 넘어가면 제한이 걸림. (늘릴시 데이터베이스 제한 수정 필요)
                    System.out.println("허용된 최대 글자수를 초과 했습니다.");
                    break;
                }
                content.append(line).append("\n");  // 줄바꿈을 돕는 Append \n 으로 바꿔줌
            } catch (NullPointerException e) {
                System.out.println("본문의 내용이 비어 있습니다, 다시 입력해 주세요. ");    // 본문 내용에 Null 값이 들어오는것을 방지한다.
            }
        }
        postsVO.setContent(content.toString()); // 본문 내용 VO로 전달
    }
}