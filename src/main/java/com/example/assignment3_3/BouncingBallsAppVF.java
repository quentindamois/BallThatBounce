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

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int BALL_RADIUS = 20;

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
        ball.setCenterX(event.getX()); // Définir la position X de la balle à la coordonnée X du clic
        ball.setCenterY(event.getY()); // Définir la position Y de la balle à la coordonnée Y du clic
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
            setCenterX(getCenterX() + dx);
            setCenterY(getCenterY() + dy);
        }

        public void checkBoundaryCollision(double width, double height) {
            if (getCenterX() - getRadius() < 0 || getCenterX() + getRadius() > width) {
                dx = -dx;
            }
            if (getCenterY() - getRadius() < 0 || getCenterY() + getRadius() > height) {
                dy = -dy;
            }
        }

        public void checkBallCollision(List<Ball> balls) {
            for (Ball other : balls) {
                if (this != other && getBoundsInLocal().intersects(other.getBoundsInLocal())) {
                    double tempDx = this.dx;
                    double tempDy = this.dy;
                    this.dx = other.dx;
                    this.dy = other.dy;
                    other.dx = tempDx;
                    other.dy = tempDy;
                }
            }
        }
    }

    private class BallSpawner {
        public Ball spawnBall() {
            Random rand = new Random();
            int blueComponent;

            do {
                blueComponent = rand.nextInt(256); // Composante bleue aléatoire
            } while (blueComponent < 100); // Exclure les nuances de bleu trop foncé (inférieur à 100)

            Color randomColor = Color.rgb(0, 0, blueComponent); // Nuance de bleu avec composantes rouge et verte à zéro

            Ball ball = new Ball(BALL_RADIUS, randomColor);
            ball.setRandomVelocity();
            return ball;
        }
    }
}


