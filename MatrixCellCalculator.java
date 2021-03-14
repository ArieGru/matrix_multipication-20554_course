/**
 * This class calculates the multiplication of two matrices concurrently.
 */
public class MatrixCellCalculator extends Thread {
	private int row;		// wanted cell's row
	private int column;		// wanted cell's column
	private MyMatrix firstMatrix;		// original first matrix for the multiplication
	private MyMatrix secondMatrix;		// original second matrix for the multiplication
	private int result;		// the result of the multiplication. our final goal.
	PrintResult monitor;	// monitor object to prevent concurrency problems.

	/**
	 * Constructor.
	 * Creates new object with the given parameters and sets the goal of the multiplication.
	 *
	 * @param row The wanted cell's row
	 * @param column The wanted cell's column
	 * @param firstMatrix First matrix of the multiplication
	 * @param secondMatrix Second matrix of the multiplication
	 * @param monitor Monitor object to prevent concurrency problems
	 * @throws MatrixMultiNotDefinedException In case that the multiplication of the two matrices is not defined, usually
	 * 			because the number of columns in the first matrix is not the same as the number of rows in the second one.
	 */
	public MatrixCellCalculator(int row, int column, MyMatrix firstMatrix, MyMatrix secondMatrix, PrintResult monitor)
			throws MatrixMultiNotDefinedException{
		// check if the number of columns in the first matrix is the same as the number of rows in the second one, so the
		// multiplication is defined
		if(firstMatrix.numberOfColumns() != secondMatrix.numberOfRows())
			throw new MatrixMultiNotDefinedException();
		// set the object parameters
		this.row = row;
		this.column = column;
		this.firstMatrix = firstMatrix;
		this.secondMatrix = secondMatrix;
		this.result = 0; // initialize the result to 0
		this.monitor = monitor;
	}

	/**
	 * The code that the thread will execute for the actual calculation.
	 */
	public void run() {
		// if the row or column is out of matrix bounds, print a message and exit with error
		try {
			// calculate the cell's value.
			for(int i = 0; i < firstMatrix.numberOfColumns(); i++)
				result += (firstMatrix.getNumber(row, i) * secondMatrix.getNumber(i, column));
			// wait for all previous cells threads to finish calculation and print their result before your turn
			monitor.waitForYourTurn(row, column);
			// print this threads cell result
			monitor.printCell(result);
		} catch (MatrixBoundsException e) {
			System.out.println("Illegal row/column");
			System.exit(1);
		}
	}
}
