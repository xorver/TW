package zad437;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    Queue<Integer> availble =new LinkedList<>();
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public Monitor() {
        for(int i=0;i<Properties.N;i++)
            availble.add(i);

    }

    public Integer getPrinter() throws InterruptedException {
        Integer toReturn;
        lock.lock();
        while(availble.isEmpty())
            condition.await();
        toReturn=availble.poll();
        System.out.println(Thread.currentThread().getName() + " zaczyna drukowac na: "+toReturn);
        lock.unlock();
        return toReturn;
    }

    public void returnPrinter(Integer i){
        lock.lock();
        availble.add(i);
        System.out.println(Thread.currentThread().getName() + " skonczyl drukowac na: "+i);
        condition.signal();
        lock.unlock();
    }


}
