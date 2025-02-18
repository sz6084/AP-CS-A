package gui;

import game.Tile;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TileLabel extends JLabel {
    public static int frameCount = 30;
    public static int frameRate = 30;

    Tile tile;
    Image[] frames;
    int width;
    int height;
    boolean animationRunning;

    public TileLabel(Tile tile, int width, int height) {
        this.tile = tile;
        this.width = width;
        this.height = height;

        this.setSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(width, height));

        this.drawFrame(29);
    }

    private Image readFrame(int frame) {
        String filename = this.tile.getFrame(frame);
        try {
            Image original = ImageIO.read(new File(filename));
            return original.getScaledInstance(this.width, this.height, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            System.out.println("Failed to load " + filename);
            throw new RuntimeException(e);
        }
    }

    private void loadFrames() {
        this.frames = new Image[30];
        for (int i = 0; i < frameCount; i++) {
            this.frames[i] = this.readFrame(i);
        }
    }

    public void drawFrame(int frame) {
        Image image;
        if (this.frames == null || this.frames[frame] == null) {
            image = this.readFrame(frame);
        }
        else {
            image = this.frames[frame];
        }
        ImageIcon icon = new ImageIcon(image);
        this.setIcon(icon);
    }

    public void flip() {
        Thread animationThread = new Thread(() -> {
            this.animationRunning = true;
            this.loadFrames();
            try {
                int startFrame = this.tile.isShowingValue() ? 0 : 29;
                int frameOffset = this.tile.isShowingValue() ? 1 : -1;
                this.tile.flip();

                for (int i = 0; i < frameCount; i++) {
                    int frame = startFrame + i * frameOffset;
                    long start = System.currentTimeMillis();
                    drawFrame(frame);
                    long end = System.currentTimeMillis();
                    long timeout = (1000 / frameRate) - (end - start);
                    Thread.sleep(Math.max(timeout, 0));
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            this.frames = null;
            System.gc();
            this.animationRunning = false;
        });
        animationThread.start();
    }
}
