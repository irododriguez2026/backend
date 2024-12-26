package project;

import java.util.concurrent.locks.*;

/**
 * Manages the pool of available stones.
 */
public class StonePool {
    private int availableStones;
    private final Lock lock;

    /**
     * Constructor for the stone pool.
     * @param initialStones The initial number of stones available.
     */
    public StonePool(int initialStones) {
        this.availableStones = initialStones;
        this.lock = new ReentrantLock();
    }

    /**
     * Adds a stone to the pool.
     */
    public void addStone() {
        lock.lock();
        try {
            availableStones++;
            System.out.println("Stone added. Total stones: " + availableStones);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Takes a stone from the pool if available.
     * @return True if a stone was successfully taken; otherwise, false.
     */
    public boolean takeStone() {
        lock.lock();
        try {
            if (availableStones > 0) {
                availableStones--;
                System.out.println("Stone taken. Stones left: " + availableStones);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
}
