package activeobject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Scheduler implements Runnable {
    BlockingQueue<MethodRequest> lockRequests=new LinkedBlockingQueue<>();
    BlockingQueue<MethodRequest> unlockRequests=new LinkedBlockingQueue<>();
    BlockingQueue<MethodRequest> lockConsumptionRequests=new LinkedBlockingQueue<>();
    BlockingQueue<MethodRequest> lockProductionRequests=new LinkedBlockingQueue<>();

    public void enqueueLockProductionRequest(MethodRequest methodRequest){
        lockRequests.add(methodRequest);
        lockProductionRequests.add(methodRequest);
    }
    public void enqueueLockConsumptionRequest(MethodRequest methodRequest){
        lockRequests.add(methodRequest);
        lockConsumptionRequests.add(methodRequest);
    }
    public void enqueueUnlockRequest(MethodRequest methodRequest){
        unlockRequests.add(methodRequest);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while(true){
                while(!unlockRequests.isEmpty())
                    unlockRequests.take().call();

                MethodRequest currentRequest = lockRequests.take();
                if(!currentRequest.isDone()) {
                    while(!currentRequest.guard()){
                        MethodRequest additionalRequest;
                        if(currentRequest.getRequestType()== MethodRequest.RequestType.lockConsumption)
                            additionalRequest=lockProductionRequests.take();
                        else
                            additionalRequest=lockConsumptionRequests.take();
                        if(!additionalRequest.isDone()){
                            while(true){
                                if(currentRequest.guard()){
                                    currentRequest.call();
                                    currentRequest=additionalRequest;
                                    break;
                                } else if(additionalRequest.guard()){
                                    additionalRequest.call();
                                    break;
                                }
                                unlockRequests.take().call();
                            }
                        }
                    }
                    currentRequest.call();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
