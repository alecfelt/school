package edu.cmps121.app.myapplication;

import java.util.Objects;
import java.util.Observable;

/**
 * Created by alecfelt on 11/7/17.
 */

public class Model extends Observable {
    private String turn;
    private String[][] board = new String[6][7];
    private boolean won;
    private static final Model modelHolder = new Model();
    private boolean wins(int x, int y) {
        int vertical=1, horizontal=1, diagnol1=1, diagnol2=1;
        int left = y, right = 6-y, up = x, down = 5-x;
        int upLeft = Math.min(up, left), upRight = Math.min(up, right), downLeft = Math.min(left, down), downRight = Math.min(down, right);
        // horizontal
        int count = 1;
        while((count <= left) && (Objects.equals(board[x][y-count], turn))) {
            horizontal++;
            count++;
        }
        count = 1;
        while((count <= right) && (Objects.equals(board[x][y+count], turn))) {
            horizontal++;
            count++;
        }
        // vertical
        count = 1;
        while((count <= up) && (Objects.equals(board[x-count][y], turn))) {
            vertical++;
            count++;
        }
        count = 1;
        while((count <= down) && (Objects.equals(board[x+count][y], turn))) {
            vertical++;
            count++;
        }
        // diagnol1
        count = 1;
        while((count <= upLeft) && (Objects.equals(board[x-count][y-count], turn))) {
            diagnol1++;
            count++;
        }
        count = 1;
        while((count <= downRight) && (Objects.equals(board[x+count][y+count], turn))) {
            diagnol1++;
            count++;
        }
        // diagnol2
        count = 1;
        while((count <= upRight) && (Objects.equals(board[x-count][y+count], turn))) {
            diagnol2++;
            count++;
        }
        count = 1;
        while((count <= downLeft) && (Objects.equals(board[x+count][y-count], turn))) {
            diagnol2++;
            count++;
        }
        // did anyone win?
        if(horizontal>=4 || vertical>=4 || diagnol1>=4 || diagnol2>=4) {
            won = true;
            return true;
        }
        return false;
    }
    public void clearBoard() {
        for(int i=0; i<6; i++) {
            for(int j=0; j<7; j++) {
                board[i][j] = "[]";
            }
        }
        setTurn("red");
        setChanged();
        notifyObservers();
    }
    public void setTurn() {
        if(Objects.equals(turn, "red")) {
            turn = "blue";
        } else {
            turn = "red";
        }
    }
    public void setTurn(String s) {
        turn = s;
    }
    public void setBoard(int row, String[] rowStr) {
        for(int i = 0; i < 7; i++) {
            board[row][i] = rowStr[i];
        }
        setChanged();
        notifyObservers();
    }
    public void add(int y) {
        int x = 0;
        while(Objects.equals(board[x][y], "[]")) {
            if(x == 5) { x++; break; }
            x++;
        }
        if(x != 0) {
            board[x-1][y] = turn;
            if(wins(x-1, y)) { return; }
            setTurn();
        }
        setChanged();
        notifyObservers();
    }
    public void setWon(boolean d) { won = d; }
    public boolean getWon() { return modelHolder.won; }
    public String getTurn() { return modelHolder.turn; }
    public String[][] getBoard() { return board; }
    public static Model getInstance() { return modelHolder; }
}
