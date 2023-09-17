package com.example.assignment3_3;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class AbleToBounce extends Circle {
    private int xSpeed;
    private int ySpeed;
    public AbleToBounce() {
        super();
    }
    public AbleToBounce(double centerX, double centerY, double radius, int xSpeed, int ySpeed) {
        super(centerX, centerY, radius, Color.BLUE);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }
    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
    public int getXSpeed() {
        return this.xSpeed;
    }
    public int getYSpeed() {
        return this.ySpeed;
    }
    public double[] getNorth() {
        double[] res = { this.getCenterX(), this.getCenterY() - this.getRadius() * 2};
        return res;
    }
    public double[] getEast() {
        double[] res = {this.getCenterX() + this.getRadius() * 2, this.getCenterY()};
        return res;
    }
    public double[] getSouth() {
        double[] res = {this.getCenterX(), this.getCenterY() + this.getRadius() * 2};
        return res;
    }
    public double[] getWest() {
        double[] res = {this.getCenterX() - this.getRadius() * 2, this.getCenterY()};
        return res;
    }
    public boolean isBorderColide(int windowWidth, int windowHeight, double[] point) {
        return point[0] <= -50 || point[0] >= windowWidth || point[1] <= -50 || point[1] >= windowHeight;
    }
    public void BorderColideHandler(int windowWidth, int windowHeigth) {
        if (this.isBorderColide(windowWidth, windowHeigth, this.getNorth()) || this.isBorderColide(windowWidth, windowHeigth, this.getSouth())) this.ySpeed *= -1;
        if (this.isBorderColide(windowWidth, windowHeigth, this.getWest()) || this.isBorderColide(windowWidth, windowHeigth, this.getEast())) this.xSpeed *= -1;
    }

    public String toString() {
        return "Center : (" + this.getCenterX() + ", " + this.getCenterY() + ")\nRadius : " + this.getRadius() + "\nSpeed on the x axis : " + this.xSpeed + "\nSpeed on the y axis : " + this.ySpeed;
    }
}
