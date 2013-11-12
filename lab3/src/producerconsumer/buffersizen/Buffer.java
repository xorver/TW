package producerconsumer.buffersizen;

public class Buffer {
    int[] buffer;
    private Integer empty;
    private Integer top;
    private final Integer monitorProduce=0;
    private final Integer monitorTop=1;
    private final Integer monitorConsume =2;

    public Buffer() {
        buffer = new int[Properties.N];
        empty=Properties.N;
        top=0;
    }

    public void produce(int val) throws InterruptedException{
        synchronized(monitorProduce){
            while(empty==0)
                monitorProduce.wait();
            buffer[top]=val;
            synchronized (monitorTop){
                top=(top+1)%Properties.N;
                empty--;
            }
            System.out.println(Thread.currentThread().getName() +" Produced "+val);
        }
        synchronized (monitorConsume) {
            monitorConsume.notify();
        }

    }

    public int consume() throws InterruptedException{
        int toReturn;
        synchronized (monitorConsume){
            while(empty==Properties.N)
                monitorConsume.wait();
            synchronized (monitorTop){
                toReturn = buffer[(top+empty)%Properties.N];
                empty++;
            }
            System.out.println(Thread.currentThread().getName() + " Consumed: " + toReturn);
        }
        synchronized (monitorProduce){
            monitorProduce.notify();
        }
        return toReturn;

    }
}
