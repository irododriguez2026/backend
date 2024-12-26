package project;

/**
 * Main class to initialize and start the simulation.
 */
public class Main {
    public static void main(String[] args) {
    	
    	
        Wall wall = new Wall(3, 10); // Wall with 5 rows and 50 stones per row
        StonePool stonePool = new StonePool(1); // Initial stone count increased to 100
        ToolSemaphore tools = new ToolSemaphore(5); // Increase tool count to 5

        // Create multiple workers
        Thread[] workers = new Thread[] {
            new Thread(new Searcher(1, stonePool)),
            new Thread(new Searcher(2, stonePool)),
            new Thread(new Transporter(3, stonePool, tools, wall)),
            new Thread(new Transporter(4, stonePool, tools,wall)),
            new Thread(new Mason(5, wall)),
            new Thread(new Searcher(6, stonePool)),
            new Thread(new Searcher(7, stonePool)),
            new Thread(new Transporter(8, stonePool, tools, wall)),
            new Thread(new Transporter(9, stonePool, tools,wall)),
            new Thread(new Searcher(10, stonePool)),
            new Thread(new Searcher(11, stonePool)),
            new Thread(new Transporter(12, stonePool, tools, wall)),
            new Thread(new Transporter(13, stonePool, tools,wall))
        };

        // Start all worker threads
        for (Thread worker : workers) {
            worker.start();
        }
    }
}
