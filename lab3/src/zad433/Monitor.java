package zad433;

import common.Resource;

public class Monitor {
    private final int M=Properties.M;
    private final Integer monitorC=0;
    private final Integer monitorP=1;

    private Buffer buffer;

    public Monitor() {
        buffer= new Buffer(2*M);
    }

    public void produce(Resource[] resources,int n) throws InterruptedException {
        synchronized (monitorP){
            while(buffer.size>M)
                monitorP.wait();
            buffer.addElements(resources,n);
        }
        if(buffer.size>M)
            synchronized (monitorC) {
                monitorC.notify();
            }
        else
            synchronized (monitorP) {
                monitorP.notify();
            }
    }

    public Resource[] consume(int n) throws InterruptedException {
        Resource[] toReturn;
        synchronized (monitorC) {
            while (buffer.size<=M)
                monitorC.wait();
            toReturn = buffer.getElements(n);
        }
        if(buffer.size>M)
            synchronized (monitorC) {
                monitorC.notify();
            }
        else
            synchronized (monitorP) {
                monitorP.notify();
            }
        return toReturn;
    }
}
