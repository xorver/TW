import java.util.List;

public class Consumer implements Runnable {

    private Monitor m;
    public boolean active=true;
    public int operations=0;

    public Consumer(Monitor m) {
        this.m = m;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (active){
                List<Integer> forConsumption = m.lockConsumption((int)Math.floor(Math.random()*Properties.HALF_BUFFER_SIZE));
                for(int i=0;i<Properties.CONSUMER_OPERATIONS;i++)
                    Math.random();
//                System.out.println("Consuming values from: ");
//                for(int i:forConsumption)
//                    System.out.print(i+" ");
//                System.out.println();
                m.unlockConsumption(forConsumption);
                operations++;

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

