package breakout;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;

/**
 * Represents a ball that can collide with the paddle and the bricks.
 */
public class Ball {

    public final static double BALL_RADIUS = 10;
    public final double MAX_SPEED = 4.5;
    public double speedMultiplier = 1.0;
    public static final Color BALL_COLOR = new Color(255, 242, 156, 255);
    public static final Color OUTLINE_COLOR = new Color(255, 188, 100, 255);

    private Ellipse ballShape;
    private int collisionCount;
    private double centerX,
    centerY,
    dx,
    dy,
    maxX,
    maxY;

    /**
     * Constructs a ball centered on the centerX/Y position with the specified radius.
     * The X direction of the ball (-x or x) is randomized.
     */
    public Ball(
        double centerX,
        double centerY,
        double initialSpeed,
        double maxX,
        double maxY) {
            ballShape = new Ellipse(centerX, centerY, BALL_RADIUS, BALL_RADIUS);
            ballShape.setFillColor(BALL_COLOR);
            ballShape.setStrokeColor(OUTLINE_COLOR);
            ballShape.setStrokeWidth(2);
            dx = (Math.random() <= 0.5) ? -initialSpeed : initialSpeed;
            dy = initialSpeed;
            this.centerX = centerX;
            this.centerY = centerY;
            this.maxX = maxX;
            this.maxY = maxY;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getSpeedX() {
        return dx;
    }

    public double getSpeedY() {
        return dy;
    }

    /**
     * Reverses the X trajectory of the ball.
     */
    public void reverseSpeedX() {
        dx *= -1;
    }
    /**
     * Reverses the Y trajectory of the ball.
     */
    public void reverseSpeedY() {
        dy *= -1;
    }

    public void changeSpeedMultiplier(double amplifier) {
        speedMultiplier += amplifier;
    }

    /**
     * Update the ball's position if it is in bounds.
     * @return true if ball is within the maxY bound.
     */
    public boolean updatePosition(Paddle paddle, CanvasWindow canvas, BrickManager brickManager) {
        checkForCollision(paddle, canvas, brickManager);
        centerX += dx;
        centerY += dy;
        if (inBounds()) {
            ballShape.setCenter(centerX, centerY);
            return true;
        }
        else return false;
    }

    private boolean inBounds() {
        return ballShape.getY() <= maxY;
    }

    /**
     * Checks if the ball collides with either a wall, a paddle, or a brick.         
     */
    private void checkForCollision(Paddle paddle, CanvasWindow canvas, BrickManager brickManager) {
        intersectsWithWall();            
        intersectsWithPaddle(paddle, canvas);
        brickManager.testHit(this);
    }

    /**
     * Reverses the ball's Y direction if it collides with the paddle.
     */
    private void intersectsWithPaddle(Paddle paddle, CanvasWindow canvas) {
        if (canvas.getElementAt(getCenterX(), getCenterY() + BALL_RADIUS) == paddle.getPaddle()
        || canvas.getElementAt(getCenterX() - BALL_RADIUS, getCenterY() + BALL_RADIUS) == paddle.getPaddle()
        || canvas.getElementAt(getCenterX() + BALL_RADIUS, getCenterY() + BALL_RADIUS) == paddle.getPaddle()) {
            // This if statement prevents the collition from being detected inside the paddle.
            if (getCenterY() + BALL_RADIUS > Paddle.PADDLE_Y + Paddle.PADDLE_WIDTH / 2) {
            }
            else {
                // This if statement prevents the ball from going downward.
                if (dy > 0) {
                    // This if statement reverses the ball's X direction if it touches the nearest side of the paddle.
                    if (dx > 0) {
                        if (getCenterX() <= paddle.getCenterX()) reverseSpeedX();
                    }
                    else {
                        if (getCenterX() >= paddle.getCenterX()) reverseSpeedX();
                    }
                    reverseSpeedY();
                    increaseCollisionCount();
                }
            }
        }       
    } 

    /**
     * Reverses the ball's X or Y direction if it collides with the wall.
     */
    private void intersectsWithWall() {
        if (centerX <= BALL_RADIUS || centerX >= maxX - BALL_RADIUS) {
            reverseSpeedX();
            increaseCollisionCount();
        }
        if (centerY <= BALL_RADIUS) {
            reverseSpeedY();
            increaseCollisionCount();
        }
    }

    /**
     * Updates the total amount of times the ball has collided with an object.
     */
    public void increaseCollisionCount() {
        collisionCount++;
        increaseSpeed();
    }

    /**
     * Increases the speed of the ball by 5% every four collisions until it hits the MAX_SPEED cap.
     */
    private void increaseSpeed() {
        if (collisionCount % 4 == 0) {
            if (dx >= Math.abs(MAX_SPEED) 
            && dy >= Math.abs(MAX_SPEED)) {
            }
            else {
                dx *= speedMultiplier;
                dy *= speedMultiplier;
            }
        }
    }

    /**
     * Adds the ball's shape to the given canvas.
     */
    public void addToCanvas(CanvasWindow canvas) {
        canvas.add(ballShape);
    }

    /**
     * Removes the ball's shape to the given canvas.
     */
    public void removeFromCanvas(CanvasWindow canvas) {
        canvas.remove(ballShape);
    }

}