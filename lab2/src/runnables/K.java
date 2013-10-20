package runnables;

import buffers.Buffer;

public class K implements Runnable {
    private Buffer b;

    public K(Buffer b) {
        this.b = b;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (true){
                int[] consumed = b.consumeAll();
                if(consumed!=null) {
                    StringBuilder s = new StringBuilder();
                    for(int i:consumed)
                        s.append(i).append(" ");
                    System.out.println("b2 consumed with values: " + s);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
