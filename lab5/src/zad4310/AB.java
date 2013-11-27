package zad4310;

public class AB implements Runnable {
    private Monitor monitor;

    public AB(Monitor monitor) {
        this.monitor = monitor;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while(true)
            try {
                Monitor.Resource resource = monitor.getAB();
                System.out.println("Process AB has obtained resource: "+resource.type);
                monitor.returnResource(resource);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}