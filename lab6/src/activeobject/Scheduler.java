package activeobject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Scheduler implements Runnable {
    private BlockingQueue<MethodRequest> consumptionRequests =new LinkedBlockingQueue<>();
    private BlockingQueue<MethodRequest> productionRequests =new LinkedBlockingQueue<>();
    private boolean productionPhase=true;

    public void enqueueProductionRequest(MethodRequest methodRequest){
        productionRequests.add(methodRequest);
    }
    public void enqueueConsumptionRequest(MethodRequest methodRequest){
        consumptionRequests.add(methodRequest);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while(true)
            try {
                MethodRequest currentRequest;
                if(productionPhase){
                    currentRequest=productionRequests.take();
                    while(!currentRequest.guard())
                        consumptionRequests.take().call();
                }
                else {
                    currentRequest=consumptionRequests.take();
                    while(!currentRequest.guard())
                        productionRequests.take().call();
                }
                currentRequest.call();
                productionPhase=!productionPhase;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
