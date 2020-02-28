package com.hagincodes.snakeai;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Food {
	
	
	Point pos; // position for the food
	Random r;
	//Constructor
	Food(){
		
		r = new Random();
		pos = new Point();
		
		pos.x = r.nextInt(49);
		pos.y = r.nextInt(49);
 	
		
	}

	

	
	public void show(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(pos.x, pos.y, 10, 10);
		
	}
	
	public Food clone() {
		Food clone = new Food();
		clone.pos = new Point(pos.x, pos.y);
		
		return clone;
	}
	
	public int getxCoor() {
        return pos.y;
    }
    public void setxCoor(int x) {
        this.pos.x = x;
    }
    public int getyCoor() {
        return pos.y;
    }
    public void setyCoor(int y) {
        this.pos.y = y;
    }
	
	
}
	