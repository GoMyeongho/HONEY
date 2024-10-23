package controller;


import dao.CategoryDAO;
import dao.PostListDAO;
import vo.LikesVO;
import vo.PostsVO;
import dao.LikesDAO;

import java.util.*;

public class PostListController {
    private int page;
    private final static String[] listType = {"전체 글 보기", " 카테고리 검색 : ", "작성자 검색 : ", "제목 검색 : ", "내 글 보기", "좋아요한 글 보기"};
    static List<String> category = new CategoryDAO().getCategories();
    private int postSel;
    private int maxPage;


    Scanner sc = null;

    public PostListController(int sel, String name, PostListDAO dao) {
        sc = new Scanner(System.in);
        while (true) {
            postSel = 0;
            page = 0;
            List<PostsVO> list = selectSearchOption(sel, name, dao);
            if (list != null && !list.isEmpty()) {
                showSelections(list, sel, name, dao);
            } else {
                System.out.println("페이지를 보이는데 실패했습니다");
                System.out.println("다시 시도하시겠습니까?");
                System.out.print("[다시] 다시 시도   [그외] 종료");
                if (sc.next().equals("다시")) continue;
                else break;
            }
            while(inPageSet()) showSelections(list, sel, name, dao);
            if (postSel > 0) {
                postSel = list.get(postSel + page * 10 -1).getPostno();
            }
            break;

        }
    }

    public List<PostsVO> selectSearchOption(int sel, String name, PostListDAO dao) {
        List<PostsVO> list;
        switch (sel) {
            case 0:
                list = dao.selectPage(PostListDAO.allSearch);
                break;
            case 1:
                Collections.sort(category, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        if (o1.equals("QNA")) return -1;
                        if (o2.equals("QNA")) return 1;
                        return o1.compareTo(o2);
                    }
                });
                String catelist = "";
                for (int i = 1; i < category.size(); i++) catelist += "[" + i + 1 + "] " + category.get(i) + "  ";
                catelist += "[0] " + category.get(0);
                System.out.println("검색할 카테고리 입력");
                System.out.print(catelist);
                int choice = sc.nextInt();
                if (choice < category.size() && choice >= 0) {
                    list = dao.selectPage(dao.categorySearch(category.get(choice)));
                }
                else{
                    System.out.println("잘못된 입력입니다.");
                    return null;
                }
                break;
            case 2:
                System.out.println("검색할 작성자 이름 입력 : ");
                list = dao.selectPage(dao.authorSearch(sc.next()));
                break;
            case 3:
                System.out.println("검색할 글 제목 입력 : ");
                list = dao.selectPage(dao.titleSearch(sc.next()));
                break;
            case 4:
                list = dao.selectPage(dao.myPostsSearch(name));
                break;
            case 6:
                list = dao.selectPage(dao.LikeSearch(name));
                break;
            default:
                System.out.println("잘못된 코딩 입력입니다.");
                return null;
        }
        return list;
    }

    public void showSelections(List<PostsVO> list, int sel, String name, PostListDAO dao) {
        Collections.sort(list);
        maxPage = list.size() / 10;
        System.out.println("=".repeat(60));
        System.out.println(" ".repeat(10) + listType[sel] + " | 페이지 번호 : " + page);
        System.out.println("=".repeat(60));
        LikesDAO likes = new LikesDAO();
        HashSet<LikesVO> likeSet = likes.likeSet(name);
        for (int i = page * 10; i < page * 10 + 10; i++) {
            System.out.println("-".repeat(60));
            System.out.println("[" + i % 10 + 1 + "] " + list.get(i).getCategory() + " | " + list.get(i).getTitle() + " | "
                    + list.get(i).getAuthor() + " | " + list.get(i).getDate() + likes.likeMark(likeSet, list.get(i).getPostno()) + " ]");
            System.out.println("-".repeat(60));
        }
        System.out.println("=".repeat(60));


    }

    public boolean inPageSet() {
        System.out.println("[<] 이전 페이지" + " ".repeat(30) + "[>] 다음 페이지");
        System.out.println("=".repeat(60));
        System.out.println("[1] ~ [10] 번호의 글 보기    [0] 뒤로가기    [종료] 종료하기");
        String sel = sc.next();
        switch (sel) {
            case "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" :
                postSel = Integer.parseInt(sel);
                return false;
            case "<":
                if(page == 0) System.out.println("가장 처음 페이지 입니다.");
                page = (page > 0) ? page-- : 0;
                return true;
            case ">":
                if (page == maxPage) System.out.println("가장 마지막 페이지 입니다.");
                page = (page > maxPage) ? maxPage : page++;
                return true;
            case "0":
                return false;
            case "종료":
                System.exit(1);
                return false;
            default:
                return true;
        }
    }

    public int getPostSel() {
        return postSel;
    }
}



