package zad433;

import common.Resource;

public class Buffer {
    private int capacity;
    public int size=0;

    public Buffer(int capacity) {
        this.capacity = capacity;
    }

    @SuppressWarnings("UnusedParameters")
    public void addElements(Resource[] resources, int n){
        System.out.println(Thread.currentThread() + " added "+n+" elements");
        size+=n;
        if(size>capacity)
            throw new OutOfMemoryError();
    }

    public Resource[] getElements(int n){
        System.out.println(Thread.currentThread() + " removed "+n+" elements");
        size-=n;
        if(size<0)
            throw new IllegalAccessError();
        return null;
    }
}
