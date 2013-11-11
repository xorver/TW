package producerconsumer.buffersize1;

public class P implements Runnable {

    private Buffer b;
    private int id;

    public P(Buffer b,int id) {
        this.b = b;
        this.id = id;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (true){
                b.produce(id);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
