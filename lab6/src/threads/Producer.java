package threads;

import activeobject.Future;
import activeobject.Proxy;
import properties.Properties;

import java.util.List;

public class Producer implements Runnable {
    private Proxy proxy;

    public boolean active=true;
    public int operations;

    public Producer(Proxy proxy) {
        this.proxy = proxy;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while(active){
                int n=(int)Math.floor(Math.random()* Properties.HALF_BUFFER_SIZE);
                Future futureIndexes = proxy.lockProduction(n);
                for(int i=0;i<Properties.PRODUCER_OPERATIONS;i++)
                    Math.random();
                List<Integer> indexes = futureIndexes.get();
//                System.out.println("Producing values on: ");
//                for(int i:indexes)
//                    System.out.print(i+" ");
//                System.out.println();
                proxy.unlockProduction(indexes).get();
                operations++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
