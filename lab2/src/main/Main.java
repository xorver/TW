package main;

import buffers.Buffer;
import runnables.K;
import runnables.P;
import runnables.S;

public class Main {

    public static void main(String args[]) {
        final int N=10;
        Buffer b1=new Buffer(N);
        Buffer b2=new Buffer(N);
        for(int i=0;i<5;i++)
            new Thread(new P(b1,i)).start();
        new Thread(new S(b1,b2)).start();
        new Thread(new K(b2)).start();
    }
}
