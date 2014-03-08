package activeobject;

import activeobject.requests.ConsumptionMethod;
import activeobject.requests.ProductionMethod;

import java.util.List;

public class Proxy {
    private Scheduler scheduler = new Scheduler();
    private Servant servant = new Servant();

    public Proxy() {
        new Thread(scheduler).start();
    }

    public Future produce(List<Integer> argument) {
        Future result = new Future();
        scheduler.enqueueProductionRequest(new ProductionMethod(servant, result, argument));
        return result;
    }
    public Future consume(int n) {
        Future result = new Future();
        scheduler.enqueueConsumptionRequest(new ConsumptionMethod(servant, result, n));
        return result;
    }


}
