import java.util.List;

public class Consumer implements Runnable {

    private final Buffer buffer;
    private Monitor m;
    public boolean active=true;
    public int operations=0;

    public Consumer(Monitor m, Buffer buffer) {
        this.m = m;
        this.buffer = buffer;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (active){
                //Ask-for-buffer-space---------------
                int n=(int)Math.ceil(Math.random()*Properties.HALF_BUFFER_SIZE);
                List<Integer> forConsumption = m.lockConsumption(n);
                //------------------------------------

                //Get-values--------------------------
                int tmp;
                for(Integer i:forConsumption)
                    tmp=buffer.resources[i];
                //------------------------------------

                //Process-values----------------------
                for(int i=0;i<Properties.CONSUMER_OPERATIONS;i++)
                    Math.random();
                //------------------------------------

                //Say-what-are-you-doing--------------
//                System.out.println("Consuming values from: ");
//                for(int i:forConsumption)
//                    System.out.print(i+" ");
//                System.out.println();
                //------------------------------------

                //Return-buffer-space-----------------
                m.unlockConsumption(forConsumption);
                operations++;
                //------------------------------------
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

