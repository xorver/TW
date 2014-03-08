package threads;

import activeobject.Future;
import activeobject.Proxy;
import properties.Properties;

import java.util.ArrayList;
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
                //Prepare-values-------------------
                int n=(int)Math.ceil(Math.random()*Properties.HALF_BUFFER_SIZE);
                List<Integer> argument = new ArrayList<>(n);
                for(int i=0;i<n;i++)
                    argument.add(i);
                for(int i=0;i<Properties.PRODUCER_OPERATIONS;i++)
                    Math.random();
                //---------------------------------

                //Send-values-to-buffer------------
                Future future=proxy.produce(argument);
                future.get();
                //---------------------------------

                //Say-what-are-you-doing-----------
//                System.out.println("Producing values: ");
//                for(int i:argument)
//                    System.out.print(i+" ");
//                System.out.println();
                //---------------------------------
                operations++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
