import java.util.List;

public class P implements Runnable {

    private Buffer b;
    private Monitor m;

    public P(Buffer b,Monitor m) {
        this.b = b;
        this.m = m;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (true){
                List<Integer> forProduction = m.lockProduction((int)Math.floor(Math.random()*b.SIZE));
                try {
                    System.out.println("Producing values on: ");
                    for(int i:forProduction)
                        System.out.print(i+" ");
                    System.out.println();
                } finally {
                    m.unlockProduction(forProduction);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
