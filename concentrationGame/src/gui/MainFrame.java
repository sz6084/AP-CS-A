package gui;

import game.Board;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    Board board;
    BoardTable table;
    Sidebar sidebar;
    JSplitPane splitPane;

    public MainFrame() {
        this.setTitle("Concentration Game");
        this.setSize(1300, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            Image iconImage = ImageIO.read(new File("images/converted/card.png"));
            this.setIconImage(iconImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.sidebar = new Sidebar(this);
        this.setupBoard();

        this.setVisible(true);
    }

    public void setupBoard() {
        this.board = new Board(5, 4);
        this.table = new BoardTable(this.board, this);
        if (this.splitPane != null) this.remove(splitPane);
        this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.table, this.sidebar);
        this.add(splitPane);
        this.validate();
    }
}
