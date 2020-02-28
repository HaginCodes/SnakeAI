package com.hagincodes.snakeai;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
 
    private static final long serialVersionUID = 1L;
 
    public static final int WIDTH = 500, HEIGHT = 500;
    
    private Thread thread;
    private boolean running = false;
 
    private BodyPart b;
    private ArrayList<BodyPart> snake;
 
    private Apple apple;
    private ArrayList<Apple> apples;
    
    private Random r;
    
    private int xCoor = 10;

	private int yCoor = 10;
	
    private int appleX, appleY;
    private int size = 5;
    
    private int ticks = 0;
    
    
    
    public GamePanel() {
    	setFocusable(true);
    	
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
 
        r = new Random();
        snake = new ArrayList<BodyPart>();
        apples = new ArrayList<Apple>();
        
        
        start();
    }
    
    
 
    public void tick() {
    	

        while(snake.size() < 5) {
        	b = new BodyPart(xCoor, yCoor, 10);
        	snake.add(b);
        	xCoor++;
        } 
		
        if(apples.size() == 0) {
        	 appleX = r.nextInt(49);
        	 appleY = r.nextInt(49);
        	 
        	 
        	
        	apple = new Apple(appleX, appleY, 10);
        	apples.add(apple);
        }
                
        
        
        for(int i = 0; i < apples.size(); i++) {        	
        	
        	if(xCoor == apples.get(i).getxCoor() && 
        			yCoor == apples.get(i).getyCoor()) {
        		size++;
        		apples.remove(i);
        		i++;
        		System.out.println("snake length: " + snake.size());
        	}
        }
         
		
		for (int i = 0; i < snake.size(); i++) {
			if (xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
				if (i != snake.size() - 1) {
					stop();
				}
			}
		} 
        
        
        if(xCoor < 0 || xCoor > 49 || yCoor < 0 || yCoor > 49) {
        	stop();
        }
        
        
        ticks++;        
	
    	if(ticks > 1000000) {
        
    		potentialDirections();
    		ticks = 0;

    				      
		}
    }
    
    
    
   
    String directions []= {"left 1, up 1",   "up 1",      "right 1, up 1",
    					   "left 1",                      "right 1",
    					   "left 1, down 1", "down 1",    "right 1, down 1"};
    
    int nextSpot []= new int [2];
   
    
	public void potentialDirections() {
    	//checking 8 potential directions from snake head calculating which is closer
    	
    	int shortest = Integer.MAX_VALUE;
    	int currentShortest = 0;
    	@SuppressWarnings("unused")
		String bestDirection = null;
    	
    	int options [][] = {{xCoor - 1, yCoor + 1}, {xCoor, yCoor ++}, {xCoor + 1, yCoor + 1},
		   			        {xCoor -- , yCoor},                        {xCoor ++, yCoor},
		   			        {xCoor - 1, yCoor - 1}, {xCoor, yCoor --}, {xCoor + 1, yCoor - 1}};

    	//added to calculated distance so that calculation favors (straight paths > path with turns);
    	int turn_costs [] = {1, 0, 1, 0, 0, 1, 0, 1};
    	
    	for(int i = 0; i < options.length; i++) {
    		
    		currentShortest = distFromApple(options[i][0], options[i][1]);
    		currentShortest += turn_costs[i];
    		System.out.println(currentShortest);
			
        	
    		if (currentShortest < shortest) {
    			shortest = currentShortest;
    			bestDirection = directions[i];
    			nextSpot[0] = options[i][0];
    			nextSpot[1] = options[i][1];
    		}
    	}
    	
    	System.out.println("Best distance");
    	System.out.println("Distance: " + shortest + "\nDirection: " + bestDirection);
    	System.out.println();

    	
    	newSpot(nextSpot[0], nextSpot[1]);
    	//return nextSpot;
    }
	
	 public int distFromApple(int dx, int dy) {
		 	
	    	return (Math.abs((appleX - dx) + Math.abs(appleY - dy)));	
	    }
    
    
    
	public boolean newSpot(int newX, int newY) {

		while (xCoor != newX && yCoor != newY) {
			System.out.println("curr: " + xCoor + "," + yCoor + " Destination: " + newX + ", " + newY);
			System.out.println();

			if (xCoor < newX) {
				xCoor++;
				addPart();
				removeLastPart();
			} else if (xCoor > newX) {
				xCoor--;
				addPart();
				removeLastPart();
			} else if (xCoor == newX) {
				xCoor = newX;
			}

			if (yCoor < newY) {
				yCoor++;
				addPart();
				removeLastPart();
			} else if (yCoor > newY) {
				yCoor--;
				addPart();
				removeLastPart();
			}

		}
		return true;
	}    
    
    
    public void addPart() {
    	b = new BodyPart(xCoor, yCoor, 10);
        snake.add(b);
    }
    
    public void removeLastPart() {

		if (snake.size() > size) {
			snake.remove(0);
			System.out.println("removed");
		}
 
    }
    
    //move to the new spot predicted
        
   
 
    public void paint(Graphics g) {
    	g.clearRect(0, 0, WIDTH, HEIGHT);
    	g.setColor(Color.BLACK);
    	g.fillRect(0, 0, WIDTH, HEIGHT);
    	
       g.setColor(Color.WHITE);
        for (int i = 0; i < WIDTH / 10; i++) {
            g.drawLine(i * 10, 0, i * 10, HEIGHT);
        }
        for (int i = 0; i < HEIGHT / 10; i++) {
            g.drawLine(0, i * 10, WIDTH, i * 10);
        }
 
        for (int i = 0; i < snake.size(); i++) {
            snake.get(i).draw(g);
        }
        for(int i = 0; i < apples.size(); i++) {
        	apples.get(i).draw(g);
        }

    }
 
    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }
 
    public void stop() {
    	running = false;
    	try {
			thread.join();
			System.out.println("game over");
			System.exit(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    public void run() {
        while (running) {
            tick();
            repaint();
        }
    } 
}
