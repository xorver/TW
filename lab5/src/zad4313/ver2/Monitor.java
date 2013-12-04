package zad4313.ver2;

import java.util.TreeSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    private Lock lock = new ReentrantLock();
    private TreeSet<WaitingDemand> waitingDemands = new TreeSet<>();
    private int currentPosition=0;
    private boolean goingUp=true;
    private WaitingDemand currentDemand;
    private Monitor.WaitingDemand letInOnlyThisDemand;

    private void changePosition(WaitingDemand nextDemand){
        if(nextDemand.demand.cylinder!=currentPosition)
            goingUp = nextDemand.demand.cylinder >=currentPosition;
        currentPosition = nextDemand.demand.cylinder;
        currentDemand = nextDemand;
        System.out.println("position changed to: "+currentPosition);
    }

    public void demandNewPosition(Demand demand) throws InterruptedException {
        lock.lock();
        WaitingDemand waitingDemand = new WaitingDemand(lock.newCondition(), demand);
        waitingDemands.add(waitingDemand);
        while(waitingDemands.size()!=1 && letInOnlyThisDemand!=waitingDemand)
            waitingDemand.condition.await();
        changePosition(waitingDemand);
        lock.unlock();
    }

    public void release() {
        lock.lock();
        waitingDemands.remove(currentDemand);
        if(!waitingDemands.isEmpty())
            if((goingUp  && waitingDemands.higher(currentDemand)!=null) ||
                    (!goingUp && waitingDemands.lower(currentDemand)==null)){
                letInOnlyThisDemand = waitingDemands.higher(currentDemand);
                letInOnlyThisDemand.condition.signal();
            }
            else{
                letInOnlyThisDemand = waitingDemands.lower(currentDemand);
                letInOnlyThisDemand.condition.signal();
            }
        lock.unlock();
    }

    private class WaitingDemand implements Comparable<WaitingDemand>{
        public Condition condition;
        public Demand demand;

        private WaitingDemand(Condition condition, Demand demand) {
            this.condition = condition;
            this.demand = demand;
        }

        @Override
        public int compareTo(WaitingDemand o) {
            if(demand.cylinder.compareTo(o.demand.cylinder)==0)
                return Integer.valueOf(this.hashCode()).compareTo(o.hashCode());
            return demand.cylinder.compareTo(o.demand.cylinder);
        }
    }
}
