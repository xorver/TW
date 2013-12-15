import java.util.List;

public class K implements Runnable {

    private Buffer b;
    private Monitor m;

    public K(Buffer b,Monitor m) {
        this.b = b;
        this.m = m;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (true){
                List<Integer> forConsumption = m.lockConsumption((int)Math.floor(Math.random()*b.SIZE));
                try {
                    System.out.println("Consuming values from: ");
                    for(int i:forConsumption)
                        System.out.print(i+" ");
                System.out.println();
                } finally {
                    m.unlockConsumption(forConsumption);
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

