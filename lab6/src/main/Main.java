package main;

import activeobject.Proxy;
import properties.Properties;
import threads.Consumer;
import threads.Producer;

public class Main {

    public static void main(String[] args) throws InterruptedException {
       System.out.println("result: "+doTest());
    }

    public static int doTest() throws InterruptedException {
        Proxy proxy = new Proxy();
        Consumer[] consumers = new Consumer[Properties.CONSUMERS];
        for(int i=0;i< Properties.CONSUMERS;i++){
            consumers[i]=new Consumer(proxy);
            new Thread(consumers[i]).start();
        }
        Producer[] producers = new Producer[Properties.PRODUCERS];
        for(int i=0;i< Properties.PRODUCERS;i++){
            producers[i]=new Producer(proxy);
            new Thread(producers[i]).start();
        }

        Thread.sleep(Properties.SLEEP_TIME * 1000);

        int operations=0;
        for(Consumer c : consumers){
            c.active=false;
            operations+=c.operations;
        }
        for(Producer p :producers){
            p.active=false;
            operations+=p.operations;
        }
        return operations;
    }
}
