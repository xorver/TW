package buffers;

import semaphores.BinarySemaphore;

public class Buffer {
    private int N;
    private int[] val;
    private BinarySemaphore semaphore=new BinarySemaphore();
    private int inserted=0;

    public Buffer(int n) {
        N = n;
        val = new int[n];
    }

    public Integer getValue() throws InterruptedException {
        try {
            semaphore.down();
            if(!this.isEmpty()){
                inserted--;
                return val[inserted];
            }
            else
                return null;
        } finally {
            semaphore.up();
        }
    }

    public void insertValue(int v) throws InterruptedException {
        semaphore.down();
            if(!this.isFull()){
                val[inserted]=v;
                inserted++;
            }
        semaphore.up();
    }

    public void consumeAll() throws InterruptedException {
        semaphore.down();
            if(this.isFull())
                inserted=0;
        semaphore.up();
    }

    private boolean isEmpty() {
        return inserted==0;
    }

    private boolean isFull(){
        return N==inserted;
    }
}
