package zad434.a;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    Queue<Integer> full=new LinkedList<>();
    Queue<Integer> empty=new LinkedList<>();
    Lock lockFull = new ReentrantLock();
    Lock lockEmpty = new ReentrantLock();
    Condition consumentCondition = lockFull.newCondition();

    public Monitor() {
        lockEmpty.lock();
        for(int i=0;i<Properties.N;i++)
            empty.add(i);
        lockEmpty.unlock();
    }

    public Integer getForProduction(){
        lockEmpty.lock();
        Integer toReturn = empty.poll();
        lockEmpty.unlock();
        return toReturn;
    }
    public int getForConsumption() throws InterruptedException {
        lockFull.lock();
        while(full.isEmpty())
            consumentCondition.await();
        Integer toReturn = full.poll();
        lockFull.unlock();
        return toReturn;
    }
    public void returnFromProduction(Integer i){
        lockFull.lock();
        full.add(i);
        consumentCondition.signal();
        lockFull.unlock();
    }
    public void returnFromConsumption(Integer i){
        lockEmpty.lock();
        empty.add(i);
        lockEmpty.unlock();
    }

}
