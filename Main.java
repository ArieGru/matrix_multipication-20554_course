/**
 * This project demonstrates how to implement a matrix multiplication using concurrency in java.
 *
 * maman 15 question 2, 2020b
 * Advanced java programming
 *
 * @author Arie Gruber
 *
 */
import java.util.Scanner;

public class Main {
	private static Scanner scan;
	/**
	 * Main method for testing. we will ask the user to give us dimensions for 2 matrices, then we will generate
	 * two random matrices with the given dimensions and print the result of the multiplication.
	 */
	public static void main(String[] args) {
		MyMatrix firstMatrix;
		MyMatrix secondMatrix;
		int firstMatrixRows = 0;
		int firstMatrixCols = 0;
		int secondMatrixRows = 0;
		int secondMatrixCols = 0;
		scan = new Scanner(System.in);
		// get input from user. if the input was not valid exit the program with an error.
		try {
			System.out.println("How many rows for the first matrix?");
			firstMatrixRows = getInput();
			System.out.println("How many columns for the first matrix?");
			firstMatrixCols = getInput();
			System.out.println("How many rows for the second matrix?");
			secondMatrixRows = getInput();
			System.out.println("How many columns for the second matrix?");
			secondMatrixCols = getInput();
		} catch(MatrixSizeException e) {
			e.printStackTrace();
			System.out.println("Input is not valid. program exit with an error");
			System.exit(1);
		}
		try {
			firstMatrix = new MyMatrix(firstMatrixRows, firstMatrixCols, true);		// create new random matrix
			secondMatrix = new MyMatrix(secondMatrixRows, secondMatrixCols, true);	// create new random matrix
			System.out.println("First matrix:");
			firstMatrix.printMatrix();		// print the first matrix
			System.out.println();
			System.out.println("Second matrix matrix:");
			secondMatrix.printMatrix();		// print the second matrix
			System.out.println();
			System.out.println("Multiplication result:");
			MyMatrix.multiplyAndPrint(firstMatrix, secondMatrix);
		} catch (MatrixSizeException e) {

		}
		scan.close();
	}

	/**
	 * this function gets input from the user and stores it in the proper variables.
	 */
	private static int getInput() throws MatrixSizeException {
		int input;
		while(scan.hasNext() && !scan.hasNextInt()) {
			scan.next();
		}
		if(scan.hasNextInt() && (input = scan.nextInt()) > 0) {
			return input;
		} else {
			throw new MatrixSizeException();
		}
	}
}
