package zad4313;

public class P implements Runnable{
    private Monitor monitor;

    public P(Monitor monitor) {
        this.monitor = monitor;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while(true)
            try {
                int randCylinder =(int) Math.floor(Math.random()*Properties.C);
                monitor.demandNewPosition(new Demand(randCylinder));
                Thread.yield();
                monitor.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
