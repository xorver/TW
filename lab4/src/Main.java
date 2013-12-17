public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("result: "+doTest());
    }

    public static int doTest() throws InterruptedException {
        Monitor monitor = new Monitor();
        Consumer[] consumers = new Consumer[Properties.CONSUMERS];
        for(int i=0;i< Properties.CONSUMERS;i++){
            consumers[i]=new Consumer(monitor);
            new Thread(consumers[i]).start();
        }
        Producer[] producers = new Producer[Properties.PRODUCERS];
        for(int i=0;i< Properties.PRODUCERS;i++){
            producers[i]=new Producer(monitor);
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
