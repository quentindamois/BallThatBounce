package com.example.assignment3_3; // Declaration of the package where the class is located

import javafx.animation.AnimationTimer; // Importing the AnimationTimer class from JavaFX to create an animation loop
import javafx.application.Application; // Importing the Application class from JavaFX to create the application
import javafx.scene.Scene; // Importing the Scene class from JavaFX to manage the scene
import javafx.scene.input.MouseEvent; // Importing the MouseEvent class from JavaFX to handle mouse events
import javafx.scene.layout.Pane; // Importing the Pane class from JavaFX to create a pane-based layout
import javafx.scene.paint.Color; // Importing the Color class from JavaFX to manage colors
import javafx.scene.shape.Circle; // Importing the Circle class from JavaFX to create circles
import javafx.stage.Stage; // Importing the Stage class from JavaFX to manage the application window

import java.util.ArrayList; // Importing the ArrayList class to store objects in a list
import java.util.List; // Importing the List class to manage lists
import java.util.Random; // Importing the Random class to generate random numbers

public class BouncingBallsAppVF extends Application { // Defining the main class of the application

    private static final int WIDTH = 600; // Defining the width of the window
    private static final int HEIGHT = 400; // Defining the height of the window
    private static final int BALL_RADIUS = 20; // Defining the radius of the balls

    private List<Ball> balls = new ArrayList<>(); // Creating a list to store the balls
    private BallSpawner ballSpawner = new BallSpawner(); // Creating a ball spawner

    @Override
    public void start(Stage primaryStage) { // Main method to set up the user interface
        Pane pane = new Pane(); // Creating a pane to display the balls
        Scene scene = new Scene(pane, WIDTH, HEIGHT); // Creating a scene with the specified size

        scene.setOnMouseClicked(this::spawnBallOnClick); // Event handler to create a ball on mouse click

        AnimationTimer animationTimer = new AnimationTimer() { // Creating an animation loop
            @Override
            public void handle(long now) { // Method called on each frame of the animation
                for (Ball ball : balls) { // Loop to move each ball and handle collisions
                    ball.move();
                    ball.checkBoundaryCollision(WIDTH, HEIGHT);
                    ball.checkBallCollision(balls);
                }
            }
        };

        primaryStage.setScene(scene); // Setting up the scene in the main window
        primaryStage.setTitle("Bouncing Balls"); // Setting the window title
        primaryStage.show(); // Displaying the window

        animationTimer.start(); // Starting the animation loop
    }

    private void spawnBallOnClick(MouseEvent event) { // Method to create a ball on mouse click
        Ball ball = ballSpawner.spawnBall(); // Creating a new ball using the ball spawner
        ball.setCenterX(event.getX()); // Setting the X position of the ball to the click's X coordinate
        ball.setCenterY(event.getY()); // Setting the Y position of the ball to the click's Y coordinate
        balls.add(ball); // Adding the ball to the list of balls
        Pane pane = (Pane) ((Scene) event.getSource()).getRoot(); // Getting the root pane of the scene
        pane.getChildren().add(ball); // Adding the ball to the pane
    }

    public static void main(String[] args) { // Main method to launch the application
        launch(args); // Calling the application launch method
    }

    private class Ball extends Circle { // Definition of the Ball class, a subclass of Circle
        private double dx; // X-axis velocity
        private double dy; // Y-axis velocity

        public Ball(double radius, Color color) { // Constructor for the Ball class
            super(radius, color); // Calling the constructor of the Circle class
            dx = 0; // Initializing X velocity to 0
            dy = 0; // Initializing Y velocity to 0
        }

        public void setRandomVelocity() { // Method to set a random velocity
            Random rand = new Random(); // Creating a Random object to generate random numbers
            dx = rand.nextDouble() * 4 - 2; // Random X velocity between -2 and 2
            dy = rand.nextDouble() * 4 - 2; // Random Y velocity between -2 and 2
        }

        public void move() { // Method to move the ball based on its velocity
            setCenterX(getCenterX() + dx); // Updating the X position
            setCenterY(getCenterY() + dy); // Updating the Y position
        }

        public void checkBoundaryCollision(double width, double height) { // Method to handle collisions with window borders
            if (getCenterX() - getRadius() < 0 || getCenterX() + getRadius() > width) {
                dx = -dx; // Reversing X velocity on collision with horizontal borders
            }
            if (getCenterY() - getRadius() < 0 || getCenterY() + getRadius() > height) {
                dy = -dy; // Reversing Y velocity on collision with vertical borders
            }
        }

        public void checkBallCollision(List<Ball> balls) { // Method to handle collisions between balls
            for (Ball other : balls) { // Loop to check collisions with each other ball
                if (this != other && getBoundsInLocal().intersects(other.getBoundsInLocal())) { // If balls overlap
                    double tempDx = this.dx; // Swap X velocities
                    double tempDy = this.dy; // Swap Y velocities
                    this.dx = other.dx;
                    this.dy = other.dy;
                    other.dx = tempDx;
                    other.dy = tempDy;
                }
            }
        }
    }

    private class BallSpawner { // Inner class to spawn balls
        public Ball spawnBall() { // Method to create a new ball
            Random rand = new Random(); // Creating a Random object to generate random numbers
            int blueComponent;

            do {
                blueComponent = rand.nextInt(256); // Generating a random blue component
            } while (blueComponent < 100); // Excluding shades of blue that are too dark (less than 100)

            Color randomColor = Color.rgb(0, 0, blueComponent); // Creating a random color with a variable blue component

            Ball ball = new Ball(BALL_RADIUS, randomColor); // Creating a new ball with the specified radius and color
            ball.setRandomVelocity(); // Assigning a random velocity to the ball
            return ball; // Returning the newly created ball
        }
    }
}
