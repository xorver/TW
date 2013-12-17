import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Monitor {

    private LinkedList<Integer> free=new LinkedList<>();
    private LinkedList<Integer> full=new LinkedList<>();
    private Lock lockFree = new ReentrantLock();
    private Lock lockFull = new ReentrantLock();
    private Condition produceCond = lockFree.newCondition();
    private Condition firstProducerCond = lockFree.newCondition();
    private Condition consumeCond = lockFull.newCondition();
    private Condition firstConsumerCond = lockFull.newCondition();
    private boolean someConsumerIsWaiting =false;
    private boolean someProducerIsWaiting =false;

    public Monitor(){
        for(int i=0;i<2*Properties.HALF_BUFFER_SIZE;i++)
            free.add(i);
    }

    public List<Integer> lockProduction(int n) throws InterruptedException {
        List<Integer> toReturn;
        lockFree.lock();
        try {
            while(someProducerIsWaiting)
                produceCond.await();
            someProducerIsWaiting=true;
            while(free.size()<n)
                firstProducerCond.await();
            someProducerIsWaiting=false;
            produceCond.signal();
            toReturn=new LinkedList<>(free.subList(0,n));
            free.subList(0,n).clear();
        } finally {
            lockFree.unlock();
        }
        return toReturn;
    }

    public void unlockProduction(List<Integer> indexes){
        lockFull.lock();
        try {
            full.addAll(indexes);
            firstConsumerCond.signal();
        } finally {
            lockFull.unlock();
        }
    }

    public List<Integer> lockConsumption(int n) throws InterruptedException {
        List<Integer> toReturn;
        lockFull.lock();
        try {
            while(someConsumerIsWaiting)
                consumeCond.await();
            someConsumerIsWaiting=true;
            while(full.size()<n)
                firstConsumerCond.await();
            someConsumerIsWaiting=false;
            consumeCond.signal();
            toReturn=new LinkedList<>(full.subList(0,n));
            full.subList(0,n).clear();
        } finally {
            lockFull.unlock();
        }
        return toReturn;
    }

    public void unlockConsumption(List<Integer> indexes){
        lockFree.lock();
        try {
            free.addAll(indexes);
            firstProducerCond.signal();
        } finally {
            lockFree.unlock();
        }
    }
}