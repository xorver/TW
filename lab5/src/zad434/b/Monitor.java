package zad434.b;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    Queue<Integer> full=new LinkedList<>();
    Queue<Integer> empty=new LinkedList<>();
    boolean[] isFull = new boolean[Properties.N];
    Lock lockFull = new ReentrantLock();
    Condition fullCondition = lockFull.newCondition();
    Lock lockEmpty = new ReentrantLock();
    Condition emptyCondition = lockEmpty.newCondition();
    Lock resource[] = new Lock[Properties.N];
    Condition[] resourceCondition = new Condition[Properties.N];

    public Monitor() {
        lockEmpty.lock();
        for(int i=0;i< Properties.N;i++){
            empty.add(i);
            resource[i]=new ReentrantLock();
            resourceCondition[i]=resource[i].newCondition();
        }
        lockEmpty.unlock();

    }

    public Integer getForProduction() throws InterruptedException {
        lockEmpty.lock();
        while(empty.isEmpty())
            emptyCondition.await();
        Integer toReturn = empty.poll();
        lockEmpty.unlock();

        lockFull.lock();
        full.add(toReturn);
        fullCondition.signal();
        lockFull.unlock();
        return toReturn;
    }
    public int getForConsumption() throws InterruptedException {
        lockFull.lock();
        while(full.isEmpty())
            fullCondition.await();
        Integer probablyFullResource = full.poll();
        lockFull.unlock();
        resource[probablyFullResource].lock();
        while(!isFull[probablyFullResource])
            resourceCondition[probablyFullResource].await();
        resource[probablyFullResource].unlock();
        return probablyFullResource;
    }
    public void returnFromProduction(Integer i){
        resource[i].lock();
        isFull[i]=true;
        resourceCondition[i].signal();
        resource[i].unlock();
    }
    public void returnFromConsumption(Integer i){
        resource[i].lock();
        isFull[i]=false;
        resource[i].unlock();
        lockEmpty.lock();
        empty.add(i);
        emptyCondition.signal();
        lockEmpty.unlock();
    }

}
