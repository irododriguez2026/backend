package project;

import java.util.concurrent.Semaphore;

/**
 * Manages the shared tools (e.g., carts) using semaphores.
 */
public class ToolSemaphore {
    private final Semaphore tools;

    /**
     * Constructor for the tool manager.
     * @param toolCount The number of tools available.
     */
    public ToolSemaphore(int toolCount) {
        this.tools = new Semaphore(toolCount);
    }

    /**
     * Acquires a tool for use.
     * @throws InterruptedException If interrupted while waiting for a tool.
     */
    public void acquireTool() throws InterruptedException {
        tools.acquire();
        System.out.println("Tool acquired.");
    }

    /**
     * Releases a tool after use.
     */
    public void releaseTool() {
        tools.release();
        System.out.println("Tool released.");
    }
}
