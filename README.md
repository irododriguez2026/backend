# Final Project Report

## 1. Implementation Description

### 1.1 Classes Overview
- **Wall Class**: This class is responsible for managing the wall's construction. It maintains the structure of the wall, the current row being built, and the stones available for masons. The synchronization mechanism used in this class is a `Lock` and a `Condition` to ensure that masons wait for stones to be delivered by transporters.
  
- **Transporter Class**: The transporter is responsible for transporting stones from the `StonePool` to the wall. It uses a `ToolSemaphore` to synchronize access to a limited number of tools. The transporter takes a stone from the pool, delivers it to the wall's reserve, and waits if no stones are available in the pool.

- **Mason Class**: Masons place stones in the wall. Before placing a stone, they check if stones are available in the wall's reserve. If no stones are available, the mason waits for a transporter to deliver more. The synchronization in this class is managed using a `Condition` to wait until stones are available.

### 1.2 Synchronization Mechanisms
- **Locks**: We use a `ReentrantLock` in the `Wall` class to protect shared resources, ensuring only one worker can modify the wall's structure at a time.
  
- **Condition Variables**: In the `Wall` class, we use a `Condition` object to signal the masons when a stone is available. If no stones are available, masons will wait until notified by the transporter.
  
- **Semaphore**: In the `ToolSemaphore` class, we use a semaphore to control access to a limited number of tools. This prevents multiple workers from accessing the tools simultaneously and ensures proper synchronization.

### 1.3 Design Choices
- **Threading**: Each worker (Transporter or Mason) operates in its own thread, allowing them to perform their tasks concurrently.
- **Concurrency Management**: We chose to use `Locks` and `Conditions` to handle the dependency between transporters and masons (i.e., masons need stones to work, and transporters need to deliver stones).
  
## 2. Real-Time Execution Screenshot

![Wall Construction Process](path-to-screenshot.png)

*This screenshot shows the wall construction process in real-time, including the delivery of stones and the actions of the masons. Random events, such as a transporter waiting for a stone or a mason waiting for a stone to be delivered, can be observed here.*

### Explanation of the Screenshot
- The first part shows the wall construction step-by-step with workers placing stones and checking if stones are available.
- Random events like a worker waiting or a delay in stone delivery are visible during the process, showing how synchronization mechanisms control the workflow.

## 3. Performance Analysis and Optimizations

### 3.1 Challenges in Managing Concurrency
- **Resource Contention**: Multiple workers (transporters and masons) may attempt to access the same resources (tools, stones) at the same time. This can lead to delays and inefficient execution.
  
- **Waiting Times**: Masons might need to wait for transporters to deliver stones. If transporters are slow or if the stone pool is empty, it leads to idle time for masons.
  
### 3.2 Optimization Solutions
- **Efficient Synchronization**: By using `Locks` and `Conditions` in the `Wall` class, we efficiently manage when a mason should wait for stones and when they can proceed. This minimizes unnecessary waiting.
  
- **Tool Management**: The use of a `ToolSemaphore` optimizes the access to tools, ensuring that no more than a set number of workers can acquire a tool at the same time, reducing contention and improving overall efficiency.

- **Thread Pooling**: In the future, we could optimize the use of threads by employing a thread pool to reduce overhead from creating and destroying threads repeatedly.

## 4. Discussion of Random Events

### 4.1 Impact of Random Events
- **Stone Availability**: Random events, such as the stone pool running out of stones, significantly impact the progress of the wall construction. When no stones are available, masons are forced to wait, slowing down the entire process.
  
- **Transporter Delays**: Random delays in the time it takes for transporters to acquire and deliver stones can lead to bottlenecks. The system must handle these delays efficiently to avoid idle time.

- **Mason Availability**: Random events like a mason finishing their work early or being delayed by other factors can affect the overall efficiency of the wall construction. Proper synchronization ensures that masons do not waste time waiting unnecessarily.

### 4.2 Handling Random Events in the System
- We use synchronization mechanisms to handle waiting times for masons when stones are not available, ensuring that they don't waste resources.
  
- By adding random delays in the transport process (simulated with `Thread.sleep()`), we can observe how these events influence the construction process and analyze system performance during such delays.

## 5. Conclusion
In this project, we designed and implemented a system that simulates the construction of a wall with multiple workers performing concurrent tasks. Through the use of proper synchronization mechanisms like locks, conditions, and semaphores, we effectively managed concurrency and ensured that workers interacted correctly with shared resources. The performance analysis highlighted key challenges and optimization solutions, while the discussion on random events provided insight into how external factors can impact the construction process.

We believe this project demonstrates the importance of synchronization in multi-threaded applications and showcases efficient techniques for managing concurrent processes in a real-time simulation.
