package zad4310;

public class A implements Runnable {

    private Monitor monitor;

    public A(Monitor monitor) {
        this.monitor = monitor;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while(true)
            try {
                Monitor.Resource resource = monitor.getA();
                System.out.println("Process A has obtained resource: "+resource.type);
                monitor.returnResource(resource);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
