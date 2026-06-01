package breakout;

import edu.macalester.graphics.CanvasWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * This class keeps track of creating and destroying the bricks on the screen.
 */
public class BrickManager {

    private CanvasWindow canvas;
    private List<Brick> bricks;

    public static final int MAX_COLUMNS = 10;
    public static final int MAX_ROWS = 8;
    public static final int PADDING = 6; 

    /**
     * Constructs a brick manager for the specified window.
     */
    public BrickManager(CanvasWindow canvas) {
        bricks = new ArrayList<>();
        this.canvas = canvas; 
    }

    /**
     * Generates 80 bricks, aligned in 10 MAX_COLUMNS and 8 MAX_ROWS.
     */
    public void generateBricks() {
        double startY = canvas.getHeight() * 0.0625;
        for (int i = 1; i <= MAX_ROWS; i++) {
            double currentY = startY + (Brick.BRICK_HEIGHT + PADDING) * i;
            for (int n = 1; n <= MAX_COLUMNS; n++) {
                double startX = ((Brick.BRICK_WIDTH + PADDING) * n) - 20;
                Brick brick = new Brick(startX, currentY, i);
                createBrick(brick);
            }
        }
    }
    /**
     * Checks whether the ball hits any of the bricks,
     * destroying them if so.
     * It also checks if bricks still exist in the canvas,
     * and sets BreakoutGame.gameWin to true if so.
     * @return True if a brick has been destroyed.
     */
    public boolean testHit(Ball ball) {
        for (Brick brick : bricks) {
            if (brick.intersectsWithBall(ball, canvas)) {
                destroyBrick(brick);
                return true;
            }
        }
        if (!bricksStillExist()) BreakoutGame.gameWin = true;
        return false;
    }

    /**
     * Adds a brick to the canvas and the list of bricks.
     */
    public void createBrick(Brick brick) {
        canvas.add(brick);
        bricks.add(brick);
    }
    
    /**
     * Removes a brick from the canvas and the list of bricks.
     */
    public void destroyBrick(Brick brick) {
        canvas.remove(brick);
        bricks.remove(brick);
    }

    /**
     * Removes all bricks from the canvas and the list of bricks.
     */
    public void removeAllBricks() {
        for (Brick brick : bricks) canvas.remove(brick);
        bricks.clear();
    }

    /**
     * Checks if any bricks still exist.
     * @return true if the size of the list of bricks is greater than 0.
     */
    public boolean bricksStillExist() {
        return bricks.size() > 0;
    }

    /**
     * @return The size of the list of bricks.
     */
    public int getNumberOfBricks() {
        return bricks.size();
    }

}