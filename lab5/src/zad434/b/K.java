package zad434.b;

public class K implements Runnable {

    private Monitor monitor;

    public K(Monitor monitor) {
        this.monitor = monitor;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while(true)
            try {
                int index = monitor.getForConsumption();
                System.out.println(Thread.currentThread().getName()+" konsumuje: "+index);
                monitor.returnFromConsumption(index);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }
}