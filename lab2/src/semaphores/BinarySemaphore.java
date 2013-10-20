package semaphores;


public class BinarySemaphore {

    private boolean isUp=true;

    public synchronized void up(){
        isUp=true;
        notify();
    }

    public synchronized void down() throws InterruptedException {
        while(!isUp)
            wait();
        isUp=false;
    }

}
