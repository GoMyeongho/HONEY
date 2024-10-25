package controller;


import dao.*;
import vo.CommentsVO;
import vo.LikesVO;
import vo.PostsVO;


import java.util.*;


public class PostViewController {
    Scanner sc = new Scanner(System.in);
    private int page;
    private int postSel;
    private String name;
    private String id;
    PostViewDAO dao = new PostViewDAO();
    LikesDAO likes = new LikesDAO();
    CommentsDAO comments = new CommentsDAO();
    List<CommentsVO> cList = new ArrayList<CommentsVO>();
    HashSet<LikesVO> likeSet = new HashSet<>();
    PostsVO vo;
    static List<String> category = new CategoryDAO().getCategories();

    public PostViewController(int postSel, String name, String id) {
        this.postSel = postSel;
        this.name = name;
        this.id = id;
        page = 0;
        do showPage(postSel,name,id);
        while(selectOptions());
    }

    public void showPage(int postSel, String name, String id){
        cList = comments.commentsSet(postSel);
        Collections.sort(cList);
        likeSet = likes.likeSet(postSel,id);
        int likeCount = likeSet.size();

        vo = dao.viewPost(postSel);
        System.out.println("=".repeat(60));
        System.out.println("[" + postSel + "] " + vo.getCategory() + " | " + vo.getTitle() + " | " + vo.getAuthor() + " | " + vo.getDate());
        System.out.println("=".repeat(60));
        System.out.println();
        System.out.println(vo.getContent());
        System.out.println();
        System.out.print(" ".repeat(30));
        if (likes.isLike(likeSet, name)) System.out.println("♥");
        else System.out.println("♡");
        System.out.print(" ".repeat(30));
        System.out.println(likeCount);
        System.out.println();

        System.out.println("=".repeat(60));
        for(int i = page * 8; i < Math.min(page * 8 + 8,cList.size()); i++) {
            String ans = (i == 0 || cList.get(i).getCommNo() == cList.get(i-1).getCommNo())
                    ? "" : "->";
            System.out.print(ans);
            System.out.print(cList.get(i).getnName() + " | " + cList.get(i).getcDate() + "\n" + cList.get(i).getContent());
            System.out.println("-".repeat(60));
        }
        System.out.println("[<] 댓글 이전 페이지  [" + (page + 1) + "]  [>] 댓글 다음 페이지");

        System.out.print("[1] ");
        if (likes.isLike(likeSet, name)) System.out.print("좋아요 취소");
        else System.out.print("좋아요 하기");
        if (vo.getAuthor().equals(name)) {
            System.out.println("  [2] 댓글 쓰기  [3] 글 수정하기  [4] 댓글 수정하기  [5] 댓글 삭제하기  [삭제] 글 삭제하기  [0] 나가기");
        }
        else System.out.println("  [2] 댓글 쓰기            [4] 댓글 수정하기               [5] 댓글 삭제하기                [0] 나가기");
    }
    public boolean selectOptions() {
        String sel = sc.next();
        int maxPage = cList.size() / 8;
        switch (sel) {
            case "1":
                if (likes.isLike(likeSet, name)) likes.cancelLike(postSel, id);
                else likes.addLike(postSel, id);
                return true;
            case "2":
                System.out.println("댓글을 쓸 위치를 선택하시오");
                System.out.println("[숫자] 해당 댓글에 대댓글   [9] 맨 마지막에 작성   [0] 나가기");
                int choice = sc.nextInt();
                CommentsVO mkComm;
                switch (choice) {
                    case 1, 2, 3, 4, 5, 6, 7, 8:
                        if(cList.size() < page * 8 + choice - 1){
                            System.out.println("해당하는 댓글이 없습니다");
                            return true;
                        }
                        CommentsVO tempVO = cList.get(page * 8 + choice - 1);
                        mkComm = getCommentsVO();
                        if(mkComm == null) return true;
                        mkComm.setnName(name);
                        mkComm.setUserId(id);
                        mkComm.setPostNo(postSel);
                        comments.addComment(mkComm,tempVO.getCommNo());
                        return true;
                    case 9:
                        mkComm = getCommentsVO();
                        if(mkComm == null) return true;
                        mkComm.setnName(name);
                        mkComm.setUserId(id);
                        mkComm.setPostNo(postSel);
                        mkComm.setSubNo(1);
                        comments.addComment(mkComm);
                        return true;
                    case 0:
                        return false;
                    default:
                        System.out.println("잘못된 입력입니다.");
                        return true;
                }
            case "3":
                if (vo.getAuthor().equals(name)) {
                    PostsVO mkPost = getPostsVO(vo);
                    dao.updatePost(mkPost);
                }
                else System.out.println("잘못된 입력입니다.");
                return true;
            case "삭제":
                if (vo.getAuthor().equals(name)) {
                    System.out.println("정말로 삭제하시려면 \"삭제확인\" 을 입력해주세요 ");
                    if (sc.next().equals("삭제확인")) {
                        dao.deletePost(vo);
                        return false;
                    }
                    else return true;
                }
                else System.out.println("잘못된 입력입니다.");
                return true;
            case "0":
                return false;

            case "4":
                while(updateComm());
                return true;
            case "5":
                while(deleteComm());
                return true;

            case "<":
                if(page == 0) System.out.println("가장 처음 페이지 입니다.");
                page = (page > 0) ? page - 1 : 0;
                return true;

            case ">":
                if (page == maxPage) System.out.println("가장 마지막 페이지 입니다.");
                page = (page == maxPage) ? maxPage : page + 1;
                return true;

            default:
                System.out.println("잘못된 입력입니다.");
                return true;
        }
    }
    public CommentsVO getCommentsVO() {
        sc.nextLine();
        System.out.println("=".repeat(15) + " 댓글 작성 " + "=".repeat(15));
        System.out.println("내용을 입력하세요(글자수 제한 한글 기준 50자, 영문과 숫자 150자)");
        System.out.println("\"[완료]\"를 입력하면 작성이 완성되고, \"[취소]\"를 입력하면 작성이 취소 됩니다.");
        String temp= "";
        String content = "";
        while (content.getBytes().length < 150){

            temp = sc.nextLine();
            temp += "\n";
            if (temp.equals("[완료]\n")) break;
            if (temp.equals("[취소]\n")) return null;
            if((temp + content).getBytes().length > 150) {
                temp = "";
                System.out.println("글자수 제한을 초과했습니다.");

            }
            content += temp;
        }
        CommentsVO vo = new CommentsVO();
        vo.setContent(content);
        return vo;
    }
    public PostsVO getPostsVO(PostsVO vo) {

        System.out.println("=".repeat(15) + " 글 수정 " + "=".repeat(15));
        System.out.println("제목을 수정하시겠습니까?");
        System.out.println("[1] 수정  [그외] 그대로 진행 : ");
        if(sc.next().equals("1")) {
            sc.nextLine();
            System.out.println("수정하실 제목을 입력하세요(Enter키 기준으로 입력되며 [취소] 입력시 취소됩니다)");
            System.out.println("※ 단 한글기준 20자, 영문과 숫자기준 60자 이내만 가능합니다");
            while(true) {
                String title = sc.nextLine();
                if (title.equals("취소")) break;
                if (title.getBytes().length > 60) {
                    System.out.println("글자수가 초과 되었습니다.");
                    continue;
                }
                vo.setTitle(title);
                break;
            }
        }
        System.out.println("카테고리를 수정하시겠습니까?");
        System.out.print("[1] 수정  [그외] 그대로 진행 :");
        if(sc.next().equals("1")) {
            Collections.sort(category, new Comparator<String>() {
                public int compare(String o1, String o2) {
                    if (o1.equals("QNA")) return -1;
                    if (o2.equals("QNA")) return 1;
                    return o1.compareTo(o2);
                }
            });
            sc.nextLine();
            String catelist = "";
            for (int i = 1; i < category.size(); i++) catelist += "[" + i + "] " + category.get(i) + "  ";
            catelist += "[0] " + category.get(0);
            System.out.println("수정하실 카테고리를 입력하세요([-1] 입력시 변경을 취소합니다.)");
            System.out.print(catelist);
            while(true) {
                int choice = sc.nextInt();
                if (choice < category.size() && choice >= 0) {
                    vo.setCategory(category.get(choice));
                }
                else if (choice == -1) {
                    break;
                }
                else{
                    System.out.println("잘못된 입력입니다.");
                    continue;
                }
                break;
            }

        }
        System.out.println("내용을 입력하세요(글자수 제한 한글 기준 200자, 영문과 숫자 600자)");
        System.out.println("\"[완료]\"를 입력하면 작성이 완성되고, \"[취소]\"를 입력하면 작성이 취소 됩니다.");
        String temp= "";
        String content = "";
        while (content.getBytes().length < 600){
            temp = sc.nextLine();
            temp += "\n";
            if (temp.equals("[완료]\n")) {
                vo.setContent(content);
                break;
            }
            if (temp.equals("[취소]\n")) break;
            if((temp + content).getBytes().length > 600) {
                temp = "";
                System.out.println("글자수 제한을 초과했습니다.");
            }
            content += temp;
        }
        return vo;
    }
    public boolean updateComm() {
        int commCnt = 1;
        String sel;
        int choice;
        System.out.println("댓글 목록을 불러옵니다.");
        for (CommentsVO vo : cList){
            if (vo.getnName().equals(name)) {
                System.out.println();
                System.out.print("[" + commCnt++ + "]" + "|" + vo.getnName() + " | " + vo.getcDate() + "\n" + vo.getContent());
                System.out.println("-".repeat(60));
            }
        }
        if (commCnt == 1) {
            System.out.println("댓글이 존재하지 않습니다.");
            return false;
        }
        System.out.println("수정하실 댓글을 선택해주세요");
        System.out.println("[번호] 해당 글 수정  [0] 나가기");
        while(true){
            sel = sc.next();
            if (sel.equals("0")) return false;
            try{
                choice = Integer.parseInt(sel);
                if (choice < commCnt) {
                    commCnt = 1;
                    for (CommentsVO vo2 : cList){
                        if (Objects.equals(vo2.getnName(), name)) {
                            if (commCnt++ == choice) {
                                    CommentsVO updateVO = getCommentsVO();
                                    updateVO.setCommNo(vo2.getCommNo());
                                    updateVO.setPostNo(vo2.getPostNo());
                                    updateVO.setSubNo(vo2.getSubNo());
                                    comments.updateComment(updateVO);
                                    System.out.println("수정이 완료 되었습니다.");
                                    return false;
                            }
                        }
                    }
                }
            } catch (NumberFormatException e) {
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    public boolean deleteComm() {
        int commCnt = 1;
        String sel;
        int choice;
        System.out.println("댓글 목록을 불러옵니다.");
        for (CommentsVO vo : cList){
            if (vo.getnName().equals(name)) {
                System.out.println();
                System.out.print("[" + commCnt++ + "]" + "|" + vo.getnName() + " | " + vo.getcDate() + "\n" + vo.getContent());
                System.out.println("-".repeat(60));
            }
        }
        if (commCnt == 1) {
            System.out.println("댓글이 존재하지 않습니다.");
            return false;
        }
        System.out.println("삭제하실 댓글을 선택해주세요");
        System.out.println("[번호] 해당 글 수정  [0] 나가기");
        while(true){
            sel = sc.next();
            if (sel.equals("0")) return false;
            try{
                choice = Integer.parseInt(sel);
                if (choice < commCnt) {
                    commCnt = 1;
                    for (CommentsVO vo2 : cList){
                        if (Objects.equals(vo2.getnName(), name)) {
                            if (commCnt++ == choice) {
                                CommentsVO deleteVO = new CommentsVO();
                                deleteVO.setCommNo(vo2.getCommNo());
                                deleteVO.setPostNo(vo2.getPostNo());
                                deleteVO.setSubNo(vo2.getSubNo());
                                comments.deleteComment(deleteVO);
                                System.out.println("수정이 완료 되었습니다.");
                                return false;
                            }
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

}

