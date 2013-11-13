package zad431;

import common.Resource;

public class RunnableB implements Runnable {
    Monitor monitor;

    public RunnableB(Monitor monitor) {
        this.monitor = monitor;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while(true){
            try {
                Resource b = monitor.getB();
                System.out.println(Thread.currentThread()+" B"+b.id);
                monitor.releaseB(b);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
