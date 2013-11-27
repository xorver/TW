package zad434.a;

public class P implements Runnable {

    private Monitor monitor;

    public P(Monitor monitor) {
        this.monitor = monitor;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while(true) {
            Integer index = monitor.getForProduction();
            if(index!=null){
                System.out.println(Thread.currentThread().getName()+" produkuje: "+index);
                monitor.returnFromProduction(index);
            }
        }
    }
}
