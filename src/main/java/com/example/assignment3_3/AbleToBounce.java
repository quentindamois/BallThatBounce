package com.example.assignment3_3;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class AbleToBounce extends Circle {
    /**
     * This is the AbleToBounce class. This is a subclass of the circle class.
     * */
    /**
     * xSpeed is a double field used to store the speed on the x axis of the object.
     * */
    private int xSpeed;
    /**
     * ySpeed is a int field used to stre the speed of the object on the y axis.
     * */
    private int ySpeed;
    /**
     * This is the default constructor of the AbleToBounce class.
     * */
    public AbleToBounce() {
        super();
    }
    /**
     * This construcor has parameter to choses the vlaue stored in the field of the object.
     * */
    public AbleToBounce(double centerX, double centerY, double radius, int xSpeed, int ySpeed) {
        super(centerX, centerY, radius, Color.BLUE);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    /**
     * This is mutator of the xSpeed field.
     * */
    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }
    /**
     * This is the mutator of the ySpeed field.
     * */
    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
    /**
     * This is the accessor of the xSpeed field.
     * */
    public int getXSpeed() {
        return this.xSpeed;
    }
    /**
     * This is the accessor of the ySpeed field.
     * */
    public int getYSpeed() {
        return this.ySpeed;
    }
    /**
     * This is a mutator object used to get the point locate at the top of circle.
     * */
    public double[] getNorth() {
        double[] res = { this.getCenterX(), this.getCenterY() - this.getRadius() * 2};
        return res;
    }
    /**
     * This is a mutator object used to get the point locate at the most right part of circle.
     * */
    public double[] getEast() {
        double[] res = {this.getCenterX() + this.getRadius() * 2, this.getCenterY()};
        return res;
    }
    /**
     * This is a mutator object used to get the point locate at the most left part of circle.
     * */
    public double[] getSouth() {
        double[] res = {this.getCenterX(), this.getCenterY() + this.getRadius() * 2};
        return res;
    }
    /**
     * This is a mutator object used to get the point locate at the bottom of circle.
     * */
    public double[] getWest() {
        double[] res = {this.getCenterX() - this.getRadius() * 2, this.getCenterY()};
        return res;
    }
    /**
     * This method is used for checking if the ball is touching a wall.
     * */
    public boolean isBorderColide(int windowWidth, int windowHeight, double[] point) {
        return point[0] <= -50 || point[0] >= windowWidth || point[1] <= -50 || point[1] >= windowHeight;
    }
    /**
     * This method call the method isBOrderColide to see if one the four border is touched.
     * */
    public void BorderColideHandler(int windowWidth, int windowHeigth) {
        if (this.isBorderColide(windowWidth, windowHeigth, this.getNorth()) || this.isBorderColide(windowWidth, windowHeigth, this.getSouth())) this.ySpeed *= -1;
        if (this.isBorderColide(windowWidth, windowHeigth, this.getWest()) || this.isBorderColide(windowWidth, windowHeigth, this.getEast())) this.xSpeed *= -1;
    }
    /**
     * This is the toString method of the
     * */
    public String toString() {
        return "Center : (" + this.getCenterX() + ", " + this.getCenterY() + ")\nRadius : " + this.getRadius() + "\nSpeed on the x axis : " + this.xSpeed + "\nSpeed on the y axis : " + this.ySpeed;
    }
}
