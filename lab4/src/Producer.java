import java.util.List;

public class Producer implements Runnable {

    private Monitor m;
    public boolean active=true;
    public int operations=0;

    public Producer(Monitor m) {
        this.m = m;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (active){
                List<Integer> forProduction = m.lockProduction((int)Math.floor(Math.random()*Properties.HALF_BUFFER_SIZE));
                for(int i=0;i<Properties.PRODUCER_OPERATIONS;i++)
                    Math.random();
//                System.out.println("Producing values on: ");
//                for(int i:forProduction)
//                    System.out.print(i+" ");
//                System.out.println();
                m.unlockProduction(forProduction);
                operations++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
