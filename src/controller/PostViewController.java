package controller;

import com.sun.source.tree.Tree;
import dao.CommentsDAO;
import dao.LikesDAO;
import dao.PostViewDAO;
import vo.CommentsVO;
import vo.LikesVO;
import vo.PostsVO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


public class PostViewController {
    Scanner sc = new Scanner(System.in);
    private int page= 0;
    private int postSel;
    private String name;
    PostViewDAO dao = new PostViewDAO();
    LikesDAO likes = new LikesDAO();
    CommentsDAO comments = new CommentsDAO();
    List<CommentsVO> cList = new ArrayList<CommentsVO>();
    HashSet<LikesVO> likeSet = new HashSet<>();

    public void showPage(int postSel, String name){
        this.postSel = postSel;
        this.name = name;
        cList = comments.commentsSet(postSel);
        Collections.sort(cList);
        likeSet = likes.likeSet(postSel);
        int likeCount = likeSet.size();

        PostsVO vo = dao.viewPost(postSel);
        System.out.println("=".repeat(60));
        System.out.println("[" + postSel + "] " + vo.getCategory() + " | " + vo.getTitle() + " | " + vo.getAuthor() + " | " + vo.getDate());
        System.out.println("=".repeat(60));
        System.out.println();
        System.out.println(vo.getContent());
        System.out.println();
        System.out.print(" ".repeat(30));
        if (likes.isLike(likeSet, name)) System.out.println("♥");
        else System.out.println("♡");
        System.out.print(" ".repeat(29));
        System.out.println(likeCount);
        System.out.println();

        System.out.println("=".repeat(60));
        for(int i = page * 5; i < page * 5 + 5; i++) {
            BigDecimal commNo = cList.get(i).getCommNo();
            String ans = (cList.get(i).getCommNo().equals(commNo.setScale(0, RoundingMode.FLOOR)))
                    ? "" : "->";
            System.out.print(ans);
            System.out.print(cList.get(i).getnName() + "\n" + cList.get(i).getContent());
            System.out.print("-".repeat(60));
        }
        System.out.println("[<] 댓글 이전 페이지           [>] 댓글 다음 페이지");

        System.out.print("[1] ");
        if (likes.isLike(likeSet, name)) System.out.print("좋아요 취소");
        else System.out.print("좋아요 하기");
        if (vo.getAuthor().equals(name)) {
            System.out.println("  [2] 댓글 쓰기  [3] 글 수정하기  [삭제] 글 삭제하기  [0] 나가기");
        }
    }
    public boolean selectOptions() {
        String sel = sc.next();
        switch (sel) {
            case "1":
                if (likes.isLike(likeSet, name)) likes.cancelLike(postSel, name);
                else likes.addLike(postSel, name);
                return true;
            case "2":
                System.out.println("댓글을 쓸 위치를 선택하시오");
                System.out.println("[1] ~ [5] 해당 댓글에 대댓글   [9] 맨 마지막에 작성   [0] 나가기");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1, 2, 3, 4, 5:

                        return true;
                }
        }
    }
}

