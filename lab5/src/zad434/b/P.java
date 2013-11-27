package zad434.b;

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
                Integer index = monitor.getForProduction();
                System.out.println(Thread.currentThread().getName()+" produkuje: "+index);
                monitor.returnFromProduction(index);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
