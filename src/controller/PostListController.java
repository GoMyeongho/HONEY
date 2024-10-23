package controller;


import dao.PostListDAO;
import vo.PostsVO;

import java.util.Collections;
import java.util.List;

public class PostListController {
    private int page = 1;


    PostListController(String sel, PostListDAO dao) {
        switch (sel) {
            case "all":
                List<PostsVO> list =  dao.selectPage(PostListDAO.allSearch);
                Collections.sort(list);
                break;
            case "cate":

                List<PostsVO>

                break;
            case "nickname":


                break;
            case "title":


                break;
            case "mine":


                break;

        }
    }

    public void viewPage(String category){

    }


}
