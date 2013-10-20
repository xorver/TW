package runnables;

import buffers.Buffer;

public class S implements Runnable {
    private Buffer b1;
    private Buffer b2;

    public S(Buffer b1, Buffer b2) {
        this.b1 = b1;
        this.b2 = b2;
    }


    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while(true)
                b2.insertValue(b1.getValue()+b1.getValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
