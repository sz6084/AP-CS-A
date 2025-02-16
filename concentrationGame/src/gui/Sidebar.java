package gui;

import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {
    int timeElapsed = 0;
    int bestTime = 0;
    boolean pauseTimer;
    MainFrame main;

    GridBagLayout bagLayout;
    GridBagConstraints bagConstraints;
    JLabel titleLabel;
    JLabel infoLabel;
    JLabel timeDisplay;
    JLabel bestTimeDisplay;
    JButton resetGameButton;
    Timer timer;

    public Sidebar(MainFrame main) {
        this.main = main;
        this.bagLayout = new GridBagLayout();
        this.bagConstraints = new GridBagConstraints();
        this.bagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        this.bagConstraints.insets = new Insets(3, 10, 3, 10);
        this.setLayout(this.bagLayout);

        this.timer = new Timer(1000, e -> {
            this.timeElapsed++;
            updateTimeDisplay();
        });
        this.timer.start();

        this.titleLabel = new JLabel("APCS Concentration Game");
        this.titleLabel.setFont(new Font("sans-serif", Font.BOLD, 25));
        this.addComponent(this.titleLabel, 0);

        this.infoLabel = new JLabel("Match the cards and try to achieve the best possible time!");
        this.addComponent(this.infoLabel, 1);

        this.timeDisplay = new JLabel("Current Time: 0s");
        this.addComponent(this.timeDisplay, 2);

        this.bestTimeDisplay = new JLabel("Best Time: 0s");
        this.addComponent(this.bestTimeDisplay, 3);

        this.resetGameButton = new JButton("Start Over");
        this.resetGameButton.addActionListener(e -> {
            this.resetGame();
        });
        this.addComponent(this.resetGameButton, 4);

        JPanel filler = new JPanel();
        this.bagConstraints.weightx = 1;
        this.bagConstraints.weighty = 1;
        this.addComponent(filler, 5);
    }

    private void addComponent(Component component, int gridY, GridBagConstraints constraints) {
        constraints.gridx = 0;
        constraints.gridy = gridY;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        this.bagLayout.addLayoutComponent(component, constraints);
        this.add(component);
    }
    private void addComponent(Component component, int gridY) {
        this.addComponent(component, gridY, this.bagConstraints);
    }

    private void updateTimeDisplay(){
        this.timeDisplay.setText("Current Time: " + this.timeElapsed + "s");
        this.bestTimeDisplay.setText("Best Time: " + this.bestTime + "s");
    }

    private void resetGame() {
        this.timeElapsed = 0;
        this.updateTimeDisplay();
        this.main.setupBoard();
        if (!this.timer.isRunning()) this.timer.start();
    }

    public void onWin() {
        String dialogMessage = this.timeElapsed < this.bestTime ?
                "Congratulations, you set a new personal best! Play again to see if you can beat this time." :
                "Congratulations! Try again to see if you can beat your best time.";
        this.bestTime = this.bestTime == 0 ? this.timeElapsed : Math.min(this.bestTime, this.timeElapsed);
        this.updateTimeDisplay();
        this.timer.stop();
        Utils.playSound("sounds/win.wav");

        int userChoice = JOptionPane.showConfirmDialog(null, dialogMessage);
        if (userChoice == 0) {
            this.resetGame();
        }
    }
}
