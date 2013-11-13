package zad433;

public class P implements Runnable {

    private Monitor monitor;

    public P(Monitor monitor) {
        this.monitor = monitor;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (true){
                monitor.produce(null,(int)Math.floor(Math.random()*Properties.M));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
