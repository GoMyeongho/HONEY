package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class LikedDAO {
    private final static String[] heart = {"♡", "♥"};
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement psmt = null;
    ResultSet rs = null;
    Scanner sc = null;
    public LikedDAO() {
        sc = new Scanner(System.in);
    }


    public String isLiked(String nName, int postNo) {

    }
}
