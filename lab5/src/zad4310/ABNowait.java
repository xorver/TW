package zad4310;

public class ABNowait implements Runnable {
    private Monitor monitor;

    public ABNowait(Monitor monitor) {
        this.monitor = monitor;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while(true) {
            Monitor.Resource resource = monitor.getABNowait();
            if(resource!=null) {
                System.out.println("Process ABNowait has obtained resource: "+resource.type);
                monitor.returnResource(resource);
            }
        }
    }
}