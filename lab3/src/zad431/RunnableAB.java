package zad431;

public class RunnableAB implements Runnable {
    Monitor monitor;

    public RunnableAB(Monitor monitor) {
        this.monitor = monitor;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while(true){
            try {
                Monitor.ResourcePair ab = monitor.getAB();
                System.out.println(Thread.currentThread()+" AB("+ab.A.id+"'"+ab.B.id+")");
                monitor.releaseAB(ab);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

