/**
 * This class is a monitor class that controls the synchronization of the threads that want to print their result to
 * the screen, so that each thread will print it's result only at it's turn - from left to right and from up down.
 */
public class PrintResult {
	private int totalCols;	// total columns in the result matrix. needed for new line signal.
	private int nextNumberRow;	// next cell's row
	private int nextNumberCol;	// next cell's column

	/**
	 * Constructor.
	 * Creates a new monitor object for a result matrix with the given number of columns and initializes the first cell
	 * to print it's result to (0,0) - the upper-left cell.
	 *
	 * @param totalCols Number of columns in the result matrix.
	 */
	public PrintResult(int totalCols) {
		this.nextNumberRow = 0;
		this.nextNumberCol = 0;
		this.totalCols = totalCols;
	}

	/**
	 * Prints the given number to the screen.
	 * If the cell that called this method is the last one in it's row (the rightmost one) then jump to the next line.
	 *
	 * @param number Cell's value
	 */
	public synchronized void printCell(int number) {
		// Print cell's' value to the screen
		System.out.printf("%4d ", number);
		// check if this cell is the rightmost cell in it's row, if so jump to next row.
		if(++nextNumberCol == totalCols) {
			nextNumberCol = 0;
			nextNumberRow++;
			System.out.println();
		}
		// notify all waiting threads that we are ready to print the next cell.
		notifyAll();
	}

	/**
	 * Each thread will pass it's cell's position, and the monitor will manage the printing queue.
	 *
	 * @param row Thread's cell row
	 * @param column Thread's cell column
	 */
	public synchronized void waitForYourTurn(int row, int column) {
		// if it's not this thread's turn to print it's result, wait for your turn.
		while(nextNumberRow != row || nextNumberCol != column) {
			// if there is some interruption when the thread is waiting, print a message to the screen and exit program
			// with an error
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println();
				System.out.println("Interrupted while waiting");
				System.exit(1);
			}
		}
	}

}
