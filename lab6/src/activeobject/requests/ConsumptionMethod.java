package activeobject.requests;

import activeobject.Future;
import activeobject.MethodRequest;
import activeobject.Servant;

public class ConsumptionMethod extends MethodRequest {
    private int argument;

    public ConsumptionMethod(Servant servant, Future future, int argument) {
        super(servant, future, RequestType.lockConsumption);
        this.argument = argument;
    }

    @Override
    public boolean guard() {
        return  argument <= servant.getNumberOfFullElements();
    }

    @Override
    public void call() {
        future.setResult(servant.consume(argument));
        done=true;
    }
}
