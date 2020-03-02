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
	
	
	//calculate the output values of feeding forward through the NN
	public float[] output(float[] inputArr) {
		
		
		//convert array to matrix
		
		Matrix inputs = woh.singleColumnMatrixFromArray(inputArr);
		
		//add bias
		Matrix inputBias = inputs.addBias();
		
		//calculate based on the output too...........
		
		
		//apply layer of one weight to the inputs
		Matrix hiddenInputs = whi.dot(inputBias);
		
		
		//pass through activation function(sigmoid)		
		Matrix hiddenOutputs = hiddenInputs.activate();
		
		//add bias
		Matrix hiddenOutputsBias = hiddenOutputs.addBias();
		
		//apply layer to two weights
		Matrix hiddenInputs2 = whh.dot(hiddenOutputsBias);
		Matrix hiddenOutputs2 = hiddenInputs2.activate();
		Matrix hiddenOutputBias2 = hiddenOutputs2.addBias();
		
		//apply level 3 weights
		Matrix outputInputs = woh.dot(hiddenOutputBias2);
		//pass through sigmoid function
		
		Matrix outputs = outputInputs.activate();
		
		return outputs.toArray();
		
		
	}
	
	
	
}
