package gui;

import game.Tile;
import game.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardTable extends JPanel {
    Board board;
    MainFrame main;
    TileLabel[][] tileLabels;

    public BoardTable(Board board, MainFrame main) {
        super(new GridLayout(board.getHeight(), board.getWidth()));
        this.main = main;
        this.board = board;
        this.tileLabels = new TileLabel[this.board.getHeight()][this.board.getWidth()];
        this.populateTiles();
    }
    public void populateTiles() {
        for (int y = 0; y < this.board.getHeight(); y++) {
            for (int x = 0; x < this.board.getWidth(); x++) {
                Tile tile = this.board.getTile(y, x);
                TileLabel label = new TileLabel(tile, 180, 256);
                this.tileLabels[y][x] = label;
                this.addClickListener(y, x);
                this.add(label);
            }
        }
    }

    private void addClickListener(int y, int x) {
        TileLabel label = this.tileLabels[y][x];
        Tile tile = this.board.getTile(y, x);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (label.animationRunning) return;
                onClick(tile);
            }
        });
    }

    private void onClick(Tile tile) {
        if (tile.isShowingValue()) return;

        Tile[] visibleTiles = this.board.getVisibleTiles();
        TileLabel tileLabel = this.tileLabels[tile.getY()][tile.getX()];

        Thread flipThread = new Thread(() -> {
            try {
                if (visibleTiles.length == 0) {
                    tileLabel.flip();
                }

                else if (visibleTiles.length == 1) {
                    tileLabel.flip();
                    Tile otherTile = visibleTiles[0];
                    TileLabel otherTileLabel = this.tileLabels[otherTile.getY()][otherTile.getX()];

                    if (tile.equals(otherTile)) {
                        tile.foundMatch();
                        otherTile.foundMatch();

                        if (this.board.allTilesMatch()) {
                            Thread.sleep(2000);
                            this.main.sidebar.onWin();
                        }
                    }

                    else {
                        Thread.sleep(1500);
                        tileLabel.flip();
                        otherTileLabel.flip();
                    }
                }
            }

            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        flipThread.start();
    }
}