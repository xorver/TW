package zad432;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    int[] readyToWork=new int[Properties.N];
    Lock lock[] = new Lock[Properties.N];
    Condition[] condition = new Condition[Properties.N];

    public Monitor() {
        for(int i=0;i<Properties.N;i++) {
            this.lock[i]=new ReentrantLock();
            this.condition[i]= lock[i].newCondition();
        }
        readyToWork[0]=Properties.M;
    }

    public void get(int i) throws InterruptedException {
        lock[i].lock();
        while(readyToWork[i]==0)
            condition[i].await();
        readyToWork[i]--;
        lock[i].unlock();
    }

    public void add(int i){
        int index=(i+1)%Properties.N;
        lock[index].lock();
        readyToWork[index]++;
        condition[index].signal();
        lock[index].unlock();
    }
}
