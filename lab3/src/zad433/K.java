package zad433;

public class K implements Runnable {

    private Monitor monitor;

    public K(Monitor monitor1) {
        this.monitor = monitor1;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (true){
                monitor.consume((int)Math.floor(Math.random()*Properties.M));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
