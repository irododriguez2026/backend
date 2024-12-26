package project;

import java.util.concurrent.*;

/**
 * Manages random events like stone shortages or tool failures.
 */
public class EventManager {
    private final ScheduledExecutorService scheduler;

    /**
     * Constructor for the event manager.
     */
    public EventManager() {
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    /**
     * Starts scheduling events at fixed intervals.
     * @param event The event to run.
     * @param interval The interval between events in milliseconds.
     */
    public void startEvents(Runnable event, long interval) {
        scheduler.scheduleAtFixedRate(event, interval, interval, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops all scheduled events.
     */
    public void stopEvents() {
        scheduler.shutdown();
    }
}
