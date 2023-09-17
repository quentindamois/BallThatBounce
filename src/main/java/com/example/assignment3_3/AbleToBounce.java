package com.example.assignment3_3;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class AbleToBounce extends Circle {
    private int xSpeed;
    private int ySpeed;
    public AbleToBounce() {
        super();
    }
    public AbleToBounce(double radius) {
        super(radius);
    }
    public AbleToBounce(double centerX, double centerY, double radius, int xSpeed, int ySpeed) {
        super(centerX, centerY, radius);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    public AbleToBounce(double radius, Paint fill) {
        super(radius, fill);
    }
    public AbleToBounce(double centerX, double centerY, double radius, Paint fill) {
        super(centerX, centerY, radius, fill);
    }
    /*
    public AbleToBounce(double centerX, double centerY) {
        super(centerX, centerY);
    }
     */
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
    public double[] getNort() {
        double[] res = { this.getCenterX(), this.getCenterY() - this.getRadius() * 2};
        return res;
    }
    public double[] getNorthEast() {
        double[] res = {this.getCenterX() + this.getRadius() * 2, this.getCenterY() - this.getRadius() * 2};
        return res;
    }
    public double[] getEast() {
        double[] res = {this.getCenterX() + this.getRadius() * 2, this.getCenterY()};
        return res;
    }
    public double[] getSouthEast() {
        double[] res = {this.getCenterX() + this.getRadius() * 2, this.getCenterY() + this.getRadius() * 2};
        return res;
    }
    public double[] getSouth() {
        double[] res = {this.getCenterX(), this.getCenterY() + this.getRadius() * 2};
        return res;
    }
    public double[] getSouthWest() {
        double[] res = {this.getCenterX() - this.getRadius() * 2, this.getCenterY() + this.getRadius() * 2};
        return res;
    }
    public double[] getWest() {
        double[] res = {this.getCenterX() - this.getRadius() * 2, this.getCenterY()};
        return res;
    }
    public double[] getNortWest() {
        double[] res = {this.getCenterX() - this.getRadius() * 2, this.getCenterY() - this.getRadius() * 2};
        return res;
    }
    public boolean Colide(AbleToBounce ball) {
        return this.isIn(ball.getNort()) || this.isIn(ball.getNortWest()) || this.isIn(ball.getWest()) || this.isIn(ball.getSouthWest()) || this.isIn(ball.getSouth()) || this.isIn(ball.getSouthEast()) || this.isIn(ball.getEast()) || this.isIn(ball.getNorthEast());
    }
    public boolean isIn(double[] point) {
        return ((point[0] - this.getCenterX()) * (point[0] - this.getCenterX()) + (point[1] - this.getCenterY()) * (point[1] - this.getCenterY()) <= ((this.getRadius()/2) * (this.getRadius()/2)));
    }
    public boolean isBorderColide(int windowWidth, int windowHeight, double[] point) {
        return point[0] <= -50 || point[0] >= windowWidth || point[1] <= -50 || point[1] >= windowHeight;
    }
    public boolean iSBorderColideAll(int windowWidth, int windowHeigth) {
        return this.isBorderColide(windowWidth, windowHeigth, this.getNort()) || this.isBorderColide(windowWidth, windowHeigth, this.getNortWest()) || this.isBorderColide(windowWidth, windowHeigth, this.getWest()) || this.isBorderColide(windowWidth, windowHeigth, this.getSouthWest()) || this.isBorderColide(windowWidth, windowHeigth, this.getSouth()) || this.isBorderColide(windowWidth, windowHeigth, this.getSouthEast()) || this.isBorderColide(windowWidth, windowHeigth, this.getEast()) || this.isBorderColide(windowWidth, windowHeigth, this.getNorthEast());
    }
    public void BorderColideHandler(int windowWidth, int windowHeigth) {
        if (this.isBorderColide(windowWidth, windowHeigth, this.getNort()) || this.isBorderColide(windowWidth, windowHeigth, this.getSouth())) this.ySpeed *= -1;
        if (this.isBorderColide(windowWidth, windowHeigth, this.getWest()) || this.isBorderColide(windowWidth, windowHeigth, this.getEast())) this.xSpeed *= -1;
    }

    public String toString() {
        return "Center : (" + this.getCenterX() + ", " + this.getCenterY() + ")\nRadius : " + this.getRadius() + "\nSpeed on the x axis : " + this.xSpeed + "\nSpeed on the y axis : " + this.ySpeed;
    }
}
