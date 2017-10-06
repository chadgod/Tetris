package chad.plugin;

import com.intellij.openapi.actionSystem.TimerListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class TetrisBlock extends JPanel {
    private int blockType;//方塊類型
    private int turnState;//方塊狀態
    private int score = 0;
    private int x,y;//控制的方塊位置
    int flag = 0;
    int[][] area = new int[13][23];
    private final int shapes[][][] = new int[][][] {
            // i
            { { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },
                    { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 } },
            // s
            { { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
                    { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
            // z
            { { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                    { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },
            // j
            { { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
                    { 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                    { 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
            // o
            { { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
            // l
            { { 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
                    { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
            // t
            { { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
                    { 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 } } };
    TetrisBlock() {
        newBlock();
        newArea();
        drawWall();
        Timer timer = new Timer(1000, new TimerListener());
        timer.start();
    }
    public void newBlock() {//產生新方塊
        blockType = (int) (Math.random() * 1000) % 7;
        turnState = (int) (Math.random() * 1000) % 4;
        x = 4;
        y = 0;
        if (gameOver(x, y) == 1) {
            newArea();
            drawWall();
            score = 0;
        }
    }
    public int gameOver(int x, int y) {//判斷遊戲結束
        if (blow(x, y, blockType, turnState) == 0) {
            return 1;
        }
        return 0;
    }
    public int blow(int x, int y, int b_type, int t_state) {//是否合法
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                if (((shapes[b_type][t_state][a * 4 + b] == 1) && (area[x + b + 1][y + a] == 1))
                        || ((shapes[b_type][t_state][a * 4 + b] == 1) && (area[x + b + 1][y + a] == 2))) {
                    return 0;
                }
            }
        }
        return 1;
    }
    public void newArea() {//初始化場景
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 22; j++) {
                area[i][j] = 0;
            }
        }
    }
    public void drawWall() {
        for (int i = 0; i < 12; i++) {
            area[i][21] = 2;
        }
        for (int j = 0; j < 22; j++) {
            area[11][j] = 2;
            area[0][j] = 2;
        }
    }
    class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            repaint();
            if (blow(x, y + 1, blockType, turnState) == 1) {
                y = y + 1;
                delLine();
            }
            if (blow(x, y + 1, blockType, turnState) == 0) {
                if (flag == 1) {
                    addtoArea(x, y, blockType, turnState);
                    delLine();
                    newBlock();
                    flag = 0;
                }
                flag = 1;
            }
        }
    }
    public void delLine() {//消行
        int c = 0;
        for (int b = 0; b < 22; b++) {
            for (int a = 0; a < 12; a++) {
                if (area[a][b] == 1) {
                    c = c + 1;
                    if (c == 10) {
                        score += 10;
                        for (int d = b; d > 0; d--) {
                            for (int e = 0; e < 11; e++) {
                                area[e][d] = area[e][d - 1];
                            }
                        }
                    }
                }
            }
            c = 0;
        }
    }
    public void addtoArea(int x, int y, int b_type, int t_state) {
        int j = 0;
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                if (area[x + b + 1][y + a] == 0) {
                    area[x + b + 1][y + a] = shapes[b_type][t_state][j];
                }
                j++;
            }
        }
    }
    public void paintComponent(Graphics g) {//畫方塊
        super.paintComponent(g);
        // 目前的方塊
        for (int j = 0; j < 16; j++) {
            if (shapes[blockType][turnState][j] == 1) {
                g.fillRect((j % 4 + x + 1) * 10, (j / 4 + y) * 10, 10, 10);
            }
        }
        // 固定好了的方塊
        for (int j = 0; j < 22; j++) {
            for (int i = 0; i < 12; i++) {
                if (area[i][j] == 1) {
                    g.fillRect(i * 10, j * 10, 10, 10);
                }
                if (area[i][j] == 2) {
                    g.drawRect(i * 10, j * 10, 10, 10);
                }
            }
        }
        g.drawString("score=" + score, 0, 240);
    }
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                down();
                break;
            case KeyEvent.VK_UP:
                turn();
                break;
            case KeyEvent.VK_RIGHT:
                right();
                break;
            case KeyEvent.VK_LEFT:
                left();
                break;
        }
    }
    public void down() {
        if (blow(x, y + 1, blockType, turnState) == 1) {
            y = y + 1;
            delLine();
        }
        if (blow(x, y + 1, blockType, turnState) == 0) {
            addtoArea(x, y, blockType, turnState);
            newBlock();
            delLine();
        }
        repaint();
    }
    public void right() {
        if (blow(x + 1, y, blockType, turnState) == 1) {
            x = x + 1;
        }
        repaint();
    }
    public void left() {
        if (blow(x - 1, y, blockType, turnState) == 1) {
            x = x - 1;
        }
        repaint();
    }
    public void turn() {
        int tempState = turnState;
        turnState = (turnState + 1) % 4;
        if (blow(x, y, blockType, turnState) == 1) {
        }
        if (blow(x, y, blockType, turnState) == 0) {
            turnState = tempState;
        }
        repaint();
    }
}
