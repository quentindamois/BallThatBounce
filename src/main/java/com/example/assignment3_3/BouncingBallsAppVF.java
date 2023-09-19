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

    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;
    private static final int BALL_RADIUS = 20;
    private static final double DAMPING_FACTOR = 0.5;
    private static final double MAX_SPEED = 5.0; // Limite de vitesse maximale

    private List<Ball> balls = new ArrayList<>();
    private BallSpawner ballSpawner = new BallSpawner();

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, WIDTH, HEIGHT);

        scene.setOnMouseClicked(this::spawnBallOnClick);

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Ball ball : balls) {
                    ball.move();
                    ball.checkBoundaryCollision(WIDTH, HEIGHT);
                    ball.checkBallCollision(balls);
                }
            }
        };

        primaryStage.setScene(scene);
        primaryStage.setTitle("Bouncing Balls");
        primaryStage.show();

        animationTimer.start();
    }

    private void spawnBallOnClick(MouseEvent event) {
        Ball ball = ballSpawner.spawnBall();
        ball.setCenterX(event.getX());
        ball.setCenterY(event.getY());
        balls.add(ball);
        Pane pane = (Pane) ((Scene) event.getSource()).getRoot();
        pane.getChildren().add(ball);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class Ball extends Circle {
        private double dx;
        private double dy;

        public Ball(double radius, Color color) {
            super(radius, color);
            dx = 0;
            dy = 0;
        }

        public void setRandomVelocity() {
            Random rand = new Random();
            dx = rand.nextDouble() * 4 - 2;
            dy = rand.nextDouble() * 4 - 2;
        }

        public void move() {
            // Limitez la vitesse en utilisant MAX_SPEED
            double speed = Math.sqrt(dx * dx + dy * dy);
            if (speed > MAX_SPEED) {
                double ratio = MAX_SPEED / speed;
                dx *= ratio;
                dy *= ratio;
            }

            setCenterX(getCenterX() + dx);
            setCenterY(getCenterY() + dy);
        }

        public void checkBoundaryCollision(double width, double height) {
            if (getCenterX() - getRadius() < 0) {
                setCenterX(getRadius());
                dx = -dx * DAMPING_FACTOR;
            }
            if (getCenterX() + getRadius() > width) {
                setCenterX(width - getRadius());
                dx = -dx * DAMPING_FACTOR;
            }
            if (getCenterY() - getRadius() < 0) {
                setCenterY(getRadius());
                dy = -dy * DAMPING_FACTOR;
            }
            if (getCenterY() + getRadius() > height) {
                setCenterY(height - getRadius());
                dy = -dy * DAMPING_FACTOR;
            }
        }

        public void checkBallCollision(List<Ball> balls) {
            for (Ball other : balls) {
                if (this != other && intersects(other.getBoundsInLocal())) {
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

    private class BallSpawner {
        public Ball spawnBall() {
            Random rand = new Random();
            int blueComponent;

            do {
                blueComponent = rand.nextInt(256);
            } while (blueComponent < 100);

            Color randomColor = Color.rgb(0, 0, blueComponent);

            Ball ball = new Ball(BALL_RADIUS, randomColor);
            ball.setRandomVelocity();
            return ball;
        }
    }
}
