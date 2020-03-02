package com.hagincodes.snakeai;

public class NeuralNet {

	int iNodes; //No. of input nodes
	int hNodes; //No. of hidden nodes
	int oNodes; //No. of output nodes
	
	Matrix whi; //matrix containing weights between the input nodes and the hidden nodes
	Matrix whh; //matrix containing weights between the hidden notes and second layer hidden nodes
	Matrix woh; //matrix containing weights between the second hidden layer nodes and the output nodes
	
	
	//Constructor
	public NeuralNet(int inputs, int hiddenNo, int outputNo){
		
		//set dimentions from parameters
		iNodes = inputs;
		oNodes = outputNo;
		hNodes = hiddenNo;
		
		//create first layer of weights -> include bias weight
		whi = new Matrix(hNodes, inputs + 1);
		
		//create second layer of weights
		woh = new Matrix(oNodes, hNodes + 1);
		
		
		//set the matricies to random values
		whi.randomize();
		whh.randomize();
		woh.randomize();	
		
	}
	
	//mutation to support the genetic algorithm
	
	public void mutate(float mr) {
		
		whi.mutate(mr);
		whh.mutate(mr);
		woh.multiply(mr);
	}
	
	
	
}
