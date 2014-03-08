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

                //Ask-for-values---------------------
                int n=(int)Math.ceil(Math.random()* Properties.HALF_BUFFER_SIZE);
                Future futureValues = proxy.consume(n);
                List<Integer> values = futureValues.get();
                //-----------------------------------

                //Say-what-are-you-doing-------------
//                System.out.println("Consuming values: ");
//                for(int i:values)
//                    System.out.print(i+" ");
//                System.out.println();
                //-----------------------------------

                //Process-values----------------------
                for(int i=0;i<Properties.CONSUMER_OPERATIONS;i++)
                    Math.random();
                //------------------------------------

                operations++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
