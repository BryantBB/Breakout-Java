package breakout;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;

/**
 * A paddle represented as a rectangle object that moves based on the mouse's position.
 */
public class Paddle {

    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 8;
    public static final double PADDLE_Y = BreakoutGame.CANVAS_HEIGHT * 0.8;
    private Rectangle paddle;
    
    /**
     * Constructs a paddle centered on the centerX position.
     */
    public Paddle(double centerX) {
        paddle = new Rectangle(centerX, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFillColor(Ball.BALL_COLOR);
        paddle.setStrokeColor(Ball.OUTLINE_COLOR);
        paddle.setStrokeWidth(2);
    }

    /**
     * Updates the paddle's X position if it is within the bounds of canvasWidth.
     */
    public void updatePaddle(double newX, int canvasWidth) {
        if (PADDLE_WIDTH / 2 < newX && newX <= canvasWidth - PADDLE_WIDTH / 2) {
            paddle.setCenter(newX, PADDLE_Y + PADDLE_HEIGHT / 2);
        }
    }

    public double getCenterX() {
        return paddle.getX() + paddle.getHeight() / 2 + paddle.getWidth() / 2;
    }

    public double getCenterY() {
        return PADDLE_Y + paddle.getHeight() / 2 + paddle.getWidth() / 2;
    }
    
    /**
     * Adds the paddle to the given canvas.
     */
    public void addToCanvas(CanvasWindow canvas) {
        canvas.add(paddle);
    }

    /**
     * @return The object type of paddle.
     */
    public Rectangle getPaddle() {
        return paddle;
    }
    
}