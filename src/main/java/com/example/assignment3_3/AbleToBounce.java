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
     * @param centerX : this double variable store the center of the ball on the x.
     * @param centerY : this double variable store the center of the ball on the y.
     * @param radius : this double variable store the radius of the ball.
     * @param xSpeed : this variable store the speed of the ball on the x axis.
     * @param ySpeed : this variable store the speed of the ball ont y axis.
     * */
    public AbleToBounce(double centerX, double centerY, double radius, int xSpeed, int ySpeed) {
        super(centerX, centerY, radius, Color.BLUE);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    /**
     * This is mutator of the xSpeed field.
     * @param xSpeed : this double variable store the new speed of the ball on the x axis.
     * */
    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }
    /**
     * This is the mutator of the ySpeed field.
     * @param ySpeed : this double variable store the new speed of the ball on the y axis.
     * */
    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
    /**
     * This is the accessor of the xSpeed field.
     * @return xSpeed : the current speed of the ball on the x axis.
     * */
    public int getXSpeed() {
        return this.xSpeed;
    }
    /**
     * This is the accessor of the ySpeed field.
     * @return ySpeed : th espeed of the ball on the y axis.
     * */
    public int getYSpeed() {
        return this.ySpeed;
    }
    /**
     * This is a mutator object used to get the point locate at the top of circle.
     * @return : the position hight point in the ball.
     * */
    public double[] getNorth() {
        double[] res = { this.getCenterX(), this.getCenterY() - this.getRadius() * 2};
        return res;
    }
    /**
     * This is a mutator object used to get the point locate at the most right part of circle.
     * @return : the point located at the most right part of the circle.
     * */
    public double[] getEast() {
        double[] res = {this.getCenterX() + this.getRadius() * 2, this.getCenterY()};
        return res;
    }
    /**
     * This is a mutator object used to get the point locate at the most left part of circle.
     * @return : the point is located  at the bottom of the circle.
     * */
    public double[] getSouth() {
        double[] res = {this.getCenterX(), this.getCenterY() + this.getRadius() * 2};
        return res;
    }
    /**
     * This is a mutator object used to get the point locate at the bottom of circle.
     * @return : the point located at the most left part of the circle.
     * */
    public double[] getWest() {
        double[] res = {this.getCenterX() - this.getRadius() * 2, this.getCenterY()};
        return res;
    }
    /**
     * This method is used for checking if the ball is touching a wall.
     * @param windowWidth : this int variable store the width of the window.
     * @param windowHeight : this int variable store the height of the window.
     * @param point : this double array variable housed the coordinate of the point.
     * @return : a boolean value, true if the point crossed the border and false if not.
     * */
    public boolean isBorderColide(int windowWidth, int windowHeight, double[] point) {
        return point[0] <= -50 || point[0] >= windowWidth || point[1] <= -50 || point[1] >= windowHeight;
    }
    /**
     * This method call the method isBOrderColide to see if one the four border is touched.
     * @param windowWidth : this int variable store the width of the window.
     * @param windowHeigth : this int variable store the height of the window.
     * */
    public void BorderColideHandler(int windowWidth, int windowHeigth) {
        if (this.isBorderColide(windowWidth, windowHeigth, this.getNorth()) || this.isBorderColide(windowWidth, windowHeigth, this.getSouth())) this.ySpeed *= -1;
        if (this.isBorderColide(windowWidth, windowHeigth, this.getWest()) || this.isBorderColide(windowWidth, windowHeigth, this.getEast())) this.xSpeed *= -1;
    }
    /**
     * This is the toString method of the class.
     * @return : a String containni the important info about the object.
     * */
    public String toString() {
        return "Center : (" + this.getCenterX() + ", " + this.getCenterY() + ")\nRadius : " + this.getRadius() + "\nSpeed on the x axis : " + this.xSpeed + "\nSpeed on the y axis : " + this.ySpeed;
    }
}
