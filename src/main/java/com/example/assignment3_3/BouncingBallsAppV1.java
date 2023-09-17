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

public class BouncingBallsAppV1 extends Application {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int BALL_RADIUS = 20;
    private static final int NUM_BALLS = 5; // Number of balls

    private List<Ball> balls = new ArrayList<>();
    private boolean isStarted = false;

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, WIDTH, HEIGHT);

        scene.setOnMouseClicked(this::startBouncing); // Start animation on mouse click

        // Animation loop
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isStarted) {
                    for (Ball ball : balls) {
                        ball.move();
                        ball.checkBoundaryCollision(WIDTH, HEIGHT);
                        ball.checkBallCollision(balls);
                    }
                }
            }
        };

        primaryStage.setScene(scene);
        primaryStage.setTitle("Bouncing Balls");
        primaryStage.show();

        animationTimer.start();
    }

    private void startBouncing(MouseEvent event) {
        if (!isStarted) {
            isStarted = true;
            Pane pane = (Pane) ((Scene) event.getSource()).getRoot();

            for (int i = 0; i < NUM_BALLS; i++) {
                Ball ball = new Ball(BALL_RADIUS, Color.BLUE);
                ball.setRandomPosition();
                ball.setRandomVelocity();
                balls.add(ball);
                pane.getChildren().add(ball.getCircle());
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class Ball {
        private Circle circle;
        private double dx;
        private double dy;
        private double boundaryWidth;
        private double boundaryHeight;

        public Ball(double radius, Color color) {
            circle = new Circle(radius, color);
            dx = 0;
            dy = 0;
        }

        public void setRandomPosition() {
            circle.setCenterX(Math.random() * (WIDTH - 2 * BALL_RADIUS) + BALL_RADIUS);
            circle.setCenterY(Math.random() * (HEIGHT - 2 * BALL_RADIUS) + BALL_RADIUS);
        }

        public void setRandomVelocity() {
            Random rand = new Random();
            dx = rand.nextDouble() * 4 - 2; // Random horizontal velocity between -2 and 2
            dy = rand.nextDouble() * 4 - 2; // Random vertical velocity between -2 and 2
        }

        public void move() {
            circle.setCenterX(circle.getCenterX() + dx);
            circle.setCenterY(circle.getCenterY() + dy);
        }

        public void checkBoundaryCollision(double width, double height) {
            if (circle.getCenterX() - circle.getRadius() < 0 || circle.getCenterX() + circle.getRadius() > width) {
                dx = -dx; // Reverse horizontal direction
            }
            if (circle.getCenterY() - circle.getRadius() < 0 || circle.getCenterY() + circle.getRadius() > height) {
                dy = -dy; // Reverse vertical direction
            }
        }

        public void checkBallCollision(List<Ball> balls) {
            for (Ball other : balls) {
                if (this != other && circle.getBoundsInLocal().intersects(other.circle.getBoundsInLocal())) {
                    // Swap velocities to simulate collision
                    double tempDx = this.dx;
                    double tempDy = this.dy;
                    this.dx = other.dx;
                    this.dy = other.dy;
                    other.dx = tempDx;
                    other.dy = tempDy;
                }
            }
        }

        public Circle getCircle() {
            return circle;
        }

        public void setBoundaryWidth(double width) {
            this.boundaryWidth = width;
        }

        public void setBoundaryHeight(double height) {
            this.boundaryHeight = height;
        }
    }
}

