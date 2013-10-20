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
            while (true)
                b.consumeAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
