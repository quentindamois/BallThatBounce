package com.example.assignment3_3;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BouncingBallsAppVF extends Application {

    private static final int WIDTH = 900;                 // Width of the application window
    private static final int HEIGHT = 600;                // Height of the application window
    private static final int BALL_RADIUS = 20;            // Radius of the balls
    private static final double DAMPING_FACTOR = 0.5;     // Damping factor for collisions
    private static final double MAX_SPEED = 5.0;         // Maximum speed limit for balls

    private List<Ball> balls = new ArrayList<>();         // List to store the balls
    private BallSpawner ballSpawner = new BallSpawner();  // Creates a ball spawner

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();                // Create a pane to display the balls
        Scene scene = new Scene(pane, WIDTH, HEIGHT); // Create a scene with specified size

        scene.setOnMouseClicked(this::spawnBallOnClick); // Event handler to create a ball on mouse click

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {   // Method called on each frame of the animation
                for (Ball ball : balls) {   // Loop to move each ball and handle collisions
                    ball.move();
                    ball.checkBoundaryCollision(WIDTH, HEIGHT);
                    ball.checkBallCollision(balls);
                }
            }
        };

        primaryStage.setScene(scene);            // Set up the scene in the main window
        primaryStage.setTitle("Bouncing Balls");  // Set the window title
        primaryStage.show();                     // Display the window

        animationTimer.start();                   // Start the animation loop
    }

    private void spawnBallOnClick(MouseEvent event) {  // Method to create a ball on mouse click
        Ball ball = ballSpawner.spawnBall();           // Create a new ball using the ball spawner
        ball.setCenterX(event.getX());                 // Set the X position of the ball to the click's X coordinate
        ball.setCenterY(event.getY());                 // Set the Y position of the ball to the click's Y coordinate
        balls.add(ball);                               // Add the ball to the list of balls
        Pane pane = (Pane) ((Scene) event.getSource()).getRoot(); // Get the root pane of the scene
        pane.getChildren().add(ball);                  // Add the ball to the pane
    }

    public static void main(String[] args) {   // Main method to launch the application
        launch(args);                           // Call the application launch method
    }

    private class Ball extends Circle {         // Definition of the Ball class, a subclass of Circle
        private double dx;                      // X-axis velocity
        private double dy;                      // Y-axis velocity

        public Ball(double radius, Color color) {  // Constructor for the Ball class
            super(radius, color);               // Call the constructor of the Circle class
            dx = 0;                            // Initialize X velocity to 0
            dy = 0;                            // Initialize Y velocity to 0
        }

        public void setRandomVelocity() {        // Method to set a random velocity
            Random rand = new Random();          // Create a Random object to generate random numbers
            dx = rand.nextDouble() * 4 - 2;     // Random X velocity between -2 and 2
            dy = rand.nextDouble() * 4 - 2;     // Random Y velocity between -2 and 2
        }

        public void move() {                     // Method to move the ball based on its velocity
            // Limit the speed using MAX_SPEED
            double speed = Math.sqrt(dx * dx + dy * dy);
            if (speed > MAX_SPEED) {
                double ratio = MAX_SPEED / speed;
                dx *= ratio;
                dy *= ratio;
            }

            setCenterX(getCenterX() + dx);       // Update the X position
            setCenterY(getCenterY() + dy);       // Update the Y position
        }

        public void checkBoundaryCollision(double width, double height) {  // Method to handle collisions with window borders
            if (getCenterX() - getRadius() < 0) {
                setCenterX(getRadius());
                dx = -dx * DAMPING_FACTOR;       // Reverse X velocity on collision with horizontal borders
            }
            if (getCenterX() + getRadius() > width) {
                setCenterX(width - getRadius());
                dx = -dx * DAMPING_FACTOR;       // Reverse X velocity on collision with horizontal borders
            }
            if (getCenterY() - getRadius() < 0) {
                setCenterY(getRadius());
                dy = -dy * DAMPING_FACTOR;       // Reverse Y velocity on collision with vertical borders
            }
            if (getCenterY() + getRadius() > height) {
                setCenterY(height - getRadius());
                dy = -dy * DAMPING_FACTOR;       // Reverse Y velocity on collision with vertical borders
            }
        }

        public void checkBallCollision(List<Ball> balls) {  // Method to handle collisions between balls
            for (Ball other : balls) {                      // Loop to check collisions with each other ball
                if (this != other && intersects(other.getBoundsInLocal())) {  // If balls overlap
                    double deltaX = other.getCenterX() - getCenterX();
                    double deltaY = other.getCenterY() - getCenterY();
                    double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                    if (distance < getRadius() + other.getRadius()) {
                        double normalX = deltaX / distance;
                        double normalY = deltaY / distance;

                        double relativeVelocityX = other.dx - dx;
                        double relativeVelocityY = other.dy - dy;
                        double dotProduct = relativeVelocityX * normalX + relativeVelocityY * normalY;

                        double impulse = (2.0 * dotProduct) / (1.0 / getRadius() + 1.0 / other.getRadius());
                        dx += impulse * normalX;
                        dy += impulse * normalY;
                        other.dx -= impulse * normalX;
                        other.dy -= impulse * normalY;
                    }
                }
            }
        }
    }

    private class BallSpawner {    // Inner class to spawn balls
        public Ball spawnBall() {   // Method to create a new ball
            Random rand = new Random();   // Create a Random object to generate random numbers
            int blueComponent;

            do {
                blueComponent = rand.nextInt(256);  // Generate a random blue component
            } while (blueComponent < 100);          // Exclude shades of blue that are too dark (less than 100)

            Color randomColor = Color.rgb(0, 0, blueComponent);  // Create a random color with a variable blue component

            Ball ball = new Ball(BALL_RADIUS, randomColor);  // Create a new ball with the specified radius and color
            ball.setRandomVelocity();  // Assign a random velocity to the ball
            return ball;  // Return the newly created ball
        }
    }
}
