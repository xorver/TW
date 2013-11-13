package zad431;

import java.util.LinkedList;
import java.util.Queue;

public class Monitor {
    private final int N=5;
    private final int M=10;

    private Queue<Resource> A;
    private Queue<Resource> B;
    private final Integer monitorA=0;
    private final Integer monitorB=1;


    public Monitor() {
        A = new LinkedList<>();
        B = new LinkedList<>();
        for(int i=0;i<N;i++)
            A.add(new Resource(i));
        for(int i=0;i<M;i++)
            B.add(new Resource(i));
    }

    public Resource getA() throws InterruptedException {
        Resource toReturn;
        synchronized (monitorA) {
            while(A.isEmpty())
                monitorA.wait();
            toReturn = A.poll();
        }
        synchronized (monitorB) {
            monitorB.notify();
        }
        return toReturn;

    }
    public void releaseA(Resource r){
        synchronized (monitorA){
            A.add(r);
        }
    }
    public Resource getB() throws InterruptedException {
        Resource toReturn;
        synchronized (monitorB) {
            while(B.isEmpty())
                monitorB.wait();
            toReturn = B.poll();
        }
        synchronized (monitorA) {
            monitorA.notify();
        }
        return toReturn;
    }
    public void releaseB(Resource r){
        synchronized (monitorB){
            B.add(r);
        }
    }
    public ResourcePair getAB() throws InterruptedException {
        return new ResourcePair(getA(),getB());
    }
    public void releaseAB(ResourcePair pair){
        synchronized (monitorA){
            A.add(pair.A);
        }
        synchronized (monitorB){
            B.add(pair.B);
        }
    }

    public class ResourcePair{
        public Resource A;
        public Resource B;

        public ResourcePair(Resource a, Resource b) {
            A = a;
            B = b;
        }
    }

}
