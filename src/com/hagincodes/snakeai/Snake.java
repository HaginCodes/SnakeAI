package com.hagincodes.snakeai;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Snake {
	
	private int xCoor, yCoor, width, height;
	
	int x = 600;
	int y = 200;
	int len = 1;
	Point pos;
	ArrayList<Point> tailPositions;
	Point vel; // the velocity of the snake. The direction it will move
	Point temp; //just a temp Point. This will get used a to.
	Food munchy;
	//NeuralNet brain; //the neural net controlling the snake
	float[] vision = new float[24]; //inputs for the NN
	float[] decision; //output of nn
	
	int lifetime = 0; //how long snake boi lived.
	long fitness = 0;// the quality of the snake
	int leftToLive = 200; //the numbers of moves left to live. This prevents snake from looping.
	
	int growCount = 0;// the amount the snake still needs to grow
	
	boolean alive = true; //true if the snake is alive
	boolean test = false; //true if the snake is being tested and not trained.
	
	
	

	public Snake() {
		
		
		int x = 10;
		int y = 20;
		
		pos = new Point(x,y);
		vel = new Point(10, 0);
		tailPositions = new ArrayList<Point>();
		temp = new Point(x-5,y);
		tailPositions.add(temp);
		temp = new Point(x-4,y);
		tailPositions.add(temp);
		temp = new Point(x-3,y);
		tailPositions.add(temp);
		len+=3;
		
		munchy = new Food();
		
		
		//brain = new NeuralNet(24, 18, 4);  <- create NN with 24 inp, 18 hid, 4 out
		leftToLive = 200;
		
	}
	
	//mutate NN, pass mutation rate
	public void mutate(float mr) {
		//TODO: Implement mutate 
		//brain.mutate(mr);
	}
	
	
	//set the velocity(direction) from the output of the network
	
	public void setVelocity() {
		
		//based on the decision from NN
		
		//----- method call to NN 
		
		//Get the max pos from the output of the arr make decision of where to go.
		
		float max = 0;
		int maxIndex = 0;
		for(int i = 0; i < decision.length; i++) {
			if(max < decision[i]) {
				max = decision[i];
				maxIndex = i;
			}
		}
		
		//Now set the velocity.
		if(maxIndex == 0) {
			vel.x =- 1;
			vel.y = 0;
		} else if (maxIndex == 1) {
			vel.x = 0;
			vel.y =- 1;
		} else if (maxIndex == 2) {
			vel.x = 1;
			vel.y = 0;
		} else {
			vel.x = 0;
			vel.y = 1;
		}
		
		
		
	}
	
	
	public void tick() {
		
	}
	
	
	
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(xCoor * width, yCoor * height, width, height);
		
	}
	
	public int getxCoor() {
		return xCoor;
	}
	
	public void setXcoor(int xCoor) {
		this.xCoor = xCoor;
	}
	
	public int getyCoor() {
		return yCoor;
	}
	
	public void setyCoor(int yCoor) {
		this.yCoor = yCoor;
	}
	
}
