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
        Integer toReturn = null;
        semaphore.down();
            if(!this.isEmpty()){
                inserted--;
                toReturn = val[inserted];
            }
        semaphore.up();
        return toReturn;

    }

    public boolean insertValue(int v) throws InterruptedException {
        boolean toReturn=false;
        semaphore.down();
            if(!this.isFull()){
                val[inserted]=v;
                inserted++;
                toReturn=true;
            }
        semaphore.up();
        return toReturn;
    }

    public int[] consumeAll() throws InterruptedException {
        int[] toReturn=null;
        semaphore.down();
            if(this.isFull()){
                inserted=0;
                toReturn = val.clone();
            }
        semaphore.up();
        return toReturn;
    }

    private boolean isEmpty() {
        return inserted==0;
    }

    private boolean isFull(){
        return N==inserted;
    }
}
