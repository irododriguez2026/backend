package project;

import java.util.concurrent.locks.*;

/**
 * Represents the wall being constructed.
 */
public class Wall {
    private final int[][] wallStructure; // Structure of the wall
    private int currentRow; // Current row being constructed
    private int stonesInReserve; // Stones available for Masons to use
    private final Lock lock; // Lock for synchronization
    private final Condition stonesAvailable; // Condition for stones availability

    /**
     * Constructor for the wall.
     * @param height The number of rows in the wall.
     * @param length The number of stones in each row.
     */
    public Wall(int height, int length) {
        this.wallStructure = new int[height][length];
        this.currentRow = 0;
        this.stonesInReserve = 0;
        this.lock = new ReentrantLock();
        this.stonesAvailable = lock.newCondition();
    }

    /**
     * Transporter delivers a stone to the wall's reserve.
     */
    public void deliverStone() {
        lock.lock();
        try {
            stonesInReserve++;
            System.out.println("Stone delivered to the wall. Stones in reserve: " + stonesInReserve);
            stonesAvailable.signal(); // Notify Masons that a stone is available
        } finally {
            lock.unlock();
        }
    }

    /**
     * Adds a stone from the reserve to the current row of the wall.
     * @param workerId The ID of the Mason placing the stone.
     * @return True if the stone is placed successfully; otherwise, false.
     * @throws InterruptedException If the thread is interrupted while waiting for a stone.
     */
    public boolean addStone(int workerId) throws InterruptedException {
        lock.lock();
        try {
            // Wait until there is a stone in the reserve
            while (stonesInReserve == 0) {
                System.out.println("Mason " + workerId + " is waiting for stones.");
                stonesAvailable.await(); // Wait for stones to be delivered
            }

            // Place the stone in the current row
            for (int i = 0; i < wallStructure[currentRow].length; i++) {
                if (wallStructure[currentRow][i] == 0) {
                    wallStructure[currentRow][i] = workerId;
                    stonesInReserve--; // Consume a stone from the reserve
                    System.out.println("Mason " + workerId + " placed a stone at row " + currentRow + ", column " + i);

                    // Check if the current row is complete
                    if (isRowComplete()) {
                        currentRow++;
                    }
                    return true;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Checks if the wall construction is complete.
     * @return True if the wall is complete; otherwise, false.
     */
    public boolean isComplete() {
        return currentRow >= wallStructure.length;
    }

    /**
     * Checks if the current row is completely filled with stones.
     * @return True if the row is complete; otherwise, false.
     */
    private boolean isRowComplete() {
        for (int cell : wallStructure[currentRow]) {
            if (cell == 0) return false;
        }
        return true;
    }
}
