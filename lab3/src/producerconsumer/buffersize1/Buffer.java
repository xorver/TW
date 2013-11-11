package producerconsumer.buffersize1;

public class Buffer {
    final int N=1;
    int[] buffer;
    public Buffer() {
        buffer = new int[N];
    }

    public synchronized void produce(int val) throws InterruptedException{
        while(buffer[0]!=0)
            this.wait();
        buffer[0]=val;
        this.notifyAll();
        System.out.println(Thread.currentThread().getName() +" Produced "+val);
    }

    public synchronized int consume() throws InterruptedException{
        while(buffer[0]==0)
            this.wait();
        int toReturn = buffer[0];
        buffer[0]=0;
        this.notifyAll();
        System.out.println(Thread.currentThread().getName() +" Consumed: "+toReturn);
        return toReturn;
    }
}
