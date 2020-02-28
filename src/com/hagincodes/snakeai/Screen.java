package com.hagincodes.snakeai;


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
		
		new Screen();
	}

}
