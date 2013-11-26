package zad432;

public class P implements Runnable {

    private  int id;
    private Monitor monitor;

    public P(int id, Monitor monitor) {
        this.id = id;
        this.monitor = monitor;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        int j=0;
        while(true)
            try {
                monitor.get(id);
                System.out.println(id+" przetwarza zasob: "+j);
                monitor.add(id);
                j=(j+1)%Properties.M;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
