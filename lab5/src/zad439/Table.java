package zad439;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Table {
    private Lock tableLock = new ReentrantLock();
    private Lock[] pairLock = new Lock[Properties.N];
    private Condition[] waitingForPair = new Condition[Properties.N];
    private boolean[] partnerIsWaiting = new boolean[Properties.N];
    private Condition waitingForTable = tableLock.newCondition();
    private int peopleAtTable=0;

    public Table() {
        for(int i=0;i<Properties.N;i++) {
            pairLock[i] = new ReentrantLock();
            waitingForPair[i]=pairLock[i].newCondition();
        }
    }

    public void seatAtTable(int j) throws InterruptedException {
        pairLock[j].lock();
        if(partnerIsWaiting[j]){
            tableLock.lock();
            while(peopleAtTable>0)
                waitingForTable.await();
            peopleAtTable+=2;
//            System.out.println("**sitting "+j);
            partnerIsWaiting[j]=false;
            waitingForPair[j].signal();
            tableLock.unlock();
        }else{
            partnerIsWaiting[j]=true;
            waitingForPair[j].await();
            tableLock.lock();
//            System.out.println("**sitting "+j);
            tableLock.unlock();
        }
        pairLock[j].unlock();
    }

    public void leaveTable(){
        tableLock.lock();
        peopleAtTable--;
//        System.out.println("**leaving ");
        if(peopleAtTable==0){
            System.out.println("------------");
            waitingForTable.signal();
        }
        tableLock.unlock();
    }
}
