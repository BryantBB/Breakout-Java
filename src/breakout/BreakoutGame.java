package breakout;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;

import java.awt.Color;
import java.util.Random;

/**
 * "BREAKOUT!"
 * 
 * Version 1.0 - 03/30/24:
 * Finished "BREAKOUT!, the fourth Object Oriented Programming homework assignment.
 * 
 * Version 1.1 - 10/30/24:
 * Changed ball, brick, paddle, and text colors.
 * Added speed amplifiers - when the player wins, the speed amplifier increases.
 * The paddle will reverse the ball's X velocity if it hits its nearest side.
 */
public class BreakoutGame {

    public static final int CANVAS_WIDTH = 600;
    public static final int CANVAS_HEIGHT = 800;
    private final Color TEXT_COLOR = new Color(66, 66, 66, 255);
    private final Color BACKGROUND_COLOR = new Color(255, 222, 254, 255);
    private double speedAmplifier = 0.0375;
    private byte lives; 
    private CanvasWindow canvas;
    private Paddle paddle;
    private Ball ball;
    private BrickManager brickManager;
    private GraphicsText countDown;
    private GraphicsText livesText;
    private GraphicsText winLossText;
    private boolean gameLoss = false;
    public static boolean gameWin = false;
    private Image image;

    public BreakoutGame() {
        canvas = new CanvasWindow("BREAKOUT!", CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setBackground(BACKGROUND_COLOR);
        brickManager = new BrickManager(canvas);
    }

    public static void main(String[] args){
        BreakoutGame game = new BreakoutGame();
        game.run();
    }

    public void run() {
        resetGame();
        canvas.performEventAction(() ->
            moveBallAndPaddle());
    }

    /**
     * Sets the number of lives the player has.
     * @param numOfLives Number of lives to set.
     */
    public void setLives(byte numOfLives) {
        lives = numOfLives;
    }

    /**
     * Using lambda expressions, it moves the paddle and the ball simultaneously.
     */
    public void moveBallAndPaddle() {
        canvas.onMouseMove((event) ->
            paddle.updatePaddle(event.getPosition().getX(), CANVAS_WIDTH));
        canvas.animate(() ->
            moveBall());
    }

    /**
     * Moves the ball. Additionally, this method handles win/loss conditions because it is ran
     * by canvas.animate() every frame.
     */   
    public void moveBall() {
        if (!gameLoss && !gameWin) {
            if (ball.updatePosition(paddle, canvas, brickManager)) {
                ball.updatePosition(paddle, canvas, brickManager);
            }
            else lossALife();
        }
        else if (gameWin) winGame();
    }

    /**
     * Restarts the game after the player wins.
     */ 
    private void winGame() {
        // gameWin is set to true by BrickManager's test hit method.
        winLossText.setText("GOOD JOB!");
        canvas.draw();
        canvas.pause(3000);
        speedAmplifier += 0.0125;
        restartGameAfterWinLoss();
    }

    /**
     * Restarts the game after the player losses.
     */ 
    private void lossGame() {
        gameLoss = true;
        winLossText.setText("WOMP WOMP!");
        canvas.draw();
        canvas.pause(3000);
        if (speedAmplifier <= 0.0125) speedAmplifier = 0.0125;
        else speedAmplifier -= 0.0125;
        restartGameAfterWinLoss();
    }

    /**
     * Subtracts lives from the player and continues the game.
     */ 
    private void lossALife() {
        ball.removeFromCanvas(canvas);
        lives -= 1;
        livesText.setText("LIVES: " + lives);
        continueGame();
        countDown.setText("");
    }

    /**
     * Displays text that countdowns from 3, the game starts on "GO!"
     */ 
    private void countDown() {
        for (int i = 3; i >= 0; i--) {
            if (i == 0) countDown.setText("GO!");
            else countDown.setText(String.valueOf(i));
            countDown.setCenter(CANVAS_WIDTH * 0.50, CANVAS_HEIGHT * 0.5);
            canvas.draw();
            canvas.pause(1000);
        }
        canvas.remove(countDown);
    }

    /**
     * Resets the canvas by removing everything and redrawing them.
     */
    private void resetGame() {
        setLives((byte) 3);
        brickManager.removeAllBricks();
        canvas.removeAll();
        brickManager.generateBricks();
        createBall();
        createPaddle();
        createCountdownText();
        createLivesText();
        createWinLossText();
        canvas.draw();
        countDown();
    }

    /**
     * Continues the game if lives are greater than 0.
     */
    private void continueGame() {
        if (lives > 0) {
            createBall();
            canvas.add(countDown);
            countDown();
        }
        else if (lives == 0) lossGame();
    }

    private void restartGameAfterWinLoss() {
        gameLoss = false;
        gameWin = false;
        resetGame();
    }

    private void createBall() {
        Random random = new Random();
        double randomStartingX = random.nextDouble((CANVAS_WIDTH * 0.95) - CANVAS_WIDTH * 0.05) + CANVAS_WIDTH * 0.05;
        ball = new Ball(randomStartingX, CANVAS_HEIGHT * 0.75, -2, CANVAS_WIDTH, CANVAS_HEIGHT);
        ball.addToCanvas(canvas);
        ball.changeSpeedMultiplier(speedAmplifier);
    }

    private void createPaddle() {
        paddle = new Paddle((CANVAS_WIDTH / 2) - 25);
        paddle.addToCanvas(canvas);
    }

    private void createCountdownText() {
        countDown = new GraphicsText();
        countDown.setFont(FontStyle.BOLD, 24);
        countDown.setFillColor(TEXT_COLOR);
        countDown.setCenter(CANVAS_WIDTH * 0.50, CANVAS_HEIGHT * 0.5);
        canvas.add(countDown);
    }

    private void createLivesText() {
        livesText = new GraphicsText();
        livesText.setFont(FontStyle.BOLD, 24);
        livesText.setFillColor(TEXT_COLOR);
        livesText.setCenter(CANVAS_WIDTH * 0.82, CANVAS_HEIGHT * 0.85);
        canvas.add(livesText);
        livesText.setText("LIVES: " + lives);
    }

    private void createWinLossText() {
        winLossText = new GraphicsText();
        winLossText.setFont(FontStyle.BOLD, 24);
        winLossText.setFillColor(TEXT_COLOR);
        winLossText.setCenter(CANVAS_WIDTH * 0.33, CANVAS_HEIGHT * 0.5);
        canvas.add(winLossText);
    }

}