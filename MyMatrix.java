import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * This class represents a matrix
 */
public class MyMatrix {
	private int CELL_UPPER_VALUE = 11;	// upper (exclusive) value for random generated cell
	private int[][] myMatrix;	// The matrix will be stored in this 2D array

	/**
	 * Constructor.
	 * Creates a new matrix of the given size, and can generate random cell values or initialize all cells to 0.
	 *
	 * @param rows Number of rows in the matrix
	 * @param columns Number of columns in the matrix
	 * @param randomMatrix True to generate a random matrix, false to initialize all cells to 0
	 * @throws MatrixSizeException In case of illegal matrix dimension, usually by non-positive number of rows\columns
	 */
	public MyMatrix(int rows, int columns, boolean randomMatrix) throws MatrixSizeException{
		// check if the matrix size is valid, if not throw an exceptoin
		if(rows <= 0 || columns <= 0)
			throw new MatrixSizeException();
		// Create a new matrix, all cells will be 0
		myMatrix = new int[rows][columns];
		// if wanted, generate random cells
		if(randomMatrix)
			initializeRandomMatrix();
	}

	/**
	 * Generate a random values for this matrix, between 0 and CELL_UPPER_VALUE
	 */
	private void initializeRandomMatrix() {
		Random rand = new Random();
		for(int i = 0; i < myMatrix.length; i++) {
			for(int j = 0; j < myMatrix[0].length; j++) {
				myMatrix[i][j] = rand.nextInt(CELL_UPPER_VALUE);
			}
		}
	}

	/**
	 * Prints this matrix
	 */
	public void printMatrix() {
		for(int[] i : myMatrix) {
			for(int j : i) {
				System.out.printf("%4d", j);
			}
			System.out.println();
		}
	}

	/**
	 * The number of rows in this matrix.
	 *
	 * @return The number of rows in this matrix.
	 */
	public int numberOfRows() {
		return myMatrix.length;
	}

	/**
	 * The number of columns in this matrix.
	 *
	 * @return The number of columns in this matrix.
	 */
	public int numberOfColumns() {
		return myMatrix[0].length;
	}

	/**
	 * Get the value in the wanted cell.
	 *
	 * @param row Wanted cell's row
	 * @param column Wanted cell's column
	 * @return The value of the wanted cell in this matrix.
	 * @throws MatrixBoundsException In case that one of the values exceeds matrix bounds.
	 */
	public int getNumber(int row, int column) throws MatrixBoundsException {
		
		if(row < 0 || row >= myMatrix.length || column < 0 || column >= myMatrix[0].length) {
			throw new MatrixBoundsException();
		}
		return myMatrix[row][column];
	}

	/**
	 * A static method that gets two matrices and concurrently multiplies them and prints the result to the screen.
	 *
	 * @param firstMatrix The first matrix of the multiplication
	 * @param secondMatrix The second matrix of the multiplication
	 */
	public static void multiplyAndPrint(MyMatrix firstMatrix, MyMatrix secondMatrix) {
		// each cell of the result matrix will get a different thread that will calculate it's value.
		// calculate how many cells in the result matrix.
		int numberOfThreads = firstMatrix.numberOfRows() * secondMatrix.numberOfColumns();
		// Create a thread pool with the size of the total cells in the result matrix.
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
		// A monitor that will prevent each thread to print it's result before it's time.
		PrintResult monitor = new PrintResult(secondMatrix.numberOfColumns());
		// generate new threads and run them
		for(int row = 0; row < firstMatrix.numberOfRows(); row++) {
			for(int column = 0; column < secondMatrix.numberOfColumns(); column++) {
				// if the multiplication is not defined print a message to the screen and quit program with an error.
				try {
					executor.execute(new MatrixCellCalculator(row, column, firstMatrix, secondMatrix, monitor));
				} catch (MatrixMultiNotDefinedException e) {
					System.out.println("Matrix multiplication is not defined for those matrices");
					System.exit(1);
				}
				
			}
		}
		// shut down the ExecutorService
		executor.shutdown();
	}
}
