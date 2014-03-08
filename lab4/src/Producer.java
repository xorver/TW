import java.util.List;

public class Producer implements Runnable {

    private Monitor m;
    public boolean active=true;
    public int operations=0;
    private Buffer buffer;

    public Producer(Monitor m,Buffer buffer) {
        this.m = m;
        this.buffer = buffer;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (active){
                //Prepare-values---------------------
                for(int i=0;i<Properties.PRODUCER_OPERATIONS;i++)
                    Math.random();
                //-----------------------------------

                //Ask-for-buffer-space---------------
                int n=(int)Math.ceil(Math.random()*Properties.HALF_BUFFER_SIZE);
                List<Integer> forProduction = m.lockProduction(n);
                //-----------------------------------

                //Fill-buffer------------------------
                for(Integer i:forProduction)
                    buffer.resources[i]=i;
                //-----------------------------------

                //Say-what-are-you-doing-------------
//                System.out.println("Producing values on: ");
//                for(int i:forProduction)
//                    System.out.print(i+" ");
//                System.out.println();
                //-----------------------------------

                //Return-buffer-space----------------
                m.unlockProduction(forProduction);
                //-----------------------------------

                operations++;

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
