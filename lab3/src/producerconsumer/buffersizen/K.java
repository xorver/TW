package producerconsumer.buffersizen;


public class K implements Runnable {

    private Buffer b;

    public K(Buffer b) {
        this.b = b;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (true){
                b.consume();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

