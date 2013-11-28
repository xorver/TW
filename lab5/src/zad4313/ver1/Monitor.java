package zad4313.ver1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    private Lock lock = new ReentrantLock();
    private Queue<WaitingDemand> waitingDemands = new LinkedList<>();
    private boolean busy;
    private int currentPosition;

    public void demandNewPosition(Demand demand) throws InterruptedException {
        lock.lock();
        WaitingDemand waitingDemand = new WaitingDemand(lock.newCondition(), demand);
        waitingDemands.add(waitingDemand);
        while(busy && waitingDemands.peek()!=waitingDemand)
            waitingDemand.condition.await();
        busy=true;
        waitingDemands.poll();
        currentPosition =waitingDemand.demand.cylinder;
        System.out.println("position changed to: " + currentPosition);
        lock.unlock();
    }

    public void release() {
        lock.lock();
        if(waitingDemands.isEmpty())
            busy=false;
        else
            waitingDemands.peek().condition.signal();
        lock.unlock();
    }

    private class WaitingDemand{
        public Condition condition;
        public Demand demand;

        private WaitingDemand(Condition condition, Demand demand) {
            this.condition = condition;
            this.demand = demand;
        }
    }
}
