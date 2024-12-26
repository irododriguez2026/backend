package project;

/**
 * Abstract base class for all worker types.
 */
public abstract class Worker implements Runnable {
    protected final int workerId;

    public Worker(int workerId) {
        this.workerId = workerId;
    }

    public abstract void work();

    @Override
    public void run() {
        work();
    }
}

class Searcher extends Worker {
    private final StonePool stonePool;

    public Searcher(int workerId, StonePool stonePool) {
        super(workerId);
        this.stonePool = stonePool;
    }

    @Override
    public void work() {
        while (true) {
            stonePool.addStone();
            System.out.println("Searcher " + workerId + " found a new stone and add it to the pool.");
            try {
                Thread.sleep(1000); // Simulate search time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Transporter extends Worker {
    private final StonePool stonePool;
    private final ToolSemaphore tools;
    private Wall wall;

    public Transporter(int workerId, StonePool stonePool, ToolSemaphore tools, Wall wall) {
        super(workerId);
        this.stonePool = stonePool;
        this.tools = tools;
        this.wall = wall;
    }

    @Override
    public void work() {
        while (true) {
            try {
                tools.acquireTool(); // Acquire a tool
                if (stonePool.takeStone()) {
                    System.out.println("Transporter " + workerId + " took a stone.");
                    Thread.sleep(500); // Simulate transport time
                    wall.deliverStone(); // Deliver the stone to the wall's reserve
                    System.out.println("Transporter " + workerId + " delivered a stone to the wall.");
                } else {
                    System.out.println("Transporter " + workerId + " found no stones available.");
                    Thread.sleep(500); // Wait before retrying
                }
                tools.releaseTool(); // Release the tool
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}

class Mason extends Worker {
    private final Wall wall;

    public Mason(int workerId, Wall wall) {
        super(workerId);
        this.wall = wall;
    }

    @Override
    public void work() {
        while (!wall.isComplete()) {
            try {
                if (!wall.addStone(workerId)) {
                    Thread.sleep(500); // Wait if the current row is full
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Mason " + workerId + " has finished constructing the wall!");
        System.exit(0); // Terminate the program when the wall is complete
    }

}
