package producerconsumer.buffersize1;

public class Buffer {
    int buffer;

    public synchronized void produce(int val) throws InterruptedException{
        while(buffer!=0)
            this.wait();
        buffer=val;
        this.notifyAll();
        System.out.println(Thread.currentThread().getName() +" Produced "+val);
    }

    public synchronized int consume() throws InterruptedException{
        while(buffer==0)
            this.wait();
        int toReturn = buffer;
        buffer=0;
        this.notifyAll();
        System.out.println(Thread.currentThread().getName() +" Consumed: "+toReturn);
        return toReturn;
    }
}
