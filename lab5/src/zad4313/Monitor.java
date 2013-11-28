package zad4313;

import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    private Lock lock = new ReentrantLock();
    private TreeSet<WaitingDemand> waitingDemands = new TreeSet<>();
    private Queue<WaitingDemand>[] waitingDemandsList = new Queue[Properties.C];
    private boolean busy;
    private int currentPosition=0;
    private boolean goingUp=true;
    private WaitingDemand currentDemand;

    public Monitor() {
        for(int i=0;i<Properties.C;i++)
            waitingDemandsList[i]=new LinkedList<>();
    }

    private void changePosition(WaitingDemand nextDemand){
        goingUp = nextDemand.demand.cylinder >=currentPosition;
        currentPosition = nextDemand.demand.cylinder;
        currentDemand = nextDemand;
        System.out.println("position changed to: "+currentPosition);

    }

    public void demandNewPosition(Demand demand) throws InterruptedException {
        lock.lock();
        WaitingDemand waitingDemand = new WaitingDemand(lock.newCondition(), demand);
        if(waitingDemands.contains(waitingDemand))
            waitingDemandsList[waitingDemand.demand.cylinder].add(waitingDemand);
        else
            waitingDemands.add(waitingDemand);

        if(busy)
            waitingDemand.condition.await();
        busy=true;
        changePosition(waitingDemand);
        lock.unlock();
    }

    public void release() {
        lock.lock();
        for(WaitingDemand d : waitingDemands)
            System.out.print(d.demand.cylinder+" ");
        System.out.println();
        if(!waitingDemandsList[currentDemand.demand.cylinder].isEmpty())     {
            System.out.println("a");
            WaitingDemand nextDemand = waitingDemandsList[currentDemand.demand.cylinder].poll();
            waitingDemands.add(nextDemand);
            nextDemand.condition.signal();
        }
        else if(waitingDemands.isEmpty()){
            System.out.println("b");
            busy=false;
        }
        else
            if((goingUp && waitingDemands.higher(currentDemand)!=null) ||
                    (!goingUp && waitingDemands.lower(currentDemand)==null && waitingDemands.higher(currentDemand)!=null)){
//                for(WaitingDemand d : waitingDemands.tailSet(currentDemand))
//                    System.out.print(d.demand.cylinder+" ");
//                System.out.println();
                System.out.println("c");
                WaitingDemand nextDemand = waitingDemands.higher(currentDemand);
                waitingDemands.remove(nextDemand);

                nextDemand.condition.signal();

            }
            else if(!waitingDemands.isEmpty() && waitingDemands.lower(currentDemand)!=null){
//                for(WaitingDemand d : waitingDemands.headSet(currentDemand))
//                    System.out.print(d.demand.cylinder+" ");
//                System.out.println();
                WaitingDemand nextDemand = waitingDemands.lower(currentDemand);
                waitingDemands.remove(nextDemand);
                nextDemand.condition.signal();
                System.out.println("d");
            }
//            else {
//                System.out.println("e");
//                WaitingDemand nextDemand = waitingDemands.pollFirst();
//                nextDemand.condition.signal();
//            }


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
            return demand.cylinder.compareTo(o.demand.cylinder);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof WaitingDemand)
                return ((WaitingDemand) obj).demand.cylinder==this.demand.cylinder;
            else
                return obj.equals(this);
        }
    }
}
