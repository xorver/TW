package runnables;

import buffers.Buffer;

public class P implements Runnable {
    private Buffer b1;
    private int indx;

    public P(Buffer b1, int indx) {
        this.b1 = b1;
        this.indx = indx;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (true)
                if(b1.insertValue(indx))
                    System.out.println(indx + " inserted to b1");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
