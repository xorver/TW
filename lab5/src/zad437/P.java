package zad437;

public class P implements Runnable {

    private Monitor monitor;

    public P(Monitor monitor) {
        this.monitor = monitor;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while(true) {
            try {
                Integer index = monitor.getPrinter();
                monitor.returnPrinter(index);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
