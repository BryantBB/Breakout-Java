package breakout;

import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;

import java.awt.Color;

/**
 * Represents a brick that could be destroyed by a ball.
 */
public class Brick extends GraphicsGroup {

    private final Color BRICK_RED_COLOR = new Color(255, 187, 202, 255);
    private final Color BRICK_GREEN_COLOR = new Color(182, 255, 156, 255);
    private final Color BRICK_BLUE_COLOR = new Color(156, 245, 255, 255);
    private final Color BRICK_RED_OUTLINE_COLOR = new Color(255, 96, 168, 255);
    private final Color BRICK_GREEN_OUTLINE_COLOR = new Color(33, 205, 148, 255);
    private final Color BRICK_BLUE_OUTLINE_COLOR = new Color(95, 153, 255, 255);
    public static final double BRICK_WIDTH = 48;
    public static final double BRICK_HEIGHT = 10; 

    private Rectangle brick;

    /**
     * Constructs a brick centered on the centerX/Y position with the
     * BRICK_WIDTH and BRICK_HEIGHT final variables.
     * @param row determines the color of the brick based on the row its on.
     */
    public Brick(double centerX, double centerY, int row) {
        brick = new Rectangle(centerX, centerY, BRICK_WIDTH, BRICK_HEIGHT);
        add(brick);
        createBrickDrawing(row);
    }
    
    private void createBrickDrawing(int row) {
        brick.setStrokeWidth(2);
        setBrickFillColor(row);
    }

    /**
     * Sets the fill color of the brick based on the row its on.
     * @param row determines the color of the brick.
     */
    public void setBrickFillColor(int row) {
        switch (row) {
            case 1:
            case 2:
                brick.setFillColor(BRICK_RED_COLOR);
                brick.setStrokeColor(BRICK_RED_OUTLINE_COLOR);
                break;
            case 3:
            case 4:
                brick.setFillColor(Ball.BALL_COLOR);
                brick.setStrokeColor(Ball.OUTLINE_COLOR);
                break;
            case 5:
            case 6:
                brick.setFillColor(BRICK_GREEN_COLOR);
                brick.setStrokeColor(BRICK_GREEN_OUTLINE_COLOR);
                break;
            case 7:
            case 8:
                brick.setFillColor(BRICK_BLUE_COLOR);        
                brick.setStrokeColor(BRICK_BLUE_OUTLINE_COLOR);
                break;
        }   
    }

    public double getCenterX() {
        return brick.getX() + brick.getHeight() / 2 + brick.getWidth() / 2;
    }

    public double getCenterY() {
        return brick.getY() + brick.getHeight() / 2 + brick.getWidth() / 2;
    }

    /**
     * @return The object type of brick.
     */
    public Rectangle getBrick() {
        return brick;
    }

    /**
     * Reverses the ball's X or Y direction if it collides with the brick
     * @return true if it collides.
     */
    public boolean intersectsWithBall(Ball ball, CanvasWindow canvas) {
        Rectangle brickObject = getBrick();
        if (canvas.getElementAt(ball.getCenterX(), ball.getCenterY() + Ball.BALL_RADIUS) == brickObject
        || canvas.getElementAt(ball.getCenterX(), ball.getCenterY() - Ball.BALL_RADIUS) == brickObject) {
            ball.reverseSpeedY();
            ball.increaseCollisionCount();
            return true;
        }
        if (canvas.getElementAt(ball.getCenterX() + Ball.BALL_RADIUS, ball.getCenterY()) == brickObject
        || canvas.getElementAt(ball.getCenterX() - Ball.BALL_RADIUS, ball.getCenterY()) == brickObject) {
            ball.reverseSpeedX();
            ball.increaseCollisionCount();
            return true;
        }
        return false;
    }
}