package com.hagincodes.snakeai;


import java.util.Random;

import javax.swing.JFrame;

public class Screen {

	public Screen() {
		
		JFrame frame = new JFrame();
		GamePanel gamepanel = new GamePanel();
		
		frame.add(gamepanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Screen AI");
		frame.pack();

		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//new Screen();
		
        //int number = Math.random.nextInt(2 - -2) + -2; 
		Random r = new Random();
		for(int i = 0; i < 100; i++) {
            System.out.println(r.nextInt(1 + 2) + -1);
            
            //random.nextInt(30 + 10) - 10;
 

		}
	}

}
