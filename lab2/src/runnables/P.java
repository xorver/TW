package runnables;

import buffers.Buffer;

public class P implements Runnable {
    private Buffer b;
    private int indx;

    public P(Buffer b, int indx) {
        this.b = b;
        this.indx = indx;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (true)
                b.insertValue(indx);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
