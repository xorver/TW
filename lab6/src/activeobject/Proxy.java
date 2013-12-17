package activeobject;

import activeobject.requests.LockConsumptionMethod;
import activeobject.requests.LockProductionMethod;
import activeobject.requests.UnlockConsumptionMethod;
import activeobject.requests.UnlockProductionMethod;

import java.util.List;

public class Proxy {
    private Scheduler scheduler = new Scheduler();
    private Servant servant = new Servant();

    public Proxy() {
        new Thread(scheduler).start();
    }

    public Future lockProduction(int n) {
        Future result = new Future();
        scheduler.enqueueLockProductionRequest(new LockProductionMethod(servant,result,n));
        return result;
    }
    public Future lockConsumption(int n) {
        Future result = new Future();
        scheduler.enqueueLockConsumptionRequest(new LockConsumptionMethod(servant, result, n));
        return result;
    }
    public Future unlockProduction(List<Integer> indexes) {
        Future result = new Future();
        scheduler.enqueueUnlockRequest(new UnlockProductionMethod(servant,result,indexes));
        return result;
    }
    public Future unlockConsumption(List<Integer> indexes) {
        Future result = new Future();
        scheduler.enqueueUnlockRequest(new UnlockConsumptionMethod(servant,result,indexes));
        return result;
    }

}
