package com.hagincodes.snakeai;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Snake extends Point{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int xCoor, yCoor, width, height;
	
	int x = 600;
	int y = 200;
	int len = 1;
	Point pos; //position of the hea do the snake
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
	
	int growCount = 0; // the amount the snake still needs to grow
	
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
	
	//move the snake in the direction of vel Point
	public void move() {
		//move through time
		
		lifetime+= 1;
		leftToLive -= 1;
		
		//if the time left to live is up then the snake will K.I.S lol
		if (leftToLive < 0) {
			alive = false;
		}
		
		//if the snake hit the edge or itself, terminate life
		if(gonnaDie(pos.x + vel.x, pos.y + vel.y)) {
			alive = false;
		}
		
		//if snake is on the food position we munch it up.
		if(pos.x + vel.x == munchy.pos.x && pos.y + vel.y == munchy.pos.y) {
			eat(); //yum yum
		}
	
		
		//snake grows 1 square at a time so if the snake has recently eaten the grow count will be greater than 0
		if(growCount > 0) {
			growCount --;
			grow();
		} else {
			for(int i = 0; i < tailPositions.size() - 1; i++) {
				temp = new Point(tailPositions.get(i+1).x, tailPositions.get(i+1).y);
				tailPositions.set(i, temp);
			}
			temp = new Point(pos.x, pos.y);
			tailPositions.set(len-2, temp);
		}
		
		//make the head change position
		((Snake) pos).add(vel);
			
	}
	
	//the snake just ate some food
	public void eat() {
		
		//reset food position 
		munchy = new Food();
		while(tailPositions.contains(munchy.pos)) {
			munchy = new Food();
		}
		
		//increase time left to live.
		leftToLive += 100;
		
		
		// if we are testing then grow by 4 it if not the snake is still small grow +1
		// this is for helping snake evolve so they don't get too big. 
		if(test|| len>=10) {
			growCount += 4;
		} else {
			growCount += 1;
		}
	}
	
	public boolean gonnaDie(float x, float y) {
		//check if hit wall
		
		  if(x < 0 || x > 49 || y < 0 || y > 49) {
	        	return true;
	        }
		  
		  return isOnTail(x,y);
	}
	
	
	public void grow() {
		
		temp = new Point(pos.x, pos.y);
		tailPositions.add(temp);
		len += 1;
		
	}
	
	
	//returns true if the coordinates on the snakes tail
	public boolean isOnTail(float x, float y) {
		
		for(int i = 0; i < tailPositions.size(); i++) {
			if(x == tailPositions.get(i).x && y == tailPositions.get(i).y) {
				return true;
			}
		}
		return false;
	}
	
	
	public void calcFitness() {
		//fitness is after snake has died
		//wa waa waaaa
		
		if(len < 10) {
			fitness = (int) Math.floor(lifetime * lifetime * Math.pow(2,  Math.floor(len)));
			System.out.println("Fitness: " +fitness);
		} else {
			//grows slower after 10 to stop fitness from getting stupidly big
			//ensure it's greater than len = 9
			fitness = lifetime * lifetime;
			fitness *= Math.pow(2, 10);
			fitness *=(len-9);
			
		}
	}
	
	//crossover function for genetic algorithm
	public Snake crossover(Snake partner) {
		Snake child = new Snake();
		
		//child.brain = brain.crossover(partner.brain);
		return child;
	}
	
	public Snake clone() {
		Snake clone = new Snake();
		clone.brain = brain.clone();
		clone.alive = true;
		return clone;
	}
	
	//save the snake to file by making it into table format
	public void saveSnake(int snakeNum, int score, int IDpop) {
		//save the snake top score and its population id
		
		Table snakeMetrics = new Table();
		snakeMetrics.addColumn("Top Score");
		snakeMetrics.addColumn("PopulationID");
		TableRow tr = snakeMetrics.addRow();
		tr.setFloat(0, score);
		tr.setInt(1, IDpop);
		
		//save stable stuff
		//TODO: object call for saveTable, to implement
		
		//save snake brain
		//TODO: saveTable with snake object
		
	}
	
	public Snake loadSnake(int snakeNum) {
		
		Snake load = new Snake();
		Table t = loadTable("data/Snake" + snakeNum + ".csv");
		load.brain.TableToNet(t);
		return load;
	}
	
	 //looks in 8 directions to find food,walls and its tail
	 public void look() {
	    vision = new float[24];
	    //look left
	    float[] tempValues = lookInDirection(new Point(-10, 0));
	    vision[0] = tempValues[0];
	    vision[1] = tempValues[1];
	    vision[2] = tempValues[2];
	    //look left/up  
	    tempValues = lookInDirection(new Point(-10, -10));
	    vision[3] = tempValues[0];
	    vision[4] = tempValues[1];
	    vision[5] = tempValues[2];
	    //look up
	    tempValues = lookInDirection(new Point(0, -10));
	    vision[6] = tempValues[0];
	    vision[7] = tempValues[1];
	    vision[8] = tempValues[2];
	    //look up/right
	    tempValues = lookInDirection(new Point(10, -10));
	    vision[9] = tempValues[0];
	    vision[10] = tempValues[1];
	    vision[11] = tempValues[2];
	    //look right
	    tempValues = lookInDirection(new Point(10, 0));
	    vision[12] = tempValues[0];
	    vision[13] = tempValues[1];
	    vision[14] = tempValues[2];
	    //look right/down
	    tempValues = lookInDirection(new Point(10, 10));
	    vision[15] = tempValues[0];
	    vision[16] = tempValues[1];
	    vision[17] = tempValues[2];
	    //look down
	    tempValues = lookInDirection(new Point(0, 10));
	    vision[18] = tempValues[0];
	    vision[19] = tempValues[1];
	    vision[20] = tempValues[2];
	    //look down/left
	    tempValues = lookInDirection(new Point(-10, 10));
	    vision[21] = tempValues[0];
	    vision[22] = tempValues[1];
	    vision[23] = tempValues[2];
	  }

	
	 float[] lookInDirection(Point direction) {
		    //set up a temp array to hold the values that are going to be passed to the main vision array
		    float[] visionInDirection = new float[3];

		    Point position = new Point(pos.x, pos.y); //the position where we are currently looking for food or tail or wall
		    boolean foodIsFound = false; //true if the food has been located in the direction looked
		    boolean tailIsFound = false; //true if the tail has been located in the direction looked 
		    float distance = 0;
		    //move once in the desired direction before starting 
		    ((Snake) position).add(direction);
		    distance +=1;

		    //look in the direction until you reach a wall
		    while (!(position.x < 400 || position.y < 0 || position.x >= 800 || position.y >= 400)) {

		      //check for food at the position
		      if (!foodIsFound && position.x == munchy.pos.x && position.y == munchy.pos.y) {
		        visionInDirection[0] = 1;
		        foodIsFound = true; // don't check if food is already found
		      }

		      //check for tail at the position
		      if (!tailIsFound && isOnTail(position.x, position.y)) {
		        visionInDirection[1] = 1/distance;
		        tailIsFound = true; // don't check if tail is already found
		      }

		      //look further in the direction
		      ((Snake) position).add(direction);
		      distance +=1;
		    }

		    //set the distance to the wall
		    visionInDirection[2] = 1/distance;
		    
		    return visionInDirection;
		  }
		
	
	
	public Point add(Point other){
        this.x += other.x;
        this.y += other.y;
        return this;
    }
	
	public void show(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(pos.x * 10, pos.y * 10, width, height);
		
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
