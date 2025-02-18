package game;
/*
 * A game board of NxM board of tiles.
 * 
 *  @author PLTW
 * @version 2.0
 */

/*
 * A Board class for concentration
 */

import java.util.*;

public class Board {
    private static String[] tileValues = {
            "lion", "lion",
            "penguin", "penguin",
            "dolphin", "dolphin",
            "fox", "fox",
            "monkey", "monkey",
            "turtle", "turtle" };
    private Tile[][] gameboard;
    private int width;
    private int height;

    /**
     * Constructor for the game. Creates the 2D gameboard
     * by populating it with card values
     * 
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        gameboard = new Tile[width][height];
        List<String> validValues = new ArrayList<String>(Arrays.asList(tileValues));
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int randIndex = (int) (Math.random() * validValues.size());
                gameboard[i][j] = new Tile(validValues.remove(randIndex));
            }
        }

    }

    /**
     * Returns a string representation of the board, getting the state of
     * each tile. If the tile is showing, displays its value,
     * otherwise displays it as hidden.
     * Precondition: gameboard is populated with tiles
     * 
     * @return a string representation of the board
     */
    public String toString() {
        String out = "";
        for (int i = 0; i < gameboard.length; i++) {
            for (int j = 0; j < gameboard[0].length; j++) {
                if (gameboard[i][j].isShowingValue()) {
                    out += " "+gameboard[i][j].getValue()+" ";
                } else if (gameboard[i][j].getHidden().equals("matched")) {
                    out += " [*] ";
                } else {
                    out += " [_] ";
                }
            }
            out+="\n";
        }

        return out;
    }

    /**
     * Determines if the board is full of tiles that have all been matched,
     * indicating the game is over.
     * Precondition: gameboard is populated with tiles
     * 
     * @return true if all tiles have been matched, false otherwise
     */
    public boolean allTilesMatch() {

        for (int i = 0; i < gameboard.length; i++) {
            for (int j = 0; j < gameboard[0].length; j++) {
                if (!gameboard[i][j].getValue().equals("matched")) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sets the tile to show its value (like a playing card face up)
     * Preconditions:
     * gameboard is populated with tiles,
     * row values must be in the range of 0 to gameboard.length,
     * column values must be in the range of 0 to gameboard[0].length
     * 
     * @param row    the row value of concentrationGame.src.game.Tile
     * @param column the column value of concentrationGame.src.game.Tile
     */
    public void showValue(int row, int column) {
        gameboard[row][column].show();
    }

    /**
     * Checks if the Tiles in the two locations match.
     * If Tiles match, show Tiles in matched state and return a "matched" message
     * If Tiles do not match, re-hide Tiles (turn face down).
     * Preconditions:
     * gameboard is populated with Tiles,
     * row values must be in the range of 0 to gameboard.length,
     * column values must be in the range of 0 to gameboard[0].length
     * 
     * @param row1 the row value of concentrationGame.src.game.Tile 1
     * @param col1 the column value of concentrationGame.src.game.Tile 1
     * @param row2 the row value of concentrationGame.src.game.Tile 2
     * @param col2 the column value of concentrationGame.src.game.Tile 2
     * @return a message indicating whether a match occurred
     */
    public String checkForMatch(int row1, int col1, int row2, int col2) {
        String msg = "";
        if(gameboard[row1][col1].equals(gameboard[row2][col2])) {
            msg = "matched";
            gameboard[row1][col1].foundMatch();
            gameboard[row2][col2].foundMatch();
            showValue(row1, col1);
            showValue(row2, col2);
        } else {
            msg = "not a match";
            gameboard[row1][col1].hide();
            gameboard[row2][col2].hide();
        }
        return msg;
    }

    /**
     * Checks the provided values fall within the range of the gameboard's dimension
     * and that the tile has not been previously matched
     * 
     * @param row the row value of concentrationGame.src.game.Tile
     * @param col the column value of concentrationGame.src.game.Tile
     * @return true if row and col fall on the board and the row,col tile is
     *         unmatched, false otherwise
     */
    public boolean validateSelection(int row, int col) {

        if(row<gameboard.length&&col<gameboard[0].length&&!gameboard[row][col].matched())
            return true;
        else
            return false;
    }

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }

    public Tile getTile(int y, int x) {
        return gameboard[y][x];
    }

    public Tile[] getVisibleTiles() {
        ArrayList<Tile> visibleList = new ArrayList<>();
        for(Tile[] row: gameboard) {
            for(Tile elem: row) {
                if (elem.isShowingValue()) {
                    visibleList.add(elem);
                }
            }
        }
        Tile[] visibleArray = new Tile[visibleList.size()];
        visibleList.toArray(visibleArray);
        return visibleArray;
    }
}