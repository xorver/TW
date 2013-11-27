package zad4310;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {

    private Queue<Resource> a = new LinkedList<>();
    private Queue<Resource> b = new LinkedList<>();
    private Lock lock = new ReentrantLock();
    private Condition aCondition = lock.newCondition();
    private Condition abCondition = lock.newCondition();

    public Monitor() {
        for(int i=0;i<Properties.M;i++)
            a.add(new Resource(ResourceType.A));
        for(int i=0;i<Properties.N;i++)
            b.add(new Resource(ResourceType.B));
    }

    public Resource getA() throws InterruptedException {
        Resource toReturn;
        lock.lock();
        while(a.isEmpty())
            aCondition.await();
        toReturn = a.poll();
        lock.unlock();
        return toReturn;
    }

    public Resource getAB() throws InterruptedException {
        Resource toReturn;
        lock.lock();
        while(a.isEmpty() && b.isEmpty())
            abCondition.await();
        if(!a.isEmpty())
            toReturn = a.poll();
        else
            toReturn = b.poll();
        lock.unlock();
        return toReturn;
    }

    public Resource getABNowait(){
        Resource toReturn;
        lock.lock();
        if(!b.isEmpty())
            toReturn = b.poll();
        else
            toReturn = a.poll();
        lock.unlock();
        return toReturn;
    }

    public void returnResource(Resource resource){
        lock.lock();
        if(resource.type==ResourceType.A){
            a.add(resource);
            aCondition.signal();
            lock.unlock();
            Thread.yield();
            lock.lock();
            abCondition.signal();
        }
        else {
            b.add(resource);
            abCondition.signal();
        }
        lock.unlock();
        Thread.yield();
    }


    public class Resource{
        public ResourceType type;

        public Resource(ResourceType type) {
            this.type = type;
        }
    }
    public enum ResourceType{
        A,B
    }
}
