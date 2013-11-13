package zad431;

public class RunnableA implements Runnable {
    Monitor monitor;

    public RunnableA(Monitor monitor) {
        this.monitor = monitor;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while(true){
            try {
                Resource a = monitor.getA();
                System.out.println(Thread.currentThread()+" A"+a.id);
                monitor.releaseA(a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
