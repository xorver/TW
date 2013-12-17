package threads;

import activeobject.Future;
import activeobject.Proxy;
import properties.Properties;

import java.util.List;

public class Consumer implements Runnable {
    private Proxy proxy;

    public boolean active=true;
    public int operations=0;

    public Consumer(Proxy proxy) {
        this.proxy = proxy;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while(active){
                int n=(int)Math.floor(Math.random()* Properties.HALF_BUFFER_SIZE);
                Future futureIndexes = proxy.lockConsumption(n);
                for(int i=0;i<Properties.CONSUMER_OPERATIONS;i++)
                    Math.random();
                List<Integer> indexes = futureIndexes.get();
//                System.out.println("Consuming values from: ");
//                for(int i:indexes)
//                    System.out.print(i+" ");
//                System.out.println();
                proxy.unlockConsumption(indexes).get();
                operations++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
