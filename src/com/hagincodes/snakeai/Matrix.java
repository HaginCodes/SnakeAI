package com.hagincodes.snakeai;

import java.util.Random;

public class Matrix {

	
	//local variables
	int rows;
	int cols;
	float [][] matrix;
	
	
	Matrix(int r, int c){
		rows = r;
		cols = c;
		
		matrix = new float[rows][cols];
	}
	
	//print matrix
	public void output() {
		for(int i = 0; i<rows;i++) {
			for(int j =0; j<cols;j++) {
				System.out.print(matrix[i][j] + "  ");
			}
			System.out.println(" ");
		}
		System.out.println();
	}
	
	//multiply scalar
	public void multiply(float a) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j<cols; j++) {
				matrix[i][j] *= a;
			}
		}
	}
	
	//return a matrix which is this matrix dot product param matrix
	public Matrix dot(Matrix n) {
		Matrix result = new Matrix(rows, n.cols);
		
		if(cols == n.rows) {
			//for each spot in the new matrix
			
			for(int i = 0; i <rows;i++) {
				for(int j = 0; j<n.cols; j++) {
					float sum = 0;
					for(int k = 0; k<cols;k++) {
						sum+= matrix[i][k]*n.matrix[k][j];
					}
					result.matrix[i][j] = sum;
				}
			}
		}
		return result;
	}
	
	//set the matrix to random ints between -1 and 1
	
	public void randomize() {
		Random ran = new Random();
		for(int i 	= 0; i <rows; i++) {
			for(int j = 0; j<cols;j++) {	
				matrix[i][j] = ran.nextInt(1 + 2) + -1;
			}
		}
	}
	
	//add scalar to the matrix
	public void Add(float n) {
		for (int i = 0; i < rows; i++) {
			for(int j = 0; j<cols; j++) {
				matrix[i][j] += n;
			}
		}
	}
	
	//return a matrix which is a matrix + parameter matrix
	
	public Matrix add(Matrix n) {
		Matrix newMatrix = new Matrix(rows, cols);
		if(cols ==n.cols && rows == n.rows) {
			for (int i=0; i<rows; i++) {
				for(int j=0; j<cols; j++) {
					newMatrix.matrix[i][j] = matrix[i][j] + n.matrix[i][j];
				}
			}
		}
		return newMatrix;
	}
	
	//return a matrix which is the matrix - the parameter
	
	public Matrix subtract(Matrix n) {
		Matrix newMatrix = new Matrix(rows, cols);
		if(cols ==n.cols && rows == n.rows) {
			for (int i=0; i<rows; i++) {
				for(int j=0; j<cols; j++) {
					newMatrix.matrix[i][j] = matrix[i][j] - n.matrix[i][j];
				}
			}
		}
		return newMatrix;
	}
	
	//return a matrix which is the matrix * per-element
	
	public Matrix multiply(Matrix n) {
		Matrix newMatrix = new Matrix(rows, cols);
		if (cols == n.cols && rows == n.rows) {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					newMatrix.matrix[i][j] = matrix[i][j] * n.matrix[i][j];
				}
			}
		}
		return newMatrix;
	}
	
	//return a matrix which is the transposition of the matrix
	
	public Matrix transpose() {
		Matrix n = new Matrix(cols, rows);
		for(int i =0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				n.matrix[j][i] = matrix[i][j];
			}
		}
		return n;
	}
	
	//make a single array from parameter array
	
	public Matrix singleColumnMatrixFromArray(float[] arr) {
		Matrix n = new Matrix(arr.length, 1);
		for(int i = 0; i < arr.length; i++) {
			n.matrix[i][0] = arr[i];
		}
		return n;
	}
	
	//set this matrix from an array
	
	public void fromArray(float[] arr) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j< cols; j++) {
				matrix[i][j] = arr[j+i*cols];
			}
		}
	}
	
	
	//return an array which represents this matrix
	public float[] toArray() {
		float[] arr = new float[rows*cols];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j< cols; j++) {
				arr[j+i*cols] = matrix[i][j];
			}
		}
		return arr;
	}
	
	//for ix1 matrixes add ONE to the bottom
	public Matrix addBias() {
		Matrix n = new Matrix(rows+1, 1);
		for(int i =0; i < rows; i++) {
			n.matrix[i][0] = matrix[i][0];
		}
		n.matrix[rows][0] = 1;
		return n;
	}
	
	//applies the activation function(sigmoid) to each element of the matrix 
	
	public Matrix activate() {
		Matrix n = new Matrix(rows, cols);
		for(int i=0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				n.matrix[i][j] = sigmoid(matrix[i][j]);
			}
		}
		return n;
	}
	
	//sigmoid activation function
	public float sigmoid(float x) {
		float y = (float) (1 / (1 + Math.pow((float)Math.E, -x)));
		return y;
	}
	
	//return the matrix that is derived from sig func of curr matrix
	
	public Matrix sigmoidDerived() {
		Matrix n = new Matrix(rows, cols);
		for(int i = 0; i< rows; i++) {
			for(int j = 0; j<cols; j++) {
				n.matrix[i][j] = (matrix[i][j] * (1-matrix[i][j]));
			}
		}
		return n;
	}
	
	//return the matrix which is the matrix with the bottom layer removed
	
	public Matrix removeBottomLayer() {
		Matrix n = new Matrix(rows-1, cols);
		for (int i = 0; i <n.rows; i++) {
			for(int j = 0; j<cols; j++) {
				n.matrix[i][j] = matrix[i][j];
			}
		}
		return n;
	}
	
	//Mutation function for genetic algorithm
	
	public void mutate(float mutationRate) {
		
		Random r = new Random();
		//for each element in the matrix
		for(int i =0; i<rows; i++) {
			for(int j = 0; j<cols; j++) {
				float rand = r.nextFloat();
				if(rand<mutationRate) {
					matrix[i][j] += r.nextGaussian()/5;
					
					if(matrix[i][j]>1) {
						matrix[i][j] += 1;
					}
					if(matrix[i][j] <-1) {
						matrix[i][j] = -1;
					}		
				}
			}
		}
	}
	
	//return a matrix which has a random number of values from this matrix and the rest from the parameter matrix
	
	public Matrix crossover(Matrix partner) {
		Matrix child = new Matrix(rows, cols);
		Random r = new Random();
		
		//pick a random point in the matrix
		int randC = (int) Math.floor(r.nextInt(cols));
		int randR = (int) Math.floor(r.nextInt(rows));
		for (int i = 0; i< rows; i++) {
			for(int j = 0; j<cols;j++) {
				
				
				if((i < randR) || (i==randR && j<=randC)) {
					child.matrix[i][j] = matrix[i][j];
				} else {
					child.matrix[i][j] = partner.matrix[i][j];
				}
			}
		}
		return child;
	}
	
	
	
}
